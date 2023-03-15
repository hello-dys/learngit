package P3;

public class Person
{
    private final String Name;
    public Person (String Name)
    {
        this.Name = Name;
    }
    public String getName()
    {
        return this.Name;
    }
    public boolean isSameName(String Name)
    {
        return this.Name.equals(Name);
    }
}