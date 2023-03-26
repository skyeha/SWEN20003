public class Person {
    public String name;
    private Point location = new Point();

    private Household householdName = new Household();
    private static Person[] people = new Person[100];

    private static int peopleCount = 0;


    public Person(String name, double x, double y, String householdName) {
        this.name = name;
        this.location.setX(x);
        this.location.setY(y);
        this.householdName.setName(householdName);

        if (peopleCount < 100) {
            people[peopleCount++] = this;
        }
    }

    private void addPerson(String )
    private double distanceToPerson(Person person) {
        return this.location.distanceTo(person.location);
    }

    private Person[] peopleCloserThan(double distance) {
        int numCloser = 0;
        // Count how many people are close
        for (int i = 0; i < peopleCount; ++i) {
            if (distanceToPerson(people[i]) < distance) {
                ++numCloser;
            }
        }

        // Create an appropriately-sized array, and then fill it
        Person[] result = new Person[numCloser];
        int count = 0;
        for (int i = 0; i < peopleCount; ++i) {
            if (distanceToPerson(people[i]) < distance) {
                result[count++] = people[i];
            }
        }

        return result;
    }

    public int numCloseOutsideHousehold(double distance) {
        Person[] people = peopleCloserThan(distance);
        int count = 0;
        for (int i = 0; i < people.length; ++i) {
            // If they are not from this person's household, increment counter
            if (!people[i].householdName.equals(householdName)) {
                ++count;
            }
        }
        return count;
    }
}
