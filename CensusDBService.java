import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
// import CensusOperationResult; // Removed or replace with the correct package if needed, e.g.:


public class CensusDBService {

    private Connection connection;

    public CensusDBService() throws SQLException {
        try {
            Properties prop = new Properties();
            try (InputStream input = new FileInputStream("db.properties")) {
                prop.load(input);
            }
            String dbUrl = prop.getProperty("db.url");
            String dbUser = prop.getProperty("db.user");
            String dbPassword = prop.getProperty("db.password");
            String dbDriver = prop.getProperty("db.driver");
            Class.forName(dbDriver);
            this.connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            this.connection.setAutoCommit(false);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        } catch (IOException e) {
            throw new SQLException("Could not read db.properties file", e);
        }
    }

    public CensusOperationResult insertCensusRecord(CensusRecord record) {
        CallableStatement stmt = null;
        try {
            stmt = connection.prepareCall("{CALL sp_insert_census_record(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            // Set parameters as before...
            stmt.setString(1, record.getOfficerID());
            stmt.setString(2, record.getOfficerName());
            stmt.setString(3, record.getOfficerPosition());
            stmt.setString(4, record.getOfficerContact());
            stmt.setString(5, record.getPersonID());
            stmt.setString(6, record.getFirstName());
            stmt.setString(7, record.getLastName());
            stmt.setString(8, record.getGender());
            stmt.setDate(9, record.getDateOfBirth());
            stmt.setString(10, record.getAddressID());
            stmt.setString(11, record.getRegion());
            stmt.setString(12, record.getDistrict());
            stmt.setString(13, record.getCommunity());
            stmt.setString(14, record.getHouseNumber());
            stmt.setString(15, record.getStreetName());
            stmt.setString(16, record.getGpsLocation());
            stmt.setString(17, record.getEducationID());
            stmt.setString(18, record.getEducationLevel());
            stmt.setString(19, record.getSchoolName());
            stmt.setString(20, record.getYearCompleted());
            stmt.setString(21, record.getEmploymentID());
            stmt.setString(22, record.getOccupation());
            stmt.setString(23, record.getEmployerName());
            stmt.setString(24, record.getEmploymentStatus());
            stmt.setString(25, record.getHouseholdID());
            stmt.setString(26, record.getRelationshipToHead());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                connection.commit();
                return new CensusOperationResult(true, "Census record inserted successfully", null);
            } else {
                connection.rollback();
                return new CensusOperationResult(false, "Insert operation failed, no rows affected.", null);
            }
        } catch (SQLException e) {
            try { connection.rollback(); } catch (SQLException ignored) {}
            return new CensusOperationResult(false, "Database error during insert: " + e.getMessage(), null);
        } finally {
            closeStatement(stmt);
        }
    }

    public CensusOperationResult retrieveCensusRecords(String searchCriteria, String searchValue) {
        CallableStatement stmt = null;
        List<CensusRecord> records = new ArrayList<>();
        try {
            stmt = connection.prepareCall("{CALL sp_retrieve_census_records(?, ?)}");
            stmt.setString(1, searchCriteria);
            stmt.setString(2, searchValue);
            boolean hasResultSet = stmt.execute();
            if (hasResultSet) {
                try (ResultSet rs = stmt.getResultSet()) {
                    while (rs.next()) {
                        CensusRecord record = new CensusRecord();
                        record.setOfficerID(rs.getString("officer_id"));
                        record.setOfficerName(rs.getString("officer_name"));
                        record.setOfficerPosition(rs.getString("officer_position"));
                        record.setOfficerContact(rs.getString("officer_contact"));
                        record.setPersonID(rs.getString("person_id"));
                        record.setFirstName(rs.getString("first_name"));
                        record.setLastName(rs.getString("last_name"));
                        record.setGender(rs.getString("gender"));
                        record.setDateOfBirth(rs.getDate("date_of_birth"));
                        record.setAddressID(rs.getString("address_id"));
                        record.setRegion(rs.getString("region"));
                        record.setDistrict(rs.getString("district"));
                        record.setCommunity(rs.getString("community"));
                        record.setHouseNumber(rs.getString("house_number"));
                        record.setStreetName(rs.getString("street_name"));
                        record.setGpsLocation(rs.getString("gps_location"));
                        record.setEducationID(rs.getString("education_id"));
                        record.setEducationLevel(rs.getString("education_level"));
                        record.setSchoolName(rs.getString("school_name"));
                        record.setYearCompleted(rs.getString("year_completed"));
                        record.setEmploymentID(rs.getString("employment_id"));
                        record.setOccupation(rs.getString("occupation"));
                        record.setEmployerName(rs.getString("employer_name"));
                        record.setEmploymentStatus(rs.getString("employment_status"));
                        record.setHouseholdID(rs.getString("household_id"));
                        record.setRelationshipToHead(rs.getString("relationship_to_head"));
                        records.add(record);
                    }
                }
                return new CensusOperationResult(true, "Retrieved " + records.size() + " record(s)", records);
            }
            return new CensusOperationResult(false, "No records found", null);
        } catch (SQLException e) {
            return new CensusOperationResult(false, "Database error during retrieve: " + e.getMessage(), null);
        } finally {
            closeStatement(stmt);
        }
    }

