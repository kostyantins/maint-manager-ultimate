package com.example.maintmanagerultimate.persistence.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "maint_profile")
public class MaintProfile {

    @Id
    private Long id;

    @Column(name = "pbr_planed_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate pbrPlanedDate;

    @Column(name = "pbr_real_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate pbrRealDate;

    @Column(name = "pbr_conclusions")
    private String pbrConclusion;

    @Column(name = "definition_of_ready")
    private String definitionOfReady;

    @Column(name = "definition_of_done")
    private String definitionOfDone;

    @MapsId
    @OneToOne
    private Maint maint;
}
