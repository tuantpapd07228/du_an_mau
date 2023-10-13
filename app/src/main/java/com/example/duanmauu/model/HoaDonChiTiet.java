package com.example.duanmauu.model;

public class HoaDonChiTiet {
    public String idHDCT, idHoaDon, idSach;
    public int soLuongMua;

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(String idHDCT, String idHoaDon, String idSach, int soLuongMua) {
        this.idHDCT = idHDCT;
        this.idHoaDon = idHoaDon;
        this.idSach = idSach;
        this.soLuongMua = soLuongMua;
    }

    public String getIdHDCT() {
        return idHDCT;
    }

    public void setIdHDCT(String idHDCT) {
        this.idHDCT = idHDCT;
    }

    public String getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(String idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public String getIdSach() {
        return idSach;
    }

    public void setIdSach(String idSach) {
        this.idSach = idSach;
    }

    public int getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(int soLuongMua) {
        this.soLuongMua = soLuongMua;
    }
}
