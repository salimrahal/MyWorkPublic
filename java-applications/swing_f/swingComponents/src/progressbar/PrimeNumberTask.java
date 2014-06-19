/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package progressbar;

import java.util.List;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

/**
 *
 * @author salim
 * http://docs.oracle.com/javase/7/docs/api/javax/swing/SwingWorker.html
 */
class PrimeNumbersTask extends
         SwingWorker<List<Integer>, Integer> {
    
         int number;
     List<Integer> numbers;
     int numbersToFind;
     JTextField textfield;
     
     PrimeNumbersTask(JTextField textfield, int numbersToFind) {
         //initialize
     }


     @Override
     public List<Integer> doInBackground() {
        // while (! enough && ! isCancelled()) {
                 number = nextPrimeNumber();
                 publish(number);
                 setProgress(100 * numbers.size() / numbersToFind);
             //}
         return numbers;
     }
     
     

//     @Override
//     protected void process(List<Integer> chunks) {
//         for (int number : chunks) {
//             textfield.setText(number);//  textArea.append(number + "\n");
//         }
//     }
 }

// JTextArea textArea = new JTextArea();
// final JProgressBar progressBar = new JProgressBar(0, 100);
// 
