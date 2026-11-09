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

public class GerenteEmpresaController {

    @FXML
    private TextField txtNome;


    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtSenha;

    @FXML
    private TextField txtRazaoSocial;

    @FXML
    private TextField txtCnpj;

    @FXML
    private TextField txtNomefantasia;

    @FXML
    private PasswordField txtChaveSecreta;





    @FXML
    public void salvarGerenteEmpresa(ActionEvent event) throws Exception {
        if (txtNome.getText().isBlank() || txtEmail.getText().isBlank() || txtSenha.getText().isBlank() || txtRazaoSocial.getText().isBlank() || txtCnpj.getText().isBlank() || txtNomefantasia.getText().isBlank() || txtChaveSecreta.getText().isBlank()) {
            showMessage(Alert.AlertType.INFORMATION, "Preencha todos os campos obrigatórios.");
            return;
        }


        //Validar Dados

        String jsonRequest = String.format(
                "{\"nome\": \"%s\", \"email\": \"%s\", \"senha\": \"%s\", \"secretKey\": \"%s\", "
                        + "\"razaoSocial\": \"%s\", \"cnpj\": \"%s\", \"nomeFantasia\": \"%s\"}",
                txtNome.getText(),
                txtEmail.getText(),
                txtSenha.getText(),
                txtChaveSecreta.getText(),
                txtRazaoSocial.getText(),
                txtCnpj.getText(),
                txtNomefantasia.getText());







        int respostaApi = executaMetodoAPI("http://localhost:8080/gerentes/admin",jsonRequest,"POST");


        if(respostaApi ==200){
            showMessage(Alert.AlertType.INFORMATION,"Gerente e Empresa salvo com sucesso!");
            voltar(event);

        }else {
            showMessage(Alert.AlertType.ERROR,"Erro ao salvar gerente e empresa! Código: " + respostaApi);
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