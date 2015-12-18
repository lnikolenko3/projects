import java.time.LocalDateTime;
import java.util.HashSet;
/**
*This calss represents a message
*@author Liubov Nikolenko
*@version 1
*/
public class Message implements Comparable<Message> {
    private Person sender;
    private HashSet<Person> recipients;
    private String subject;
    private LocalDateTime date;
    private String body;
    /**
    *Creates a message
    *@param sender sender
    *@param recipients recipients
    *@param subject subject
    *@param date date
    *@param body body
    */
    public Message(Person sender, HashSet<Person> recipients,
        String subject, LocalDateTime date, String body) {
        this.sender = sender;
        this.recipients = recipients;
        this.subject = subject;
        this.date = date;
        this.body = body;
    }
    /**
    *returns a sender of a message
    *@return sender
    */
    public Person getSender() {
        return sender;
    }
    /**
    *returns the recipients of a message
    *@return recipients
    */
    public HashSet<Person> getRecipients() {
        return recipients;
    }
    /**
    *return the subject of a message
    *@return subject of a message
    */
    public String getSubject() {
        return subject;
    }
    /**
    *return the date of a message
    *@return date of a message
    */
    public LocalDateTime getDate() {
        return date;
    }
    /**
    *return the body of a message
    *@return the body of a message
    */
    public String getBody() {
        return body;
    }
    @Override
    public boolean equals(Object other) {
        if (this == null) {
            return false;
        }
        if (this == other) {
            return true;
        }
        if (!(other instanceof Message)) {
            return false;
        }
        Message that = (Message) other;
        return this.sender.equals(that.sender)
            && this.recipients.equals(that.recipients)
            && this.subject.equals(that.subject)
            && this.date.equals(that.date)
            && this.body.equals(that.body);
    }
    @Override
    public int hashCode() {
        return sender.hashCode() + recipients.hashCode() + subject.hashCode();
    }
    @Override
    public int compareTo(Message other) {
        return -this.date.compareTo(other.date);
    }
    @Override
    public String toString() {
        return sender.getName() + ": \n" + subject;
    }


}
