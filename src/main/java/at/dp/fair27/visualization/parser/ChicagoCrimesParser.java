package at.dp.fair27.visualization.parser;

import org.apache.commons.csv.CSVRecord;

import java.util.Map;
import java.util.TreeMap;

public class ChicagoCrimesParser implements ICrimesParser {

  private final Map<Integer, Long> crimesPerYearChicago = new TreeMap<>();
  private final Iterable<CSVRecord> crimesRecords;

  public ChicagoCrimesParser(Iterable<CSVRecord> crimesRecords) {
    this.crimesRecords = crimesRecords;
  }

  public Map<Integer, Long> getParsedCrimesRecords() {
    for (final CSVRecord record : crimesRecords) {
      handleCrimesRecord(record);
    }
    return crimesPerYearChicago;
  }

  private void handleCrimesRecord(final CSVRecord record) {
    String date = record.get("YEAR").split(";")[0];
    Integer yearVal = Integer.valueOf(date);
    Long crimes = Long.valueOf(record.get("YEAR").split(";")[1]);
    if (!yearVal.equals(2018) && !yearVal.equals(2017)) {
      crimesPerYearChicago.putIfAbsent(yearVal, crimes);
    }
  }
}
