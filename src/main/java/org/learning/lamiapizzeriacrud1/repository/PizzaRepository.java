package org.learning.lamiapizzeriacrud1.repository;

import org.learning.lamiapizzeriacrud1.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
}
