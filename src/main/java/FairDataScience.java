import com.google.common.collect.Lists;
import com.sun.prism.impl.Disposer.Record;
import java.io.FileReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class FairDataScience extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");


        String[] HEADERS = { "ID","Case Number","Date","Block","IUCR","Primary Type","Description"
            ,"Location Description","Arrest","Domestic","Beat","District","Ward","Community Area"
            ,"FBI Code","X Coordinate","Y Coordinate","Year","Updated On","Latitude","Longitude"
            ,"Location"
        };

        Reader in = new FileReader(getClass().getResource("Crimes_-_2001_to_present.csv").getPath());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
            .withHeader(HEADERS)
            .withSkipHeaderRecord()
            .parse(in);

        Map<Integer, Long> crimesPerYear = new TreeMap<>();

        for (CSVRecord record : records) {

            String date = record.get("Date");
            String year = date.substring(date.lastIndexOf("/")+1, date.lastIndexOf("/")+5);
            //System.out.println(year);
            Integer yearVal = Integer.valueOf(year);

            if(!yearVal.equals(2018)){

                if(!crimesPerYear.containsKey(yearVal)){
                    crimesPerYear.put(yearVal, 0L);
                }else{
                    crimesPerYear.put(yearVal, crimesPerYear.get(yearVal)+1);
                }

            }

        }

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of crimes");
        xAxis.setLabel("Month");
        final LineChart<String,Number> lineChart = new LineChart<>(xAxis,yAxis);

        lineChart.setTitle("Incidents of crime in the City of Chicago correlates with <Example>");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Crimes in Chicago");

        for(Map.Entry<Integer, Long> entry : crimesPerYear.entrySet()){
            series1.getData().add(new XYChart.Data(entry.getKey() + "", entry.getValue()));
        }

        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().addAll(series1);



        primaryStage.setScene(scene);
        //primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
