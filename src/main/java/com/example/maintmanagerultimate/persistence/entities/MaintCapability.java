package com.example.maintmanagerultimate.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "maint_capability")
public class MaintCapability {

    @Id
    @Column
    private Long maintId;

    @OneToOne
    @JoinColumn(name = "capability_id")
    //todo why I cannot map on id directly but on Entity need
    private Capability capabilityId;
}
