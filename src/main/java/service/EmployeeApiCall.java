package service;

import entities.Employee;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.List;
import utils.PasswordUtilities;

public class EmployeeApiCall {

    private Gson gson;

    private HttpClient client;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    private final String BASE_URL = ApiUtils.BASE_URL + "employees";

    public EmployeeApiCall() {
        gson = new GsonBuilder().setDateFormat(dateFormat.toPattern()).create();
        client = HttpClient.newHttpClient();
    }

    public List<Employee> getAllEmployees() {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            // Deserializar el JSON en una lista de productos
            List<Employee> employeeList = gson.fromJson(responseBody, new TypeToken<List<Employee>>() {
            }.getType());

            // Devolver la lista de productos
            return employeeList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Employee> getAllAvailableEmployees() {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/available"))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            // Deserializar el JSON en una lista de productos
            List<Employee> employeeList = gson.fromJson(responseBody, new TypeToken<List<Employee>>() {
            }.getType());

            // Devolver la lista de productos
            return employeeList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Employee> getEmployeesByName(String name) {
        try {
            // Codificar el nombre antes de agregarlo a la URL, asi permite tener espacios
            String encodedName = URLEncoder.encode(name, "UTF-8");
            String url = BASE_URL + "/name?name=" + encodedName;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            // Parsear la respuesta directamente a una lista de clientes
            List<Employee> employeesList = gson.fromJson(responseBody, new TypeToken<List<Employee>>() {}.getType());
            return employeesList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Employee createEmployee(Employee employee) {
        String url = BASE_URL + "/add";

        String requestBody = gson.toJson(employee);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                return gson.fromJson(response.body(), Employee.class);
            } else {
                System.out.println("Error al crear el empleado: " + response.body());
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int updateEmployee(Employee employee) {
        try {
            // URL de la API para editar un empleado
            String url = BASE_URL + "/" + employee.getEmployeeId();

            String encryptPassword = PasswordUtilities.encryptPassword(employee.getPassword());
            employee.setPassword(encryptPassword);
            
            // Convertir el objeto EmployeeDto a formato JSON
            String jsonBody = gson.toJson(employee);

            // Crear la solicitud PUT con el cuerpo JSON
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            // Enviar la solicitud y obtener la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Devolver el código de estado de la respuesta
            return response.statusCode();
        } catch (Exception e) {
            throw new RuntimeException("Error al editar el empleado.", e);
        }
    }

    public void deactivateEmployee(Long employeeId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/deactivate/" + employeeId))
                    .PUT(HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
            if (response.statusCode() != 200) {
                throw new RuntimeException("Failed to deactivate employee. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void activateEmployee(Long employeeId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/activate/" + employeeId))
                    .PUT(HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
            if (response.statusCode() != 200) {
                throw new RuntimeException("Failed to activate employee. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
