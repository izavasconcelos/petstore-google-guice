package com.izavasconcelos.cloud.tema02.petstore;

public class BathWaterWithPerfume implements PetStore{

    private static final double WATER_WITH_PRICE = 45;

    @Override
    public String getService() {
        return "Water Bath With Perfume";
    }

    @Override
    public double getPrice() {
        return WATER_WITH_PRICE;
    }

    @Override
    public String toString() {
        return "waterwith";
    }
}
