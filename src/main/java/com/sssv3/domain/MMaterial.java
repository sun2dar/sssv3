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
 * A MMaterial.
 */
@Entity
@Table(name = "m_material")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MMaterial implements Serializable {

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

    @NotNull
    @Column(name = "harga", nullable = false)
    private Double harga;

    @NotNull
    @Column(name = "qty", nullable = false)
    private Double qty;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "created_on")
    private LocalDate createdOn;

    @OneToMany(mappedBy = "mmaterial")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TMaterial> tmaterials = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("")
    private User createdby;

    @ManyToOne
    @JsonIgnoreProperties("materials")
    private MSatuan satuan;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("materials")
    private MMaterialType materialtype;

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

    public MMaterial nama(String nama) {
        this.nama = nama;
        return this;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public MMaterial deskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
        return this;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Double getHarga() {
        return harga;
    }

    public MMaterial harga(Double harga) {
        this.harga = harga;
        return this;
    }

    public void setHarga(Double harga) {
        this.harga = harga;
    }

    public Double getQty() {
        return qty;
    }

    public MMaterial qty(Double qty) {
        this.qty = qty;
        return this;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Status getStatus() {
        return status;
    }

    public MMaterial status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public MMaterial createdOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public Set<TMaterial> getTmaterials() {
        return tmaterials;
    }

    public MMaterial tmaterials(Set<TMaterial> tMaterials) {
        this.tmaterials = tMaterials;
        return this;
    }

    public MMaterial addTmaterial(TMaterial tMaterial) {
        this.tmaterials.add(tMaterial);
        tMaterial.setMmaterial(this);
        return this;
    }

    public MMaterial removeTmaterial(TMaterial tMaterial) {
        this.tmaterials.remove(tMaterial);
        tMaterial.setMmaterial(null);
        return this;
    }

    public void setTmaterials(Set<TMaterial> tMaterials) {
        this.tmaterials = tMaterials;
    }

    public User getCreatedby() {
        return createdby;
    }

    public MMaterial createdby(User user) {
        this.createdby = user;
        return this;
    }

    public void setCreatedby(User user) {
        this.createdby = user;
    }

    public MSatuan getSatuan() {
        return satuan;
    }

    public MMaterial satuan(MSatuan mSatuan) {
        this.satuan = mSatuan;
        return this;
    }

    public void setSatuan(MSatuan mSatuan) {
        this.satuan = mSatuan;
    }

    public MMaterialType getMaterialtype() {
        return materialtype;
    }

    public MMaterial materialtype(MMaterialType mMaterialType) {
        this.materialtype = mMaterialType;
        return this;
    }

    public void setMaterialtype(MMaterialType mMaterialType) {
        this.materialtype = mMaterialType;
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
        MMaterial mMaterial = (MMaterial) o;
        if (mMaterial.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mMaterial.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MMaterial{" +
            "id=" + getId() +
            ", nama='" + getNama() + "'" +
            ", deskripsi='" + getDeskripsi() + "'" +
            ", harga=" + getHarga() +
            ", qty=" + getQty() +
            ", status='" + getStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            "}";
    }
}
