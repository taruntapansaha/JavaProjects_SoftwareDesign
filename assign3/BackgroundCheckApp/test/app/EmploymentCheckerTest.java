package app;

public class EmploymentCheckerTest extends BaseCheckerTest {

  @Override
  public Checker createChecker() {
    return new EmploymentChecker();
  }

  @Override
  public String getMessage() {
    return "No Employment found";
  }
}