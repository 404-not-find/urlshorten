package aws.urlshortener.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ResumeLog {

  private static final Logger LOGGER = LoggerFactory.getLogger(ResumeLog.class);

  private final BufferedWriter writer;

  public ResumeLog(@Value("${database.folder}") String resumeFolder) throws IOException {

    String filename;
    do {
      filename = generateFilename(resumeFolder);
    } while (new File(filename).exists());

    LOGGER.info("Appending to {} in folder {}.", filename, resumeFolder);
    final OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8);
    this.writer = new BufferedWriter(osw);
  }

  @PreDestroy
  public void bye() {
    try {
      this.writer.flush();
      this.writer.close();
    } catch (final IOException e) {
      LOGGER.info("Error shutting down.", e);
    }
  }

  public synchronized void write(ShortUrl hash, String url) throws IOException {
    this.writer.write(String.format("%s,%s%n", hash.toString(), url));
    this.writer.flush();
  }

  private static String generateFilename(final String resumeFolder) {
    return resumeFolder + String.valueOf(Instant.now().toEpochMilli());
  }
}
