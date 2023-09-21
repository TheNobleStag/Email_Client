package Client;

import java.io.Serializable;
import java.util.Date;

public class EmailObject implements Serializable {
    private static final long serialVersionUID = 1964655827880334299L;
    String recipient, subject, content;
    Date date;
    EmailObject(String recipient, String subject, String content, Date date){
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
        this.date = date;
    }
}
