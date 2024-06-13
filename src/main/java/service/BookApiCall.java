package service;

import entities.BookPage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import entities.Book;
import entities.Movie;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BookApiCall {

    private Gson gson;

    private HttpClient client;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private final String BASE_URL = ApiUtils.BASE_URL + "books";

    
    public BookApiCall() {
        gson = new GsonBuilder().setDateFormat(dateFormat.toPattern()).create();
        client = HttpClient.newHttpClient();
    }

    public List<Book> getAllBooks() {
        List<Book> allBooks = new ArrayList<>();
        int pageSize = 10; // Tamaño de página predeterminado
        int pageNumber = 0; // Página inicial

        try {
            while (true) {
                String url = BASE_URL + "?page=" + pageNumber + "&limit=" + pageSize;
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                String responseBody = response.body();

                JsonObject jsonResponse = gson.fromJson(responseBody, JsonObject.class);
                JsonArray booksArray = jsonResponse.getAsJsonArray("books");
                int totalBooks = jsonResponse.get("total").getAsInt();
                int booksPerPage = booksArray.size();

                for (JsonElement element : booksArray) {
                    Book book = gson.fromJson(element, Book.class);
                    allBooks.add(book);
                }

                // Incrementar el número de página para la siguiente solicitud
                pageNumber++;

                // Si hemos alcanzado el total de libros, salir del bucle
                if (allBooks.size() >= totalBooks) {
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return allBooks;
    }

    public Book getBookById(Long id) {
        String url = String.format(BASE_URL + "/" + id);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            // Enviar la solicitud y obtener la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            // Deserializar el JSON en un objeto Book
            Book book = gson.fromJson(responseBody, Book.class);

            // Devolver el objeto Book
            return book;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Book createBook(Book book) {
        String url = BASE_URL + "/add";

        // Convertir el objeto Book a JSON
        String requestBody = gson.toJson(book);

        // Configurar la solicitud HTTP POST
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            // Enviar la solicitud y obtener la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Verificar el código de respuesta
            if (response.statusCode() == 201) {
                // Si la respuesta es exitosa (código 201), devolver el libro creado
                return gson.fromJson(response.body(), Book.class);
            } else {
                // Si la respuesta no es exitosa, imprimir el cuerpo de la respuesta
                System.out.println("Error al crear el libro: " + response.body());
                return null; // O manejar el error de otra manera
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int updateBook(Book book) {
        try {
            // URL de la API para editar un libro
            String url = BASE_URL + "/" + book.getProductId();

            // Convertir el objeto Book a formato JSON
            String jsonBody = gson.toJson(book);
            System.out.println(jsonBody);

            // Crear la solicitud POST con el cuerpo JSON
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
            throw new RuntimeException("Error al editar el libro.", e);
        }
    }

    public List<Book> getAllBooksByTitle(String title) {
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

            // Parsear la respuesta directamente a una lista de libros
            List<Book> bookList = gson.fromJson(responseBody, new TypeToken<List<Book>>() {
            }.getType());

            return bookList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
