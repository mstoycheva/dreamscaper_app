package com.example.dreamscaper.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Interpretations")
public class Interpretation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "dream_id")
    @JsonIgnore
    private Dream dream;

    @Column(name = "interpreter_name")
    private String interpreterName;

    @Column(name = "interpretation_text", columnDefinition = "TEXT")
    private String interpretationText;

    @Column(name = "rating")
    private Integer rating;

}

