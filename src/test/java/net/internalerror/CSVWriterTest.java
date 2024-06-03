package net.internalerror;

import java.io.File;
import java.time.LocalDate;
import net.internalerror.bean.Bean;
import net.internalerror.bean.BeanWithIndexedColumns;
import net.internalerror.bean.BeanWithNonColumnFields;
import org.junit.jupiter.api.Test;

import static net.internalerror.CustomAssertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class CSVWriterTest {

  private static final File BEAN_SEPARATED_WITH_COMMA_FILE = new File("src/test/resources/BeanSeparatedWithComma.csv");

  private static final File BEAN_SEPARATED_WITH_HASHTAG_FILE = new File("src/test/resources/BeanSeparatedWithHashtag.csv");

  private static final File BEAN_QUOTED_WITH_SINGLE_QUOTES_FILE = new File("src/test/resources/BeanQuotedWithSingleQuotes.csv");

  private static final File BEAN_QUOTED_WITH_PERCENT_FILE = new File("src/test/resources/BeanQuotedWithPercent.csv");

  private static final File BEAN_WITH_NON_COLUMN_FIELD_FILE = new File("src/test/resources/BeanWithNonColumnField.csv");

  private static final File BEAN_WITH_INDEXED_COLUMNS = new File("src/test/resources/BeanWithIndexedColumns.csv");

  private String tempFile(String name) {
    return System.getProperty("java.io.tmpdir") + File.separator + name;
  }

  @Test
  void givenCommaAsSeparator_whenWrite_thenFileShouldBeSeparatedWithComma() {
    String filepath = tempFile("BeanSeparatedWithComma.csv");

    try (CSVWriter<Bean> writer = new CSVWriter<>(Bean.class, ",", "\"", filepath)) {
      writer.write(new Bean("John", "Snow", LocalDate.of(2000, 12, 12), 1));
    } catch (Exception e) {
      fail(e.getMessage(), e);
    }

    assertEquals(BEAN_SEPARATED_WITH_COMMA_FILE, new File(filepath));
  }

  @Test
  void givenHashtagAsSeparator_whenWrite_thenFileShouldBeSeparatedWithHashtag() {
    String filepath = tempFile("BeanSeparatedWithHashtag.csv");

    try (CSVWriter<Bean> writer = new CSVWriter<>(Bean.class, "#", "\"", filepath)) {
      writer.write(new Bean("John", "Snow", LocalDate.of(2000, 12, 12), 3));
      writer.write(new Bean("John", "Snow", LocalDate.of(2000, 10, 10), 2));
    } catch (Exception e) {
      fail(e.getMessage(), e);
    }

    assertEquals(BEAN_SEPARATED_WITH_HASHTAG_FILE, new File(filepath));
  }

  @Test
  void givenPercentAsSingleQuotes_whenWrite_thenFileShouldBeQuotedWithSingleQuotes() {
    String filepath = tempFile("BeanQuotedWithSingleQuotes.csv");

    try (CSVWriter<Bean> writer = new CSVWriter<>(Bean.class, ";", "'", filepath)) {
      writer.write(new Bean("John", "Snow", LocalDate.of(2000, 12, 12), 1));
      writer.write(new Bean("James", "Potter", LocalDate.of(2000, 10, 10), 764));
    } catch (Exception e) {
      fail(e.getMessage(), e);
    }

    assertEquals(BEAN_QUOTED_WITH_SINGLE_QUOTES_FILE, new File(filepath));
  }

  @Test
  void givenPercentAsQuoteChar_whenWrite_thenFileShouldBeQuotedWithPercent() {
    String filepath = tempFile("BeanQuotedWithPercent.csv");

    try (CSVWriter<Bean> writer = new CSVWriter<>(Bean.class, ";", "%", filepath)) {
      writer.write(new Bean("John", "Snow", LocalDate.of(2000, 12, 12), 23));
      writer.write(new Bean("James", "Potter", LocalDate.of(2000, 10, 10), 86));
    } catch (Exception e) {
      fail(e.getMessage(), e);
    }

    assertEquals(BEAN_QUOTED_WITH_PERCENT_FILE, new File(filepath));
  }

  @Test
  void givenBeanWithNonColumnFields_whenWrite_thenShouldFileShouldNotContainNonColumnFields() {

    String filepath = tempFile("BeanWithNonColumnField.csv");

    try (CSVWriter<BeanWithNonColumnFields> writer = new CSVWriter<>(BeanWithNonColumnFields.class, filepath)) {
      writer.write(new BeanWithNonColumnFields("John", "Snow", LocalDate.of(2000, 12, 12), 81));
      writer.write(new BeanWithNonColumnFields("James", "Potter", LocalDate.of(2000, 10, 10), 5));
    } catch (Exception e) {
      fail(e.getMessage(), e);
    }

    assertEquals(BEAN_WITH_NON_COLUMN_FIELD_FILE, new File(filepath));
  }

  @Test
  void givenBeanWithIndexedColumns_whenWrite_thenShouldFileShouldHaveColumnsInCorrectOrder() {

    String filepath = tempFile("BeanWithIndexedColumns.csv");

    try (CSVWriter<BeanWithIndexedColumns> writer = new CSVWriter<>(BeanWithIndexedColumns.class, filepath)) {
      writer.write(new BeanWithIndexedColumns[]{
          new BeanWithIndexedColumns("John", "Snow", LocalDate.of(2000, 12, 12), 45),
          new BeanWithIndexedColumns("James", "Potter", LocalDate.of(2000, 10, 10), 23)
      });
    } catch (Exception e) {
      fail(e.getMessage(), e);
    }

    assertEquals(BEAN_WITH_INDEXED_COLUMNS, new File(filepath));
  }

}