package dk.eazyit.halalguide.domain;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Entity
public class Subject extends BaseEntity {

    private String title;

    private Number count;

    private Date lastMessage;

    public Subject() {
        super();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Number getCount() {
        return count;
    }

    public void setCount(Number count) {
        this.count = count;
    }

    public Date getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Date lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id + '\'' +
                ", title='" + title + '\'' +
                ", count=" + count +
                ", lastMessage=" + lastMessage +
                ", creationStatus=" + creationStatus +
                ", submitterId='" + submitterId + '\'' +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}
