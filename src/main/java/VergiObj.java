public class VergiObj {
    private String vergiNo = "";
    private Integer vergiDairesiIndex = 0;
    private String tc = "";
    private String vergiKimlik = "";
    private String unvan = "";
    private String vergiDairesi = "";
    private String faaliyetDurumu = "";

    public VergiObj() {}
    public VergiObj (String vergiNo, Integer vergiDairesiIndex, String tc, String vergiKimlik, String unvan, String vergiDairesi, String faaliyetDurumu) {
        this.vergiNo = vergiNo;
        this.vergiDairesiIndex = vergiDairesiIndex;
        this.tc = tc;
        this.vergiKimlik = vergiKimlik;
        this.unvan = unvan;
        this.vergiDairesi = vergiDairesi;
        this.faaliyetDurumu = faaliyetDurumu;
    }

    public String getVergiNo() {
        return vergiNo;
    }

    public void setVergiNo(String vergiNo) {
        this.vergiNo = vergiNo;
    }

    public Integer getVergiDairesiIndex() {
        return vergiDairesiIndex;
    }

    public void setVergiDairesiIndex(Integer vergiDairesiIndex) {
        this.vergiDairesiIndex = vergiDairesiIndex;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getVergiKimlik() {
        return vergiKimlik;
    }

    public void setVergiKimlik(String vergiKimlik) {
        this.vergiKimlik = vergiKimlik;
    }

    public String getUnvan() {
        return unvan;
    }

    public void setUnvan(String unvan) {
        this.unvan = unvan;
    }

    public String getVergiDairesi() {
        return vergiDairesi;
    }

    public void setVergiDairesi(String vergiDairesi) {
        this.vergiDairesi = vergiDairesi;
    }

    public String getFaaliyetDurumu() {
        return faaliyetDurumu;
    }

    public void setFaaliyetDurumu(String faaliyetDurumu) {
        this.faaliyetDurumu = faaliyetDurumu;
    }
}

