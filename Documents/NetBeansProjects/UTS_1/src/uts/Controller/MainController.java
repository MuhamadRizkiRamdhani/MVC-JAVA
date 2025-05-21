package uts.Controller;

import uts.DAO.BukuDAO;
import uts.DAO.KategoriDAO;
import uts.Model.Buku;
import uts.Model.KategoriBuku;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController implements Initializable {
    @FXML private TextField LKodeBuku;
    @FXML private TextField LJudul;
    @FXML private ComboBox<KategoriBuku> CKategori;
    @FXML private TextField LPengarang;
    @FXML private TextField LPenerbit;
    @FXML private TextField LTahun;
    @FXML private TextField LEdisi;

    @FXML private Button BtnAdd;
    @FXML private Button BtnUpdate;
    @FXML private Button BtnDelete;

    @FXML private TableView<Buku> tabel;
    @FXML private TableColumn<Buku, String> CKodeBuku;
    @FXML private TableColumn<Buku, String> kategori;
    @FXML private TableColumn<Buku, String> CJudul;
    @FXML private TableColumn<Buku, String> CPengarang;
    @FXML private TableColumn<Buku, String> CPenerbit;
    @FXML private TableColumn<Buku, String> CTahun;
    @FXML private TableColumn<Buku, String> CEdisi;
    @FXML private TableColumn<Buku, LocalDate> tahun_pengadaan;

    @FXML private DatePicker datepicker;

    private Buku selectedBuku;
    private KategoriDAO kategoriDAO = new KategoriDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTable();
        loadDataKategori();
        loadDataBuku();

        tabel.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> selectBuku(newSelection)
        );

        tabel.setOnMouseClicked(e -> selectBuku(tabel.getSelectionModel().getSelectedItem()));
    }

    private void setupTable() {
        CKodeBuku.setCellValueFactory(new PropertyValueFactory<>("kode_buku"));
        kategori.setCellValueFactory(cellData -> {
        String kode = cellData.getValue().getKode_kategori();
        String nama = kategoriMap.getOrDefault(kode, "Tidak Diketahui");
        return new ReadOnlyStringWrapper(nama);
        });
        CJudul.setCellValueFactory(new PropertyValueFactory<>("judul"));
        CPengarang.setCellValueFactory(new PropertyValueFactory<>("pengarang"));
        CPenerbit.setCellValueFactory(new PropertyValueFactory<>("penerbit"));
        CTahun.setCellValueFactory(new PropertyValueFactory<>("tahun"));
        CEdisi.setCellValueFactory(new PropertyValueFactory<>("edisi"));
        tahun_pengadaan.setCellValueFactory(new PropertyValueFactory<>("tahun_pengadaan"));
    }

    private void loadDataBuku() {
        tabel.setItems(FXCollections.observableArrayList(BukuDAO.getBuku()));
    }

    private void loadDataKategori() {
        ObservableList<KategoriBuku> kategoriList = FXCollections.observableArrayList(KategoriDAO.getKategori());
    CKategori.setItems(kategoriList);

    // Bikin map dari kode ke nama
    kategoriMap.clear();
    for (KategoriBuku k : kategoriList) {
        kategoriMap.put(k.getKode_kategori(), k.getNama_kategori());
    }
    }

    private void selectBuku(Buku buku) {
        if (buku != null) {
            selectedBuku = buku;
            LKodeBuku.setText(buku.getKode_buku());
            LJudul.setText(buku.getJudul());
            LPengarang.setText(buku.getPengarang());
            LPenerbit.setText(buku.getPenerbit());
            LTahun.setText(buku.getTahun());
            LEdisi.setText(buku.getEdisi());
            datepicker.setValue(buku.getTahun_pengadaan());
            
            for (KategoriBuku kategori : CKategori.getItems()) {
            if (kategori.getKode_kategori().equals(buku.getKode_kategori())) {
                CKategori.getSelectionModel().select(kategori);
                break;
            }
        }
    }
    }

    private void clearFields() {
        LKodeBuku.clear();
        LJudul.clear();
        LPengarang.clear();
        LPenerbit.clear();
        LTahun.clear();
        LEdisi.clear();
        datepicker.setValue(null);
        CKategori.setValue(null);
        selectedBuku = null;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean showDeleteConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    //CRUD
    @FXML
    private void addBuku(ActionEvent event) {
         if (!isInputValid()) {
        return; 
    }

    Buku newBuku = new Buku(
        LKodeBuku.getText(),
        CKategori.getValue().getKode_kategori(),
        LJudul.getText(),
        LPenerbit.getText(),
        LPengarang.getText(),
        LTahun.getText(),
        LEdisi.getText(),
        datepicker.getValue()
    );

    BukuDAO.addBuku(newBuku);
    showAlert(Alert.AlertType.INFORMATION, "Berhasil", "Data berhasil ditambahkan.");
    loadDataBuku();
    clearFields();
    }


    @FXML
    private void updateBuku(ActionEvent event) {
        if (selectedBuku == null) {
        showAlert(Alert.AlertType.ERROR, "Update Gagal", "Silakan pilih buku yang ingin diperbarui terlebih dahulu.");
        return;
    }

    if (!isInputValid()) {
        return; 
    }

    selectedBuku.setKode_kategori(CKategori.getValue().getKode_kategori());
    selectedBuku.setJudul(LJudul.getText());
    selectedBuku.setPengarang(LPengarang.getText());
    selectedBuku.setPenerbit(LPenerbit.getText());
    selectedBuku.setTahun(LTahun.getText());
    selectedBuku.setEdisi(LEdisi.getText());
    selectedBuku.setTahun_pengadaan(datepicker.getValue());

    BukuDAO.updateBuku(selectedBuku);
    showAlert(Alert.AlertType.INFORMATION, "Berhasil", "Data berhasil diperbarui.");
    loadDataBuku();
    clearFields();
    }

    @FXML
    private void deleteBuku(ActionEvent event) {
        if (selectedBuku == null) {
        showAlert(Alert.AlertType.ERROR, "Hapus Gagal", "Silakan pilih buku yang ingin dihapus terlebih dahulu.");
        return;
    }
    if (showDeleteConfirmation("Konfirmasi Hapus", "Apakah Anda yakin ingin menghapus buku ini?")) {
        BukuDAO.deleteBuku(selectedBuku.getJudul());
        showAlert(Alert.AlertType.INFORMATION, "Berhasil", "Data berhasil dihapus.");
        loadDataBuku();
        clearFields();
        }
    }
    
   //CRUD

    private boolean isFieldEmpty() {
        return LKodeBuku.getText().isEmpty() ||
               CKategori.getValue() == null ||
               LJudul.getText().isEmpty() ||
               LPengarang.getText().isEmpty() ||
               LPenerbit.getText().isEmpty() ||
               LTahun.getText().isEmpty() ||
               LEdisi.getText().isEmpty() ||
               datepicker.getValue() == null;
    }
    
    //validasi
        private boolean isInputValid() {
        if (LKodeBuku.getText().isEmpty() || CKategori.getValue() == null || LJudul.getText().isEmpty() ||
                LPengarang.getText().isEmpty() || LPenerbit.getText().isEmpty() ||
                LTahun.getText().isEmpty() || LEdisi.getText().isEmpty() || datepicker.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validasi", "Semua field harus diisi.");
            return false;
        }

        if (!LKodeBuku.getText().matches("^[A-Za-z0-9]{2,10}$")) {
            showAlert(Alert.AlertType.WARNING, "Validasi", "Kode buku hanya boleh huruf/angka (max 10 karakter).");
            return false;
        }

        if (!LTahun.getText().matches("^\\d{4}$")) {
            showAlert(Alert.AlertType.WARNING, "Validasi", "Tahun harus terdiri dari 4 digit angka.");
            return false;
        }

        if (!LEdisi.getText().matches("^\\d{1,2}$")) {
            showAlert(Alert.AlertType.WARNING, "Validasi", "Edisi hanya boleh 1-2 digit angka.");
            return false;
        }

        try {
            Integer.parseInt(LTahun.getText());
            Integer.parseInt(LEdisi.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Validasi", "Tahun dan Edisi harus berupa angka.");
            return false;
        }

        return true;
    }
    //validasi
    
    private Map<String, String> kategoriMap = new HashMap<>();
}