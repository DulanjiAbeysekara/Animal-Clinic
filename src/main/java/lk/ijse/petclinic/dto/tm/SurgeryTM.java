package lk.ijse.petclinic.dto.tm;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString


public class SurgeryTM  {
    private String id;
    private String name;
    private String schedule;
    private String reason;
    private String price;
    private String doctorId;
}
