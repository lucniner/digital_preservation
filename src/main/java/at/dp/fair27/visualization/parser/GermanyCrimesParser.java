package at.dp.fair27.visualization.parser;

import com.google.common.collect.Lists;
import org.apache.commons.csv.CSVRecord;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GermanyCrimesParser implements ICrimesParser {
  private final Map<Integer, Long> crimesPerYearGermany = new TreeMap<>();
  private final Iterable<CSVRecord> crimesRecords;
  private final String[] germanHeaders;

  public GermanyCrimesParser(Iterable<CSVRecord> crimesRecords, String[] germanHeaders) {
    this.crimesRecords = crimesRecords;
    this.germanHeaders = germanHeaders;
  }

  public Map<Integer, Long> getParsedCrimesRecords() {
    final String[] crimesSplitPerYear = getCrimesPerYear();
    for (int i = 26; i < germanHeaders.length; i++) {
      String year = germanHeaders[i];
      crimesPerYearGermany.put(Integer.valueOf(year), Long.valueOf(crimesSplitPerYear[i]));
    }
    return crimesPerYearGermany;
  }

  private String[] getCrimesPerYear() {
    final Iterator<CSVRecord> csvRecordIterator = crimesRecords.iterator();
    final CSVRecord record = csvRecordIterator.next();
    final Map<String, String> crimes = record.toMap();
    final List<String> crimesValues = Lists.newArrayList(crimes.values());
    return crimesValues.get(0).split(";");
  }

}
