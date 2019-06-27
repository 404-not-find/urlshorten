package aws.urlshortener.filters;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Log http request and response.
 *
 * @author sunway
 */
@Component
@Order(1)
public class LoggingFilter implements Filter {

  private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);

  @Override
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
      throws IOException, ServletException {

    chain.doFilter(req, resp);

    final HttpServletRequest request = (HttpServletRequest) req;
    final HttpServletResponse response = (HttpServletResponse) resp;

    final StringBuilder sb = new StringBuilder();
    sb.append(request.getRemoteAddr());
    sb.append(" - - [");
    sb.append(DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z").format(ZonedDateTime.now()));
    sb.append("] \"");
    sb.append(request.getMethod());
    sb.append(" ");
    sb.append(request.getRequestURI());
    sb.append(" ");
    sb.append(request.getProtocol());
    sb.append("\" ");
    sb.append(response.getStatus());
    sb.append(" NA NA NA");

    LOGGER.info(sb.toString());
  }
}
