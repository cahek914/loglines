package org.kao.loglines.dto.project;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ProjectFullDto extends ProjectUpdateDto {

    private Long id;

    private List<Long> taskIds;

}
