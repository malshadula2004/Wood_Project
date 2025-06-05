package org.example.ggg.dto;

public class WoodOrderDTO {
    private String id;
    private String woodId;
    private String woodLength;
    private String total;
    private String orderDate;

    public WoodOrderDTO() {}

    public WoodOrderDTO(String id, String woodId, String woodLength, String total, String orderDate) {
        this.id = id;
        this.woodId = woodId;
        this.woodLength = woodLength;
        this.total = total;
        this.orderDate = orderDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWoodId() {
        return woodId;
    }

    public void setWoodId(String woodId) {
        this.woodId = woodId;
    }

    public String getWoodLength() {
        return woodLength;
    }

    public void setWoodLength(String woodLength) {
        this.woodLength = woodLength;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "WoodOrderDTO{" +
                "id='" + id + '\'' +
                ", woodId='" + woodId + '\'' +
                ", woodLength='" + woodLength + '\'' +
                ", total='" + total + '\'' +
                ", orderDate='" + orderDate + '\'' +
                '}';
    }
}
