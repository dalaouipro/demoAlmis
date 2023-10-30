package org.example.repositories;

import org.example.models.Pricing;
import org.springframework.data.repository.CrudRepository;

public interface PricingRepo extends CrudRepository<Pricing, Integer> {
}
