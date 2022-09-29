package com.example.maintmanagerultimate.persistence.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString(exclude = "maint")
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "maint_comments")
public class MaintComments {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "comment_text", nullable = false)
    private String commentText;

    @JsonFormat(pattern = "yyyy-MM-dd")
    //todo how to get nullable = false without error
    @Column(name = "created_data", nullable = true)
    private LocalDate createdData;

    //todo why I can not use FetchType.LAZY
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maint_id")
    private Maint maint;

    public MaintComments(String commentText, Maint maint) {
        this.commentText = commentText;
        this.maint = maint;
        this.createdData = LocalDate.now();
    }
}
