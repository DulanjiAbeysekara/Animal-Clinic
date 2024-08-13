package lk.ijse.petclinic.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString


public class PetItemTM {
    private String id;
    private String name;
    private String brand;
    private String datOfIssue;
    private String expiryDate;
    private String sellingPrice;
    private String purchasePrice;
    private String qty;
}
