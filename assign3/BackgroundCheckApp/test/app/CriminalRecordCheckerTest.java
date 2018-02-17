package app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CriminalRecordCheckerTest extends BaseCheckerTest{

  public CriminalChecker createChecker(){
    return new CriminalChecker();
  }

  public String getMessage(){
    return "Criminal Record(s) found";
  }
}