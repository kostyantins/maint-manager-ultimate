package com.example.maintmanagerultimate.persistence.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "maint_comments")
public class MaintComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment_text", nullable = false)
    private String commentText;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @CreationTimestamp
    @Column(name = "created_data", nullable = false)
    private LocalDate createdData;


    @ManyToOne
    @JoinColumn(name = "maint_id", nullable = false)
    private Maint maint;

    public MaintComments(String commentText, Maint maint) {
        this.commentText = commentText;
        this.maint = maint;
        this.createdData = LocalDate.now();
    }
}
