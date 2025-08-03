package com.realEstate.controller;


import com.realEstate.model.GlobalParameter;
import com.realEstate.service.GlobalParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/parameters")
public class GlobalParameterController {

    @Autowired
    private GlobalParameterService parameterService;

    @GetMapping
    public ResponseEntity<GlobalParameter> getParameters() {
        return ResponseEntity.ok(parameterService.getParameters());
    }

    @PutMapping
    public ResponseEntity<GlobalParameter> updateParameters(@RequestBody GlobalParameter parameters) {
        return ResponseEntity.ok(parameterService.updateParameters(parameters));
    }
}