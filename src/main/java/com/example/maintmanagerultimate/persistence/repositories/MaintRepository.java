package com.example.maintmanagerultimate.persistence.repositories;

import com.example.maintmanagerultimate.persistence.entities.Maint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintRepository extends JpaRepository<Maint, Long> {

    Maint findMaintByMaintIdentifier(String maintIdentifier);
}
