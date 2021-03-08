package com.izavasconcelos.cloud.tema02.petstore;

public class BathDryWithPerfume implements PetStore{

    private static final double DRY_WITH_PRICE = 50;

    @Override
    public String getService() {
        return "Dry Bath With Perfume";
    }

    @Override
    public double getPrice() {
        return DRY_WITH_PRICE;
    }

    @Override
    public String toString() {
        return "drywith";
    }
}
