package com.digdes.simple.service.member.impl;

import com.digdes.simple.dao.employee.EmployeeDAO;
import com.digdes.simple.dao.member.MemberDAO;
import com.digdes.simple.dao.project.ProjectDAO;
import com.digdes.simple.dto.member.MemberDTO;
import com.digdes.simple.dto.member.MemberDltDTO;
import com.digdes.simple.mapping.member.MemberMapper;
import com.digdes.simple.model.member.MemberModel;
import com.digdes.simple.model.member.MembersKey;
import com.digdes.simple.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDAO memberDAO;

    @Autowired
    EmployeeDAO employeeDAO;

    @Autowired
    ProjectDAO projectDAO;


    @Override
    public MemberDTO create(MemberDTO dto) {
        // Проверяем, что DTO не пустое, и содержит обязательные поля код проекта и id сотрудника
        if (dto == null || dto.getEmpid() == null || dto.getPrjcode() ==null || dto.getRole() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        // Проверяем, что сотрудник с таким id существеует
        if (employeeDAO.getById(dto.getEmpid())==null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        // Проверяем, что проект с таким кодом существеует
        if (projectDAO.getByCode(dto.getPrjcode())==null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        MembersKey mk = new MembersKey();
        mk.setEmpid(dto.getEmpid());
        mk.setPrjcode(dto.getPrjcode());

        // Проверяем что данный сотрудник отсутсвует в этом проекте
        if ((memberDAO.getById(mk)!=null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        MemberModel model = MemberMapper.map(dto);
        model = memberDAO.create(model);
        return MemberMapper.map(model);
    }

    @Override
    public MemberDTO delete(String code, Long id) {
        if (code == null || id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        MembersKey mk = new MembersKey();
        mk.setEmpid(id);
        mk.setPrjcode(code);
        MemberModel model = memberDAO.deleteById(mk);
        if (model==null) {throw new ResponseStatusException(HttpStatus.NOT_FOUND);}
        return MemberMapper.map(model);
    }

    @Override
    public List<MemberDTO> getByPrjCode(String code) {
        List<MemberDTO> dtos = memberDAO.getByPrjCode(code)
                .stream()
                .map(m-> MemberMapper.map(m))
                .toList();
        return dtos;
    }

    @Override
    public MemberDTO getById(String code, Long id) {
        if (code == null || id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        MembersKey mk = new MembersKey();
        mk.setEmpid(id);
        mk.setPrjcode(code);
        MemberModel model = memberDAO.getById(mk);
        return MemberMapper.map(model);
    }
}
