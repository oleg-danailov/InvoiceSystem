package com.estafet.taxservice.route;

import com.estafet.invoicesystem.jpa.model.Tax;
import com.estafet.invoicesystem.jpa.model.TaxResponse;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.module.jsv.JsonSchemaValidator;
import com.jayway.restassured.parsing.Parser;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static com.jayway.restassured.RestAssured.get;
import static org.junit.Assert.*;

import static com.jayway.restassured.RestAssured.given;

public class TaxServiceRestRouteIntegTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost:7654";
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    public void testRestRoute() {
        String invoiceType = "testInType";
        String requestBody = "{\"taxName\":\"test\", \"invoiceType\":\""+invoiceType+"\", \"taxPercent\":0.23}";


        //test create tax
        given().contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/taxes")
                .then()
                .assertThat()
                .statusCode(200)
                .assertThat()
                .body("code", CoreMatchers.containsString("OK"));


        //test get tax
        TaxResponse tax = given().contentType(ContentType.JSON)
                .param("invoiceType", invoiceType)
                .when().get("/taxes").as(TaxResponse.class);

        assertTrue(tax != null);
        Integer taxId = tax.getTaxId();
        assertTrue(taxId != null);
        assertEquals(invoiceType, tax.getInvoiceType());
        assertEquals(new BigDecimal("0.23"), tax.getTaxPercent());
        assertEquals("test", tax.getTaxName());


        //test delete tax and clean
        given().param("taxId", taxId).when().delete("/taxes").then()
                .assertThat().statusCode(200)
                .assertThat().body("code", CoreMatchers.containsString("OK"));
    }


}