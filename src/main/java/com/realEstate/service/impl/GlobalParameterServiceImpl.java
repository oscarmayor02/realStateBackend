package com.realEstate.service.impl;

import com.realEstate.model.GlobalParameter;
import com.realEstate.repository.GlobalParameterRepository;
import com.realEstate.service.GlobalParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GlobalParameterServiceImpl implements GlobalParameterService {

    @Autowired
    private GlobalParameterRepository parameterRepository;

    @Override
    public GlobalParameter getParameters() {
        Optional<GlobalParameter> paramsOpt = parameterRepository.findById(1L);
        return paramsOpt.orElseGet(() -> {
            // Si no existe, crea uno con valores por defecto
            GlobalParameter defaultParams = new GlobalParameter();
            defaultParams.setMaxReservations(10);
            defaultParams.setPlatformFee(5.0);
            return parameterRepository.save(defaultParams);
        });
    }

    @Override
    public GlobalParameter updateParameters(GlobalParameter parameters) {
        parameters.setId(1L); // Asegurar que siempre actualiza el registro único
        return parameterRepository.save(parameters);
    }
}
