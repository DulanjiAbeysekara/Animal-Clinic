package lk.ijse.petclinic.model;

import lk.ijse.petclinic.db.DBConnection;
import lk.ijse.petclinic.dto.tm.Pet;
import lk.ijse.petclinic.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PetModel {
    public static List<Pet> getAll() throws SQLException {
            Connection con = DBConnection.getInstance().getConnection();
            List<Pet> data = new ArrayList<>();

            String sql = "SELECT * FROM Pet";
            ResultSet resultSet = CrudUtil.execute(sql);

            while (resultSet.next()) {
                data.add(new Pet(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                       resultSet.getString(6),
                        resultSet.getString(7)
                ));
            }
            return data;

        }
    public static Pet searchById(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM pet WHERE PetId = ?");
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return  new Pet(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)

            );
        }
        return null;
    }
    public static List<String> loadIds() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT PetId FROM pet");

        List<String> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }


    }















