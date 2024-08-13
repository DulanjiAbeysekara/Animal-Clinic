package lk.ijse.petclinic.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class VaccinationTM {
    private String id;
    private String name;
    private String price;
    private String brand;
    private String expiryDate;
    private String dateOfIssue;
    private String doctorId;
}
