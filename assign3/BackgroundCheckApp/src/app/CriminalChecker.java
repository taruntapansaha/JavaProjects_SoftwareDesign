package app;

public class CriminalChecker implements Checker {
  public EvaluationResult getReport(Candidate candidate) {
    boolean approved = (candidate.getSSN() / 100000000) % 2 == 0;
    if (approved)
      return EvaluationResult.createApproved();
    else
      return EvaluationResult.createRejected("Criminal Record(s) found");
  }
}
