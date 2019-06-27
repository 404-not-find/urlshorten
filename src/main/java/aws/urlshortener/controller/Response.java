package aws.urlshortener.controller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "response")
class Response {

  public enum Success {
    YES, NO
  }

  @ApiModelProperty(value = "success",name = "success")
  private final Success success;
  @ApiModelProperty(value = "data",name = "data")
  private final String data;

  private Response(Success success, String result) {
    this.success = success;
    this.data = result;
  }

  public static Response fail(String message) {
    return new Response(Success.NO, message);
  }

  public static Response ok() {
    return new Response(Success.YES, null);
  }

  public static Response ok(String result) {
    return new Response(Success.YES, result);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final Response other = (Response) obj;
    if (this.data == null) {
      if (other.data != null) {
        return false;
      }
    } else {
      if (!this.data.equals(other.data)) {
        return false;
      }
    }
    return this.success == other.success;
  }

  public String getResult() {
    return this.data;
  }

  public Success getSuccess() {
    return this.success;
  }

  @Override
  public int hashCode() {
    final int PRIME = 31;
    int result = 1;
    result = PRIME * result + (this.data == null ? 0 : this.data.hashCode());
    result = PRIME * result + (this.success == null ? 0 : this.success.hashCode());
    return result;
  }
}
