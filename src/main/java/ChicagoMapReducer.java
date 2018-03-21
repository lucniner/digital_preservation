import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 * @author Lukas Kathrein
 * @version 1.0.0
 * @since 1.0.0
 */
public class ChicagoMapReducer {

  private static final ChicagoMapReducer mapReducer = new ChicagoMapReducer();

  public static void main(String[] args) {

    try {
      mapReducer.reduce();
    } catch (IOException e) {
      e.printStackTrace();
    }


  }

  public void reduce() throws IOException {
    String[] CHICAGO_CRIME_HEADERS = {"ID", "Case Number", "Date", "Block", "IUCR", "Primary Type",
        "Description"
        , "Location Description", "Arrest", "Domestic", "Beat", "District", "Ward", "Community Area"
        , "FBI Code", "X Coordinate", "Y Coordinate", "Year", "Updated On", "Latitude", "Longitude"
        , "Location"
    };

    Reader chicagoFileReader = new FileReader(
        getClass().getResource("Crimes_-_2001_to_present.csv").getPath());
    Iterable<CSVRecord> chicagoRecords = CSVFormat.DEFAULT
        .withHeader(CHICAGO_CRIME_HEADERS)
        .withSkipHeaderRecord()
        .parse(chicagoFileReader);

    Map<Integer, Long> crimesPerYearChicago = new TreeMap<>();

    for (CSVRecord record : chicagoRecords) {
      String date = record.get("Date");
      String year = date.substring(date.lastIndexOf("/") + 1, date.lastIndexOf("/") + 5);
      Integer yearVal = Integer.valueOf(year);

      if (!yearVal.equals(2018) && !yearVal.equals(2017)) {
        if (!crimesPerYearChicago.containsKey(yearVal)) {
          crimesPerYearChicago.put(yearVal, 0L);
        } else {
          crimesPerYearChicago.put(yearVal, crimesPerYearChicago.get(yearVal) + 1);
        }
      }
    }

    File file = new File("chicago_minimized.csv");
    FileWriter fileWriter = new FileWriter(file);
    fileWriter.write("YEAR;SUM_OF_CRIMES\n");
    for (Map.Entry<Integer, Long> entry : crimesPerYearChicago.entrySet()) {
      fileWriter.write(entry.getKey() + ";" + entry.getValue() + "\n");
    }
    fileWriter.close();
  }
}
