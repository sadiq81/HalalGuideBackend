package dk.eazyit.halalguide.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Set;

@Entity
public class Review extends BaseEntity {

    @Transient
    private Location location;

    private int rating;

    @Column(length = 1000)
    private String review;

    @Transient
    private Set<Picture> pictures;

    public Review() {
        super();
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id + '\'' +
                ", review=" + review +
                ", rating=" + rating +
                ", creationStatus=" + creationStatus +
                ", submitterId='" + submitterId + '\'' +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}
