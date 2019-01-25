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
 * A MPlywoodGrade.
 */
@Entity
@Table(name = "m_plywood_grade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MPlywoodGrade implements Serializable {

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

    @OneToMany(mappedBy = "plywoodgrade")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MPlywoodCategory> plywoodcategories = new HashSet<>();
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

    public MPlywoodGrade nama(String nama) {
        this.nama = nama;
        return this;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public MPlywoodGrade deskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
        return this;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Status getStatus() {
        return status;
    }

    public MPlywoodGrade status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public MPlywoodGrade createdOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public Set<MPlywoodCategory> getPlywoodcategories() {
        return plywoodcategories;
    }

    public MPlywoodGrade plywoodcategories(Set<MPlywoodCategory> mPlywoodCategories) {
        this.plywoodcategories = mPlywoodCategories;
        return this;
    }

    public MPlywoodGrade addPlywoodcategory(MPlywoodCategory mPlywoodCategory) {
        this.plywoodcategories.add(mPlywoodCategory);
        mPlywoodCategory.setPlywoodgrade(this);
        return this;
    }

    public MPlywoodGrade removePlywoodcategory(MPlywoodCategory mPlywoodCategory) {
        this.plywoodcategories.remove(mPlywoodCategory);
        mPlywoodCategory.setPlywoodgrade(null);
        return this;
    }

    public void setPlywoodcategories(Set<MPlywoodCategory> mPlywoodCategories) {
        this.plywoodcategories = mPlywoodCategories;
    }

    public User getCreatedby() {
        return createdby;
    }

    public MPlywoodGrade createdby(User user) {
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
        MPlywoodGrade mPlywoodGrade = (MPlywoodGrade) o;
        if (mPlywoodGrade.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mPlywoodGrade.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MPlywoodGrade{" +
            "id=" + getId() +
            ", nama='" + getNama() + "'" +
            ", deskripsi='" + getDeskripsi() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            "}";
    }
}
