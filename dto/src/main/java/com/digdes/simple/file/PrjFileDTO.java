package com.digdes.simple.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Файл проекта")
public class PrjFileDTO {
    @Schema(description = "Имя файла")
    public String name;
    @Schema(description = "Код проекта, к которому привязан файл")
    public String prjcode;
}

