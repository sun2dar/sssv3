package com.sssv3.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TBongkar.
 */
@Entity
@Table(name = "t_bongkar")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TBongkar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rate_upah")
    private Double rateUpah;

    @Column(name = "volume")
    private Double volume;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("tbongkars")
    private Transaksi transaksi;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRateUpah() {
        return rateUpah;
    }

    public TBongkar rateUpah(Double rateUpah) {
        this.rateUpah = rateUpah;
        return this;
    }

    public void setRateUpah(Double rateUpah) {
        this.rateUpah = rateUpah;
    }

    public Double getVolume() {
        return volume;
    }

    public TBongkar volume(Double volume) {
        this.volume = volume;
        return this;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Transaksi getTransaksi() {
        return transaksi;
    }

    public TBongkar transaksi(Transaksi transaksi) {
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
        TBongkar tBongkar = (TBongkar) o;
        if (tBongkar.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tBongkar.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TBongkar{" +
            "id=" + getId() +
            ", rateUpah=" + getRateUpah() +
            ", volume=" + getVolume() +
            "}";
    }
}
