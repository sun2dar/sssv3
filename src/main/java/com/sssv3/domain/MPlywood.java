package com.sssv3.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.sssv3.domain.enumeration.Status;

/**
 * A MPlywood.
 */
@Entity
@Table(name = "m_plywood")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MPlywood implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "harga_beli")
    private Double hargaBeli;

    @Column(name = "qty")
    private Double qty;

    @Column(name = "qty_produksi")
    private Double qtyProduksi;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "created_on")
    private LocalDate createdOn;

    @OneToMany(mappedBy = "mplywood")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TPlywood> tplywoods = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("")
    private User createdby;

    @ManyToOne
    @JsonIgnoreProperties("mplywoods")
    private MPlywoodCategory plywoodcategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getHargaBeli() {
        return hargaBeli;
    }

    public MPlywood hargaBeli(Double hargaBeli) {
        this.hargaBeli = hargaBeli;
        return this;
    }

    public void setHargaBeli(Double hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public Double getQty() {
        return qty;
    }

    public MPlywood qty(Double qty) {
        this.qty = qty;
        return this;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getQtyProduksi() {
        return qtyProduksi;
    }

    public MPlywood qtyProduksi(Double qtyProduksi) {
        this.qtyProduksi = qtyProduksi;
        return this;
    }

    public void setQtyProduksi(Double qtyProduksi) {
        this.qtyProduksi = qtyProduksi;
    }

    public Status getStatus() {
        return status;
    }

    public MPlywood status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public MPlywood createdOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public Set<TPlywood> getTplywoods() {
        return tplywoods;
    }

    public MPlywood tplywoods(Set<TPlywood> tPlywoods) {
        this.tplywoods = tPlywoods;
        return this;
    }

    public MPlywood addTplywood(TPlywood tPlywood) {
        this.tplywoods.add(tPlywood);
        tPlywood.setMplywood(this);
        return this;
    }

    public MPlywood removeTplywood(TPlywood tPlywood) {
        this.tplywoods.remove(tPlywood);
        tPlywood.setMplywood(null);
        return this;
    }

    public void setTplywoods(Set<TPlywood> tPlywoods) {
        this.tplywoods = tPlywoods;
    }

    public User getCreatedby() {
        return createdby;
    }

    public MPlywood createdby(User user) {
        this.createdby = user;
        return this;
    }

    public void setCreatedby(User user) {
        this.createdby = user;
    }

    public MPlywoodCategory getPlywoodcategory() {
        return plywoodcategory;
    }

    public MPlywood plywoodcategory(MPlywoodCategory mPlywoodCategory) {
        this.plywoodcategory = mPlywoodCategory;
        return this;
    }

    public void setPlywoodcategory(MPlywoodCategory mPlywoodCategory) {
        this.plywoodcategory = mPlywoodCategory;
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
        MPlywood mPlywood = (MPlywood) o;
        if (mPlywood.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mPlywood.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MPlywood{" +
            "id=" + getId() +
            ", hargaBeli=" + getHargaBeli() +
            ", qty=" + getQty() +
            ", qtyProduksi=" + getQtyProduksi() +
            ", status='" + getStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            "}";
    }
}
