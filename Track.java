import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.temporal.ChronoUnit;

/**
 * Represents a point in space and time, recorded by a GPS sensor.
 *
 * @author Mohid Chaudhry
 */
public class Track {
    private ArrayList<Point> pointObjects;
    private String fileName;


    public Track(String filename) throws IOException {
        this.pointObjects=new ArrayList<Point>();
        this.fileName=filename;
        // reading file file using the the filename
        this.readFile(this.fileName); 
    }

    public Track()  {
        this.pointObjects=new ArrayList<Point>();

    }
 
    public void readFile(String filename) throws IOException {
        // clearing the ArrayList before reading the file again
        pointObjects.clear();
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        // going to the second line as the first line doesnt count
        scanner.nextLine(); 
        // checks if there are more lines in file
        while (scanner.hasNextLine()){  
            String line = scanner.nextLine();
            // string array stores the split up line
            String[] data = line.split(","); 
                if (data.length==4){
                    // parsing from one data type to another
                    ZonedDateTime t = ZonedDateTime.parse(data[0]); 
                    double lon = Double.parseDouble(data[1]);
                    double lat = Double.parseDouble(data[2]);
                    double elev = Double.parseDouble(data[3]);
                    this.pointObjects.add(new Point(t,lon,lat,elev));
                }else{
                    throw new GPSException(null);
                }
        }

    }
   
    public void add(Point point){
        // adds the Point from the parameters into the arrayList
        this.pointObjects.add(point); 
    }

   

    public Point get(int index) { 
        // checks if the index is out of bounds
        if (index<0 || index>this.pointObjects.size()-1) { 
            throw new GPSException(null);
            // checks if the Arraylist if empty/no Points
        } else if (this.pointObjects.size()==0) { 
            throw new GPSException(null);
        } else{
            return this.pointObjects.get(index);
        }
    }
 
    public int size(){ 
    // checks number of elements in the ArrayList
    int size = this.pointObjects.size(); 
    return size;
    }


    public Point lowestPoint() throws GPSException{ 
        // if Arraylist is empty there are no points so throws exception
        if (pointObjects.size()==0){ 
            throw new GPSException(null);
        }
        // initialising the smallest elevation variable
        double smallestElev=this.pointObjects.get(0).getElevation(); 
        int indexForSmallest=0;
        // goes through all elements/points in the ArrayList
        for (int i=0; i<this.pointObjects.size();i++){ 
                // checks if the elevation of next point is the smallest and sets it if smaller
                if (this.pointObjects.get(i).getElevation()<smallestElev){ 
                    smallestElev=this.pointObjects.get(i).getElevation(); 
                    indexForSmallest=i;
                }
        }
        return this.pointObjects.get(indexForSmallest); 
    }
    
    // same as the above but the other way around
    public Point highestPoint() throws GPSException{ 
        if (pointObjects.size()==0){
            throw new GPSException(null);
        }
        double largestElev=this.pointObjects.get(0).getElevation();
        int indexForlargest=0;
        for (int i=0; i<this.pointObjects.size();i++){
            if (this.pointObjects.get(i).getElevation()>largestElev){
                largestElev=this.pointObjects.get(i).getElevation();
                indexForlargest=i;
            }
        }
        return this.pointObjects.get(indexForlargest);
    }
 
    public double totalDistance() throws GPSException{
        // should find total distance if there are more than two points in the ArrayList
        if (this.pointObjects.size()<2){ 
            throw new GPSException(null);
        }
        double totalDistance=0;
        //goes through all the points in the arrayList 
        for(int i=0;i<this.pointObjects.size()-1;i++){  
            // finds distance between each point and adds to total distance
            Point firstPoint = this.pointObjects.get(i);
            final Point secondPoint = this.pointObjects.get(i+1);
            totalDistance+=Point.greatCircleDistance(firstPoint, secondPoint); 
        }
        return totalDistance;

    }
   
    public double averageSpeed(){
        if (this.pointObjects.size()<2){
            throw new GPSException(null);
        }
        ZonedDateTime firstTime = this.pointObjects.get(0).getTime();
        ZonedDateTime secondTime = this.pointObjects.get(size()-1).getTime();
        double timeElapsed= ChronoUnit.SECONDS.between(firstTime,secondTime);
        double averageSpeed=totalDistance()/timeElapsed;
        return averageSpeed;
    }

}




