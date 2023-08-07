package com.example.maintmanagerultimate.persistence.entities;

import com.example.maintmanagerultimate.persistence.enums.Capabilities;
import com.example.maintmanagerultimate.persistence.enums.Priorities;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "maint")
public class Maint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "maint_identifier", unique = true)
    private String maintIdentifier;

    @Enumerated
    @Column(name = "capability_id", nullable = false)
    private Capabilities capabilityId;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Enumerated
    @Column(name = "sovle_priority_id", nullable = false)
    private Priorities solvePriorityId;

    @Column
    @ColumnDefault("'N/A'")
    private Integer estimate;

    @Column(name = "fix_version")
    @ColumnDefault("'N/A'")
    private String fixVersion;

    @Column(nullable = false)
    private String client;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(
            mappedBy = "maint",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<MaintComments> comments = new ArrayList<>();

    @OneToOne(
            mappedBy = "maint",
            cascade = CascadeType.ALL)
    private MaintProfile maintProfile;

    public void addComment(MaintComments comment) {
        comments.add(comment);
        comment.setMaint(this);
    }

    public void addComments(List<MaintComments> maintComments) {
        if (maintComments == null || maintComments.isEmpty()) {
            throw new IllegalArgumentException("Maint comments should not be null or empty !!");
        }

        maintComments.forEach(comment -> comment.setMaint(this));
        comments.addAll(maintComments);
    }

    public void removeComment(MaintComments comment) {
        comments.remove(comment);
        comment.setMaint(null);
    }

    public void addProfile(MaintProfile maintProfile){
        if (maintProfile == null) {
            throw new IllegalArgumentException("Maint profile should not be null or empty !!");
        }

        maintProfile.setMaint(this);
        this.maintProfile = maintProfile;
    }
}
