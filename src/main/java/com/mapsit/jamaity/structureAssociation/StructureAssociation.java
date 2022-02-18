package com.mapsit.jamaity.structureAssociation;

import com.mapsit.jamaity.subDomain.SubDomain;
import com.mapsit.jamaity.subStructureAssociation.SubStructureAssociation;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "structure_association")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StructureAssociation implements Serializable {

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


    @OneToMany(mappedBy = "structureAssociation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<SubStructureAssociation> subStructureAssociations = new ArrayList<>();


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public StructureAssociation name(String name) {
        this.name = name;
        return this;
    }
    public void setName(String name) {
        this.name = name;
    }



    public String getNameAr() {
        return nameAr;
    }

    public StructureAssociation nameAr(String nameAr) {
        this.nameAr = nameAr;
        return this;
    }
    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }



    public String getNameFr() {
        return nameFr;
    }

    public StructureAssociation nameFr(String nameFr) {
        this.nameFr = nameFr;
        return this;
    }
    public void setNameFr(String nameFr) {
        this.nameFr = nameFr;
    }


    public String getNameEn() {
        return nameEn;
    }

    public StructureAssociation nameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }



    public String getSlug() {
        return slug;
    }

    public StructureAssociation slug(String slug) {
        this.slug = slug;
        return this;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }


    public String getDescriptionAr() {
        return descriptionAr;
    }

    public StructureAssociation descriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
        return this;
    }

    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }



    public String getDescriptionFr() {
        return descriptionFr;
    }

    public StructureAssociation descriptionFr(String descriptionFr) {
        this.descriptionFr = descriptionFr;
        return this;
    }

    public void setDescriptionFr(String descriptionFr) {
        this.descriptionFr = descriptionFr;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public StructureAssociation descriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
        return this;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }


    public String getDescription() {
        return description;
    }

    public StructureAssociation description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public List<SubStructureAssociation> getSubStructureAssociations() {
        return subStructureAssociations;
    }

    public StructureAssociation subStructureAssociations(List<SubStructureAssociation> subStructureAssociations) {
        this.subStructureAssociations = subStructureAssociations;
        return this;
    }

    public StructureAssociation addSubSubStructureAssociation(SubStructureAssociation subStructureAssociation) {
        this.subStructureAssociations.add(subStructureAssociation);
        subStructureAssociation.setStructureAssociation(this);
        return this;
    }

    public StructureAssociation removeSubStructureAssociation(SubStructureAssociation subStructureAssociation) {
        this.subStructureAssociations.remove(subStructureAssociation);
        subStructureAssociation.setStructureAssociation(null);
        return this;
    }
    public void setSubStructureAssociations(List<SubStructureAssociation> subStructureAssociations) {
        this.subStructureAssociations = subStructureAssociations;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StructureAssociation)) {
            return false;
        }
        return id != null && id.equals(((StructureAssociation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StructureAssociation{" +
                "id=" + getId() +
                ", nameAr='" + getNameAr() + "'" +
                "}";
    }
}
