import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CSV2JSON {
    static void processFilesForValidation(String[] attributes,String[] data, String filename, int linenumber, int count) throws CSVFileInvalidException, CSVDataMissing 
    {
        String file = filename;
        int k;
        if(count == 0)
        {
            for(k=0; k<attributes.length; k++)
            {
                if(attributes[k].length() == 0  || attributes[k].equals(" "))
                {
                    CSVFileInvalidException ex = new CSVFileInvalidException(attributes, file);
                    throw ex;
                    // attributes[k] = "***";
                    // missing_attribute++;
                         
                }
            }

        }
        if(count==1){
            for(k=0; k<data.length; k++)
            {
                if(data[k].length() == 0  || data[k].equals(" "))
                {
                    CSVDataMissing ex = new CSVDataMissing(attributes,data, file,linenumber);
                    throw ex;
                    // attributes[k] = "***";
                    // missing_attribute++;
                         
                }
            }

        }
        
    }

    public static void main(String[] args) 
    {
        Scanner currentFile = null;
        Scanner input = new Scanner(System.in);
        String attributeString;
        String[] attributes;
        String[] data={};

        System.out.println("How many files do you want to process? ");
        int number_of_files = Integer.parseInt(input.nextLine());
        
        String[] files = new String[number_of_files];
        System.out.println("Enter the name of the files");
        for(int m=0; m<number_of_files; m++)
        {
            String file_name = input.nextLine();
            
            files[m] = file_name;
        }
        for(int m=0; m<files.length; m++)
        {
            String fileName = files[m];
            try
            {
                currentFile = new Scanner(new FileInputStream(fileName));
                attributeString = currentFile.nextLine();
                if(attributeString.endsWith(","))
                {
                    attributeString = attributeString+" ";
                }
                attributes = splitIgnoreInQuotes(",", attributeString);
                try
                {
                    processFilesForValidation(attributes, data, fileName, 0,0);
                }
                catch(CSVFileInvalidException e)
                {
                    new CSVFileInvalidException(attributes, fileName);
                    continue;
                }
                catch(CSVDataMissing e)
                {

                }
            
                //now will handle data
                String json_data="";
                json_data = json_data + "[";
                int linenumber = 0;

                while(currentFile.hasNextLine())
                {
                    linenumber++;
                    // json_data = json_data + "\n\t{";
                    attributeString=currentFile.nextLine();
                    if(attributeString.endsWith(","))
                    {
                        attributeString = attributeString+" ";
                    }
                    data = splitIgnoreInQuotes(",", attributeString);
                    try
                    {
                        processFilesForValidation(attributes, data, fileName, linenumber,1);
                    }
                    catch(CSVDataMissing e)
                    {
                        new CSVDataMissing(data, attributes, fileName, linenumber);
                        continue;
                    }
                    catch(CSVFileInvalidException e)
                    {

                    }
                    // for(int i=0; i<data.length; i++){
                    //     System.out.println(data[i]);
                    
                    // }
                    // System.exit(0);
                    json_data = json_data + "\n\t{";

                    for(int i=0; i<attributes.length; i++)
                    {
                        if(isInteger(data[i])){
                            json_data = json_data +"\n\t\t\""+ attributes[i]+"\": "+data[i]+",";

                        }
                        else
                        {
                            json_data = json_data +"\n\t\t\""+ attributes[i]+"\": \""+data[i]+"\",";
                        }
                        
                    }

                    // Remove comma after last attribute
                    json_data = json_data.substring(0, json_data.length()-1)+"\n";
                
                    json_data = json_data + "\t},";
                }
                // Remove comma after last record
                json_data = json_data.substring(0, json_data.length()-1)+"\n";

                json_data = json_data+"]";
                // System.out.println(json_data);
                writeToJSON(json_data, fileName);
            }
            catch(FileNotFoundException e)
            {
                System.out.println("File not found");
            }
            finally
            {
                currentFile.close();
            }
        }
        
        // String fileName = "car_rental_no_DrivLic.txt";
        // try
        // {
        //     currentFile = new Scanner(new FileInputStream(fileName));
        //     attributeString = currentFile.nextLine();
        //     if(attributeString.endsWith(","))
        //     {
        //         attributeString = attributeString+" ";
        //     }
        //     attributes = attributeString.split(",");
        //     try{
        //         processFilesForValidation(attributes, data, fileName, 0);
        //     }
        //     catch(CSVFileInvalidException e){
        //         new CSVFileInvalidException(attributes, fileName);
        //         System.exit(0);

        //     }
        //     catch(CSVDataMissing e){

        //     }
            
        //     //now will handle data
        //     String json_data="";
        //     json_data = json_data + "[";
        //     int linenumber = 0;

        //     while(currentFile.hasNextLine())
        //     {
        //         linenumber++;
        //         // json_data = json_data + "\n\t{";
        //         attributeString=currentFile.nextLine();
        //         if(attributeString.endsWith(","))
        //         {
        //             attributeString = attributeString+" ";
        //         }
        //         data = attributeString.split(",");
        //         try{
        //             processFilesForValidation(attributes, data, fileName, linenumber);
        //         }
        //         catch(CSVDataMissing e){
        //             new CSVDataMissing(data, attributes, fileName, linenumber);
        //             continue;
        //         }
        //         catch(CSVFileInvalidException e){

        //         }
        //         // for(int i=0; i<data.length; i++){
        //         //     System.out.println(data[i]);
                    
        //         // }
        //         // System.exit(0);
        //         json_data = json_data + "\n\t{";

        //         for(int i=0; i<attributes.length; i++){
        //             if(isInteger(data[i]))
        //                 json_data = json_data +"\n\t\t\""+ attributes[i]+"\": "+data[i]+",";
        //             else
        //                 json_data = json_data +"\n\t\t\""+ attributes[i]+"\": \""+data[i]+"\",";
        //         }

        //         // Remove comma after last attribute
        //         json_data = json_data.substring(0, json_data.length()-1)+"\n";
                
        //         json_data = json_data + "\t},";
        //     }
        //     // Remove comma after last record
        //     json_data = json_data.substring(0, json_data.length()-1)+"\n";

        //     json_data = json_data+"]";
        //     System.out.println(json_data);
        //     writeToJSON(json_data, "ABC.json");
        // }
        // catch(FileNotFoundException e)
        // {
        //     System.out.println("File not found");

        // }
        input.close();
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

    // Function to split string by given delimitter, but ignore instances that occur within quotes
    static String[] splitIgnoreInQuotes(String delimitter, String input) {
        String[] entries = input.split(",");
        int[] ignoreIndices = new int[entries.length/2];
        int ignoreCount = 0;
        String[] finalEntries = null;

        for (int i=0; i<ignoreIndices.length; i++)
            ignoreIndices[i] = -1;

        try {
            for (int i=0; i<entries.length-1; i++) {
                if (entries[i] != "" && substringCount(entries[i], "\"")%2 == 1) {
                    int mergeCount = 1;
                    while (substringCount(entries[i+mergeCount], "\"")%2 == 0)
                        mergeCount++;

                    for (int j=0; j<mergeCount; j++) {
                        entries[i] += ","+entries[i+j+1];
                        entries[i+j+1] = "";
                        ignoreIndices[ignoreCount] = i+j+1;
                        ignoreCount++;
                    }
                }
            }
        
            finalEntries = new String[entries.length-ignoreCount];
            int ignoreIndex = 0;
            int finalIndex = 0;
            for (int i=0; i<entries.length; i++) {
                if (i!=ignoreIndices[ignoreIndex]) {
                    entries[i] = entries[i].replace("\"", "");
                    finalEntries[finalIndex] = entries[i];
                    finalIndex++;
                } else {
                    ignoreIndex++;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please check to enure that the CSV file has commas");
            System.exit(0);
        }

        return finalEntries;
    }

    static int substringCount(String str, String key) {
        int count = 0;
        for (String s : str.split("")) {
            if (s.equals(key)) {
                count++;
            }
        }
        return count;
    }

    static void writeToJSON(String payload, String fileName) throws FileNotFoundException {
        String file = fileName+".json";
        PrintWriter pw = new PrintWriter(file);
        pw.write(payload);
        pw.close();
        System.out.println("Written");
    }
}

class CSVFileInvalidException extends Exception 
{
    public CSVFileInvalidException(String[] attributes, String file)
    {
        int k;
        int missing_attribute=0;
        for(k=0; k<attributes.length; k++)
        {
            if(attributes[k].length() == 0  || attributes[k].equals(" "))
            {
                attributes[k] = "***";
                missing_attribute++;
                     
            }
        }

        if(missing_attribute>0)
        {
            PrintWriter log = null;
            String logString = "";
            try {
                log = new PrintWriter(new FileWriter("log.txt", true));
            } catch (FileNotFoundException e) {
                System.out.println("Log file not found");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            System.out.println("File "+file+" is invalid: field is missing");
            System.out.println("File is not converted to JSON");

            logString+="File "+file+" is invalid.\n";
            logString+="Missing field: " + (attributes.length-missing_attribute)+" detected," + missing_attribute + " missing\n";
            for(k=0; k<attributes.length; k++)
            {
                if(k==(attributes.length-1))
                {
                    logString+=attributes[k]+"\n";
                }
                else
                {
                    logString+=attributes[k]+",";

                }
                
            }
            log.println(logString);
            log.close();
        }
    }
}

class CSVDataMissing extends Exception {
    public CSVDataMissing(String[] attributes,String[] data, String file, int linenumber)
    {
        int k;
        int missing_attribute=0;
        for(k=0; k<data.length; k++)
        {
            if(data[k].length() == 0  || data[k].equals(" "))
            {
                data[k] = "***";
                missing_attribute++;
                     
            }
        }
        if(missing_attribute>0)
        {

            PrintWriter log = null;
            String logString = "";
            try {
                log = new PrintWriter(new FileWriter("log.txt", true));
            } catch (FileNotFoundException e) {
                System.out.println("Log file not found");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            System.out.println("In File "+file+" line" +linenumber +" not converted to json: missing data");
            logString+="In file "+file+" line"+ linenumber+"\n";
            for(k=0; k<data.length; k++)
            {
                if(k==(data.length-1))
                {
                    logString+=data[k]+"\n";
                }
                else
                {
                    logString+=data[k]+",";

                }
                
            }
            logString+="Missing: ";
            for(k=0; k<attributes.length; k++){
                if(data[k].equals("***")){
                    logString+=attributes[k];
                }
            }

            log.println(logString+"\n");
            log.close();
        }
    }

}

