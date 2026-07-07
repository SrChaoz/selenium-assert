import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeleniumTest {

    private static WebDriver driver;
    private static final String BASE_URL = "https://the-internet.herokuapp.com";

    @BeforeAll
    public static void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
    }

    // Método utilitario para cumplir con el requisito de capturas de pantalla
    public void tomarCaptura(String nombreEjercicio) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile, new File("./capturas/" + nombreEjercicio + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    @DisplayName("Ejercicio 1: Verificar el título de la página")
    public void testVerificarTitulo() {
        driver.get(BASE_URL + "/");
        String tituloActual = driver.getTitle();

        assertEquals("The Internet", tituloActual);
        tomarCaptura("Ejercicio_1_Titulo"); // 
    }

    @Test
    @Order(2)
    @DisplayName("Ejercicio 2: Verificar un encabezado")
    public void testVerificarEncabezado() {
        driver.get(BASE_URL + "/");
        WebElement encabezado = driver.findElement(By.tagName("h1"));

        assertEquals("Welcome to the-internet", encabezado.getText()); //
        tomarCaptura("Ejercicio_2_Encabezado"); // 
    }

    @Test
    @Order(3)
    @DisplayName("Ejercicio 4: Login incorrecto")
    public void testLoginIncorrecto() {
        driver.get(BASE_URL + "/login");

        driver.findElement(By.id("username")).sendKeys("usuario");
        driver.findElement(By.id("password")).sendKeys("12345");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        WebElement mensajeError = driver.findElement(By.id("flash"));

        assertTrue(mensajeError.getText().contains("Your username is invalid!"));
        tomarCaptura("Ejercicio_4_Login_Incorrecto"); // 
    }

    @Test
    @Order(4)
    @DisplayName("Ejercicio 3, 5, 6, 7 y 8: Flujo de Login Exitoso y Logout")
    public void testFlujoLoginLogout() {
        // --- EJERCICIO 3: Login Exitoso ---
        driver.get(BASE_URL + "/login");
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        WebElement mensajeExito = driver.findElement(By.id("flash"));
        assertTrue(mensajeExito.getText().contains("You logged into a secure area!"));
        tomarCaptura("Ejercicio_3_Login_Exitoso"); // 

        // --- EJERCICIO 5: Verificar URL ---
        String urlActual = driver.getCurrentUrl();
        assertTrue(urlActual.contains("secure"));
        tomarCaptura("Ejercicio_5_Verificar_URL"); // 

        // --- EJERCICIO 6: Verificar botón Logout visible ---
        WebElement botonLogout = driver.findElement(By.cssSelector("a.button.secondary.radius"));
        assertTrue(botonLogout.isDisplayed());
        tomarCaptura("Ejercicio_6_Boton_Visible"); // 

        // --- EJERCICIO 7: Verificar el texto del botón ---
        assertTrue(botonLogout.getText().contains("Logout"));
        tomarCaptura("Ejercicio_7_Texto_Boton"); // 

        // --- EJERCICIO 8: Logout ---
        botonLogout.click();
        WebElement formularioLogin = driver.findElement(By.id("login"));

        assertNotNull(formularioLogin);
        tomarCaptura("Ejercicio_8_Logout_Exitoso"); // 
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}