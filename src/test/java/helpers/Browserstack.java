package helpers;

import config.ConfigReader;

import static io.restassured.RestAssured.given;


public class Browserstack {

    public static String videoUrl(String sessionId) {
        String url = String.format("%s/%s.json", ConfigReader.apiUrl(), sessionId);

        return given()
                .auth().basic(ConfigReader.user(), ConfigReader.key())
                .get(url)
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().path("automation_session.video_url");
    }
}
