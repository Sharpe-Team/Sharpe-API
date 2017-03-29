package com.esgi.point;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by rfruitet on 07/03/2017.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "point")
public class PointEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "idline")
    private Long idLine;

    @Column(name = "iduser")
    @NotNull
    private Long idUser;

    @Column
    @NotNull
    @NotEmpty
    @Length(min = 1)
    private String content;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date created;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updated;

    public Long getId() {
        return this.id;
    }

    public Long getIdLine() {
        return this.idLine;
    }

    public Long getIdUser() {
        return this.idUser;
    }

    public String getContent() {
        return this.content;
    }

    public Date getCreated() {
        return this.created;
    }

    public Date getUpdated() {
        return this.updated;
    }
}