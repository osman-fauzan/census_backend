import java.sql.Date;

/**
 * Census Record Data Model
 * Represents a complete census record with all related information
 */
public class CensusRecord {

    // ... existing fields, getters, and setters ...


    
    // Census Officer fields
    private String officerID;
    private String officerName;
    private String officerPosition;
    private String officerContact;
    
    // Person fields
    private String personID;
    private String firstName;
    private String lastName;
    private String gender;
    private Date dateOfBirth;
    private String addressID;
    private String region;
    private String district;
    private String community;
    private String houseNumber;
    private String streetName;
    private String gpsLocation;
    
    // Education fields
    private String educationID;
    private String educationLevel;
    private String schoolName;
    private String yearCompleted;
    
    // Employment fields
    private String employmentID;
    private String occupation;
    private String employerName;
    private String employmentStatus;
    
    // Household fields
    private String householdID;
    private String relationshipToHead;
    
    // Default constructor
    public CensusRecord() {}
    
    // Getters and Setters for Census Officer
    public String getOfficerID() { return officerID; }
    public void setOfficerID(String officerID) { this.officerID = officerID; }
    
    public String getOfficerName() { return officerName; }
    public void setOfficerName(String officerName) { this.officerName = officerName; }
    
    public String getOfficerPosition() { return officerPosition; }
    public void setOfficerPosition(String officerPosition) { this.officerPosition = officerPosition; }
    
    public String getOfficerContact() { return officerContact; }
    public void setOfficerContact(String officerContact) { this.officerContact = officerContact; }
    
    // Getters and Setters for Person
    public String getPersonID() { return personID; }
    public void setPersonID(String personID) { this.personID = personID; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    
    public String getAddressID() { return addressID; }
    public void setAddressID(String addressID) { this.addressID = addressID; }
    
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    
    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
    
    public String getCommunity() { return community; }
    public void setCommunity(String community) { this.community = community; }
    
    public String getHouseNumber() { return houseNumber; }
    public void setHouseNumber(String houseNumber) { this.houseNumber = houseNumber; }
    
    public String getStreetName() { return streetName; }
    public void setStreetName(String streetName) { this.streetName = streetName; }
    
    public String getGpsLocation() { return gpsLocation; }
    public void setGpsLocation(String gpsLocation) { this.gpsLocation = gpsLocation; }
    
    // Getters and Setters for Education
    public String getEducationID() { return educationID; }
    public void setEducationID(String educationID) { this.educationID = educationID; }
    
    public String getEducationLevel() { return educationLevel; }
    public void setEducationLevel(String educationLevel) { this.educationLevel = educationLevel; }
    
    public String getSchoolName() { return schoolName; }
    public void setSchoolName(String schoolName) { this.schoolName = schoolName; }
    
    public String getYearCompleted() { return yearCompleted; }
    public void setYearCompleted(String yearCompleted) { this.yearCompleted = yearCompleted; }
    
    // Getters and Setters for Employment
    public String getEmploymentID() { return employmentID; }
    public void setEmploymentID(String employmentID) { this.employmentID = employmentID; }
    
    public String getOccupation() { return occupation; }
    public void setOccupation(String occupation) { this.occupation = occupation; }
    
    public String getEmployerName() { return employerName; }
    public void setEmployerName(String employerName) { this.employerName = employerName; }
    
    public String getEmploymentStatus() { return employmentStatus; }
    public void setEmploymentStatus(String employmentStatus) { this.employmentStatus = employmentStatus; }
    
    // Getters and Setters for Household
    public String getHouseholdID() { return householdID; }
    public void setHouseholdID(String householdID) { this.householdID = householdID; }
    
    public String getRelationshipToHead() { return relationshipToHead; }
    public void setRelationshipToHead(String relationshipToHead) { this.relationshipToHead = relationshipToHead; }
    
    @Override
    public String toString() {
        return "CensusRecord{" +
                "officerID='" + officerID + '\'' +
                ", officerName='" + officerName + '\'' +
                ", officerPosition='" + officerPosition + '\'' +
                ", officerContact='" + officerContact + '\'' +
                ", personID='" + personID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", addressID='" + addressID + '\'' +
                ", region='" + region + '\'' +
                ", district='" + district + '\'' +
                ", community='" + community + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", streetName='" + streetName + '\'' +
                ", gpsLocation='" + gpsLocation + '\'' +
                ", educationID='" + educationID + '\'' +
                ", educationLevel='" + educationLevel + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", yearCompleted='" + yearCompleted + '\'' +
                ", employmentID='" + employmentID + '\'' +
                ", occupation='" + occupation + '\'' +
                ", employerName='" + employerName + '\'' +
                ", employmentStatus='" + employmentStatus + '\'' +
                ", householdID='" + householdID + '\'' +
                ", relationshipToHead='" + relationshipToHead + '\'' +
                '}';
    }
}
