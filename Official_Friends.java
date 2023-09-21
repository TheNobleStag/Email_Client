package Client;

public class Official_Friends extends Recipients {
    protected String name,title,email,bday;
    public Official_Friends(String name, String email, String [] data){
        super(name, email);
        this.title = data[2];
        this.bday = data[3];
    }
}
