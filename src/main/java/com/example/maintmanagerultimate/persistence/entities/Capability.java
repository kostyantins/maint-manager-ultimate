package com.example.maintmanagerultimate.persistence.entities;

import lombok.Getter;

import javax.persistence.*;

//todo investigate how to create default values (approval, limit, access-control)
@Getter
@Entity
@Table(name = "capability")
public class Capability {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "capabilety_name")
    private String capabilityName;

    @OneToOne
    @JoinColumn(name = "maint_capability_id")
    private Maint maint;
}
