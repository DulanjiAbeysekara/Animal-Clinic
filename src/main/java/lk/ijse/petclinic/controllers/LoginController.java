package lk.ijse.petclinic.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    public static final String userName="Dulanji";
    public static final String pw="12345";

    public AnchorPane root;

    public PasswordField pass;
    @FXML
    private TextField txtUserNamer;
    @FXML
    private Button btnLogin;
    @FXML
    private Button loginButton;

    @FXML
    void loginButtonOnAction(ActionEvent event) {

    }

    private int count;

    public void btnLogInOnAction(ActionEvent actionEvent) throws IOException {
        count++;

        if (count>3){
            System.exit(0);
        }

        try {
           if(userName.equals(txtUserNamer.getText()) && pw.equals(pass.getText())){
                AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/homepage.fxml"));

                Scene scene = new Scene(anchorPane);

                Stage stage = (Stage)root.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Home Page");
                stage.centerOnScreen();

           }else {
                pass.setStyle("-fx-border-color: red");
                txtUserNamer.setStyle("-fx-border-color: red");

            }
  }catch (Exception e){
            System.out.println(e);
        }




    }

    public void txtPasswordOnAction(ActionEvent actionEvent) throws IOException {
        btnLogInOnAction(actionEvent);
    }
}
