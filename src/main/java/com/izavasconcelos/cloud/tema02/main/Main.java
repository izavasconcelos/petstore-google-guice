package com.izavasconcelos.cloud.tema02.main;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.izavasconcelos.cloud.tema02.model.Pet;
import com.izavasconcelos.cloud.tema02.module.PetStoreModule;
import com.izavasconcelos.cloud.tema02.petstore.ServiceType;
import com.izavasconcelos.cloud.tema02.service.PetStoreService;

public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new PetStoreModule());
        PetStoreService service = injector.getInstance(PetStoreService.class);
        Pet pet = new Pet("Duque", "Cat", 10);
        service.addPet(pet);
        Pet pet2 = new Pet("Frajola", "Cat", 2);
        service.addPet(pet2);
        Pet pet3 = new Pet("Frida", "Cat", 2);
        service.addPet(pet3);

        service.bath(pet2, ServiceType.DRY_BATH_WITH_PERFUME);
        service.bath(pet3, ServiceType.DRY_BATH_WITHOUT_PERFUME);
        service.bath(pet, ServiceType.WATER_BATH_WITH_PERFUME);
        service.bath(pet3, ServiceType.WATER_BATH_WITHOUT_PERFUME);

        service.bath(pet2, ServiceType.WATER_BATH_WITH_PERFUME);
        service.hairCut(pet2, ServiceType.LONG_HAIRCUT);
        service.hairCut(pet3, ServiceType.SHORT_HAIRCUT);

        System.out.println(service.historyService());

        System.out.println(service.topTenRevenue());

    }
}
