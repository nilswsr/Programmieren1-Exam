package student;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import ias.Factory;
import ias.Game;
import ias.GameException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Add your own tests.
 */
public class MyTests {
    private Game game;

    @BeforeEach
    public void createGame() throws GameException {
        game = Factory.createGame("Test");
    }

    /*
    @Test
    public void Factory_returnssomething() throws GameException {
        Game game = Factory.createGame("Testing 123");
        assertNotNull(game);
    }
*/
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
