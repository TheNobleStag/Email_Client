package Client;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class RecipientFactory {
    static LocalDate today = LocalDate.now();
    static DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    static String formattedDate = today.format(myFormat);
    static String [] FormDate = formattedDate.split("/",2);
    private Recipients recipient;
    public boolean dateValid;
    public RecipientFactory(String dataString, boolean newEntry){
        try {
            String [] y = dataString.split(":",2);
            String type = y[0];
            y[1] = y[1].strip();
            String [] data = y[1].split(",",0);

            if (type.equalsIgnoreCase("Official")){
                this.recipient = new Official(data[0].strip(), data[1].strip(), data);
                this.dateValid = true;
            }
            else if (type.equalsIgnoreCase("Office_Friend")){
                this.recipient = new Official_Friends(data[0].strip(), data[1].strip(), data);
                this.dateValid = true;
                try {
                    String[] temptemp = data[3].split("/",0);
                    String temp = temptemp[0]+"-"+temptemp[1]+"-"+temptemp[2];
                    LocalDate.parse(temp);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid Birthday");
                    this.dateValid = false;
                }
                if(this.dateValid){
                    Birthday_List.add_bday_Official(this.recipient, data[3]);
                    if(FormDate[1].equals(data[3].substring(5)) && newEntry){
                        Email.sendMail(this.recipient.email, "Wishes", "Wish you a Happy Birthday.\nTharusha Bandaranayake");
                    }
                }
            }
            else if (type.equalsIgnoreCase("Personal")){
                this.recipient = new Personal(data[0].strip(), data[2].strip(), data);
                this.dateValid = true;
                try {
                    String[] temptemp = data[3].split("/",0);
                    String temp = temptemp[0]+"-"+temptemp[1]+"-"+temptemp[2];
                    LocalDate.parse(temp);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid Birthday");
                    this.dateValid = false;
                }
                if(this.dateValid){
                    Birthday_List.add_bday_Personal(this.recipient, data[3]);
                    if(FormDate[1].equals(data[3].substring(5)) && newEntry){
                        Email.sendMail(this.recipient.email, "Wishes", "Hugs and love on your birthday.\nTharusha Bandaranayake");
                    }
                }
            }
            else{
                System.out.println("Invalid input");
            }
        } 
        catch (Exception e) {
            //System.out.println("Invalid input");
        }    
    }    

    public Recipients getRecipient(){
        return this.recipient;
    }   
    
    public static boolean invalid(Recipients x){
        if(x.email.contains(".") && x.email.contains("@") && !x.email.contains(" ")){
            return true;
        }
        return false;
    } 
}
