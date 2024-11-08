package com.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;

class Menu {
    String nama,kategori;
    int harga;

    public Menu(String nama, int harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }
}

public class Main extends JFrame {
    // Daftar menu
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

    // Method Konversi Ke Rupiah
    private String formatIDR(int amount) {
        NumberFormat formatIDR = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return formatIDR.format(amount);
    }

    //Format Data Array
    private String formatDataArray(List<String> dataList) {
        Set<String> uniqueData = new HashSet<>(dataList);
        StringBuilder formattedData = new StringBuilder();

        int nomor = 1;
        for (String data : uniqueData) {
            formattedData.append(nomor).append(". ").append(data).append("\n");
            nomor++;
        }

        return formattedData.toString();
    }

    //Template Struk
    private void template() {
        template.append("------------------------------| Daftar Menu |-----------------------------\n");
        template.append("===============================================\n");

        // Menampilkan menu makanan
        template.append("Makanan:\n");
        template.append("Nasi Goreng - ").append(formatIDR(15000)).append("\n");
        template.append("Mie Goreng - ").append(formatIDR(15000)).append("\n");
        template.append("Mie Rebus - ").append(formatIDR(15000)).append("\n");
        template.append("Kwetiau Goreng - ").append(formatIDR(15000)).append("\n");
        template.append("Kwetiau Rebus - ").append(formatIDR(15000)).append("\n");

        // Menampilkan menu minuman
        template.append("\nMinuman:\n");
        template.append("Es Teh - ").append(formatIDR(5000)).append("\n");
        template.append("Es Jeruk - ").append(formatIDR(8000)).append("\n");
        template.append("Jus Alpukat - ").append(formatIDR(10000)).append("\n");
        template.append("Jus Mangga - ").append(formatIDR(10000)).append("\n");
        template.append("Air Mineral - ").append(formatIDR(3000)).append("\n");

        template.append("===============================================\n");

        template.append("----------------------------| Daftar Pesanan |---------------------------\n");
        template.append("===============================================\n");

        strukArea.setText(template.toString());
    }

    //Reset Pesanan
    private void resetPesanan(){
        daftarPesanan.clear();
        pesananStruk.setLength(0);
        totalBiaya = 0;
        nomerPesanan = 1;
        minumanDipesan.clear();
    }

    private JComboBox<String> menuDropdown;
    private JTextField jumlahField;
    private List<String> daftarPesanan = new ArrayList<>();
    private int nomerPesanan = 1;
    private List<String> minumanDipesan = new ArrayList<>();
    private List<String> makananDipesan = new ArrayList<>();
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
        cetakButton.setEnabled(false);
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

