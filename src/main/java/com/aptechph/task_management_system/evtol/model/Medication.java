package com.aptechph.task_management_system.evtol.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", 
             message = "Name can only contain letters, numbers, hyphens, and underscores")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Weight is required")
    @Min(value = 1, message = "Weight must be at least 1 gram")
    @Column(nullable = false)
    private Integer weight; // in grams

    @NotBlank(message = "Code is required")
    @Pattern(regexp = "^[A-Z0-9_]+$", 
             message = "Code can only contain uppercase letters, numbers, and underscores")
    @Column(nullable = false, unique = true)
    private String code;

    @Column(length = 1000)
    private String image; // URL or base64 encoded image

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evtol_id")
    @JsonIgnore
    private Evtol evtol;
}
