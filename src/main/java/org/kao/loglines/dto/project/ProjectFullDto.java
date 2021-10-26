package org.kao.loglines.dto.project;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.kao.loglines.entity.TitleDescription;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ProjectFullDto extends TitleDescription {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private List<Long> taskIds;

    private Long parentDirectoryId;

}
