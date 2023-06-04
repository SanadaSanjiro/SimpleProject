package com.digdes.simple.dao.member;

import com.digdes.simple.model.member.MemberModel;
import com.digdes.simple.model.member.MembersKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberModel, MembersKey> {
//    @Query(value = "select m from MemberModel m where m.getProject().getCode()e=:code")
//    Optional<List<MemberModel>> getByProject(String code);
}
