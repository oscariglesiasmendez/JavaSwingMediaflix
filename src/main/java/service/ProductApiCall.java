package service;

import entities.Product;
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

/**
 *
 * @author igles
 */
public class ProductApiCall {

    private Gson gson;

    private HttpClient client;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private final String BASE_URL = ApiUtils.BASE_URL + "products";

    public ProductApiCall() {
        gson = new GsonBuilder().setDateFormat(dateFormat.toPattern()).create();
        client = HttpClient.newHttpClient();
    }

    public List<Product> getAllProducts() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/all"))
                .GET()
                .build();

        try {
            // Enviar la solicitud y obtener la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            // Deserializar el JSON en una lista de productos
            List<Product> productList = gson.fromJson(responseBody, new TypeToken<List<Product>>() {
            }.getType());

            // Devolver la lista de productos
            return productList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Product getById(Long id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return gson.fromJson(response.body(), Product.class);
            } else if (response.statusCode() == 404) {
                // Product not found
                return null;
            } else {
                throw new RuntimeException("Error retrieving product. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Product> getProductsByTitle(String title) {
        try {
            // Codificar el título antes de agregarlo a la URL
            String encodedTitle = URLEncoder.encode(title, "UTF-8");
            String url = BASE_URL + "/title?title=" + encodedTitle;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            // Parsear la respuesta directamente a una lista de productos
            List<Product> productList = gson.fromJson(responseBody, new TypeToken<List<Product>>() {
            }.getType());

            return productList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deactivateProduct(Long productId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/deactivate/" + productId))
                    .PUT(HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
            if (response.statusCode() != 200) {
                throw new RuntimeException("Failed to deactivate product. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void activateProduct(Long productId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/activate/" + productId))
                    .PUT(HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
            if (response.statusCode() != 200) {
                throw new RuntimeException("Failed to activate product. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Método para obtener todos los productos sin stock
    public List<Product> findAllProductsZeroStock() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/nostock"))
                .GET()
                .build();

        try {
            // Enviar la solicitud y obtener la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            // Deserializar el JSON en una lista de productos
            List<Product> productList = gson.fromJson(responseBody, new TypeToken<List<Product>>() {
            }.getType());

            // Devolver la lista de productos
            return productList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Método para obtener todos los productos con stock entre 1 y 5
    public List<Product> findAllProductsStockBetween1And5() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/lowstock"))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            List<Product> productList = gson.fromJson(responseBody, new TypeToken<List<Product>>() {
            }.getType());
            
            return productList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Product updateProduct(Long id, Product producto) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(gson.toJson(producto)))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Product updatedProduct = gson.fromJson(response.body(), Product.class);
                return updatedProduct;
            } else if (response.statusCode() == 404) {
                return null;
            } else {
                throw new RuntimeException("Error updating product. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
