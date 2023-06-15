package com.digdes.simple;

import com.digdes.simple.file.*;
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
@RequestMapping("/tasks")
public class TskFileController {
    private final TskFileService fileService;

    @Operation(summary = "Прикрепить файл к задаче")
    @PostMapping(value = "/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public TskFileDTO addFile(@RequestParam MultipartFile attachment, @PathVariable Long id) {
        try {
            return fileService.addTskFile(id, attachment);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Получить список файлов, привязанных к задаче")
    @GetMapping(value = "download/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TskFileDTO> getByTask(@PathVariable Long id) {
        List<TskFileDTO> list = null;
        try {
            list = fileService.getByTskId(id);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return list;
    }

    @GetMapping(path = "download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> download(@RequestParam(value="id") Long id,
                                             @RequestParam(value="name") String name) {
        try {
            TskFileModel tskFileModel = fileService.findTskFileByKey(id, name);
            Resource resource = fileService.downloadTskFile(tskFileModel.getKey());
            return ResponseEntity.ok()
                    .header("Content-Disposition",
                            "attachment; filename=" + tskFileModel.getKey().getName())
                    .body(resource);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
