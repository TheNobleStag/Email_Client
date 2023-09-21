package Client;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Active_Recipients {
    public static boolean add_Recipients(String recipient) throws IOException{
        BufferedWriter out = new BufferedWriter(new FileWriter("Recipients.txt", true));
        try{
            String [] y = recipient.split(":",2);
            String type = y[0];
            if(type.equalsIgnoreCase("Official") || type.equalsIgnoreCase("Office_Friend") || type.equalsIgnoreCase("Personal")){
                out.write(recipient);
                out.close();
                System.out.println("Recipient added successfully");
                return true;
            }
        }
        catch(IOException e){
            System.out.println("Error occurd when writing to the file");
        }
        finally{
            out.close();
        }
        return false;
    }
}
