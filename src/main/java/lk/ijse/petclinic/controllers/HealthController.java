package lk.ijse.petclinic.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.petclinic.dto.Sugery;
import lk.ijse.petclinic.dto.Vaccination;
import lk.ijse.petclinic.dto.tm.SurgeryTM;
import lk.ijse.petclinic.dto.tm.VaccinationTM;
import lk.ijse.petclinic.model.SurgeryModel;
import lk.ijse.petclinic.model.VaccinationModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class HealthController implements Initializable {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnViewSurgery;

    @FXML
    private Button btnViewVaccination;

    @FXML
    private TableColumn<?, ?> colBrand;

    @FXML
    private TableColumn<?, ?> colDateOfIssue;

    @FXML
    private TableColumn<?, ?> colDoctorId;

    @FXML
    private TableColumn<?, ?> colExpiryDate;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colReason;

    @FXML
    private TableColumn<?, ?> colSurgeryId;

    @FXML
    private TableColumn<?, ?> colSurgeryName;

    @FXML
    private TableColumn<?, ?> colSurgerySchedule;

    @FXML
    private TableColumn<?, ?> colVaccinationId;

    @FXML
    private TableColumn<?, ?> colVaccinationName;

    @FXML
    private TableColumn<?, ?> colVaccinationNum;

    @FXML
    private TableColumn<?, ?> colVaccinationStore;

    @FXML
    private AnchorPane healthPane;

    @FXML
    private TableView<SurgeryTM> tblSurgery;

    @FXML
    private TableView<VaccinationTM> tblVaccination;

    @FXML
    //private TableView<?> tblVaccination;


    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/homepage.fxml"));

        Scene scene = new Scene(anchorPane);

        Stage stage = (Stage)healthPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Home Page");
        stage.centerOnScreen();

    }


    private void getAll() {
        try {
            ObservableList<SurgeryTM> obList = FXCollections.observableArrayList();
            List<Sugery> surgeryList = SurgeryModel.getAll();

            for (Sugery sugery : surgeryList) {
                obList.add(new SurgeryTM(
                        sugery.getId(),
                        sugery.getName(),
                        sugery.getSchedule(),
                        sugery.getReason(),
                        sugery.getPrice(),
                        sugery.getDoctorId()
                ));
            }
            tblSurgery.setItems(obList);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

        //////////////////////////////////////////////////////////////////////
        try {
            ObservableList<VaccinationTM> obList = FXCollections.observableArrayList();
            List<Vaccination> vaccinationList = VaccinationModel.getAll();

            for (Vaccination vaccination : vaccinationList) {
                obList.add(new VaccinationTM(
                        vaccination.getId(),
                        vaccination.getName(),
                        vaccination.getPrice(),
                        vaccination.getBrand(),
                        vaccination.getExpiryDate(),
                        vaccination.getDateOfIssue(),
                        vaccination.getDoctorId()
                ));
            }
            tblVaccination.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
    }

   private void setCellValueFactory() {

       colSurgeryId.setCellValueFactory(new PropertyValueFactory<>("id"));
       colSurgeryName.setCellValueFactory(new PropertyValueFactory<>("name"));
       colSurgerySchedule.setCellValueFactory(new PropertyValueFactory<>("schedule"));
       colReason.setCellValueFactory(new PropertyValueFactory<>("reason"));
       colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
       colDoctorId .setCellValueFactory(new PropertyValueFactory<>("doctorId"));

        colVaccinationId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colVaccinationName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colExpiryDate.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
        colDateOfIssue .setCellValueFactory(new PropertyValueFactory<>("dateOfIssue"));
        colDoctorId.setCellValueFactory(new PropertyValueFactory<>("doctorId"));


    }



    public void btnViewSurgeryOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/surgeryDetails.fxml"));

        Scene scene = new Scene(anchorPane);

        Stage stage = (Stage)healthPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Health Management");
        stage.centerOnScreen();
    }

    public void btnViewVaccinationOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/vaccinationDetails.fxml"));

        Scene scene = new Scene(anchorPane);

        Stage stage = (Stage)healthPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Health Management");
        stage.centerOnScreen();
    }
}
