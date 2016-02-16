package dk.eazyit.halalguide.domain;

import dk.eazyit.halalguide.domain.enums.SocialMediaType;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Privat
 * Date: 12/02/16
 * Time: 19.59
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class User extends BaseEntity {

    private String firstName;

    private String lastName;

    private SocialMediaType socialMediaType = SocialMediaType.Facebook;

    private String socialMediaId;

    @Transient
    private Map<String, String> userData;

    public User() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public SocialMediaType getSocialMediaType() {
        return socialMediaType;
    }

    public void setSocialMediaType(SocialMediaType socialMediaType) {
        this.socialMediaType = socialMediaType;
    }

    public String getSocialMediaId() {
        return socialMediaId;
    }

    public void setSocialMediaId(String socialMediaId) {
        this.socialMediaId = socialMediaId;
    }

    public Map<String, String> getUserData() {
        return userData;
    }

    public void setUserData(Map<String, String> userData) {
        if (userData != null) {
            this.firstName = userData.get("first_name");
            this.lastName = userData.get("last_name");
            this.socialMediaId = userData.get("id");
            this.userData = userData;
        }
    }
}
