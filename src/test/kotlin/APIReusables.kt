import io.restassured.response.Response
import org.json.simple.JSONObject

open class APIReusables {
    protected var response: Response? = null
    protected var apiUtil: APIUtil = APIUtil()
    protected var jsonObject: JSONObject? = null
    protected var requestUrl: String? = null
    protected var customerID: Int? = null

    private val fromAccountNumber = 17784
    private val url = "https://parabank.parasoft.com/parabank/services/bank"

    fun loginToParabank() {
        requestUrl = "$url/login/john/demo"
        response = apiUtil.getResponse(requestUrl)
        customerID = apiUtil.getJsonPath(response!!)?.getJsonObject<Int>("id")
    }


    fun createAccount(accountType: String, accountBalance: Int) {
        requestUrl =
            "$url/createAccount?customerId=$customerID&newAccountType=$accountType&fromAccountId=$fromAccountNumber"
        response = apiUtil.postResponse(requestUrl)
    }

    fun getAccountDetails(accountID: Int?) {
        requestUrl = "$url/accounts/$accountID"
        response = apiUtil.getResponse(requestUrl)
    }

    fun resetObjects() {
        jsonObject = null
        response = null
        apiUtil
        requestUrl = null
        customerID = null
    }

    fun setBillPayBody(receiverAccountID: Int?, amount: Int, payeeName: String?) {
        jsonObject = JSONObject()
        jsonObject!!["payeeName"] = payeeName
        jsonObject!!["amount"] = amount
        jsonObject!!["accountId"] = receiverAccountID
    }

    fun billPay(senderAccountID: Int?, accountBalance: Int) {
        requestUrl = "$url/billpay?accountId=$senderAccountID&amount=$accountBalance"
        response = apiUtil.postResponse(requestUrl, jsonObject.toString())
    }
}