package com.realEstate.service;

import com.realEstate.model.Subscription;
import java.util.List;

public interface SubscriptionService {
    List<Subscription> getActiveSubscriptionsByUser(Long userId);
    Subscription createSubscription(Subscription subscription);
    void deactivateSubscription(Long id);
}
