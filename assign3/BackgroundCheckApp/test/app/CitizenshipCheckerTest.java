package app;

public class CitizenshipCheckerTest extends BaseCheckerTest {

  @Override
  public Checker createChecker() {
    return new CitizenshipChecker();
  }

  @Override
  public String getMessage() {
    return "Foreign National";
  }
}