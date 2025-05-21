/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uts.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uts.Model.Buku;

/**
 *
 * @author Lenovo
 */
public class BukuDAO {
    public static ObservableList<Buku> getBuku() {
        // ObservableList untuk menyimpan data user
        ObservableList bukuList = FXCollections.observableArrayList();
        String query = "SELECT b.*, k.nama_kategori FROM buku b INNER JOIN kategori_buku k ON b.kode_kategori = k.kode_kategori";

        try {
            // Membuat koneksi ke database
            Connection koneksi = DBConnection.getConnection();
            // Membuat statement
            Statement stmt = koneksi.createStatement();
            // Query untuk mengambil data user
            ResultSet rs = stmt.executeQuery(query);
            
             // Menambahkan data ke ObservableList
              while (rs.next()) {
            String kode_buku = rs.getString("kode_buku"); 
            String kode_kategori = rs.getString("kode_kategori");
            String judul = rs.getString("judul");
            String penerbit = rs.getString("penerbit");
            String pengarang = rs.getString("pengarang");
            String tahun = rs.getString("tahun");
            String edisi = rs.getString("edisi");
            LocalDate tahun_pengadaan = rs.getDate("tahun_pengadaan").toLocalDate();


            bukuList.add(new Buku(kode_buku, kode_kategori, judul, penerbit, pengarang, tahun, edisi, tahun_pengadaan));
        }

            // Menutup koneksi
            rs.close();
            stmt.close();
            koneksi.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        
     
        return bukuList;
    }
    
    public static void addBuku(Buku buku) {
      String query = "INSERT INTO buku (kode_buku, kode_kategori,judul, penerbit, pengarang, tahun, edisi,tahun_pengadaan) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    try {
        Connection koneksi = DBConnection.getConnection();
        PreparedStatement smt = koneksi.prepareStatement(query);

        smt.setString(1, buku.getKode_buku());
        smt.setString(2, buku.getKode_kategori());
        smt.setString(3, buku.getJudul());
        smt.setString(4, buku.getPenerbit());
        smt.setString(5, buku.getPengarang());
        smt.setString(6, buku.getTahun());
        smt.setString(7, buku.getEdisi());
        smt.setDate(8, java.sql.Date.valueOf(buku.getTahun_pengadaan()));

        smt.executeUpdate();

        smt.close();
        koneksi.close();

    } catch (SQLException e) {
        e.printStackTrace();
    }
    }
    
    public static void updateBuku(Buku buku) {
        String query = "UPDATE buku SET kode_kategori= ?, judul = ?, penerbit = ?, pengarang = ?, tahun = ?, edisi = ?, tahun_pengadaan = ? WHERE kode_buku = ?";

        try (Connection koneksi = DBConnection.getConnection()) {
            PreparedStatement mst = koneksi.prepareStatement(query);
            mst.setString(1, buku.getKode_kategori());
            mst.setString(2, buku.getJudul());
            mst.setString(3, buku.getPenerbit());
            mst.setString(4, buku.getPengarang());
            mst.setString(5, buku.getTahun());
            mst.setString(6, buku.getEdisi());
            mst.setDate(7, java.sql.Date.valueOf(buku.getTahun_pengadaan()));
            mst.setString(8, buku.getKode_buku());

            mst.executeUpdate();
            mst.close();
            koneksi.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteBuku(String judul) {
        String query = "DELETE FROM buku WHERE judul = ?";

        try (Connection koneksi = DBConnection.getConnection()) {
           PreparedStatement mst = koneksi.prepareStatement(query);
           mst.setString(1, judul);

           mst.executeUpdate();
           mst.close();
           koneksi.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } 
    
    public static List<String> getKategoriList() {
        List<String> kategoriList = new ArrayList<>();
        // contoh kode ambil data kategori dari DB
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT nama_kategori FROM kategori_buku")) {

            while (rs.next()) {
                kategoriList.add(rs.getString("nama_kategori"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kategoriList;
    }
    
}
