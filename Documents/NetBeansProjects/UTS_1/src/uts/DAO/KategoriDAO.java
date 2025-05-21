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
import uts.Model.KategoriBuku;

/**
 *
 * @author Lenovo
 */
public class KategoriDAO {
    public static ObservableList<KategoriBuku> getKategori() {
        // ObservableList untuk menyimpan data user
        ObservableList<KategoriBuku> kategoriList = FXCollections.observableArrayList();
        String query = "SELECT * FROM kategori_buku";

        try {
            // Membuat koneksi ke database
            Connection koneksi = DBConnection.getConnection();
            // Membuat statement
            Statement stmt = koneksi.createStatement();
            // Query untuk mengambil data user
            ResultSet rs = stmt.executeQuery(query);
            
             // Menambahkan data ke ObservableList
              while (rs.next()) {
            String kode_kategori = rs.getString("kode_kategori");  
            String nama_kategori = rs.getString("nama_kategori");  
            

            kategoriList.add(new KategoriBuku(kode_kategori,nama_kategori));
        }

            // Menutup koneksi
            rs.close();
            stmt.close();
            koneksi.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        
     
        return kategoriList;
    }
}
