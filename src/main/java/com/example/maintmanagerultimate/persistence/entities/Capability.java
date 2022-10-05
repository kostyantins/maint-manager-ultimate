package com.example.maintmanagerultimate.persistence.entities;

import com.example.maintmanagerultimate.persistence.enums.CapabilityNames;
import lombok.*;

import javax.persistence.*;

//todo investigate how to create default values (approval, limit, access-control)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "capability")
public class Capability {

    @Id
    @Column
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "capabilety_name")
    private CapabilityNames capabilityName;
}
