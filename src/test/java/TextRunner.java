import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TextRunner {
    public static final String PROJECT_DIR = "/home/prituladima/IdeaProjects/web_driver/";
    public static final String LIBS = PROJECT_DIR + "jars/";

    public static final String CHROME_DRIVER_PATH = LIBS + "chromedriver";
    public static final String MOZILA_DRIVER_PATH = LIBS + "geckodriver";

    public static final String CHROME_DRIVER_VAR = "webdriver.chrome.driver";
    public static final String MOZILA_DRIVER_VAR = "webdriver.gecko.driver";

    public static final Dimension[] POSSIBLE_DIMENSIONS_4x3 = {
            new Dimension(640, 480),
            new Dimension(800, 600),
            new Dimension(960, 720),
            new Dimension(1024, 768),
            new Dimension(1280, 960),
            new Dimension(1400, 1050),
            new Dimension(1440, 1080),
            new Dimension(1600, 1200),
            new Dimension(1856, 1392),
            new Dimension(1920, 1440),
            new Dimension(2048, 1536)
    };

    public static final Date TIME_OF_TEST = new Date();

    /**
     * 16:10 aspect ratio resolutions: - 1280×800, 1440×900, 1680×1050, 1920×1200 and 2560×1600.
     * 16:9 aspect ratio resolutions: 1024×576, 1152×648, 1280×720, 1366×768, 1600×900, 1920×1080, 2560×1440 and 3840×2160.
     */

    public static void main(String[] args) throws InterruptedException, IOException{
        new TextRunner().runTestOnChrome();
        new TextRunner().runTestOnFirefox();
    }


    @Test
    public void runTestOnChrome() throws IOException {

        System.setProperty(CHROME_DRIVER_VAR, CHROME_DRIVER_PATH);
        for (Dimension d : POSSIBLE_DIMENSIONS_4x3)
            runOn(new ChromeDriver(), d);

    }

    @Test
    public void runTestOnFirefox() throws IOException {

        System.setProperty(MOZILA_DRIVER_VAR, MOZILA_DRIVER_PATH);
        for (Dimension d : POSSIBLE_DIMENSIONS_4x3)
            runOn(new FirefoxDriver(), d);

    }

    private void runOn(WebDriver driver, Dimension dimension) throws IOException {
        driver.manage().window().setSize(dimension);
        driver.get("http://www.wikipedia.org");

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        makeScreenShoot(driver, "1_localization");

        WebElement link;
        link = driver.findElement(By.id("js-link-box-ru"));
        link.click();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        makeScreenShoot(driver, "2_main");
        WebElement searchBox;

        searchBox = driver.findElement(By.id("searchInput"));
        searchBox.sendKeys("Software");

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        makeScreenShoot(driver, "3_article");
        driver.quit();

    }


    private void makeScreenShoot(WebDriver driver, String screen) throws IOException {

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        String dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        String stimeOfTest = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(TIME_OF_TEST);

        FileUtils.copyFile(scrFile,
                new File("/home/prituladima/IdeaProjects/web_driver/screenshoots/test_" + stimeOfTest + "/"+ screen + "/" + driver.getClass().getSimpleName()+ "/" + "screen_" + dateFormat));


    }

}