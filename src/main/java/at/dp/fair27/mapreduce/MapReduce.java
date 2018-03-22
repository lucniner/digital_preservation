package at.dp.fair27.mapreduce;

import org.apache.commons.csv.CSVRecord;

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MapReduce {

  private static final Logger LOGGER = Logger.getLogger(MapReduce.class.getSimpleName());

  private final Map<Integer, Long> crimesPerYearChicago = new TreeMap<>();

  private final Iterable<CSVRecord> records;


  MapReduce(Iterable<CSVRecord> records) {
    this.records = records;
  }

  public Map<Integer, Long> getRecordsGroupedByYear() {
    LOGGER.log(Level.INFO, "starting map reduce job");
    for (CSVRecord record : records) {
      final Integer year = extractYearFromRecord(record);
      handleYear(year);
    }
    LOGGER.log(Level.INFO, "map reduce job finished");

    return crimesPerYearChicago;
  }

  private Integer extractYearFromRecord(final CSVRecord record) {
    final String dateFormatted = record.get("Date");
    final String year = dateFormatted.substring(dateFormatted.lastIndexOf('/') + 1, dateFormatted.lastIndexOf('/') + 5);
    return Integer.valueOf(year);
  }

  private void handleYear(final Integer year) {
    if (!year.equals(2018) && !year.equals(2017)) {
      if (!crimesPerYearChicago.containsKey(year)) {
        crimesPerYearChicago.put(year, 0L);
      } else {
        crimesPerYearChicago.put(year, crimesPerYearChicago.get(year) + 1);
      }
    }
  }
}
