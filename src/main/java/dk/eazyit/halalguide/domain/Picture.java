package dk.eazyit.halalguide.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Picture extends BaseEntity{

    @ManyToOne
    private Location location;

    @ManyToOne
    private Review review;

    private String picture;

    public Picture() {
        super();
    }

    public Picture(Location location, Review review) {
        this.location = location;
        this.review = review;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id + '\'' +
                "picture='" + picture + '\'' +
                ", creationStatus=" + creationStatus +
                ", submitterId='" + submitterId + '\'' +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}
