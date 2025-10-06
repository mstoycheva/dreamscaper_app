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
@Table(name = "DreamLinks")
public class DreamLink {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "dream1_id")
    @JsonIgnore
    private Dream dream1;

    @ManyToOne
    @JoinColumn(name = "dream2_id")
    @JsonIgnore
    private Dream dream2;

    @Column(name = "connection_reason")
    private String connectionReason;


}

