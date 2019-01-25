package com.sssv3.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.sssv3.domain.enumeration.InOut;

/**
 * A TMaterial.
 */
@Entity
@Table(name = "t_material")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TMaterial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "qty")
    private Double qty;

    @Column(name = "harga_beli")
    private Double hargaBeli;

    @Column(name = "harga_total")
    private Double hargaTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_inout")
    private InOut inout;

    @ManyToOne
    @JsonIgnoreProperties("tmaterials")
    private MMaterial mmaterial;

    @ManyToOne
    @JsonIgnoreProperties("tmaterials")
    private Transaksi transaksi;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQty() {
        return qty;
    }

    public TMaterial qty(Double qty) {
        this.qty = qty;
        return this;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getHargaBeli() {
        return hargaBeli;
    }

    public TMaterial hargaBeli(Double hargaBeli) {
        this.hargaBeli = hargaBeli;
        return this;
    }

    public void setHargaBeli(Double hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public Double getHargaTotal() {
        return hargaTotal;
    }

    public TMaterial hargaTotal(Double hargaTotal) {
        this.hargaTotal = hargaTotal;
        return this;
    }

    public void setHargaTotal(Double hargaTotal) {
        this.hargaTotal = hargaTotal;
    }

    public InOut getInout() {
        return inout;
    }

    public TMaterial inout(InOut inout) {
        this.inout = inout;
        return this;
    }

    public void setInout(InOut inout) {
        this.inout = inout;
    }

    public MMaterial getMmaterial() {
        return mmaterial;
    }

    public TMaterial mmaterial(MMaterial mMaterial) {
        this.mmaterial = mMaterial;
        return this;
    }

    public void setMmaterial(MMaterial mMaterial) {
        this.mmaterial = mMaterial;
    }

    public Transaksi getTransaksi() {
        return transaksi;
    }

    public TMaterial transaksi(Transaksi transaksi) {
        this.transaksi = transaksi;
        return this;
    }

    public void setTransaksi(Transaksi transaksi) {
        this.transaksi = transaksi;
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
        TMaterial tMaterial = (TMaterial) o;
        if (tMaterial.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tMaterial.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TMaterial{" +
            "id=" + getId() +
            ", qty=" + getQty() +
            ", hargaBeli=" + getHargaBeli() +
            ", hargaTotal=" + getHargaTotal() +
            ", inout='" + getInout() + "'" +
            "}";
    }
}
