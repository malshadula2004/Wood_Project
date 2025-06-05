package org.example.ggg.TM;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class woodTM {
    private String woodId;
    private String name;
    private String quantity;
    private String price;
    private String total;
    private Button removeButton;
}
