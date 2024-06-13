package codigo;

import entities.Client;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ClientTableModel extends AbstractTableModel {

    private List<Client> clients = new ArrayList<>();
    String[] nombreColumnas = {"Id", "Nombre", "Email", "Fecha Alta", "Disponible"};

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public List<Client> getClients() {
        return clients;
    }

    @Override
    public String getColumnName(int column) {
        return nombreColumnas[column];
    }

    @Override
    public int getRowCount() {
        return clients.size();
    }

    @Override
    public int getColumnCount() {
        return nombreColumnas.length;
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == getColumnCount() - 1) {
            return Boolean.class; // La última columna será de tipo booleano para el checkbox
        }
        return super.getColumnClass(columnIndex);
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        Client c = clients.get(fila);

        switch (columna) {
            case 0:
                return c.getClientId();
            case 1:
                return c.getFirstName();
            case 2:
                return c.getEmail();
            case 3:
                return sdf.format(c.getCreationDate());
            case 4:
                return c.getAvailable();
            default:
                return null;
        }
    }

    public void addClients(List<Client> clients) {
        this.clients.addAll(clients);
        fireTableDataChanged();
    }

    public void removeClients() {
        this.clients.clear();
        fireTableDataChanged();
    }

    public Client getClient(int fila) {
        return clients.get(fila);
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

        List<Client> filterClients = null;

        switch (filtro) {
            case "Todos":
                filterClients = new ArrayList<>(clients);
                break;
            case "Disponibles":
                filterClients = new ArrayList<>();
                for (Client p : clients) {
                    if (p.getAvailable()) {
                        filterClients.add(p);
                    }
                }
                break;
            case "No Disponibles":
                filterClients = new ArrayList<>();
                for (Client p : clients) {
                    if (!p.getAvailable()) {
                        filterClients.add(p);
                    }
                }
                break;
            case "Fecha Ascendente":
                Collections.sort(clients, Comparator.comparing(Client::getCreationDate));
                filterClients = new ArrayList<>(clients);
                break;
            case "Fecha Descendente":
                Collections.sort(clients, Comparator.comparing(Client::getCreationDate).reversed());
                filterClients = new ArrayList<>(clients);
                break;
            case "Alfabéticamente Ascendente":
                Collections.sort(clients, Comparator.comparing(Client::getFirstName));
                filterClients = new ArrayList<>(clients);
                break;
            case "Alfabéticamente Descendente":
                Collections.sort(clients, Comparator.comparing(Client::getFirstName).reversed());
                filterClients = new ArrayList<>(clients);
                break;
        }

        this.clients.clear();
        this.addClients(filterClients);
        this.fireTableDataChanged();
    }

}
