package KlinikApps;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DataPasienApp {
    private List<Patient> patients = new ArrayList<>();
    private JFrame frame;
    private DefaultTableModel tableModel;
    private JTable patientTable;
    private JTextField nameField;
    private JTextField birthDateField;
    private JTextField addressField;
    private JTextField noTelpField;
    private int selectedIndex = -1; // Track the selected index for updating

    public DataPasienApp() {
        frame = new JFrame("Hospital Patient Registration");
        
        JPanel formPanel = new JPanel(new GridLayout(8, 2));
        nameField = new JTextField();
        birthDateField = new JTextField();
        addressField = new JTextField();
        noTelpField = new JTextField();
        JButton registerButton = new JButton("Register Patient");
        JButton deleteButton = new JButton("Delete Patient");
        JButton updateButton = new JButton("Update Patient");
        JButton clearButton = new JButton("Clear Data");
        JButton nextButton = new JButton("Next");
        JButton prevButton = new JButton("Prev");
        JButton listButton = new JButton("List Patient");
        JButton closeButton = new JButton("Close");
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Birth Date (yyyy-MMM-dd):"));
        formPanel.add(birthDateField);
        formPanel.add(new JLabel("Address:"));
        formPanel.add(addressField);
        formPanel.add(new JLabel("No. Telp:"));
        formPanel.add(noTelpField);
        formPanel.add(registerButton);
        formPanel.add(clearButton);
        formPanel.add(deleteButton);
        formPanel.add(updateButton);
        formPanel.add(prevButton);
        formPanel.add(nextButton);
        formPanel.add(listButton);
        formPanel.add(closeButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                Date birthDate = parseDate(birthDateField.getText());
                String address = addressField.getText();
                String noTelp = noTelpField.getText();

                if (birthDate != null) {
                    Patient patient = new Patient(name, birthDate, address, noTelp);
                    patients.add(patient);
                    clearFormFields();
                    selectedIndex = patients.size();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid birth date format. Please use yyyy-MMM-dd.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex >= 0) {
                    patients.remove(selectedIndex);
                    selectedIndex -= 1;
                    showPatient(selectedIndex);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex >= 0) {
                    String name = nameField.getText();
                    Date birthDate = parseDate(birthDateField.getText());
                    String address = addressField.getText();
                    String noTelp = noTelpField.getText();
                    Patient patient = patients.get(selectedIndex);
                    patient.update(name, birthDate, address, noTelp);
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFormFields();
                selectedIndex = -1;
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPatient(selectedIndex + 1);
            }
        });

        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPatient(selectedIndex - 1);
            }
        });

        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog(frame, "List Pasien", false);

                JPanel tablePanel = new JPanel(new BorderLayout());
                tableModel = new DefaultTableModel();
                tableModel.addColumn("Reg. Number");
                tableModel.addColumn("Name");
                tableModel.addColumn("Birth Date");
                tableModel.addColumn("Address");
                tableModel.addColumn("No. Telp");
                patientTable = new JTable(tableModel);
                JScrollPane tableScrollPane = new JScrollPane(patientTable);
                tablePanel.add(tableScrollPane, BorderLayout.CENTER);

                for (Patient patient : patients) {       
                    tableModel.addRow(new Object[]{patient.getRegisterNumber(), patient.getName(), patient.getFormattedBirthDate(), patient.getAddress(), patient.getNoTelp()});
                }

                dialog.getContentPane().add(tablePanel);
                dialog.pack();
                dialog.setVisible(true);
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        frame.add(formPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }
        
    private void clearFormFields() {
        nameField.setText("");
        birthDateField.setText("");
        addressField.setText("");
        noTelpField.setText("");
    }

    private void showPatient(int currentIndex) {
        if (currentIndex + 1 > patients.size() || currentIndex < 0)
            return;

        Patient selectedPatient = patients.get(currentIndex);
        nameField.setText(selectedPatient.getName());
        birthDateField.setText(selectedPatient.getFormattedBirthDate());
        addressField.setText(selectedPatient.getAddress());
        noTelpField.setText(selectedPatient.getNoTelp());

        selectedIndex = currentIndex;
    }

    private Date parseDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd");
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DataPasienApp();
            }
        });
    }
}