package org.example.ggg.model;

public class SuppliersDto {

    private String supplierId;
    private String name;
    private String contactInfo;
    private String address;
    private String suppliedMaterials; // Fixed field name

    public SuppliersDto() {}

    public SuppliersDto(String supplierId, String name, String contactInfo, String address, String suppliedMaterials) {
        this.supplierId = supplierId;
        this.name = name;
        this.contactInfo = contactInfo;
        this.address = address;
        this.suppliedMaterials = suppliedMaterials; // Fixed usage
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSuppliedMaterials() { // Fixed method name
        return suppliedMaterials;
    }

    public void setSuppliedMaterials(String suppliedMaterials) { // Fixed method name
        this.suppliedMaterials = suppliedMaterials;
    }

    @Override
    public String toString() {
        return "SuppliersDto{" +
                "supplierId='" + supplierId + '\'' +
                ", name='" + name + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", address='" + address + '\'' +
                ", suppliedMaterials='" + suppliedMaterials + '\'' +
                '}';
    }
}
