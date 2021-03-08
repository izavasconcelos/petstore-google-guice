package com.izavasconcelos.cloud.tema02.service;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.izavasconcelos.cloud.tema02.exceptions.EmptyOrNullFieldException;
import com.izavasconcelos.cloud.tema02.exceptions.IsAInvalidOptionException;
import com.izavasconcelos.cloud.tema02.exceptions.PetIsNullException;
import com.izavasconcelos.cloud.tema02.model.Pet;
import com.izavasconcelos.cloud.tema02.module.PetStoreModule;
import com.izavasconcelos.cloud.tema02.petstore.ServiceType;
import org.junit.Before;
import org.junit.Test;


import java.util.Map;

import static org.junit.Assert.*;

public class PetStoreServiceTest {
    private Injector injector = Guice.createInjector(new PetStoreModule());
    private PetStoreService service = injector.getInstance(PetStoreService.class);


    @Before
    public void init() {
        service.getPetList().clear();
    }

    @Test
    public void addPetTest() {
        Pet pet = new Pet("Duque", "Cat", 10);
        assertTrue(service.addPet(pet));
        assertEquals(1, service.getPetList().size());
    }

    @Test (expected = PetIsNullException.class)
    public void addNullPetTest() {
        service.addPet(null);
    }

    @Test (expected = EmptyOrNullFieldException.class)
    public void addNullNamePetTest() {
        Pet petNullName = new Pet(null,"Cat", 1);
        service.addPet(petNullName);
    }

    @Test (expected = EmptyOrNullFieldException.class)
    public void addEmptyNamePetTest() {
        Pet petEmptyName = new Pet("","Cat", 1);
        service.addPet(petEmptyName);
    }

    @Test (expected = EmptyOrNullFieldException.class)
    public void addNullRacePetTest() {
        Pet petNullRace = new Pet("Olli",null, 4);
        service.addPet(petNullRace);
    }

    @Test (expected = EmptyOrNullFieldException.class)
    public void addEmptyRacePetTest() {
        Pet petEmptyRace = new Pet("Duque","", 7);
        service.addPet(petEmptyRace);
    }

    @Test
    public void removePetByIdTest() {
        Pet duque = new Pet("Duque", "Cat", 10);
        Pet olli = new Pet("Olli", "Cat", 5);
        Pet frida = new Pet("Frida", "Cat", 2);

        assertEquals(0, service.getPetList().size());

        service.addPet(duque);
        service.addPet(olli);
        service.addPet(frida);
        assertEquals(3, service.getPetList().size());

        service.removePetById(1);
        service.removePetById(2);
        assertEquals(1, service.getPetList().size());

        assertFalse(service.searchPetById(1).isPresent());
        assertFalse(service.searchPetById(2).isPresent());
        assertTrue(service.searchPetById(3).isPresent());
        assertEquals("Frida", service.searchPetById(3).get().getName());
    }

    @Test
    public void removeInvalidPetByIdTest() {
        boolean removeInvalidPet = service.removePetById(666);
        assertFalse(removeInvalidPet);
    }

    @Test
    public void searchPetByAgeTest() {
        Pet duque = new Pet("Duque", "Cat", 10);
        Pet olli = new Pet("Olli", "Cat", 5);
        Pet frida = new Pet("Frida", "Cat", 5);
        service.addPet(duque);
        service.addPet(olli);
        service.addPet(frida);

        assertEquals(2, service.searchPetByAge(5).size());
        assertEquals("Olli", service.searchPetByAge(5).get(0).getName());
        assertEquals("Frida", service.searchPetByAge(5).get(1).getName());
    }

    @Test
    public void bathTest() {
        Pet duque = new Pet("Duque", "Cat", 10);
        Pet olli = new Pet("Olli", "Cat", 5);
        service.addPet(duque);
        service.addPet(olli);

        service.bath(duque, ServiceType.DRY_BATH_WITH_PERFUME);
        service.bath(olli, ServiceType.WATER_BATH_WITHOUT_PERFUME);

        assertEquals("Dry Bath With Perfume", service.historyService().get(duque.getId()).get(0).getService());
        assertEquals("Water Bath Without Perfume", service.historyService().get(olli.getId()).get(0).getService());
    }

    @Test (expected = IsAInvalidOptionException.class)
    public void invalidBathTest() {
        Pet duque = new Pet("Duque", "Cat", 10);
        service.addPet(duque);
        service.bath(duque, null);
    }

    @Test
    public void hairCutTest() {
        Pet duque = new Pet("Duque", "Cat", 10);
        Pet olli = new Pet("Olli", "Cat", 5);
        service.addPet(duque);
        service.addPet(olli);

        service.hairCut(duque, ServiceType.LONG_HAIRCUT);
        service.hairCut(olli, ServiceType.SHORT_HAIRCUT);

        assertEquals("Long Hair Cut", service.historyService().get(duque.getId()).get(0).getService());
        assertEquals("Short Hair Cut", service.historyService().get(olli.getId()).get(0).getService());
    }

