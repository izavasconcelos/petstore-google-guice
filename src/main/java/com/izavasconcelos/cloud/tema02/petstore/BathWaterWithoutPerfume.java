package com.izavasconcelos.cloud.tema02.petstore;

public class BathWaterWithoutPerfume implements PetStore{

    private static final double WATER_WITHOUT_PRICE = 25;

    @Override
    public String getService() {
        return "Water Bath Without Perfume";
    }

    @Override
    public double getPrice() {
        return WATER_WITHOUT_PRICE;
    }

    @Override
    public String toString() {
        return "waterwithout";
    }
}
