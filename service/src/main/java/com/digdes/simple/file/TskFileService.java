package com.digdes.simple.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TskFileService {
    TskFileDTO addTskFile(Long id, MultipartFile resource) throws IOException;

    List<TskFileDTO> getByTskId(Long id) throws IOException;

    TskFileModel findTskFileByKey(Long id, String name);

    Resource downloadTskFile(TskFileKey key) throws IOException;
}