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

import com.sssv3.domain.enumeration.TIPEUTANG;

import com.sssv3.domain.enumeration.Status;

/**
 * A MUtang.
 */
@Entity
@Table(name = "m_utang")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MUtang implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nominal")
    private Double nominal;

    @Column(name = "sisa")
    private Double sisa;

    @Column(name = "duedate")
    private LocalDate duedate;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipe")
    private TIPEUTANG tipe;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToMany(mappedBy = "mutang")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TUtang> tutangs = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("mutangs")
    private Transaksi transaksi;

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

    public MUtang nominal(Double nominal) {
        this.nominal = nominal;
        return this;
    }

    public void setNominal(Double nominal) {
        this.nominal = nominal;
    }

    public Double getSisa() {
        return sisa;
    }

    public MUtang sisa(Double sisa) {
        this.sisa = sisa;
        return this;
    }

    public void setSisa(Double sisa) {
        this.sisa = sisa;
    }

    public LocalDate getDuedate() {
        return duedate;
    }

    public MUtang duedate(LocalDate duedate) {
        this.duedate = duedate;
        return this;
    }

    public void setDuedate(LocalDate duedate) {
        this.duedate = duedate;
    }

    public TIPEUTANG getTipe() {
        return tipe;
    }

    public MUtang tipe(TIPEUTANG tipe) {
        this.tipe = tipe;
        return this;
    }

    public void setTipe(TIPEUTANG tipe) {
        this.tipe = tipe;
    }

    public Status getStatus() {
        return status;
    }

    public MUtang status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<TUtang> getTutangs() {
        return tutangs;
    }

    public MUtang tutangs(Set<TUtang> tUtangs) {
        this.tutangs = tUtangs;
        return this;
    }

    public MUtang addTutang(TUtang tUtang) {
        this.tutangs.add(tUtang);
        tUtang.setMutang(this);
        return this;
    }

    public MUtang removeTutang(TUtang tUtang) {
        this.tutangs.remove(tUtang);
        tUtang.setMutang(null);
        return this;
    }

    public void setTutangs(Set<TUtang> tUtangs) {
        this.tutangs = tUtangs;
    }

    public Transaksi getTransaksi() {
        return transaksi;
    }

    public MUtang transaksi(Transaksi transaksi) {
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
        MUtang mUtang = (MUtang) o;
        if (mUtang.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mUtang.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MUtang{" +
            "id=" + getId() +
            ", nominal=" + getNominal() +
            ", sisa=" + getSisa() +
            ", duedate='" + getDuedate() + "'" +
            ", tipe='" + getTipe() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
