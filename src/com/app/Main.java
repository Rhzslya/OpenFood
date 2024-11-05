package com.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.List;
import java.util.ArrayList;

class Menu {
    String nama;
    int harga;
    String kategori;

    public Menu(String nama, int harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }
}

public class Main extends JFrame {

    // Daftar menu tanpa perlu memasukkan data satu per satu
    private static final Menu[] menuList = {
            new Menu("Nasi Goreng", 15000, "makanan"),
            new Menu("Mie Goreng", 15000, "makanan"),
            new Menu("Mie Rebus", 15000, "makanan"),
            new Menu("Kwetiau Goreng", 15000, "makanan"),
            new Menu("Kwetiau Rebus", 15000, "makanan"),
            new Menu("Es Teh", 5000, "minuman"),
            new Menu("Es Jeruk", 8000, "minuman"),
            new Menu("Jus Alpukat", 10000, "minuman"),
            new Menu("Jus Mangga", 10000, "minuman"),
            new Menu("Air Mineral", 3000, "minuman"),
    };

    private String formatIDR(int amount) {
        NumberFormat formatIDR = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return formatIDR.format(amount);
    }

    private JComboBox<String> menuDropdown;
    private JTextField jumlahField;
    private List<String> daftarPesanan = new ArrayList<>();
    private int nomerPesanan = 1;
    private JTextArea strukArea;
    private JButton tambahButton;
    private JButton cetakButton;
    private int totalBiaya = 0;
    private StringBuilder template = new StringBuilder();
    private StringBuilder pesananStruk = new StringBuilder();

