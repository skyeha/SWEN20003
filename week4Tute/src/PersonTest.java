public class PersonTest {
    public static void main(String[] args) {
        Person person1 = new Person("Sku", 0,0,"Ha");
        Person person2 = new Person("Lam", 1,1,"Le");
        Person person3 = new Person("Joey",2,2, "Lim");

        System.out.println(person1.numCloseOutsideHousehold(3));
        System.out.println(person1.getHouseholdName().getName());
    }
}
