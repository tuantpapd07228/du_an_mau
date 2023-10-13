package com.example.duanmauu.model;

public class HoaDonOuter {
    private String idHoaDon, ngayMua, tongTien, trangthai;

    public HoaDonOuter(String idHoaDon, String ngayMua, String tongTien,String trangthai) {
        this.idHoaDon = idHoaDon;
        this.ngayMua = ngayMua;
        this.tongTien = tongTien;
        this.trangthai = trangthai;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(String idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public String getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(String ngayMua) {
        this.ngayMua = ngayMua;
    }

    public String getTongTien() {
        return tongTien;
    }

    public void setTongTien(String tongTien) {
        this.tongTien = tongTien;
    }
}
