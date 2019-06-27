package aws.urlshortener.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Api(tags = "Index Controller")
@Controller
public class IndexController {
    @ApiOperation(value = "index",notes = "forward to Index Page",httpMethod = "GET",response = String.class)
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数错误"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(path = "/main/index")
    public String index(){
        return "/main/index.html";
    }
}
