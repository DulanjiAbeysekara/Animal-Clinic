package lk.ijse.petclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CustomerDto {
    private String id;
    private String name;
    private String address ;
    private  int mobileNum;
    private String appointmentDate;
    private String email;
}


