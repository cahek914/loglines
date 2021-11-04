package org.kao.loglines.dto.directory;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.kao.loglines.entity.EntityId;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class DirectoryFullDto extends DirectoryUpdateDto implements EntityId {

    private Long id;

    private List<Long> projectIds;

}
