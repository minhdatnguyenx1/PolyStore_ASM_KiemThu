package com.polystore.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductCrudAutomationTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @Test
    public void testCreateProduct() throws InterruptedException {
        // 1. Robot tự động Đăng nhập bằng tài khoản Admin trước
        driver.get("http://localhost:8080/auth/login");
        driver.findElement(By.name("username")).sendKeys("admin"); // Đổi thành tài khoản admin của bạn nếu cần
        driver.findElement(By.name("password")).sendKeys("123456");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        Thread.sleep(2000); // Đợi load xong đăng nhập

        // 2. Chuyển hướng đến trang Thêm Sản Phẩm (Bạn hãy sửa lại URL này cho đúng với web của bạn nhé)
        driver.get("http://localhost:8080/admin/product/index"); 
        Thread.sleep(2000);

        // 3. Tự động điền form Thêm Sản phẩm 
        // LƯU Ý: Sửa lại By.name("...") cho khớp với thuộc tính name trong file HTML (Thymeleaf) của bạn
        driver.findElement(By.name("name")).sendKeys("Chuột Gaming Logitech G102 (Test Auto)");
        driver.findElement(By.name("price")).sendKeys("450000");
        
        // Nếu có trường số lượng (quantity) hoặc danh mục (category) thì thêm lệnh tương tự ở đây
        // driver.findElement(By.name("quantity")).sendKeys("10");

        // 4. Bấm nút Lưu/Thêm mới
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        Thread.sleep(2000);

        // 5. Kiểm tra kết quả: Xác nhận đã quay lại trang danh sách sản phẩm hoặc hiển thị thông báo thành công
        String currentUrl = driver.getCurrentUrl();
        System.out.println(">>> URL SAU KHI THÊM SẢN PHẨM: " + currentUrl);
        
        // Trạng thái Pass nếu trình duyệt thoát khỏi trang thêm mới
        assertTrue(!currentUrl.contains("/admin/product/create"), "Lỗi: Vẫn kẹt ở trang thêm sản phẩm!");
    }

    @AfterEach
    public void tearDown() {
        // Tạm thời comment dòng này lại nếu bạn muốn xem trình duyệt dừng lại ở màn hình cuối
        // if (driver != null) { driver.quit(); }
    }
}