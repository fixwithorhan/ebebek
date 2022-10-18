package dev.orhantugrul.ebebekfirstcase;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

/**
 * Completely immutable class. No need a boring stuffs like getter, setters, etc. <br />
 * Impressed by new Record Classes {@see <a href="https://docs.oracle.com/en/java/javase/15/language/records.html">Oracle Docs</a>}. <br />
 * If you need a new object from this class you should create new one. <br />
 * <b>
 * Before start good to know. Yes, I love the 'final' keyword too much.
 * <i>Immutability will win.</i>
 * </b>
 */
public class Employee {
  public final String name;
  public final BigDecimal salary;
  public final int workHours;
  public final int hireYear;

  // Constructor taking a four parameter then validate them.
  // Parameters cannot be null and must be valid.
  public Employee(
      final String name,
      final BigDecimal salary,
      final int workHours,
      final int hireYear) {
    Objects.requireNonNull(name, "Employee name cannot be null");
    Objects.requireNonNull(salary, "Employee salary cannot be null");

    this.name = Optional.of(name)
        .filter(it -> !it.isEmpty())
        .orElseThrow(() -> new IllegalArgumentException("Employee name cannot be empty"));

    this.salary = Optional.of(salary)
        .filter(it -> it.compareTo(BigDecimal.ZERO) >= 0)
        .orElseThrow(() -> new IllegalArgumentException("Employee salary cannot lower than zero"));

    this.workHours = Optional.of(workHours)
        .filter(it -> it >= 0)
        .orElseThrow(() -> new IllegalArgumentException("Employee workHours cannot be negative"));

    this.hireYear = Optional.of(hireYear)
        .filter(it -> it >= 0)
        .orElseThrow(() -> new IllegalArgumentException("Employee hireYear cannot be negative"));
  }

  public BigDecimal tax() {
    final BigDecimal taxLimit = new BigDecimal("1000");
    final BigDecimal taxRate = new BigDecimal("0.03");

    return Optional.of(salary)
        .filter(it -> it.compareTo(taxLimit) > 0)
        .map(it -> it.multiply(taxRate))
        .orElse(BigDecimal.ZERO);
  }

  public BigDecimal bonus() {
    final int totalWorkHours = 40;
    final BigDecimal perHoursBonus = new BigDecimal("30");

    return Optional.of(workHours)
        .filter(it -> it > totalWorkHours)
        .map(it -> it - totalWorkHours)
        .map(it -> perHoursBonus.multiply(new BigDecimal(it)))
        .orElse(BigDecimal.ZERO);
  }

  public BigDecimal raiseSalary() {
    final int yearsOfWorkDays = 2021 - hireYear;
    final BigDecimal salaryRaiseRate = salaryRaiseRate(yearsOfWorkDays);
    return salary.multiply(salaryRaiseRate);
  }

  private BigDecimal salaryRaiseRate(final int yearsOfWorkDays) {
    if (yearsOfWorkDays < 10) return new BigDecimal("0.05");
    if (yearsOfWorkDays < 20) return new BigDecimal("0.10");
    return new BigDecimal("0.15");
  }

  private BigDecimal salaryWithBonusAndTax() {
    final BigDecimal bonus = bonus();
    final BigDecimal tax = tax();
    return salary.add(bonus).subtract(tax);
  }

  public BigDecimal totalSalary() {
    final BigDecimal raiseSalary = raiseSalary();
    final BigDecimal bonus = bonus();
    final BigDecimal tax = tax();
    return salary.add(raiseSalary).add(bonus).subtract(tax);
  }

  @Override
  public String toString() {
    final String output =
        "Adı : %s\n" +
            "Maaşı : %s\n" +
            "Çalışma saati : %s\n" +
            "Başlangıç yılı : %s\n" +
            "Vergi : %s\n" +
            "Bonus : %s\n" +
            "Maaş artışı : %s\n" +
            "Vergi ve bonuslar ile irlikte maaş : %s\n" +
            "Toplam maaş: %s\n";

    return String.format(output,
        name,
        salary,
        workHours,
        hireYear,
        tax(),
        bonus(),
        raiseSalary(),
        salaryWithBonusAndTax(),
        totalSalary());
  }
}
