package lk.ijse.petclinic.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString


public class PetTM {
    private String id;
    private String name;
    private  String  age;
    private  String breed;
    private  String dateOfBirth;
    private  String  gender;
    private String customerId;
}
