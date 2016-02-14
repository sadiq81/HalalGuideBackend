package dk.eazyit.halalguide.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
public class Picture extends BaseEntity {

    @JsonIgnore
    @ManyToOne
    private Location location;

    @JsonIgnore
    @ManyToOne
    private Review review;

    private String picture;

    public Picture() {
        super();
    }

    public Picture(Location location, String picture) {
        this.location = location;
        this.picture = picture;
    }

    public Picture(Location location, Review review, String picture) {
        this.location = location;
        this.review = review;
        this.picture = picture;
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
