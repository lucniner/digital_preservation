package at.dp.fair27.visualization;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class CrimesReader {


  public Iterable<CSVRecord> getCrimesRecords(final String fileLocation, final String[] headers) throws IOException {
    final Reader fileReader = new FileReader(fileLocation);
    return CSVFormat.DEFAULT
            .withHeader(headers)
            .withSkipHeaderRecord()
            .parse(fileReader);
  }
}
