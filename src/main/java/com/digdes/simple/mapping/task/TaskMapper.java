package com.digdes.simple.mapping.task;

import com.digdes.simple.dto.task.TaskDTO;
import com.digdes.simple.model.task.TaskModel;
import org.springframework.util.ObjectUtils;

public class TaskMapper {

    public static TaskDTO map (TaskModel model) {
        TaskDTO dto = new TaskDTO();
        if (!ObjectUtils.isEmpty(model.getId())) {
            dto.setId(model.getId());
        }
        if (!ObjectUtils.isEmpty(model.getName())) {
            dto.setName(model.getName());
        }
        if (!ObjectUtils.isEmpty(model.getDetails())) {
            dto.setDetails(model.getDetails());
        }
        if (!ObjectUtils.isEmpty(model.getEmployee())) {
            dto.setEmployee(model.getEmployee().getId());
        }
        if (!ObjectUtils.isEmpty(model.getLaborCost())) {
            dto.setLaborCost(model.getLaborCost());
        }
        if (!ObjectUtils.isEmpty(model.getExecutionDate())) {
            dto.setExecutionDate(model.getExecutionDate());
        }
        if (!ObjectUtils.isEmpty(model.getAuthor())) {
            dto.setEmployee(model.getAuthor().getId());
        }
        if (!ObjectUtils.isEmpty(model.getCreationDate())) {
            dto.setCreationDate(model.getCreationDate());
        }
        if (!ObjectUtils.isEmpty(model.getChangeDate())) {
            dto.setCreationDate(model.getChangeDate());
        }
        if (!ObjectUtils.isEmpty(model.getStatus())) {
            model.getStatus().toString();
        }
        return dto;
    }
}
