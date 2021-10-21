package org.kao.loglines.dto.task;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.kao.loglines.configuration.SizeType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@EqualsAndHashCode
public class TaskUpdateDto {

    @NotNull
    @Size(max = SizeType.TASK_TITLE_MAX_SIZE)
    private String title;

    @NotNull
    @Size(max = SizeType.TASK_DESCRIPTION_MAX_SIZE)
    private String description;

}
