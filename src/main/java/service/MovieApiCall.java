package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import entities.Book;
import entities.BookPage;
import entities.Movie;
import entities.MoviePage;
import entities.Product;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MovieApiCall {

    private Gson gson;

    private HttpClient client;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private final String BASE_URL = ApiUtils.BASE_URL + "movies";

    public MovieApiCall() {
        gson = new GsonBuilder().setDateFormat(dateFormat.toPattern()).create();
        client = HttpClient.newHttpClient();
    }

    public List<Movie> getAllMovies() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/all"))
                .GET()
                .build();

        try {
            // Enviar la solicitud y obtener la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            // Deserializar el JSON en una lista de películas
            List<Movie> movieList = gson.fromJson(responseBody, new TypeToken<List<Movie>>() {
            }.getType());

            // Devolver la lista de películas
            return movieList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
    public List<Movie> getAllMovies() {
        List<Movie> allMovies = new ArrayList<>();
        int pageSize = 10; // Tamaño de página predeterminado
        int pageNumber = 0; // Página inicial

        try {
            while (true) {
                String url = BASE_URL + "movies?page=" + pageNumber + "&limit=" + pageSize;
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                String responseBody = response.body();

                JsonObject jsonResponse = gson.fromJson(responseBody, JsonObject.class);
                JsonArray moviesArray = jsonResponse.getAsJsonArray("movies");
                int totalMovies = jsonResponse.get("total").getAsInt();
                int moviesPerPage = moviesArray.size();

                for (JsonElement element : moviesArray) {
                    Movie movie = gson.fromJson(element, Movie.class);
                    allMovies.add(movie);
                }

                // Incrementar el número de página para la siguiente solicitud
                pageNumber++;

                // Si hemos alcanzado el total de peliculas, salir del bucle
                if (allMovies.size() >= totalMovies) {
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return allMovies;
    }
     */
    public Movie getMovieById(Long id) {
        String url = String.format(BASE_URL + "/" + id);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            // Enviar la solicitud y obtener la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            Movie movie = gson.fromJson(responseBody, Movie.class);

            return movie;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Movie createMovie(Movie movie) {
        String url = BASE_URL + "/add";

        String requestBody = gson.toJson(movie);

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
                // Si la respuesta es código 201, devolver la pelicula creada
                return gson.fromJson(response.body(), Movie.class);
            } else {
                System.out.println("Error al crear la pelicula: " + response.body());
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int updateMovie(Movie movie) {
        try {
            // URL de la API para editar un libro
            String url = BASE_URL + "/" + movie.getProductId();

            String jsonBody = gson.toJson(movie);
            System.out.println(jsonBody);

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
            throw new RuntimeException("Error al editar la pelicula.", e);
        }
    }

    public List<Movie> getAllMoviesByTitle(String title) {
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
            List<Movie> movieList = gson.fromJson(responseBody, new TypeToken<List<Movie>>() {
            }.getType());

            return movieList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
