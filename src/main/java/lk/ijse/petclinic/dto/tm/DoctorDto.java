package lk.ijse.petclinic.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class DoctorDto {
    private String id;
    private String name;
    private String schedule;
    private int  mobileNum;

}
