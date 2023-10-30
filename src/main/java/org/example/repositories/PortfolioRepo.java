package org.example.repositories;

import org.example.models.Portfolio;
import org.springframework.data.repository.CrudRepository;

public interface PortfolioRepo extends CrudRepository<Portfolio, Integer> {
}
