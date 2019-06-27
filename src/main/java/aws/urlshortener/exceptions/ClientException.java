package aws.urlshortener.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Request has insufficient or incorrect data.
 *
 * @author sunway
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClientException extends RuntimeException {

  private static final long serialVersionUID = 3866317307521665552L;

  public ClientException(String msg) {
    super(msg);
  }
}
