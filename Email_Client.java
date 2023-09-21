package Client;

import java.util.*;
import javax.mail.internet.AddressException;
//import java.util.regex.Pattern;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
// import java.time.LocalDate;
// import java.time.format.DateTimeParseException;

public class Email_Client {
    static int recipientCount=0;
    static HashMap<String, List<EmailObject>> emailSent = new HashMap<String, List<EmailObject>>();
    public void start() throws Exception{
        try{
            File myFile = new File("Recipients.txt");
            myFile.createNewFile();
            Scanner myReader = new Scanner(myFile).useDelimiter("\\n");
            while(myReader.hasNextLine()){
                String Recipient = myReader.nextLine();
                new RecipientFactory(Recipient, false);
                recipientCount++;
            }
            myReader.close();
        }
        catch(IOException e){
            System.out.println("Error occurd when reading from file");
        }
        Birthday_List.sendBdayWish();
    }

    public void deserialize() throws FileNotFoundException, IOException, ClassNotFoundException{
        int objectCount=0;
        File myObj1 = new File("SerializedObjects.txt");
        myObj1.createNewFile();
        BufferedReader newReader = new BufferedReader(new FileReader(myObj1));
        try {
            while(newReader.readLine()!=null){
                objectCount++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally{
            newReader.close();
        }
        
        List<EmailObject> EmailList = new ArrayList<EmailObject>();
        File myObj2 = new File("EmailDetails.ser");
        myObj2.createNewFile();
        if(objectCount!=0){
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(myObj2));
            try{
                for(int i=0; i<objectCount; i++){
                    EmailList.add((EmailObject) in.readObject());
                }
            } catch (IOException e){
                System.out.println("Error while reading the Email Details");
            }
            finally{
                in.close();
            }
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");   
        for(int i=0; i<EmailList.size(); i++){
            String strDate = dateFormat.format(EmailList.get(i).date);
            if(emailSent.get(strDate)!=null){
                List<EmailObject> temp = new ArrayList<EmailObject>();
                temp = emailSent.get(strDate);
                temp.add(EmailList.get(i));
                emailSent.put(strDate, temp);
            }
            else{
                List<EmailObject> temp = new ArrayList<EmailObject>();
                temp.add(EmailList.get(i));
                emailSent.put(strDate, temp);
            }
        }
    }

    public Email_Client(){
    }

    public static void main(String[] args) throws Exception {
        Email_Client eClient = new Email_Client();
        eClient.start();
        boolean running = true;
        Scanner option = new Scanner(System.in);
        Scanner newRec = new Scanner(System.in).useDelimiter("\\n");
        Scanner contentScanner = new Scanner(System.in).useDelimiter("/exit");
        while(running){
            eClient.deserialize();
            System.out.println("1 - Add a new recipient");
            System.out.println("2 - Send an email");
            System.out.println("3 - Print the names of recipients who have their birthday today");
            System.out.println("4 - Print the details of all the emails sent on a specific date");
            System.out.println("5 - Print the number of recipient objects");
            System.out.println("6 - Exit");
            String num = option.next();
            switch(num){
                case "1":
                    System.out.println("Enter the recipient details in the following order");
                    System.out.println("Type: Name,Nickname if available,Email,Designation if available,Birthday if available (YYYY/MM/DD)");
                    System.out.println("Accepted types are Official, Office_Friend, Personal");

                    String dataString = newRec.next();
                    RecipientFactory NEW = new RecipientFactory(dataString, true);
                    if(RecipientFactory.invalid(NEW.getRecipient()) && NEW.dateValid){
                        if(Active_Recipients.add_Recipients(dataString)){
                            recipientCount++;
                        }    
                        else{
                            System.out.println("Invalid recipient email");
                        }
                    }
                    System.out.println();
                    break;
                case "2":
                    String Content = ""; 
                    //String ContentTemp = "";
                    //The following code segment is to send an email with line/tab seperations but it is a little hard to draft the message
                    //This code segment uses a single line input
                    //To use this code segment please uncomment the imported library at the top of this class
                    /*
                    System.out.println("<Enter recipient email> <subject> <content([For a new line : type \\n][For a tab space : type \\t])>");
                    int i=1;
                    String [] emailObject = scan.nextLine().split(">",4);
                    String [] temp = emailObject[2].split(Pattern.quote("\\"),0);
                    Content = Content + temp[0].strip().substring(1);
                    while(i<temp.length){
                        char temptemp = temp[i].strip().charAt(0);
                        if(temptemp=='n'){
                            ContentTemp=temp[i].strip().substring(1);
                            Content = Content + "\n" + ContentTemp;
                        }
                        else if(temptemp=='t'){
                            ContentTemp=temp[i].strip().substring(1);
                            Content = Content + "\t" + ContentTemp;
                        }
                        i++;
                    }
                    Email.sendMail(emailObject[0].substring(1), emailObject[1].strip().substring(1), Content);
                    System.out.println("Email sent successfully");
                    */

                    //The following code segment is to send an email with line/tab seperations using normal procedure and it is easy to draft the message
                    //This code uses a three line input
                    String Recipient, Subject;
                    System.out.println("Enter the recipient email :");
                    option.nextLine();
                    Recipient = option.nextLine();
                    if(Recipient.contains(" ") || (!Recipient.contains(".") && !Recipient.contains("@"))){
                        System.out.println("Invalid email address");
                        break;
                    }
                    System.out.println("Enter the subject :");
                    Subject = option.nextLine();
                    System.out.println("Enter the message (Type /exit when the message is finished):");
                    Content = contentScanner.next();
                    try {
                        if(Email.sendMail(Recipient, Subject, Content)){
                            System.out.println("Email sent successfully");
                        }
                    } catch (AddressException e) {
                        System.out.println("Invalid email address");
                    }
                    System.out.println();
                    break;
                case "3":
                    Birthday_List.getBdayList();
                    System.out.println();
                    break;
                case "4":
                    System.out.println("Enter the date (YYYY/MM/DD)");
                    option.nextLine();
                    String sentDate = option.nextLine();
                    // try {
                    //     LocalDate.parse(sentDate);
                    // } catch (DateTimeParseException e) {
                    //     System.out.println("Invalid date");
                    //     break;
                    // }
                    List<EmailObject> EmailListdat = new ArrayList<EmailObject>();
                    if(emailSent!=null){
                        EmailListdat = emailSent.get(sentDate);
                    }
                    if(EmailListdat!=null){
                        for(EmailObject x : EmailListdat){
                            System.out.println("To: "+x.recipient);
                            System.out.println("Subject: "+x.subject);
                            System.out.println('\n');
                        }
                    }
                    else{
                        System.out.println("No emails sent on this date");
                    }
                    System.out.println();
                    break;
                case "5":
                    System.out.println("Number of recipients : " + recipientCount);
                    System.out.println();
                    break;
                case "6":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid input. Try again");
            }
        }
        option.close();
        newRec.close();
        contentScanner.close();
    }
}
