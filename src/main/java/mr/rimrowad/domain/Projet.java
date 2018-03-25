package mr.rimrowad.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import mr.rimrowad.domain.enumeration.ProjetStatus;

import mr.rimrowad.domain.enumeration.Cible;

import mr.rimrowad.domain.enumeration.TypeProjet;

/**
 * A Projet.
 */
@Entity
@Table(name = "projet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Projet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "details")
    private String details;

    @Lob
    @Column(name = "etudef")
    private byte[] etudef;

    @Column(name = "etudef_content_type")
    private String etudefContentType;

    @Lob
    @Column(name = "etude_2")
    private byte[] etude2;

    @Column(name = "etude_2_content_type")
    private String etude2ContentType;

    @Lob
    @Column(name = "etude_3")
    private byte[] etude3;

    @Column(name = "etude_3_content_type")
    private String etude3ContentType;

    @Column(name = "rendement")
    private Double rendement;

    @Column(name = "budget")
    private Double budget;

    @Column(name = "delai")
    private Integer delai;

    @Column(name = "jhi_date")
    private Instant date;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProjetStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "cible")
    private Cible cible;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private TypeProjet type;

    @ManyToOne
    private Equipe equipe;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Projet title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public Projet details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public byte[] getEtudef() {
        return etudef;
    }

    public Projet etudef(byte[] etudef) {
        this.etudef = etudef;
        return this;
    }

    public void setEtudef(byte[] etudef) {
        this.etudef = etudef;
    }

    public String getEtudefContentType() {
        return etudefContentType;
    }

    public Projet etudefContentType(String etudefContentType) {
        this.etudefContentType = etudefContentType;
        return this;
    }

    public void setEtudefContentType(String etudefContentType) {
        this.etudefContentType = etudefContentType;
    }

    public byte[] getEtude2() {
        return etude2;
    }

    public Projet etude2(byte[] etude2) {
        this.etude2 = etude2;
        return this;
    }

    public void setEtude2(byte[] etude2) {
        this.etude2 = etude2;
    }

    public String getEtude2ContentType() {
        return etude2ContentType;
    }

    public Projet etude2ContentType(String etude2ContentType) {
        this.etude2ContentType = etude2ContentType;
        return this;
    }

    public void setEtude2ContentType(String etude2ContentType) {
        this.etude2ContentType = etude2ContentType;
    }

    public byte[] getEtude3() {
        return etude3;
    }

    public Projet etude3(byte[] etude3) {
        this.etude3 = etude3;
        return this;
    }

    public void setEtude3(byte[] etude3) {
        this.etude3 = etude3;
    }

    public String getEtude3ContentType() {
        return etude3ContentType;
    }

    public Projet etude3ContentType(String etude3ContentType) {
        this.etude3ContentType = etude3ContentType;
        return this;
    }

    public void setEtude3ContentType(String etude3ContentType) {
        this.etude3ContentType = etude3ContentType;
    }

    public Double getRendement() {
        return rendement;
    }

    public Projet rendement(Double rendement) {
        this.rendement = rendement;
        return this;
    }

    public void setRendement(Double rendement) {
        this.rendement = rendement;
    }

    public Double getBudget() {
        return budget;
    }

    public Projet budget(Double budget) {
        this.budget = budget;
        return this;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public Integer getDelai() {
        return delai;
    }

    public Projet delai(Integer delai) {
        this.delai = delai;
        return this;
    }

    public void setDelai(Integer delai) {
        this.delai = delai;
    }

    public Instant getDate() {
        return date;
    }

    public Projet date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public ProjetStatus getStatus() {
        return status;
    }

    public Projet status(ProjetStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ProjetStatus status) {
        this.status = status;
    }

    public Cible getCible() {
        return cible;
    }

    public Projet cible(Cible cible) {
        this.cible = cible;
        return this;
    }

    public void setCible(Cible cible) {
        this.cible = cible;
    }

    public TypeProjet getType() {
        return type;
    }

    public Projet type(TypeProjet type) {
        this.type = type;
        return this;
    }

    public void setType(TypeProjet type) {
        this.type = type;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public Projet equipe(Equipe equipe) {
        this.equipe = equipe;
        return this;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public User getUser() {
        return user;
    }

    public Projet user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Projet projet = (Projet) o;
        if (projet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Projet{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", details='" + getDetails() + "'" +
            ", etudef='" + getEtudef() + "'" +
            ", etudefContentType='" + getEtudefContentType() + "'" +
            ", etude2='" + getEtude2() + "'" +
            ", etude2ContentType='" + getEtude2ContentType() + "'" +
            ", etude3='" + getEtude3() + "'" +
            ", etude3ContentType='" + getEtude3ContentType() + "'" +
            ", rendement=" + getRendement() +
            ", budget=" + getBudget() +
            ", delai=" + getDelai() +
            ", date='" + getDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", cible='" + getCible() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
