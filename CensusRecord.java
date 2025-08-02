public class CensusRecord {
    private String officerID;
    private String officerName;
    private String officerPosition;
    private String officerContact;

    private String personID;
    private String firstName;
    private String lastName;
    private String gender;
    private java.sql.Date dateOfBirth;
    private String age;
    private String maritalStatus;
    private String occupation;
    private String religion;
    private String nationality;
    private String ethnicity;

    private String region;
    private String district;
    private String community;
    private String streetName;
    private String houseNumber;
    private String gpsLocation;

    private String educationID;
    private String educationLevel;
    private String schoolName;
    private String yearCompleted;

    private String employmentID;
    private String employmentStatus;
    private String employerName;

    private String householdID;
    private String headID;
    private String householdSize;
    private String relationToHead;

    // Additional fields to resolve missing methods in CensusDBService
    private String addressID;
    private String relationshipToHead;

    // --- Getters and Setters ---

    public String getOfficerID() { return officerID; }
    public void setOfficerID(String officerID) { this.officerID = officerID; }
    public String getOfficerName() { return officerName; }
    public void setOfficerName(String officerName) { this.officerName = officerName; }
    public String getOfficerPosition() { return officerPosition; }
    public void setOfficerPosition(String officerPosition) { this.officerPosition = officerPosition; }
    public String getOfficerContact() { return officerContact; }
    public void setOfficerContact(String officerContact) { this.officerContact = officerContact; }

    public String getPersonID() { return personID; }
    public void setPersonID(String personID) { this.personID = personID; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public java.sql.Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(java.sql.Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }
    public String getMaritalStatus() { return maritalStatus; }
    public void setMaritalStatus(String maritalStatus) { this.maritalStatus = maritalStatus; }
    public String getOccupation() { return occupation; }
    public void setOccupation(String occupation) { this.occupation = occupation; }
    public String getReligion() { return religion; }
    public void setReligion(String religion) { this.religion = religion; }
    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public String getEthnicity() { return ethnicity; }
    public void setEthnicity(String ethnicity) { this.ethnicity = ethnicity; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
    public String getCommunity() { return community; }
    public void setCommunity(String community) { this.community = community; }
    public String getStreetName() { return streetName; }
    public void setStreetName(String streetName) { this.streetName = streetName; }
    public String getHouseNumber() { return houseNumber; }
    public void setHouseNumber(String houseNumber) { this.houseNumber = houseNumber; }
    public String getGpsLocation() { return gpsLocation; }
    public void setGpsLocation(String gpsLocation) { this.gpsLocation = gpsLocation; }

    public String getEducationID() { return educationID; }
    public void setEducationID(String educationID) { this.educationID = educationID; }
    public String getEducationLevel() { return educationLevel; }
    public void setEducationLevel(String educationLevel) { this.educationLevel = educationLevel; }
    public String getSchoolName() { return schoolName; }
    public void setSchoolName(String schoolName) { this.schoolName = schoolName; }
    public String getYearCompleted() { return yearCompleted; }
    public void setYearCompleted(String yearCompleted) { this.yearCompleted = yearCompleted; }

    public String getEmploymentID() { return employmentID; }
    public void setEmploymentID(String employmentID) { this.employmentID = employmentID; }
    public String getEmploymentStatus() { return employmentStatus; }
    public void setEmploymentStatus(String employmentStatus) { this.employmentStatus = employmentStatus; }
    public String getEmployerName() { return employerName; }
    public void setEmployerName(String employerName) { this.employerName = employerName; }

    public String getHouseholdID() { return householdID; }
    public void setHouseholdID(String householdID) { this.householdID = householdID; }
    public String getHeadID() { return headID; }
    public void setHeadID(String headID) { this.headID = headID; }
    public String getHouseholdSize() { return householdSize; }
    public void setHouseholdSize(String householdSize) { this.householdSize = householdSize; }
    public String getRelationToHead() { return relationToHead; }
    public void setRelationToHead(String relationToHead) { this.relationToHead = relationToHead; }

    // For compatibility with CensusDBService
    public String getAddressID() { return addressID; }
    public void setAddressID(String addressID) { this.addressID = addressID; }
    public String getRelationshipToHead() { return relationshipToHead; }
    public void setRelationshipToHead(String relationshipToHead) { this.relationshipToHead = relationshipToHead; }
}
