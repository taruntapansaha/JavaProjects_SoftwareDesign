package app;

public class Candidate {
  private final long SSN;
  private final String firstName;
  private final String lastName;

  public Candidate(long SSN, String firstName, String lastName) {
    this.SSN = SSN;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public long getSSN() { return SSN; }
}