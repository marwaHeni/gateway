package com.mapsit.jamaity.subStructureAssociation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mapsit.jamaity.association.Association;
import com.mapsit.jamaity.domain.Domain;
import com.mapsit.jamaity.structureAssociation.StructureAssociation;
import com.mapsit.jamaity.subDomain.SubDomain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "sub_structure_association")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SubStructureAssociation implements Serializable {

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
    @JsonIgnoreProperties(value = "subStructureAssociations", allowSetters = true)
    private StructureAssociation structureAssociation;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "subStructureAssociations")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = "subStructureAssociations", allowSetters = true)
    private Set<Association> associations = new HashSet<>();



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public SubStructureAssociation name(String name) {
        this.name = name;
        return this;
    }
    public void setName(String name) {
        this.name = name;
    }



    public String getNameAr() {
        return nameAr;
    }

    public SubStructureAssociation nameAr(String nameAr) {
        this.nameAr = nameAr;
        return this;
    }
    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }



    public String getNameFr() {
        return nameFr;
    }

    public SubStructureAssociation nameFr(String nameFr) {
        this.nameFr = nameFr;
        return this;
    }
    public void setNameFr(String nameFr) {
        this.nameFr = nameFr;
    }


    public String getNameEn() {
        return nameEn;
    }

    public SubStructureAssociation nameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }



    public String getSlug() {
        return slug;
    }

    public SubStructureAssociation slug(String slug) {
        this.slug = slug;
        return this;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }


    public String getDescriptionAr() {
        return descriptionAr;
    }

    public SubStructureAssociation descriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
        return this;
    }

    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }



    public String getDescriptionFr() {
        return descriptionFr;
    }

    public SubStructureAssociation descriptionFr(String descriptionFr) {
        this.descriptionFr = descriptionFr;
        return this;
    }

    public void setDescriptionFr(String descriptionFr) {
        this.descriptionFr = descriptionFr;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public SubStructureAssociation descriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
        return this;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }


    public String getDescription() {
        return description;
    }

    public SubStructureAssociation description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public StructureAssociation getStructureAssociation() {
        return structureAssociation;
    }

    public SubStructureAssociation domain(StructureAssociation structureAssociation) {
        this.structureAssociation = structureAssociation;
        return this;
    }
    public void setStructureAssociation(StructureAssociation structureAssociation) {
        this.structureAssociation = structureAssociation;
    }



    public Set<Association> getAssociations() {
        return associations;
    }

    public SubStructureAssociation associations(Set<Association> associations) {
        this.associations = associations;
        return this;
    }

    public SubStructureAssociation addAssociation(Association association) {
        this.associations.add(association);
        association.getSubStructureAssociations().add(this);
        return this;
    }

    public SubStructureAssociation removeAssociation(Association association) {
        this.associations.remove(association);
        association.getSubStructureAssociations().remove(this);
        return this;
    }

    public void setAssociations(Set<Association> associations) {
        this.associations = associations;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubStructureAssociation)) {
            return false;
        }
        return id != null && id.equals(((SubStructureAssociation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SubStructureAssociation{" +
                "id=" + getId() +
                ", nameAr='" + getNameAr() + "'" +
                "}";
    }
}
