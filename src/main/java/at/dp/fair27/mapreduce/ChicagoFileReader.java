package at.dp.fair27.mapreduce;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class ChicagoFileReader {

  private static String[] CHICAGO_CRIME_HEADERS = {"ID", "Case Number", "Date", "Block", "IUCR", "Primary Type",
          "Description", "Location Description", "Arrest", "Domestic", "Beat", "District", "Ward", "Community Area"
          , "FBI Code", "X Coordinate", "Y Coordinate", "Year", "Updated On", "Latitude", "Longitude", "Location"
  };

  private final String resourceLocation;

  ChicagoFileReader(String resourceLocation) {
    this.resourceLocation = resourceLocation;
  }

  public Iterable<CSVRecord> getChicagoCrimesRecords() throws IOException {
    final Reader chicagoFileReader = new FileReader(resourceLocation);
    return CSVFormat.DEFAULT
            .withHeader(CHICAGO_CRIME_HEADERS)
            .withSkipHeaderRecord()
            .parse(chicagoFileReader);

  }
}
