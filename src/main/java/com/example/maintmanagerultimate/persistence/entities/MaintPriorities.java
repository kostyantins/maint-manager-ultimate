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
@Table(name = "maint_priorities")
public class MaintPriorities {

    @Id
    @Column
    private Long mainId;

    @OneToOne
    @JoinColumn(name = "priorities_id")
    private Priorities priorityId;
}
