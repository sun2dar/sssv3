package com.sssv3.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A TUtang.
 */
@Entity
@Table(name = "t_utang")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TUtang implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nominal")
    private Double nominal;

    @Lob
    @Column(name = "deskripsi")
    private String deskripsi;

    @Column(name = "created_on")
    private LocalDate createdOn;

    @ManyToOne
    @JsonIgnoreProperties("tutangs")
    private MUtang mutang;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getNominal() {
        return nominal;
    }

    public TUtang nominal(Double nominal) {
        this.nominal = nominal;
        return this;
    }

    public void setNominal(Double nominal) {
        this.nominal = nominal;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public TUtang deskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
        return this;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public TUtang createdOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public MUtang getMutang() {
        return mutang;
    }

    public TUtang mutang(MUtang mUtang) {
        this.mutang = mUtang;
        return this;
    }

    public void setMutang(MUtang mUtang) {
        this.mutang = mUtang;
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
        TUtang tUtang = (TUtang) o;
        if (tUtang.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tUtang.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TUtang{" +
            "id=" + getId() +
            ", nominal=" + getNominal() +
            ", deskripsi='" + getDeskripsi() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            "}";
    }
}
