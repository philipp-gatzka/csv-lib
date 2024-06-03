package net.internalerror;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import lombok.experimental.UtilityClass;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

@UtilityClass
public class CustomAssertions {

  public static void assertFileExists(String filepath) {
    assertFileExists(new File(filepath));
  }

  public static void assertFileExists(File file) {
    assertTrue(file.exists());
    assertTrue(file.isFile());
  }

  public static void assertEquals(File expected, File actual) {
    assertFileExists(actual);

    assertDoesNotThrow(() -> {
      String expectedContent = readFile(expected);
      String actualContent = readFile(actual);

      Assertions.assertEquals(expectedContent, actualContent);
    });
  }

  public static String readFile(String filepath) throws Exception {
    return readFile(new File(filepath));
  }

  public static String readFile(File file) throws Exception {
    StringBuilder stringBuilder = new StringBuilder();

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String line;

      while ((line = reader.readLine()) != null) {
        stringBuilder.append(line).append("\n");
      }
    }

    return stringBuilder.toString();
  }

}
