package entities;

import java.math.BigDecimal;
import java.sql.Date;

public class Employee {

    private Long employeeId;

    private String firstName;

    private String lastName;

    private String nif;

    private String password;

    private String email;

    private Double salary;

    private Date hiringDate;

    private Boolean available;

    public Employee(Long employeeId, String firstName, String lastName, String nif, String password, String email, Double salary, Date hiringDate, Boolean available) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nif = nif;
        this.password = password;
        this.email = email;
        this.salary = salary;
        this.hiringDate = hiringDate;
        this.available = available;
    }

    public Employee(String firstName, String lastName, String nif, String password, String email, Double salary, Date hiringDate, Boolean available) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nif = nif;
        this.password = password;
        this.email = email;
        this.salary = salary;
        this.hiringDate = hiringDate;
        this.available = available;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Date getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(Date hiringDate) {
        this.hiringDate = hiringDate;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Employee{" + "employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName + ", nif=" + nif + ", password=" + password + ", email=" + email + ", salary=" + salary + ", hiringDate=" + hiringDate + ", available=" + available + '}';
    }

}
