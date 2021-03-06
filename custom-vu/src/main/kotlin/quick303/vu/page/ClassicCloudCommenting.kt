package quick303.vu.page

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable
import org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated
import quick303.vu.wait

class ClassicCloudCommenting(
    private val driver: WebDriver
) : Commenting {

    override fun openEditor() {
        driver
            .wait(elementToBeClickable(By.id("footer-comment-button")))
            .click()
        waitForEditor()
    }

    private fun waitForEditor() {
        driver
            .wait(elementToBeClickable(By.id("comment")))
            .click()
    }

    override fun typeIn(comment: String) {
        Actions(driver)
            .sendKeys(comment)
            .perform()
    }

    override fun saveComment() {
        driver.findElement(By.id("issue-comment-add-submit")).click()
    }

    override fun waitForTheNewComment() {
        driver.wait(visibilityOfElementLocated(By.cssSelector(".activity-comment.focused")))
    }
}
