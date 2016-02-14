package dk.eazyit.halalguide.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
public class Message extends BaseEntity {

    @JsonIgnore
    @ManyToOne
    private Subject subject;

    private String text;

    private String media;

    public Message() {
        super();
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id + '\'' +
                ", text='" + text + '\'' +
                ", media='" + media + '\'' +
                ", creationStatus=" + creationStatus +
                ", submitterId='" + submitterId + '\'' +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}
