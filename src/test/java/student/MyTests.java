package student;

import static org.junit.jupiter.api.Assertions.*;

import ias.*;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Add your own tests.
 */
public class MyTests {
    private Game game;
    private Deck deck;

    @BeforeEach
    public void createGame() throws GameException {
        this.game = Factory.createGame("Test");
        this.deck = game.createDeck();
    }

    @Test
    public void selectBeatingCards_withOneIntegerProperty_cardDoesNotBeatItself() throws GameException {
        game.defineCard("Card 1");
        game.defineCard("Card 2");
        game.defineProperty("power", "integer");
        game.setProperty("Card 1", "power", 2);
        game.setProperty("Card 2", "power", 4);
        game.defineRule("power", ">");
        deck.addCard("Card 2");
        TestUtil.assertArrayEqualsUnordered(new String[]{"Card 2"}, deck.selectBeatingCards("Card 1"));
    }

    @Test
    public void saveToFile_withMultipleCardProperties_writesAllCardProperties() throws GameException, IOException {
        game.defineCard("Arbor Elf");
        game.defineCard("Realm Cloaked Giant");
        game.defineProperty("height", "integer");
        game.defineProperty("race", "string");
        game.setProperty("Arbor Elf", "race", "elf");
        game.setProperty("Realm Cloaked Giant", "race", "giant");
        game.setProperty("Arbor Elf", "height", 4);
        game.setProperty("Realm Cloaked Giant", "height", 9000);

        File targetFile = new File("Test.game");
        game.saveToFile(targetFile.getAbsolutePath());
        String file = Files.readString(targetFile.toPath());
        assertTrue(file.contains("CardProperty: Arbor Elf | race | elf"));
        assertTrue(file.contains("CardProperty: Arbor Elf | height | 4"));
        assertTrue(file.contains("CardProperty: Realm Cloaked Giant | race | giant"));
        assertTrue(file.contains("CardProperty: Realm Cloaked Giant | height | 9000"));
    }
}
