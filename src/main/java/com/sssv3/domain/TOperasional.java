package com.sssv3.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A TOperasional.
 */
@Entity
@Table(name = "t_operasional")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TOperasional implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tanggal")
    private LocalDate tanggal;

    @Column(name = "nominal")
    private Double nominal;

    @Column(name = "created_on")
    private LocalDate createdOn;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User createdby;

    @ManyToOne
    @JsonIgnoreProperties("toperasionals")
    private MOperasionalType moperasionaltype;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public TOperasional tanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
        return this;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public Double getNominal() {
        return nominal;
    }

    public TOperasional nominal(Double nominal) {
        this.nominal = nominal;
        return this;
    }

    public void setNominal(Double nominal) {
        this.nominal = nominal;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public TOperasional createdOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public User getCreatedby() {
        return createdby;
    }

    public TOperasional createdby(User user) {
        this.createdby = user;
        return this;
    }

    public void setCreatedby(User user) {
        this.createdby = user;
    }

    public MOperasionalType getMoperasionaltype() {
        return moperasionaltype;
    }

    public TOperasional moperasionaltype(MOperasionalType mOperasionalType) {
        this.moperasionaltype = mOperasionalType;
        return this;
    }

    public void setMoperasionaltype(MOperasionalType mOperasionalType) {
        this.moperasionaltype = mOperasionalType;
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
        TOperasional tOperasional = (TOperasional) o;
        if (tOperasional.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tOperasional.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TOperasional{" +
            "id=" + getId() +
            ", tanggal='" + getTanggal() + "'" +
            ", nominal=" + getNominal() +
            ", createdOn='" + getCreatedOn() + "'" +
            "}";
    }
}
