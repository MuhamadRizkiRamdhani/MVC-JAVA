package uts.Model;

public class KategoriBuku {
    private String kode_kategori;
    private String nama_kategori;

    public KategoriBuku(String kode_kategori, String nama_kategori) {
        this.kode_kategori = kode_kategori;
        this.nama_kategori = nama_kategori;
    }

    public String getKode_kategori() {
        return kode_kategori;
    }

    public void setKode_kategori(String kode_kategori) {
        this.kode_kategori = kode_kategori;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }

    @Override
    public String toString() {
    return nama_kategori;  // atau kombinasi kode + nama
}
}
