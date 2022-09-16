package com.example.maintmanagerultimate.persistence.repositories;

import com.example.maintmanagerultimate.persistence.entities.MaintComments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintCommentsRepository extends JpaRepository<MaintComments, Long> {
}
