package Client;

public class Official extends Recipients {
    protected String name,title,email;
    public Official(String name, String email,String [] data){
        super(name, email);
        this.title = data[2];
    }
}
