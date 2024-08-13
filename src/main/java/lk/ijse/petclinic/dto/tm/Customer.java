package lk.ijse.petclinic.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter

public class Customer {
    private String id;
    private String name;
    private String address ;
    private  int mobileNum;
    private String appointmentDate;
    private String email;


}


