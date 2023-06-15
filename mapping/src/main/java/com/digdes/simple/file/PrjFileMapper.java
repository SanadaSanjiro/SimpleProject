package com.digdes.simple.file;

import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

public class PrjFileMapper {
    public static PrjFileModel map (PrjFileDTO dto) {
        PrjFileModel model = new PrjFileModel();
        if (!ObjectUtils.isEmpty(dto.getPrjcode())&&
                !ObjectUtils.isEmpty(dto.getName())) {
            PrjFileKey key = new PrjFileKey();
            key.setName(dto.getName());
            key.setPrjcode(dto.getPrjcode());
            model.setKey(key);
        } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return model;
    }

    public static PrjFileDTO map(PrjFileModel model) {
        PrjFileDTO dto = new PrjFileDTO();
        dto.setName(model.getKey().getName());
        dto.setPrjcode(model.getProject().getCode());
        return dto;
    }
}
