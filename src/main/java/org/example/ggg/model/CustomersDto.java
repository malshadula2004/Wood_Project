package org.example.ggg.model;

public class CustomersDto {
    private String customerId;
    private String name;
    private String contactInfo; // Corrected spelling
    private String address;
    private String email;
    private String paymentMethod;

    public CustomersDto() {
    }

    public CustomersDto(String customerId, String name, String contactInfo, String address, String email, String paymentMethod) {
        this.customerId = customerId;
        this.name = name;
        this.contactInfo = contactInfo;
        this.address = address;
        this.email = email;
        this.paymentMethod = paymentMethod;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() { // Corrected spelling
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) { // Corrected spelling
        this.contactInfo = contactInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "CustomersDto{" +
                "customerId='" + customerId + '\'' +
                ", name='" + name + '\'' +
                ", contactInfo='" + contactInfo + '\'' + // Corrected spelling
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}
