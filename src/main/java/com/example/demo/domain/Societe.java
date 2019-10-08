package com.example.demo.domain;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Societe.
 */
@Entity
@Table(name = "societe")
public class Societe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "idsociete")
    private Integer idsociete;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "adresse")
    private String adresse;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdsociete() {
        return idsociete;
    }

    public Societe idsociete(Integer idsociete) {
        this.idsociete = idsociete;
        return this;
    }

    public void setIdsociete(Integer idsociete) {
        this.idsociete = idsociete;
    }

    public String getLibelle() {
        return libelle;
    }

    public Societe libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getAdresse() {
        return adresse;
    }

    public Societe adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Societe)) {
            return false;
        }
        return id != null && id.equals(((Societe) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Societe{" +
            "id=" + getId() +
            ", idsociete=" + getIdsociete() +
            ", libelle='" + getLibelle() + "'" +
            ", adresse='" + getAdresse() + "'" +
            "}";
    }
}
