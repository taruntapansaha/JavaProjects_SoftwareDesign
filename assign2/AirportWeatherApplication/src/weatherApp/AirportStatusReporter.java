package weatherApp;

import java.util.Arrays;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class AirportStatusReporter  {

  private AirportStatusService theService;

  public void setTheService(AirportStatusService newService){
    theService = newService;
  }

  public List<AirportStatus> sortAirportStatuses(List<AirportStatus> airportStatuses){

     return airportStatuses.stream()
         .sorted(comparing(AirportStatus::getCity)
             .thenComparing(AirportStatus::getState))
         .collect(toList());
  }

  public List<AirportStatus> addAirports(List<String> airportCodes) {
    return airportCodes.stream()
        .map(theService::getAirportStatus)
        .collect(toList());
  }


  public int countDelayedAirport(List<AirportStatus> listOfAirportStatus) {
     return (int) listOfAirportStatus.stream()
       .filter(AirportStatus::isDelayed)
       .count();
  }

  public AirportStatusReportData getAirportStatusReport(List<String> airportCodes){
    List<AirportStatus> airportStatuses;
    List<AirportStatus> sortedAirportStatuses;
    int countDelayedAirports;

    if(!airportCodes.isEmpty()){
      airportStatuses = addAirports(airportCodes);
      sortedAirportStatuses = sortAirportStatuses(airportStatuses);
      countDelayedAirports = countDelayedAirport(sortedAirportStatuses);
    }
    else {
      sortedAirportStatuses = Arrays.asList();
      countDelayedAirports = 0;
    }
    return new AirportStatusReportData(sortedAirportStatuses, countDelayedAirports);
  }

}