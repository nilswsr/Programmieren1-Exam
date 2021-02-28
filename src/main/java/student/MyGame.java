package student;

import java.util.ArrayList;

public class MyGame implements Game{
    public ArrayList<Card> cards = new ArrayList<Card>();
    public ArrayList<String[]> properties = new ArrayList<String[]>();
    public ArrayList<String[]> rulesInt = new ArrayList<String[]>();
    public ArrayList<String[]> rulesString = new ArrayList<String[]>();

    @Override
    public void defineCard(String name) throws GameException {
        cards.add(new Card(name));
    }

    @Override
    public void defineProperty(String name, String type) throws GameException {
        properties.add(new String[] {name, type} );
    }

    @Override
    public void setProperty(String cardName, String propertyName, String value) throws GameException {
        for(Card c : cards){
            if(c.name.equals(cardName)){
                c.properties.add(new String[] {propertyName, value});
            }
        }
    }

    @Override
    public void setProperty(String cardName, String propertyName, int value) throws GameException {
        setProperty(cardName, propertyName, Integer.toString(value));
    }

    @Override
    public void defineRule(String propertyName, String operation) throws GameException {
        rulesInt.add(new String[]{propertyName, operation});
    }

    @Override
    public void defineRule(String propertyName, String winningName, String losingName) throws GameException {
        rulesString.add(new String[]{propertyName, winningName, losingName});
    }

    @Override
    public String[] get(String type, String name) throws GameException {
        if(type.equals("game")) {
            // do whatever to do for game
        }
        else if(type.equals("card")) {
            for(Card c : cards) {
                if(c.name == name){
                    return new String[]{name};
                }
            }
            return new String[0];
        }
        else if(type.equals("property")) {
            for(String[] property : properties) {
                if(property[0] == name) {
                    return new String[]{name};
                }
            }
            return new String[0];
        }
        else if(type.equals("rule")) {
            for(String[] rule : rulesInt){
                if(rule[0] == name) {
                    return new String[]{name};
                }
            }
            for(String[] rule : rulesString){
                if(rule[0] == name) {
                    return new String[]{name};
                }
            }
            return new String[0];
        }
    }

    @Override
    public void saveToFile(String path) throws GameException {

    }

    @Override
    public Deck createDeck() {
        return null;
    }
}
