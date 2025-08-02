import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;

public class CensusMain extends JFrame {

    private JTextField txtPersonID, txtFirstName, txtLastName, txtGender, txtRegion, txtDistrict,
            txtCommunity, txtOccupation, txtAge, txtMaritalStatus, txtEducationLevel,
            txtReligion, txtNationality, txtEthnicity, txtHouseholdSize, txtRelationToHead,
            txtOfficerID, txtOfficerName, txtOfficerPosition, txtOfficerContact;

    private JTextArea textAreaOutput;

    public CensusMain() {
        setTitle("Census Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 750);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        formPanel.add(createSection("Officer Information", new Color(230, 255, 230),
                new String[]{"Officer ID", "Officer Name", "Officer Position", "Officer Contact"},
                new JTextField[]{txtOfficerID = new JTextField(), txtOfficerName = new JTextField(),
                        txtOfficerPosition = new JTextField(), txtOfficerContact = new JTextField()}
        ));

        formPanel.add(Box.createVerticalStrut(10));

        formPanel.add(createSection("Personal Information", new Color(255, 255, 210),
                new String[]{"Person ID", "First Name", "Last Name", "Gender", "Age", "Marital Status", "Occupation"},
                new JTextField[]{txtPersonID = new JTextField(), txtFirstName = new JTextField(), txtLastName = new JTextField(),
                        txtGender = new JTextField(), txtAge = new JTextField(), txtMaritalStatus = new JTextField(),
                        txtOccupation = new JTextField()}
        ));

        formPanel.add(Box.createVerticalStrut(10));

        formPanel.add(createSection("Demographic & Address Details", new Color(210, 240, 255),
                new String[]{"Region", "District", "Community", "Education Level", "Religion", "Nationality", "Ethnicity", "Household Size", "Relation to Head"},
                new JTextField[]{txtRegion = new JTextField(), txtDistrict = new JTextField(), txtCommunity = new JTextField(),
                        txtEducationLevel = new JTextField(), txtReligion = new JTextField(), txtNationality = new JTextField(),
                        txtEthnicity = new JTextField(), txtHouseholdSize = new JTextField(), txtRelationToHead = new JTextField()}
        ));

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnInsert = new JButton("Insert");
        JButton btnRetrieve = new JButton("Retrieve");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");

        buttonPanel.add(btnInsert);
        buttonPanel.add(btnRetrieve);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);

        // Output area
        textAreaOutput = new JTextArea(10, 90);
        textAreaOutput.setEditable(false);
        textAreaOutput.setBorder(BorderFactory.createTitledBorder("Retrieved Records"));
        JScrollPane scrollPane = new JScrollPane(textAreaOutput);

        // Add to frame
        add(scrollPane, BorderLayout.NORTH);
        add(new JScrollPane(formPanel), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        btnInsert.addActionListener(e -> insertRecord());
        btnRetrieve.addActionListener(e -> retrieveRecords());
        btnUpdate.addActionListener(e -> updateRecord());
        btnDelete.addActionListener(e -> deleteRecord());
    }

    private JPanel createSection(String title, Color bgColor, String[] labels, JTextField[] fields) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(bgColor);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                title, TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14), Color.DARK_GRAY
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            JLabel lbl = new JLabel(labels[i] + ":");
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            panel.add(lbl, gbc);

            gbc.gridx = 1;
            fields[i].setPreferredSize(new Dimension(250, 25));
            fields[i].setToolTipText("Enter " + labels[i].toLowerCase());
            panel.add(fields[i], gbc);
        }
        return panel;
    }

    private CensusRecord collectInput() {
        CensusRecord record = new CensusRecord();
        record.setOfficerID(txtOfficerID.getText());
        record.setOfficerName(txtOfficerName.getText());
        record.setOfficerPosition(txtOfficerPosition.getText());
        record.setOfficerContact(txtOfficerContact.getText());
        record.setPersonID(txtPersonID.getText());
        record.setFirstName(txtFirstName.getText());
        record.setLastName(txtLastName.getText());
        record.setGender(txtGender.getText());
        record.setRegion(txtRegion.getText());
        record.setDistrict(txtDistrict.getText());
        record.setCommunity(txtCommunity.getText());
        record.setOccupation(txtOccupation.getText());
        record.setAge(Integer.parseInt(txtAge.getText()));
        record.setMaritalStatus(txtMaritalStatus.getText());
        record.setEducationLevel(txtEducationLevel.getText());
        record.setReligion(txtReligion.getText());
        record.setNationality(txtNationality.getText());
        record.setEthnicity(txtEthnicity.getText());
        record.setHouseholdSize(txtHouseholdSize.getText());
        record.setRelationshipToHead(txtRelationToHead.getText());
        return record;
    }

    private void insertRecord() {
        try {
            CensusDBService service = new CensusDBService();
            CensusRecord record = collectInput();
            CensusOperationResult result = service.insertCensusRecord(record);
            service.closeConnection();
            showResult(result);
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void retrieveRecords() {
        try {
            String criteria = JOptionPane.showInputDialog(this, "Enter search criteria (e.g., region):");
            String value = JOptionPane.showInputDialog(this, "Enter search value:");
            CensusDBService service = new CensusDBService();
            CensusOperationResult result = service.retrieveCensusRecords(criteria, value);
            service.closeConnection();

            if (result.isSuccess()) {
                List<CensusRecord> records = result.getRecords();
                textAreaOutput.setText("");
                for (CensusRecord rec : records) {
                    textAreaOutput.append(rec.toString() + "\n\n");
                }
            } else {
                showResult(result);
            }
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void updateRecord() {
        try {
            String recordID = txtPersonID.getText();
            if (recordID.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Person ID required for update");
                return;
            }
            CensusRecord record = collectInput();
            CensusDBService service = new CensusDBService();
            CensusOperationResult result = service.updateCensusRecord(recordID, record);
            service.closeConnection();
            showResult(result);
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void deleteRecord() {
        try {
            String recordID = txtPersonID.getText();
            if (recordID.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Person ID required for delete");
                return;
            }
            CensusDBService service = new CensusDBService();
            CensusOperationResult result = service.deleteCensusRecord(recordID, "person");
            service.closeConnection();
            showResult(result);
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void showResult(CensusOperationResult result) {
        JOptionPane.showMessageDialog(this, result.getMessage());
    }

    private void showError(Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CensusMain().setVisible(true));
    }
}
