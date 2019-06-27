function getUrlFromForm() {
    var url = f.v.value.replace(/^\s\s*/, '').replace(/\s\s*$/, '')
    if ((url.indexOf('http://') !== 0) && (url.indexOf('https://') !== 0)) {
        return 'http://' + url
    }
    return url
}


function inputError() {
    f.v.style.borderColor = 'red'
    error.innerHTML = 'Invalid URL!'
}

function inputForbidden(reason) {
    f.v.style.borderColor = 'red'
    if (reason === 'malware')
        error.innerHTML = 'Forbidden URL - Suspected Malware'
    else if (reason === 'phishing')
        error.innerHTML = 'Forbidden URL - Suspected Phishing'
}

function inputDisable() {
    f.v.enabled = false;
    f.s.enabled = false;
}

function inputEnable() {
    f.v.enabled = true;
    f.s.enabled = true;
}

function clearError() {
    f.v.style.borderColor = '#444'
    error.innerHTML = '&nbsp;'
}

function submit(su) {
    var http = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP")
    http.onreadystatechange = function() {
        if (http.readyState == 4) {
            inputEnable()
            if (http.status == 200 || http.status == 201) {
                if (http.responseText !== '') {
                    error.innerHTML = '&nbsp;'
                        // place result in form and select text

                    var resp = http.responseText
                    var jsObj = JSON.parse(resp)
                    var px = window.location.protocol
                    var host = window.location.host
                    var port = window.location.port
                    f.v.value = px + '//' + host  + '/' + jsObj.result
                    f.v.focus()
                    f.v.select()
                } else {
                    error.innerHTML = 'Please try again.'
                }
            } else if (http.status == 202) {
                submit(su)
            } else if (http.status == 400 || http.status == 500) {
                inputError()
            } else if (http.status == 403) {
                inputForbidden(http.responseText)
            } else {
                // alert(http.status)
                error.innerHTML = 'Please try again.'
            }
        }
    };
    http.open('POST', '/new/?url=' + su, true)
    http.send()
    error.innerHTML = 'Processing...'
}

window.onload = function() {
    f.v.onfocus = function() {
        clearError()
    };
    f.v.onclick = function() {
        f.v.select()
    };
    f.v.onchange = function() {
        clearError()
    };
    f.onsubmit = function() {
        // do basic validation, server will still refuse invalid Urls
        var su = getUrlFromForm()
        f.v.style.color = 'black'
        inputDisable();
        submit(su);
        // returning 'false' cancels HTML basic submit behavior
        return false
    };
};