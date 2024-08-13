package lk.ijse.petclinic.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter

public class Supplier {
    private String id;
    private String name;
    private int supplierMobileNum;
    private String  nameOfTheSupplierCompany;
}
