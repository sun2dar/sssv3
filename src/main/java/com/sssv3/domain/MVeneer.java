package com.sssv3.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.sssv3.domain.enumeration.Status;

/**
 * A MVeneer.
 */
@Entity
@Table(name = "m_veneer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MVeneer implements Serializable {

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

    @ManyToOne
    @JsonIgnoreProperties("")
    private User createdby;

    @ManyToOne
    @JsonIgnoreProperties("mveneers")
    private MVeneerCategory veneercategory;

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

    public MVeneer hargaBeli(Double hargaBeli) {
        this.hargaBeli = hargaBeli;
        return this;
    }

    public void setHargaBeli(Double hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public Double getQty() {
        return qty;
    }

    public MVeneer qty(Double qty) {
        this.qty = qty;
        return this;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getQtyProduksi() {
        return qtyProduksi;
    }

    public MVeneer qtyProduksi(Double qtyProduksi) {
        this.qtyProduksi = qtyProduksi;
        return this;
    }

    public void setQtyProduksi(Double qtyProduksi) {
        this.qtyProduksi = qtyProduksi;
    }

    public Status getStatus() {
        return status;
    }

    public MVeneer status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public MVeneer createdOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public User getCreatedby() {
        return createdby;
    }

    public MVeneer createdby(User user) {
        this.createdby = user;
        return this;
    }

    public void setCreatedby(User user) {
        this.createdby = user;
    }

    public MVeneerCategory getVeneercategory() {
        return veneercategory;
    }

    public MVeneer veneercategory(MVeneerCategory mVeneerCategory) {
        this.veneercategory = mVeneerCategory;
        return this;
    }

    public void setVeneercategory(MVeneerCategory mVeneerCategory) {
        this.veneercategory = mVeneerCategory;
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
        MVeneer mVeneer = (MVeneer) o;
        if (mVeneer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mVeneer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MVeneer{" +
            "id=" + getId() +
            ", hargaBeli=" + getHargaBeli() +
            ", qty=" + getQty() +
            ", qtyProduksi=" + getQtyProduksi() +
            ", status='" + getStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            "}";
    }
}
