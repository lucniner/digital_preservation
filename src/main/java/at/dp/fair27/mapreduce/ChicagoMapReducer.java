package at.dp.fair27.mapreduce;

import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Lukas Kathrein
 * @version 1.0.0
 * @since 1.0.0
 */
public class ChicagoMapReducer {

  private static final Logger LOGGER = Logger.getLogger(ChicagoMapReducer.class.getSimpleName());

  public static void main(String[] args) {
    if (args.length != 1) {
      LOGGER.log(Level.SEVERE, "program argument mismatch - please start program with exactly the path to chicago crimes data set");
    } else {
      LOGGER.log(Level.INFO, "starting to process chicago crimes");
      final String argument = args[0];
      executeMapReduceJob(argument);
    }

  }

  private static void executeMapReduceJob(final String argument) {
    final Optional<Iterable<CSVRecord>> optionalRecords = getRecords(argument);
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
    final ChicagoFileWriter writer = new ChicagoFileWriter(reducedCrimes);
    try {
      writer.writeResult();
    } catch (IOException e) {
      LOGGER.log(Level.WARNING, "exception writing reduced records to file", e);
    }
  }


}
