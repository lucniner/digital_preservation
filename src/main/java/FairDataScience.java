import com.google.common.collect.Lists;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;
import java.util.*;

public class FairDataScience extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader.load(getClass().getResource("sample.fxml"));
    primaryStage.setTitle("FAIR DATA SCIENCE - Crimes in Chicago vs Germany 2001 - 2016");

    String[] CHICAGO_CRIME_HEADERS = {"YEAR", "SUM_OF_CRIMES"};

    String[] GERMANY_CRIME_HEADERS = {"Art", "1976", "1977", "1978", "1979", "1980", "1981", "1982",
            "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994"
        , "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005"
        , "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016"
    };

    Reader chicagoFileReader = new FileReader(
            getClass().getResource("crimes_in_chicago_summized_per_year.csv").getPath());
    Iterable<CSVRecord> chicagoRecords = CSVFormat.DEFAULT
        .withHeader(CHICAGO_CRIME_HEADERS)
        .withSkipHeaderRecord()
        .parse(chicagoFileReader);

    Reader germanyFileReader = new FileReader(
            getClass().getResource("crimes_in_germany_1976_to_2016.csv").getPath());
    Iterable<CSVRecord> germanyRecords = CSVFormat.DEFAULT
        .withHeader(GERMANY_CRIME_HEADERS)
        .withSkipHeaderRecord()
        .parse(germanyFileReader);

    Map<Integer, Long> crimesPerYearChicago = new TreeMap<>();
    Map<Integer, Long> crimesPerYearGermany = new TreeMap<>();

    for (CSVRecord record : chicagoRecords) {
      String date = record.get("YEAR").split(";")[0];
      Integer yearVal = Integer.valueOf(date);
      Long crimes = Long.valueOf(record.get("YEAR").split(";")[1]);
      if (!yearVal.equals(2018) && !yearVal.equals(2017)) {
        crimesPerYearChicago.putIfAbsent(yearVal, crimes);
      }

    }

    Iterator<CSVRecord> csvRecordIterator = germanyRecords.iterator();
    CSVRecord record = csvRecordIterator.next();
    Map<String, String> map = record.toMap();
    Collection<String> values = map.values();
    List<String> someList = Lists.newArrayList(values);
    String[] valueArray = someList.get(0).split(";");

    for (int i = 26; i < GERMANY_CRIME_HEADERS.length; i++) {
      String year = GERMANY_CRIME_HEADERS[i];
      crimesPerYearGermany.put(Integer.valueOf(year), Long.valueOf(valueArray[i]));
    }

    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel("Year");
    yAxis.setLabel("Number of crimes");

    final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
    lineChart.setTitle(
        "Incidents of crime in the City of Chicago correlates with incidents of crime in Germany");

    XYChart.Series chicagoSeries = new XYChart.Series();
    chicagoSeries.setName("Crimes in Chicago");

    for (Map.Entry<Integer, Long> entry : crimesPerYearChicago.entrySet()) {
      chicagoSeries.getData().add(new XYChart.Data(entry.getKey() + "", entry.getValue()));
    }

    XYChart.Series germanySeries = new XYChart.Series();
    germanySeries.setName("Crimes in Germany");

    for (Map.Entry<Integer, Long> entry : crimesPerYearGermany.entrySet()) {
      germanySeries.getData().add(new XYChart.Data(entry.getKey() + "", entry.getValue()));
    }

    Scene scene = new Scene(lineChart, 800, 600);
    lineChart.getData().addAll(chicagoSeries, germanySeries);

    primaryStage.setScene(scene);
    primaryStage.show();

  }
}
