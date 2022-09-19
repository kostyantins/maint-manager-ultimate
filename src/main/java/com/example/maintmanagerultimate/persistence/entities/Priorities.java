package com.example.maintmanagerultimate.persistence.entities;

import lombok.Getter;

import javax.persistence.*;

//todo investigate how to create default values (1-high, 2-middle, 3-low)
@Getter
@Entity
@Table(name = "priority")
public class Priorities {

    @Id
    @Column
    private Long id;

    @Column(name = "priority_name")
    private String priorityName;

    @OneToOne
    @JoinColumn(name = "maint_sovle_priority_id")
    private Maint maint;
}
