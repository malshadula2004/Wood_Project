package org.example.ggg.dto;

public class InventoryDto {
    private String itemId;
    private String supplierOrderId;
    private String quantityAvailable;

    public InventoryDto() {}

    public InventoryDto(String itemId, String supplierOrderId, String quantityAvailable) {
        this.itemId = itemId;
        this.supplierOrderId = supplierOrderId;
        this.quantityAvailable = quantityAvailable;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getSupplierOrderId() {
        return supplierOrderId;
    }

    public void setSupplierOrderId(String supplierOrderId) {
        this.supplierOrderId = supplierOrderId;
    }

    public String getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(String quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    @Override
    public String toString() {
        return "InventoryDto{" +
                "itemId='" + itemId + '\'' +
                ", supplierOrderId='" + supplierOrderId + '\'' +
                ", quantityAvailable='" + quantityAvailable + '\'' +
                '}';
    }
}
