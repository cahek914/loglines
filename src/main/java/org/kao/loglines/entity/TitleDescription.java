package org.kao.loglines.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.kao.loglines.configuration.SizeType;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TitleDescription {

    @NotNull
    @Size(max = SizeType.TITLE_MAX_SIZE)
    @Column(nullable = false, length = SizeType.TITLE_MAX_SIZE)
    private String title;

    @NotNull
    @Size(max = SizeType.DESCRIPTION_MAX_SIZE)
    @Column(nullable = false, length = SizeType.DESCRIPTION_MAX_SIZE)
    private String description;

}
