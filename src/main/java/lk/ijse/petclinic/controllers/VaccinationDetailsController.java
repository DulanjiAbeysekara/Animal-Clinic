package lk.ijse.petclinic.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.petclinic.dto.Vaccination;
import lk.ijse.petclinic.dto.tm.VaccinationDetail;
import lk.ijse.petclinic.model.VaccinationModel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;

public class VaccinationDetailsController {
    private static final String URL = "jdbc:mysql://localhost:3306/petClinic";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "12345");
    }

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnView;

    @FXML
    private TextField txtBrand;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtDateOfIssue;

    @FXML
    private TextField txtDoctorId;

    @FXML
    private TextField txtExpiryDate;

    @FXML
    private TextField txtPaymentCode;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtVaccinationId;

    @FXML
    private TextField txtVaccineName;

    @FXML
    private TextField txtPetId;

    @FXML
    private AnchorPane vaccineDetailsPane;


    public void btnAddOnAction(ActionEvent actionEvent) {

        String id = txtVaccinationId.getText();
        String name = txtVaccineName.getText();
        String price = txtPrice.getText();
        String brand = txtBrand.getText();
        String expiryDate = txtExpiryDate.getText();
        String dateOfIssue = txtDateOfIssue.getText();
        String doctorId = txtDoctorId.getText();
        String paymentId = txtPaymentCode.getText();
        String customerId = txtCustomerId.getText();
        String petId=txtPetId.getText();

       /* VaccinationDetail vaccinationDetail=new VaccinationDetail();
        vaccinationDetail.setVaccinationId(txtVaccinationId.getText());
        vaccinationDetail.setPetId(txtPetId.getText());


        try (Connection con = getConnection(URL, props)) {
            String sql = "INSERT INTO vaccination(VaccinationId, VaccinationName,Price,Barnd,ExipryDate,DateOfIssue,DoctorId)" +
                    "VALUES(?,?,?,?,?,?,?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);
            pstm.setString(2, name);
            pstm.setString(3, price);
            pstm.setString(4, brand);
            pstm.setString(5, expiryDate);
            pstm.setString(6, dateOfIssue);
            pstm.setString(7, doctorId);

            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                new Alert(Alert.AlertType.CONFIRMATION,


                        "huree!! details added :)")
                        .show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        boolean isPlaced = false;
        try {
            isPlaced = VaccinationModel.save(vaccinationDetail);

            //isPlaced = PaymentModel.save(paymentId,customerId,id,price);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Order Not Placed").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error").show();
        }*/
        Vaccination vaccination=new Vaccination(id,name,price,brand,expiryDate,dateOfIssue,doctorId);
        VaccinationDetail vaccinationDetail=new VaccinationDetail(id,petId);
        try {
            boolean isSave= VaccinationModel.saveDetail(vaccination,vaccinationDetail,customerId);

            if(isSave){
                new Alert(Alert.AlertType.CONFIRMATION,"success").show();

            }

        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,"sql error").show();
            throwables.printStackTrace();
        }
    }

        public void btnUpdateOnAction (ActionEvent actionEvent){

            String id = txtVaccinationId.getText();
            String name = txtVaccineName.getText();
            String price = txtPrice.getText();
            String brand = txtBrand.getText();
            String expiryDate= txtExpiryDate.getText();
            String dateOfIssue=txtDateOfIssue.getText();
            String doctorId=txtDoctorId.getText();


            try (Connection con = getConnection(URL, props)) {
                String sql = "UPDATE vaccination SET VaccinatioName = ?, Price  = ?,Brand=?,ExpiryDate=?,DateOfIssue=?,doctorId=? WHERE VaccinationId = ?";
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, name);
                pstm.setString(2, price);
                pstm.setString(3,brand);
                pstm.setString(4,expiryDate);
                pstm.setString(5,dateOfIssue);
                pstm.setString(6,doctorId);
                pstm.setString(7, id);

                boolean isUpdated = pstm.executeUpdate() > 0;
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "yes! updated!!").show();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

        public void btnDeleteOnAction (ActionEvent actionEvent){
            String id = txtVaccinationId.getText();
            try (Connection con = getConnection(URL, props)) {
                String sql = "DELETE FROM vaccination WHERE VaccinationId  = ?";

                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, id);

                if (pstm.executeUpdate() > 0) {
                    new Alert(Alert.AlertType.CONFIRMATION, "deletd!").show();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        public void btnViewOnAction (ActionEvent actionEvent) throws IOException {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/health.fxml"));

            Scene scene = new Scene(anchorPane);

            Stage stage = (Stage) vaccineDetailsPane.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Vaccination Details");
            stage.centerOnScreen();

        }


    }

