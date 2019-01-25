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

import com.sssv3.domain.enumeration.TransaksiType;

import com.sssv3.domain.enumeration.TransaksiCategory;

import com.sssv3.domain.enumeration.Status;

/**
 * A Transaksi.
 */
@Entity
@Table(name = "transaksi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Transaksi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipe")
    private TransaksiType tipe;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private TransaksiCategory category;

    @NotNull
    @Column(name = "tanggal", nullable = false)
    private LocalDate tanggal;

    @Column(name = "invoiceno")
    private String invoiceno;

    @Lob
    @Column(name = "invoicefile")
    private byte[] invoicefile;

    @Column(name = "invoicefile_content_type")
    private String invoicefileContentType;

    @Column(name = "nopol")
    private String nopol;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "created_on")
    private LocalDate createdOn;

    @OneToMany(mappedBy = "transaksi")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TLog> tlogs = new HashSet<>();
    @OneToMany(mappedBy = "transaksi")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TVeneer> tveneers = new HashSet<>();
    @OneToMany(mappedBy = "transaksi")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TPlywood> tplywoods = new HashSet<>();
    @OneToMany(mappedBy = "transaksi")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TMaterial> tmaterials = new HashSet<>();
    @OneToMany(mappedBy = "transaksi")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TBongkar> tbongkars = new HashSet<>();
    @OneToMany(mappedBy = "transaksi")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MUtang> mutangs = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("")
    private User createdby;

    @ManyToOne
    @JsonIgnoreProperties("transaksis")
    private MShift shift;

    @ManyToOne
    @JsonIgnoreProperties("transaksis")
    private MSupplier supplier;

    @ManyToOne
    @JsonIgnoreProperties("transaksis")
    private MCustomer customer;

    @ManyToOne
    @JsonIgnoreProperties("transaksis")
    private MPaytype paytype;

    @ManyToOne
    @JsonIgnoreProperties("transaksis")
    private MEkspedisi ekpedisi;

    @ManyToOne
    @JsonIgnoreProperties("transaksis")
    private MTeam team;

    @ManyToOne
    @JsonIgnoreProperties("transaksis")
    private MPajak pajak;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransaksiType getTipe() {
        return tipe;
    }

    public Transaksi tipe(TransaksiType tipe) {
        this.tipe = tipe;
        return this;
    }

    public void setTipe(TransaksiType tipe) {
        this.tipe = tipe;
    }

    public TransaksiCategory getCategory() {
        return category;
    }

    public Transaksi category(TransaksiCategory category) {
        this.category = category;
        return this;
    }

    public void setCategory(TransaksiCategory category) {
        this.category = category;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public Transaksi tanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
        return this;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public Transaksi invoiceno(String invoiceno) {
        this.invoiceno = invoiceno;
        return this;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno;
    }

    public byte[] getInvoicefile() {
        return invoicefile;
    }

    public Transaksi invoicefile(byte[] invoicefile) {
        this.invoicefile = invoicefile;
        return this;
    }

    public void setInvoicefile(byte[] invoicefile) {
        this.invoicefile = invoicefile;
    }

    public String getInvoicefileContentType() {
        return invoicefileContentType;
    }

    public Transaksi invoicefileContentType(String invoicefileContentType) {
        this.invoicefileContentType = invoicefileContentType;
        return this;
    }

    public void setInvoicefileContentType(String invoicefileContentType) {
        this.invoicefileContentType = invoicefileContentType;
    }

    public String getNopol() {
        return nopol;
    }

    public Transaksi nopol(String nopol) {
        this.nopol = nopol;
        return this;
    }

    public void setNopol(String nopol) {
        this.nopol = nopol;
    }

    public Status getStatus() {
        return status;
    }

    public Transaksi status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public Transaksi createdOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public Set<TLog> getTlogs() {
        return tlogs;
    }

    public Transaksi tlogs(Set<TLog> tLogs) {
        this.tlogs = tLogs;
        return this;
    }

    public Transaksi addTlog(TLog tLog) {
        this.tlogs.add(tLog);
        tLog.setTransaksi(this);
        return this;
    }

    public Transaksi removeTlog(TLog tLog) {
        this.tlogs.remove(tLog);
        tLog.setTransaksi(null);
        return this;
    }

    public void setTlogs(Set<TLog> tLogs) {
        this.tlogs = tLogs;
    }

    public Set<TVeneer> getTveneers() {
        return tveneers;
    }

    public Transaksi tveneers(Set<TVeneer> tVeneers) {
        this.tveneers = tVeneers;
        return this;
    }

    public Transaksi addTveneer(TVeneer tVeneer) {
        this.tveneers.add(tVeneer);
        tVeneer.setTransaksi(this);
        return this;
    }

    public Transaksi removeTveneer(TVeneer tVeneer) {
        this.tveneers.remove(tVeneer);
        tVeneer.setTransaksi(null);
        return this;
    }

    public void setTveneers(Set<TVeneer> tVeneers) {
        this.tveneers = tVeneers;
    }

    public Set<TPlywood> getTplywoods() {
        return tplywoods;
    }

    public Transaksi tplywoods(Set<TPlywood> tPlywoods) {
        this.tplywoods = tPlywoods;
        return this;
    }

    public Transaksi addTplywood(TPlywood tPlywood) {
        this.tplywoods.add(tPlywood);
        tPlywood.setTransaksi(this);
        return this;
    }

    public Transaksi removeTplywood(TPlywood tPlywood) {
        this.tplywoods.remove(tPlywood);
        tPlywood.setTransaksi(null);
        return this;
    }

    public void setTplywoods(Set<TPlywood> tPlywoods) {
        this.tplywoods = tPlywoods;
    }

    public Set<TMaterial> getTmaterials() {
        return tmaterials;
    }

    public Transaksi tmaterials(Set<TMaterial> tMaterials) {
        this.tmaterials = tMaterials;
        return this;
    }

    public Transaksi addTmaterial(TMaterial tMaterial) {
        this.tmaterials.add(tMaterial);
        tMaterial.setTransaksi(this);
        return this;
    }

    public Transaksi removeTmaterial(TMaterial tMaterial) {
        this.tmaterials.remove(tMaterial);
        tMaterial.setTransaksi(null);
        return this;
    }

    public void setTmaterials(Set<TMaterial> tMaterials) {
        this.tmaterials = tMaterials;
    }

    public Set<TBongkar> getTbongkars() {
        return tbongkars;
    }

    public Transaksi tbongkars(Set<TBongkar> tBongkars) {
        this.tbongkars = tBongkars;
        return this;
    }

    public Transaksi addTbongkar(TBongkar tBongkar) {
        this.tbongkars.add(tBongkar);
        tBongkar.setTransaksi(this);
        return this;
    }

    public Transaksi removeTbongkar(TBongkar tBongkar) {
        this.tbongkars.remove(tBongkar);
        tBongkar.setTransaksi(null);
        return this;
    }

    public void setTbongkars(Set<TBongkar> tBongkars) {
        this.tbongkars = tBongkars;
    }

    public Set<MUtang> getMutangs() {
        return mutangs;
    }

    public Transaksi mutangs(Set<MUtang> mUtangs) {
        this.mutangs = mUtangs;
        return this;
    }

    public Transaksi addMutang(MUtang mUtang) {
        this.mutangs.add(mUtang);
        mUtang.setTransaksi(this);
        return this;
    }

    public Transaksi removeMutang(MUtang mUtang) {
        this.mutangs.remove(mUtang);
        mUtang.setTransaksi(null);
        return this;
    }

    public void setMutangs(Set<MUtang> mUtangs) {
        this.mutangs = mUtangs;
    }

    public User getCreatedby() {
        return createdby;
    }

    public Transaksi createdby(User user) {
        this.createdby = user;
        return this;
    }

    public void setCreatedby(User user) {
        this.createdby = user;
    }

    public MShift getShift() {
        return shift;
    }

    public Transaksi shift(MShift mShift) {
        this.shift = mShift;
        return this;
    }

    public void setShift(MShift mShift) {
        this.shift = mShift;
    }

    public MSupplier getSupplier() {
        return supplier;
    }

    public Transaksi supplier(MSupplier mSupplier) {
        this.supplier = mSupplier;
        return this;
    }

    public void setSupplier(MSupplier mSupplier) {
        this.supplier = mSupplier;
    }

    public MCustomer getCustomer() {
        return customer;
    }

    public Transaksi customer(MCustomer mCustomer) {
        this.customer = mCustomer;
        return this;
    }

    public void setCustomer(MCustomer mCustomer) {
        this.customer = mCustomer;
    }

    public MPaytype getPaytype() {
        return paytype;
    }

    public Transaksi paytype(MPaytype mPaytype) {
        this.paytype = mPaytype;
        return this;
    }

    public void setPaytype(MPaytype mPaytype) {
        this.paytype = mPaytype;
    }

    public MEkspedisi getEkpedisi() {
        return ekpedisi;
    }

    public Transaksi ekpedisi(MEkspedisi mEkspedisi) {
        this.ekpedisi = mEkspedisi;
        return this;
    }

    public void setEkpedisi(MEkspedisi mEkspedisi) {
        this.ekpedisi = mEkspedisi;
    }

    public MTeam getTeam() {
        return team;
    }

    public Transaksi team(MTeam mTeam) {
        this.team = mTeam;
        return this;
    }

    public void setTeam(MTeam mTeam) {
        this.team = mTeam;
    }

    public MPajak getPajak() {
        return pajak;
    }

    public Transaksi pajak(MPajak mPajak) {
        this.pajak = mPajak;
        return this;
    }

    public void setPajak(MPajak mPajak) {
        this.pajak = mPajak;
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
        Transaksi transaksi = (Transaksi) o;
        if (transaksi.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transaksi.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Transaksi{" +
            "id=" + getId() +
            ", tipe='" + getTipe() + "'" +
            ", category='" + getCategory() + "'" +
            ", tanggal='" + getTanggal() + "'" +
            ", invoiceno='" + getInvoiceno() + "'" +
            ", invoicefile='" + getInvoicefile() + "'" +
            ", invoicefileContentType='" + getInvoicefileContentType() + "'" +
            ", nopol='" + getNopol() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            "}";
    }
}
