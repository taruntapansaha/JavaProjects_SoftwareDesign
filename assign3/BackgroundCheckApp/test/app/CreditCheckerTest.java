package app;

public class CreditCheckerTest extends BaseCheckerTest{

  public CreditChecker createChecker(){
    return new CreditChecker();
  }

  public String getMessage(){
    return "Credit too low";
  }
}