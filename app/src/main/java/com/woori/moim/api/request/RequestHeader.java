package com.woori.moim.api.request;

import com.google.gson.annotations.SerializedName;

public class RequestHeader {

    @SerializedName("UTZPE_CNCT_IPAD")
    private String UTZPE_CNCT_IPAD;
    @SerializedName("UTZPE_CNCT_MCHR_UNQ_ID")
    private String UTZPE_CNCT_MCHR_UNQ_ID;
    @SerializedName("UTZPE_CNCT_TEL_NO_TXT")
    private String UTZPE_CNCT_TEL_NO_TXT;
    @SerializedName("UTZPE_CNCT_MCHR_IDF_SRNO")
    private String UTZPE_CNCT_MCHR_IDF_SRNO;
    @SerializedName("UTZ_MCHR_OS_DSCD")
    private String UTZ_MCHR_OS_DSCD;
    @SerializedName("UTZ_MCHR_OS_VER_NM")
    private String UTZ_MCHR_OS_VER_NM;
    @SerializedName("UTZ_MCHR_MDL_NM")
    private String UTZ_MCHR_MDL_NM;
    @SerializedName("UTZ_MCHR_APP_VER_NM")
    private String UTZ_MCHR_APP_VER_NM;

    public void setUTZPE_CNCT_IPAD(String UTZPE_CNCT_IPAD) {
        this.UTZPE_CNCT_IPAD = UTZPE_CNCT_IPAD;
    }

    public void setUTZPE_CNCT_MCHR_UNQ_ID(String UTZPE_CNCT_MCHR_UNQ_ID) {
        this.UTZPE_CNCT_MCHR_UNQ_ID = UTZPE_CNCT_MCHR_UNQ_ID;
    }

    public void setUTZPE_CNCT_TEL_NO_TXT(String UTZPE_CNCT_TEL_NO_TXT) {
        this.UTZPE_CNCT_TEL_NO_TXT = UTZPE_CNCT_TEL_NO_TXT;
    }

    public void setUTZPE_CNCT_MCHR_IDF_SRNO(String UTZPE_CNCT_MCHR_IDF_SRNO) {
        this.UTZPE_CNCT_MCHR_IDF_SRNO = UTZPE_CNCT_MCHR_IDF_SRNO;
    }

    public void setUTZ_MCHR_OS_DSCD(String UTZ_MCHR_OS_DSCD) {
        this.UTZ_MCHR_OS_DSCD = UTZ_MCHR_OS_DSCD;
    }

    public void setUTZ_MCHR_OS_VER_NM(String UTZ_MCHR_OS_VER_NM) {
        this.UTZ_MCHR_OS_VER_NM = UTZ_MCHR_OS_VER_NM;
    }

    public void setUTZ_MCHR_MDL_NM(String UTZ_MCHR_MDL_NM) {
        this.UTZ_MCHR_MDL_NM = UTZ_MCHR_MDL_NM;
    }

    public void setUTZ_MCHR_APP_VER_NM(String UTZ_MCHR_APP_VER_NM) {
        this.UTZ_MCHR_APP_VER_NM = UTZ_MCHR_APP_VER_NM;
    }
}