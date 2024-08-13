package lk.ijse.petclinic.model;

import javafx.scene.control.Alert;
import lk.ijse.petclinic.db.DBConnection;
import lk.ijse.petclinic.dto.Sugery;
import lk.ijse.petclinic.dto.SurgeryDetail;
import lk.ijse.petclinic.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;

public class SurgeryModel {
    private static final String URL = "jdbc:mysql://localhost:3306/petClinic";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "12345");
    }
    public static List<Sugery> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        List<Sugery> data = new ArrayList<>();

        String sql = "SELECT * FROM surgery";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            data.add(new Sugery(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));
        }
        return data;


    }

    public static boolean save(SurgeryDetail surgeryDetail) throws SQLException {
        Connection con = getConnection(URL, props);
            String sql = "INSERT INTO surgerydetails(SurgeryId, PetId)" +
                    "VALUES(?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, surgeryDetail.getSurgeryId());
            pstm.setString(2, surgeryDetail.getPetId());


            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                new Alert(Alert.AlertType.CONFIRMATION,


                        "huree!! details added :)")
                        .show();
                return true;
            }

        return false;

    }

}

