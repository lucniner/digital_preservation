package at.dp.fair27.visualization;

import at.dp.fair27.visualization.parser.ChicagoCrimesParser;
import at.dp.fair27.visualization.parser.GermanyCrimesParser;
import at.dp.fair27.visualization.parser.ICrimesParser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.stage.Stage;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FairDataScience extends Application {

  private static final Logger LOGGER = Logger.getLogger(FairDataScience.class.getSimpleName());
  private static final CrimesReader crimesReader = new CrimesReader();

  private static String chicagoCrimesFileLocation;
  private static String germanyCrimesFileLocation;

  public static void main(String[] args) {
    if (args.length != 2) {
      LOGGER.log(Level.SEVERE, "argument mismatch - please provide 1) file location of the chicago 2) file location of the  german crimes data set");
      System.exit(0);
    } else {
      LOGGER.log(Level.INFO, "launching applicaiton");
      chicagoCrimesFileLocation = args[0];
      germanyCrimesFileLocation = args[1];
      launch(args);
    }
  }

  private static DiagramLabeling getLabeling() {
    final String title = "Incidents of crime in the City of Chicago correlates with incidents of crime in Germany";
    final String xLabel = "Year";
    final String yLabel = "Number of crimes";
    final String germanSeriesLabel = "Crimes in Germany";
    final String chicagoSeriesLabel = "Crimes in Chicago";
    return new DiagramLabeling(title, xLabel, yLabel, chicagoSeriesLabel, germanSeriesLabel);
  }

  private static Map<Integer, Long> getCrimesForChicago() throws IOException {
    final Iterable<CSVRecord> chicagoCrimesRecords = crimesReader.getCrimesRecords(chicagoCrimesFileLocation, SharedConstants.CHICAGO_CRIME_HEADERS);
    final ICrimesParser chicagoCrimesParser = new ChicagoCrimesParser(chicagoCrimesRecords);
    return getCrimes(chicagoCrimesParser);
  }

  private static Map<Integer, Long> getCrimesForGermany() throws IOException {
    final Iterable<CSVRecord> germanCrimesRecords = crimesReader.getCrimesRecords(germanyCrimesFileLocation, SharedConstants.GERMANY_CRIME_HEADERS);
    final ICrimesParser germanyCrimesParser = new GermanyCrimesParser(germanCrimesRecords, SharedConstants.GERMANY_CRIME_HEADERS);
    return getCrimes(germanyCrimesParser);
  }

  private static Map<Integer, Long> getCrimes(final ICrimesParser parser) {
    return parser.getParsedCrimesRecords();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
    primaryStage.setTitle("FAIR DATA SCIENCE - Crimes in Chicago vs Germany 2001 - 2016");

    final DiagramLabeling labeling = getLabeling();
    final Map<Integer, Long> chicagoCrimes = getCrimesForChicago();
    final Map<Integer, Long> germanCrimes = getCrimesForGermany();

    final CrimesVisualizer visualizer = new CrimesVisualizer(chicagoCrimes, germanCrimes, labeling);
    final LineChart<String, Number> lineChart = visualizer.visualize();
    final Scene scene = new Scene(lineChart, 800, 600);

    primaryStage.setScene(scene);
    primaryStage.show();

  }
}
