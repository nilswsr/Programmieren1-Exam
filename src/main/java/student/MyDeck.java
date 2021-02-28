package student;

import ias.Deck;
import ias.GameException;

import java.util.ArrayList;

public class MyDeck implements Deck {
    private ArrayList<Card> cardsInDeck = new ArrayList<>();

    public MyDeck(){
        ArrayList<Card> cards = MyGame.getCards();
    }

    @Override
    public void addCard(String cardName) throws GameException {
    }

    @Override
    public String[] getAllCards() {
        return new String[0];
    }

    @Override
    public String[] getMatchingCards(String propertyName, int value) throws GameException {
        return new String[0];
    }

    @Override
    public String[] getMatchingCards(String propertyName, String value) throws GameException {
        return new String[0];
    }

    @Override
    public String[] selectBeatingCards(String opponentCard) throws GameException {
        return new String[0];
    }
}
