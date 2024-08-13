package lk.ijse.petclinic.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SupplierDto {
    private String id;
    private String name;
    private int supplierMobileNum;
    private String  nameOfTheSupplierCompany;

}
