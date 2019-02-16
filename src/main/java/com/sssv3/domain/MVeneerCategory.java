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

import com.sssv3.domain.enumeration.VeneerType;

import com.sssv3.domain.enumeration.VeneerSubType;

import com.sssv3.domain.enumeration.Status;

/**
 * A MVeneerCategory.
 */
@Entity
@Table(name = "m_veneer_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MVeneerCategory implements Serializable {

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

    @Column(name = "tebal")
    private Double tebal;

    @Column(name = "panjang")
    private Double panjang;

    @Column(name = "lebar")
    private Double lebar;

    @Column(name = "harga_beli")
    private Double hargaBeli;

    @Column(name = "harga_jual")
    private Double hargaJual;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private VeneerType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "subtype")
    private VeneerSubType subtype;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "created_on")
    private LocalDate createdOn;

    @OneToMany(mappedBy = "veneercategory")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MVeneer> mveneers = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("")
    private User createdby;

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

    public MVeneerCategory nama(String nama) {
        this.nama = nama;
        return this;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public MVeneerCategory deskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
        return this;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Double getTebal() {
        return tebal;
    }

    public MVeneerCategory tebal(Double tebal) {
        this.tebal = tebal;
        return this;
    }

    public void setTebal(Double tebal) {
        this.tebal = tebal;
    }

    public Double getPanjang() {
        return panjang;
    }

    public MVeneerCategory panjang(Double panjang) {
        this.panjang = panjang;
        return this;
    }

    public void setPanjang(Double panjang) {
        this.panjang = panjang;
    }

    public Double getLebar() {
        return lebar;
    }

    public MVeneerCategory lebar(Double lebar) {
        this.lebar = lebar;
        return this;
    }

    public void setLebar(Double lebar) {
        this.lebar = lebar;
    }

    public Double getHargaBeli() {
        return hargaBeli;
    }

    public MVeneerCategory hargaBeli(Double hargaBeli) {
        this.hargaBeli = hargaBeli;
        return this;
    }

    public void setHargaBeli(Double hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public Double getHargaJual() {
        return hargaJual;
    }

    public MVeneerCategory hargaJual(Double hargaJual) {
        this.hargaJual = hargaJual;
        return this;
    }

    public void setHargaJual(Double hargaJual) {
        this.hargaJual = hargaJual;
    }

    public VeneerType getType() {
        return type;
    }

    public MVeneerCategory type(VeneerType type) {
        this.type = type;
        return this;
    }

    public void setType(VeneerType type) {
        this.type = type;
    }

    public VeneerSubType getSubtype() {
        return subtype;
    }

    public MVeneerCategory subtype(VeneerSubType subtype) {
        this.subtype = subtype;
        return this;
    }

    public void setSubtype(VeneerSubType subtype) {
        this.subtype = subtype;
    }

    public Status getStatus() {
        return status;
    }

    public MVeneerCategory status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public MVeneerCategory createdOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public Set<MVeneer> getMveneers() {
        return mveneers;
    }

    public MVeneerCategory mveneers(Set<MVeneer> mVeneers) {
        this.mveneers = mVeneers;
        return this;
    }

    public MVeneerCategory addMveneer(MVeneer mVeneer) {
        this.mveneers.add(mVeneer);
        mVeneer.setVeneercategory(this);
        return this;
    }

    public MVeneerCategory removeMveneer(MVeneer mVeneer) {
        this.mveneers.remove(mVeneer);
        mVeneer.setVeneercategory(null);
        return this;
    }

    public void setMveneers(Set<MVeneer> mVeneers) {
        this.mveneers = mVeneers;
    }

    public User getCreatedby() {
        return createdby;
    }

    public MVeneerCategory createdby(User user) {
        this.createdby = user;
        return this;
    }

    public void setCreatedby(User user) {
        this.createdby = user;
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
        MVeneerCategory mVeneerCategory = (MVeneerCategory) o;
        if (mVeneerCategory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mVeneerCategory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MVeneerCategory{" +
            "id=" + getId() +
            ", nama='" + getNama() + "'" +
            ", deskripsi='" + getDeskripsi() + "'" +
            ", tebal=" + getTebal() +
            ", panjang=" + getPanjang() +
            ", lebar=" + getLebar() +
            ", hargaBeli=" + getHargaBeli() +
            ", hargaJual=" + getHargaJual() +
            ", type='" + getType() + "'" +
            ", subtype='" + getSubtype() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            "}";
    }
}
