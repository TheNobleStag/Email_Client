package Client;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Birthday_List {
    final static HashMap<String, List<Recipients>> bmapPersonal = new HashMap<String, List<Recipients>>();
    final static HashMap<String, List<Recipients>> bmapOfficial = new HashMap<String, List<Recipients>>();
    static LocalDate today = LocalDate.now();
    static DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    static String formattedDate = today.format(myFormat);
    static String [] FormDate = formattedDate.split("/",2);

    public static void add_bday_Personal(Recipients bRecipient, String key){
        List<Recipients> tempL = new ArrayList<Recipients>(); 
        String [] formatBday = key.split("/", 2);
        boolean found = bmapPersonal.containsKey(formatBday[1]);
        if (found){
            tempL = bmapPersonal.get(formatBday[1]);
            tempL.add(bRecipient);
            bmapPersonal.put(formatBday[1], tempL);
        }
        else{
            tempL.add(bRecipient);
            bmapPersonal.put(formatBday[1], tempL);
        }
    }

    public static void add_bday_Official(Recipients bRecipient, String key){
        List<Recipients> tempL = new ArrayList<Recipients>(); 
        String [] formatBday = key.split("/", 2);
        boolean found = bmapOfficial.containsKey(formatBday[1]);
        if (found){
            tempL = bmapOfficial.get(formatBday[1]);
            tempL.add(bRecipient);
            bmapOfficial.put(formatBday[1], tempL);
        }
        else{
            tempL.add(bRecipient);
            bmapOfficial.put(formatBday[1], tempL);
        }
    }

    public static void sendBdayWish() throws Exception{
        boolean found1 = bmapPersonal.containsKey(FormDate[1]);
        boolean found2 = bmapOfficial.containsKey(FormDate[1]);
        List<Recipients> bdayWish1 = bmapPersonal.get(FormDate[1]);
        List<Recipients> bdayWish2 = bmapOfficial.get(FormDate[1]);
        if (found1 && bmapPersonal.get(FormDate[1])!=null){
        	for (Recipients x: bdayWish1) {
        		Email.sendMail(x.email, "Wishes", "Hugs and love on your birthday.\nTharusha Bandaranayake");
        	}
        }
        if (found2 && bmapOfficial.get(FormDate[1])!=null){
        	for (Recipients x: bdayWish2) {
        		Email.sendMail(x.email, "Wishes", "Wish you a Happy Birthday.\nTharusha Bandaranayake");
        	}
        }
    }

    public static void getBdayList(){
        boolean norec1=false,norec2=false;
        List<Recipients> temp1 = bmapPersonal.get(FormDate[1]);
        List<Recipients> temp2 = bmapOfficial.get(FormDate[1]);
        if(temp1!=null){
            for(Recipients x : temp1){
                System.out.println(x.name);
            }
            norec1=true;
        }
        if(temp2!=null){
            for(Recipients x : temp2){
                System.out.println(x.name);
            }
            norec2=true;
        }
        if(!(norec1 || norec2)){
            System.out.println("No one has their birthday today");
        }
    }
}
