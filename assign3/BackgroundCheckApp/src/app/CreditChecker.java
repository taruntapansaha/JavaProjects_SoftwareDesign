package app;

public class CreditChecker implements Checker {
  public EvaluationResult getReport (Candidate candidate) {
    boolean approved = (candidate.getSSN() / 100000000) < 6;
    if(approved)
      return EvaluationResult.createApproved();
    else
      return EvaluationResult.createRejected("Credit too low");
  }
}
