package lk.ijse.petclinic.model;

import lk.ijse.petclinic.dto.tm.VaccinationDetail;
import lk.ijse.petclinic.util.CrudUtil;

import java.sql.SQLException;

public class VaccinationDetailModel {
    public static boolean save(VaccinationDetail vaccinationDetail) throws SQLException {
        String sql="INSERT INTO vaccinationDetails(VaccinationId,PetId)" +
                "VALUES(?,?)";

        return CrudUtil.execute(sql,vaccinationDetail.getVaccinationId(),vaccinationDetail.getPetId());
    }
}
