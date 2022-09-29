package com.example.maintmanagerultimate.persistence.repositories.custom_repositories_implementation;

import com.example.maintmanagerultimate.persistence.repositories.custom_repositories.CustomMaintRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//Such repo implementation   was created as an example, such functionality can be placed into MaintRepository
@Repository
@Transactional
public class CustomMaintRepositoryImpl implements CustomMaintRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void updateMaintFixVersion(String fixVersion, Long maintId) {
        entityManager
                .createQuery("update Maint set fixVersion = :fixVersion where id = :id")
                .setParameter("fixVersion", fixVersion)
                .setParameter("id", maintId)
                .executeUpdate();
    }
}
