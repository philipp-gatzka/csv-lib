package net.internalerror.bean;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import net.internalerror.CSVBean;
import net.internalerror.CSVColumn;

@AllArgsConstructor
public class BeanWithNonColumnFields implements CSVBean {

  @CSVColumn("Firstname")
  private final String firstname;

  @CSVColumn("Lastname")
  private final String lastname;

  private final LocalDate dateOfBirth;

  @CSVColumn(value = "Gender")
  private final int gender;

}
