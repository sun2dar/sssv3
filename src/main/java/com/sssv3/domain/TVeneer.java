package com.sssv3.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.sssv3.domain.enumeration.InOut;

/**
 * A TVeneer.
 */
@Entity
@Table(name = "t_veneer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TVeneer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "qty")
    private Double qty;

    @Column(name = "volume")
    private Double volume;

    @Column(name = "harga_beli")
    private Double hargaBeli;

    @Column(name = "harga_total")
    private Double hargaTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_inout")
    private InOut inout;

    @ManyToOne
    @JsonIgnoreProperties("tveneers")
    private MVeneer mveneer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("tveneers")
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

    public TVeneer qty(Double qty) {
        this.qty = qty;
        return this;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getVolume() {
        return volume;
    }

    public TVeneer volume(Double volume) {
        this.volume = volume;
        return this;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getHargaBeli() {
        return hargaBeli;
    }

    public TVeneer hargaBeli(Double hargaBeli) {
        this.hargaBeli = hargaBeli;
        return this;
    }

    public void setHargaBeli(Double hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public Double getHargaTotal() {
        return hargaTotal;
    }

    public TVeneer hargaTotal(Double hargaTotal) {
        this.hargaTotal = hargaTotal;
        return this;
    }

    public void setHargaTotal(Double hargaTotal) {
        this.hargaTotal = hargaTotal;
    }

    public InOut getInout() {
        return inout;
    }

    public TVeneer inout(InOut inout) {
        this.inout = inout;
        return this;
    }

    public void setInout(InOut inout) {
        this.inout = inout;
    }

    public MVeneer getMveneer() {
        return mveneer;
    }

    public TVeneer mveneer(MVeneer mVeneer) {
        this.mveneer = mVeneer;
        return this;
    }

    public void setMveneer(MVeneer mVeneer) {
        this.mveneer = mVeneer;
    }

    public Transaksi getTransaksi() {
        return transaksi;
    }

    public TVeneer transaksi(Transaksi transaksi) {
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
        TVeneer tVeneer = (TVeneer) o;
        if (tVeneer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tVeneer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TVeneer{" +
            "id=" + getId() +
            ", qty=" + getQty() +
            ", volume=" + getVolume() +
            ", hargaBeli=" + getHargaBeli() +
            ", hargaTotal=" + getHargaTotal() +
            ", inout='" + getInout() + "'" +
            "}";
    }
}
