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
 * A MPaytype.
 */
@Entity
@Table(name = "m_paytype")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MPaytype implements Serializable {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "created_on")
    private LocalDate createdOn;

    @OneToMany(mappedBy = "paytype")
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

    public MPaytype nama(String nama) {
        this.nama = nama;
        return this;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public MPaytype deskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
        return this;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Status getStatus() {
        return status;
    }

    public MPaytype status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public MPaytype createdOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public Set<Transaksi> getTransaksis() {
        return transaksis;
    }

    public MPaytype transaksis(Set<Transaksi> transaksis) {
        this.transaksis = transaksis;
        return this;
    }

    public MPaytype addTransaksi(Transaksi transaksi) {
        this.transaksis.add(transaksi);
        transaksi.setPaytype(this);
        return this;
    }

    public MPaytype removeTransaksi(Transaksi transaksi) {
        this.transaksis.remove(transaksi);
        transaksi.setPaytype(null);
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
        MPaytype mPaytype = (MPaytype) o;
        if (mPaytype.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mPaytype.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MPaytype{" +
            "id=" + getId() +
            ", nama='" + getNama() + "'" +
            ", deskripsi='" + getDeskripsi() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            "}";
    }
}
