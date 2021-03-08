package com.izavasconcelos.cloud.tema02.module;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import com.izavasconcelos.cloud.tema02.petstore.*;


public class PetStoreModule extends AbstractModule {

    @Override
    protected void configure() {
        MapBinder<ServiceType, PetStore> mapBinder = MapBinder.newMapBinder(binder(), ServiceType.class, PetStore.class);
        mapBinder.addBinding(ServiceType.DRY_BATH_WITH_PERFUME).to(BathDryWithPerfume.class);
        mapBinder.addBinding(ServiceType.DRY_BATH_WITHOUT_PERFUME).to(BathDryWithoutPerfume.class);
        mapBinder.addBinding(ServiceType.WATER_BATH_WITH_PERFUME).to(BathWaterWithPerfume.class);
        mapBinder.addBinding(ServiceType.WATER_BATH_WITHOUT_PERFUME).to(BathWaterWithoutPerfume.class);
        mapBinder.addBinding(ServiceType.LONG_HAIRCUT).to(HairCutLong.class);
        mapBinder.addBinding(ServiceType.SHORT_HAIRCUT).to(HairCutShort.class);
    }


}
