package lk.ijse.petclinic.model;

import lk.ijse.petclinic.db.DBConnection;
import lk.ijse.petclinic.dto.Vaccination;
import lk.ijse.petclinic.dto.tm.VaccinationDetail;
import lk.ijse.petclinic.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class VaccinationModel {
    private static final String URL = "jdbc:mysql://localhost:3306/petClinic";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "12345");
    }
    public static List<Vaccination> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        List<Vaccination> data = new ArrayList<>();

        String sql = "SELECT * FROM vaccination";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            data.add(new Vaccination(
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

  /*  public static boolean save(VaccinationDetail vaccinationDetail) {
        try (Connection con = getConnection(URL, props)) {
            String sql = "INSERT INTO vaccinationdeatails(VaccinationId, PetId)" +
                    "VALUES(?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, vaccinationDetail.getVaccinationId());
            pstm.setString(2, vaccinationDetail.getPetId());


            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                new Alert(Alert.AlertType.CONFIRMATION,


                        "huree!! details added :)")
                        .show();
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;

    }*/

    public static boolean saveDetail(Vaccination vaccination, VaccinationDetail vaccinationDetail,String customerId) throws SQLException {
        Connection con= null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            boolean isSave=save(vaccination);

            if(isSave){
                boolean isSaveVaccinationDetail=VaccinationDetailModel.save(vaccinationDetail);

                if(isSaveVaccinationDetail){
                    boolean isUpdate=PaymentModel.setVaccinationId(vaccination,customerId);

                    if(isUpdate){
                        con.commit();
                        return true;
                    }

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

    private static boolean save(Vaccination vaccination) throws SQLException {

        String sql="INSERT INTO vaccination(VaccinationId,VaccinatioName,Price ,Brand,ExpiryDate,DateOfIssue,DoctorId)" +
                "VALUES(?,?,?,?,?,?,?)";

                return CrudUtil.execute(sql,vaccination.getId(),vaccination.getName(),vaccination.getPrice(),vaccination.getBrand(),vaccination.getExpiryDate(),vaccination.getDateOfIssue(),vaccination.getDoctorId());
    }

}
