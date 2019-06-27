package aws.urlshortener.dao;

import java.io.IOException;

import org.springframework.stereotype.Repository;

import aws.urlshortener.io.ResumeLog;
import aws.urlshortener.io.ShortUrl;
import aws.urlshortener.io.Storage;

@Repository
public class ShortenerRepository {

  private final ResumeLog backup;
  private final Storage storage;

  public ShortenerRepository(ResumeLog backup, Storage storage) {
    this.storage = storage;
    this.backup = backup;
  }

  public String findHash(ShortUrl hash) {
    return this.storage.findHash(hash);
  }

  public ShortUrl findUrl(String url) {
    return this.storage.findUrl(url);
  }

  public boolean store(ShortUrl hash, String url) {
    return this.storage.store(hash, url);
  }

  public void write(ShortUrl hash, String url) throws IOException {
    this.backup.write(hash, url);
  }
}
