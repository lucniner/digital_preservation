package at.dp.fair27.mapreduce;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class ChicagoFileWriter {

  private static String OUTPUT_FILE_NAME = "crimes_in_chicago_summized_per_year.csv";

  private final Map<Integer, Long> crimesPerYearChicago;

  ChicagoFileWriter(Map<Integer, Long> crimesPerYearChicago) {
    this.crimesPerYearChicago = crimesPerYearChicago;
  }


  public void writeResult() throws IOException {
    final File file = new File(OUTPUT_FILE_NAME);
    try (final FileWriter fileWriter = new FileWriter(file)) {
      writeHeader(fileWriter);
      writeBody(fileWriter);
      fileWriter.close();
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
