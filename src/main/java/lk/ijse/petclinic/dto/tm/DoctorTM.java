package lk.ijse.petclinic.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString


public class DoctorTM {
    private String id;
    private String name;
    private String schedule;
    private int  mobileNum;

}
