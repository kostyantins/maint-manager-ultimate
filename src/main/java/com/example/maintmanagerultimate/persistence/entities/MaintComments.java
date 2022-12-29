package com.example.maintmanagerultimate.persistence.entities;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "maint_comments")
public class MaintComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment_text", nullable = false)
    private String commentText;

    @CreatedDate
    @Column(name = "created_data", nullable = false)
    private LocalDate createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maint_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Maint maint;

    public MaintComments(String commentText, Maint maint) {
        this.commentText = commentText;
        this.maint = maint;
        this.createdDate = LocalDate.now();
    }
}
