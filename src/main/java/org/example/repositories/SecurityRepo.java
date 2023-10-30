package org.example.repositories;

import org.example.models.Security;
import org.springframework.data.repository.CrudRepository;

public interface SecurityRepo extends CrudRepository<Security, Integer> {
}
