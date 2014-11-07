/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import bo.TrfBo;
import com.safirasoft.ResVo;
import cr.Cc;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import vp.vo.CdcVo;

/**
 *
 * @author salim
 */
public class TrfJPanel extends javax.swing.JPanel implements PropertyChangeListener {

    private TL tk;
    TrfBo trfBo;
    private static Cc cc;

    public String custnm;

    public TrfJPanel(String custnm) throws Exception {
        initComponents();
        this.custnm = custnm;
        trfBo = new TrfBo();
        cc = new Cc();
    }

    public static Cc getCc() {
        return cc;
    }

    public static void setCc(Cc cc) {
        TrfJPanel.cc = cc;
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
        runTestButton = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        reset = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        codecComboBox1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        timelengthjComboBox = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        testStatTextArea = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaPortused = new javax.swing.JTextArea();

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

        runTestButton.setText("Run Test");
        runTestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runTestButtonActionPerformed(evt);
            }
        });

        jLabel26.setBackground(new java.awt.Color(230, 137, 45));
        jLabel26.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel26.setText("VoIP test - Traffic generator");

        reset.setText("Reset");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });

        jProgressBar1.setStringPainted(true);

        codecComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "G.711 (87.2 Kbps)", "G.722 (80 kbps)", "G.729 (31.2 Kbps)", "ILBC (27.7 kbps)", "SILK (178,5 kbps) " }));

        jLabel1.setText("Codec:");

        jLabel2.setText("Time for the test (secs):");

        timelengthjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15", "30", "60" }));

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel3.setText("VoIP test statistics");

        jLabel4.setFont(new java.awt.Font("Ubuntu", 0, 10)); // NOI18N
        jLabel4.setText("Recommended java 1.7+");

        testStatTextArea.setEditable(false);
        testStatTextArea.setColumns(20);
        testStatTextArea.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
        testStatTextArea.setRows(10);
        testStatTextArea.setToolTipText("");
        jScrollPane1.setViewportView(testStatTextArea);

        jTabbedPane1.addTab("Statistics", jScrollPane1);

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jTextArea2.setText("The idea is to send Voip traffic from the client ( phone location ) to the server ( PBX location ) and vice \nversa. The purpose of this test is to determine the quality and stability of the internet  between \nour PBX Server and the phones, we have situations where the upload ( traffic from the phone to the server )\nand download ( from the server to the phone ) may not have the same latency, packet lost or jitter, \nand sometimes that difference is noticeable. In fact, this software will compute the upload and download packet loss, latency, jitter.\n\nLatency maximum desired: 150ms\n150ms is the specified maximum desired one-way latency to achieve high-quality voice. Voice users will notice round-trip delays that exceed 250ms. \nMore than that, and callers start talking over each other.");
        jScrollPane3.setViewportView(jTextArea2);

        jTabbedPane1.addTab("Why Voip traffic generator?", jScrollPane3);

        jTextAreaPortused.setColumns(20);
        jTextAreaPortused.setRows(5);
        jScrollPane2.setViewportView(jTextAreaPortused);

        jTabbedPane1.addTab("Port Used", jScrollPane2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(resultmsgjlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(codecComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(runTestButton, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 171, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(152, 152, 152)
                                        .addComponent(jLabel2)
                                        .addGap(6, 6, 6)
                                        .addComponent(timelengthjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(reset)))
                            .addComponent(jTabbedPane1))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(302, 302, 302))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addContainerGap())))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel26)
                .addGap(264, 264, 264))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(resultmsgjlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(codecComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(timelengthjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(runTestButton)
                            .addComponent(reset))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addContainerGap(198, Short.MAX_VALUE))
        );

        resultmsgjlabel.getAccessibleContext().setAccessibleName("<html>Critical Error : SIP ALG is corrupting SIP Messages, Please disable SIP ALG in the router<html>");
    }// </editor-fold>//GEN-END:initComponents

    private void runTestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runTestButtonActionPerformed
        jTextAreaPortused.setText("");
        runTestButton.setEnabled(false);
        reset.setEnabled(false);

        tk = new TL();
        tk.addPropertyChangeListener(this);
        tk.execute();
        //System.out.println("runALGButtonActionPerformed initializing progress bar .. ");
        jProgressBar1.setValue(0);
        testStatTextArea.setText(TrfBo.M_PR);
    }//GEN-LAST:event_runTestButtonActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        testStatTextArea.setText("");
        resultmsgjlabel.setText("");
        jTextAreaPortused.setText("");
    }//GEN-LAST:event_resetActionPerformed

    private void formAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_formAncestorAdded

    }//GEN-LAST:event_formAncestorAdded


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox codecComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    public javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea2;
    public static javax.swing.JTextArea jTextAreaPortused;
    private javax.swing.JButton reset;
    public static javax.swing.JLabel resultmsgjlabel;
    public static javax.swing.JButton runTestButton;
    private javax.swing.JTextArea testStatTextArea;
    private javax.swing.JComboBox timelengthjComboBox;
    // End of variables declaration//GEN-END:variables

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
        public Void doInBackground() throws Exception {

            int progress = 0;
            //Initialize progress property.
            setProgress(0);
            //setProgress(50);
            System.out.println("doInBackground::Thread name: " + Thread.currentThread().getName() + " Priority=" + Thread.currentThread().getPriority());
            try {
                launchtest();
            } catch (IOException ex) {
                Logger.getLogger(TrfJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            //progress = 100;
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
            runTestButton.setEnabled(true);
            reset.setEnabled(true);
            setCursor(null); //turn off the wait cursor
            //taskOutput.append("Done!\n");
        }
    }//end of swing worker task Class

    public String launchtest() throws IOException, Exception {
        String codec = CdcVo.returnSelectedCodec(codecComboBox1.getSelectedIndex());
        //System.out.println(codec);
        String timeLength = timelengthjComboBox.getSelectedItem().toString();
        //System.out.println("timelength=" + timeLength);
        runTestButton.setEnabled(false);
        //cc.updateJprogressBar(jProgressBar1, 80);
        reset.setEnabled(false);
        ResVo resvo = cc.launchtest(codec, timeLength, custnm, jProgressBar1);
        if (resvo != null) {
            if (trfBo.isES(resvo)) {
                trfBo.setresultmessage(resultmsgjlabel, TrfBo.M_U_T);
                trfBo.renderJTextAreaMessage(testStatTextArea, TrfBo.M_U_T);
            } else {//case of successfull test
                trfBo.renderJTextAreaStatistics(testStatTextArea, resvo);
            }
        } else {//handle the case of ws return null in retrieving results
            trfBo.setresultmessage(resultmsgjlabel, TrfBo.NO_RES);
            trfBo.renderJTextAreaMessage(testStatTextArea, TrfBo.NO_RES);
        }
        return null;
    }

}
