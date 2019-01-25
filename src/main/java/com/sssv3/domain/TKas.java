package com.sssv3.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.sssv3.domain.enumeration.KasType;

/**
 * A TKas.
 */
@Entity
@Table(name = "t_kas")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TKas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipekas")
    private KasType tipekas;

    @Column(name = "nominal")
    private Double nominal;

    @Lob
    @Column(name = "deskripsi")
    private String deskripsi;

    @Column(name = "objectid")
    private Long objectid;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public KasType getTipekas() {
        return tipekas;
    }

    public TKas tipekas(KasType tipekas) {
        this.tipekas = tipekas;
        return this;
    }

    public void setTipekas(KasType tipekas) {
        this.tipekas = tipekas;
    }

    public Double getNominal() {
        return nominal;
    }

    public TKas nominal(Double nominal) {
        this.nominal = nominal;
        return this;
    }

    public void setNominal(Double nominal) {
        this.nominal = nominal;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public TKas deskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
        return this;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Long getObjectid() {
        return objectid;
    }

    public TKas objectid(Long objectid) {
        this.objectid = objectid;
        return this;
    }

    public void setObjectid(Long objectid) {
        this.objectid = objectid;
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
        TKas tKas = (TKas) o;
        if (tKas.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tKas.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TKas{" +
            "id=" + getId() +
            ", tipekas='" + getTipekas() + "'" +
            ", nominal=" + getNominal() +
            ", deskripsi='" + getDeskripsi() + "'" +
            ", objectid=" + getObjectid() +
            "}";
    }
}
