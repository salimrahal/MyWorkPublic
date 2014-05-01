/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algGui;

import algBo.ALGBo;
import algBo.ALgDetect;
import algBo.Networking;
import algController.ClientController;
import algVo.Combination;
import java.awt.Color;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sip.ObjectInUseException;
import javax.sip.PeerUnavailableException;
import javax.sip.TransportNotSupportedException;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author salim
 */
public class AlgJPanel extends javax.swing.JPanel {

    /**
     * Creates new form AlgJPanel
     */
    private static ClientController sipClientController;

    public static ClientController getSipClientController() {
        return sipClientController;
    }

    public static void setSipClientController(ClientController sipClientControllerparam) {
        sipClientController = sipClientControllerparam;
    }

    public AlgJPanel() throws Exception {
        initComponents();
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
        runALGtest = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        reset = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jTabbedPane1Reg = new javax.swing.JTabbedPane();
        jScrollPane9 = new javax.swing.JScrollPane();
        comb1SentMsgREG = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        comb1SentMsgINV = new javax.swing.JTextArea();
        jTabbedPane2Reg = new javax.swing.JTabbedPane();
        jScrollPane10 = new javax.swing.JScrollPane();
        comb1RcvMsgREG = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        comb1RcvMsgINV = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jRadioButton2 = new javax.swing.JRadioButton();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jScrollPane11 = new javax.swing.JScrollPane();
        comb2SentMsgREG = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        comb2SentMsgINV = new javax.swing.JTextArea();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jScrollPane12 = new javax.swing.JScrollPane();
        comb2RcvMsgREG = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        comb2RcvMsgINV = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jRadioButton3 = new javax.swing.JRadioButton();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jScrollPane13 = new javax.swing.JScrollPane();
        comb3SentMsgREG = new javax.swing.JTextArea();
        jScrollPane14 = new javax.swing.JScrollPane();
        comb3SentMsgINV = new javax.swing.JTextArea();
        jTabbedPane6 = new javax.swing.JTabbedPane();
        jScrollPane16 = new javax.swing.JScrollPane();
        comb3RcvMsgREG = new javax.swing.JTextArea();
        jScrollPane15 = new javax.swing.JScrollPane();
        comb3RcvMsgINV = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jRadioButton4 = new javax.swing.JRadioButton();
        jTabbedPane7 = new javax.swing.JTabbedPane();
        jScrollPane18 = new javax.swing.JScrollPane();
        comb4SentMsgREG = new javax.swing.JTextArea();
        jScrollPane17 = new javax.swing.JScrollPane();
        comb4SentMsgINV = new javax.swing.JTextArea();
        jTabbedPane8 = new javax.swing.JTabbedPane();
        jScrollPane19 = new javax.swing.JScrollPane();
        comb4RcvMsgREG = new javax.swing.JTextArea();
        jScrollPane20 = new javax.swing.JScrollPane();
        comb4RcvMsgINV = new javax.swing.JTextArea();

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

        runALGtest.setText("Run Test");
        runALGtest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runALGtestActionPerformed(evt);
            }
        });

        jLabel26.setBackground(new java.awt.Color(230, 137, 45));
        jLabel26.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel26.setText("ALG Detector");

        reset.setText("Reset");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Transport: UDP Source Port: 5060 Destination Port: 5060");

        comb1SentMsgREG.setColumns(20);
        comb1SentMsgREG.setRows(5);
        comb1SentMsgREG.setText("Sent message:");
        jScrollPane9.setViewportView(comb1SentMsgREG);

        jTabbedPane1Reg.addTab("Register", jScrollPane9);

        comb1SentMsgINV.setColumns(20);
        comb1SentMsgINV.setRows(5);
        comb1SentMsgINV.setText("Sent message:");
        jScrollPane1.setViewportView(comb1SentMsgINV);

        jTabbedPane1Reg.addTab("Invite", jScrollPane1);

        comb1RcvMsgREG.setColumns(20);
        comb1RcvMsgREG.setLineWrap(true);
        comb1RcvMsgREG.setRows(5);
        comb1RcvMsgREG.setText("Received message:");
        jScrollPane10.setViewportView(comb1RcvMsgREG);

        jTabbedPane2Reg.addTab("Register", jScrollPane10);

        comb1RcvMsgINV.setColumns(20);
        comb1RcvMsgINV.setRows(5);
        comb1RcvMsgINV.setText("Received message:");
        jScrollPane2.setViewportView(comb1RcvMsgINV);

        jTabbedPane2Reg.addTab("Invite", jScrollPane2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTabbedPane1Reg, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane2Reg, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jRadioButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTabbedPane2Reg, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1Reg))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab1", jPanel1);

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Transport: TCP Source Port: 5060 Destination Port: 5060");

        comb2SentMsgREG.setColumns(20);
        comb2SentMsgREG.setRows(5);
        jScrollPane11.setViewportView(comb2SentMsgREG);

        jTabbedPane3.addTab("Register", jScrollPane11);

        comb2SentMsgINV.setColumns(20);
        comb2SentMsgINV.setRows(5);
        comb2SentMsgINV.setText("Sent message:");
        jScrollPane3.setViewportView(comb2SentMsgINV);

        jTabbedPane3.addTab("Invite", jScrollPane3);

        comb2RcvMsgREG.setColumns(20);
        comb2RcvMsgREG.setRows(5);
        jScrollPane12.setViewportView(comb2RcvMsgREG);

        jTabbedPane4.addTab("Register", jScrollPane12);

        comb2RcvMsgINV.setColumns(20);
        comb2RcvMsgINV.setRows(5);
        comb2RcvMsgINV.setText("Received message:");
        jScrollPane4.setViewportView(comb2RcvMsgINV);

        jTabbedPane4.addTab("Invite", jScrollPane4);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 221, Short.MAX_VALUE)
                        .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("tab2", jPanel2);

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("Transport: UDP Source Port: 5062 Destination Port: 5060");

        comb3SentMsgREG.setColumns(20);
        comb3SentMsgREG.setRows(5);
        jScrollPane13.setViewportView(comb3SentMsgREG);

        jTabbedPane5.addTab("Register", jScrollPane13);

        comb3SentMsgINV.setColumns(20);
        comb3SentMsgINV.setRows(5);
        comb3SentMsgINV.setText("Sent message:");
        jScrollPane14.setViewportView(comb3SentMsgINV);

        jTabbedPane5.addTab("Invite", jScrollPane14);

        comb3RcvMsgREG.setColumns(20);
        comb3RcvMsgREG.setRows(5);
        jScrollPane16.setViewportView(comb3RcvMsgREG);

        jTabbedPane6.addTab("Register", jScrollPane16);

        comb3RcvMsgINV.setColumns(20);
        comb3RcvMsgINV.setRows(5);
        comb3RcvMsgINV.setText("Received message:");
        jScrollPane15.setViewportView(comb3RcvMsgINV);

        jTabbedPane6.addTab("Invite", jScrollPane15);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton3)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTabbedPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTabbedPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTabbedPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jRadioButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTabbedPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("tab3", jPanel3);

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setText("Transport: TCP Source Port: 5062 Destination Port: 5060");

        comb4SentMsgREG.setColumns(20);
        comb4SentMsgREG.setRows(5);
        comb4SentMsgREG.setText("Sent message:");
        jScrollPane18.setViewportView(comb4SentMsgREG);

        jTabbedPane7.addTab("Register", jScrollPane18);

        comb4SentMsgINV.setColumns(20);
        comb4SentMsgINV.setRows(5);
        jScrollPane17.setViewportView(comb4SentMsgINV);

        jTabbedPane7.addTab("Invite", jScrollPane17);

        comb4RcvMsgREG.setColumns(20);
        comb4RcvMsgREG.setRows(5);
        comb4RcvMsgREG.setText("Received message:");
        jScrollPane19.setViewportView(comb4RcvMsgREG);

        jTabbedPane8.addTab("Register", jScrollPane19);

        comb4RcvMsgINV.setColumns(20);
        comb4RcvMsgINV.setRows(5);
        jScrollPane20.setViewportView(comb4RcvMsgINV);

        jTabbedPane8.addTab("Invite", jScrollPane20);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jRadioButton4)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jTabbedPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jRadioButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 211, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTabbedPane8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("tab4", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(runALGtest)
                .addGap(2, 2, 2)
                .addComponent(jLabel26)
                .addGap(2, 2, 2)
                .addComponent(reset)
                .addGap(108, 108, 108)
                .addComponent(resultmsgjlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(runALGtest)
                            .addComponent(reset)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(resultmsgjlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        resultmsgjlabel.getAccessibleContext().setAccessibleName("<html>Critical Error : SIP ALG is corrupting SIP Messages, Please disable SIP ALG in the router<html>");
    }// </editor-fold>//GEN-END:initComponents

    private void runALGtestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runALGtestActionPerformed
        //method-1: sending Datagrame directly to the server
//        ALgDetect alg = new ALgDetect();
//        String res = alg.algDetect();
//        resultmsg.setText(res);
        resultmsgjlabel.setText(ALGBo.INPROGRESS);
//        if (getSipClientController() == null) {
//            try {
//                sipClientController = new ClientController();
//                sipClientController.createSipStack();
//                sipClientController.createSipFrameWork();
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);           
//            }//end exception
//        }//end sipclientcontroller == null
        if (jRadioButton1.isSelected()) {
            sendrequests(1, comb1SentMsgREG, comb1SentMsgINV, sipClientController);
        } else if (jRadioButton2.isSelected()) {
            sendrequests(2, comb2SentMsgREG, comb2SentMsgINV, sipClientController);
        } else if (jRadioButton3.isSelected()) {
            sendrequests(3, comb3SentMsgREG, comb3SentMsgINV, sipClientController);
        } else if (jRadioButton4.isSelected()) {
            sendrequests(4, comb4SentMsgREG, comb4SentMsgINV, sipClientController);
        }
    }//GEN-LAST:event_runALGtestActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
