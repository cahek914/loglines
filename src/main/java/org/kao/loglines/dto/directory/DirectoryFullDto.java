package org.kao.loglines.dto.directory;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class DirectoryFullDto extends DirectoryUpdateDto {

    private Long id;

    private List<Long> projectIds;

}
