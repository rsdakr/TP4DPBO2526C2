import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PeopleMenu {
    private JTable personTable;
    private JButton deleteButton;
    private JButton addUpdateButton;
    private JLabel titleLabel;
    private JButton cancelButton;
    private JPanel mainPanel;
    private JTextField idField;
    private JTextField namaField;
    private JTextField tahunField;
    private JComboBox kategoriComboBox;
    private JRadioButton yaRadioButton;
    private JRadioButton tidakRadioButton;
    private ArrayList<Person> listPerson;
    private int selectedIndex;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Product Menu");
        PeopleMenu menu = new PeopleMenu();

        frame.setSize(700, 600);

        frame.setLocationRelativeTo(null);

        frame.setContentPane(menu.mainPanel);

        frame.getContentPane().setBackground(Color.WHITE);

        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public PeopleMenu() {
        listPerson = new ArrayList<>();

        populateList();

        selectedIndex = -1;

        ButtonGroup yesNoGroup = new ButtonGroup();
        yesNoGroup.add(yaRadioButton);
        yesNoGroup.add(tidakRadioButton);
        yaRadioButton.setSelected(true);

        personTable.setModel(setTable());

        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        String[] kategoriData = { "????", "Ilmu Komputer", "Musik", "Akuntansi", "Sastra Inggris", "Arsitektur" };
        kategoriComboBox.setModel(new DefaultComboBoxModel<>(kategoriData));

        deleteButton.setVisible(false);

        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menghapus?") == 0) {
                    deleteData();
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                clearForm();
            }
        });

        personTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedIndex = personTable.getSelectedRow();

                String curId = personTable.getModel().getValueAt(selectedIndex, 1).toString();
                String curNama = personTable.getModel().getValueAt(selectedIndex, 2).toString();
                String curTahun = personTable.getModel().getValueAt(selectedIndex, 3).toString();
                String curKategori = personTable.getModel().getValueAt(selectedIndex, 4).toString();

                idField.setText(curId);
                namaField.setText(curNama);
                tahunField.setText(curTahun);
                kategoriComboBox.setSelectedItem(curKategori);
                if (personTable.getModel().getValueAt(selectedIndex, 5).toString().equals("Ya")) {
                    yaRadioButton.setSelected(true);
                    tidakRadioButton.setSelected(false);
                } else {
                    yaRadioButton.setSelected(false);
                    tidakRadioButton.setSelected(true);
                }

                addUpdateButton.setText("update");
                deleteButton.setVisible(true);
            }
        });
    }

    public void populateList() {
        listPerson.add(new Person("2501001", "Budi Santoso", 2005, "Ilmu Komputer", true));
        listPerson.add(new Person("2501002", "Siti Nurhaliza", 2006, "Sastra Inggris", true));
        listPerson.add(new Person("2501003", "Rian Hidayat", 2004, "Arsitektur", false));
        listPerson.add(new Person("2501004", "Dewi Lestari", 2005, "Akuntansi", true));
        listPerson.add(new Person("2501005", "Ahmad Pratama", 2006, "Fisika", true));
        listPerson.add(new Person("2501006", "Clarissa Amanda", 2005, "Musik", false));
    }

    public final DefaultTableModel setTable() {
        Object[] cols = { "No", "ID (NIM)", "Nama", "Tahun Lahir", "Kategori (Prodi)", "Aktif" };

        DefaultTableModel tmp = new DefaultTableModel(null, cols);

        for (int i = 0; i < listPerson.size(); i++) {
            Object[] row = {
                    i + 1,
                    listPerson.get(i).getId(),
                    listPerson.get(i).getNama(),
                    String.format("%d", listPerson.get(i).getTahun()),
                    listPerson.get(i).getKategori(),
                    listPerson.get(i).getAktif() ? "Ya" : "Tidak"
            };
            tmp.addRow(row);
        }

        return tmp;
    }

    public void insertData() {
        try {
            String id = idField.getText();
            String nama = namaField.getText();
            int tahun = Integer.parseInt(tahunField.getText());
            String kategori = kategoriComboBox.getSelectedItem().toString();
            boolean aktif = yaRadioButton.isSelected();

            listPerson.add(new Person(id, nama, tahun, kategori, aktif));

            personTable.setModel(setTable());

            clearForm();

            System.out.println("Insert Berhasil");
            JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Tahun harus berupa angka bulat!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateData() {
        try {
            String id = idField.getText();
            String nama = namaField.getText();
            int tahun = Integer.parseInt(tahunField.getText());
            String kategori = kategoriComboBox.getSelectedItem().toString();
            boolean aktif = yaRadioButton.isSelected();

            listPerson.get(selectedIndex).setId(id);
            listPerson.get(selectedIndex).setNama(nama);
            listPerson.get(selectedIndex).setTahun(tahun);
            listPerson.get(selectedIndex).setKategori(kategori);
            listPerson.get(selectedIndex).setAktif(aktif);

            personTable.setModel(setTable());

            clearForm();

            System.out.println("Update Berhasil");
            JOptionPane.showMessageDialog(null, "Data berhasil diubah.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Tahun harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteData() {
        listPerson.remove(selectedIndex);

        personTable.setModel(setTable());

        clearForm();

        System.out.println("Delete Berhasil");
        JOptionPane.showMessageDialog(null, "Data berhasil dihapus.");
    }

    public void clearForm() {
        idField.setText("");
        namaField.setText("");
        tahunField.setText("");
        kategoriComboBox.setSelectedIndex(0);
        yaRadioButton.setSelected(true);
        tidakRadioButton.setSelected(false);

        addUpdateButton.setText("add");

        deleteButton.setVisible(false);

        selectedIndex = -1;
    }
}