package at.dp.fair27.visualization;

import java.util.Map;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class CrimesVisualizer {

  final XYChart.Series chicagoSeries = new XYChart.Series();
  final XYChart.Series germanySeries = new XYChart.Series();
  private final CategoryAxis xAxis = new CategoryAxis();
  private final NumberAxis yAxis = new NumberAxis();
  private final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
  private final Map<Integer, Long> crimesInChicago;
  private final Map<Integer, Long> crimesInGermany;

  public CrimesVisualizer(Map<Integer, Long> crimesInChicago, Map<Integer, Long> crimesInGermany, DiagramLabeling labeling) {
    this.crimesInChicago = crimesInChicago;
    this.crimesInGermany = crimesInGermany;

    xAxis.setLabel(labeling.getxLabel());
    yAxis.setLabel(labeling.getyLabel());
    lineChart.setTitle(labeling.getTitle());
    chicagoSeries.setName(labeling.getChicagoSeriesLabel());
    germanySeries.setName(labeling.getGermanySeriesLabel());

  }

  public LineChart<String, Number> visualize() {
    visualizeChicago();
    visualizeGermany();
    lineChart.getData().addAll(chicagoSeries, germanySeries);
    return lineChart;
  }

  private void visualizeChicago() {
    addDataToSeries(chicagoSeries, crimesInChicago);
  }

  private void visualizeGermany() {
    addDataToSeries(germanySeries, crimesInGermany);
  }

  private void addDataToSeries(final XYChart.Series series, Map<Integer, Long> valueMap) {
    for (Map.Entry<Integer, Long> entry : valueMap.entrySet()) {
      series.getData().add(new XYChart.Data<String, Number>(String.valueOf(entry.getKey()), entry.getValue()));
    }
  }
}
