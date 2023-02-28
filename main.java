import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class main {
    public static void writeToJSON(String payload, String fileName) throws FileNotFoundException {
    PrintWriter pw = new PrintWriter("Car Rental Record.json");
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
            for(int i=0; i<attributes.length; i++){
                System.out.println(attributes[i]);
            }
        }
        catch(FileNotFoundException e){
            System.out.println("File not found");

        }

    }
}
