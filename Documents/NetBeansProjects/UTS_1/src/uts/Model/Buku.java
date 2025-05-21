package uts.Model;

import java.time.LocalDate;

public class Buku {
    private String kode_buku;
    private String kode_kategori;
    private String judul;
    private String pengarang;
    private String penerbit;
    private String tahun;
    private String edisi;
    private LocalDate tahun_pengadaan;

    public Buku(String kode_buku, String kode_kategori, String judul, String penerbit, String pengarang, String tahun, String edisi, LocalDate tahun_pengadaan) {
        this.kode_buku = kode_buku;
        this.kode_kategori = kode_kategori;
        this.judul = judul;
        this.pengarang = pengarang;
        this.penerbit = penerbit;
        this.tahun = tahun;
        this.edisi = edisi;
        this.tahun_pengadaan = tahun_pengadaan;
    }

    public String getKode_buku() {
        return kode_buku;
    }

    public void setKode_buku(String kode_buku) {
        this.kode_buku = kode_buku;
    }

    public String getKode_kategori() {
        return kode_kategori;
    }

    public void setKode_kategori(String kode_kategori) {
        this.kode_kategori = kode_kategori;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPengarang() {
        return pengarang;
    }

    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getEdisi() {
        return edisi;
    }

    public void setEdisi(String edisi) {
        this.edisi = edisi;
    }

    public LocalDate getTahun_pengadaan() {
        return tahun_pengadaan;
    }

    public void setTahun_pengadaan(LocalDate tahun_pengadaan) {
        this.tahun_pengadaan = tahun_pengadaan;
    }
}
