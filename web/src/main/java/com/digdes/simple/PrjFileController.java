package com.digdes.simple;

import com.digdes.simple.file.PrjFileDTO;
import com.digdes.simple.file.PrjFileModel;
import com.digdes.simple.file.PrjFileService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class PrjFileController {
    private final PrjFileService prjFileService;

    @Operation(summary = "Прикрепить к проекту файл")
    @PostMapping(value = "/upload/{code}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PrjFileDTO addFile(@RequestParam MultipartFile attachment, @PathVariable String code) {
        try {
            return prjFileService.addPrjFile(code, attachment);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Получить список файлов, привязанных к проекту")
    @GetMapping(value = "download/get/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PrjFileDTO> getByProject(@PathVariable String code) {
        List<PrjFileDTO> list = null;
        try {
            list = prjFileService.getByPrjCode(code);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return list;
    }

    @GetMapping(path = "download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> download(@RequestParam(value="code") String code,
                                             @RequestParam(value="name") String name) {
        try {
            PrjFileModel prjFileModel = prjFileService.findPrjFileByKey(code, name);
            Resource resource = prjFileService.downloadPrjFile(prjFileModel.getKey());
            return ResponseEntity.ok()
                    .header("Content-Disposition",
                            "attachment; filename=" + prjFileModel.getKey().getName())
                    .body(resource);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
