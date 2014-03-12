package exceptions;

import java.io.FileReader;
import java.io.IOException;

public class ExceptionUtil
{
   public void openFile()
   {
      FileReader reader = null;
      try
      {
         reader = new FileReader("someFile");
         int i = 0;
         while(i != -1)
         {
            i = reader.read();
            System.out.println((char) i);
         }
      }
      catch(IOException e)
      {
         // do something clever with the exception
      }
      finally
      {
         if(reader != null)
         {
            try
            {
               reader.close();
            }
            catch(IOException e)
            {
               // do something clever with the exception
            }
         }
         System.out.println("--- File End ---");
      }
   }

   public static String numberExceptionTest()
   {  String res = "res";
      try
      {
         int x = 1;
         int y = x / 0;
         System.out.print("Begin----");
        
      }
      //catch(ArithmeticException e)
      catch(NumberFormatException e)
      {
         System.out.print(e);
      }

         System.out.print("\n Execute code -----");
         
         return res;
   }
   
   public static String numberExceptionTestFinally()
   {  String res = "res";
      try
      {
         int x = 1;
         float y = x / 0;
         System.out.print("Begin-----");
        
      }
      //catch(RuntimeException e)
      catch(NumberFormatException e)
      {
         //System.out.print(e);
      }
      finally
      {
    	  System.out.print("\n Finally:: Execute code -----");
      }
         
      return res;
   }
   
   
   
}
