package aws.urlshortener.service;

import java.io.IOException;

import aws.urlshortener.dao.ShortenerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import aws.urlshortener.io.ShortUrl;

@Service
public class ShortenerService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ShortenerService.class);

  private final ShortenerRepository repository;

  public ShortenerService(ShortenerRepository repository) {
    this.repository = repository;
  }

  public String getRedirect(String hash) {
    return this.repository.findHash(new ShortUrl(hash));
  }

  public String newUrl(String url) {

    if (!url.startsWith("http://") && !url.startsWith("https://")) {
      url = "http://" + url;
    }

    final ShortUrl stored = this.repository.findUrl(url);
    if (stored != null) {
      LOGGER.info("Short url {} for {} already existed.", stored, url);
      return stored.toString();
    }

    ShortUrl shortUrl = new ShortUrl();
    while (!this.repository.store(shortUrl, url)) {
      LOGGER.error("Short url (hash) already in use {}.", shortUrl);
      shortUrl = new ShortUrl();
    }

    LOGGER.info("New short url: {} for {}.", shortUrl, url);
    try {
      this.repository.write(shortUrl, url);
      return shortUrl.toString();
    } catch (final IOException e) {
      LOGGER.error("Couldn't save short URL {} : {}.", shortUrl, url, e);
      return null;
    }
  }
}
