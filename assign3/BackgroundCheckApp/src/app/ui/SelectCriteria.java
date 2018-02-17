package app.ui;


import app.Candidate;
import app.Checker;
import app.CriteriaEvaluator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;


public class SelectCriteria {

  public List<Checker> createListOfCriteria(List<String> checkersString){
    List<Checker> list = new ArrayList<>();
    for (String checker: checkersString) {
      list.add(stringToChecker(checker));
    }
    return list;
  }

  Checker stringToChecker(String checker){
    try {
      return (Checker) Class.forName("app." + checker + "Checker").newInstance();
    }
    catch(Exception e){System.out.println("Class(es) not found");}
    return null;
  }

  public static void main(String[] args){
    Candidate candidate;
    List<String> listOfCriteria = new ArrayList<>();
    List<String> reasonsForRejection = new ArrayList<>();
    int ssn;
    String firstName, lastName, criteriaInput;
    CriteriaEvaluator criteriaEvaluator = new CriteriaEvaluator();
    SelectCriteria selectCriteria = new SelectCriteria();
    DisplayRejectionReasons displayRejectionReasons = new DisplayRejectionReasons();
    Scanner sc = new Scanner(System.in);

    System.out.print("Please enter the candidate's SSN: ");
    ssn = sc.nextInt();
    System.out.print("Please enter the candidate's first name: ");
    firstName = sc.next();
    System.out.print("Please enter the candidate's last name: ");
    lastName = sc.next();

    candidate = new Candidate(ssn, firstName, lastName);

    System.out.println("Please enter Criteria separated by space");

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      StringTokenizer st = new StringTokenizer(br.readLine());
      while(st.hasMoreTokens()){
        listOfCriteria.add(st.nextToken());
      }
    }
    catch(Exception e){
      System.out.println("Input error");
    }

    reasonsForRejection = criteriaEvaluator.evaluate(candidate, selectCriteria.createListOfCriteria(listOfCriteria));

    if(reasonsForRejection.equals(Arrays.asList())) {
      System.out.println("Candidate approved.");
    }
    else {
      System.out.println("Candidate rejected.");
      displayRejectionReasons.display(reasonsForRejection);
    }
  }
}





