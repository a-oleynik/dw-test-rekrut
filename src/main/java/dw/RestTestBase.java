package dw;

import dw.model.Session;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

import java.util.Map;

import static dw.framework.config.Constants.*;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.fail;

public class RestTestBase extends TestBase {

    public static Map<String, String> token;
    public static final String XSRF_COOKIE_NAME = "XSRF-TOKEN";
    public static final String XSRF_COOKIE_VALUE = "123-automaty";
    public static final String XSRF_HEADER_NAME = "X-XSRF-TOKEN";
    public static final String XSRF_HEADER_VALUE = XSRF_COOKIE_VALUE;

    @BeforeClass
    public void auth() {
        RestAssured.useRelaxedHTTPSValidation();
        token = authenticate(LOGIN, PASSWORD);
    }

    private Map<String, String> authenticate(String userName, String password) {

        Response post = of()
                .contentType(ContentType.JSON)
                .body(Session.builder().user(userName).password(password).overrideExistingSession(true).build())
                .post("/api/v1/auth/app");

        post.prettyPrint();
        Map<String, String> cookies = post.getCookies();

        if (cookies.size() == 0) {
            fail("Authentication failure for user: " + userName + "\nResponse: " + post.prettyPrint());
        }

        return cookies;
    }

    public RequestSpecification of() {
        return given()
                .log().all()
                .header(XSRF_HEADER_NAME, XSRF_HEADER_VALUE)
                .cookie(XSRF_COOKIE_NAME, XSRF_COOKIE_VALUE)
                .baseUri(BASE_URL)
                .and();
    }
}
