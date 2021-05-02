package com.woori.moim.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Body {

    @SerializedName("REPT_FA")
    ArrayList<Rept> REPT_FA;

    public ArrayList<Rept> getREPT_FA() {
        return REPT_FA;
    }

    public class Rept {
        @SerializedName("CUS_USG_ACNO")
        String CUS_USG_ACNO;

        public String getCUS_USG_ACNO() {
            return CUS_USG_ACNO;
        }
    }

//    @SerializedName("ACNO")
//    private String ACNO;
//    @SerializedName("DPS_STCD")
//    private String DPS_STCD;
//    @SerializedName("CUCD")
//    private String CUCD;
//    @SerializedName("CT_BAL")
//    private String CT_BAL;
//    @SerializedName("PAY_AVL_AM")
//    private String PAY_AVL_AM;
//    @SerializedName("NEW_DT")
//    private String NEW_DT;
//    @SerializedName("XPR_DT")
//    private String XPR_DT;
//    @SerializedName("TXTR_PDCD")
//    private String TXTR_PDCD;
//    @SerializedName("MM_PID_AM")
//    private String MM_PID_AM;
//    @SerializedName("FDN_DSCD")
//    private String FDN_DSCD;
//    @SerializedName("TDY_EVL_AM")
//    private String TDY_EVL_AM;
//    @SerializedName("IVST_PRN")
//    private String IVST_PRN;
//    @SerializedName("SMPL_PRFT_RT")
//    private String SMPL_PRFT_RT;
//    @SerializedName("LST_LN_PCS_AM")
//    private String LST_LN_PCS_AM;
//    @SerializedName("ERRCODE")
//    private String ERRCODE;
//
//    public String getERRCODE() {
//        return ERRCODE;
//    }
//
//    public String getERRUSRMSG() {
//        return ERRUSRMSG;
//    }
//
//    @SerializedName("ERRUSRMSG")
//    private String ERRUSRMSG;
//
//    public String getACNO() {
//        return ACNO;
//    }
//
//    public String getDPS_STCD() {
//        return DPS_STCD;
//    }
//
//    public String getCUCD() {
//        return CUCD;
//    }
//
//    public String getCT_BAL() {
//        return CT_BAL;
//    }
//
//    public String getPAY_AVL_AM() {
//        return PAY_AVL_AM;
//    }
//
//    public String getNEW_DT() {
//        return NEW_DT;
//    }
//
//    public String getXPR_DT() {
//        return XPR_DT;
//    }
//
//    public String getTXTR_PDCD() {
//        return TXTR_PDCD;
//    }
//
//    public String getMM_PID_AM() {
//        return MM_PID_AM;
//    }
//
//    public String getFDN_DSCD() {
//        return FDN_DSCD;
//    }
//
//    public String getTDY_EVL_AM() {
//        return TDY_EVL_AM;
//    }
//
//    public String getIVST_PRN() {
//        return IVST_PRN;
//    }
//
//    public String getSMPL_PRFT_RT() {
//        return SMPL_PRFT_RT;
//    }
//
//    public String getLST_LN_PCS_AM() {
//        return LST_LN_PCS_AM;
//    }
}