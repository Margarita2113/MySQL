package test;

import com.codeborne.selenide.Configuration;
import data.SQLHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import data.DataHelper;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static data.SQLHelper.cleanDatabase;

public class AuthorizationTest {

    //  @AfterAll
   // static void shouldCleanDatabase() {

   //     cleanDatabase();
   // }

    @Test
    void shouldSuccessfulLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPageVisibility();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    void shouldGetErrorNotificationWithRandomUser() {
        var LoginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.generateRandomUser();
        LoginPage.validLogin(authInfo);
        LoginPage.verifyErrorNotificationVisibility();
    }

    @Test
    void shouldGetErrorNotificationWithExistUserAndRandomVerificationCode() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPageVisibility();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
        verificationPage.verifyErrorNotificationVisibility();
    }
    @Test
    void shouldAuthorizationWithInvalidCodeForThreeTimes() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        Configuration.holdBrowserOpen = true;
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        var verifyInfo = DataHelper.getInvalidCode();
        verificationPage.invalidCodeOverLimit(verifyInfo);
    }
}