package weatherApp;


import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FAAAirportStatusServiceTest {
  AirportStatus iahHoustonTX, iadWashingtonDC, sfoSanFransiscoCA, houHoustonTX, invalidAirport, networkError;
  boolean getDataFromJsonElementCalled;
  boolean getContentFromURLCalled;
  boolean connectToTheUrlCalled;
  AirportStatusReportData AirportStatusReportWithAnEmptyList;
  AirportStatusReportData AirportStatusReportWithValidListOfCities;
  AirportStatusReportData AirportStatusReportWithAnInvalidCity;
  AirportStatusReportData AirportStatusReportWithNetworkError;


  @Before
  public void setUp(){

    iahHoustonTX  = new AirportStatus("Houston", "Texas", "IAH", "George Bush", "75", true);
    iadWashingtonDC  = new AirportStatus("Dulles", "Virginia", "IAD", "Washington Dulles",  "66", false);
    sfoSanFransiscoCA  = new AirportStatus("San Francisco", "California", "SFO", "San Francisco", "60", true);
    houHoustonTX = new AirportStatus("Houston", "Texas", "HOU", "William P. Hobby Airport", "75", false);
    invalidAirport = new AirportStatus("AAA", "Invalid Airport");
    networkError = new AirportStatus("IAH", "Network Problem");
    AirportStatusReportWithAnEmptyList = new AirportStatusReportData(Arrays.asList(), 0);
    AirportStatusReportWithValidListOfCities = new AirportStatusReportData(Arrays.asList(iadWashingtonDC, iahHoustonTX, sfoSanFransiscoCA), 2);
    AirportStatusReportWithAnInvalidCity = new AirportStatusReportData(Arrays.asList(iadWashingtonDC, iahHoustonTX, invalidAirport), 1);
    AirportStatusReportWithNetworkError = new AirportStatusReportData(Arrays.asList(iadWashingtonDC, iahHoustonTX, networkError), 1);

  }

  @Test
  public void connectToFAAWhenThereIsNetworkError () throws Exception{
    FAAAirportStatusService spy = Mockito.spy(new FAAAirportStatusService());

    Mockito.doThrow(new RuntimeException("Network Error")).when(spy).connectToTheUrl();

    assertEquals(networkError.getError(), spy.getAirportStatus("IAH").getError());

  }

  @Test
  public void connectToFAAWhenThereIsInvalidAirport () throws Exception{
    FAAAirportStatusService spy = Mockito.spy(new FAAAirportStatusService());

    Mockito.doNothing().when(spy).connectToTheUrl();
    Mockito.doThrow(new RuntimeException("Invalid Airport")).when(spy).getContentFromURL();

    assertEquals(invalidAirport.getError(), spy.getAirportStatus("AAA").getError());

  }

  @Test
  public void checkIfgetDataFromJsonElementReturnObjectWithCorrectValues () throws IOException {
    InputStream inputStream = new FileInputStream("IAH_JSON.txt");
    JsonParser jasonParser = new JsonParser();
    JsonElement jsonElement = jasonParser.parse(new InputStreamReader(inputStream));

    FAAAirportStatusService faaAirportStatusStatusService = new FAAAirportStatusService();

    AirportStatus airportStatus = faaAirportStatusStatusService.getDataFromJsonElement(jsonElement, "IAH");

    assertEquals(iahHoustonTX.getCity(), airportStatus.getCity());

  }

  class FAAAirportStatusStubbed extends FAAAirportStatusService{
    @Override
    public AirportStatus getDataFromJsonElement(JsonElement jsonElement, String airportCode){
      getDataFromJsonElementCalled = true;
      return null;
    }

    @Override
    public JsonElement getContentFromURL(){
      getContentFromURLCalled = true;

      return null;
    }

    @Override
    public void connectToTheUrl(){
      connectToTheUrlCalled = true;
    }
  }

  @Test
  public void getAirportStatusCallsGetDataFromJsonElement (){

    getContentFromURLCalled = false;
    getDataFromJsonElementCalled = false;
    connectToTheUrlCalled = false;

    FAAAirportStatusServiceTest.FAAAirportStatusStubbed faaAirportStatusStubbed = new  FAAAirportStatusServiceTest.FAAAirportStatusStubbed();
    faaAirportStatusStubbed.getAirportStatus("IAH");
    assertTrue(getDataFromJsonElementCalled);
    assertTrue(getContentFromURLCalled);
    assertTrue(connectToTheUrlCalled);
  }
}
