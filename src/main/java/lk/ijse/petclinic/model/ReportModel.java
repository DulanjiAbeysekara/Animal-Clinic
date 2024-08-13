package lk.ijse.petclinic.model;


import lk.ijse.petclinic.db.DBConnection;
import lk.ijse.petclinic.dto.tm.Report;
import lk.ijse.petclinic.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportModel {
    public static List<Report> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        List<Report> data = new ArrayList<>();

        String sql = "SELECT * FROM orders ";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            data.add(new Report(

                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
                   // resultSet.getString(4)
            ));
        }
        return data;
    }

    public static Report searchById(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM orders WHERE OrdersId = ?");
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return  new Report(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
                   // resultSet.getInt(4)
            );
        }
        return null;
    }
    public static List<String> loadIds() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT id FROM orders ");

        List<String> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }
}

