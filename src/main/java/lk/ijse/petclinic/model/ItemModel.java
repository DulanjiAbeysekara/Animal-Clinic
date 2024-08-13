package lk.ijse.petclinic.model;

import lk.ijse.petclinic.db.DBConnection;
import lk.ijse.petclinic.dto.tm.Item;
import lk.ijse.petclinic.dto.tm.ItemDetails;
import lk.ijse.petclinic.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ItemModel {

    private static final String URL = "jdbc:mysql://localhost:3306/petClinic";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "12345");
    }

    public static List<Item> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        List<Item> data = new ArrayList<>();

        String sql = "SELECT * FROM item";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            data.add(new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8)
            ));
        }
        return data;


    }

    public static boolean saveDetail(Item item, ItemDetails itemDetails) throws SQLException {
        Connection con= null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            boolean isSave=save(item);



                if(isSave){
                    boolean isUpdate=ItemModel.setItemId(item,itemDetails.getSupplierId());

                    if(isUpdate){
                        con.commit();
                        return true;
                    }

                }


        } catch (SQLException throwables) {
            con.rollback();

            throwables.printStackTrace();

        }
        finally {
            con.setAutoCommit(true);
        }
        return  false;

    }

    private static boolean setItemId( Item item,String supplierId) throws SQLException {
        String sql="INSERT INTO ItemDetails(ItemId,SupplierId)" +
                "VALUES(?,?)";



        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1, item.getId());
        pstm.setString(2, supplierId);




        boolean isUpdated = pstm.executeUpdate() > 0;

       return isUpdated;
    }

    private static boolean save(Item item) throws SQLException {
        String sql="INSERT INTO Item(ItemId,ItemName,BrandName ,DateOfIssue,ExpiryDate,PurchasePrice,SellingPrice,Qty)" +
                "VALUES(?,?,?,?,?,?,?,?)";

        return CrudUtil.execute(sql,item.getId(),item.getName(),item.getBrand(),item.getDatOfIssue(),item.getExpiryDate(),item.getPurchasePrice(),item.getSellingPrice(),item.getQty());
    }


}
