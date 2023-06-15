package com.digdes.simple.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Файл задачи")
public class TskFileDTO {
    @Schema(description = "Имя файла")
    public String name;
    @Schema(description = "ID задачи, к которой привязан файл")
    public Long id;
}
