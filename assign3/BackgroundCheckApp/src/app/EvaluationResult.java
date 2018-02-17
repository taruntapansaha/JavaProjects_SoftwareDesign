package app;

import java.util.Optional;

public class EvaluationResult {
  private boolean approved;
  private String reason;

  private EvaluationResult() {
   approved = true;
  }

  public static EvaluationResult createApproved() {
    return new EvaluationResult();
  }

  public static EvaluationResult createRejected(String reason) {
    EvaluationResult evaluationResult = new EvaluationResult();
    evaluationResult.approved = false;
    evaluationResult.reason = reason;
    return evaluationResult;
  }

  public boolean isApproved() {
    return approved;
  }

  public Optional<String> getReasonForRejection() {
    return Optional.ofNullable(reason);
  }
}
