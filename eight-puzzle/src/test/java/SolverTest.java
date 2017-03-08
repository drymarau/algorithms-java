import org.junit.Test;

public final class SolverTest {

  @Test(expected = NullPointerException.class)
  public void throwsNullPointerExceptionIfConstructorParameterIsNull() throws Exception {
    new Solver(null);
  }
}
