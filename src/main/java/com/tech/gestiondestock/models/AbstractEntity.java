package com.tech.gestiondestock.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue
    Integer id;

    @Column(name = "creationDate"/*, nullable = false v16 min44*/, nullable = false, updatable = false)
    @CreatedDate
    Instant creationDate;

    @Column(name = "lastModifiedDate" )
    @LastModifiedDate
    Instant lastModifiedDate;
}
