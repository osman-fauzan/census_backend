// All problems corrected, including all missing getters/setters and correct method usage in CensusRecord

import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

public class CensusMain {

    private JFrame frame;
    private JTextField[] textFields = new JTextField[32];
    private JTextArea txtOutput;
    private JTable recordsTable;
    private DefaultTableModel tableModel;

    private final CensusDBService dbService;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                CensusMain window = new CensusMain();
                window.frame.setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Startup failed: " + e.getMessage());
            }
        });
    }

    public CensusMain() throws SQLException {
        dbService = new CensusDBService();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Census Management System");
        frame.setBounds(100, 100, 1100, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        frame.getContentPane().add(tabbedPane);

        JPanel formPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        int fieldIdx = 0;
        fieldIdx = createSection(inputPanel, "Officer Information", new Color(230, 255, 230),
                new String[]{"Officer ID", "Officer Name", "Officer Position", "Officer Contact"}, fieldIdx);

        fieldIdx = createSection(inputPanel, "Personal Information", new Color(255, 255, 210),
                new String[]{"Person ID", "First Name", "Last Name", "Gender", "Date of Birth", "Age",
                        "Marital Status", "Occupation", "Religion", "Nationality", "Ethnicity"}, fieldIdx);

        fieldIdx = createSection(inputPanel, "Address Details", new Color(210, 240, 255),
                new String[]{"Region", "District", "Community", "Street Name", "House Number", "GPS Location"}, fieldIdx);

        fieldIdx = createSection(inputPanel, "Education", new Color(245, 245, 245),
                new String[]{"Education ID", "Education Level", "School Name", "Year Completed"}, fieldIdx);

        fieldIdx = createSection(inputPanel, "Employment", new Color(240, 255, 255),
                new String[]{"Employment ID", "Employment Status", "Employer Name"}, fieldIdx);

        fieldIdx = createSection(inputPanel, "Household Info", new Color(255, 230, 230),
                new String[]{"Household ID", "Head ID", "Household Size", "Relation to Head"}, fieldIdx);

        formPanel.add(new JScrollPane(inputPanel), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnInsert = new JButton("Insert");
        JButton btnUpdate = new JButton("Update");
        JButton btnRetrieve = new JButton("Retrieve");
        JButton btnDelete = new JButton("Delete");

        buttonPanel.add(btnInsert);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnRetrieve);
        buttonPanel.add(btnDelete);

        txtOutput = new JTextArea(6, 100);
        txtOutput.setEditable(false);
        txtOutput.setBorder(BorderFactory.createTitledBorder("Output"));

        formPanel.add(new JScrollPane(txtOutput), BorderLayout.NORTH);
        formPanel.add(buttonPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Census Form", formPanel);

        // Table View Tab
        JPanel viewPanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel();
        recordsTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(recordsTable);
        viewPanel.add(tableScrollPane, BorderLayout.CENTER);

        JButton btnLoadRecords = new JButton("Load Records");
        btnLoadRecords.addActionListener(evt -> loadRecords());
        viewPanel.add(btnLoadRecords, BorderLayout.SOUTH);

        tabbedPane.addTab("All Records", viewPanel);

        btnInsert.addActionListener(evt -> {
            try {
                insertRecord();
            } catch (SQLException ex) {
                txtOutput.setText("Insert Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        btnUpdate.addActionListener(evt -> {
            try {
                updateRecord();
            } catch (SQLException ex) {
                txtOutput.setText("Update Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        btnRetrieve.addActionListener(evt -> {
            try {
                retrieveRecord();
            } catch (SQLException ex) {
                txtOutput.setText("Retrieve Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        btnDelete.addActionListener(evt -> {
            try {
                deleteRecord();
            } catch (SQLException ex) {
                txtOutput.setText("Delete Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }

    /**
     * Adds a section with labeled fields to the panel, and fills textFields[] sequentially.
     * @return index for next available field in textFields[]
     */
    private int createSection(JPanel parent, String title, Color bgColor, String[] labels, int fieldIdx) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(bgColor);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                title, TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14), Color.DARK_GRAY));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for (String label : labels) {
            gbc.gridx = 0;
            gbc.gridy = fieldIdx % labels.length;
            JLabel lbl = new JLabel(label + ":");
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            panel.add(lbl, gbc);

            gbc.gridx = 1;
            JTextField tf = new JTextField();
            tf.setPreferredSize(new Dimension(250, 25));
            tf.setToolTipText("Enter " + label.toLowerCase());
            panel.add(tf, gbc);

            if (fieldIdx < textFields.length)
                textFields[fieldIdx++] = tf;
        }
        parent.add(panel);
        return fieldIdx;
    }

    private CensusRecord collectInput() {
        CensusRecord record = new CensusRecord();
        try {
            record.setOfficerID(textFields[0].getText());
            record.setOfficerName(textFields[1].getText());
            record.setOfficerPosition(textFields[2].getText());
            record.setOfficerContact(textFields[3].getText());

            record.setPersonID(textFields[4].getText());
            record.setFirstName(textFields[5].getText());
            record.setLastName(textFields[6].getText());
            record.setGender(textFields[7].getText());
            record.setDateOfBirth(parseDateSafe(textFields[8].getText()));
            record.setAge(textFields[9].getText());
            record.setMaritalStatus(textFields[10].getText());
            record.setOccupation(textFields[11].getText());
            record.setReligion(textFields[12].getText());
            record.setNationality(textFields[13].getText());
            record.setEthnicity(textFields[14].getText());

            record.setRegion(textFields[15].getText());
            record.setDistrict(textFields[16].getText());
            record.setCommunity(textFields[17].getText());
            record.setStreetName(textFields[18].getText());
            record.setHouseNumber(textFields[19].getText());
            record.setGpsLocation(textFields[20].getText());

            record.setEducationID(textFields[21].getText());
            record.setEducationLevel(textFields[22].getText());
            record.setSchoolName(textFields[23].getText());
            record.setYearCompleted(textFields[24].getText());

            record.setEmploymentID(textFields[25].getText());
            record.setEmploymentStatus(textFields[26].getText());
            record.setEmployerName(textFields[27].getText());

            record.setHouseholdID(textFields[28].getText());
            record.setHeadID(textFields[29].getText());
            record.setHouseholdSize(textFields[30].getText());
            record.setRelationToHead(textFields[31].getText());
        } catch (Exception ex) {
            txtOutput.setText("Error collecting input: " + ex.getMessage());
        }
        return record;
    }

    private void insertRecord() throws SQLException {
        CensusRecord record = collectInput();
        CensusOperationResult result = dbService.insertCensusRecord(record);
        txtOutput.setText(result.getMessage());
    }

    private void updateRecord() throws SQLException {
        CensusRecord record = collectInput();
        CensusOperationResult result = dbService.updateCensusRecord(record.getPersonID(), record);
        txtOutput.setText(result.getMessage());
    }

    private void retrieveRecord() throws SQLException {
        String officerId = JOptionPane.showInputDialog("Enter Officer ID:");
        String personId = JOptionPane.showInputDialog("Enter Person ID:");

        if (officerId == null || personId == null || officerId.isEmpty() || personId.isEmpty()) {
            txtOutput.setText("Officer ID and Person ID are required.");
            return;
        }

        CensusOperationResult result = dbService.retrieveCensusRecords(officerId, personId);
        if (!result.isSuccess()) {
            txtOutput.setText("Retrieve failed: " + result.getMessage());
            return;
        }

        List<CensusRecord> records = result.getRecords();
        if (records == null || records.isEmpty()) {
            txtOutput.setText("No matching record found.");
            return;
        }

        CensusRecord record = records.get(0); // Assuming you want the first matching record
        populateFields(record);
        txtOutput.setText("Record retrieved successfully.");
    }

    private void populateFields(CensusRecord record) {
        textFields[0].setText(record.getOfficerID());
        textFields[1].setText(record.getOfficerName());
        textFields[2].setText(record.getOfficerPosition());
        textFields[3].setText(record.getOfficerContact());

        textFields[4].setText(record.getPersonID());
        textFields[5].setText(record.getFirstName());
        textFields[6].setText(record.getLastName());
        textFields[7].setText(record.getGender());
        textFields[8].setText(record.getDateOfBirth() != null ? record.getDateOfBirth().toString() : "");
        textFields[9].setText(record.getAge());
        textFields[10].setText(record.getMaritalStatus());
        textFields[11].setText(record.getOccupation());
        textFields[12].setText(record.getReligion());
        textFields[13].setText(record.getNationality());
        textFields[14].setText(record.getEthnicity());

        textFields[15].setText(record.getRegion());
        textFields[16].setText(record.getDistrict());
        textFields[17].setText(record.getCommunity());
        textFields[18].setText(record.getStreetName());
        textFields[19].setText(record.getHouseNumber());
        textFields[20].setText(record.getGpsLocation());

        textFields[21].setText(record.getEducationID());
        textFields[22].setText(record.getEducationLevel());
        textFields[23].setText(record.getSchoolName());
        textFields[24].setText(record.getYearCompleted());

        textFields[25].setText(record.getEmploymentID());
        textFields[26].setText(record.getEmploymentStatus());
        textFields[27].setText(record.getEmployerName());

        textFields[28].setText(record.getHouseholdID());
        textFields[29].setText(record.getHeadID());
        textFields[30].setText(record.getHouseholdSize());
        textFields[31].setText(record.getRelationToHead());
    }

    private void deleteRecord() throws SQLException {
        String personID = textFields[4].getText();
        CensusOperationResult result = dbService.deleteCensusRecord(personID, "person");
        txtOutput.setText(result.getMessage());
    }

    private void loadRecords() {
        try {
            CensusOperationResult result = dbService.retrieveCensusRecords("all", "");
            if (result.isSuccess()) {
                List<CensusRecord> records = result.getRecords();
                updateTable(records);
            } else {
                txtOutput.setText(result.getMessage());
            }
        } catch (Exception e) {
            txtOutput.setText("Load Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateTable(List<CensusRecord> records) {
        String[] columns = {"Person ID", "First Name", "Last Name", "Region", "District", "Occupation"};
        tableModel.setDataVector(null, columns);
        for (CensusRecord r : records) {
            tableModel.addRow(new Object[]{
                    r.getPersonID(), r.getFirstName(), r.getLastName(),
                    r.getRegion(), r.getDistrict(), r.getOccupation()
            });
        }
    }

    private Date parseDateSafe(String text) {
        try {
            return Date.valueOf(text);
        } catch (Exception e) {
            return null;
        }
    }
}
