package dw.test.rest;

import dw.RestTestBase;
import dw.enums.AggregateOption;
import dw.model.aggregate.*;
import dw.model.aggregate.response.AggregateResponse;
import dw.model.aggregate.response.AggregateValue;
import dw.model.tables.CrimesColumns;
import dw.model.tables.Tables;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TabularTest extends RestTestBase {

    int idOfCrimeColumnID = CrimesColumns.ID.getId(); // column ID = 9106
    int idOfCrimesTable = Tables.CRIMES.getId(); // Table Crimes id 8100

    @Test
    public void checkMaxValueForIntegerColumn() {
        Map<String, List<AggregateEntry>> aggregateMap = Map.of(
                String.valueOf(idOfCrimeColumnID),
                List.of(
                        AggregateEntry.builder()
                                .selected(true)
                                .sourceId(idOfCrimeColumnID)
                                .type(AggregateOption.MAX.name())
                                .build()
                )
        );
        Map<String, List<BaseEntry>> contextBaseEntryMap = Map.of(
                String.valueOf(idOfCrimesTable),
                List.of(
                        BaseEntry.builder()
                                .type("class")
                                .data(
                                        BaseData.builder()
                                                .entityClassIds(List.of(idOfCrimesTable))
                                                .build()
                                )
                                .build()
                )
        );

        AggregateRequest aggregateRequest = AggregateRequest.builder()
                .aggregateParameters(
                        AggregateParameters.builder()
                                .attributeIdToAggregateMap(aggregateMap)
                                .contextScope(
                                        ContextScope.builder()
                                                .context(
                                                        InnerContext.builder()
                                                                .context(
                                                                        InnerContextLevel.builder()
                                                                                .base(contextBaseEntryMap)                                                                                .build()
                                                                )
                                                                .readOnly(false)
                                                                .token(token.values().stream().findFirst().get())
                                                                .build()
                                                )
                                                .key(String.valueOf(idOfCrimesTable))
                                                .build()
                                )
                                .build()
                )
                .build();


        Response response = of().cookies(token)
                .contentType(ContentType.JSON)
                .when()
                .body(aggregateRequest) // set body
                .put("/api/v1/entity/aggregate") // set endpoint
                .then()
                .statusCode(200)
                .extract().response();

        response.prettyPrint();
        AggregateResponse aggregateResponse = response.body().as(AggregateResponse.class);
        AggregateValue aggregateValue = aggregateResponse.getFirstAggregateResult().getFirstAggregateValue();

        int actualValue = (int)aggregateValue.getValue();// get MAX value for given response for column, response.jsonPath()....

        assertThat(actualValue)
                .describedAs("Aggregate max value for: ABC is different than displayed in tabular.")
                .isEqualTo(69);
    }
}
