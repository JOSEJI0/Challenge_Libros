package com.literatura.reto_literatura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {
    public String obtenerDatos(String url){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;
        try{
            System.out.println("Datos desde la URL" + url);
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Estatus del servidor: " + response.statusCode() + " " + response.body());
        } catch (IOException | InterruptedException e){
            throw new RuntimeException(e);
        }
        String json = response.body();
        return json;
    }
}
