package com.digdes.simple.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="projects")
public class ProjectModel implements Serializable {
    @Id
    private String code;
    private String uuid;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @OneToMany(mappedBy = "project")
    private Set<MemberModel> members;

    public ProjectModel() {
        members = new HashSet<>();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public void addMember(MemberModel member) {
        members.add(member);
    }

    public void deleteMember(MemberModel member) {
        members.remove(member);
    }

    public Set<MemberModel> getMembers() {
        return members;
    }
}
