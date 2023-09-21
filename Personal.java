package Client;

public class Personal extends Recipients{
    protected String name,nickname,email,bday;
    public Personal(String name, String email, String [] data){
        super(name, email);
        this.nickname = data[1];
        this.bday = data[3];
    }
}
