package com.example.maintmanagerultimate.persistence.repositories;

import com.example.maintmanagerultimate.persistence.entities.MaintProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintProfileRepository extends JpaRepository<MaintProfile, Long> {
}
