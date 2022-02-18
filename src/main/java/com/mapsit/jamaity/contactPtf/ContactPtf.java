package com.mapsit.jamaity.contactPtf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mapsit.jamaity.ptf.Ptf;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A Delegations.
 */
@Entity
@Table(name = "contact_ptf")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ContactPtf implements Serializable {

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

    @Column(name = "post")
    private String post;

    @Column(name = "post_ar")
    private String postAr;

    @Column(name = "post_fr")
    private String postFr;

    @Column(name = "post_en")
    private String postEn;

    @Column(name = "phone")
    private String phone;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email_1")
    private String email;


    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonIgnoreProperties(value = "contactPtfs", allowSetters = true)
    private Ptf ptf;



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public ContactPtf name(String name) {
        this.name = name;
        return this;
    }
    public void setName(String name) {
        this.name = name;
    }



    public String getNameAr() {
        return nameAr;
    }

    public ContactPtf nameAr(String nameAr) {
        this.nameAr = nameAr;
        return this;
    }
    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }



    public String getNameFr() {
        return nameFr;
    }

    public ContactPtf nameFr(String nameFr) {
        this.nameFr = nameFr;
        return this;
    }
    public void setNameFr(String nameFr) {
        this.nameFr = nameFr;
    }


    public String getNameEn() {
        return nameEn;
    }

    public ContactPtf nameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }



    public String getPhone() {
        return phone;
    }

    public ContactPtf phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getEmail() {
        return email;
    }

    public ContactPtf email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPostAr() {
        return postAr;
    }

    public ContactPtf postAr(String postAr) {
        this.postAr = postAr;
        return this;
    }

    public void setPostAr(String postAr) {
        this.postAr = postAr;
    }



    public String getPostFr() {
        return postFr;
    }

    public ContactPtf postFr(String postFr) {
        this.postFr = postFr;
        return this;
    }

    public void setPostFr(String postFr) {
        this.postFr = postFr;
    }


    public String getPostEn() {
        return postEn;
    }

    public ContactPtf postEn(String postEn) {
        this.postEn = postEn;
        return this;
    }

    public void setPostEn(String postEn) {
        this.postEn = postEn;
    }


    public String getPost() {
        return post;
    }

    public ContactPtf post(String post) {
        this.post = post;
        return this;
    }

    public void setPost(String post) {
        this.post = post;
    }



    public Ptf getPtf() {
        return ptf;
    }

    public ContactPtf ptf(Ptf ptf) {
        this.ptf = ptf;
        return this;
    }

    public void setPtf(Ptf ptf) {
        this.ptf = ptf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContactPtf)) {
            return false;
        }
        return id != null && id.equals(((ContactPtf) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ContactPtf{" +
                "id=" + getId() +
                ", nameAr='" + getNameAr() + "'" +
                "}";
    }
}
