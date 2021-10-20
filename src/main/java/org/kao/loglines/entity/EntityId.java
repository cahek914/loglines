package org.kao.loglines.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode
@ToString
public abstract class EntityId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
