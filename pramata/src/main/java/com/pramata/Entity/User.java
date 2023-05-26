package com.pramata.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pramata.Enum.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Role role;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<ChannelUserMapping> teamUserMappings;

}
