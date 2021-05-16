package org.example;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class TestLoginApi {

    String applicationId = "7A75552C-D1E1-4125-A95C-4337AC9240D3";
    String restApiKey="/0B7315B9-0778-475C-8CD6-2B8EB6037012";

    String baseUrl = "https://api.backendless.com/";

    String userToken="";
    String userId ="";

    @BeforeTest
    public void beforeMethod(){
        baseURI = baseUrl+applicationId+restApiKey;
    }

    @Test
    public void testLoginApi(){



       Response response =  given().header("Content-Type","application/json").
        body(createRequestBody().toJSONString()).log().all().when().post("/users/login");

       System.out.println("Response : "+response.asPrettyString());

        JsonPath jsonPath = response.jsonPath();
        userToken = jsonPath.getString("user-token");
        userId = jsonPath.getString("ownerId");
        System.out.println("user token : "+userToken);

        Assert.assertTrue(userId!=null);
    }

    @Test
    public void testUpdateUser(){


        given().header("Content-Type","application/json").header("user-token",userToken).body(createRequestBody().toJSONString()).log().all().
                when().put("/users/"+userId).then().log().all().body("updated",notNullValue()).body("email",equalToIgnoringCase("sujith@gmail.com"));
    }

    @Test
    public void testLogOut(){

    }
    public JSONObject createRequestBody(){

        JSONObject requestBody = new JSONObject();
        requestBody.put("login","sujith@gmail.com");
        requestBody.put("password","password");
        requestBody.put("name","Sujith");
        return requestBody;
    }
}
