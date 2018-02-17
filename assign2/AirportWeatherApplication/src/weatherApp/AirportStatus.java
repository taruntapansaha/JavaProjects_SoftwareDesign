package weatherApp;

public class AirportStatus {

  private final String city;
  private final String state;
  private final String airportCode;
  private final String airportName;
  private final boolean delayed;
  private final String temperature;
  private final String error;


  AirportStatus(String city, String state, String airportCode, String airportName, String temperature, boolean delayed ){
    this.city = city;
    this.state = state;
    this.airportName = airportName;
    this.airportCode = airportCode;
    this.temperature = temperature;
    this.delayed = delayed;
    this.error = "";

  }

  public AirportStatus(String airportCode, String ex) {
    this.city = "---";
    this.state = "---";
    this.airportName = "---";
    this.airportCode = airportCode;
    this.temperature = "---";
    this.delayed = false;
    this.error = ex;
  }

  public String getCity(){
    return this.city;
  }

  public String getState(){
    return this.state;
  }

  public String getAirportCode() {
    return airportCode;
  }

  public String getAirportName() {
    return airportName;
  }

  public String getTemperature() {
    return temperature;
  }

  public String getError() {
    return this.error;
  }

  public boolean isDelayed() {
    return delayed;
  }
}