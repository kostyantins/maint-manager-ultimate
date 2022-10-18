package com.example.maintmanagerultimate.persistence.repositories;

import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.persistence.repositories.custom_repositories.CustomMaintRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MaintRepository extends JpaRepository<Maint, Long>, CustomMaintRepository {

    Maint findMaintByMaintIdentifier(String maintIdentifier);

    void deleteById(Long maintId);

    @Query("select m from Maint m order by m.createdData desc")
    Maint findLastAddedMaint();

    @Query("select m from Maint m join fetch m.comments where m.id = :id")
    Maint findByIdFetchComment(@Param("id") Long id);

    //The purpose of such constructions (native queries) is mostly to use 'join fetch' approach that allows us to avoid additional sql/DB calls
    @Query("select m from Maint m where m.solvePriorityId = :solvePriorityId")
    Maint findMaintBySolvePriorityId(@Param("solvePriorityId") Integer solvePriorityId);

    //todo why we were insisted to use @Transactional and @Modifying?
    //Just an example
    @Transactional
    @Modifying
    @Query("update Maint set fixVersion = :fixVersion where id = :id")
    void updateMaintFixVersionBy(@Param("fixVersion") String fixVersion, @Param("id") Long id);
}
