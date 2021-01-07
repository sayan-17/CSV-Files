import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;


public class Class2 {
    
    private boolean isLess (double curr, double prev){
        if ( curr < prev )
            return true;
        else
            return false;
    }
    
    private boolean isLess (double curr, double prev, double falseCase){
        if ( curr == falseCase )
            return false;
        else if ( curr < prev )
            return true;
        else
            return false;
    }
    
    private boolean isLess (double curr, double prev, String falseCase){
        String x = "" + curr;
        if (falseCase.equals(x))
            return false;
        else if ( curr < prev )
            return true;
        else
            return false;
    }
    
    private CSVRecord coldestHourInFile (CSVParser parser, FileResource fr){
        parser = fr.getCSVParser();
        boolean flag = true;
        CSVRecord minTempRecord = null;
        double minTemp = 0, falseCase = -9999;
        for(CSVRecord record : parser){
            String tempData = record.get("TemperatureF");
            double currentTemp = Double.parseDouble(tempData);
            if (flag){
                if (currentTemp != falseCase){
                    minTemp = currentTemp;
                    minTempRecord = record;
                    flag = false;
                }
            }
            else if ( isLess( currentTemp, minTemp, falseCase) ){
                minTemp = currentTemp;
                minTempRecord = record;
            }
        }
        return minTempRecord;
    }
    
    private String fileWithColdestTemperature (){
        boolean flag = true;
        int i = 0;
        double minTemp = 0;
        String nameOfFile = "NO FILE FOUND";
        DirectoryResource dr = new DirectoryResource();
        CSVRecord coldestTempFile = null;
        for (File f : dr.selectedFiles()){
            FileResource fr = new FileResource (f);
            CSVRecord record = coldestHourInFile(fr.getCSVParser(),fr);
            String currTempData = record.get("TemperatureF");
            double currTemp = Double.parseDouble(currTempData);
            if(flag){
                coldestTempFile = record;
                minTemp = currTemp;
                nameOfFile = f.getName();
                flag = false;
            }
            else if (isLess(currTemp, minTemp)){
                minTemp = currTemp;
                coldestTempFile = record;
                nameOfFile = f.getName();
            }
        }
        System.out.println("Lowest Temperature recorded - " + minTemp);
        return nameOfFile;
    }
    
    CSVRecord lowestHumidityInFile (CSVParser parser, FileResource fr){
        parser = fr.getCSVParser();
        boolean flag = true;
        int i = 0;
        String falseCase = "N/A";
        CSVRecord minHumidityRecord = null;
        double minHumidity = 0;
        for(CSVRecord record : parser){
            String HumidityData = record.get("Humidity");
            double currentHumidity = Double.parseDouble(HumidityData);
            if (flag){
                if (!HumidityData.equals(falseCase)){
                    minHumidity = currentHumidity;
                    minHumidityRecord = record;
                    flag = false;
                }
            }
            else if ( isLess( currentHumidity, minHumidity, falseCase) ){
                minHumidity = currentHumidity;
                minHumidityRecord = record;
            }
        }
        return minHumidityRecord;
    }
    
    private String lowestHumidityInManyFiles (){
        boolean flag = true;
        double minHumidity = 0;
        String nameOfFile = "NO FILE FOUND";
        DirectoryResource dr = new DirectoryResource();
        CSVRecord lowestHumidityFile = null;
        for (File f : dr.selectedFiles()){
            FileResource fr = new FileResource (f);
            CSVRecord record = lowestHumidityInFile(fr.getCSVParser(), fr);
            String currHumidityData = record.get("Humidity");
            double currHumidity = Double.parseDouble(currHumidityData);
            if(flag){
                lowestHumidityFile = record;
                minHumidity = currHumidity;
                nameOfFile = f.getName();
                flag = false;
            }
            else if (isLess(currHumidity, minHumidity)){
                minHumidity = currHumidity;
                lowestHumidityFile = record;
                nameOfFile = f.getName();
            }
        }
        System.out.println("Lowest Humidity recorded - " + minHumidity);
        System.out.println(lowestHumidityFile.toString());
        return nameOfFile;
    }
    
    private double averageTemperatureInFile (CSVParser parser, FileResource fr){
        parser = fr.getCSVParser();
        int count = 0;
        double avgTemp = 0, falseCase = -9999;
        for(CSVRecord record : parser){
            String tempData = record.get("TemperatureF");
            double currentTemp = Double.parseDouble(tempData);
            if ( currentTemp != falseCase ){
                avgTemp += currentTemp;
                ++count;
            }
        }
        return avgTemp/count;
    }
    
    private double avgTempWithHighHumidity (CSVParser parser, double value, FileResource fr){
        parser = fr.getCSVParser();
        int count = 0;
        double avgTemp = 0, falseCase = -9999;
        for(CSVRecord record : parser){
            String tempData = record.get("TemperatureF");
            double currentTemp = Double.parseDouble(tempData);
            String currHumidityData = record.get("Humidity");
            double currHumidity = Double.parseDouble(currHumidityData);
            if ( currentTemp != falseCase && currHumidity >= value){
                avgTemp += currentTemp;
                ++count;
            }
        }
        if (count == 0)
            return -1;
        return avgTemp/count;
    }
    
    public void testFiles (){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        //System.out.println(" Lowest temperature hour = " + coldestHourInFile(parser, fr).toString());
        //System.out.println(" Lowest Humidity = " + lowestHumidityInFile(parser, fr).toString());
        //System.out.println(" Average temperature = " + averageTemperatureInFile(parser, fr));
        //double humidity = 80;
        //System.out.println(" Average temperature with humidity " + humidity + " = " + avgTempWithHighHumidity(parser, humidity, fr));
        
    }
    
    public void testDirectories (){
        System.out.println( "Lowest Humidity - " + lowestHumidityInManyFiles() );
        //System.out.println( "Lowest Temperature - " + fileWithColdestTemperature () );
    }
}
