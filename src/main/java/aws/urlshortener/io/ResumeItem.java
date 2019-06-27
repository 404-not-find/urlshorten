package aws.urlshortener.io;

/**
 * Class to hold data records from restore files
 */
public class ResumeItem {
  private final String key;
  private final String value;

  public ResumeItem(final String key, final String value) {
    this.key = key;
    this.value = value;
  }

  public String getKey() {
    return this.key;
  }

  public String getValue() {
    return this.value;
  }
}
