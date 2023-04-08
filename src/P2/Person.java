package P2;

import java.util.Objects;

public class Person {
    private final String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Person other)) return false;
        return this.name.equals(other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}