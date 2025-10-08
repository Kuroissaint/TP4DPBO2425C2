import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ProductMenu extends JFrame {
    public static void main(String[] args) {
        ProductMenu menu = new ProductMenu();
        menu.setSize(700,600);
        menu.setLocationRelativeTo(null);
        menu.setContentPane(menu.mainPanel);
        menu.getContentPane().setBackground(Color.DARK_GRAY);
        menu.getContentPane().setForeground(Color.WHITE);
        menu.setVisible(true);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    private int selectedIndex = -1;
    private ArrayList<Product> listProduct;

    private JPanel mainPanel;
    private JTextField idAkunField;
    private JTextField usernameField;
    private JTextField hargaAkunField;
    private JTable productTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox<String> platformComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel idAkunLabel;
    private JLabel usernameLabel;
    private JLabel hargaAkunLabel;
    private JLabel platformLabel;
    private JLabel statusPremiumLabel;
    private JLabel statusPenjualLabel;
    private JRadioButton whalerRadioButton;
    private JRadioButton f2pRadioButton;
    private JRadioButton lowSpenderRadioButton;
    private ButtonGroup statusPremiumGroup;
    private JCheckBox verifiedCheckBox;

    public ProductMenu() {
        listProduct = new ArrayList<>();
        populateList();
        productTable.setModel(setTable());

        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));
        titleLabel.setForeground(new Color (255, 255, 255)); // Biru Tua
        Color labelColor = new Color(255, 255, 255); // Abu-abu gelap (hampir hitam)
        idAkunLabel.setForeground(labelColor);
        usernameLabel.setForeground(labelColor);
        hargaAkunLabel.setForeground(labelColor);
        platformLabel.setForeground(labelColor);
        statusPremiumLabel.setForeground(labelColor);
        statusPenjualLabel.setForeground(labelColor);
        String[] platformData = { "???", "Steam", "Mobile Legends", "Valorant", "Genshin Impact", "Free Fire", "Roblox"};
        platformComboBox.setModel(new  DefaultComboBoxModel<>(platformData));

        deleteButton.setVisible(false);

        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1){
                    insertData();
                } else {
                    updateData();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogResult = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus data ini?", "Konfirmasi Hapus",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (dialogResult == JOptionPane.YES_OPTION){
                    deleteData();
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        productTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedIndex = productTable.getSelectedRow();

                String curIdAkun = productTable.getModel().getValueAt(selectedIndex, 1).toString();
                String curUsername = productTable.getModel().getValueAt(selectedIndex, 2).toString();
                String curHargaAkun = productTable.getModel().getValueAt(selectedIndex, 3).toString();
                String curPlatform = productTable.getModel().getValueAt(selectedIndex, 4).toString();
                String curStatusPremium = productTable.getModel().getValueAt(selectedIndex, 5).toString();
                String curIsVerified = productTable.getModel().getValueAt(selectedIndex, 6).toString();

                idAkunField.setText(curIdAkun);
                usernameField.setText(curUsername);
                hargaAkunField.setText(curHargaAkun);
                platformComboBox.setSelectedItem(curPlatform);

                addUpdateButton.setText("Update");
                deleteButton.setVisible(true);

                statusPremiumGroup.clearSelection();
                if ("Whaler".equals(curStatusPremium)){
                    whalerRadioButton.setSelected(true);
                } else if ("F2P".equals(curStatusPremium)) {
                    f2pRadioButton.setSelected(true);
                } else if ("LowSpender".equals(curStatusPremium)) {
                    lowSpenderRadioButton.setSelected(true);
                }
                verifiedCheckBox.setSelected("Yes".equals(curIsVerified));
            }
        });
    }

    public final DefaultTableModel setTable() {
        Object[] cols = {"No", "ID Akun", "Username", "Harga Akun", "Platform", "Status Premium", "Status Penjual"};
        DefaultTableModel tmp = new DefaultTableModel(null, cols);

        for (int i = 0; i < listProduct.size(); i++){
            Object[] row = {
                    i + 1,
                    listProduct.get(i).getId(),
                    listProduct.get(i).getNama(),
                    String.format("%.2f", listProduct.get(i).getHarga()),
                    listProduct.get(i).getKategori(),
                    listProduct.get(i).getStatusPremium(),
                    listProduct.get(i).getIsVerified()
            };
            tmp.addRow(row);
        }
        return tmp;
    }

    // Helper untuk mengambil nilai dari Radio Button Status Premium
    private String getSelectedStatusPremium() {
        if (whalerRadioButton.isSelected()) {
            return "Sultan/Whale";
        } else if (f2pRadioButton.isSelected()) {
            return "F2P";
        } else if (lowSpenderRadioButton.isSelected()) {
            return "Low Spender";
        }
        return "N/A"; // Jika tidak ada yang dipilih
    }

    // Helper untuk mengambil nilai dari CheckBox Status Penjual
    private String getVerifiedStatus() {
        return verifiedCheckBox.isSelected() ? "Yes" : "No";
    }

    public void insertData() {
        try {
            String id = idAkunField.getText();
            String username = usernameField.getText();
            double harga = Double.parseDouble(hargaAkunField.getText());
            String platform = platformComboBox.getSelectedItem().toString();
            String statusPremium = getSelectedStatusPremium();
            String isVerified = getVerifiedStatus();

            listProduct.add(new Product(id, username, harga, platform, statusPremium, isVerified));
            productTable.setModel(setTable());
            clearForm();

            System.out.println("Insert berhasil");
            JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Harga harus berupa angka!", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateData() {
        try {
            String id = idAkunField.getText();
            String username = usernameField.getText();
            double harga = Double.parseDouble(hargaAkunField.getText());
            String platform = platformComboBox.getSelectedItem().toString();
            String statusPremium = getSelectedStatusPremium();
            String isVerified = getVerifiedStatus();

            listProduct.get(selectedIndex).setId(id);
            listProduct.get(selectedIndex).setNama(username);
            listProduct.get(selectedIndex).setHarga(harga);
            listProduct.get(selectedIndex).setKategori(platform);
            listProduct.get(selectedIndex).setStatusPremium(statusPremium);
            listProduct.get(selectedIndex).setIsVerified(isVerified);

            productTable.setModel(setTable());
            clearForm();

            System.out.println("Update berhasil");
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Harga harus berupa angka!", "Error"
            , JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteData() {
        listProduct.remove(selectedIndex);
        productTable.setModel(setTable());
        clearForm();

        System.out.println("Delete berhasil!");
        JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
    }

    public void clearForm() {
        idAkunField.setText("");
        usernameField.setText("");
        hargaAkunField.setText("");
        platformComboBox.setSelectedIndex(0);
        addUpdateButton.setText("Add");
        deleteButton.setVisible(false);
        selectedIndex = -1;
        statusPremiumGroup.clearSelection();
        f2pRadioButton.setSelected(true);
        verifiedCheckBox.setSelected(false);
    }

    private void populateList() {
        listProduct.add(new Product("A001", "ML_ProPlayer99", 250000.0, "Mobile Legends", "Low Spender", "Yes"));
        listProduct.add(new Product("A002", "ValorantAce", 400000.0, "Valorant", "Sultan/Whale", "No"));
        listProduct.add(new Product("A003", "SteamKingdom", 550000.0, "Steam", "F2P", "Yes"));
        listProduct.add(new Product("A004", "GenshinLover", 700000.0, "Genshin Impact", "Sultan/Whale", "Yes"));
        listProduct.add(new Product("A005", "FreeFireNo1", 200000.0, "Free Fire", "Low Spender", "No"));
        listProduct.add(new Product("A006", "RobloxRichKid", 150000.0, "Roblox", "F2P", "Yes"));
        listProduct.add(new Product("A007", "SteamCollector", 600000.0, "Steam", "Low Spender", "Yes"));
        listProduct.add(new Product("A008", "ValorantSmurf", 300000.0, "Valorant", "F2P", "No"));
        listProduct.add(new Product("A009", "ML_Warrior", 180000.0, "Mobile Legends", "Low Spender", "Yes"));
        listProduct.add(new Product("A010", "GenshinWhale", 950000.0, "Genshin Impact", "Sultan/Whale", "Yes"));
        listProduct.add(new Product("A011", "FF_Legendary", 320000.0, "Free Fire", "Low Spender", "Yes"));
        listProduct.add(new Product("A012", "RobloxBuilder", 190000.0, "Roblox", "F2P", "No"));
        listProduct.add(new Product("A013", "ValorantImmortal", 850000.0, "Valorant", "Sultan/Whale", "Yes"));
        listProduct.add(new Product("A014", "ML_MithicGlory", 450000.0, "Mobile Legends", "Low Spender", "Yes"));
        listProduct.add(new Product("A015", "SteamRarities", 720000.0, "Steam", "Sultan/Whale", "No"));
    }
}
