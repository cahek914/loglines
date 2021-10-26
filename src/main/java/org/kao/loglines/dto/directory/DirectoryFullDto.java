package org.kao.loglines.dto.directory;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.kao.loglines.entity.TitleDescription;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class DirectoryFullDto extends TitleDescription {

    private List<Long> projectIds;

    private Long parentDirectoryId;

}
