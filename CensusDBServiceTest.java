import java.sql.SQLException;

public class CensusDBServiceTest {
    public static void main(String[] args) {
        try {
            CensusDBService service = new CensusDBService();

            // Example: test retrieve (adjust as needed)
            CensusOperationResult result = service.retrieveCensusRecords("region", "Greater Accra");
            System.out.println(result.getMessage());

            if (result.getData() != null) {
                System.out.println("Records: " + result.getData().size());
            } else {
                System.out.println("No records found.");
            }

            service.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
