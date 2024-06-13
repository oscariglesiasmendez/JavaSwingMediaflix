package codigo;

import entities.Employee;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class EmployeeTableModel extends AbstractTableModel {

    private List<Employee> employees = new ArrayList<>();
    String[] nombreColumnas = {"Id", "Nombre", "Apellidos", "NIF", "Fecha Contratación", "Salario"};
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public List<Employee> getEmployees() {
        return employees;
    }

    @Override
    public String getColumnName(int column) {
        return nombreColumnas[column];
    }

    @Override
    public int getRowCount() {
        return employees.size();
    }

    @Override
    public int getColumnCount() {
        return nombreColumnas.length;
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        Employee e = employees.get(fila);

        switch (columna) {
            case 0:
                return e.getEmployeeId();
            case 1:
                return e.getFirstName();
            case 2:
                return e.getLastName();
            case 3:
                return e.getNif();
            case 4:
                return e.getHiringDate();
            case 5:
                return e.getSalary();
            default:
                return null;
        }
    }

    public void addEmployees(List<Employee> employees) {
        this.employees.addAll(employees);
        fireTableDataChanged();
    }

    public void removeEmployees() {
        this.employees.clear();
        fireTableDataChanged();
    }

    public Employee getEmployee(int fila) {
        return employees.get(fila);
    }

    public void setValue(Object value) {
        if (value instanceof Date) {
            value = sdf.format(value);
        }
        this.setValue(value);
    }

    public void mostrarTablaPor(String filtro) {

        if (filtro == null) {
            return; // Si el filtro es null, sale del método
        }

        List<Employee> filterEmployees = null;

        switch (filtro) {
            case "Todos":
                filterEmployees = new ArrayList<>(employees);
                break;
            case "Empleados actuales":
                filterEmployees = new ArrayList<>();
                for (Employee e : employees) {
                    if (e.getAvailable()) {
                        filterEmployees.add(e);
                    }
                }
                break;
            case "Antiguos empleados":
                filterEmployees = new ArrayList<>();
                for (Employee e : employees) {
                    if (!e.getAvailable()) {
                        filterEmployees.add(e);
                    }
                }
                break;
            case "Fecha contratación ascendente":
                Collections.sort(employees, Comparator.comparing(Employee::getHiringDate));
                filterEmployees = new ArrayList<>(employees);
                break;
            case "Fecha contratación descendente":
                Collections.sort(employees, Comparator.comparing(Employee::getHiringDate).reversed());
                filterEmployees = new ArrayList<>(employees);
                break;
            case "Salario ascendente":
                Collections.sort(employees, Comparator.comparing(Employee::getSalary));
                filterEmployees = new ArrayList<>(employees);
                break;
            case "Salario descendente":
                Collections.sort(employees, Comparator.comparing(Employee::getSalary).reversed());
                filterEmployees = new ArrayList<>(employees);
                break;
            case "Alfabéticamente Ascendente":
                Collections.sort(employees, Comparator.comparing(Employee::getFirstName));
                filterEmployees = new ArrayList<>(employees);
                break;
            case "Alfabéticamente Descendente":
                Collections.sort(employees, Comparator.comparing(Employee::getFirstName).reversed());
                filterEmployees = new ArrayList<>(employees);
                break;
        }

        this.employees.clear();
        this.addEmployees(filterEmployees);
        this.fireTableDataChanged();
    }

}
