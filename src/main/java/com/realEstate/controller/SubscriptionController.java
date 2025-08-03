package com.realEstate.controller;


import com.realEstate.model.Subscription;
import com.realEstate.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/active/{userId}")
    public ResponseEntity<List<Subscription>> getActiveSubscriptions(@PathVariable Long userId) {
        return ResponseEntity.ok(subscriptionService.getActiveSubscriptionsByUser(userId));
    }

    @PostMapping
    public ResponseEntity<Subscription> createSubscription(@RequestBody Subscription subscription) {
        return ResponseEntity.ok(subscriptionService.createSubscription(subscription));
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<Void> deactivateSubscription(@PathVariable Long id) {
        subscriptionService.deactivateSubscription(id);
        return ResponseEntity.noContent().build();
    }
}