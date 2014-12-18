/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algGui;

import algBo.Alb;
import algcr.Cc;
import algVo.Test;
import java.awt.Color;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 *
 * @author salim
 * Light version: text area have no effect on the App
 */
public class AlgJPanel extends javax.swing.JPanel implements PropertyChangeListener {

    private TL tk;
    /**
     * Creates new form AlgJPanel
     */
    private static Cc cc;
    public String custnmParam;

    public static javax.swing.JTextArea comb1RcvMsgINV = new JTextArea();
    public static javax.swing.JTextArea comb1RcvMsgREG = new JTextArea();
    private static javax.swing.JTextArea comb1SentMsgINV = new JTextArea();
    public static javax.swing.JTextArea comb1SentMsgREG = new JTextArea();
    public static javax.swing.JTextArea comb2RcvMsgINV = new JTextArea();
    public static javax.swing.JTextArea comb2RcvMsgREG = new JTextArea();
    public static javax.swing.JTextArea comb2SentMsgINV = new JTextArea();
    public static javax.swing.JTextArea comb2SentMsgREG = new JTextArea();
    public static javax.swing.JTextArea comb3RcvMsgINV = new JTextArea();
    public static javax.swing.JTextArea comb3RcvMsgREG = new JTextArea();
    public static javax.swing.JTextArea comb3SentMsgINV = new JTextArea();
    public static javax.swing.JTextArea comb3SentMsgREG = new JTextArea();
    public static javax.swing.JTextArea comb4RcvMsgINV = new JTextArea();
    public static javax.swing.JTextArea comb4RcvMsgREG = new JTextArea();
    public static javax.swing.JTextArea comb4SentMsgINV = new JTextArea();
    public static javax.swing.JTextArea comb4SentMsgREG = new JTextArea();

    public static Cc getCc() {
        return cc;
    }

    public static void setCc(Cc ccparam) {
        cc = ccparam;
    }

    public AlgJPanel() throws Exception {
        initComponents();
    }

    public String getCustnm() {
        return custnmParam;
    }

    public void setCustnm(String custnm) {
        this.custnmParam = custnm;
        //todo set custname to the label customer name
        if (custnm != null && !custnm.isEmpty()) {
            jTextFieldCustomer.setText(custnm);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        resultmsgjlabel = new javax.swing.JLabel();
        runALGButton = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jTabbedPaneTest = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        reset = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        jTextFieldCustomer = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(800, 735));
        addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                formAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        resultmsgjlabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        resultmsgjlabel.setText("result message");
        resultmsgjlabel.setToolTipText("");
        resultmsgjlabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(211, 27, 27)));

        runALGButton.setText("Run Test");
        runALGButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runALGButtonActionPerformed(evt);
            }
        });

        jLabel26.setBackground(new java.awt.Color(230, 137, 45));
        jLabel26.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel26.setText("ALG Detector");

        jTabbedPaneTest.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 883, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jTabbedPaneTest.addTab("Test A", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 883, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jTabbedPaneTest.addTab("Test B", jPanel2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 883, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jTabbedPaneTest.addTab("Test C", jPanel3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 883, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jTabbedPaneTest.addTab("Test D", jPanel4);

        reset.setText("Reset");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });

        jProgressBar1.setStringPainted(true);

        jLabel1.setText("Customer or company name:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPaneTest, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldCustomer))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addGap(140, 140, 140))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(runALGButton)
                                .addGap(95, 95, 95)
                                .addComponent(reset)
                                .addGap(84, 84, 84)))))
                .addComponent(resultmsgjlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(runALGButton)
                            .addComponent(reset)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(resultmsgjlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPaneTest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(572, Short.MAX_VALUE))
        );

        resultmsgjlabel.getAccessibleContext().setAccessibleName("<html>Critical Error : SIP ALG is corrupting SIP Messages, Please disable SIP ALG in the router<html>");
    }// </editor-fold>//GEN-END:initComponents

    private void runALGButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runALGButtonActionPerformed

        String custNameFromForm = jTextFieldCustomer.getText();
        if (!custNameFromForm.isEmpty()) {
            custnmParam = custNameFromForm;
            runALGButton.setEnabled(false);
            reset.setEnabled(false);

            tk = new TL();
            tk.addPropertyChangeListener(this);
            tk.execute();
            //System.out.println("runALGButtonActionPerformed initializing progress bar .. ");
            jProgressBar1.setValue(0);
        } else {
            JOptionPane.showMessageDialog(this, "Enter Customer or Company name");
        }
    }//GEN-LAST:event_runALGButtonActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
