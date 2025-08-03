package com.realEstate.controller;


import com.realEstate.model.Availability;
import com.realEstate.repository.AvailabilityRepository;
import com.realEstate.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/availabilities")
public class AvailabilityController {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @GetMapping
    public List<Availability> getByProperty(@RequestParam Long propertyId) {
        return availabilityRepository.findByPropertyId(propertyId);
    }

    @PostMapping
    public Availability create(@RequestBody Availability availability) {
        return availabilityRepository.save(availability);
    }
}
