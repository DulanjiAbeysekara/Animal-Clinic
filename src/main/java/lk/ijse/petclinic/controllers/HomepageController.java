package lk.ijse.petclinic.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomepageController {


    @FXML
    private Button btnCustomer;

    @FXML
    private Button btnDoctor;

    @FXML
    private Button btnHealth;

    @FXML
    private Button btnPayment;

    @FXML
    private Button btnPet;

    @FXML
    private Button btnPetItem;

    @FXML
    private Button btnReport;

    @FXML
    private Button btnSupplier;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXButton supplierManagementBtn;

    public void btnPetOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/pet.fxml"));

        Scene scene = new Scene(anchorPane);

        Stage stage = (Stage)mainPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Pet Management");
        stage.centerOnScreen();

    }

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/customer.fxml"));

        Scene scene = new Scene(anchorPane);

        Stage stage = (Stage)mainPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Customer Management");
        stage.centerOnScreen();
    }

    public void btnDoctorOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/doctor.fxml"));

        Scene scene = new Scene(anchorPane);

        Stage stage = (Stage)mainPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Doctor Management");
        stage.centerOnScreen();
    }

    public void btnHealthOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/health.fxml"));

        Scene scene = new Scene(anchorPane);

        Stage stage = (Stage)mainPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Health Management");
        stage.centerOnScreen();
    }

    public void btnPetItemOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/petItem.fxml"));

        Scene scene = new Scene(anchorPane);

        Stage stage = (Stage)mainPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Pet Item Management");
        stage.centerOnScreen();
    }

    public void btnSupplierOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/supplier.fxml"));

        Scene scene = new Scene(anchorPane);

        Stage stage = (Stage)mainPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Supplier Management");
        stage.centerOnScreen();
    }

  /*  public void btnPaymentOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/payment.fxml"));

        Scene scene = new Scene(anchorPane);

        Stage stage = (Stage)mainPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Payment");
        stage.centerOnScreen();

    }*/

    public void btnReportOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/report.fxml"));

        Scene scene = new Scene(anchorPane);

        Stage stage = (Stage)mainPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Report");
        stage.centerOnScreen();
    }
}



