package weatherApp;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FAAAirportStatusService implements AirportStatusService {
  HttpURLConnection request;
  JsonElement jsonElement;
  String base = "http://services.faa.gov/airport/status/";
  String token = "?format=application/json";
  String airportCode;
  String errorMessage = "Invalid Airport";

  public AirportStatus getAirportStatus(String airportCode){

    this.airportCode = airportCode;
    try {
      connectToTheUrl();
    }
    catch(Exception ex){
      errorMessage = "Network Problem";
      return new AirportStatus(airportCode, errorMessage);
    }

    try {
      getContentFromURL();
      return getDataFromJsonElement(jsonElement, airportCode);

    }catch (Exception ex){
      return new AirportStatus(airportCode, errorMessage);
    }
  }

  AirportStatus getDataFromJsonElement(JsonElement jsonElement, String airportCode) {
    JsonObject jsonObject = jsonElement.getAsJsonObject();

    JsonObject cityObject = jsonElement.getAsJsonObject();
    String city = cityObject.get("city").getAsString();

    JsonObject stateObject = jsonElement.getAsJsonObject();
    String state = stateObject.get("state").getAsString();

    JsonObject airportNameObject = jsonElement.getAsJsonObject();
    String airportName = airportNameObject.get("name").getAsString();

    JsonObject airportCodeObject = jsonElement.getAsJsonObject();
    airportCode = airportCodeObject.get("IATA").getAsString();

    JsonElement main = jsonObject.get("weather");
    JsonObject temperatureObject = main.getAsJsonObject();
    String temperature = temperatureObject.get("temp").getAsString();

    JsonObject delayedObject = jsonElement.getAsJsonObject();
    boolean delay = delayedObject.get("delay").getAsBoolean();

    return new AirportStatus(city, state, airportName, airportCode, temperature, delay);
  }

  public void connectToTheUrl() throws RuntimeException {
    try {
      URL url = new URL(base + airportCode + token);
      request = (HttpURLConnection) url.openConnection();
      request.connect();

    } catch (Exception ex) {
      errorMessage = "Network Problem";
    }
  }

  public JsonElement getContentFromURL() throws IOException
  {
    try {
      JsonParser jasonParser = new JsonParser();
      jsonElement = jasonParser.parse(new InputStreamReader((InputStream) request.getContent()));
      return jsonElement;
    }catch (Exception ex)
    {
      return null;
    }
  }

}
