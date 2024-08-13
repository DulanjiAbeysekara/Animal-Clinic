package lk.ijse.petclinic.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class CustomerTM {
    private String id;
    private String name;
    private String address ;
    private  int mobileNum;
    private String appointmentDate;
    private String email;
}
