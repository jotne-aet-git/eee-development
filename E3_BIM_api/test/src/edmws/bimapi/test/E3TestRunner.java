package edmws.bimapi.test;


	
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class E3TestRunner {

   public static void main(String[] args) {
	   for(int i0=0;i0<args.length;i0++)
	   {
		   String arg = args[i0];
		   if(arg.startsWith("e3test.devRoot=")) E3TestBaseWeb.SetDevRoot(arg.substring(arg.indexOf('=')+1)); 
	   }
      Result result = JUnitCore.runClasses(
    		  E3Tests01.class,
    		  E3Tests02.class,
    		  E3Tests03.class
    		  );
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println(result.wasSuccessful());
   }
}
