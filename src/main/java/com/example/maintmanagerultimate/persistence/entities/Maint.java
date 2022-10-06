package com.example.maintmanagerultimate.persistence.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
@Builder
@Entity
@Table(name = "maint")
public class Maint {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "maint_identifier", unique = true)
    private String maintIdentifier;

    @Column(name = "capability_id", nullable = false)
    private Long capabilityId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "appeared_data", nullable = false)
    private LocalDate createdData;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "due_data", nullable = false)
    private LocalDate dueData;

    @Column(name = "sovle_priority_id", nullable = false)
    private Integer solvePriorityId;

    @Column
    @ColumnDefault("'N/A'")
    private Integer estimate;

    @Column(name = "fix_version")
    @ColumnDefault("'N/A'")
    private String fixVersion;

    @Column(nullable = false)
    private String client;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "maint", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<MaintComments> comments = new ArrayList<>();

    public void addComment(MaintComments comment) {
        comments.add(comment);
        comment.setMaint(this);
    }

    public void removeComment(MaintComments comment) {
        comments.remove(comment);
        comment.setMaint(null);
    }
}
