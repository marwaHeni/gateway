package com.mapsit.jamaity.imageAssociation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mapsit.jamaity.association.Association;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * ImageAssociation entity.\n@author The Moo3in team.
 */
@ApiModel(description = "ImageAssociation entity.\n@author The Moo3in team.")
@Entity
@Table(name = "image_association")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ImageAssociation implements Serializable {

    private static final long serialVersionUID = 1L;

//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
//    @SequenceGenerator(name = "sequenceGenerator")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;



    @Column(name = "url")
    private String url;


   //@Column(name = "avatar", nullable = false, length = 5000)
  //  @Column(name = "avatar", insertable = false, updatable = false)
    //base64
    @Transient
    private String avatar;

    @JsonBackReference
    @ManyToOne
    @JsonIgnoreProperties(value = "imageAssociations", allowSetters = true)
    private Association association;



    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ImageAssociation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }



    public Association getAssociation() {
        return association;
    }

    public ImageAssociation association(Association association) {
        this.association = association;
        return this;
    }

    public void setAssociation(Association association) {
        this.association = association;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public String getUrl() {
        return url;
    }

    public ImageAssociation url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImageAssociation)) {
            return false;
        }
        return id != null && id.equals(((ImageAssociation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImageAssociation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", avatar='" + getAvatar() + "'" +
            "}";
    }
}
