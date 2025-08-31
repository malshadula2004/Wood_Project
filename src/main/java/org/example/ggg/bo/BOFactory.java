package org.example.ggg.bo;



public class BOFactory {

    public enum BOTypes { CUSTOMER }

    private static BOFactory boFactory;

    private BOFactory() {}

    public static BOFactory getInstance(BOTypes customer) {
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public SuperBo getBO(BOTypes type) {
        switch (type) {
            case CUSTOMER: return new org.example.ggg.bo.impl.customerBOImpl();
            default: return null;
        }
    }
}