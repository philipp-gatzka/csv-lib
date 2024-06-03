package net.internalerror.bean;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import net.internalerror.CSVBean;
import net.internalerror.CSVColumn;

@AllArgsConstructor
public class BeanWithIndexedColumns implements CSVBean {

  @CSVColumn(value = "Firstname", index = 10)
  private final String firstname;

  @CSVColumn(value = "Lastname", index = 1)
  private final String lastname;

  @CSVColumn(value = "Birthdate", index = 4)
  private final LocalDate dateOfBirth;

  @CSVColumn(value = "Gender", index = 7)
  private final int gender;

}
