package com.digdes.simple.file;

import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

public class TskFileMapper {
    public static TskFileModel map (TskFileDTO dto) {
        TskFileModel model = new TskFileModel();
        if (!ObjectUtils.isEmpty(dto.getId())&&
                !ObjectUtils.isEmpty(dto.getName())) {
            TskFileKey key = new TskFileKey();
            key.setName(dto.getName());
            key.setTaskid(dto.getId());
            model.setKey(key);
        } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return model;
    }

    public static TskFileDTO map(TskFileModel model) {
        TskFileDTO dto = new TskFileDTO();
        dto.setName(model.getKey().getName());
        dto.setId(model.getTaskModel().getId());
        return dto;
    }
}
