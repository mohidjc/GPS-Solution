import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * JavaFX application to plot elevations of a GPS track
 * 
 *
 * @author Mohid Chaudhry
 */
public class PlotApplication extends Application {
   
      @Override
      public void start(Stage primaryStage) throws Exception {
        // gets the command line argument
        String filename = getParameters().getRaw().get(0); 
        Track trackObject = new Track(filename);
        int theDistance=(int) trackObject.totalDistance();
        // max distance for the max x axis value
        theDistance =theDistance + (100-(theDistance%100));
        int theElevation=(int) trackObject.highestPoint().getElevation();
        // highest elevation for the max y value on graph
        theElevation = theElevation+ (5-(theElevation%5));
        //max x and y values for maximum graph size
        NumberAxis xAxis = new NumberAxis(0,theDistance,100); 
        // labelling the axis
        xAxis.setLabel("Distance (m)");
        NumberAxis yAxis = new NumberAxis(0,theElevation,5);
        yAxis.setLabel("Elevation (m)");

        // line chart to draw the chart
        LineChart lineChart = new LineChart(xAxis,yAxis);

        XYChart.Series series = new XYChart.Series();

        series.setName(filename);

        double distance = 0;
        // adds the first value on the graph
        series.getData().add(new XYChart.Data(0, trackObject.get(0).getElevation()));

        // goes through all points as the distance increases elevation changes and is plotted
        for (int i=1;i<trackObject.size();i++){
          Point firstPoint1 = trackObject.get(i-1);
          Point secondPoint1 = trackObject.get(i);
          //distnace between each point added to current distance is the distance of latest point
          distance+=Point.greatCircleDistance(firstPoint1,secondPoint1);
          series.getData().add(new XYChart.Data(distance, trackObject.get(i).getElevation()));
        }

        // displaying the linegraph
        lineChart.getData().add(series);
        Group group = new Group(lineChart);
        Scene scene = new Scene (group, 600, 400);
        primaryStage.setTitle("Elevation Plot");
        primaryStage.setScene(scene);
        primaryStage.show();


      }



  public static void main(String[] args) {
    launch(args);
  }
}
