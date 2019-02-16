package com.sssv3.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.sssv3.domain.enumeration.Status;

/**
 * A MLogCategory.
 */
@Entity
@Table(name = "m_log_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MLogCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Column(name = "diameter_1", nullable = false)
    private Double diameter1;

    @NotNull
    @Column(name = "diameter_2", nullable = false)
    private Double diameter2;

    @NotNull
    @Column(name = "harga_beli", nullable = false)
    private Double hargaBeli;

    @Column(name = "harga_jual")
    private Double hargaJual;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "created_on")
    private LocalDate createdOn;

    @OneToMany(mappedBy = "mlogcat")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MLog> mlogs = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("")
    private User createdby;

    @ManyToOne
    @JsonIgnoreProperties("mlogs")
    private MLogType mlogtype;

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

    public MLogCategory nama(String nama) {
        this.nama = nama;
        return this;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Double getDiameter1() {
        return diameter1;
    }

    public MLogCategory diameter1(Double diameter1) {
        this.diameter1 = diameter1;
        return this;
    }

    public void setDiameter1(Double diameter1) {
        this.diameter1 = diameter1;
    }

    public Double getDiameter2() {
        return diameter2;
    }

    public MLogCategory diameter2(Double diameter2) {
        this.diameter2 = diameter2;
        return this;
    }

    public void setDiameter2(Double diameter2) {
        this.diameter2 = diameter2;
    }

    public Double getHargaBeli() {
        return hargaBeli;
    }

    public MLogCategory hargaBeli(Double hargaBeli) {
        this.hargaBeli = hargaBeli;
        return this;
    }

    public void setHargaBeli(Double hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public Double getHargaJual() {
        return hargaJual;
    }

    public MLogCategory hargaJual(Double hargaJual) {
        this.hargaJual = hargaJual;
        return this;
    }

    public void setHargaJual(Double hargaJual) {
        this.hargaJual = hargaJual;
    }

    public Status getStatus() {
        return status;
    }

    public MLogCategory status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public MLogCategory createdOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public Set<MLog> getMlogs() {
        return mlogs;
    }

    public MLogCategory mlogs(Set<MLog> mLogs) {
        this.mlogs = mLogs;
        return this;
    }

    public MLogCategory addMlog(MLog mLog) {
        this.mlogs.add(mLog);
        mLog.setMlogcat(this);
        return this;
    }

    public MLogCategory removeMlog(MLog mLog) {
        this.mlogs.remove(mLog);
        mLog.setMlogcat(null);
        return this;
    }

    public void setMlogs(Set<MLog> mLogs) {
        this.mlogs = mLogs;
    }

    public User getCreatedby() {
        return createdby;
    }

    public MLogCategory createdby(User user) {
        this.createdby = user;
        return this;
    }

    public void setCreatedby(User user) {
        this.createdby = user;
    }

    public MLogType getMlogtype() {
        return mlogtype;
    }

    public MLogCategory mlogtype(MLogType mLogType) {
        this.mlogtype = mLogType;
        return this;
    }

    public void setMlogtype(MLogType mLogType) {
        this.mlogtype = mLogType;
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
        MLogCategory mLogCategory = (MLogCategory) o;
        if (mLogCategory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mLogCategory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MLogCategory{" +
            "id=" + getId() +
            ", nama='" + getNama() + "'" +
            ", diameter1=" + getDiameter1() +
            ", diameter2=" + getDiameter2() +
            ", hargaBeli=" + getHargaBeli() +
            ", hargaJual=" + getHargaJual() +
            ", status='" + getStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            "}";
    }
}
