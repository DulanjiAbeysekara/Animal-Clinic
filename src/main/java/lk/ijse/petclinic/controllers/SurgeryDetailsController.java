package lk.ijse.petclinic.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.petclinic.db.DBConnection;
import lk.ijse.petclinic.dto.SurgeryDetail;
import lk.ijse.petclinic.model.PaymentModel;
import lk.ijse.petclinic.model.SurgeryModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;

public class SurgeryDetailsController  {
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
    private Label lblDoctorId;

    @FXML
    private Label lblPaymentCode;

    @FXML
    private Label lblPetId;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblSurgeryId;

    @FXML
    private Label lblSurgeryName;

    @FXML
    private Label lblSurgerySchedule;

    @FXML
    private AnchorPane surgeryPane;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtDoctorId;

    @FXML
    private TextField txtPaymentCode;

    @FXML
    private TextField txtPetId;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtReason;

    @FXML
    private TextField txtSurgeryId;

    @FXML
    private TextField txtSurgeryName;

    @FXML
    private TextField txtSurgerySchedule;


    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {


            String id = txtSurgeryId.getText();
            String name = txtSurgeryName.getText();
            String surgerySchedule = txtSurgerySchedule.getText();
            String reason = txtReason.getText();
            String price= txtPrice.getText();
            String doctorId=txtDoctorId.getText();

            Connection con = getConnection(URL, props);
                String sql = "UPDATE Surgery SET SurgeryName = ?,  surgerySchedule = ?,reason=?,price=?,doctorId=? WHERE SurgeryId = ?";
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, name);
                pstm.setString(2,surgerySchedule );
                pstm.setString(3,reason);
                pstm.setString(4,price);
                pstm.setString(5,doctorId);
                pstm.setString(6, id);

                boolean isUpdated = pstm.executeUpdate() > 0;
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "yes! updated!!").show();
                }


    }



    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtSurgeryId.getText();
        try (Connection con = getConnection(URL, props)) {
            String sql = "DELETE FROM surgery WHERE SurgeryId  = ?";

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
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/health.fxml"));

        Scene scene = new Scene(anchorPane);

        Stage stage = (Stage)surgeryPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Health Management");
        stage.centerOnScreen();
    }


    @FXML
    void viewBillOnAction(ActionEvent event) throws SQLException {
            try{
                JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Report/Blank_A4.jrxml");
                JRDesignQuery query = new JRDesignQuery();
                query.setText("SELECT * FROM payment WHERE PaymentCode ='"+txtPaymentCode.getText()+"'");
                jasperDesign.setQuery(query);

                JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null, DBConnection.getInstance().getConnection());
                JasperViewer.viewReport(jasperPrint);

            }catch (JRException e){
                e.printStackTrace();
            }


    }

    public void btnAddOnAction(ActionEvent actionEvent) throws SQLException {
        String id = txtSurgeryId.getText();
        String name = txtSurgeryName.getText();
        String surgerySchedule = txtSurgerySchedule.getText();
        String reason = txtReason.getText();
        String price= txtPrice.getText();
        String doctorId=txtDoctorId.getText();
        String paymentId = txtPaymentCode.getText();
        String customerId = txtCustomerId.getText();

        SurgeryDetail surgeryDetail=new SurgeryDetail();
        surgeryDetail.setSurgeryId(txtSurgeryId.getText());
        surgeryDetail.setPetId(txtPetId.getText());


        Connection con = getConnection(URL, props);
            String sql = "INSERT INTO surgery(SurgeryId, SurgeryName, SurgerySchedule,Reason,Price,DoctorId)" +
                    "VALUES(?,?,?,?,?,?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);
            pstm.setString(2, name);
            pstm.setString(3, surgerySchedule);
            pstm.setString(4,reason);
            pstm.setString(5,price);
            pstm.setString(6, doctorId);

            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                new Alert(Alert.AlertType.CONFIRMATION,


                        "huree!! details added :)")
                        .show();
            }




        boolean isPlaced = false;
        try {
            isPlaced = SurgeryModel.save(surgeryDetail);
            isPlaced = PaymentModel.save(paymentId,customerId,id,price);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Order Not Placed").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error").show();
        }

    }


}
