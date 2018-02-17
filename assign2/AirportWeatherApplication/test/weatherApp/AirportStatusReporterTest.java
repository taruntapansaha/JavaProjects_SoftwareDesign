//package weatherApp;
//
//import com.google.gson.JsonElement;
//import com.google.gson.JsonParser;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mockito;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Mockito.when;
//
//public class AirportStatusReporterTest {
//  AirportStatus iahHoustonTX, iadWashingtonDC, sfoSanFransiscoCA, houHoustonTX, invalidAirport, networkError;
//  AirportStatusReporter airportStatusReporter;
//  AirportStatusService airportStatusServiceMock;
//  boolean getDataFromJsonElementCalled;
//  boolean getContentFromURLCalled;
//  boolean connectToTheUrlCalled;
//  AirportStatusReportData AirportStatusReportWithAnEmptyList;
//  AirportStatusReportData AirportStatusReportWithValidListOfCities;
//  AirportStatusReportData AirportStatusReportWithAnInvalidCity;
//  AirportStatusReportData AirportStatusReportWithNetworkError;
//
//  @Test
//  public void canary (){
//    assertTrue(true);
//  }
//
//  @Before
//  public void setUp(){
//
//    iahHoustonTX  = new AirportStatus("Houston", "Texas", "IAH", "George Bush", "75", true);
//    iadWashingtonDC  = new AirportStatus("Dulles", "Virginia", "IAD", "Washington Dulles",  "66", false);
//    sfoSanFransiscoCA  = new AirportStatus("San Francisco", "California", "SFO", "San Francisco", "60", true);
//    houHoustonTX = new AirportStatus("Houston", "Texas", "HOU", "William P. Hobby Airport", "75", false);
//    invalidAirport = new AirportStatus("AAA", "Invalid Airport");
//    networkError = new AirportStatus("IAH", "Network Problem");
//    AirportStatusReportWithAnEmptyList = new AirportStatusReportData(Arrays.asList(), 0);
//    AirportStatusReportWithValidListOfCities = new AirportStatusReportData(Arrays.asList(iadWashingtonDC, iahHoustonTX, sfoSanFransiscoCA), 2);
//    AirportStatusReportWithAnInvalidCity = new AirportStatusReportData(Arrays.asList(iadWashingtonDC, iahHoustonTX, invalidAirport), 1);
//    AirportStatusReportWithNetworkError = new AirportStatusReportData(Arrays.asList(iadWashingtonDC, iahHoustonTX, networkError), 1);
//
//    airportStatusReporter = new AirportStatusReporter();
//    airportStatusServiceMock = Mockito.mock(AirportStatusService.class);
//    airportStatusReporter.setTheService(airportStatusServiceMock);
//  }
//
//  @Test
//  public void sortAirportStatusesForEmptyList (){
//    assertEquals(Arrays.asList(), airportStatusReporter.sortAirportStatuses(Arrays.asList()) );
//  }
//
//  @Test
//  public void sortAirportStatusesForOneAirport (){
//    List<AirportStatus> airportStatuses = Arrays.asList(iadWashingtonDC);
//
//    List<AirportStatus> expectedList = Arrays.asList(iadWashingtonDC);
//
//    assertEquals(expectedList, airportStatusReporter.sortAirportStatuses(airportStatuses) );
//  }
//
//  @Test
//  public void sortAirportStatusesForTwoAirportsInSortedOrder (){
//    List<AirportStatus> airportStatuses = Arrays.asList(iadWashingtonDC, iahHoustonTX);
//
//    List<AirportStatus> expectedList = Arrays.asList(iadWashingtonDC, iahHoustonTX);
//
//    assertEquals(expectedList, airportStatusReporter.sortAirportStatuses(airportStatuses) );
//  }
//
//  @Test
//  public void sortAirportStatusesForTwoAirportsUnsortedOrder (){
//    List<AirportStatus> airportStatuses = Arrays.asList(iahHoustonTX, iadWashingtonDC);
//
//    List<AirportStatus> expectedList = Arrays.asList(iadWashingtonDC, iahHoustonTX);
//
//    assertEquals(expectedList, airportStatusReporter.sortAirportStatuses(airportStatuses) );
//  }
//
//  @Test
//  public void sortAirportStatusesForThreeCities (){
//    List<AirportStatus> airportStatuses = Arrays.asList(iahHoustonTX, iadWashingtonDC, sfoSanFransiscoCA );
//
//    List<AirportStatus> expectedList = Arrays.asList(iadWashingtonDC, iahHoustonTX, sfoSanFransiscoCA);
//
//    assertEquals(expectedList,  airportStatusReporter.sortAirportStatuses(airportStatuses));
//  }
//
//  @Test
//  public void sortAirportStatusesForSameCityNames (){
//    List<AirportStatus> airportStatuses = Arrays.asList(iahHoustonTX, houHoustonTX);
//
//    List<AirportStatus> expectedList = Arrays.asList(iahHoustonTX, houHoustonTX);
//
//    assertEquals(expectedList, airportStatusReporter.sortAirportStatuses(airportStatuses) );
//  }
//
//  @Test
//  public void getAirportStatusWhenListIsEmpty(){
//    when(airportStatusServiceMock.getAirportStatus("")).thenReturn(null);
//
//    assertEquals(Arrays.asList(), airportStatusReporter.addAirports(Arrays.asList()));
//  }
//
//  @Test
//  public void getAirportStatusWhenListHasOne(){
//    List<AirportStatus> expectedList = Arrays.asList(houHoustonTX);
//    List<String> airportCodes = Arrays.asList("HOU");
//
//    when(airportStatusServiceMock.getAirportStatus("HOU")).thenReturn(houHoustonTX);
//
//    assertEquals(expectedList, airportStatusReporter.addAirports(airportCodes));
//  }
//
//  @Test
//  public void getAirportStatusWhenListHasMultiple(){
//    List<AirportStatus> expectedList = Arrays.asList(iadWashingtonDC, houHoustonTX, sfoSanFransiscoCA);
//    List<String> airportCodes = Arrays.asList("IAD", "HOU", "SFO");
//
//    when(airportStatusServiceMock.getAirportStatus("HOU")).thenReturn(houHoustonTX);
//    when(airportStatusServiceMock.getAirportStatus("IAD")).thenReturn(iadWashingtonDC);
//    when(airportStatusServiceMock.getAirportStatus("SFO")).thenReturn(sfoSanFransiscoCA);
//
//
//    assertEquals(expectedList, airportStatusReporter.addAirports(airportCodes));
//  }
//
//  @Test
//  public void getAirportStatusWhenListHasInvalidCode(){
//
//    List<String> airportCodes = Arrays.asList("IAD", "HOU", "AAA", "SFO");
//
//    when(airportStatusServiceMock.getAirportStatus("HOU")).thenReturn(houHoustonTX);
//    when(airportStatusServiceMock.getAirportStatus("IAD")).thenReturn(iadWashingtonDC);
//    when(airportStatusServiceMock.getAirportStatus("AAA")).thenReturn(invalidAirport);
//    when(airportStatusServiceMock.getAirportStatus("SFO")).thenReturn(sfoSanFransiscoCA);
//
//    assertEquals("", airportStatusReporter.addAirports(airportCodes).get(0).getError());
//    assertEquals("", airportStatusReporter.addAirports(airportCodes).get(1).getError());
//    assertEquals("Invalid Airport", airportStatusReporter.addAirports(airportCodes).get(2).getError());
//    assertEquals("", airportStatusReporter.addAirports(airportCodes).get(3).getError());
//  }
//
//  @Test
//  public void getAirportStatusWhenThereIsNetworkError(){
//    List<String> airportCodes = Arrays.asList("IAD", "HOU", "AAA", "SFO");
//
//    when(airportStatusServiceMock.getAirportStatus("HOU")).thenReturn(houHoustonTX);
//    when(airportStatusServiceMock.getAirportStatus("IAD")).thenReturn(iadWashingtonDC);
//    when(airportStatusServiceMock.getAirportStatus("AAA")).thenReturn(networkError);
//    when(airportStatusServiceMock.getAirportStatus("SFO")).thenReturn(sfoSanFransiscoCA);
//
//    assertEquals("", airportStatusReporter.addAirports(airportCodes).get(0).getError());
//    assertEquals("", airportStatusReporter.addAirports(airportCodes).get(1).getError());
//    assertEquals("Network Problem", airportStatusReporter.addAirports(airportCodes).get(2).getError());
//    assertEquals("", airportStatusReporter.addAirports(airportCodes).get(3).getError());
//  }
//
//  @Test
//  public void countDelayedAirportForZeroDelayedAirport(){
//    List<AirportStatus> airportStatuses = Arrays.asList(iadWashingtonDC);
//    assertEquals(0, airportStatusReporter.countDelayedAirport(airportStatuses));
//  }
//
//  @Test
//  public void countDelayedAirportForOneDelayedAirport(){
//    List<AirportStatus> airportStatuses = Arrays.asList(iahHoustonTX, iadWashingtonDC);
//    assertEquals(1, airportStatusReporter.countDelayedAirport(airportStatuses));
//  }
//
//
//  @Test
//  public void countDelayedAirportForMultipleDelayedAirport(){
//    List<AirportStatus> airportStatuses = Arrays.asList(iadWashingtonDC, sfoSanFransiscoCA, iahHoustonTX, houHoustonTX );
//    assertEquals(2, airportStatusReporter.countDelayedAirport(airportStatuses));
//  }
//
//  @Test
//  public void dontCountInvalidAirportAsDelayedAirport(){
//    List<AirportStatus> airportStatuses = Arrays.asList(iadWashingtonDC, invalidAirport, sfoSanFransiscoCA, iahHoustonTX, houHoustonTX );
//    assertEquals(2, airportStatusReporter.countDelayedAirport(airportStatuses));
//  }
//
//  @Test
//  public void countDelayedAirportWhenThereIsNetworkProblem(){
//    List<AirportStatus> airportStatuses = Arrays.asList(iadWashingtonDC, networkError, sfoSanFransiscoCA, iahHoustonTX, houHoustonTX );
//    assertEquals(2, airportStatusReporter.countDelayedAirport(airportStatuses));
//  }
//
//
//  @Test
//  public void getAirportStatusReportForAnValidListOfAirportCode () {
//    List<String> airportCodes = Arrays.asList("SFO", "IAD", "IAH");
//
//    when(airportStatusServiceMock.getAirportStatus("IAH")).thenReturn(iahHoustonTX);
//    when(airportStatusServiceMock.getAirportStatus("IAD")).thenReturn(iadWashingtonDC);
//    when(airportStatusServiceMock.getAirportStatus("SFO")).thenReturn(sfoSanFransiscoCA);
//
//    assertEquals(AirportStatusReportWithValidListOfCities.sortedListOfAirportStatus.size(),
//        airportStatusReporter.getAirportStatusReport(airportCodes).sortedListOfAirportStatus.size());
//
//    assertEquals(AirportStatusReportWithValidListOfCities.noOfDelayedAirports,
//        airportStatusReporter.getAirportStatusReport(airportCodes).noOfDelayedAirports);
//
//    assertEquals("Dulles",
//        airportStatusReporter.getAirportStatusReport(airportCodes).sortedListOfAirportStatus.get(0).getCity());
//
//    assertEquals("Houston",
//        airportStatusReporter.getAirportStatusReport(airportCodes).sortedListOfAirportStatus.get(1).getCity());
//
//    assertEquals("San Francisco",
//        airportStatusReporter.getAirportStatusReport(airportCodes).sortedListOfAirportStatus.get(2).getCity());
//  }
//
//  @Test
//  public void getAirportStatusReportForAnInValidAirportCode (){
//    List<String> airportCodes = Arrays.asList("AAA", "IAD", "IAH");
//
//    when(airportStatusServiceMock.getAirportStatus("IAH")).thenReturn(iahHoustonTX);
//    when(airportStatusServiceMock.getAirportStatus("IAD")).thenReturn(iadWashingtonDC);
//    when(airportStatusServiceMock.getAirportStatus("AAA")).thenReturn(invalidAirport);
//
//    assertEquals(AirportStatusReportWithAnInvalidCity.sortedListOfAirportStatus.size(),
//        airportStatusReporter.getAirportStatusReport(airportCodes).sortedListOfAirportStatus.size());
//
//    assertEquals(AirportStatusReportWithAnInvalidCity.noOfDelayedAirports,
//        airportStatusReporter.getAirportStatusReport(airportCodes).noOfDelayedAirports);
//
//    assertEquals("---",
//        airportStatusReporter.getAirportStatusReport(airportCodes).sortedListOfAirportStatus.get(0).getCity());
//
//    assertEquals("Dulles",
//        airportStatusReporter.getAirportStatusReport(airportCodes).sortedListOfAirportStatus.get(1).getCity());
//
//    assertEquals("Houston",
//        airportStatusReporter.getAirportStatusReport(airportCodes).sortedListOfAirportStatus.get(2).getCity());
//
//    assertEquals("Houston",
//        airportStatusReporter.getAirportStatusReport(airportCodes).sortedListOfAirportStatus.get(2).getCity());
//  }
//
//}