//     removed to avoid aobject already in used exception while doing reset
        resultmsgjlabel.setText(ALGBo.INPROGRESS);
        System.out.println("resetActionPerformed...");
//        if (getSipClientController() != null) {
//             System.out.println("resetActionPerformed...getSipClientController is null");
//            try {
//                String res = sipClientController.reset();
//                if(res.equalsIgnoreCase(ALGBo.RESET_OK)){
//                    resultmsgjlabel.setText(ALGBo.RESET_OK);
//                    sipClientController = null;
//                }else{
//                    resultmsgjlabel.setText("Reset failed: "+res);
//                    System.out.println("Reset failed! "+res);
//                           
//                    //TODO: clean well the Sip points
//                    //resultmsgjlabel.setText("Reset failed! "+res);
//                }   
//            } catch (Exception ex1) {
//                JOptionPane.showMessageDialog(this, ex1.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        }
        //resultmsgjlabel.setText("OK");
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
         resultmsgjlabel.setText(ALGBo.RESET_OK);

    }//GEN-LAST:event_resetActionPerformed

    private void formAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_formAncestorAdded
        // TODO add your handling code here:
        System.out.println("Event AncestorAdded...");
         if (getSipClientController() == null) {
             System.out.println("formAncestorAdded: Sip Listener null..initialise the stack");
            try {
                sipClientController = new ClientController();
                sipClientController.createSipStack();
                sipClientController.createSipFrameWork();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);           
            }//end exception
        }//end sipclientcontroller == null
    }//GEN-LAST:event_formAncestorAdded


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    public static javax.swing.JTextArea comb1RcvMsgINV;
    public static javax.swing.JTextArea comb1RcvMsgREG;
    private static javax.swing.JTextArea comb1SentMsgINV;
    public static javax.swing.JTextArea comb1SentMsgREG;
    public static javax.swing.JTextArea comb2RcvMsgINV;
    public static javax.swing.JTextArea comb2RcvMsgREG;
    public static javax.swing.JTextArea comb2SentMsgINV;
    public static javax.swing.JTextArea comb2SentMsgREG;
    public static javax.swing.JTextArea comb3RcvMsgINV;
    public static javax.swing.JTextArea comb3RcvMsgREG;
    public static javax.swing.JTextArea comb3SentMsgINV;
    public static javax.swing.JTextArea comb3SentMsgREG;
    public static javax.swing.JTextArea comb4RcvMsgINV;
    public static javax.swing.JTextArea comb4RcvMsgREG;
    public static javax.swing.JTextArea comb4SentMsgINV;
    public static javax.swing.JTextArea comb4SentMsgREG;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    public static javax.swing.JRadioButton jRadioButton1;
    public static javax.swing.JRadioButton jRadioButton2;
    public static javax.swing.JRadioButton jRadioButton3;
    public static javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane1Reg;
    private javax.swing.JTabbedPane jTabbedPane2Reg;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JTabbedPane jTabbedPane6;
    private javax.swing.JTabbedPane jTabbedPane7;
    private javax.swing.JTabbedPane jTabbedPane8;
    private javax.swing.JButton reset;
    public static javax.swing.JLabel resultmsgjlabel;
    private javax.swing.JButton runALGtest;
    // End of variables declaration//GEN-END:variables

    public void sendrequests(Integer combSeq, JTextArea sentmsgReg, JTextArea sentmsgInv, ClientController sipClientController) {
        Combination comb = null;
        if (combSeq == 1) {
            comb = new Combination(1, ALGBo.getPortsrc1(), ALGBo.getPortdest1(), ALGBo.getTransport1());
        } else if (combSeq == 2) {
            comb = new Combination(2, ALGBo.getPortsrc2(), ALGBo.getPortdest2(), ALGBo.getTransport2());
        } else if (combSeq == 3) {
            comb = new Combination(3, ALGBo.getPortsrc3(), ALGBo.getPortdest3(), ALGBo.getTransport3());
        } else if (combSeq == 4) {
            comb = new Combination(4, ALGBo.getPortsrc4(), ALGBo.getPortdest4(), ALGBo.getTransport4());
        }

        String resReg = sipClientController.sendRegisterStateful(comb);
        String resInv = sipClientController.sendInvite(comb);

        //filling the output log after sending the messeges
        //REG
        sentmsgReg.setText(resReg);
        //set the caret to the top always
        sentmsgReg.setCaretPosition(0);
        //INV
        sentmsgInv.setText(resInv);
        //set the caret to the top always
        sentmsgInv.setCaretPosition(0);
    }

}
