package org.kao.loglines.entity.project;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.kao.loglines.entity.EntityId;
import org.kao.loglines.entity.TitleDescription;
import org.kao.loglines.entity.directory.Directory;
import org.kao.loglines.entity.task.Task;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "project")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Project extends TitleDescription implements EntityId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @OneToMany(
            mappedBy = "project",
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private List<Task> tasks = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_directory_id")
    private Directory parentDirectory;

}
