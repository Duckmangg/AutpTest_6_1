package ru.netology.test;

import lombok.val;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.hamcrest.Matchers.containsString;
import static ru.netology.data.DataHelper.getFirstCardInfo;
import static ru.netology.data.DataHelper.getSecondCardInfo;

class MoneyTransferTest {

    @Test
    void shouldLogin() {

        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val firstBalance = dashboardPage.getCardBalance(getFirstCardInfo());
        val secondBalance = dashboardPage.getCardBalance(getSecondCardInfo());
        val transferPage = dashboardPage.replenishButtonClick(getSecondCardInfo());
        int amount = 4000;
        transferPage.moneyTransfer(amount, getFirstCardInfo().getCardNumber());
        val dashboardPageAfterTransfer = new DashboardPage();
        dashboardPageAfterTransfer.getCardBalance(getFirstCardInfo());
        assertThat(dashboardPageAfterTransfer, containsString(String.valueOf(getFirstCardInfo())));
        assertThat(dashboardPageAfterTransfer, containsString(String.valueOf(getSecondCardInfo())));

    }

    private void assertThat(DashboardPage dashboardPageAfterTransfer, Matcher<String> containsString) {
    }
}
