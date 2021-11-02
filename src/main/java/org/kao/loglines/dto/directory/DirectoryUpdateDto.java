package org.kao.loglines.dto.directory;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.kao.loglines.entity.TitleDescription;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class DirectoryUpdateDto extends TitleDescription {

    private Long parentDirectoryId;

}
