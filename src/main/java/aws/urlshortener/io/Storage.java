package aws.urlshortener.io;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class Storage {

  private final Map<ShortUrl, String> hash2url = new HashMap<>();
  private final Map<String, ShortUrl> url2hash = new HashMap<>();

  private final Resume resume;

  public Storage(Resume resume) {
    this.resume = resume;
  }

  public synchronized void clear() {
    this.url2hash.clear();
    this.hash2url.clear();
  }

  public synchronized String findHash(ShortUrl hash) {
    return this.hash2url.get(hash);
  }

  public synchronized ShortUrl findUrl(String url) {
    return this.url2hash.get(url);
  }

  @PostConstruct
  public void init() {
    for (final ResumeItem ri : this.resume.load()) {
      final ShortUrl su = new ShortUrl(ri.getKey());
      this.hash2url.putIfAbsent(su, ri.getValue());
      this.url2hash.put(ri.getKey(), su);
    }
  }

  public synchronized boolean store(ShortUrl hash, String url) {
    if (this.hash2url.putIfAbsent(hash, url) != null) {
      return false;
    }
    this.url2hash.put(url, hash);
    return true;
  }
}
