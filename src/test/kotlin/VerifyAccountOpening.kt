import org.testng.Assert
import org.testng.annotations.AfterTest
import org.testng.annotations.BeforeTest
import org.testng.annotations.Test

class VerifyAccountOpening: APIReusables() {

    private var checkingAccountID: Int? = null
    private var savingsAccountID: Int? = null
    private var checkingAccountBalance = 100
    private var savingsAccountBalance = 100
    private val checkingAccountName: String? = null
    private val savingsAccountName: String? = null

    @BeforeTest
    fun preSetup() {
        loginToParabank()
    }

    @Test
    fun createCheckingAccount() {
        createAccount("CHECKING", checkingAccountBalance)
        checkingAccountID = apiUtil.getJsonPath(response!!)?.getJsonObject<Int>("id")
        checkingAccountBalance = apiUtil.getJsonPath(response!!)?.getJsonObject<Int>("balance")!!
        Assert.assertEquals(response?.statusCode, 200)
    }

    @Test
    fun createSavingsAccount() {
        createAccount("SAVINGS", savingsAccountBalance)
        savingsAccountID = apiUtil.getJsonPath(response!!)?.getJsonObject<String>("id").toString().toInt()
        savingsAccountBalance = apiUtil.getJsonPath(response!!)?.getJsonObject<String>("balance").toString().toInt()
        Assert.assertEquals(response?.statusCode, 200)
    }

    @Test
    fun getDetailsSavingsAccount() {
        getAccountDetails(checkingAccountID)
        Assert.assertEquals(response?.statusCode, 200)
    }

    @Test
    fun getDetailsCheckingAccount() {
        getAccountDetails(savingsAccountID)
        Assert.assertEquals(response?.statusCode, 200)
    }

    @Test
    fun billPay() {
        val newCheckingAccountBalance: Int
        val newSavingBalance: Int
        setBillPayBody(savingsAccountID, 100, "Savings Account")
        billPay(checkingAccountID, checkingAccountBalance)
        getAccountDetails(checkingAccountID)
        newCheckingAccountBalance = apiUtil.getJsonPath(response!!)?.getJsonObject<String>("balance").toString().toInt()
        getAccountDetails(savingsAccountID)
        newSavingBalance = apiUtil.getJsonPath(response!!)?.getJsonObject<String>("balance").toString().toInt()
        Assert.assertEquals(response?.statusCode, 200)
        Assert.assertEquals(savingsAccountBalance + 100, newSavingBalance)
        Assert.assertEquals(checkingAccountBalance - 100, newCheckingAccountBalance)
    }

    @AfterTest
    fun cleanResources() {
        resetObjects()
    }
}