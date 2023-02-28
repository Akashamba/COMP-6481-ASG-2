import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CSV2JSON {
    
    static void processFilesForValidation() {

    }

    public static void main(String[] args) {
        Scanner currentFile;
        String attributeString;
        
        String fileName = "car_rental.csv";
        try{
            currentFile = new Scanner(new FileInputStream(fileName));
            attributeString = currentFile.nextLine();
            String [] attributes = attributeString.split(",");
            String json_data="";
            json_data = json_data + "[";

            while(currentFile.hasNextLine())
            {
                json_data = json_data + "\n\t{";
                attributeString=currentFile.nextLine();
                String[] data = attributeString.split(",");
                for(int i=0; i<attributes.length; i++){
                    if(isInteger(data[i]))
                        json_data = json_data +"\n\t\t\""+ attributes[i]+"\": "+data[i]+",";
                    else
                        json_data = json_data +"\n\t\t\""+ attributes[i]+"\": \""+data[i]+"\",";
                }

                // Remove comma after last attribute
                json_data = json_data.substring(0, json_data.length()-1)+"\n";
                
                json_data = json_data + "\t},";
            }
            // Remove comma after last record
            json_data = json_data.substring(0, json_data.length()-1)+"\n";

            json_data = json_data+"]";
            System.out.println(json_data);
            writeToJSON(json_data, "ABC.json");
        }
        catch(FileNotFoundException e){
            System.out.println("File not found");

        }

    }

    // Helper Functions
    static boolean isInteger(String data_)
    {
        try{
            Integer.parseInt(data_);
            return true;
        }
        catch(NumberFormatException e){
            return false;
        }

    }
    
    static String[] splitWithCommas(String delimitter, String input) {
        String[] entries = input.split(",");

        return entries;
    }

    static void writeToJSON(String payload, String fileName) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        pw.write(payload);
        pw.close();
        System.out.println("Written");
    }
}

class CSVFileInvalidException extends Exception {

}

class CSVDataMissing extends Exception {

}
