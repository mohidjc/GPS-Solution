import java.io.IOException;

/**
 * Program to provide information on a GPS track stored in a file.
 *
 * @author Mohid Chaudhry
 */
public class TrackInfo {
  public static void main(String[] args) throws IOException {
     
      if (args.length==0){
          System.exit(0);
      }
      Track trackObject = new Track(args[0]);
      String distanceInKm = String.format("%.3f",trackObject.totalDistance()/1000);
      System.out.print(trackObject.size()+" points in track"+"\n");
      System.out.print("Lowest point is "+trackObject.lowestPoint().toString()+"\n");
      System.out.print("Highest point is "+trackObject.highestPoint().toString()+"\n");
      System.out.print("Total distance = "+distanceInKm+" km"+"\n");
      System.out.print("Average speed = "+String.format("%.3f",trackObject.averageSpeed())+" m/s");
  }
 }
