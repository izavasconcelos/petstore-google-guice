package com.izavasconcelos.cloud.tema02.petstore;

public class HairCutLong implements PetStore {

    private static final double HAIRCUT_LONG_PRICE = 25;

    @Override
    public String getService() {
        return "Long Hair Cut";
    }

    @Override
    public double getPrice() {
        return HAIRCUT_LONG_PRICE;
    }

    @Override
    public String toString() {
        return "haircutlong";
    }
}
