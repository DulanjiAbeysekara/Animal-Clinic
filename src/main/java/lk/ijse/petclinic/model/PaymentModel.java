package lk.ijse.petclinic.model;

import javafx.scene.control.Alert;
import lk.ijse.petclinic.db.DBConnection;
import lk.ijse.petclinic.dto.Vaccination;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentModel {

    public static boolean save(String paymentId, String customerId, String id, String price) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO payment(PaymentCode, CustomerId,SurgeryId,Price)" +
                    "VALUES(?,?,?,?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,paymentId);
            pstm.setString(2,customerId);
            pstm.setString(3,id);
            pstm.setString(4,price);
           // pstm.setString(5,vaccinationId);


            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                new Alert(Alert.AlertType.CONFIRMATION,


                        "huree!! payment added :)")
                        .show();
                return true;
            }

        return false;
    }

    public static boolean setVaccinationId(Vaccination vaccination,String customerId) throws SQLException {
        PreparedStatement pstm = DBConnection.getInstance().getConnection()
                .prepareStatement("UPDATE payment SET VaccinationId=? WHERE CustomerId = ?");

        pstm.setString(1, vaccination.getId());
        pstm.setString(2,customerId);

        return pstm.executeUpdate()>0;
    }
}
