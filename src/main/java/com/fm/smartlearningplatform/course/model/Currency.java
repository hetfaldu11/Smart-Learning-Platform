package com.fm.smartlearningplatform.course.model;

import com.fm.smartlearningplatform.user.model.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "currency")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Currency extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name", nullable = false, unique = true)
    private String name;

    @Column(name = "code", nullable = false, unique = true, length = 3)
    private String code;

}
