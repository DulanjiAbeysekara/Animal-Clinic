package lk.ijse.petclinic.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class PetDto {
    private String id;
    private String name;
    private  String  age;
    private  String breed;
    private  String dateOfBirth;
    private  String  gender;
    private String customerId;
}
