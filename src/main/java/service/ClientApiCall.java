package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import entities.Client;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

public class ClientApiCall {

    private Gson gson;
    private HttpClient client;

    private final String BASE_URL = ApiUtils.BASE_URL + "clients";
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public ClientApiCall() {
        /*
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        client = HttpClient.newHttpClient();
        */
        gson = new GsonBuilder().setDateFormat(dateFormat.toPattern()).create();
        client = HttpClient.newHttpClient();
    }

    public List<Client> getAllClients() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        try {
            // Enviar la solicitud y obtener la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            List<Client> clientList = gson.fromJson(responseBody, new TypeToken<List<Client>>() {
            }.getType());

            // Devolver la lista de clientes
            return clientList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Client getById(Long id) {
        try {
            // Construir la URL con el ID del cliente
            String url = BASE_URL + "/" + id;

            // Crear la solicitud HTTP GET
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            // Enviar la solicitud y obtener la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            if (response.statusCode() == 200) {
                return gson.fromJson(responseBody, Client.class);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Client> getClientsByName(String name) {
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
            List<Client> clientList = gson.fromJson(responseBody, new TypeToken<List<Client>>() {
            }.getType());

            return clientList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Método para obtener los últimos 20 clientes registrados
    public List<Client> getLatest20Clients() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/latest"))
                .GET()
                .build();

        try {
            // Enviar la solicitud y obtener la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            // Deserializar el JSON en una lista de clientes
            List<Client> clientList = gson.fromJson(responseBody, new TypeToken<List<Client>>() {
            }.getType());

            // Devolver la lista de clientes
            return clientList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Método para crear un nuevo cliente
    public Client createClient(Client client) {
        try {
            String jsonClient = gson.toJson(client);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/add"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonClient))
                    .build();

            HttpResponse<String> response = this.client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            if (response.statusCode() == 201) {
                return gson.fromJson(responseBody, Client.class);
            } else {
                throw new RuntimeException("Error al crear el cliente: " + response.statusCode() + " - " + responseBody);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Client searchClientByEmail(String email) {
        try {
            String url = BASE_URL + "/email?email=" + URLEncoder.encode(email, StandardCharsets.UTF_8);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return gson.fromJson(response.body(), Client.class);
            } else if (response.statusCode() == 404) {
                throw new RuntimeException("Cliente no encontrado");
            } else {
                throw new RuntimeException("Error al buscar cliente: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al realizar la solicitud: " + e.getMessage());
        }
    }

}
