package aws.urlshortener.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Resume {

  private static final Logger LOGGER = LoggerFactory.getLogger(Resume.class);

  private final String resumeFolder;

  public Resume(@Value("${database.folder}") String resumeFolder) {
    this.resumeFolder = resumeFolder;
  }

  private static List<ResumeItem> processFile(final File file) {

    final List<ResumeItem> urls = new ArrayList<>();

    try (
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file.getAbsolutePath()),
            StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr)) {

      LOGGER.info("Reading resume file: " + file.getName());
      // read all lines in file
      String line;
      while ((line = br.readLine()) != null) {
        urls.add(new ResumeItem(line.substring(0, ShortUrl.SHORT_URL_LEN), line.substring(ShortUrl.SHORT_URL_LEN + 1)));
      }
    } catch (final IOException e) {
      LOGGER.error("Error reading: " + file.getAbsolutePath(), e);
    }
    return urls;
  }

  public List<ResumeItem> load() {

    final File[] files = new File(this.resumeFolder).listFiles();

    LOGGER.info("Found " + files.length + " restore files.");

    final List<ResumeItem> urls = new ArrayList<>();
    for (final File file : files) {
      urls.addAll(processFile(file));
    }
    return urls;
  }
}
