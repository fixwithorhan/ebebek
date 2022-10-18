package dev.orhantugrul.ebebekfirstcase;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class Main {
  public static void main(final String... args) {
    createEmployees().forEach(System.out::println);
  }

  public static List<Employee> createEmployees() {
    final Employee ziynet = new Employee("Ziynet Songül", new BigDecimal("900"), 45, 2019);
    final Employee ilhan = new Employee("İlhan Devrim", new BigDecimal("1300"), 35, 2017);
    final Employee rizvan = new Employee("Rızvan Bünyamin", new BigDecimal("1500"), 38, 2015);
    final Employee yasin = new Employee("Yasin Beyazlı", new BigDecimal("2000"), 43, 2013);
    final Employee kemal = new Employee("Kemal Aydın", new BigDecimal("2000"), 45, 1985);
    return Arrays.asList(ziynet, ilhan, rizvan, yasin, kemal);
  }
}