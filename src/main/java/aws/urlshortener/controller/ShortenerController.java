package aws.urlshortener.controller;

import aws.urlshortener.exceptions.ClientException;
import aws.urlshortener.exceptions.NotFoundException;
import aws.urlshortener.exceptions.ServerException;
import aws.urlshortener.service.ShortenerService;
import io.swagger.annotations.*;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import aws.urlshortener.io.ShortUrl;

@Api(tags = "URL Short Controller")
@RestController
public class ShortenerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ShortenerController.class);

  private final int redirectStatus;

  private final ShortenerService service;

  public ShortenerController(ShortenerService service, @Value("${server.redirect}") int redirectStatus) {
    this.service = service;
    this.redirectStatus = redirectStatus;
  }


  @PostMapping(path = "/new")
  @ApiOperation(value = "newUrl",notes = "create a new Url",httpMethod = "POST",response = ResponseEntity.class)
  @ApiImplicitParams({
          @ApiImplicitParam(name = "url",value = "url",required = true, dataType = "String",paramType = "query")
  })
  @ApiResponses({
          @ApiResponse(code=400,message="请求参数错误"),
          @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
  })
  public ResponseEntity<Response> newUrl(@RequestParam(value = "url",required = true) String url) {

    LOGGER.info("url=" ,url);
    if (!new UrlValidator(new String[] { "http", "https" }).isValid(url)) {
      throw new ClientException("Invalid Url.");
    }

    final String shortUrl = this.service.newUrl(url);

    if (shortUrl == null) {
      LOGGER.error("Failed to create short url: {}.", url);
      throw new ServerException();
    }

    return new ResponseEntity<>(Response.ok(shortUrl), HttpStatus.CREATED);
  }

  @GetMapping(path = "/{hash}")
  @ApiOperation(value = "redirect",notes = "redirect Url",httpMethod = "GET",response = ResponseEntity.class)
  @ApiImplicitParams({
          @ApiImplicitParam(name = "hash",value = "hash",required = true, dataType = "String",paramType = "path")
  })
  @ApiResponses({
          @ApiResponse(code=400,message="请求参数错误"),
          @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
  })
  public ResponseEntity<Response> redirect(@PathVariable(value = "hash") String hash) {

    if (hash == null || hash.trim().isEmpty()) {
      throw new ClientException("Expected short url.");
    }

    if (hash.trim().length() != ShortUrl.SHORT_URL_LEN) {
      throw new ClientException("Short url not valid.");
    }

    final String url = this.service.getRedirect(hash);
    if (url == null) {
      throw new NotFoundException();
    }

    final HttpHeaders headers = new HttpHeaders();
    headers.add("Location", url);
    return new ResponseEntity<>(Response.ok(), headers,
        this.redirectStatus == HttpStatus.FOUND.value() ? HttpStatus.FOUND : HttpStatus.MOVED_PERMANENTLY);
  }
}
