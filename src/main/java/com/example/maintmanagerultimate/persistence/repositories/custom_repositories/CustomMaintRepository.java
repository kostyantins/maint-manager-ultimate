package com.example.maintmanagerultimate.persistence.repositories.custom_repositories;

//Such repo was created as an example, such functionality can be placed into MaintRepository
public interface CustomMaintRepository {

    void updateMaintFixVersion(String fixVersion, Long maintId);
}
