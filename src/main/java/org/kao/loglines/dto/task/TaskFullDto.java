package org.kao.loglines.dto.task;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class TaskFullDto extends TaskUpdateDto {

    private Long id;

}
