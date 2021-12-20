package org.kao.loglines.model.task;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.kao.loglines.entity.EntityId;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class TaskFullDto extends TaskUpdateDto implements EntityId {

    private Long id;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

}
