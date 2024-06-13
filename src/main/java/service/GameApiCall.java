package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import entities.Book;
import entities.Game;
import entities.GamePage;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class GameApiCall {

    private Gson gson;

    private HttpClient client;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private final String BASE_URL = ApiUtils.BASE_URL + "games";

    public GameApiCall() {
        gson = new GsonBuilder().setDateFormat(dateFormat.toPattern()).create();
        client = HttpClient.newHttpClient();
    }

    public List<Game> getAllGames() {
        List<Game> allGames = new ArrayList<>();
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
                JsonArray gamesArray = jsonResponse.getAsJsonArray("games");
                int totalGames = jsonResponse.get("total").getAsInt();
                int gamesPerPage = gamesArray.size();

                for (JsonElement element : gamesArray) {
                    Game game = gson.fromJson(element, Game.class);
                    allGames.add(game);
                }

                // Incrementar el número de página para la siguiente solicitud
                pageNumber++;

                // Si hemos alcanzado el total de peliculas, salir del bucle
                if (allGames.size() >= totalGames) {
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return allGames;
    }

    public Game getGameById(Long id) {
        String url = String.format(BASE_URL + "/" + id);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            // Enviar la solicitud y obtener la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            Game game = gson.fromJson(responseBody, Game.class);

            return game;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Game createGame(Game game) {
        String url = BASE_URL + "/add";

        String requestBody = gson.toJson(game);

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
                return gson.fromJson(response.body(), Game.class);
            } else {
                System.out.println("Error al crear el juego: " + response.body());
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int updateGame(Game game) {
        try {
            // URL de la API para editar un libro
            String url = BASE_URL + "/" + game.getProductId();

            String jsonBody = gson.toJson(game);
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
            throw new RuntimeException("Error al editar el juego.", e);
        }
    }

    public List<Game> getAllGamesByTitle(String title) {
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

            // Parsear la respuesta directamente a una lista de videojuegos
            List<Game> gameList = gson.fromJson(responseBody, new TypeToken<List<Game>>() {
            }.getType());

            return gameList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
