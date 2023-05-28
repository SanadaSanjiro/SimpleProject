package com.digdes.simple.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
public class MemberModel implements Serializable {
    @EmbeddedId
    private MembersKey id;

    @ManyToOne
    @MapsId("empid")
    @JoinColumn(name = "empid")
    private EmployeeModel employee;

    @ManyToOne
    @MapsId("prjcode")
    @JoinColumn(name = "prjcode")
    private ProjectModel project;

    @Enumerated(EnumType.STRING)
    private Role role;

}
