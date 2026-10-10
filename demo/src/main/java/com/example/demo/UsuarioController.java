package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UsuarioController {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtTelefone;


    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private TextField txtFilme;

    @FXML
    private TextField txtSessao;

    @FXML
    private TextField txtIngresso;

    @FXML
    private TextField txtStatus;

    @FXML
    public void salvarUsuario(ActionEvent event) throws Exception {


        //Validar Dados

        String jsonRequest = String.format("{\n" +
                        "  \"email\": \"%s\",\n" +
                        "  \"filme\": \"%s\",\n" +
                        "  \"ingresso\": \"%s\",\n" +
                        "  \"nome\": \"%s\",\n" +
                        "  \"senha\": \"%s\",\n" +
                        "  \"sessao\": \"%s\",\n" +
                        "  \"telefone\": \"%s\"\n" +
                        "}", txtNome.getText(),
                txtEmail.getText(),
                txtFilme.getText(),
                txtIngresso.getText(),
                txtNome.getText(),
                txtSenha.getText(),
                txtSessao.getText(),
                txtTelefone.getText(),

                "sakjbhaskjcdhaiubshLKVAShfbdw68asd65d");

        int respostaApi = executaMetodoAPI("http://localhost:8080/usuarios/admin",jsonRequest,"POST");


        if(respostaApi ==200){
            showMessage(Alert.AlertType.INFORMATION,"Usuario salv com sucesso!");
            voltar(event);

        }else {
            showMessage(Alert.AlertType.ERROR,"Erro ao salvar usuário!");
        }





    }


    @FXML
    public void voltar(ActionEvent event) throws IOException {
        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("/com/example/back/menu-view.fxml"));

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

//        var  br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
//       var sb = new StringBuilder();
//        String output;
//        while ((output = br.readLine()) != null) {
//            sb.append(output);
//        }
//
//        var retornoBody = sb.toString();

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