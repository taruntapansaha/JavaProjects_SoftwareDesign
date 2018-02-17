package app;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class CriteriaEvaluatorTest {
  CriteriaEvaluator criteriaEvaluator;

  CreditChecker creditCheckerMock;
  CriminalChecker criminalCheckerMock;
  List<Checker> listOfCheckers;
  List<String> rejectStrings;
  Candidate candidatePassesAll, candidateFailsAll, candidateFailsSome;

  @Before
  public void setUp() {
    criteriaEvaluator = new CriteriaEvaluator();
    candidatePassesAll = new Candidate(234232345, "Mary", "Pass");
    candidateFailsAll = new Candidate(789787890, "John", "Fail");
    candidateFailsSome = new Candidate(123121234, "Kate", "Half");

    creditCheckerMock = Mockito.mock(CreditChecker.class);
    criminalCheckerMock = Mockito.mock(CriminalChecker.class);

    when(creditCheckerMock.getReport(candidatePassesAll)).thenReturn(EvaluationResult.createApproved());
    when(creditCheckerMock.getReport(candidateFailsSome)).thenReturn(EvaluationResult.createApproved());
    when(creditCheckerMock.getReport(candidateFailsAll)).thenReturn(EvaluationResult.createRejected("Credit too low"));
    when(criminalCheckerMock.getReport(candidatePassesAll)).thenReturn(EvaluationResult.createApproved());
    when(criminalCheckerMock.getReport(candidateFailsSome)).thenReturn(EvaluationResult.createRejected("Criminal Record(s) found"));
    when(criminalCheckerMock.getReport(candidateFailsAll)).thenReturn(EvaluationResult.createRejected("Criminal Record(s) found"));

    listOfCheckers = new ArrayList<>();
    rejectStrings = new ArrayList<>();

  }

  @Test
  public void Canary() {
    assertTrue(true);
  }

  @Test
  public void evaluationWithZeroCheckers() {
    listOfCheckers = Arrays.asList();
    assertEquals(Arrays.asList(), criteriaEvaluator.evaluate(candidatePassesAll, listOfCheckers));
    assertEquals(Arrays.asList(), criteriaEvaluator.evaluate(candidateFailsSome, listOfCheckers));
    assertEquals(Arrays.asList(), criteriaEvaluator.evaluate(candidateFailsAll, listOfCheckers));
  }

  @Test
  public void evaluationWithOneCheckerPassing(){
    listOfCheckers.add(creditCheckerMock);
    assertEquals(true, criteriaEvaluator.getApproved(candidatePassesAll, listOfCheckers));
    assertEquals(Arrays.asList(), criteriaEvaluator.evaluate(candidatePassesAll, listOfCheckers));
  }

  @Test
  public void evaluationWithOneCheckerFailing(){
    listOfCheckers.add(creditCheckerMock);
    rejectStrings.add("Credit too low");
    assertEquals(false, criteriaEvaluator.getApproved(candidateFailsAll, listOfCheckers));
    assertEquals(rejectStrings, criteriaEvaluator.evaluate(candidateFailsAll, listOfCheckers));
  }

  @Test
  public void evaluationWithTwoCheckersPassing(){
    listOfCheckers.add(creditCheckerMock);
    listOfCheckers.add(criminalCheckerMock);
    assertEquals(Arrays.asList(), criteriaEvaluator.evaluate(candidatePassesAll, listOfCheckers));
  }

  @Test
  public void evaluationWithOnePassOneFail(){
    listOfCheckers.add(creditCheckerMock);
    listOfCheckers.add(criminalCheckerMock);
    rejectStrings.add("Criminal Record(s) found");
    assertEquals(rejectStrings, criteriaEvaluator.evaluate(candidateFailsSome, listOfCheckers));
  }

  @Test
  public void evaluationWhereBothFail(){
    listOfCheckers.add(creditCheckerMock);
    listOfCheckers.add(criminalCheckerMock);
    rejectStrings.add("Credit too low");
    rejectStrings.add("Criminal Record(s) found");
    assertEquals(rejectStrings, criteriaEvaluator.evaluate(candidateFailsAll, listOfCheckers));
  }
}
