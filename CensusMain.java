// FULLY FIXED CODE
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

public class CensusMain {

    private JFrame frame;
    private final JTextField[] textFields = new JTextField[33];
    private final java.util.Map<String, JComponent> fieldComponents = new java.util.HashMap<>();
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
                new String[]{"Address ID", "Region", "District", "Community", "Street Name", "House Number", "GPS Location"}, fieldIdx);

        fieldIdx = createSection(inputPanel, "Education", new Color(245, 245, 245),
                new String[]{"Education ID", "Education Level", "School Name", "Year Completed"}, fieldIdx);

        fieldIdx = createSection(inputPanel, "Employment", new Color(240, 255, 255),
                new String[]{"Employment ID", "Employment Status", "Employer Name"}, fieldIdx);

        fieldIdx = createSection(inputPanel, "Household Info", new Color(255, 230, 230),
                new String[]{"Household ID", "Head ID", "Household Size", "Relationship to Head"}, fieldIdx);

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
            }
        });

        btnUpdate.addActionListener(evt -> {
            try {
                updateRecord();
            } catch (SQLException ex) {
                txtOutput.setText("Update Error: " + ex.getMessage());
            }
        });

        btnRetrieve.addActionListener(evt -> {
            try {
                retrieveRecord();
            } catch (SQLException ex) {
                txtOutput.setText("Retrieve Error: " + ex.getMessage());
            }
        });

        btnDelete.addActionListener(evt -> {
            try {
                deleteRecord();
            } catch (SQLException ex) {
                txtOutput.setText("Delete Error: " + ex.getMessage());
            }
        });
    }

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

            JComponent inputField;
           inputField = switch (label.toLowerCase()) {
    case "gender" -> new JComboBox<>(new String[]{"Male", "Female", "Other"});
    
    case "age" -> {
        String[] ages = new String[121];
        for (int i = 0; i <= 120; i++) ages[i] = String.valueOf(i);
        yield new JComboBox<>(ages);
    }

    case "marital status" -> new JComboBox<>(new String[]{"Single", "Married", "Divorced", "Widowed"});

    case "education level" -> new JComboBox<>(new String[]{"None", "Primary", "JHS", "SHS", "Tertiary", "Postgraduate"});

    case "employment status" -> new JComboBox<>(new String[]{"Employed", "Unemployed", "Self-Employed", "Student"});

    case "date of birth" -> {
        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner spinner = new JSpinner(dateModel);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "yyyy-MM-dd");
        spinner.setEditor(editor);
        yield spinner;
    }

    default -> {
        JTextField tf = new JTextField();
        tf.setPreferredSize(new Dimension(250, 25));
        tf.setToolTipText("Enter " + label.toLowerCase());
        textFields[fieldIdx] = tf;
        yield tf;
    }
};


            inputField.setPreferredSize(new Dimension(250, 25));
            panel.add(inputField, gbc);
            fieldComponents.put(label.toLowerCase(), inputField);
            fieldIdx++;
        }
        parent.add(panel);
        return fieldIdx;
    }

   private String getValue(String key) {
    JComponent comp = fieldComponents.get(key);

    return switch (comp) {
        case JTextField tf -> tf.getText();
        case JComboBox<?> cb -> String.valueOf(cb.getSelectedItem());
        case JSpinner spinner -> new java.text.SimpleDateFormat("yyyy-MM-dd").format(spinner.getValue());
        default -> "";
    };
}

   private void setValue(String key, String value) {
    JComponent comp = fieldComponents.get(key);

    try {
        switch (comp) {
            case JTextField tf -> tf.setText(value);
            case JComboBox<?> cb -> cb.setSelectedItem(value);
            case JSpinner spinner -> {
                java.util.Date date = java.sql.Date.valueOf(value);
                spinner.setValue(date);
            }
            default -> txtOutput.setText("Unsupported component for key: " + key);
        }
    } catch (Exception e) {
        txtOutput.setText("Date parse error for " + key + ": " + e.getMessage());
    }
}


    private CensusRecord collectInput() {
        CensusRecord record = new CensusRecord();
        try {
            record.setOfficerID(Integer.parseInt(textFields[0].getText()));
            record.setOfficerName(textFields[1].getText());
            record.setOfficerPosition(textFields[2].getText());
            record.setOfficerContact(textFields[3].getText());

            record.setPersonID(Integer.parseInt(textFields[4].getText()));
            record.setFirstName(textFields[5].getText());
            record.setLastName(textFields[6].getText());
            record.setGender(getValue("gender"));
            record.setDateOfBirth(Date.valueOf(getValue("date of birth")));
            record.setAge(Integer.parseInt(getValue("age")));
            record.setMaritalStatus(getValue("marital status"));
            record.setOccupation(textFields[11].getText());
            record.setReligion(textFields[12].getText());
            record.setNationality(textFields[13].getText());
            record.setEthnicity(textFields[14].getText());

            record.setRegion(textFields[16].getText());
            record.setDistrict(textFields[17].getText());
            record.setCommunity(textFields[18].getText());
            record.setStreetName(textFields[19].getText());
            record.setHouseNumber(textFields[20].getText());
            record.setGpsLocation(textFields[21].getText());

            record.setEducationID(Integer.parseInt(textFields[22].getText()));
            record.setEducationLevel(getValue("education level"));
            record.setSchoolName(textFields[24].getText());
            record.setYearCompleted(textFields[25].getText());

            record.setEmploymentID(Integer.parseInt(textFields[26].getText()));
            record.setEmploymentStatus(getValue("employment status"));
            record.setEmployerName(textFields[28].getText());

            record.setHouseholdID(Integer.parseInt(textFields[29].getText()));
            record.setHeadID(textFields[30].getText());
            record.setHouseholdSize(Integer.parseInt(textFields[31].getText()));
            record.setRelationToHead(textFields[32].getText());

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

        CensusRecord record = records.get(0);
        populateFields(record);
        txtOutput.setText("Record retrieved successfully.");
    }

    private void populateFields(CensusRecord record) {
        textFields[0].setText(String.valueOf(record.getOfficerID()));
        textFields[1].setText(record.getOfficerName());
        textFields[2].setText(record.getOfficerPosition());
        textFields[3].setText(record.getOfficerContact());

        textFields[4].setText(String.valueOf(record.getPersonID()));
        textFields[5].setText(record.getFirstName());
        textFields[6].setText(record.getLastName());
        setValue("gender", record.getGender());
        setValue("date of birth", record.getDateOfBirth() != null ? record.getDateOfBirth().toString() : "");
        setValue("age", String.valueOf(record.getAge()));
        setValue("marital status", record.getMaritalStatus());
        textFields[11].setText(record.getOccupation());
        textFields[12].setText(record.getReligion());
        textFields[13].setText(record.getNationality());
        textFields[14].setText(record.getEthnicity());

        textFields[16].setText(record.getRegion());
        textFields[17].setText(record.getDistrict());
        textFields[18].setText(record.getCommunity());
        textFields[19].setText(record.getStreetName());
        textFields[20].setText(record.getHouseNumber());
        textFields[21].setText(record.getGpsLocation());

        textFields[22].setText(String.valueOf(record.getEducationID()));
        setValue("education level", record.getEducationLevel());
        textFields[24].setText(record.getSchoolName());
        textFields[25].setText(record.getYearCompleted());

        textFields[26].setText(String.valueOf(record.getEmploymentID()));
        setValue("employment status", record.getEmploymentStatus());
        textFields[28].setText(record.getEmployerName());

        textFields[29].setText(String.valueOf(record.getHouseholdID()));
        textFields[30].setText(record.getHeadID());
        textFields[31].setText(String.valueOf(record.getHouseholdSize()));
        textFields[32].setText(record.getRelationToHead());
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
}
