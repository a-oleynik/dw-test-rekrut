package dw.test.rest;

import dw.RestTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Test
public class TabularTest extends RestTestBase {

    String collectionName = "Crimes";
    String integerColumnName = "ID"; // column ID = 9106

    public void checkMaxValueForIntegerColumn() {

        Response response = of().cookies(token)
                .contentType(ContentType.JSON)
                .when()
                .body("") // set body
                .put("") // set endpoint
                .then()
                .extract().response();

        response.prettyPrint();

        String actualValue = "0";  // get MAX value for given response for column, response.jsonPath()....

        assertThat(actualValue)
                .describedAs("Aggregate max value for: ABC is different than displayed in tabular.")
                .isEqualTo("22");
    }
}