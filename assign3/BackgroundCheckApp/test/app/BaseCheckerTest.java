package app;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class BaseCheckerTest {

  Checker criteriaChecker;


  public abstract Checker createChecker();

  public abstract String getMessage();

  @Before
  public void setUp() {
    criteriaChecker = createChecker();
  }

  @Test
  public void Canary() {
    assertTrue(true);

  }

  @Test
  public void candidatePassesCheck() {
    Candidate candidate = new Candidate(234232345, "Mary", "Pass");
    EvaluationResult evaluationResult = criteriaChecker.getReport(candidate);

    assertTrue(evaluationResult.isApproved());
    assertFalse(evaluationResult.getReasonForRejection().isPresent());
  }

  @Test
  public void candidateFailsCheck() {
    Candidate candidate = new Candidate(789787890, "John", "Fail");
    EvaluationResult evaluationResult = criteriaChecker.getReport(candidate);

    assertFalse(evaluationResult.isApproved());
    assertEquals(getMessage(), evaluationResult.getReasonForRejection().orElse(""));
  }
}