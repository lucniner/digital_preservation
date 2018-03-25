package at.dp.fair27.visualization;

public class DiagramLabeling {

  private final String title;
  private final String xLabel;
  private final String yLabel;
  private final String chicagoSeriesLabel;
  private final String germanySeriesLabel;

  public DiagramLabeling(String title, String xLabel, String yLabel, String chicagoSeriesLabel, String germanySeriesLabel) {
    this.title = title;
    this.xLabel = xLabel;
    this.yLabel = yLabel;
    this.chicagoSeriesLabel = chicagoSeriesLabel;
    this.germanySeriesLabel = germanySeriesLabel;
  }

  public String getTitle() {
    return title;
  }

  public String getxLabel() {
    return xLabel;
  }

  public String getyLabel() {
    return yLabel;
  }

  public String getChicagoSeriesLabel() {
    return chicagoSeriesLabel;
  }

  public String getGermanySeriesLabel() {
    return germanySeriesLabel;
  }
}