    public CensusOperationResult updateCensusRecord(String recordID, CensusRecord updatedRecord) {
        CallableStatement stmt = null;
        try {
            stmt = connection.prepareCall("{CALL sp_update_census_record(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            stmt.setString(1, recordID);
            stmt.setString(2, updatedRecord.getOfficerID());
            stmt.setString(3, updatedRecord.getOfficerName());
            stmt.setString(4, updatedRecord.getOfficerPosition());
            stmt.setString(5, updatedRecord.getOfficerContact());
            stmt.setString(6, updatedRecord.getPersonID());
            stmt.setString(7, updatedRecord.getFirstName());
            stmt.setString(8, updatedRecord.getLastName());
            stmt.setString(9, updatedRecord.getGender());
            stmt.setDate(10, updatedRecord.getDateOfBirth());
            stmt.setString(11, updatedRecord.getAddressID());
            stmt.setString(12, updatedRecord.getRegion());
            stmt.setString(13, updatedRecord.getDistrict());
            stmt.setString(14, updatedRecord.getCommunity());
            stmt.setString(15, updatedRecord.getHouseNumber());
            stmt.setString(16, updatedRecord.getStreetName());
            stmt.setString(17, updatedRecord.getGpsLocation());
            stmt.setString(18, updatedRecord.getEducationID());
            stmt.setString(19, updatedRecord.getEducationLevel());
            stmt.setString(20, updatedRecord.getSchoolName());
            stmt.setString(21, updatedRecord.getYearCompleted());
            stmt.setString(22, updatedRecord.getEmploymentID());
            stmt.setString(23, updatedRecord.getOccupation());
            stmt.setString(24, updatedRecord.getEmployerName());
            stmt.setString(25, updatedRecord.getEmploymentStatus());
            stmt.setString(26, updatedRecord.getHouseholdID());
            stmt.setString(27, updatedRecord.getRelationshipToHead());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                connection.commit();
                return new CensusOperationResult(true, "Census record updated successfully", null);
            } else {
                connection.rollback();
                return new CensusOperationResult(false, "Update operation failed, no rows affected.", null);
            }
        } catch (SQLException e) {
            try { connection.rollback(); } catch (SQLException ignored) {}
            return new CensusOperationResult(false, "Database error during update: " + e.getMessage(), null);
        } finally {
            closeStatement(stmt);
        }
    }

    public CensusOperationResult deleteCensusRecord(String recordID, String recordType) {
        CallableStatement stmt = null;
        try {
            stmt = connection.prepareCall("{CALL sp_delete_census_record(?, ?)}");
            stmt.setString(1, recordID);
            stmt.setString(2, recordType);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                connection.commit();
                return new CensusOperationResult(true, "Census record deleted successfully", null);
            } else {
                connection.rollback();
                return new CensusOperationResult(false, "Delete operation failed, no rows affected.", null);
            }
        } catch (SQLException e) {
            try { connection.rollback(); } catch (SQLException ignored) {}
            return new CensusOperationResult(false, "Database error during delete: " + e.getMessage(), null);
        } finally {
            closeStatement(stmt);
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }

    private void closeStatement(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing statement: " + e.getMessage());
        }
    }
}
