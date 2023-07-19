package org.example.models;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class Auditable {

    @CreatedBy
    @Column
    protected String createdBy;

    @CreatedDate
    @Column
    protected Date createdDate;

    @LastModifiedBy
    @Column
    protected String lastModifiedBy;

    @LastModifiedDate
    @Column
    protected Date lastModifiedDate;

}
