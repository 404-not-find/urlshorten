package aws.urlshortener.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Server exception.
 *
 * @author sunway
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ServerException extends RuntimeException {

  private static final long serialVersionUID = -7581174838391096411L;

  public ServerException() {
    super("Service not available.");
  }
}