    @Test (expected = IsAInvalidOptionException.class)
    public void invalidHairCutTest() {
        Pet duque = new Pet("Duque", "Cat", 10);
        service.addPet(duque);
        service.hairCut(duque, null);
    }

    @Test
    public void historyServiceTest() {
        Pet duque = new Pet("Duque", "Cat", 10);
        service.addPet(duque);

        service.bath(duque, ServiceType.DRY_BATH_WITH_PERFUME);
        service.hairCut(duque, ServiceType.LONG_HAIRCUT);
        service.bath(duque, ServiceType.WATER_BATH_WITHOUT_PERFUME);
        service.hairCut(duque, ServiceType.SHORT_HAIRCUT);

        assertEquals(1, service.historyService().size());
        assertEquals(4, service.historyService().get(duque.getId()).size());

        assertEquals("Dry Bath With Perfume", service.historyService().get(duque.getId()).get(0).getService());
        assertEquals("Water Bath Without Perfume", service.historyService().get(duque.getId()).get(2).getService());
        assertEquals("Long Hair Cut", service.historyService().get(duque.getId()).get(1).getService());
        assertEquals("Short Hair Cut", service.historyService().get(duque.getId()).get(3).getService());
    }

    @Test
    public void topTenRevenueTest() {
        Pet duque = new Pet("Duque", "Cat", 10);
        Pet olli = new Pet("Olli", "Cat", 5);
        Pet frida = new Pet("Frida", "Cat", 7);
        Pet max = new Pet("Max", "Cat", 2);
        Pet axl = new Pet("Axl", "Cat", 7);
        Pet lis = new Pet("Lis", "Cat", 2);
        Pet pancho = new Pet("Pancho", "Cat", 7);
        Pet catena = new Pet("Catena", "Cat", 2);
        Pet luke = new Pet("Luke", "Cat", 7);
        Pet maya = new Pet("Maya", "Cat", 2);

        service.addPet(duque);
        service.addPet(olli);
        service.addPet(frida);
        service.addPet(max);
        service.addPet(axl);
        service.addPet(lis);
        service.addPet(pancho);
        service.addPet(catena);
        service.addPet(luke);
        service.addPet(maya);

        service.bath(duque, ServiceType.DRY_BATH_WITH_PERFUME);
        service.hairCut(maya, ServiceType.LONG_HAIRCUT);
        service.bath(lis, ServiceType.WATER_BATH_WITHOUT_PERFUME);
        service.hairCut(luke, ServiceType.SHORT_HAIRCUT);
        service.bath(duque, ServiceType.DRY_BATH_WITH_PERFUME);
        service.hairCut(axl, ServiceType.LONG_HAIRCUT);
        service.bath(frida, ServiceType.WATER_BATH_WITHOUT_PERFUME);
        service.hairCut(max, ServiceType.SHORT_HAIRCUT);
        service.bath(duque, ServiceType.DRY_BATH_WITH_PERFUME);
        service.hairCut(pancho, ServiceType.LONG_HAIRCUT);
        service.bath(duque, ServiceType.WATER_BATH_WITHOUT_PERFUME);
        service.hairCut(lis, ServiceType.SHORT_HAIRCUT);
        service.bath(catena, ServiceType.DRY_BATH_WITH_PERFUME);
        service.hairCut(lis, ServiceType.LONG_HAIRCUT);
        service.bath(lis, ServiceType.WATER_BATH_WITHOUT_PERFUME);
        service.hairCut(axl, ServiceType.SHORT_HAIRCUT);
        service.bath(duque, ServiceType.DRY_BATH_WITH_PERFUME);
        service.hairCut(olli, ServiceType.LONG_HAIRCUT);
        service.bath(pancho, ServiceType.WATER_BATH_WITHOUT_PERFUME);
        service.hairCut(lis, ServiceType.SHORT_HAIRCUT);

        assertEquals(225.0, service.topTenRevenue().get(duque.getId()), 0.001);
        assertEquals(105.0, service.topTenRevenue().get(lis.getId()), 0.001);
        assertEquals(50.0, service.topTenRevenue().get(pancho.getId()), 0.001);

        Map.Entry<Integer, Double> topTenEntry = null;
        Map<Integer, Double> topTenMap = service.topTenRevenue();
        for(Map.Entry<Integer, Double> entry : topTenMap.entrySet()){
            if(topTenEntry != null){
                assertTrue(topTenEntry.getValue() >= entry.getValue());
            }
            topTenEntry = entry;
        }

    }
}
