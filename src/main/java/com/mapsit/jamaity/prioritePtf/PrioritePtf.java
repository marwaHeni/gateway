package com.mapsit.jamaity.prioritePtf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mapsit.jamaity.ptf.Ptf;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * A Delegations.
 */
@Entity
@Table(name = "priorite_ptf")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PrioritePtf implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "theme_prioritaire")
    private String themePrioritaire;

    @Column(name = "theme_prioritaire_fr")
    private String themePrioritaireFr;

    @Column(name = "theme_prioritaire_ar")
    private String themePrioritaireAr;

    @Column(name = "theme_prioritaire_en")
    private String themePrioritaireEn;



    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonIgnoreProperties(value = "prioritePtfs", allowSetters = true)
    private Ptf ptf;



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public String getThemePrioritaire() {
        return themePrioritaire;
    }

    public PrioritePtf themePrioritaire(String themePrioritaire) {
        this.themePrioritaire = themePrioritaire;
        return this;
    }
    public void setThemePrioritaire(String themePrioritaire) {
        this.themePrioritaire = themePrioritaire;
    }



    public String getThemePrioritaireAr() {
        return themePrioritaireAr;
    }

    public PrioritePtf themePrioritaireAr(String themePrioritaireAr) {
        this.themePrioritaireAr = themePrioritaireAr;
        return this;
    }
    public void setThemePrioritaireAr(String themePrioritaireAr) {
        this.themePrioritaireAr = themePrioritaireAr;
    }



    public String getThemePrioritaireFr() {
        return themePrioritaireFr;
    }

    public PrioritePtf themePrioritaireFr(String themePrioritaireFr) {
        this.themePrioritaireFr = themePrioritaireFr;
        return this;
    }
    public void setThemePrioritaireFr(String themePrioritaireFr) {
        this.themePrioritaireFr = themePrioritaireFr;
    }


    public String getThemePrioritaireEn() {
        return themePrioritaireEn;
    }

    public PrioritePtf themePrioritaireEn(String themePrioritaireEn) {
        this.themePrioritaireEn = themePrioritaireEn;
        return this;
    }
    public void setThemePrioritaireEn(String themePrioritaireEn) {
        this.themePrioritaireEn = themePrioritaireEn;
    }

    public Ptf getPtf() {
        return ptf;
    }

    public PrioritePtf ptf(Ptf ptf) {
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
        if (!(o instanceof PrioritePtf)) {
            return false;
        }
        return id != null && id.equals(((PrioritePtf) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PrioritetPtf{" +
                "id=" + getId() +
                ", themePrioritaire='" + getThemePrioritaire() + "'" +
                "}";
    }
}
