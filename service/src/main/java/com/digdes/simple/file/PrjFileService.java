package com.digdes.simple.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PrjFileService {
    PrjFileDTO addPrjFile(String code, MultipartFile resource) throws IOException;

    List<PrjFileDTO> getByPrjCode(String code) throws IOException;

    PrjFileModel findPrjFileByKey(String code, String name);

    Resource downloadPrjFile(PrjFileKey key) throws IOException;
}
