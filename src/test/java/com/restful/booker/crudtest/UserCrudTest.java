package com.restful.booker.crudtest;

import com.restful.booker.model.AuthPojo;
import com.restful.booker.model.BookingDates;
import com.restful.booker.model.PatchPojo;
import com.restful.booker.model.RestPojo;
import com.restful.booker.testbase.TestBase;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class UserCrudTest extends TestBase {


    static String token = "af6e49ccf4d134";

    @Test()
    public void verifyUserBookingSuccessfully() {
        RestPojo restPojo = new RestPojo();
        restPojo.setFirstname("shweta");
        restPojo.setLastname("shah");
        restPojo.setTotalprice("2000");
        restPojo.setDepositpaid("yes");
        BookingDates bd = new BookingDates();
        bd.setCheckin("12-12-2023");
        bd.setCheckout("12-12-2023");
        restPojo.setBookingdates(bd);
        restPojo.setAdditionalneeds("B & B");
        Response response = given()
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + token)
                .when()
                .body(restPojo)
                .post();
//To fetch response from server
        String responseBody = response.getBody().asString();
        response.then().log().all().statusCode(200);
        JsonPath jsonPath = new JsonPath(responseBody);
        String bookingId = jsonPath.getString("bookingid");
        System.out.println("bookingid " + bookingId);
    }

    @Test()
    public void verifyUserAuthSuccessfully() {
        AuthPojo authPojo = new AuthPojo();
        authPojo.setUsername("admin");
        authPojo.setPassword("password123");

        Response response = given()
                .basePath("/auth")
                .header("Content-Type", "application/json")
                .when()
                .body(authPojo)
                .post();
        String responseBody = response.getBody().asString();
        response.then().log().all().statusCode(200);
    }

    @Test
    public void getAllBookingId() {
        Response response = given()
                .basePath("/booking")
                .header("Content-Type", "application/json")
                .when()
                .get();
        response.then().statusCode(200);
        response.prettyPrint();
    }

    @Test
    public void verifyUserUpdateBookingSuccessfully() {
        RestPojo restPojo = new RestPojo();
        restPojo.setFirstname("shweta");
        restPojo.setLastname("shah");
        restPojo.setTotalprice("2000");
        restPojo.setDepositpaid("Yes");
        BookingDates bd = new BookingDates();
        bd.setCheckin("12-12-2023");
        bd.setCheckout("12-12-2023");
        restPojo.setBookingdates(bd);
        restPojo.setAdditionalneeds("B & B");
        Response response = given()
                .basePath(RestAssured.basePath + "/2368")
                .headers("Content-Type", "application/json", "Authorization", "Basic " + "YWRtaW46cGFzc3dvcmQxMjM=")
                .when()
                .body(restPojo)
                .put();

        response.prettyPrint();
        response.then().statusCode(200);

        response.then().log().all().statusCode(200);
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);
    //    int userId = jsonPath.getInt("id");
    //    System.out.println("user id " + userId);
    }

    @Test
    public void verifyUserUpdateSingleBookingSuccessfully() {
        PatchPojo restPojo = new PatchPojo();
        restPojo.setFirstname("shweta");
        restPojo.setLastname("shah");
          Response response = given()
                .basePath(RestAssured.basePath + "/1")
                .headers("Content-Type", "application/json", "Authorization", "Basic " + "YWRtaW46cGFzc3dvcmQxMjM=")
                .when()
                .body(restPojo)
                .patch();

        response.prettyPrint();
        response.then().statusCode(200);

        response.then().log().all().statusCode(200);
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);
    }

    @Test
    public void VerifyUserDeleteBookingSuccessfully() {
        //   PostsPojo postsPojo = new PostsPojo();
        Response response = given()
                .headers("Content-Type", "application/json", "Authorization", "Basic " + "YWRtaW46cGFzc3dvcmQxMjM=")
                .basePath(RestAssured.basePath + "/1")
                .when()
                .body("")
                .delete();

        response.prettyPrint();
        response.then().statusCode(201);


    }

}