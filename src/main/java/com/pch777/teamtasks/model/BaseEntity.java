package com.pch777.teamtasks.model;

import java.util.UUID;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EqualsAndHashCode(of = "uuid")
public abstract class BaseEntity {

    private String uuid = UUID.randomUUID().toString();

    @Version
    private long version;
    
    
}
