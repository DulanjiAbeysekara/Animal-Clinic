package lk.ijse.petclinic.dto.tm;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString


public class Item {
     private String id;
     private String name;
     private String brand;
     private String datOfIssue;
     private String expiryDate;
     private String sellingPrice;
    private String purchasePrice;
     private String qty;

    public Item(String id, String name, String brand, String datOfIssue, String expiryDate, String sellingPrice, String purchasePrice, String qty) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.datOfIssue = datOfIssue;
        this.expiryDate = expiryDate;
        this.sellingPrice = sellingPrice;
        this.purchasePrice = purchasePrice;
        this.qty = qty;
    }
}
