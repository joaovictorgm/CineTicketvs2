package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class LoginController {

    private String key = "asiudhakjshdkjahsdk09oq";

    @FXML
    private TextField txtkey;

    @FXML
    protected void onLoginButtonClick(ActionEvent event) throws IOException {
        if(txtkey.getText().equals(key)){
            showMessage(Alert.AlertType.INFORMATION,"Login Efetuado com sucesso!");

            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/com/example/demo/menu-view.fxml"));

            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);


        }else {
            showMessage(Alert.AlertType.ERROR, "Erro ao Efetuar login!");
        }
    }

    private void showMessage(Alert.AlertType type, String msg){
        Alert alerta =new Alert(type);
        alerta.setTitle("Mensagem do Sistema!");
        alerta.setHeaderText(null);
        alerta.setContentText(msg);
        alerta.showAndWait();
    }

}