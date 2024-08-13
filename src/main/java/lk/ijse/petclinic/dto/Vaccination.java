package lk.ijse.petclinic.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString


public class Vaccination {
    private String id;
    private String name;
    private String price;
    private String brand;
    private String expiryDate;
    private String dateOfIssue;
    private String doctorId;

    public Vaccination(String id, String name, String price, String brand, String expiryDate, String dateOfIssue, String doctorId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.expiryDate = expiryDate;
        this.dateOfIssue = dateOfIssue;
        this.doctorId = doctorId;
    }
}
