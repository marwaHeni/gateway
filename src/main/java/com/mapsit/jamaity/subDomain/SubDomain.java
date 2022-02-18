package com.mapsit.jamaity.subDomain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mapsit.jamaity.association.Association;
import com.mapsit.jamaity.domain.Domain;
import com.mapsit.jamaity.ptf.Ptf;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A Delegations.
 */
@Entity
@Table(name = "sub_domain")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SubDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "name_fr")
    private String nameFr;

    @Column(name = "name_ar")
    private String nameAr;

    @Column(name = "name_en")
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


    @ManyToOne
    @JsonIgnoreProperties(value = "subDomains", allowSetters = true)
    private Domain domain;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "subDomains")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = "subDomains", allowSetters = true)
    private Set<Association> associations = new HashSet<>();


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "subDomains")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = "subDomains", allowSetters = true)
    private Set<Ptf> ptfs = new HashSet<>();


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public SubDomain name(String name) {
        this.name = name;
        return this;
    }
    public void setName(String name) {
        this.name = name;
    }



    public String getNameAr() {
        return nameAr;
    }

    public SubDomain nameAr(String nameAr) {
        this.nameAr = nameAr;
        return this;
    }
    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }



    public String getNameFr() {
        return nameFr;
    }

    public SubDomain nameFr(String nameFr) {
        this.nameFr = nameFr;
        return this;
    }
    public void setNameFr(String nameFr) {
        this.nameFr = nameFr;
    }


    public String getNameEn() {
        return nameEn;
    }

    public SubDomain nameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }



    public String getSlug() {
        return slug;
    }

    public SubDomain slug(String slug) {
        this.slug = slug;
        return this;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }


    public String getDescriptionAr() {
        return descriptionAr;
    }

    public SubDomain descriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
        return this;
    }

    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }



    public String getDescriptionFr() {
        return descriptionFr;
    }

    public SubDomain descriptionFr(String descriptionFr) {
        this.descriptionFr = descriptionFr;
        return this;
    }

    public void setDescriptionFr(String descriptionFr) {
        this.descriptionFr = descriptionFr;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public SubDomain descriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
        return this;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }


    public String getDescription() {
        return description;
    }

    public SubDomain description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public Domain getDomain() {
        return domain;
    }

    public SubDomain domain(Domain domain) {
        this.domain = domain;
        return this;
    }
    public void setDomain(Domain domain) {
        this.domain = domain;
    }



    public Set<Association> getAssociations() {
        return associations;
    }

    public SubDomain associations(Set<Association> associations) {
        this.associations = associations;
        return this;
    }

    public SubDomain addAssociation(Association association) {
        this.associations.add(association);
        association.getSubDomains().add(this);
        return this;
    }

    public SubDomain removeAssociation(Association association) {
        this.associations.remove(association);
        association.getSubDomains().remove(this);
        return this;
    }

    public void setAssociations(Set<Association> associations) {
        this.associations = associations;
    }





    public Set<Ptf> getPtfs() {
        return ptfs;
    }

    public SubDomain ptfs(Set<Ptf> ptfs) {
        this.ptfs = ptfs;
        return this;
    }

    public SubDomain addPtf(Ptf ptf) {
        this.ptfs.add(ptf);
        ptf.getSubDomains().add(this);
        return this;
    }

    public SubDomain removePtf(Ptf ptf) {
        this.ptfs.remove(ptf);
        ptf.getSubDomains().remove(this);
        return this;
    }

    public void setPtfs(Set<Ptf> ptfs) {
        this.ptfs = ptfs;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubDomain)) {
            return false;
        }
        return id != null && id.equals(((SubDomain) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SubDomain{" +
                "id=" + getId() +
                ", nameAr='" + getNameAr() + "'" +
                "}";
    }
}
