package org.example.repositories;

import org.example.models.Position;
import org.springframework.data.repository.CrudRepository;

public interface PositionRepo extends CrudRepository<Position, Integer> {
}
