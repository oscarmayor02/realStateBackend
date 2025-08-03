package com.realEstate.service;

import com.realEstate.model.GlobalParameter;

public interface GlobalParameterService {

    GlobalParameter getParameters();

    GlobalParameter updateParameters(GlobalParameter parameters);
}