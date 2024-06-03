package net.internalerror.bean;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import net.internalerror.CSVBean;
import net.internalerror.CSVColumn;

@AllArgsConstructor
public class Bean implements CSVBean {

  @CSVColumn
  private final String firstname;

  @CSVColumn
  private final String lastname;

  @CSVColumn
  private final LocalDate dateOfBirth;

  @CSVColumn
  private final int gender;

}
