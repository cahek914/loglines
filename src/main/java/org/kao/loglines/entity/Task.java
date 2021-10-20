package org.kao.loglines.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.kao.loglines.configuration.SizeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "task")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Task extends EntityId {

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedDate;

    @NotNull
    @Size(max = SizeType.TASK_TITLE_MAX_SIZE)
    @Column(nullable = false, length = SizeType.TASK_TITLE_MAX_SIZE)
    private String title;

    @NotNull
    @Size(max = SizeType.TASK_DESCRIPTION_MAX_SIZE)
    @Column(nullable = false, length = SizeType.TASK_DESCRIPTION_MAX_SIZE)
    private String description;

}
