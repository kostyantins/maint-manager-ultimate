package com.example.maintmanagerultimate.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//todo investigate how to create default values (1-high, 2-middle, 3-low)
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
