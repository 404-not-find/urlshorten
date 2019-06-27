package aws.urlshortener;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Application starting point.
 *
 * @author sunway
 */
@SpringBootApplication
public class UrlShortenerApplication extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(UrlShortenerApplication.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(UrlShortenerApplication.class, args);
  }
}
