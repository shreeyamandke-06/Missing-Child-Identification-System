package dbms_proj;

public class PersonItem {

    private int id;
    private String name;

    public PersonItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
