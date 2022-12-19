package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;
import static com.codeborne.selenide.Selenide.open;
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
        int amount = 5000;
        transferPage.moneyTransfer(amount, DataHelper.getFirstCardInfo().getCardNumber());
        Assertions.assertEquals(firstBalance - amount, dashboardPage.getCardBalance(getFirstCardInfo()));
        Assertions.assertEquals(secondBalance + amount, dashboardPage.getCardBalance(getSecondCardInfo()));

    }
}

