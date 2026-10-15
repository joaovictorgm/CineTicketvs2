package com.example.demo;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;



import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SessãoTipoController {

    @FXML private TextField txtId;
    @FXML private ComboBox<EnumTipoExibicao> cbTipoExibicao;

    @FXML
    public void initialize() {
        cbTipoExibicao.setItems(FXCollections.observableArrayList(EnumTipoExibicao.values()));
        cbTipoExibicao.setConverter(new StringConverter<>() {
            @Override public String toString(EnumTipoExibicao t) { return t == null ? "" : t.toString(); }
            @Override public EnumTipoExibicao fromString(String s) { return null; }
        });
    }

    @FXML
    public void salvar(ActionEvent event) {
        EnumTipoExibicao tipo = cbTipoExibicao.getValue();
        if (tipo == null) {
            showMessage(Alert.AlertType.INFORMATION, "Escolha um tipo de exibição");
            return;
        }

        long id;
        try {
            id = Long.parseLong(txtId.getText().trim());
        } catch (NumberFormatException e) {
            showMessage(Alert.AlertType.INFORMATION, "Informe um Id numérico");
            return;
        }

        try {
            String json = "{\"tipoExibicao\":\"" + tipo.name() + "\"}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/sessao/" + id + "/tipo-exibicao"))
                    .header("Content-Type", "application/json")
                    // .header("Authorization", "Bearer " + token)
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> resp = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("STATUS " + resp.statusCode() + ": " + resp.body());

            if (resp.statusCode() == 200) {
                showMessage(Alert.AlertType.INFORMATION, "Tipo de exibição atualizado!");
            } else if (resp.statusCode() == 404) {
                showMessage(Alert.AlertType.ERROR, "Sessão não encontrado");
            } else {
                showMessage(Alert.AlertType.ERROR, "Erro " + resp.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            showMessage(Alert.AlertType.ERROR, "Falha na comunicação: " + e.getMessage());
        }
    }

    @FXML
    public void voltar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/menu-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
    }

    private void showMessage(Alert.AlertType type, String msg) {
        Alert alerta = new Alert(type);
        alerta.setTitle("Mensagem do Sistema!");
        alerta.setHeaderText(null);
        alerta.setContentText(msg);
        alerta.showAndWait();
    }
}