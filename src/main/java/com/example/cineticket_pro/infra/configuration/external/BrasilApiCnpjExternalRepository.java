package com.example.cineticket_pro.infra.configuration.external;

import com.example.cineticket_pro.domain.interfaces.DadosCnpj;
import com.example.cineticket_pro.domain.interfaces.iCnpjExternalRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Component
public class BrasilApiCnpjExternalRepository implements iCnpjExternalRepository {

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10)).build();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public DadosCnpj buscarEmpresaPorCnpj(String cnpj) {
        String numeros = cnpj.replaceAll("[^0-9]", "");
        var url = "https://brasilapi.com.br/api/cnpj/v1/" + numeros;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(10))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode json = objectMapper.readTree(response.body());
                return new DadosCnpj(
                        json.path("razao_social").asText(null),
                        json.path("nome_fantasia").asText(null));
            }

            if (response.statusCode() == 404 || response.statusCode() == 400) {
                throw new IllegalArgumentException("CNPJ não encontrado");
            }

            throw new RuntimeException("Erro ao consultar CNPJ");

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Serviço de CNPJ indisponível", e);
        }
    }
}