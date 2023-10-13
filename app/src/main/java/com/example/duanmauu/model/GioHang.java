package com.example.duanmauu.model;

public class GioHang {

    private String idGHCT, idSach, idNguoiDung, tenSach, hinhAnh;
    private int soLuong, giaTien, soLuongTrongGioHang;

    public GioHang() {
    }

    public GioHang(String idHDCT, String idSach, String idNguoiDung, String tenSach,String hinhAnh, int soLuong, int giaTien, int soLuongCuaSach) {
        this.idGHCT = idHDCT;
        this.idSach = idSach;
        this.idNguoiDung = idNguoiDung;
        this.tenSach = tenSach;
        this.hinhAnh = hinhAnh;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
        this.soLuongTrongGioHang = soLuongCuaSach;
    }

    public String getIdGHCT() {
        return idGHCT;
    }

    public void setIdGHCT(String idGHCT) {
        this.idGHCT = idGHCT;
    }

    public String getIdSach() {
        return idSach;
    }

    public void setIdSach(String idSach) {
        this.idSach = idSach;
    }

    public String getIdNguoiDung() {
        return idNguoiDung;
    }

    public void setIdNguoiDung(String idNguoiDung) {
        this.idNguoiDung = idNguoiDung;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public int getSoLuongTrongGioHang() {
        return soLuongTrongGioHang;
    }

    public void setSoLuongTrongGioHang(int soLuongTrongGioHang) {
        this.soLuongTrongGioHang = soLuongTrongGioHang;
    }

    @Override
    public String toString() {
        return "GioHang{" +
                "idHDCT='" + idGHCT + '\'' +
                ", idSach='" + idSach + '\'' +
                ", idNguoiDung='" + idNguoiDung + '\'' +
                ", tenSach='" + tenSach + '\'' +
                ", hinhAnh='" + hinhAnh + '\'' +
                ", soLuong=" + soLuong +
                ", giaTien=" + giaTien +
                ", soLuongCuaSach=" + soLuongTrongGioHang +
                '}';
    }
}
