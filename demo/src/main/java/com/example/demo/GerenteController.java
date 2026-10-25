package com.example.demo;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GerenteController {

    @FXML
    private TextField txtNome;


    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtSenha;





    @FXML
    public void salvarGerente(ActionEvent event) throws Exception {
        if (txtNome.getText().isBlank() || txtEmail.getText().isBlank() || txtSenha.getText().isBlank()) {
            showMessage(Alert.AlertType.INFORMATION, "Preencha nome, e-mail e senha.");
            return;
        }


        //Validar Dados

        String jsonRequest = String.format(
                "{\"nome\": \"%s\", \"email\": \"%s\", \"senha\": \"%s\"}",
                txtNome.getText(),
                txtEmail.getText(),
                txtSenha.getText());


        int respostaApi = executaMetodoAPI("http://localhost:8080/gerentes",jsonRequest,"POST");


        if(respostaApi ==200){
            showMessage(Alert.AlertType.INFORMATION,"Gerente salvo com sucesso!");
            voltar(event);

        }else {
            showMessage(Alert.AlertType.ERROR,"Erro ao salvar gerente!");
        }





    }


    @FXML
    public void voltar(ActionEvent event) throws IOException {
        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("/com/example/demo/menu-view.fxml"));

        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);

    }

    private int executaMetodoAPI(String url,String json,String protocoloHttp)
            throws Exception {


        URL urlAPI = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlAPI.openConnection();
        connection.setRequestMethod(protocoloHttp);
        connection.setDoOutput(true);


        if(!json.isEmpty()){
            connection.setRequestProperty("Content-Type","application/json");

            try (OutputStream os = connection.getOutputStream()){
                os.write(json.getBytes());
            }

        }

//

        return connection.getResponseCode();







    }
    private void showMessage(Alert.AlertType type, String msg){
        Alert alerta =new Alert(type);
        alerta.setTitle("Mensagem do Sistema!");
        alerta.setHeaderText(null);
        alerta.setContentText(msg);
        alerta.showAndWait();
    }

}