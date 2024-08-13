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
import lk.ijse.petclinic.dto.tm.Supplier;
import lk.ijse.petclinic.dto.tm.SupplierTM;
import lk.ijse.petclinic.model.SupplierModel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import static java.sql.DriverManager.getConnection;

public class SupplierController implements Initializable {
    private static final String URL = "jdbc:mysql://localhost:3306/petClinic";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "12345");
    }

    @FXML
    private Button BackBtn;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colNameOfTheSupplierCompany;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private TableColumn<?, ?> colSupplierMobileNum;

    @FXML
    private TableColumn<?, ?> colSupplierName;

    @FXML
    private AnchorPane supplierPane;

    @FXML
    private TableView<SupplierTM> tblSupplier;

    @FXML
    private TextField txtNameOfTheSupplierCompany;

    @FXML
    private TextField txtSupplierMobileNum;

    @FXML
    private TextField txtSupplierName;

    @FXML
    private TextField txtSupplierId;


    @FXML
    void BackBtnOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/homepage.fxml"));

        Scene scene = new Scene(anchorPane);

        Stage stage = (Stage)supplierPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Home Page");
        stage.centerOnScreen();

    }
    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
    }

    private void getAll() {
        try {
            ObservableList<SupplierTM> obList = FXCollections.observableArrayList();
            List<Supplier> supplierList = SupplierModel.getAll();

            for (Supplier supplier : supplierList) {
                obList.add(new SupplierTM(
                        supplier.getId(),
                        supplier.getName(),
                        supplier.getSupplierMobileNum(),
                        supplier.getNameOfTheSupplierCompany()
                ));
            }
            tblSupplier.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
    private void setCellValueFactory() {
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSupplierMobileNum.setCellValueFactory(new PropertyValueFactory<>("supplierMobileNum"));
        colNameOfTheSupplierCompany.setCellValueFactory(new PropertyValueFactory<>("nameOfTheSupplierCompany"));
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        String id = txtSupplierId.getText();
        String name = txtSupplierName.getText();
        int supplierMobileNum = 0;
        String nameOfTheSupplierCompany = txtNameOfTheSupplierCompany.getText();


        if (!txtSupplierMobileNum.getText().matches("^(070|072|074|075|076|077|078)[0-9]{7}$")) {
            txtSupplierMobileNum.setStyle("-fx-border-color: red;");
            return;

        }else {
            supplierMobileNum = Integer.parseInt(txtSupplierMobileNum.getText());
            txtSupplierMobileNum.setText("");
        }


        try (Connection con = getConnection(URL, props)) {
            String sql = "INSERT INTO Supplier(SupplierId, SupplierName, SupplierMobileNum, NameOfSupplierCompany)" +
                    "VALUES(?, ?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);
            pstm.setString(2, name);
            pstm.setInt(3, supplierMobileNum);
            pstm.setString(4, nameOfTheSupplierCompany);



            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                new Alert(Alert.AlertType.CONFIRMATION,


                        "huree!! supplier added :)")
                        .show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        getAll();

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

        String id = txtSupplierId.getText();
        String name = txtSupplierName.getText();
        int supplierMobileNum = Integer.parseInt(txtSupplierMobileNum.getText());
        String nameOfTheSupplierCompany = txtNameOfTheSupplierCompany.getText();


        try (Connection con = getConnection(URL, props)) {
            String sql = "UPDATE Supplier SET SupplierName = ?, SupplierMobileNum = ?, NameOfSupplierCompany = ? WHERE SupplierId = ?";

            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, name);
            pstm.setInt(2, supplierMobileNum);
            pstm.setString(3,nameOfTheSupplierCompany );
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
        String id = txtSupplierId.getText();
        try (Connection con = getConnection(URL, props)) {
            String sql = "DELETE FROM Supplier WHERE SupplierId = ?";

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
