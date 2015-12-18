import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
/**
*Creates a Mailbox
*@author Liubov Nikolenko
*@version 1.0
*/
public class Mailbox implements Iterable<Message> {
    private boolean isSortedBySubject = false;
    private boolean isSortedBySender = false;
    private ArrayList<Message> g;
    private String name;
    /**
    *Creates an empty mailbox
    *@param name name of the mailbox
    */
    public Mailbox(String name) {
        this.name = name;
        g = new ArrayList<Message>();
    }
    /**
    *return the name of a mailbox
    *@return subject of a mailbox
    */
    public String getName() {
        return name;
    }
    /**
    *Adds a message to the mailbox
    *@param c a message you want to add
    *@return boolean according to the ArrayList add method
    */
    public boolean add(Message c) {
        return g.add(c);
    }
    /**
    *Removes a message from the mailbox
    *@param c a message you want to mailbox
    *@return boolean according to the ArrayList remove method
    */
    public boolean remove(Message c) {
        return g.remove(c);
    }
    /**
    *Removes a message which has the specified index
    *@param i index of the message you want to remove
    *@return the message you have removed
    */
    public Message remove(int i) {
        return g.remove(i);
    }
    /**
    *Nicely displays mailbox to the user
    *@return String reprezentation of the mailbox
    */
    public String toString() {
        String s = name + "\n";
        Collections.sort(g);
        for (int i = 0; i < g.size(); i++) {
            s = s + g.get(i) + "\n";
        }
        return s;
    }
    /**
    *Gets the message with the specified index
    *@param i an index of the message you want to get
    *@return a message with the given index
    */
    public Message get(int i) {
        return g.get(i);
    }
    /**
    *Returns the size of the mailbox
    *@return the size of the mailbox
    */
    public int size() {
        return g.size();
    }
    /**
    *Returns if the mailbox contains specified message
    *@param c message you want to check
    *@return if the mailbox contains specified message
    */
    public boolean contains(Message c) {
        return g.contains(c);
    }
    /**
    *Returns an iterator for the mailbox
    *@return an iterator for the mailbox
    */
    public Iterator<Message> iterator() {
        return g.iterator();
    }
    /**
    *Checks if the mailbox is empty
    *@return if the mailbox is empty
    */
    public boolean isEmpty() {
        return g.isEmpty();
    }
    /**
    *Moves the message to another mailbox
    *@param m message
    *@param other mailbox to which you want to move the message
    */
    public void moveTo(Message m, Mailbox other) {
        other.add(m);
        g.remove(m);
    }
    /**
    *Returns the ArrayList of the messages in the mailbox
    *@return ArrayList of the messages in the mailbox
    */
    public ArrayList<Message> getMessages() {
        return g;
    }
    /**
    *Sorts the messages in the mailbox according to the previous
    *sorting method applied on them (default is sorting by date) and
    *returns a sorted ArrayList
    *@return a sorted ArrayList
    */
    public ArrayList<Message> sort() {
        if (isSortedBySender) {
            return sortBySender();
        }
        if (isSortedBySubject) {
            return sortBySubject();
        } else {
            Collections.sort(g);
            return g;
        }
    }
    /**
    *Sorts the messages in the mailbox by subject and
    *returns a sorted ArrayList
    *@return a sorted ArrayList
    */
    public ArrayList<Message> sortBySubject() {
        isSortedBySubject = true;
        isSortedBySender = false;
        Collections.sort(g, (Message m1, Message m2) ->
            m1.getSubject().compareTo(m2.getSubject()));
        return g;
    }
    /**
    *Sorts the messages in the mailbox by sender and
    *returns a sorted ArrayList
    *@return a sorted ArrayList
    */
    public ArrayList<Message> sortBySender() {
        isSortedBySender = true;
        isSortedBySubject = false;
        Collections.sort(g, (Message m1, Message m2) ->
            m1.getSender().compareTo(m2.getSender()));
        return g;
    }
    /**
    /**
    *Sorts the messages in the mailbox by date and
    *returns a sorted ArrayList
    *@return a sorted ArrayList
    */
    public ArrayList<Message> sortByDate() {
        isSortedBySender = false;
        isSortedBySubject = false;
        Collections.sort(g);
        return g;
    }

}
