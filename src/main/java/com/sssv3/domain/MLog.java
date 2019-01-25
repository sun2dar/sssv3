package com.sssv3.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.sssv3.domain.enumeration.Status;

/**
 * A MLog.
 */
@Entity
@Table(name = "m_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Column(name = "diameter", nullable = false)
    private Double diameter;

    @NotNull
    @Column(name = "panjang", nullable = false)
    private Double panjang;

    @Column(name = "harga_beli")
    private Double hargaBeli;

    @Column(name = "qty")
    private Double qty;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "created_on")
    private LocalDate createdOn;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User createdby;

    @ManyToOne
    @JsonIgnoreProperties("mlogs")
    private MLogCategory mlogcat;

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

    public MLog nama(String nama) {
        this.nama = nama;
        return this;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Double getDiameter() {
        return diameter;
    }

    public MLog diameter(Double diameter) {
        this.diameter = diameter;
        return this;
    }

    public void setDiameter(Double diameter) {
        this.diameter = diameter;
    }

    public Double getPanjang() {
        return panjang;
    }

    public MLog panjang(Double panjang) {
        this.panjang = panjang;
        return this;
    }

    public void setPanjang(Double panjang) {
        this.panjang = panjang;
    }

    public Double getHargaBeli() {
        return hargaBeli;
    }

    public MLog hargaBeli(Double hargaBeli) {
        this.hargaBeli = hargaBeli;
        return this;
    }

    public void setHargaBeli(Double hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public Double getQty() {
        return qty;
    }

    public MLog qty(Double qty) {
        this.qty = qty;
        return this;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Status getStatus() {
        return status;
    }

    public MLog status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public MLog createdOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public User getCreatedby() {
        return createdby;
    }

    public MLog createdby(User user) {
        this.createdby = user;
        return this;
    }

    public void setCreatedby(User user) {
        this.createdby = user;
    }

    public MLogCategory getMlogcat() {
        return mlogcat;
    }

    public MLog mlogcat(MLogCategory mLogCategory) {
        this.mlogcat = mLogCategory;
        return this;
    }

    public void setMlogcat(MLogCategory mLogCategory) {
        this.mlogcat = mLogCategory;
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
        MLog mLog = (MLog) o;
        if (mLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MLog{" +
            "id=" + getId() +
            ", nama='" + getNama() + "'" +
            ", diameter=" + getDiameter() +
            ", panjang=" + getPanjang() +
            ", hargaBeli=" + getHargaBeli() +
            ", qty=" + getQty() +
            ", status='" + getStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            "}";
    }
}
