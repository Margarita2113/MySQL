package page;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement codeField = $("[data-test-id=code] input");
    private final SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private final SelenideElement errorNotification = $("['data-test-id=error-notification']");
    private final SelenideElement errorMessageOverLimitedCode = $("[data-test-id=error-notification] .notification__content");


    public void verificationPageVisibility() {
        codeField.shouldBe(visible);
    }

    public void verifyErrorNotificationVisibility() {
        errorNotification.shouldBe(visible).shouldHave(text("Ошибка! Неверно указан код! Попробуйте ещё раз."));
    }

    public void errorMessageTypeTwo() {
        errorMessageOverLimitedCode.shouldBe(visible).shouldHave(text("Ошибка! Превышено количество попыток ввода кода!"));
    }


    public DashboardPage validVerify(String verificationCode) {
        verify(verificationCode);
        verifyButton.click();
        return new DashboardPage();
    }

    public void verify(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
    }

    public void invalidCodeOverLimit(DataHelper.VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        codeField.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        codeField.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        errorMessageTypeTwo();
    }
}