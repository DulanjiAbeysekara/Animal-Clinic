package lk.ijse.petclinic.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.petclinic.dto.tm.Pet;
import lk.ijse.petclinic.dto.tm.PetTM;
import lk.ijse.petclinic.model.PetModel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import static java.sql.DriverManager.getConnection;


public class PetController implements Initializable {
    private static final String URL = "jdbc:mysql://localhost:3306/petClinic";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "12345");
    }

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colAge;

    @FXML
    private TableColumn<?, ?> colBreed;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colDateOfBirth;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TableColumn<?, ?> colPetId;

    @FXML
    private TableColumn<?, ?> colPetName;

    @FXML
    private ComboBox<String> comboGender;

    @FXML
    private AnchorPane petPane;

    @FXML
    private TableView<PetTM> tblPet;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtBreed;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtDateOfBirth;

    @FXML
    private TextField txtPetId;

    @FXML
    private TextField txtPetName;


    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        ObservableList<String>gender=FXCollections.observableArrayList();
        gender.add("male");
        gender.add("female");
        comboGender.setItems(gender);
        setCellValueFactory();
        getAll();
    }
    private void getAll() {
        try {
            ObservableList<PetTM> obList = FXCollections.observableArrayList();
            List<Pet> petList = PetModel.getAll();

            for (Pet pet : petList)
                obList.add(new PetTM(
                        pet.getId(),
                        pet.getName(),
                        pet.getAge(),
                        pet.getBreed(),
                        pet.getDateOfBirth(),
                        pet.getGender(),
                        pet.getCustomerId()
                ));
            tblPet.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
    private void setCellValueFactory() {
        colPetId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPetName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colBreed.setCellValueFactory(new PropertyValueFactory<>("breed"));
        colDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("DateOfBirth"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));

    }
    
    public void btnAddOnAction(ActionEvent actionEvent) {
        String id = txtPetId.getText();
        String name = txtPetName.getText();
        String age =txtAge.getText();
        String breed=txtBreed.getText();
        String dateOfBirth = txtDateOfBirth.getText();
        String customerId = txtCustomerId.getText();
        String gender=comboGender.getValue();



        try (Connection con = getConnection(URL, props)) {
            String sql = "INSERT INTO pet(PetId, PetName,Age, Breed,DateOfBrith,Gender,CustomerId)" +
                    "VALUES(?, ?, ?, ?,?,?,?)";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);
            pstm.setString(2, name);
            pstm.setString(3, age);
            pstm.setString(4, breed);
            pstm.setString(5, dateOfBirth);
            pstm.setString(6, gender);
            pstm.setString(7, customerId);




            txtPetId.setText("");
            txtPetName.setText("");
            txtAge.setText("");
            txtBreed.setText("");
            txtDateOfBirth.setText("");
            txtCustomerId.setText("");
            comboGender.setValue("");

            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                new Alert(Alert.AlertType.CONFIRMATION,


                        "huree!! pet added :)")
                        .show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        getAll();

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtPetId.getText();
        try (Connection con = getConnection(URL, props)) {
            String sql = "DELETE FROM pet WHERE PetId = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);

            if (pstm.executeUpdate() > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "deletd!").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = txtPetId.getText();
        String name = txtPetName.getText();
        String age = txtAge.getText();


        System.out.println("HI....");

        try (Connection con = getConnection(URL, props)) {
            String sql = "UPDATE pet SET PetName = ?, Age = ? WHERE PetId = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,name );
            pstm.setString(2, age);
            pstm.setString(3, id);


            boolean isUpdated = pstm.executeUpdate() > 0;
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "yes! updated!!").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        getAll();

    }


    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/homepage.fxml"));

        Scene scene = new Scene(anchorPane);

        Stage stage = (Stage)petPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Home Page");
        stage.centerOnScreen();

    }

    public void comboGenderOnAction(ActionEvent actionEvent) {
    }
}



