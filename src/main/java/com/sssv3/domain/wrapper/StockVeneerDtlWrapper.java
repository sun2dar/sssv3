package com.sssv3.domain.wrapper;

import java.math.BigDecimal;
import java.math.BigInteger;

public class StockVeneerDtlWrapper {
    BigInteger id;
    String name;
    Double qty;
    BigDecimal qtyproduksi;
    Double volume;
    Double hargabeli;
    String type;
    String subtype;
    Boolean flag;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public BigDecimal getQtyproduksi() {
        return qtyproduksi;
    }

    public void setQtyproduksi(BigDecimal qtyproduksi) {
        this.qtyproduksi = qtyproduksi;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getHargabeli() {
        return hargabeli;
    }

    public void setHargabeli(Double hargabeli) {
        this.hargabeli = hargabeli;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}
