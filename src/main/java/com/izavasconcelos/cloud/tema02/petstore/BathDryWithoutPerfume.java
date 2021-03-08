package com.izavasconcelos.cloud.tema02.petstore;


public class BathDryWithoutPerfume implements PetStore {

    private static final double DRY_WITHOUT_PRICE = 35;

    @Override
    public String getService() {
        return "Dry Bath Without Perfume";
    }

    @Override
    public double getPrice() {
        return DRY_WITHOUT_PRICE;
    }

    @Override
    public String toString() {
        return "drywithout";
    }
}
