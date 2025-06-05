package org.example.ggg.TM;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartTM {
    private String itemId;
    private String itemName;
    private String unitPrice;
    private String qty;
    private String total;
    private Button btnRemove;
}
