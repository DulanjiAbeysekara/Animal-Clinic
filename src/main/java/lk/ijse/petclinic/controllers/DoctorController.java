package lk.ijse.petclinic.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.petclinic.dto.tm.Doctor;
import lk.ijse.petclinic.dto.tm.DoctorTM;
import lk.ijse.petclinic.model.DoctorModel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import static java.sql.DriverManager.getConnection;

public class DoctorController implements Initializable {
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
    private TableColumn<?, ?> colDoctorId;

    @FXML
    private TableColumn<?, ?> colDoctorMobileNum;

    @FXML
    private TableColumn<?, ?> colDoctorName;

    @FXML
    private TableColumn<?, ?> colSchedule;

    @FXML
    private Button doctorBackBtn;

    @FXML
    private AnchorPane doctorPane;

    @FXML
    private TableView<DoctorTM> tblDoctor;

    @FXML
    private TextField txtDoctorId;

    @FXML
    private TextField txtDoctorMobileNum;

    @FXML
    private TextField txtDoctorName;

    @FXML
    private TextField txtSchedule;


    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
    }
    private void getAll() {
        try {
            ObservableList<DoctorTM> obList = FXCollections.observableArrayList();
            List<Doctor> docList = DoctorModel.getAll();

            for (Doctor doctor : docList) {
                obList.add(new DoctorTM(
                        doctor.getId(),
                        doctor.getName(),
                        doctor.getSchedule(),
                       doctor.getMobileNum()
                ));
            }
            tblDoctor.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void setCellValueFactory() {
        colDoctorId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDoctorName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSchedule.setCellValueFactory(new PropertyValueFactory<>("schedule"));
        colDoctorMobileNum.setCellValueFactory(new PropertyValueFactory<>("mobileNum"));
    }



    @FXML
    void doctorBackBtnOnAction(ActionEvent event) throws IOException {

        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/homepage.fxml"));

        Scene scene = new Scene(anchorPane);

        Stage stage = (Stage)doctorPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Home Page");
        stage.centerOnScreen();

    }

    public void btnAddOnAction(ActionEvent actionEvent) {

        String id = txtDoctorId.getText();
        String name = txtDoctorName.getText();
        String schedule = txtSchedule.getText();
        int mobileNum = 0;

        if (!txtDoctorMobileNum.getText().matches("^(070|072|074|075|076|077|078)[0-9]{7}$")) {
            txtDoctorMobileNum.setStyle("-fx-border-color: red;");
            return;

        }else {
            mobileNum = Integer.parseInt(txtDoctorMobileNum.getText());
            txtDoctorMobileNum.setText("");
        }

        txtDoctorId.setText("");
        txtDoctorName.setText("");
        txtSchedule.setText("");

        try (Connection con = getConnection(URL, props)) {
            String sql = "INSERT INTO Doctor(DoctorId, DoctorName, Schedule,MobileNum)" +
                    "VALUES(?, ?, ?,?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);
            pstm.setString(2, name);
            pstm.setString(3, schedule);
            pstm.setInt(4, mobileNum);



            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                new Alert(Alert.AlertType.CONFIRMATION,


                        "huree!! Doctor added :)")
                        .show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        getAll();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = txtDoctorId.getText();
        String name = txtDoctorName.getText();
        String schedule = txtSchedule.getText();
        int mobileNum = Integer.parseInt(txtDoctorMobileNum.getText());


        try (Connection con = getConnection(URL, props)) {
            String sql = "UPDATE Doctor SET DoctorName = ?, Schedule = ?,MobileNum=? WHERE DoctorId = ?";
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, name);
            pstm.setString(2, schedule);
            pstm.setInt(3,mobileNum );
            pstm.setString(4, id);

            boolean isUpdated = pstm.executeUpdate() > 0;
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "yes! updated!!").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        getAll();

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtDoctorId.getText();
        try (Connection con = getConnection(URL, props)) {
            String sql = "DELETE FROM Doctor WHERE DoctorId = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);

            if (pstm.executeUpdate() > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "deletd!").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
