import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class main {
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
            input = new Scanner(new FileInputStream("car_rental.csv"));
            // while(input.hasNextLine()){
            //     s = input.nextLine();
            //     System.out.println(s);

            // }
            s = input.nextLine();
            String [] attributes = s.split(",");
            String json_data="";
            json_data = json_data + "["+"\n";

            while(input.hasNextLine())
            {
                json_data = json_data + " {\n";
                s=input.nextLine();
                String[] data = s.split(",");
                for(int i=0; i<attributes.length; i++){
                    json_data = json_data +"   "+ attributes[i]+":"+data[i]+"\n";
                    
                    // System.out.print(json_data);
                }
                json_data = json_data + " }\n";
                // System.out.print(json_data);
                

            }
            json_data = json_data+"]";
            System.out.println(json_data);
            writeToJSON(json_data, "ABC.json");
        }
        catch(FileNotFoundException e){
            System.out.println("File not found");

        }

    }
}
