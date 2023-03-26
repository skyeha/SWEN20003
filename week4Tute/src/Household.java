public class Household {
    private String name;
    private int numMember = 0;
    private Person[] members;

    private final int maxMember;

    public Household() {
        maxMember = 5;
        members = new Person[maxMember - 1];
    }
    private boolean contains(Person person) {
        for (int i = 0; i < numMember; i++) {
            if (members[i].name.equalsIgnoreCase(person.name)) {
                return true;
            }
        }
        return false;
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}


}