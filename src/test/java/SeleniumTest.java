import org.example.Main;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void testSumar() {
        Main app = new Main();
        assertEquals(5, app.sumar(2, 3), "La suma debería ser 5");
    }

    @Test
    public void testEsPar() {
        Main app = new Main();
        assertTrue(app.esPar(4), "El número 4 debería ser par");
        assertFalse(app.esPar(7), "El número 7 no debería ser par");
    }
}