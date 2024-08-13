package lk.ijse.petclinic.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Sugery {
    private String id;
    private String name;
    private String schedule;
    private String reason;
    private String price;
    private String doctorId;
}
