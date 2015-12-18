/**
*This class represents a Person
*@author Liubov Nikolenko
*@version 1
*/
public class Person implements Comparable<Person> {
    private String name;
    private String email;
    /**
    *Create a person
    *@param name name of a person
    *@param email email of a person
    */
    public Person(String name, String email) {
        this.name = name;
        this.email = email;

    }
    /**
    *Returns the name of the Person
    *@return name of the person.
    */
    public String getName() {
        return name;
    }
    /**
    *Returns the email of the Person
    *@return email of the person.
    */
    public String getEmail() {
        return email;
    }
    @Override
    public int compareTo(Person other) {
        return this.name.compareTo(other.name);
    }
    @Override
    public String toString() {
        return getName() + " <" + getEmail() + ">";
    }
    @Override
    public boolean equals(Object other) {
        if (this == null) {
            return false;
        }
        if (this == other) {
            return true;
        }
        if (!(other instanceof Person)) {
            return false;
        }
        Person that = (Person) other;
        return this.name.equals(that.name) && this.email.equals(that.email);
    }
    @Override
    public int hashCode() {
        return name.hashCode() + email.hashCode();
    }

}
