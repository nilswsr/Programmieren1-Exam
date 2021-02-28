package student;

import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import ias.GameException;
import ias.Deck;

public class MyGame implements Game{
    public ArrayList<Card> cards = new ArrayList<Card>();
    public ArrayList<String[]> properties = new ArrayList<String[]>();
    public ArrayList<String[]> rulesInt = new ArrayList<String[]>();
    public ArrayList<String[]> rulesString = new ArrayList<String[]>();
    public String game_name;
    
    public MyGame(String name) {
        this.game_name = name;
    }
    
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
            if(name != "*") {
                for (Card c : cards) {
                    if (c.name == name) {
                        return new String[]{name};
                    }
                }
                return new String[0];
            }
            return cards.toArray(new String[0]);
        }
        else if(type.equals("property")) {
            if(name != "*") {
                for (String[] property : properties) {
                    if (property[0] == name) {
                        return new String[]{name};
                    }
                }
                return new String[0];
            }
            return properties.toArray(new String[0]);
        }
        else if(type.equals("rule")) {
            if (name != "*") {
                for (String[] rule : rulesInt) {
                    if (rule[0] == name) {
                        return new String[]{name};
                    }
                }
                for (String[] rule : rulesString) {
                    if (rule[0] == name) {
                        return new String[]{name};
                    }
                }
                return new String[0];
            }

            String[] output_array = new String[rulesInt.size() + rulesString.size()];
            for(int i = 0; i < rulesInt.size(); i++) {
                output_array[i] = rulesInt.get(i)[0] + rulesInt.get(i)[1];
            }
            for(int j = 0; j < rulesString.size(); j++) {
                output_array[j + rulesInt.size()] = rulesString.get(j)[0] + ":" + rulesString.get(j)[1] + ">" + rulesString.get(j)[2];
            }
            return output_array;
        }
        return new String[0];
    }

    @Override
    public void saveToFile(String path) throws GameException {
        try {
            File myFile = new File(path);
            myFile.createNewFile();
        } catch (IOException e) {
        }

        try {
            FileWriter myWriter = new FileWriter(path);
            myWriter.write("Game: " + game_name + "\n");

            for (Card card : cards) {
                myWriter.write("Card: " + card.name + "\n");
            }

            for (String[] property : properties) {
                myWriter.write("Property: " + property[0] + " | " + property[1] + "\n");
            }

            for (Card card : cards) {
                ArrayList<String[]> card_properties = card.properties;
                for (String[] card_property : card_properties) {
                    myWriter.write("CardProperty: " + card_property[0] + " | " + card_property[1] + " | " + card_property[2] + "\n");
                }
            }

            for (String[] rule : rulesInt) {
                myWriter.write("GameRuleInteger: " + rule[0] + " | " + rule[1] + "\n");
            }

            for (String[] rule : rulesString) {
                myWriter.write("GameRuleString: " + rule[0] + " | " + rule[1] + " | " + rule[2] + "\n");
            }

        } catch (IOException e) {
        }


    }

    @Override
    public Deck createDeck() {
        return null;
    }
}
