package weatherApp;

import java.util.List;

public class AirportStatusReportData {
  List<AirportStatus> sortedListOfAirportStatus;
  int noOfDelayedAirports;

  public AirportStatusReportData(List<AirportStatus> sortedListOfAirportStatus, int noOfDelayedAirports) {
    this.sortedListOfAirportStatus = sortedListOfAirportStatus;
    this.noOfDelayedAirports = noOfDelayedAirports;
  }

  public List<AirportStatus> getSortedListOfAirportStatus() {
    return sortedListOfAirportStatus;
  }

  public int getNoOfDelayedAirports() {
    return noOfDelayedAirports;
  }
}
