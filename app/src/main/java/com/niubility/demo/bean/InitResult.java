package com.niubility.demo.bean;

import java.io.Serializable;

public class InitResult implements Serializable {


    /**
     * asid : 600018
     * store_name : 办公室测试
     * shop_id : 16
     * mac_addr : 58:b3:fc:6f:11:c5
     * appid : wx06c555555250e82b
     * sub_appid : wx1d1efad39ae132f1
     * mch_id : 1449788602
     * sub_mch_id : 1487696602
     */

    private String asid;
    private String store_name;
    private String shop_id;
    private String mac_addr;
    private String appid;
    private String sub_appid;
    private String mch_id;
    private String sub_mch_id;

    public String getAsid() {
        return asid;
    }

    public void setAsid(String asid) {
        this.asid = asid;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getMac_addr() {
        return mac_addr;
    }

    public void setMac_addr(String mac_addr) {
        this.mac_addr = mac_addr;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSub_appid() {
        return sub_appid;
    }

    public void setSub_appid(String sub_appid) {
        this.sub_appid = sub_appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getSub_mch_id() {
        return sub_mch_id;
    }

    public void setSub_mch_id(String sub_mch_id) {
        this.sub_mch_id = sub_mch_id;
    }

}
