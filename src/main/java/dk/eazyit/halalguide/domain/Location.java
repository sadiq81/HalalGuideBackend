package dk.eazyit.halalguide.domain;

import dk.eazyit.halalguide.domain.enums.DiningCategory;
import dk.eazyit.halalguide.domain.enums.Language;
import dk.eazyit.halalguide.domain.enums.LocationType;
import dk.eazyit.halalguide.domain.enums.ShopCategory;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;
import java.util.Set;

@Entity
public class Location extends BaseEntity {

    private String name;

    private String road;

    private String roadNumber;

    private String city;

    private String postalCode;

    private float latitude;

    private float longitude;

    private String website;

    private LocationType locationType;

    private String telephone;

    //Dining location type specific
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> navneLoebeNummer;

    private boolean pork;

    private boolean alcohol;

    private boolean nonHalalFood;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<DiningCategory> categories;
    //

    //Mosque location type specific
    private Language language;
    //

    //Shop location type specific
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<ShopCategory> shopCategories;
    //

    @Transient
    private Set<Picture> pictures;

    public Location() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getRoadNumber() {
        return roadNumber;
    }

    public void setRoadNumber(String roadNumber) {
        this.roadNumber = roadNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    public Set<String> getNavneLoebeNummer() {
        return navneLoebeNummer;
    }

    public void setNavneLoebeNummer(Set<String> navneLoebeNummer) {
        this.navneLoebeNummer = navneLoebeNummer;
    }

    public boolean isPork() {
        return pork;
    }

    public void setPork(boolean pork) {
        this.pork = pork;
    }

    public boolean isAlcohol() {
        return alcohol;
    }

    public void setAlcohol(boolean alcohol) {
        this.alcohol = alcohol;
    }

    public boolean isNonHalalFood() {
        return nonHalalFood;
    }

    public void setNonHalalFood(boolean nonHalalFood) {
        this.nonHalalFood = nonHalalFood;
    }

    public Set<DiningCategory> getCategories() {
        return categories;
    }

    public void setCategories(Set<DiningCategory> categories) {
        this.categories = categories;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Set<ShopCategory> getShopCategories() {
        return shopCategories;
    }

    public void setShopCategories(Set<ShopCategory> shopCategories) {
        this.shopCategories = shopCategories;
    }

    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id + '\'' +
                ", name='" + name + '\'' +
                ", road='" + road + '\'' +
                ", roadNumber='" + roadNumber + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", Latitude=" + latitude +
                ", Longitude=" + longitude +
                ", website='" + website + '\'' +
                ", locationType=" + locationType +
                ", telephone='" + telephone + '\'' +
                ", navneLoebeNummer=" + navneLoebeNummer +
                ", pork=" + pork +
                ", alcohol=" + alcohol +
                ", nonHalalFood=" + nonHalalFood +
                ", categories=" + categories +
                ", language=" + language +
                ", shopCategories=" + shopCategories +
                ", creationStatus=" + creationStatus +
                ", submitterId='" + submitterId + '\'' +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}
