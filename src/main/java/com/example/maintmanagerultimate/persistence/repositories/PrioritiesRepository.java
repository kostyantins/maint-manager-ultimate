package com.example.maintmanagerultimate.persistence.repositories;

import com.example.maintmanagerultimate.persistence.entities.Priorities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrioritiesRepository extends JpaRepository<Priorities, Long> {
}
