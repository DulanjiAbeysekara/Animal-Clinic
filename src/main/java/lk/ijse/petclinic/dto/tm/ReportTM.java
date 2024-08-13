package lk.ijse.petclinic.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter

public class ReportTM {

    private String orderId;
    private String orderDate;
    private String customerId;
}
