package com.mapsit.jamaity.location;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * Location entity.\n@author The Moo3in team.
 */
@ApiModel(description = "Location entity.\n@author The Moo3in team.")
@Entity
@Table(name = "location")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
//    @SequenceGenerator(name = "sequenceGenerator")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "local_number")
    private String localNumber;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "street_address_fr")
    private String streetAddressFr;

    @Column(name = "street_address_ar")
    private String streetAddressAr;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "city")
    private String city;

    @Column(name = "city_fr")
    private String cityFr;

    @Column(name = "city_ar")
    private String cityAr;

    @Column(name = "state_province")
    private String stateProvince;

    @Column(name = "state_province_fr")
    private String stateProvinceFr;

    @Column(name = "state_province_ar")
    private String stateProvinceAr;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "country_name_fr")
    private String countryNameFr;

    @Column(name = "country_name_ar")
    private String countryNameAr;


    // jhipster-needle-entity-add-field - JHipster will add fields here
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLocalNumber() {
        return localNumber;
    }

    public Location localNumber(String localNumber) {
        this.localNumber = localNumber;
        return this;
    }

    public void setLocalNumber(String localNumber) {
        this.localNumber = localNumber;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public Location streetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }



    public String getStreetAddressFr() {
        return streetAddressFr;
    }

    public Location streetAddressFr(String streetAddressFr) {
        this.streetAddressFr = streetAddressFr;
        return this;
    }

    public void setStreetAddressFr(String streetAddressFr) {
        this.streetAddressFr = streetAddressFr;
    }



    public String getStreetAddressAr() {
        return streetAddressAr;
    }

    public Location streetAddressAr(String streetAddressAr) {
        this.streetAddressAr = streetAddressAr;
        return this;
    }

    public void setStreetAddressAr(String streetAddressAr) {
        this.streetAddressAr = streetAddressAr;
    }



    public String getPostalCode() {
        return postalCode;
    }

    public Location postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public Location city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }



    public String getCityFr() {
        return cityFr;
    }

    public Location cityFr(String cityFr) {
        this.cityFr = cityFr;
        return this;
    }

    public void setCityFr(String cityFr) {
        this.cityFr = cityFr;
    }


    public String getCityAr() {
        return cityAr;
    }

    public Location cityAr(String cityAr) {
        this.cityAr = cityAr;
        return this;
    }

    public void setCityAr(String cityAr) {
        this.cityAr = cityAr;
    }



    public String getStateProvince() {
        return stateProvince;
    }

    public Location stateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
        return this;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }



    public String getStateProvinceFr() {
        return stateProvinceFr;
    }

    public Location stateProvinceFr(String stateProvinceFr) {
        this.stateProvinceFr = stateProvinceFr;
        return this;
    }

    public void setStateProvinceFr(String stateProvinceFr) {
        this.stateProvinceFr = stateProvinceFr;
    }



    public String getStateProvinceAr() {
        return stateProvinceAr;
    }

    public Location stateProvinceAr(String stateProvinceAr) {
        this.stateProvinceAr = stateProvinceAr;
        return this;
    }

    public void setStateProvinceAr(String stateProvinceAr) {
        this.stateProvinceAr = stateProvinceAr;
    }

    public String getCountryName() {
        return countryName;
    }

    public Location countryName(String countryName) {
        this.countryName = countryName;
        return this;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }


    public String getCountryNameAr() {
        return countryNameAr;
    }

    public Location countryNameAr(String countryNameAr) {
        this.countryNameAr = countryNameAr;
        return this;
    }

    public void setCountryNameAr(String countryNameAr) {
        this.countryNameAr = countryNameAr;
    }


    public String getCountryNameFr() {
        return countryNameFr;
    }

    public Location countryNameFr(String countryNameFr) {
        this.countryNameFr = countryNameFr;
        return this;
    }

    public void setCountryNameFr(String countryNameFr) {
        this.countryNameFr = countryNameFr;
    }



    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Location)) {
            return false;
        }
        return id != null && id.equals(((Location) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Location{" +
            "id=" + getId() +
            ", localNumber='" + getLocalNumber() + "'" +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", city='" + getCity() + "'" +
            ", stateProvince='" + getStateProvince() + "'" +
            ", countryName='" + getCountryName() + "'" +
            "}";
    }
}
