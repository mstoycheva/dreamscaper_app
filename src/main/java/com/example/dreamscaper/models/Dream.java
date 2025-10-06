package com.example.dreamscaper.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Dreams")
public class Dream {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "mood_before_sleep")
    private Integer moodBeforeSleep;

    @Column(name = "mood_after_waking")
    private Integer moodAfterWaking;

    @Column(name = "lucidity_level")
    private Integer lucidityLevel;

    @Column(name = "nightmare")
    private Boolean nightmare;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Setter
    @ManyToMany
    @JoinTable(
            name = "DreamSymbolUsage",
            joinColumns = @JoinColumn(name = "dream_id"),
            inverseJoinColumns = @JoinColumn(name = "symbol_id")
    )
    @JsonIgnore
    private List<Symbol> symbols;

}


