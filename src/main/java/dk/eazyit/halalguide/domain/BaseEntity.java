package dk.eazyit.halalguide.domain;

import dk.eazyit.halalguide.domain.enums.CreationStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

    protected CreationStatus creationStatus;

    protected String submitterId;

    protected Date updatedAt;

    protected Date createdAt;

    protected BaseEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CreationStatus getCreationStatus() {
        return creationStatus;
    }

    public void setCreationStatus(CreationStatus creationStatus) {
        this.creationStatus = creationStatus;
    }

    public String getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(String submitterId) {
        this.submitterId = submitterId;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    public void prePersist() {
        this.creationStatus = CreationStatus.AwaitingApproval;
        this.updatedAt = this.createdAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date();
    }
}
