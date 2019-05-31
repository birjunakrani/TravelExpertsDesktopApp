package Packages.Model;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;

public class PackageType {
    private int PkgId;
    private String PkgName;
    private java.sql.Date PkgStartDate;
    private java.sql.Date PkgEndDate;
    private String PkgDesc;
    private java.math.BigDecimal PkgBasePrice;
    private java.math.BigDecimal PkgAgencyCom;

    public PackageType(String pkgName, int pkgId) {
        PkgId = pkgId;
        PkgName = pkgName;
    }

    public PackageType(int pkgId, String pkgName, Date pkgStartDate, Date pkgEndDate,
                       String pkgDesc, BigDecimal pkgBasePrice, BigDecimal pkgAgencyCom) {
        PkgId = pkgId;
        PkgName = pkgName;
        PkgStartDate = pkgStartDate;
        PkgEndDate = pkgEndDate;
        PkgDesc = pkgDesc;
        PkgBasePrice = pkgBasePrice;
        PkgAgencyCom = pkgAgencyCom;
    }

    public int getPkgId() {
        return PkgId;
    }

    public void setPkgId(int pkgId) {
        PkgId = pkgId;
    }

    public String getPkgName() {
        return PkgName;
    }

    public void setPkgName(String pkgName) {
        PkgName = pkgName;
    }

    public Date getPkgStartDate() {
        return PkgStartDate;
    }

    public void setPkgStartDate(Date pkgStartDate) {
        PkgStartDate = pkgStartDate;
    }

    public Date getPkgEndDate() {
        return PkgEndDate;
    }

    public void setPkgEndDate(Date pkgEndDate) {
        PkgEndDate = pkgEndDate;
    }

    public String getPkgDesc() {
        return PkgDesc;
    }

    public void setPkgDesc(String pkgDesc) {
        PkgDesc = pkgDesc;
    }

    public BigDecimal getPkgBasePrice() {
        return PkgBasePrice;
    }

    public void setPkgBasePrice(BigDecimal pkgBasePrice) {
        PkgBasePrice = pkgBasePrice;
    }

    public BigDecimal getPkgAgencyCom() {
        return PkgAgencyCom;
    }

    public void setPkgAgencyCom(BigDecimal pkgAgencyCom) {
        PkgAgencyCom = pkgAgencyCom;
    }

    @Override
    public String toString() {
        return "PkgId=" + PkgId;
    }
}