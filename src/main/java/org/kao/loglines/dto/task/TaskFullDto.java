package org.kao.loglines.dto.task;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.kao.loglines.entity.TitleDescription;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class TaskFullDto extends TitleDescription {

    private Long projectId;

}
