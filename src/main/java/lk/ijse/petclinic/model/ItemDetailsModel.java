package lk.ijse.petclinic.model;

import lk.ijse.petclinic.dto.tm.ItemDetails;
import lk.ijse.petclinic.util.CrudUtil;

import java.sql.SQLException;

public class ItemDetailsModel {

    public static boolean save(ItemDetails itemDetails) throws SQLException {
        String sql="INSERT INTO ItemDetails(ItemId,SupplierId)" +
                "VALUES(?,?)";

        return CrudUtil.execute(sql,itemDetails.getItemId(),itemDetails.getSupplierId());
        }
}