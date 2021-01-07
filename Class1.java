import edu.duke.*;
import org.apache.commons.csv.*;


public class Class1 {
    
    private FileResource fr = new FileResource();
    
    public void tester (){
        CSVParser parser = fr.getCSVParser();
        System.out.println("Finding Germany - " + countryInfo(parser, "Nauru"));
        System.out.println("\nFinding two exporters - ");
        listExportersTwoProducts (parser, "cotton", "flowers");
        System.out.println("\nFinding one exporter - ");
        numberOfExporters (parser, "cocoa");
        System.out.println("\nFinding big Exporters - ");
        bigExporters (parser, "$999,999,999");
    }
    
    private String countryInfo ( CSVParser parser, String country){
        parser = fr.getCSVParser();
        for( CSVRecord record : parser){
        String con = record.get("Country");
        if (con.equals(country)){
            String details = record.toString();
            return details;
        }
        }   
        return "NOT FOUND";
    }
    
    private void listExportersTwoProducts ( CSVParser parser, String export1, String export2){
        parser = fr.getCSVParser();
        for( CSVRecord record : parser ){
            String export = record.get("Exports");
            if (export.contains(export1) && export.contains(export2))
                System.out.print(record.get("Country") + ", ");
        }
    }
    
    private void numberOfExporters ( CSVParser parser, String exportItem){
        parser = fr.getCSVParser();
        int count = 0;
        for( CSVRecord record : parser ){
            String export = record.get("Exports");
            if (export.contains(exportItem)){
                count++;
            }
        }
        System.out.println("Countries with the export item " + exportItem + " is " + count);
    }
    
    private void bigExporters ( CSVParser parser, String amount ){
        parser = fr.getCSVParser();
        int lenAmount = amount.length();
        for( CSVRecord record : parser ){
            String recordAmount = record.get("Value (dollars)");
            int len = recordAmount.length();
            if (len > lenAmount){
                System.out.println(record.get("Country") + " , " + recordAmount);
            }
        }
    }
}
