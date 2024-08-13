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
import lk.ijse.petclinic.dto.tm.Customer;
import lk.ijse.petclinic.dto.tm.CustomerTM;
import lk.ijse.petclinic.model.CustomerModel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.CollationElementIterator;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import static java.sql.DriverManager.getConnection;

public class CustomerController implements Initializable {
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
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colCustomerName;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colMobileNum;

    @FXML
    private TableColumn<?, ?> coltAppointmentDate;

    @FXML
    private AnchorPane customerPane;

    @FXML
    private TableView<CustomerTM> tblCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtMobileNum;
    private CollationElementIterator Print;


    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
    }

    private void getAll() {
        try {
            ObservableList<CustomerTM> obList = FXCollections.observableArrayList();
            List<Customer> cusList = CustomerModel.getAll();

            for (Customer customer : cusList) {
                obList.add(new CustomerTM(
                        customer.getId(),
                        customer.getName(),
                        customer.getAddress(),
                        customer.getMobileNum(),
                        customer.getAppointmentDate(),
                        customer.getEmail()
                ));
            }
            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();

        }
    }

    private void setCellValueFactory() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colMobileNum.setCellValueFactory(new PropertyValueFactory<>("mobileNum"));
        coltAppointmentDate.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }


    @FXML
    void customerIdOnAction(ActionEvent event) {

    }




    public void btnAddOnAction(ActionEvent actionEvent) {

        String id = txtCustomerId.getText();
        String name = txtCustomerName.getText();
        String address  = txtAddress.getText();
        String appointmentDate = txtDate.getText();
        int mobileNum = 0;
        String email ;


        //String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

       // Pattern pattern = Pattern.compile(regex);
       // Matcher matcher = pattern.matcher(txtEmail.getText());

       /* if (matcher.matches()) {

        } else {
            txtEmail.setStyle("-fx-border-color: red;");

        }*/

        /*String regex1="^(070|072|074|075|076|077|078)[0-9]{7}$";

        Pattern pattern1 = Pattern.compile(regex1);
        Matcher matcher1 = pattern1.matcher(txtMobileNum.getText());

        
        if (matcher1.matches()){

        }else {
            txtMobileNum.setStyle("-fx-border-color: red;");
             mobileNum=Integer.parseInt(txtMobileNum.getText());
        }*/
        if (!txtEmail.getText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            txtEmail.setStyle("-fx-border-color: red;");
            return;

        }else {
            email = (txtEmail.getText());
            txtEmail.setText("");
        }

        ////////////////////////////////////////////////////////////////
        if (!txtMobileNum.getText().matches("^(070|072|074|075|076|077|078)[0-9]{7}$")) {
            txtMobileNum.setStyle("-fx-border-color: red;");
            return;

        }else {
            mobileNum = Integer.parseInt(txtMobileNum.getText());
            txtMobileNum.setText("");
        }

        txtCustomerId.setText("");
        txtCustomerName.setText("");
        txtAddress.setText("");
        txtDate.setText("");

        try (Connection con = getConnection(URL, props)) {
            String sql = "INSERT INTO Customer(CustomerId, CustomerName, Address, MobileNumber,AppoinmentDate,Email)" +
                    "VALUES(?, ?, ?, ?,?,?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);
            pstm.setString(2, name);
            pstm.setString(3, address);
            pstm.setInt(4, mobileNum);
            pstm.setString(5, appointmentDate);
            pstm.setString(6,email);


            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                new Alert(Alert.AlertType.CONFIRMATION,


                        "huree!! customer added :)")
                        .show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        getAll();


    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = txtCustomerId.getText();
        String name = txtCustomerName.getText();
        String address = txtAddress.getText();
        int mobileNum = Integer.parseInt(txtMobileNum.getText());
        String appointmentDate=txtDate.getText();
        String email = txtEmail.getText();


        try (Connection con = getConnection(URL, props)) {
            String sql = "UPDATE Customer SET CustomerName = ?, Address = ?, MobileNumber = ?,AppoinmentDate=?,Email=? WHERE CustomerId = ?";
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, name);
            pstm.setString(2, address);
            pstm.setInt(3, mobileNum);
            pstm.setString(4, appointmentDate);
            pstm.setString(5,email);
            pstm.setString(6, id);


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
        String id = txtCustomerId.getText();
        try (Connection con = getConnection(URL, props)) {
            String sql = "DELETE FROM Customer WHERE CustomerId = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);

            if (pstm.executeUpdate() > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "deletd!").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/homepage.fxml"));

        Scene scene = new Scene(anchorPane);

        Stage stage = (Stage)customerPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Home Page");
        stage.centerOnScreen();
    }
}