    public Main() {
        setTitle("Open Food");
        setSize(350, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        // Dropdown Menu
        menuDropdown = new JComboBox<>();
        menuDropdown.addItem("Nasi Goreng (Rp 15.000)");
        menuDropdown.addItem("Mie Goreng (Rp 15.000)");
        menuDropdown.addItem("Mie Rebus (Rp 15.000)");
        menuDropdown.addItem("Kwetiau Goreng (Rp 15.000)");
        menuDropdown.addItem("Kwetiau Rebus (Rp 15.000)");
        menuDropdown.addItem("Es Teh (Rp 5.000)");
        menuDropdown.addItem("Es Jeruk (Rp 8.000)");
        menuDropdown.addItem("Jus Alpukat (Rp 10.000)");
        menuDropdown.addItem("Jus Mangga (Rp 10.000)");
        menuDropdown.addItem("Air Mineral (Rp 3.000)");
        add(new JLabel("Pilih Menu"));
        add(menuDropdown);

        // Jumlah Field
        jumlahField = new JTextField(5);
        add(new JLabel("Jumlah"));
        add(jumlahField);

        // Tombol Tambah
        tambahButton = new JButton("Tambah Pesanan");
        add(tambahButton);

        // Area untuk Struk
        strukArea = new JTextArea(20, 30);
        strukArea.setEditable(false);
        add(new JScrollPane(strukArea));


        // Tombol Cetak
        cetakButton = new JButton("Cetak Struk");
        cetakButton.setEnabled(false); // Menyembunyikan tombol cetak
        add(cetakButton);

        // Event handler untuk tombol tambah
        tambahButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahPesanan();
            }
        });

        // Event handler untuk tombol cetak
        cetakButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cetakStruk();
            }
        });

        template();
    }

    private void template() {
        template.append("----------------------------| Daftar Pesanan |---------------------------\n");
        template.append("===============================================\n");
        strukArea.setText(template.toString());
    }

    private void resetPesanan(){
        daftarPesanan.clear();
        pesananStruk.setLength(0); // Mengosongkan StringBuilder pesanan
        totalBiaya = 0; // Reset total biaya
        nomerPesanan = 1;
    }

    private void tambahPesanan() {
        if (!cetakButton.isEnabled()) {
            resetPesanan();
            // Tetap pertahankan template struk yang sudah ada
            strukArea.setText(template.toString()); // Menampilkan template awal
        }

        String selectedMenu = (String) menuDropdown.getSelectedItem();
        String jumlahText = jumlahField.getText().trim();
        // Cek apakah jumlah tidak kosong
        if (jumlahText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan jumlah pesanan terlebih dahulu.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int jumlah = Integer.parseInt(jumlahText);

        // Cari harga dari menu yang dipilih tanpa perulangan eksplisit
        int hargaItem = 0;
        String namaItem = "";
        if (selectedMenu.contains("Nasi Goreng")) {
            hargaItem = 15000 * jumlah;
            namaItem = "Nasi Goreng";
        } else if (selectedMenu.contains("Mie Goreng")) {
            hargaItem = 15000 * jumlah;
            namaItem = "Mie Goreng";
        } else if (selectedMenu.contains("Mie Rebus")) {
            hargaItem = 15000 * jumlah;
            namaItem = "Mie Rebus";
        } else if (selectedMenu.contains("Kwetiau Goreng")) {
            hargaItem = 15000 * jumlah;
            namaItem = "Kwetiau Goreng";
        } else if (selectedMenu.contains("Kwetiau Rebus")) {
            hargaItem = 15000 * jumlah;
            namaItem = "Kwetiau Rebus";
        } else if (selectedMenu.contains("Es Teh")) {
            hargaItem = 5000 * jumlah;
            namaItem = "Es Teh";
        } else if (selectedMenu.contains("Es Jeruk")) {
            hargaItem = 8000 * jumlah;
            namaItem = "Es Jeruk";
        } else if (selectedMenu.contains("Jus Alpukat")) {
            hargaItem = 10000 * jumlah;
            namaItem = "Jus Alpukat";
        } else if (selectedMenu.contains("Jus Mangga")) {
            hargaItem = 10000 * jumlah;
            namaItem = "Jus Mangga";
        } else {
            hargaItem = 3000 * jumlah;
            namaItem = "Air Mineral";
        }

        totalBiaya += hargaItem;

        // Format harga menggunakan metode formatIDR
        String hargaItemFormatted = formatIDR(hargaItem);

        // Tambahkan pesanan ke struk
        pesananStruk.append(nomerPesanan++ + ". " + namaItem + " " + jumlah + "x = " + formatIDR(hargaItem) + "\n");
        strukArea.setText(template.toString() + pesananStruk.toString()); // Menggabungkan template dengan pesanan
        jumlahField.setText("");
        daftarPesanan.add(pesananStruk.toString());
        jumlahField.setText("");

        cetakButton.setEnabled(true); // Menyembunyikan tombol cetak
    }



    private void cetakStruk() {

        // Hitung pajak dan biaya pelayanan
        int biayaPajakNonFormat = (int) (0.1 * totalBiaya);
        int biayaPelayananNonFormat = 20000;
        int totalSetelahPajakNonFormat = totalBiaya + biayaPajakNonFormat + biayaPelayananNonFormat;

        // Diskon
        int totalDiskon = (totalSetelahPajakNonFormat > 100000) ? (int) (0.1 * totalSetelahPajakNonFormat) : 0;
        int grandTotal = totalSetelahPajakNonFormat - totalDiskon;

        // Tampilkan struk dengan format IDR menggunakan metode formatIDR
        strukArea.append("=================| Terima Kasih |================");
        strukArea.append("\nTotal Biaya: " + formatIDR(totalBiaya));
        strukArea.append("\nPajak (10%): " + formatIDR(biayaPajakNonFormat));
        strukArea.append("\nBiaya Pelayanan: " + formatIDR(biayaPelayananNonFormat));

        if (totalDiskon > 0) {
            strukArea.append("\nDiskon 10%: " + formatIDR(totalDiskon));
        }

        strukArea.append("\nTotal: " + formatIDR(grandTotal));

        // Reset semua data setelah cetak struk
        resetPesanan();
        cetakButton.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}
