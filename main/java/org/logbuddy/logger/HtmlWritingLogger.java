package org.logbuddy.logger;

import static org.logbuddy.LogBuddyException.check;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;

import org.logbuddy.Logger;
import org.logbuddy.Renderer;
import org.logbuddy.renderer.Html;

public class HtmlWritingLogger implements Logger {
  private final Renderer<Html> renderer;
  private final Writer writer;

  private HtmlWritingLogger(Renderer<Html> renderer, Writer writer) {
    this.renderer = renderer;
    this.writer = writer;
  }

  public static Logger writing(Renderer<Html> renderer, Writer writer) {
    check(renderer != null);
    check(writer != null);
    return new HtmlWritingLogger(renderer, writer);
  }

  public void log(Object model) {
    try {
      writer.write("<code>");
      writer.write(renderer.render(model).body);
      writer.write("</code>");
      writer.write("<br/>\n");
      writer.flush();
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
