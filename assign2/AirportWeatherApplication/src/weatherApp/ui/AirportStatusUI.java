package weatherApp.ui;

import weatherApp.AirportStatus;
import weatherApp.AirportStatusReportData;
import weatherApp.AirportStatusReporter;
import weatherApp.FAAAirportStatusService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AirportStatusUI {


  public static void main(String args[]) {

    String inputFile = "AirportCodes.txt";
    AirportStatusReporter airportStatusReporter = new AirportStatusReporter();
    List<String> airportCodes = new ArrayList<>();

    Scanner sc;

    try {
      sc = new Scanner(new File(inputFile));
      while (sc.hasNext()) {
        airportCodes.add(sc.next());
      }
    } catch (FileNotFoundException e) {
      System.out.println("File not Found.");
    }

    airportStatusReporter.setTheService(new FAAAirportStatusService());

    AirportStatusReportData airportStatusReportData = airportStatusReporter.getAirportStatusReport(airportCodes);

    AirportStatusUI airportStatusUI = new AirportStatusUI();
    
    airportStatusUI.printReport(airportStatusReportData);

  }

  private void printReport(AirportStatusReportData airportStatusReportData)
  {
    List<AirportStatus> sortedListOfAirports = airportStatusReportData.getSortedListOfAirportStatus();

    int noOfDelayedAirports = airportStatusReportData.getNoOfDelayedAirports();

    System.out.println("Airport Status Report");
    System.out.println();
    for (AirportStatus airportStatus : sortedListOfAirports) {

      if (airportStatus.getError().equals("")) {

        String delay = "---";

        if(airportStatus.isDelayed())
          delay = "delayed";

        System.out.print(airportStatus.getCity() + " | " + airportStatus.getState() + " | " +
            airportStatus.getAirportCode() + " | "  + airportStatus.getAirportName()
            + " | " + airportStatus.getTemperature() + " | " + delay);
      } else {
        System.out.print(airportStatus.getAirportCode() + " | " + airportStatus.getCity() + " | " + airportStatus.getAirportName()
            + " | " + airportStatus.getState() + " | " + airportStatus.getTemperature() + " | ---");
      }

      System.out.println();
    }

    System.out.println();
    System.out.println("No of Delayed Airports: " + noOfDelayedAirports);

  }
}
