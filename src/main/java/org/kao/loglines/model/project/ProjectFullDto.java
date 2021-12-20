package org.kao.loglines.model.project;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.kao.loglines.entity.EntityId;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ProjectFullDto extends ProjectUpdateDto implements EntityId {

    private Long id;

    private List<Long> taskIds;

}
