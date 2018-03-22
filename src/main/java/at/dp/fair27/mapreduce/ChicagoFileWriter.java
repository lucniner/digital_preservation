package at.dp.fair27.mapreduce;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class ChicagoFileWriter {

  private static String OUTPUT_FILE_NAME = "crimes_in_chicago_summarized_per_year.csv";

  private final Map<Integer, Long> crimesPerYearChicago;
  private final String outputPath;

  ChicagoFileWriter(Map<Integer, Long> crimesPerYearChicago, String outputPath) {
    this.crimesPerYearChicago = crimesPerYearChicago;
    this.outputPath = outputPath;
  }


  public void writeResult() throws IOException {
    final File file = new File(outputPath.concat(OUTPUT_FILE_NAME));
    try (final FileWriter fileWriter = new FileWriter(file)) {
      writeHeader(fileWriter);
      writeBody(fileWriter);
    }
  }

  private void writeHeader(final Writer writer) throws IOException {
    writer.write("YEAR;SUM_OF_CRIMES\n");
  }

  private void writeBody(final Writer writer) throws IOException {
    for (final Map.Entry<Integer, Long> entry : crimesPerYearChicago.entrySet()) {
      writer.write(entry.getKey() + ";" + entry.getValue() + "\n");
    }
  }


}
