package edmws.bimapi.test;


	
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class E3TestRunner {

   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(
    		  E3Tests01.class,
    		  E3Tests02.class
    		  );
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println(result.wasSuccessful());
   }
}
