/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package progressbar;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

/**
 *
 * @author salim
 * http://docs.oracle.com/javase/7/docs/api/javax/swing/SwingWorker.html
 */
class PrimeNumbersTaskBck extends
         SwingWorker<List<Integer>, Integer>  {
    
    JTextField textfield;
     JProgressBar progressbar;
    private int numbersToFind; 
    private List<Integer> numbers = new ArrayList<Integer>(); 
    private int number = 0; 
    private final int myLimit; 
    private int myLimitCounter; 
    boolean enough = false; 
    
     PrimeNumbersTaskBck(JTextField textfield, int numbersToFind,JProgressBar progressbar) {
         System.out.println("PrimeNumbersTask calling constructor");
         this.textfield = textfield; 
         this.progressbar = progressbar;
        this.numbersToFind = numbersToFind; 
        this.myLimit = numbersToFind; 
     }


     @Override
     public List<Integer> doInBackground() {
          int progress = 0;
             System.out.println("PrimeNumbersTask calling doInBackground()");
         while (! enough && ! isCancelled()) {
                 number = nextPrimeNumber();
                 //send the data chunk tp the process(List<>), in order to push them immediately
                 progress = number;
                 System.out.println("PrimeNumbersTask calling doInBackground(): progress="+progress);
                 publish(number);
                 setProgress(100 * numbers.size() / numbersToFind);
             }
         return numbers;
     }
     
     

     @Override
     protected void process(List<Integer> chunks) {
            System.out.println("PrimeNumbersTask calling process()");
         for (int number : chunks) {
             textfield.setText(String.valueOf(number));//  textArea.append(number + "\n");
         }
     }
     
      //in my case next natural number 
    private int nextPrimeNumber() { 
         System.out.println("PrimeNumbersTask calling nextPrimeNumber()");
        //end condition 
        if (++myLimitCounter >= myLimit) { 
            enough = true; 
        } 
        try { 
            Thread.sleep(1000); 
        } catch (InterruptedException ex) { 
            // 
        } 
        return (++number); 
    }
    
      /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("propertyChange invoked..");
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
               System.out.println("propertyChange: progress="+progress);
               progressbar.setValue(progress);
           
        } 
    }
 }

// JTextArea textArea = new JTextArea();
// final JProgressBar progressBar = new JProgressBar(0, 100);
// 

