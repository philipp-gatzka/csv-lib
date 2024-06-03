package net.internalerror;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public final class CSVWriter<T extends CSVBean> implements AutoCloseable {

  public static final String SEPARATOR_COMMA = ",";

  public static final String SEPARATOR_SEMICOLON = ";";

  public static final String QUOTE_CHAR_SINGLE_QUOTE = "'";

  public static final String QUOTE_CHAR_DOUBLE_QUOTE = "\"";

  private final Class<T> clazz;

  private final String separator;

  private final String quoteChar;

  private final BufferedWriter writer;

  private boolean headerWritten = false;

  public CSVWriter(@NotNull Class<T> clazz, @NotNull String separator, @NotNull String quoteChar, @NotNull String filepath) throws IOException {
    this.clazz = clazz;
    this.separator = separator;
    this.quoteChar = quoteChar;
    this.writer = new BufferedWriter(new FileWriter(filepath));
  }

  public CSVWriter(@NotNull Class<T> clazz, @NotNull String filepath) throws IOException {
    this(clazz, SEPARATOR_SEMICOLON, QUOTE_CHAR_DOUBLE_QUOTE, filepath);
  }

  @Override
  public void close() throws Exception {
    writer.close();
  }

  public void write(@NotNull T bean) throws IOException {
    write(List.of(bean));
  }

  public void write(@NotNull T[] beans) throws IOException {
    write(Arrays.asList(beans));
  }

  public void write(@NotNull Collection<T> beans) throws IOException {
    if (!headerWritten) {
      writeHeader();
    }
    for (T bean : beans) {
      writeBean(bean);
    }
  }

  private void writeBean(@NotNull T bean) throws IOException {
    List<Field> columns = getCSVColumns();
    String[] line = new String[columns.size()];

    for (int i = 0; i < columns.size(); i++) {
      Field column = columns.get(i);
      column.setAccessible(true);
      try {
        Object value = column.get(bean);
        String stringValue = value.toString();

        if (!(value instanceof Number)) {
          stringValue = quoteChar + stringValue + quoteChar;
        }
        line[i] = stringValue;

      } catch (Exception e) {
        throw new IllegalStateException(e);
      }
    }
    writeLine(line);
  }

  private void writeHeader() throws IOException {
    List<Field> columns = getCSVColumns();

    String[] header = new String[columns.size()];
    for (int i = 0; i < columns.size(); i++) {
      Field column = columns.get(i);
      String name = column.getAnnotation(CSVColumn.class).value();

      if ("".equals(name)) {
        name = column.getName();
      }
      header[i] = quoteChar + name + quoteChar;
    }

    writeLine(header);
    headerWritten = true;
  }

  private void writeLine(@NotNull String[] line) throws IOException {
    writeLine(String.join(separator, line));
  }

  private void writeLine(@NotNull String line) throws IOException {
    writer.write(line);
    writer.write("\n");
  }

  @NotNull
  public List<Field> getCSVColumns() {
    return Arrays.stream(clazz.getDeclaredFields()).filter(field -> field.isAnnotationPresent(CSVColumn.class))
                 .sorted((a, b) -> {
                   int indexA = a.getAnnotation(CSVColumn.class).index();
                   int indexB = b.getAnnotation(CSVColumn.class).index();
                   int compare = Integer.compare(indexA, indexB);

                   if (compare == 0) {
                     String valueA = a.getAnnotation(CSVColumn.class).value();
                     String valueB = b.getAnnotation(CSVColumn.class).value();
                     compare = valueA.compareTo(valueB);

                     if (compare == 0) {
                       String nameA = a.getName();
                       String nameB = b.getName();

                       return nameA.compareTo(nameB);
                     }

                   }

                   return compare;
                 }).toList();
  }

}
