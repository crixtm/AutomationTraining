package test.e2e.pages;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.PriceConverter;

import java.util.List;

public class DishesSelection extends BasePage {

    @FindBy(xpath = ".//strong")
    private List<WebElement> dishes;

    @FindBy(xpath = ".//button")
    private List<WebElement> addToCartButtons;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(id = "empty-cart")
    private WebElement emptyCartButton;

    @FindBy(className = "total-price")
    private WebElement totalPrice;

    @FindBy(className = "selected-image-price")
    private List<WebElement> dishesInCart;

    @FindBy(id = "shop-info")
    private WebElement cart;

    @FindBy(xpath = ".//*[contains(@data-bind,'itemPrice')]")
    private List<WebElement> pricesInCart;

    @FindBy(xpath = ".//*[contains(@data-bind,'quantity')]")
    private List<WebElement> quantitiesInCart;

    public DishesSelection(WebDriver browser) {
        super(browser);
        org.openqa.selenium.support.PageFactory.initElements(browser, this);
    }

    public void selectDish(String dishName) {
        dishes.stream().filter(dish -> dish.getText().equals(dishName)).forEach(WebElement::click);
    }

    public void addDishToCart(String dishName) {
        dishes.stream()
                .filter(dish -> dish.getText().equalsIgnoreCase(dishName))
                .forEach(dish -> dish.findElement(By.xpath("../..")).findElement(By.className("add-to-cart")).click());
    }

    public void checkout() {
        checkoutButton.click();
    }

    public void emptyCart() {
        emptyCartButton.click();
    }

    public double getTotalPrice() {
        return PriceConverter.getTotalPriceAsDouble(totalPrice);
    }

    public boolean isTotalPricePresent() {
        return totalPrice.isDisplayed();
    }

    public List<WebElement> getDishesInCart() {
        return dishesInCart;
    }

    public boolean isCartPresent() {
        return cart.isDisplayed();
    }

    public void addRandomDish() {
        try {
            addToCartButtons.get(0).click();
        } catch (NotFoundException elementIsNotFound) {
            System.out.println("There are no dishes on a page: " + elementIsNotFound);
        }
    }

    public double calculateTotalPrice() {
        double totalPrice = 0;
        for (int i = 0; i < dishesInCart.size(); i++) {
            totalPrice += PriceConverter.getTotalPriceAsDouble(pricesInCart.get(i))
                    * PriceConverter.getTotalPriceAsDouble(quantitiesInCart.get(i));
        }
        return totalPrice;
    }
}