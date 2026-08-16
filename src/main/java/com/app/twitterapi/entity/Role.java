package com.app.twitterapi.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String code;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "roles")
    private Set<User> users = new HashSet<>();
}

