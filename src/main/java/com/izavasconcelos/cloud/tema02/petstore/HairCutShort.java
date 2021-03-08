package com.izavasconcelos.cloud.tema02.petstore;

public class HairCutShort implements PetStore{
    private static final double HAIRCUT_SHORT_PRICE = 15;

    @Override
    public String getService() {
        return "Short Hair Cut";
    }

    @Override
    public double getPrice() {
        return HAIRCUT_SHORT_PRICE;
    }

    @Override
    public String toString() {
        return "haircutshort";
    }
}
