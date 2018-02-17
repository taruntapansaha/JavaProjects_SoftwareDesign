package app;

public class CitizenshipChecker implements Checker {
  @Override
  public EvaluationResult getReport(Candidate candidate) {
    boolean approved = (candidate.getSSN() / 100000000) < 5;
    if (approved)
      return EvaluationResult.createApproved();
    else
      return EvaluationResult.createRejected("Foreign National");
  }
}
