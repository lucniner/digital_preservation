package at.dp.fair27.mapreduce;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVRecord;

/**
 * @author Lukas Kathrein
 * @version 1.0.0
 * @since 1.0.0
 */
public class ChicagoMapReducer {

  private static final Logger LOGGER = Logger.getLogger(ChicagoMapReducer.class.getSimpleName());
  private static String chicagoCrimesInputFileLocation;
  private static String chicagoCrimesMapedOutputFileLocation;

  public static void main(String[] args) {
    if (args.length != 2) {
      LOGGER.log(Level.SEVERE,
          "program argument mismatch - please start program with the 1) path to chicago crimes data set and 2) an output path");
    } else {
      LOGGER.log(Level.INFO, "starting to process chicago crimes");
      chicagoCrimesInputFileLocation = args[0];
      chicagoCrimesMapedOutputFileLocation = args[1];
      executeMapReduceJob();
    }

  }

  private static void executeMapReduceJob() {
    final Optional<Iterable<CSVRecord>> optionalRecords = getRecords(chicagoCrimesInputFileLocation);
    optionalRecords.ifPresent(ChicagoMapReducer::reduceRecords);
    LOGGER.log(Level.INFO, "map reduce job for chicago crimes data set finished");
  }


  private static Optional<Iterable<CSVRecord>> getRecords(final String argument) {
    final ChicagoFileReader reader = new ChicagoFileReader(argument);
    try {
      final Iterable<CSVRecord> records = reader.getChicagoCrimesRecords();
      return Optional.of(records);
    } catch (IOException e) {
      LOGGER.log(Level.WARNING, "exception reading chicago records", e);
      return Optional.empty();
    }
  }

  private static void reduceRecords(final Iterable<CSVRecord> records) {
    final MapReduce mapReduce = new MapReduce(records);
    final Map<Integer, Long> reducedCrimes = mapReduce.getRecordsGroupedByYear();
    writeResults(reducedCrimes);
  }

  private static void writeResults(final Map<Integer, Long> reducedCrimes) {
    LOGGER.log(Level.INFO, "writing map reduce results to file");
    final ChicagoFileWriter writer = new ChicagoFileWriter(reducedCrimes, chicagoCrimesMapedOutputFileLocation);
    try {
      writer.writeResult();
    } catch (IOException e) {
      LOGGER.log(Level.WARNING, "exception writing reduced records to file", e);
    }
  }


}
