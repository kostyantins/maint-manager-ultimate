package com.example.maintmanagerultimate.persistence.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@EntityListeners(AuditingEntityListener.class)
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "created_date")
    private LocalDate createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maint_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Maint maint;

    public MaintComments(String commentText, Maint maint) {
        this.commentText = commentText;
        this.maint = maint;
        this.createdDate = LocalDate.now();
    }
}
