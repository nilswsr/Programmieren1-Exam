package student;

import java.util.ArrayList;

public class Card {
    public String name;
    public ArrayList<String[]> properties = new ArrayList<String[]>();

    public Card(String name) {
        this.name = name;
    }
}