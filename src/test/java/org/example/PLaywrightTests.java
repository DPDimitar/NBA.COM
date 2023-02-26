package org.example;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlaywrightTests {

    static Playwright playwright;
    static Browser browser;

    // New instance for each test method.
    BrowserContext context;
    Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Test
    void shouldHaveSearchField() {
        page.navigate("https://www.nba.com/search");
        assertTrue(page.locator("input#search-text-input").isVisible());
    }

    @Test
    void shouldHaveSearchButton() {
        page.navigate("https://www.nba.com/search");
        assertTrue(page.locator("button.Button_button__L2wUb.SearchBar_sbButton__eyDP6").isVisible());
    }

    @Test
    void shouldFillSearchField() {
        page.navigate("https://www.nba.com/search");
        page.fill("input#search-text-input", "lebron");
        assertEquals(page.locator("input#search-text-input").inputValue(), "lebron");
    }

    @Test
    void shouldSearchNBAPlayer() {
        page.navigate("https://www.nba.com/search");
        page.locator("input#search-text-input").fill("lebron");
        page.locator("button.Button_button__L2wUb.SearchBar_sbButton__eyDP6").click();
        assertEquals("https://www.nba.com/search?filters=&q=lebron&sortBy=rel", page.url());
    }

}