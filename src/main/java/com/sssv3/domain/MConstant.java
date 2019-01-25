package com.sssv3.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A MConstant.
 */
@Entity
@Table(name = "m_constant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MConstant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nama", nullable = false)
    private String nama;

    @Lob
    @Column(name = "deskripsi")
    private String deskripsi;

    @NotNull
    @Column(name = "nominal", nullable = false)
    private Double nominal;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public MConstant nama(String nama) {
        this.nama = nama;
        return this;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public MConstant deskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
        return this;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Double getNominal() {
        return nominal;
    }

    public MConstant nominal(Double nominal) {
        this.nominal = nominal;
        return this;
    }

    public void setNominal(Double nominal) {
        this.nominal = nominal;
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
        MConstant mConstant = (MConstant) o;
        if (mConstant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mConstant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MConstant{" +
            "id=" + getId() +
            ", nama='" + getNama() + "'" +
            ", deskripsi='" + getDeskripsi() + "'" +
            ", nominal=" + getNominal() +
            "}";
    }
}
