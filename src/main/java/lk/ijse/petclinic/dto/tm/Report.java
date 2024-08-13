package lk.ijse.petclinic.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter

public class Report {
    private String orderId;
    private String orderDate;
    private String customerId;
}
