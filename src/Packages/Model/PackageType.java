package Packages.Model;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class PackageType {
    private int PkgId;
    private String PkgName;
    private String PkgStartDate;
    private String  PkgEndDate;
    private String PkgDesc;
    private long PkgBasePrice;
    private long PkgAgencyCom;
    public PackageType (){

    }

    public PackageType(String pkgName, int pkgId) {
        PkgId = pkgId;
        PkgName = pkgName;
    }

    public PackageType(int pkgId, String pkgName, String  pkgStartDate, String  pkgEndDate,
                       String pkgDesc, long pkgBasePrice, long pkgAgencyCom) {
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

    public String  getPkgStartDate() {
        return PkgStartDate;
    }

    public void setPkgStartDate(String  pkgStartDate) {
        PkgStartDate = pkgStartDate;
    }

    public String  getPkgEndDate() {
        return PkgEndDate;
    }

    public void setPkgEndDate(String  pkgEndDate) {
        PkgEndDate = pkgEndDate;
    }

    public String getPkgDesc() {
        return PkgDesc;
    }

    public void setPkgDesc(String pkgDesc) {
        PkgDesc = pkgDesc;
    }

    public long getPkgBasePrice() {
        return PkgBasePrice;
    }

    public void setPkgBasePrice(long pkgBasePrice) {
        PkgBasePrice = pkgBasePrice;
    }

    public long getPkgAgencyCom() {
        return PkgAgencyCom;
    }

    public void setPkgAgencyCom(long pkgAgencyCom) {
        PkgAgencyCom = pkgAgencyCom;
    }

    @Override
    public String toString() {
        return PkgName;
    }
}