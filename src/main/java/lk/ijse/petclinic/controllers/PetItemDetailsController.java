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
import lk.ijse.petclinic.dto.tm.Item;
import lk.ijse.petclinic.dto.tm.ItemDetails;
import lk.ijse.petclinic.model.ItemModel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;

public class PetItemDetailsController {

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
    private AnchorPane petItemDetailsPane;

    @FXML
    private TextField txtBrandName;

    @FXML
    private TextField txtDateOfIssue;

    @FXML
    private TextField txtExpiryDate;

    @FXML
    private TextField txtItemId;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtPurchasePrice;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtSellingPrice;

    @FXML
    private TextField txtSupplierId;



    public void btnAddOnAction(ActionEvent actionEvent) {

        String id=txtItemId.getText();
        String name=txtItemName.getText();
        String brand=txtBrandName.getText();
        String datOfIssue=txtDateOfIssue.getText();
        String expiryDate=txtExpiryDate.getText();
        String sellingPrice=txtSellingPrice.getText();
        String purchasePrice=txtPurchasePrice.getText();
        String qty=txtQty.getText();
        String supplierId=txtSupplierId.getText();

        Item item=new Item(id,name,brand,datOfIssue,expiryDate,sellingPrice,purchasePrice,qty);
        ItemDetails itemDetails=new ItemDetails(id,supplierId);

        try {
            boolean isSave= ItemModel.saveDetail(item,itemDetails);

            if(isSave){
                new Alert(Alert.AlertType.CONFIRMATION,"success").show();

            }

        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,"sql error").show();
            throwables.printStackTrace();
        }

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = txtItemId.getText();
        String name = txtItemName.getText();
        String brand = txtBrandName.getText();
        String dateOfIssue= txtDateOfIssue.getText();
        String expiryDate=txtExpiryDate.getText();
        String purchasePrice=txtPurchasePrice.getText();
        String sellingPrice=txtSellingPrice.getText();
        String supplierId=txtSupplierId.getText();


        try (Connection con = getConnection(URL, props)) {
            String sql = "UPDATE item SET ItemName = ?,BrandName = ?,DateOfIssue=?,ExpiryDate=?,PurchasePrice=?,SellingPrice=?,Qty=? WHERE  =ItemId ?";
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, name);
            pstm.setString(2, brand);
            pstm.setString(3,dateOfIssue );
            pstm.setString(4, expiryDate);
            pstm.setString(5,purchasePrice);
            pstm.setString(6,sellingPrice);
            pstm.setString(7,supplierId);

            boolean isUpdated = pstm.executeUpdate() > 0;
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "yes! updated!!").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtItemId.getText();
        try (Connection con = getConnection(URL, props)) {
            String sql = "DELETE FROM item WHERE ItemId = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);

            if (pstm.executeUpdate() > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "deletd!").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }


    public void btnViewOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/petItem.fxml"));

        Scene scene = new Scene(anchorPane);

        Stage stage = (Stage)petItemDetailsPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Pet Item Details");
        stage.centerOnScreen();



    }
}