//     removed to avoid aobject already in used exception while doing reset
        jProgressBar1.setValue(0);
        resultmsgjlabel.setText(Alb.I_P);
        System.out.println("resetActionPerformed...");
        resultmsgjlabel.setBackground(Color.white);
        //clean the text areas:
        comb1SentMsgREG.setText("Sent message:");
        comb1SentMsgINV.setText("Sent message:");
        comb1RcvMsgREG.setText("Received message:");
        comb1RcvMsgINV.setText("Received message:");

        comb2SentMsgREG.setText("Sent message:");
        comb2SentMsgINV.setText("Sent message:");
        comb2RcvMsgREG.setText("Received message:");
        comb2RcvMsgINV.setText("Received message:");

        comb3SentMsgREG.setText("Sent message:");
        comb3SentMsgINV.setText("Sent message:");
        comb3RcvMsgREG.setText("Received message:");
        comb3RcvMsgINV.setText("Received message:");

        comb4SentMsgREG.setText("Sent message:");
        comb4SentMsgINV.setText("Sent message:");
        comb4RcvMsgREG.setText("Received message:");
        comb4RcvMsgINV.setText("Received message:");
        resultmsgjlabel.setText(Alb.R_K);

    }//GEN-LAST:event_resetActionPerformed
//creating the SIP stack and Listening point upon formAncestoradded Event occured
    private void formAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_formAncestorAdded
        // TODO add your handling code here:

        if (getCc() == null) {

            try {
                cc = new Cc();
//                sipClientController.createSipStack();
//                sipClientController.createSipFrameWork();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }//end exception
        }//end sipclientcontroller == null
    }//GEN-LAST:event_formAncestorAdded


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    public javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JTabbedPane jTabbedPaneTest;
    private javax.swing.JTextField jTextFieldCustomer;
    private javax.swing.JButton reset;
    public static javax.swing.JLabel resultmsgjlabel;
    public static javax.swing.JButton runALGButton;
    // End of variables declaration//GEN-END:variables

    public void sr(Integer combSeq, JTextArea sentmsgReg, JTextArea sentmsgInv, Cc cc) {
        Test comb = null;
        JTextArea recvjtextregister = null;
        JTextArea recvjtextinvite = null;

        Alb alb = cc.getAlgBo();
        if (combSeq == 1) {
            comb = alb.getTestfromCombId(1);
            recvjtextregister = comb1RcvMsgREG;
            recvjtextinvite = comb1RcvMsgINV;
        } else if (combSeq == 2) {
            comb = alb.getTestfromCombId(2);
            recvjtextregister = comb2RcvMsgREG;
            recvjtextinvite = comb2RcvMsgINV;
        } else if (combSeq == 3) {
            comb = alb.getTestfromCombId(3);
            recvjtextregister = comb3RcvMsgREG;
            recvjtextinvite = comb3RcvMsgINV;
        } else if (combSeq == 4) {
            comb = alb.getTestfromCombId(4);
            recvjtextregister = comb4RcvMsgREG;
            recvjtextinvite = comb4RcvMsgINV;
        }

        try {
            //System.out.println("Algpanel:sr:comb=" + comb.toString());
            cc.preprreq(comb, sentmsgReg, recvjtextregister, sentmsgInv, recvjtextinvite, custnmParam);

        } catch (IOException ex) {
            Logger.getLogger(AlgJPanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //System.out.println("propertyChange invoked..");
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            //System.out.println("propertyChange: progress=" + progress);
            //this is where we update the UI progress Bar
            jProgressBar1.setValue(progress);
//            taskOutput.append(String.format(
//                    "Completed %d%% of task.\n", task.getProgress()));
        }
    }

    class TL extends SwingWorker<Void, Void> {
        /*
         * Main task. Executed in background thread.
         */

        @Override
        public Void doInBackground() {
            int progress = 0;
            //Initialize progress property. 
            setProgress(0);
            resultmsgjlabel.setText(Alb.I_P);

            // get the currently selected index for this tabbedpan 0, 1, 2 ,3 and then select the correspondent radio button
            int selectedIndex = jTabbedPaneTest.getSelectedIndex();
            //System.out.println("selectedIndex=" + selectedIndex);

            //udp
            if (jTabbedPaneTest.getSelectedIndex() == 0) {
                setProgress(50);
                sr(1, comb1SentMsgREG, comb1SentMsgINV, cc);
            } //tcp
            else if (jTabbedPaneTest.getSelectedIndex() == 1) {
                setProgress(50);
                sr(2, comb2SentMsgREG, comb2SentMsgINV, cc);
            } //udp
            else if (jTabbedPaneTest.getSelectedIndex() == 2) {
                setProgress(50);
                sr(3, comb3SentMsgREG, comb3SentMsgINV, cc);
            } //tcp
            else if (jTabbedPaneTest.getSelectedIndex() == 3) {
                setProgress(50);
                sr(4, comb4SentMsgREG, comb4SentMsgINV, cc);
            }
            progress = 100;
            setProgress(Math.min(progress, 100));
            return null;
        }


        /*
         * Executed in event dispatching thread
         When the background task is complete, the task's done method resets the progress bar:
         */
        @Override
        public void done() {
            //System.out.println("Swing worker :: task is Done............");
            Toolkit.getDefaultToolkit().beep();
            runALGButton.setEnabled(true);
            reset.setEnabled(true);
            setCursor(null); //turn off the wait cursor
            //taskOutput.append("Done!\n");
        }
    }//end of swing worker task Class

}
