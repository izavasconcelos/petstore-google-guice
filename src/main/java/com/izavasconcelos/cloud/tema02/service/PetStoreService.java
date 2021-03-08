package com.izavasconcelos.cloud.tema02.service;

import com.google.inject.Inject;
import com.izavasconcelos.cloud.tema02.exceptions.EmptyOrNullFieldException;
import com.izavasconcelos.cloud.tema02.exceptions.IsAInvalidOptionException;
import com.izavasconcelos.cloud.tema02.exceptions.PetIsNullException;
import com.izavasconcelos.cloud.tema02.model.Pet;
import com.izavasconcelos.cloud.tema02.petstore.PetStore;
import com.izavasconcelos.cloud.tema02.petstore.ServiceType;


import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class PetStoreService {

    private Map<ServiceType, PetStore> petStoreMap;
    private Map<Integer, List<PetStore>> serviceList;
    private int countId = 0;
    private List<Pet> petList;

    @Inject
    public PetStoreService(Map<ServiceType, PetStore> petStoreMap) {
        this.petStoreMap = petStoreMap;
        this.serviceList = new HashMap<>();
        this.petList = new ArrayList<>();
    }

    public boolean addPet(Pet pet){

        if(pet == null)
            throw new PetIsNullException("Pet is null!");

        if(pet.getName() == null || pet.getName().isEmpty() ||  pet.getRace() == null || pet.getRace().isEmpty())
            throw new EmptyOrNullFieldException("Empty Or Null Field!");

        countId+=1;
        pet.setId(countId);
        serviceList.put(pet.getId(), new ArrayList<>());
        return petList.add(pet);
    }

    public boolean removePetById(int id){
        return petList.removeIf(idPet -> Objects.equals(idPet.getId(), id));
    }

    public List<Pet> getPetList() {
        return petList;
    }

    public List<Pet> searchPetByAge(int age){
        return petList.stream().filter(agePet -> Objects.equals(agePet.getAge(), age)).collect(toList());
    }

    public Optional<Pet> searchPetById(int id) {
        return petList.stream().filter(i -> i.getId() == id).findFirst();
    }

    public Map<Integer, List<PetStore>> historyService() {
        return serviceList;
    }

    public boolean bath(Pet pet, ServiceType serviceType){
        if(!searchPetById(pet.getId()).isPresent())
            throw new PetIsNullException("Pet is null or empty!");

        PetStore petBath = petStoreMap.get(serviceType);
        if(petBath == null)
            throw new IsAInvalidOptionException("Option invalid!");

        return serviceList.get(pet.getId()).add(petBath);
    }

    public boolean hairCut(Pet pet, ServiceType serviceType){
        if(!searchPetById(pet.getId()).isPresent())
            throw new PetIsNullException("Pet is null or empty!");

        PetStore petHairCut = petStoreMap.get(serviceType);
        if(petHairCut == null)
            throw new IsAInvalidOptionException("Option invalid!");

        return serviceList.get(pet.getId()).add(petHairCut);
    }

    public Map<Integer, Double> topTenRevenue(){
        Map<Integer, Double> topTen = new HashMap<>();
        serviceList.forEach((id, service) -> {
            double calculateRevenue = service.stream()
                    .map(PetStore::getPrice)
                    .reduce(0.0, Double::sum);

            topTen.put(id, calculateRevenue);
        });
        return topTen.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (x,y)-> {throw new AssertionError();}, LinkedHashMap::new));
    }

}
