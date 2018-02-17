package app;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.stream.Collectors;

public class CriteriaEvaluator {
  public List<String> evaluate(Candidate candidate, List<Checker> criteria){
      return criteria.stream()
        .map(criterion -> criterion.getReport(candidate))
        .filter(result -> !result.isApproved())
        .map(EvaluationResult::getReasonForRejection)
        .map(reason -> reason.orElse(""))
        .collect(Collectors.toList());
  }

  boolean getApproved(Candidate candidate, List<Checker> criteria){
    List<String> s = evaluate(candidate, criteria);
    return s.equals(Arrays.asList());
  }
}
