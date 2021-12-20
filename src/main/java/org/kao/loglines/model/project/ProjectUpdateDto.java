package org.kao.loglines.model.project;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.kao.loglines.entity.TitleDescription;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ProjectUpdateDto extends TitleDescription {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Long parentDirectoryId;

}
