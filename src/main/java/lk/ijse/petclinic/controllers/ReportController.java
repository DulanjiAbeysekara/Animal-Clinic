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
import lk.ijse.petclinic.dto.tm.Report;
import lk.ijse.petclinic.dto.tm.ReportTM;
import lk.ijse.petclinic.model.ReportModel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import static java.sql.DriverManager.getConnection;

public class ReportController implements Initializable {
    private static final String URL = "jdbc:mysql://localhost:3306/petClinic";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "12345");
    }

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colOrderDate;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private AnchorPane orderPane;

    @FXML
    private TableView<ReportTM> tblOrder;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtOrderDate;

    @FXML
    private TextField txtOrderId;


    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
    }

    private void getAll() {
        try {
            ObservableList<ReportTM> obList = FXCollections.observableArrayList();
            List<Report> reportList = ReportModel.getAll();

            for (Report report : reportList) {
                obList.add(new ReportTM(
                        report.getOrderId(),
                        report.getOrderDate(),
                        report.getCustomerId()
                       // report.getItemId()
                ));
            }
            tblOrder.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
    private void setCellValueFactory() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
      //  colItemId.setCellValueFactory(new PropertyValueFactory<>("nameOfTheSupplierCompany"));
    }


    @FXML
    void btnAddOnAction(ActionEvent event) {
        String orderId = txtOrderId.getText();
        String orderDate = txtOrderDate.getText();
        String customerId=txtCustomerId.getText();

        try (Connection con = getConnection(URL, props)) {
            String sql = "INSERT INTO orders(OrdersId,orderDate , CustomerId)" +
                    "VALUES(?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, orderId);
            pstm.setString(2, orderDate);
            pstm.setString(3,customerId);

            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                new Alert(Alert.AlertType.CONFIRMATION,


                        "huree!! order added :)")
                        .show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        getAll();



    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws  IOException {

        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/homepage.fxml"));

        Scene scene = new Scene(anchorPane);

        Stage stage = (Stage)orderPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Home Page");
        stage.centerOnScreen();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtOrderId.getText();
        try (Connection con = getConnection(URL, props)) {
            String sql = "DELETE FROM orders WHERE OrdersId = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);

            if (pstm.executeUpdate() > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "deletd!").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtOrderId.getText();
        String orderDate = txtOrderDate.getText();
        String customerId =txtCustomerId.getText();



        try (Connection con = getConnection(URL, props)) {
            String sql = "UPDATE orders  SET orderDate = ?, CustomerId = ? WHERE OrdersId  = ?";

            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, orderDate);
            pstm.setString(2, customerId);
            pstm.setString(3,id );


            boolean isUpdated = pstm.executeUpdate() > 0;
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "yes! updated!!").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        getAll();


    }

}
