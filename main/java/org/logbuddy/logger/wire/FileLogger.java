package org.logbuddy.logger.wire;

import static java.nio.file.Files.createFile;
import static java.nio.file.Files.deleteIfExists;
import static java.nio.file.Files.newBufferedWriter;
import static org.logbuddy.LogBuddyException.check;
import static org.logbuddy.logger.HtmlWritingLogger.writing;
import static org.logbuddy.logger.TextWritingLogger.writing;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Path;

import org.logbuddy.LogBuddyException;
import org.logbuddy.Logger;
import org.logbuddy.Renderer;
import org.logbuddy.renderer.Html;
import org.logbuddy.renderer.Text;

public class FileLogger {
  public static Logger fileLogger(Path file, Renderer<Text> renderer, Text... erasure) {
    check(file != null);
    check(renderer != null);
    return writing(renderer, writer(file));
  }

  public static Logger fileLogger(Path file, Renderer<Html> renderer, Html... erasure) {
    check(file != null);
    check(renderer != null);
    return writing(renderer, writer(file));
  }

  private static Writer writer(Path file) {
    try {
      Charset charset = Charset.forName("utf8");
      deleteIfExists(file);
      return newBufferedWriter(createFile(file), charset);
    } catch (IOException e) {
      throw new LogBuddyException(e);
    }
  }
}