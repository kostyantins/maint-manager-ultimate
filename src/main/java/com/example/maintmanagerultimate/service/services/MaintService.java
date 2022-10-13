package com.example.maintmanagerultimate.service.services;

import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.persistence.repositories.MaintRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaintService {

    private final MaintRepository maintRepository;

    public Maint createMaintAndCommentsIfPresent(Maint maint) {
        if (maint.getComments() != null && !maint.getComments().isEmpty()) {
            maint
                    .getComments()
                    .forEach(maintComments -> maintComments.setMaint(maint));
        }

        return maintRepository.save(maint);
    }
}
