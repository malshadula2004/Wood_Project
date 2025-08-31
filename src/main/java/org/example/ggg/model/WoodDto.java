package org.example.ggg.model;

public class WoodDto {

    private String woodId;
    private String type;
    private String grade;
    private String dimensions;
    private String unitPrice;

    public WoodDto(){}

    public WoodDto(String woodId, String type, String grade, String dimensions, String unitPrice) {
        this.woodId = woodId;
        this.type = type;
        this.grade = grade;
        this.dimensions = dimensions;
        this.unitPrice = unitPrice;
    }

    public String getWoodId() {
        return woodId;
    }

    public void setWoodId(String woodId) {
        this.woodId = woodId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "Wood{" +
                "woodId='" + woodId + '\'' +
                ", type='" + type + '\'' +
                ", grade='" + grade + '\'' +
                ", dimensions='" + dimensions + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                '}';
    }



}
