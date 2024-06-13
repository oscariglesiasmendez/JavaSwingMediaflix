package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import entities.Client;
import entities.Order;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;

public class OrderApiCall {

    private final String BASE_URL = ApiUtils.BASE_URL + "orders";

    private Gson gson;

    private HttpClient client;

    ClientApiCall clientApiCall = new ClientApiCall();
    
    public OrderApiCall() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();

        client = HttpClient.newHttpClient();
    }

    public List<Order> findAll() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            Type listType = new TypeToken<List<Order>>() {
            }.getType();
            return gson.fromJson(response.body(), listType);
        } else {
            System.out.println("Error: " + response.statusCode());
            return null;
        }
    }

    public Order createOrder(Long clientId, Order order) throws IOException, InterruptedException {

        String url = BASE_URL + "/" + clientId;

        String requestBody = gson.toJson(order);

        Client clientById = clientApiCall.getById(clientId);

        order.setClient(clientById);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            return gson.fromJson(response.body(), Order.class);
        } else if (response.statusCode() == 409) {
            System.out.println("Order already exists.");
            return null;
        } else {
            System.out.println("Error creating order: " + response.body());
            throw new RuntimeException("Error creating order: " + response.statusCode() + " - " + response.body());
        }
    }

    public List<Order> findLatestOrders() throws IOException, InterruptedException, URISyntaxException {
        String url = BASE_URL + "/latest";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            Type listType = new TypeToken<List<Order>>() {
            }.getType();
            return gson.fromJson(response.body(), listType);
        } else {
            System.out.println("Error: " + response.statusCode());
            return null;
        }
    }
    
    public List<Order> findOrdersByClientEmail(String email) throws IOException, InterruptedException, URISyntaxException {
        String url = BASE_URL + "/client-email?email=" + email;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            Type listType = new TypeToken<List<Order>>() {
            }.getType();
            return gson.fromJson(response.body(), listType);
        } else {
            System.out.println("Error: " + response.statusCode());
            throw new RuntimeException();
        }
    }

}
