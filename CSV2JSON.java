import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CSV2JSON {
    public static boolean isInteger(String data_)
    {
        try{
            int a = Integer.parseInt(data_);
            return true;
        }
        catch(NumberFormatException e){
            return false;

        }

    }



    public static void writeToJSON(String payload, String fileName) throws FileNotFoundException {
    PrintWriter pw = new PrintWriter(fileName);
    pw.write(payload);
    pw.close();
    System.out.println("Written");
  }
    public static void main(String[] args) {
        Scanner input;
        String s;
        try{
            input = new Scanner(new FileInputStream("car_maintanence.csv"));
            // while(input.hasNextLine()){
            //     s = input.nextLine();
            //     System.out.println(s);

            // }
            s = input.nextLine();
            String [] attributes = s.split(",");
            String json_data="";
            json_data = json_data + "[";

            while(input.hasNextLine())
            {
                json_data = json_data + "\n\t{";
                s=input.nextLine();
                String[] data = s.split(",");
                for(int i=0; i<attributes.length; i++){
                    if(isInteger(data[i]))
                    {
                        int a = Integer.parseInt(data[i]);
                        // json_data = json_data +"   "+"\""+ attributes[i]+"\""+": "+a+"\n";
                        json_data = json_data +"\n\t\t\""+ attributes[i]+"\": "+data[i]+",";
                    }
                    else{
                        // json_data = json_data +"   "+"\""+ attributes[i]+"\""+": "+"\""+data[i]+"\","+"\n";
                        json_data = json_data +"\n\t\t\""+ attributes[i]+"\": \""+data[i]+"\",";

                    }
                }
                
                // json_data = json_data +"\n\t\t\""+ attributes[i]+"\": \""+data[i]+"\",";
                
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
}
