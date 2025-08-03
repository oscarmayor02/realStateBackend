package com.realEstate.service.impl;


import com.realEstate.model.Subscription;
import com.realEstate.repository.SubscriptionRepository;
import com.realEstate.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public List<Subscription> getActiveSubscriptionsByUser(Long userId) {
        return subscriptionRepository.findByUserIdAndActiveTrue(userId);
    }

    @Override
    public Subscription createSubscription(Subscription subscription) {
        subscription.setActive(true);
        return subscriptionRepository.save(subscription);
    }

    @Override
    public void deactivateSubscription(Long id) {
        Subscription sub = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Suscripción no encontrada"));
        sub.setActive(false);
        subscriptionRepository.save(sub);
    }
}