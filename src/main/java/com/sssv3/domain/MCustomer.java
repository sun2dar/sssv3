package com.sssv3.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A MCustomer.
 */
@Entity
@Table(name = "m_customer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MCustomer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nama", nullable = false)
    private String nama;

    @Column(name = "telepon")
    private String telepon;

    @Column(name = "mobilephone")
    private String mobilephone;

    @Lob
    @Column(name = "alamat")
    private String alamat;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "created_on")
    private LocalDate createdOn;

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Transaksi> transaksis = new HashSet<>();
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

    public MCustomer nama(String nama) {
        this.nama = nama;
        return this;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTelepon() {
        return telepon;
    }

    public MCustomer telepon(String telepon) {
        this.telepon = telepon;
        return this;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public MCustomer mobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
        return this;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getAlamat() {
        return alamat;
    }

    public MCustomer alamat(String alamat) {
        this.alamat = alamat;
        return this;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Status getStatus() {
        return status;
    }

    public MCustomer status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public MCustomer createdOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public Set<Transaksi> getTransaksis() {
        return transaksis;
    }

    public MCustomer transaksis(Set<Transaksi> transaksis) {
        this.transaksis = transaksis;
        return this;
    }

    public MCustomer addTransaksi(Transaksi transaksi) {
        this.transaksis.add(transaksi);
        transaksi.setCustomer(this);
        return this;
    }

    public MCustomer removeTransaksi(Transaksi transaksi) {
        this.transaksis.remove(transaksi);
        transaksi.setCustomer(null);
        return this;
    }

    public void setTransaksis(Set<Transaksi> transaksis) {
        this.transaksis = transaksis;
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
        MCustomer mCustomer = (MCustomer) o;
        if (mCustomer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mCustomer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MCustomer{" +
            "id=" + getId() +
            ", nama='" + getNama() + "'" +
            ", telepon='" + getTelepon() + "'" +
            ", mobilephone='" + getMobilephone() + "'" +
            ", alamat='" + getAlamat() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            "}";
    }
}
