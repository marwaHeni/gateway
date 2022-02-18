package com.mapsit.jamaity.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mapsit.jamaity.association.Association;
import com.mapsit.jamaity.subDomain.SubDomain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "domain")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Domain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "name_fr", unique = true)
    private String nameFr;

    @Column(name = "name_ar", unique = true)
    private String nameAr;

    @Column(name = "name_en", unique = true)
    private String nameEn;


    @Column(name = "slug")
    private String slug;


    @Column(name = "description")
    private String description;

    @Column(name = "description_ar")
    private String descriptionAr;

    @Column(name = "description_fr")
    private String descriptionFr;

    @Column(name = "description_en")
    private String descriptionEn;


    @OneToMany(mappedBy = "domain")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<SubDomain> subDomains = new ArrayList<>();



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public Domain name(String name) {
        this.name = name;
        return this;
    }
    public void setName(String name) {
        this.name = name;
    }



    public String getNameAr() {
        return nameAr;
    }

    public Domain nameAr(String nameAr) {
        this.nameAr = nameAr;
        return this;
    }
    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }



    public String getNameFr() {
        return nameFr;
    }

    public Domain nameFr(String nameFr) {
        this.nameFr = nameFr;
        return this;
    }
    public void setNameFr(String nameFr) {
        this.nameFr = nameFr;
    }


    public String getNameEn() {
        return nameEn;
    }

    public Domain nameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }



    public String getSlug() {
        return slug;
    }

    public Domain slug(String slug) {
        this.slug = slug;
        return this;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }


    public String getDescriptionAr() {
        return descriptionAr;
    }

    public Domain descriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
        return this;
    }

    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }



    public String getDescriptionFr() {
        return descriptionFr;
    }

    public Domain descriptionFr(String descriptionFr) {
        this.descriptionFr = descriptionFr;
        return this;
    }

    public void setDescriptionFr(String descriptionFr) {
        this.descriptionFr = descriptionFr;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public Domain descriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
        return this;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }


    public String getDescription() {
        return description;
    }

    public Domain description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public List<SubDomain> getSubDomains() {
        return subDomains;
    }

    public Domain subDomains(List<SubDomain> subDomains) {
        this.subDomains = subDomains;
        return this;
    }

    public Domain addSubDomain(SubDomain subDomain) {
        this.subDomains.add(subDomain);
        subDomain.setDomain(this);
        return this;
    }

    public Domain removeSubDomain(SubDomain subDomain) {
        this.subDomains.remove(subDomain);
        subDomain.setDomain(null);
        return this;
    }
    public void setSubDomains(List<SubDomain> subDomains) {
        this.subDomains = subDomains;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Domain)) {
            return false;
        }
        return id != null && id.equals(((Domain) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Domain{" +
                "id=" + getId() +
                ", nameAr='" + getNameAr() + "'" +
                "}";
    }
}
