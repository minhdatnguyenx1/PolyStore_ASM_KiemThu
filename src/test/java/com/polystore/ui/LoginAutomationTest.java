package com.polystore.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginAutomationTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Cấu hình mở trình duyệt Chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        
        // Mở to toàn màn hình để dễ nhìn
        driver.manage().window().maximize();
    }

    @Test
    public void testLoginUI() throws InterruptedException {
        // 1. Robot tự động mở trang đăng nhập
        driver.get("http://localhost:8080/auth/login");
        
        // Nghỉ 2 giây để bạn kịp nhìn giao diện load xong
        Thread.sleep(2000);

        // 2. Tìm ô nhập Username và điền "datnm"
        WebElement usernameField = driver.findElement(By.name("username"));
        usernameField.sendKeys("admin");
        Thread.sleep(1000);

        // 3. Tìm ô nhập Password và điền "123456"
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("123456");
        Thread.sleep(1000);

        // 4. Tìm nút "Đăng nhập" và click
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();
        
        // Chờ 2 giây để web chuyển trang
        Thread.sleep(2000);

        // 5. Kiểm tra xem có đúng là đã chuyển về trang chủ (index) hay chưa
        String currentUrl = driver.getCurrentUrl();
        String actualUrl = currentUrl.split(";jsessionid")[0];

        assertEquals("http://localhost:8080/home/index", actualUrl);
    }

    @AfterEach
    public void tearDown() {
        // Test xong thì tự động đóng trình duyệt
        if (driver != null) {
            driver.quit();
        }
    }
}