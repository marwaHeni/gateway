package com.mapsit.jamaity.ptf;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mapsit.jamaity.contactPtf.ContactPtf;
import com.mapsit.jamaity.location.Location;
import com.mapsit.jamaity.etiquette.Etiquette;
import com.mapsit.jamaity.prioritePtf.PrioritePtf;
import com.mapsit.jamaity.subDomain.SubDomain;
import com.mapsit.jamaity.typePtf.TypePtf;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "ptf")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ptf implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "title_fr")
    private String titleFr;

    @Column(name = "title_ar")
    private String titleAr;

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

    @Column(name = "couleur")
    private String couleur;

    @Column(name = "mission", columnDefinition = "text")
    private String mission;

    @Column(name = "mission_ar", columnDefinition = "text")
    private String missionAr;

    @Column(name = "mission_fr", columnDefinition = "text")
    private String missionFr;

    @Column(name = "mission_en", columnDefinition = "text")
    private String missionEn;

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


    @Column(name = "image")
    private String image;

    @Transient
    private String avatar;


    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(unique = true)
    private Location location;


    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "ptf_sub_domain",
            joinColumns = @JoinColumn(name = "ptf_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "subDomain_id", referencedColumnName = "id"))
    private Set<SubDomain> subDomains = new HashSet<>();


    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "ptf_etiquette",
            joinColumns = @JoinColumn(name = "ptf_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "etiquette_id", referencedColumnName = "id"))
    private Set<Etiquette> etiquettes = new HashSet<>();


    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "ptf_type",
            joinColumns = @JoinColumn(name = "ptf_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "type_id", referencedColumnName = "id"))
    private Set<TypePtf> typePtfs = new HashSet<>();


    @OneToMany(cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinColumn(name = "ptf_id")
    private Set<ContactPtf> contactPtfs = new HashSet<>();


    @OneToMany(cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinColumn(name = "ptf_id")
    private Set<PrioritePtf> prioritePtfs = new HashSet<>();




    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescriptionAr() {
        return descriptionAr;
    }

    public Ptf descriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
        return this;
    }

    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }



    public String getDescriptionFr() {
        return descriptionFr;
    }

    public Ptf descriptionFr(String descriptionFr) {
        this.descriptionFr = descriptionFr;
        return this;
    }

    public void setDescriptionFr(String descriptionFr) {
        this.descriptionFr = descriptionFr;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public Ptf descriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
        return this;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }


    public String getDescription() {
        return description;
    }

    public Ptf description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public Location getLocation() {
        return location;
    }

    public Ptf location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
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


    public Set<SubDomain> getSubDomains() {
        return subDomains;
    }

    public Ptf subDomains(Set<SubDomain> subDomains) {
        this.subDomains = subDomains;
        return this;
    }

    public Ptf addSubDomain(SubDomain subDomain) {
        this.subDomains.add(subDomain);
        subDomain.getPtfs().add(this);
        return this;
    }

    public Ptf removeSubDomain(SubDomain subDomain) {
        this.subDomains.remove(subDomain);
        subDomain.getPtfs().remove(this);
        return this;
    }

    public void setSubDomains(Set<SubDomain> subDomains) {
        this.subDomains = subDomains;
    }


    public Set<Etiquette> getEtiquettes() {
        return etiquettes;
    }

    public Ptf etiquettes(Set<Etiquette> etiquettes) {
        this.etiquettes = etiquettes;
        return this;
    }

    public Ptf addEtiquette(Etiquette etiquette) {
        this.etiquettes.add(etiquette);
        etiquette.getPtfs().add(this);
        return this;
    }

    public Ptf removeEtiquette(Etiquette etiquette) {
        this.etiquettes.remove(etiquette);
        etiquette.getPtfs().remove(this);
        return this;
    }

    public void setEtiquettes(Set<Etiquette> etiquettes) {
        this.etiquettes = etiquettes;
    }



    public Set<TypePtf> getTypePtfs() {
        return typePtfs;
    }

    public Ptf typePtfs(Set<TypePtf> typePtfs) {
        this.typePtfs = typePtfs;
        return this;
    }

    public Ptf addTypePtf(TypePtf typePtf) {
        this.typePtfs.add(typePtf);
        typePtf.getPtfs().add(this);
        return this;
    }

    public Ptf removeTypePtf(TypePtf typePtf) {
        this.typePtfs.remove(typePtf);
        typePtf.getPtfs().remove(this);
        return this;
    }

    public void setTypePtfs(Set<TypePtf> typePtfs) {
        this.typePtfs = typePtfs;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getMissionAr() {
        return missionAr;
    }

    public void setMissionAr(String missionAr) {
        this.missionAr = missionAr;
    }

    public String getMissionFr() {
        return missionFr;
    }

    public void setMissionFr(String missionFr) {
        this.missionFr = missionFr;
    }

    public String getMissionEn() {
        return missionEn;
    }

    public void setMissionEn(String missionEn) {
        this.missionEn = missionEn;
    }


    public Set<ContactPtf> getContactPtfs() {
        return contactPtfs;
    }

    public Ptf contactPtfs(Set<ContactPtf> contactPtfs) {
        this.contactPtfs = contactPtfs;
        return this;
    }

    public Ptf addContactPtf(ContactPtf contactPtf) {
        this.contactPtfs.add(contactPtf);
        contactPtf.setPtf(this);
        return this;
    }

    public Ptf removeContactPtf(ContactPtf contactPtf) {
        this.contactPtfs.remove(contactPtf);
        contactPtf.setPtf(null);
        return this;
    }
    public void setContactPtfs(Set<ContactPtf> contactPtfs) {
        this.contactPtfs = contactPtfs;
    }




    public Set<PrioritePtf> getPrioritetPtfs() {
        return prioritePtfs;
    }

    public Ptf prioritetPtfs(Set<PrioritePtf> prioritePtfs) {
        this.prioritePtfs = prioritePtfs;
        return this;
    }

    public Ptf addPrioritetPtf(PrioritePtf prioritePtf) {
        this.prioritePtfs.add(prioritePtf);
        prioritePtf.setPtf(this);
        return this;
    }

    public Ptf removePrioritetPtf(PrioritePtf prioritePtf) {
        this.prioritePtfs.remove(prioritePtf);
        prioritePtf.setPtf(null);
        return this;
    }
    public void setPrioritetPtfs(Set<PrioritePtf> prioritePtfs) {
        this.prioritePtfs = prioritePtfs;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ptf)) {
            return false;
        }
        return id != null && id.equals(((Ptf) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Association{" +
                "id=" + getId() +
                ", title='" + getTitle() + "'" +
                "}";
    }
}
