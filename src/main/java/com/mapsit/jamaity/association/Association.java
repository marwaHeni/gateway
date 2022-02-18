package com.mapsit.jamaity.association;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mapsit.jamaity.location.Location;
import com.mapsit.jamaity.etiquette.Etiquette;
import com.mapsit.jamaity.imageAssociation.ImageAssociation;
import com.mapsit.jamaity.prixAssociation.PrixAssociation;
import com.mapsit.jamaity.subDomain.SubDomain;
import com.mapsit.jamaity.subStructureAssociation.SubStructureAssociation;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "association")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Association implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "logo")
    private String logo;

    @Column(name = "title")
    private String title;

    @Column(name = "title_fr")
    private String titleFr;

    @Column(name = "title_ar")
    private String titleAr;

    @Column(name = "deux_vis_a_vis_association")
    private String deuxVisaVisAssociation;

    @Column(name = "deux_vis_a_vis_association_fr")
    private String deuxVisaVisAssociationFr;

    @Column(name = "deux_vis_a_vis_association_ar")
    private String deuxVisaVisAssociationAr;

    @Column(name = "abreviation")
    private String abreviation;

    @Column(name = "abreviation_fr")
    private String abreviationFr;

    @Column(name = "abreviation_ar")
    private String abreviationAr;

    @Column(name = "annee_fondation")
    private String anneeFondation;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "description_ar", columnDefinition = "text")
    private String descriptionAr;

    @Column(name = "description_fr", columnDefinition = "text")
    private String descriptionFr;

    @Column(name = "description_en", columnDefinition = "text")
    private String descriptionEn;

    @Column(name = "etat")
    private String etat;

    @Column(name = "visibilite")
    private String visibilite;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @Column(name = "date_publication")
    private Instant datePublication;

    @Column(name = "president")
    private String president;

    @Column(name = "president_ar")
    private String presidentAr;

    @Column(name = "president_fr")
    private String presidentFr;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email_1")
    private String email;


    @Column(name = "site_web")
    private String siteWeb;


    @Column(name = "phone_fixe")
    private String phoneFixe;

    @Column(name = "phone_mobile")
    private String phoneMobile;

    @Column(name = "fax")
    private String fax;

    @Column(name = "facebook")
    private String facebook;

    @Column(name = "twitter")
    private String twitter;

    @Column(name = "youtube")
    private String youtube;

    @Column(name = "google_plus")
    private String googlePlus;

    @Column(name = "linkedin")
    private String linkedin;

    @Column(name = "note")
    private String note;

    @Column(name = "user_login")
    private String userLogin;

    @Column(name = "user_name")
    private String userName;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @Column(name = "date_jort")
    private Instant dateJort;

    @Column(name = "num_jort")
    private String numJort;

    @Column(name = "ordre")
    private String ordre;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "is_en_avant")
    private Boolean isEnAvant;

    @Column(name = "is_en_valeur")
    private Boolean isEnValeur;

    @Column(name = "is_coworking_space")
    private Boolean isCoworkingSpace;

    @Column(name = "is_allow_comment")
    private Boolean isAllowComment;

    @Column(name = "is_allow_retrolien")
    private Boolean isAllowRetrolien;

    @Transient
    private String avatar;


    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(unique = true)
    private Location location;


    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "association_sub_domain",
            joinColumns = @JoinColumn(name = "association_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "domain_id", referencedColumnName = "id"))
    private Set<SubDomain> subDomains = new HashSet<>();




    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "association_sub_structure",
            joinColumns = @JoinColumn(name = "association_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "sub_structure_id", referencedColumnName = "id"))
    private Set<SubStructureAssociation> subStructureAssociations = new HashSet<>();



    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "association_etiquette",
            joinColumns = @JoinColumn(name = "association_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "etiquette_id", referencedColumnName = "id"))
    private Set<Etiquette> etiquettes = new HashSet<>();


    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "association_prix",
            joinColumns = @JoinColumn(name = "association_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "prix_association_id", referencedColumnName = "id"))
    private Set<PrixAssociation> prixAssociations = new HashSet<>();



    @ManyToOne
    @JsonIgnoreProperties(value = "associations", allowSetters = true)
    private Association associationParent;


    @OneToMany(mappedBy = "associationParent")
   @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Association> associationChilds = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "association", orphanRemoval = true)
    @JsonIgnoreProperties(value = "associations", allowSetters = true)
    private Set<ImageAssociation> imageAssociations = new HashSet<ImageAssociation>();


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescriptionAr() {
        return descriptionAr;
    }

    public Association descriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
        return this;
    }

    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }



    public String getDescriptionFr() {
        return descriptionFr;
    }

    public Association descriptionFr(String descriptionFr) {
        this.descriptionFr = descriptionFr;
        return this;
    }

    public void setDescriptionFr(String descriptionFr) {
        this.descriptionFr = descriptionFr;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public Association descriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
        return this;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }


    public String getDescription() {
        return description;
    }

    public Association description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public Location getLocation() {
        return location;
    }

    public Association location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAbreviation() {
        return abreviation;
    }

    public void setAbreviation(String abreviation) {
        this.abreviation = abreviation;
    }

    public String getAbreviationFr() {
        return abreviationFr;
    }

    public void setAbreviationFr(String abreviationFr) {
        this.abreviationFr = abreviationFr;
    }

    public String getAbreviationAr() {
        return abreviationAr;
    }

    public void setAbreviationAr(String abreviationAr) {
        this.abreviationAr = abreviationAr;
    }

    public String getAnneeFondation() {
        return anneeFondation;
    }

    public void setAnneeFondation(String anneeFondation) {
        this.anneeFondation = anneeFondation;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getVisibilite() {
        return visibilite;
    }

    public void setVisibilite(String visibilite) {
        this.visibilite = visibilite;
    }

    public Instant getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(Instant datePublication) {
        this.datePublication = datePublication;
    }

    public String getPresident() {
        return president;
    }

    public void setPresident(String president) {
        this.president = president;
    }

    public String getPresidentAr() {
        return presidentAr;
    }

    public void setPresidentAr(String presidentAr) {
        this.presidentAr = presidentAr;
    }

    public String getPresidentFr() {
        return presidentFr;
    }

    public void setPresidentFr(String presidentFr) {
        this.presidentFr = presidentFr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSiteWeb() {
        return siteWeb;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    public String getPhoneFixe() {
        return phoneFixe;
    }

    public void setPhoneFixe(String phoneFixe) {
        this.phoneFixe = phoneFixe;
    }

    public String getPhoneMobile() {
        return phoneMobile;
    }

    public void setPhoneMobile(String phoneMobile) {
        this.phoneMobile = phoneMobile;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getGooglePlus() {
        return googlePlus;
    }

    public void setGooglePlus(String googlePlus) {
        this.googlePlus = googlePlus;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Instant getDateJort() {
        return dateJort;
    }

    public void setDateJort(Instant dateJort) {
        this.dateJort = dateJort;
    }

    public String getNumJort() {
        return numJort;
    }

    public void setNumJort(String numJort) {
        this.numJort = numJort;
    }

    public String getOrdre() {
        return ordre;
    }

    public void setOrdre(String ordre) {
        this.ordre = ordre;
    }


    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public Boolean getIsEnAvant() {
        return isEnAvant;
    }

    public void setIsEnAvant(Boolean isEnAvant) {
        this.isEnAvant = isEnAvant;
    }

    public Boolean getIsEnValeur() {
        return isEnValeur;
    }

    public void setIsEnValeur(Boolean isEnValeur) {
        this.isEnValeur = isEnValeur;
    }

    public Boolean getIsCoworkingSpace() {
        return isCoworkingSpace;
    }

    public void setIsCoworkingSpace(Boolean isCoworkingSpace) {
        this.isCoworkingSpace = isCoworkingSpace;
    }

    public Boolean getIsAllowComment() {
        return isAllowComment;
    }

    public void setIsAllowComment(Boolean isAllowComment) {
        this.isAllowComment = isAllowComment;
    }

    public Boolean getIsAllowRetrolien() {
        return isAllowRetrolien;
    }

    public void setIsAllowRetrolien(Boolean isAllowRetrolien) {
        this.isAllowRetrolien = isAllowRetrolien;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleFr() {
        return titleFr;
    }

    public void setTitleFr(String titleFr) {
        this.titleFr = titleFr;
    }

    public String getTitleAr() {
        return titleAr;
    }

    public void setTitleAr(String titleAr) {
        this.titleAr = titleAr;
    }

    public String getDeuxVisaVisAssociation() {
        return deuxVisaVisAssociation;
    }

    public void setDeuxVisaVisAssociation(String deuxVisaVisAssociation) {
        this.deuxVisaVisAssociation = deuxVisaVisAssociation;
    }

    public String getDeuxVisaVisAssociationFr() {
        return deuxVisaVisAssociationFr;
    }

    public void setDeuxVisaVisAssociationFr(String deuxVisaVisAssociationFr) {
        this.deuxVisaVisAssociationFr = deuxVisaVisAssociationFr;
    }

    public String getDeuxVisaVisAssociationAr() {
        return deuxVisaVisAssociationAr;
    }

    public void setDeuxVisaVisAssociationAr(String deuxVisaVisAssociationAr) {
        this.deuxVisaVisAssociationAr = deuxVisaVisAssociationAr;
    }



    public Set<SubDomain> getSubDomains() {
        return subDomains;
    }

    public Association subDomains(Set<SubDomain> subDomains) {
        this.subDomains = subDomains;
        return this;
    }

    public Association addSubDomain(SubDomain subDomain) {
        this.subDomains.add(subDomain);
        subDomain.getAssociations().add(this);
        return this;
    }

    public Association removeSubDomain(SubDomain subDomain) {
        this.subDomains.remove(subDomain);
        subDomain.getAssociations().remove(this);
        return this;
    }

    public void setSubDomains(Set<SubDomain> subDomains) {
        this.subDomains = subDomains;
    }





    public Set<SubStructureAssociation> getSubStructureAssociations() {
        return subStructureAssociations;
    }

    public Association subStructureAssociations(Set<SubStructureAssociation> subStructureAssociations) {
        this.subStructureAssociations = subStructureAssociations;
        return this;
    }

    public Association addSubStructureAssociation(SubStructureAssociation subStructureAssociation) {
        this.subStructureAssociations.add(subStructureAssociation);
        subStructureAssociation.getAssociations().add(this);
        return this;
    }

    public Association removeSubStructureAssociation(SubStructureAssociation subStructureAssociation) {
        this.subStructureAssociations.remove(subStructureAssociation);
        subStructureAssociation.getAssociations().remove(this);
        return this;
    }

    public void setSubStructureAssociations(Set<SubStructureAssociation> subStructureAssociations) {
        this.subStructureAssociations = subStructureAssociations;
    }





    public Set<Etiquette> getEtiquettes() {
        return etiquettes;
    }

    public Association etiquettes(Set<Etiquette> etiquettes) {
        this.etiquettes = etiquettes;
        return this;
    }

    public Association addEtiquette(Etiquette etiquette) {
        this.etiquettes.add(etiquette);
        etiquette.getAssociations().add(this);
        return this;
    }

    public Association removeEtiquette(Etiquette etiquette) {
        this.etiquettes.remove(etiquette);
        etiquette.getAssociations().remove(this);
        return this;
    }

    public void setEtiquettes(Set<Etiquette> etiquettes) {
        this.etiquettes = etiquettes;
    }





    public Set<PrixAssociation> getPrixAssociations() {
        return prixAssociations;
    }

    public Association prixAssociations(Set<PrixAssociation> prixAssociations) {
        this.prixAssociations = prixAssociations;
        return this;
    }

    public Association addPrixAssociation(PrixAssociation prixAssociation) {
        this.prixAssociations.add(prixAssociation);
        prixAssociation.getAssociations().add(this);
        return this;
    }

    public Association removePrixAssociation(PrixAssociation prixAssociation) {
        this.prixAssociations.remove(prixAssociation);
        prixAssociation.getAssociations().remove(this);
        return this;
    }

    public void setPrixAssociations(Set<PrixAssociation> prixAssociations) {
        this.prixAssociations = prixAssociations;
    }






    public Set<Association> getAssociationChilds() {
        return associationChilds;
    }

    public Association associations(Set<Association> associationChilds) {
        this.associationChilds = associationChilds;
        return this;
    }

    public Association addAssociationChild(Association associationChild) {
        this.associationChilds.add(associationChild);
        associationChild.getAssociationChilds().add(this);
        return this;
    }

    public Association removeAssociationChild(Association associationChild) {
        this.associationChilds.remove(associationChild);
        associationChild.getAssociationChilds().remove(this);
        return this;
    }

    public void setAssociationChilds(Set<Association> associationChilds) {
        this.associationChilds = associationChilds;
    }



    public Association getAssociationParent() {
        return associationParent;
    }

    public Association associationParent(Association associationParent) {
        this.associationParent = associationParent;
        return this;
    }

    public void setAssociationParent(Association associationParent) {
        this.associationParent = associationParent;
    }



    public Set<ImageAssociation> getImageAssociations() {
        return imageAssociations;
    }

    public Association imageAssociations(Set<ImageAssociation> imageAssociations) {
        this.imageAssociations = imageAssociations;
        return this;
    }

    public Association addImageAssociation(ImageAssociation imageAssociation) {
        this.imageAssociations.add(imageAssociation);
        imageAssociation.setAssociation(this);
        return this;
    }

    public Association removeImageAssociation(ImageAssociation imageAssociation) {
        this.imageAssociations.remove(imageAssociation);
        imageAssociation.setAssociation(null);
        return this;
    }

    public void setImageAssociations(Set<ImageAssociation> imageAssociations) {

         this.imageAssociations = imageAssociations;
       // this.imageItems.addAll(imageItems);
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Association)) {
            return false;
        }
        return id != null && id.equals(((Association) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Association{" +
                "id=" + getId() +
                ", namePresident='" + getPresident() + "'" +
                "}";
    }
}
