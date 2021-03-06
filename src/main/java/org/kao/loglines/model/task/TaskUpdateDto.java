package org.kao.loglines.model.task;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.kao.loglines.entity.TitleDescription;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class TaskUpdateDto extends TitleDescription {

    private Long projectId;

}
