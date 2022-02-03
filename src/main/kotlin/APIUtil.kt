import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.path.json.JsonPath
import io.restassured.response.Response

class APIUtil {
    fun getResponse(path: String?): Response? {
        return RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON).`when`()[path]
    }

    fun postResponse(path: String?, reqBody: String?): Response? {
        return RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON).body(reqBody).`when`()
            .post(path)
    }

    fun postResponse(path: String?): Response? {
        return RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON).`when`().post(path)
    }

    fun getJsonPath(response: Response): JsonPath? {
        return response.jsonPath()
    }
}