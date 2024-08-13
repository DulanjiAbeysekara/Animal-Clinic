package lk.ijse.petclinic.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter

public class Doctor {
    private String id;
    private String name;
    private String schedule;
    private int  mobileNum;


}
