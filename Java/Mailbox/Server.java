import java.util.Random;
import java.time.LocalDateTime;
import java.util.HashSet;
/**
*Creates a Server the spits messages to the mailbox
*@author Liubov Nikolenko
*@version 1
*/
public class Server {
    private static final String [] SENDERS = {"Sam", "Ann", "Pete", "Nikki"};
    private static final String [] SUBJECTS = {"Food", "Party",
        "Spam", "Study"};
    private static final Person RECIPIENTS = new Person("Cat", "@");
    private static final String [] BODYS = {"Test 1", "Test 2",
        "Test 3", "Test 4"};
    private static final String [] MAIL = {"fff@mail.ru", "super@gatech.edu",
        "pizza@gmail.com", "palm@rambler.ru"};
        /**
        *Spits a randomly generated message to the mailbox
        *@param inbox the mailbox that will receive the message
        *@return generated message
        */
    public Message getMessage(Mailbox inbox) {
        Random o = new Random();
        HashSet<Person> rec = new HashSet<>();
        LocalDateTime date = LocalDateTime.now();
        rec.add(RECIPIENTS);
        int isend = o.nextInt(4);
        int imail = o.nextInt(4);
        Person sender = new Person(SENDERS[isend], MAIL[imail]);
        int isub = o.nextInt(4);
        String subject = SUBJECTS[isub];
        int ib = o.nextInt(4);
        String body = BODYS[ib];
        Message m = new Message(sender, rec, subject, date, body);
        inbox.add(m);
        return m;
    }
}
