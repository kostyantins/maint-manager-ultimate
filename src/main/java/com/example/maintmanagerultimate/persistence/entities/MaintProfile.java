package com.example.maintmanagerultimate.persistence.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
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
