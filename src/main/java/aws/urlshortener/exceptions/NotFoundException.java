package aws.urlshortener.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Short url not found.
 *
 * @author sunway
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

  private static final long serialVersionUID = 8605874693571371792L;

  public NotFoundException() {
    super("Not found.");
  }
}