    private void tambahPesanan() {
        if (!cetakButton.isEnabled()) {
            resetPesanan();
            strukArea.setText(template.toString());
        }

        String selectedMenu = (String) menuDropdown.getSelectedItem();
        String jumlahText = jumlahField.getText().trim();

        if (jumlahText.isEmpty() || !jumlahText.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Masukkan jumlah pesanan yang valid.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int jumlah = Integer.parseInt(jumlahText);
        int hargaItem = 0;
        String namaItem = "";
        boolean isMinuman = false;
        boolean isMakanan = false;
        // Menentukan harga dan nama item berdasarkan pilihan
        if (selectedMenu.contains("Nasi Goreng")) {
            hargaItem = 15000 * jumlah;
            namaItem = "Nasi Goreng";
            isMakanan = true;
        } else if (selectedMenu.contains("Mie Goreng")) {
            hargaItem = 15000 * jumlah;
            namaItem = "Mie Goreng";
            isMakanan = true;
        } else if (selectedMenu.contains("Mie Rebus")) {
            hargaItem = 15000 * jumlah;
            namaItem = "Mie Rebus";
            isMakanan = true;
        } else if (selectedMenu.contains("Kwetiau Goreng")) {
            hargaItem = 15000 * jumlah;
            namaItem = "Kwetiau Goreng";
            isMakanan = true;
        } else if (selectedMenu.contains("Kwetiau Rebus")) {
            hargaItem = 15000 * jumlah;
            namaItem = "Kwetiau Rebus";
            isMakanan = true;
        } else if (selectedMenu.contains("Es Teh")) {
            hargaItem = 5000 * jumlah;
            namaItem = "Es Teh";
            isMinuman = true;  // Tandai bahwa ini adalah minuman
        } else if (selectedMenu.contains("Es Jeruk")) {
            hargaItem = 8000 * jumlah;
            namaItem = "Es Jeruk";
            isMinuman = true;
        } else if (selectedMenu.contains("Jus Alpukat")) {
            hargaItem = 10000 * jumlah;
            namaItem = "Jus Alpukat";
            isMinuman = true;
        } else if (selectedMenu.contains("Jus Mangga")) {
            hargaItem = 10000 * jumlah;
            namaItem = "Jus Mangga";
            isMinuman = true;
        } else {
            hargaItem = 3000 * jumlah;
            namaItem = "Air Mineral";
            isMinuman = true;
        }

        // Tambahkan pesanan ke list sesuai dengan kategori
        if (isMinuman) {
            minumanDipesan.addAll(Collections.nCopies(jumlah, namaItem));
        } else if (isMakanan) {
            makananDipesan.addAll(Collections.nCopies(jumlah, namaItem));
        }

        // Cek jika jumlah pesanan sudah mencapai batas maksimal 4 menu
        if (daftarPesanan.size() >= 4) {
            JOptionPane.showMessageDialog(this, "Maksimal 4 menu yang bisa dipesan.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        totalBiaya += hargaItem;

        // Tambahkan pesanan ke struk
        pesananStruk.append(nomerPesanan++ + ". " + namaItem + " " + jumlah + "x = " + formatIDR(hargaItem) + "\n");
        strukArea.setText(template.toString() + pesananStruk.toString());
        jumlahField.setText("");
        daftarPesanan.add(pesananStruk.toString());
        cetakButton.setEnabled(true);
    }

    private void cetakStruk() {
        // Hitung pajak dan biaya pelayanan
        int biayaPajakNonFormat = (int) (0.1 * totalBiaya);
        int biayaPelayananNonFormat = 20000;
        int totalSetelahPajakNonFormat = totalBiaya + biayaPajakNonFormat + biayaPelayananNonFormat;

        // Diskon
        int totalDiskon = (totalSetelahPajakNonFormat > 100000) ? (int) (0.1 * totalSetelahPajakNonFormat) : 0;
        int grandTotal = totalSetelahPajakNonFormat - totalDiskon;

        // Diskon Beli Satu Gratis Satu Kategori Minuman
        String minumanGratis = "";
        Set<String> uniqueMinuman = new HashSet<>(minumanDipesan);

        // Mengecek jika ada lebih dari 1 jenis minuman dan total biaya lebih dari 50000
        if (uniqueMinuman.size() > 0 && uniqueMinuman.size() < 2 && totalBiaya > 50000) {
            minumanGratis = "Selamat Anda Mendapatkan 1 Minuman Gratis\n" + formatDataArray(minumanDipesan);
        } else if (uniqueMinuman.size() >= 2 && totalBiaya > 50000) {
            minumanGratis = "Selamat Anda Mendapatkan 1 Minuman Gratis (Pilih Salah Satu)\n" + formatDataArray(minumanDipesan);
        }


        // Tampilkan struk dengan format IDR menggunakan metode formatIDR
        strukArea.append("=================| Terima Kasih |================");
        strukArea.append("\nTotal Biaya: " + formatIDR(totalBiaya));
        if (minumanGratis.length() > 0) {
            strukArea.append("\n" + minumanGratis);
        }
        strukArea.append("\nPajak (10%): " + formatIDR(biayaPajakNonFormat));
        strukArea.append("\nBiaya Pelayanan: " + formatIDR(biayaPelayananNonFormat));
        strukArea.append("\nTotal Sebelum Diskon: " + formatIDR(totalBiaya + biayaPajakNonFormat + biayaPelayananNonFormat));

        if (totalDiskon > 0) {
            strukArea.append("\nDiskon 10%: " + formatIDR(totalDiskon));
        }

        strukArea.append("\nTotal Akhir: " + formatIDR(grandTotal));

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
