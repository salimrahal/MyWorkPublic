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
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 *
 * @author salim
 */
public class AlgJPanel extends javax.swing.JPanel implements PropertyChangeListener {

    private TL tk;
    /**
     * Creates new form AlgJPanel
     */
    private static Cc cc;
    public String custnm;

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
        return custnm;
    }

    public void setCustnm(String custnm) {
        this.custnm = custnm;
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
        jPanel5 = new javax.swing.JPanel();
        jPanel6ALHHelp = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        reset = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();

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

        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
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
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTabbedPane1Reg, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addComponent(jTabbedPane2Reg, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jRadioButton1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
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
                .addContainerGap(148, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Test A", jPanel1);

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Transport: TCP Source Port: 5060 Destination Port: 5060");

        comb2SentMsgREG.setColumns(20);
        comb2SentMsgREG.setRows(5);
        comb2SentMsgREG.setText("Sent message:");
        jScrollPane11.setViewportView(comb2SentMsgREG);

        jTabbedPane3.addTab("Register", jScrollPane11);

        comb2SentMsgINV.setColumns(20);
        comb2SentMsgINV.setRows(5);
        comb2SentMsgINV.setText("Sent message:");
        jScrollPane3.setViewportView(comb2SentMsgINV);

        jTabbedPane3.addTab("Invite", jScrollPane3);

        comb2RcvMsgREG.setColumns(20);
        comb2RcvMsgREG.setRows(5);
        comb2RcvMsgREG.setText("Received message:");
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
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(145, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Test B", jPanel2);

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("Transport: UDP Source Port: 5062 Destination Port: 5060");

        comb3SentMsgREG.setColumns(20);
        comb3SentMsgREG.setRows(5);
        comb3SentMsgREG.setText("Sent message:");
        jScrollPane13.setViewportView(comb3SentMsgREG);

        jTabbedPane5.addTab("Register", jScrollPane13);

        comb3SentMsgINV.setColumns(20);
        comb3SentMsgINV.setRows(5);
        comb3SentMsgINV.setText("Sent message:");
        jScrollPane14.setViewportView(comb3SentMsgINV);

        jTabbedPane5.addTab("Invite", jScrollPane14);

        comb3RcvMsgREG.setColumns(20);
        comb3RcvMsgREG.setRows(5);
        comb3RcvMsgREG.setText("Received message:");
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
                        .addComponent(jTabbedPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTabbedPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButton3)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTabbedPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(142, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Test C", jPanel3);

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
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jTabbedPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(jTabbedPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jRadioButton4)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jRadioButton4)
                        .addGap(18, 18, 18)
                        .addComponent(jTabbedPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(141, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Test D", jPanel4);

        jPanel6ALHHelp.setBorder(new javax.swing.border.MatteBorder(null));

        jScrollPane5.setAlignmentX(0.0F);
        jScrollPane5.setAlignmentY(0.0F);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Purpose of this test:\nSIP-ALG is a software implemented in routers, it may interrupts the VOIP call. When a SIP-ALG is detected during this test, please check your router or Internet Service Provider.\n\nYou may be affected by SIP-ALG if you run into these scenarios:\n  -One way audio on calls\n  -No audio\n  -Phones dropping registration\n  -Callers go straight to voicemail for no known reason\n  -Random error messages when your number is called (e.g. “The caller you have dialed is no longer in service”)\n\nSIP-ALG - Overview:\n\nMany of today's commercial routers implement SIP ALG (Application-level gateway), coming with this feature enabled by default.\n While ALG could help in solving NAT related problems, the fact is that many routers' ALG implementations are wrong and break SIP.\nThere are various solutions for SIP clients behind NAT, some of them in client side (STUN, TURN, ICE), others in server side (Proxy RTP as RtpProxy, MediaProxy). \nALG works typically in the client LAN router or gateway. In some scenarios some client side solutions are not valid, for example STUN with symmetrical NAT router.\n If the SIP proxy doesn't provide a server side NAT solution, then an ALG solution could have a place.\n\nAn ALG understands the protocol used by the specific applications that it supports (in this case SIP) and does a protocol packet-inspection of traffic through it. \nA NAT router with a built-in SIP ALG can re-write information within the SIP messages (SIP headers and SDP body) making signaling \nand audio traffic between the client behind NAT and the SIP endpoint possible.");
        jScrollPane5.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel6ALHHelpLayout = new javax.swing.GroupLayout(jPanel6ALHHelp);
        jPanel6ALHHelp.setLayout(jPanel6ALHHelpLayout);
        jPanel6ALHHelpLayout.setHorizontalGroup(
            jPanel6ALHHelpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6ALHHelpLayout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1017, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6ALHHelpLayout.setVerticalGroup(
            jPanel6ALHHelpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6ALHHelpLayout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6ALHHelp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6ALHHelp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Why ALG Detection?", jPanel5);

        reset.setText("Reset");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });

        jProgressBar1.setStringPainted(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(runALGButton)
                                .addGap(95, 95, 95)
                                .addComponent(reset)
                                .addGap(84, 84, 84))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addGap(140, 140, 140))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(resultmsgjlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jProgressBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1))
        );

        resultmsgjlabel.getAccessibleContext().setAccessibleName("<html>Critical Error : SIP ALG is corrupting SIP Messages, Please disable SIP ALG in the router<html>");
    }// </editor-fold>//GEN-END:initComponents

    private void runALGButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runALGButtonActionPerformed

        runALGButton.setEnabled(false);
        reset.setEnabled(false);

        tk = new TL();
        tk.addPropertyChangeListener(this);
        tk.execute();
        //System.out.println("runALGButtonActionPerformed initializing progress bar .. ");
        jProgressBar1.setValue(0);

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

        //update the test parameters 
        updateTestLabels();
    }//GEN-LAST:event_formAncestorAdded

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked

        // get the currently selected index for this tabbedpan 0, 1, 2 ,3 and then select the correspondent radio button
        int selectedIndex = jTabbedPane1.getSelectedIndex();
        //System.out.println(selectedIndex);
        switch (selectedIndex) {
            case 0:
                //select the first combination or first radiobutton
                jRadioButton1.setSelected(true);
                break;
            case 1:
                //select the 2nd combination or first radiobutton                 
                jRadioButton2.setSelected(true);
                break;
            case 2:
                jRadioButton3.setSelected(true);
                break;
            case 3:
                //select the 4th combination or first radiobutton       
                jRadioButton4.setSelected(true);
                break;
        }
    }//GEN-LAST:event_jTabbedPane1MouseClicked


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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6ALHHelp;
    public javax.swing.JProgressBar jProgressBar1;
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
    private javax.swing.JScrollPane jScrollPane5;
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
    private javax.swing.JTextArea jTextArea1;
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
            cc.preprreq(comb, sentmsgReg, recvjtextregister, sentmsgInv, recvjtextinvite, custnm);

        } catch (IOException ex) {
            Logger.getLogger(AlgJPanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void updateTestLabels() {
        //update the test parameters 
        String testA;

        testA = "No Parameters have been found!";
        Alb algBo = getCc().getAlgBo();
        if (algBo.getPortsrc1() != null && algBo.getTransport1() != null && algBo.getPortdest1() != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Transport: ").append(algBo.getTransport1()).append(" Source Port: ").append(algBo.getPortsrc1()).append(" Destination Port: ").append(algBo.getPortdest1());
            testA = sb.toString();
        }
        jRadioButton1.setText(testA);

        String testB;

        testB = "No Parameters have been found!";
        if (algBo.getPortsrc2() != null && algBo.getTransport2() != null && algBo.getPortdest2() != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Transport: ").append(algBo.getTransport2()).append(" Source Port: ").append(algBo.getPortsrc2()).append(" Destination Port: ").append(algBo.getPortdest2());
            testB = sb.toString();
        }
        jRadioButton2.setText(testB);

        String testC;
        //assign default value
        testC = "No Parameters have been found!";
        if (algBo.getPortsrc3() != null && algBo.getTransport3() != null && algBo.getPortdest3() != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Transport: ").append(algBo.getTransport3()).append(" Source Port: ").append(algBo.getPortsrc3()).append(" Destination Port: ").append(algBo.getPortdest3());
            testC = sb.toString();

        }
        jRadioButton3.setText(testC);

        String testD;
        //assign default value
        testD = "No Parameters have been found!";
        if (algBo.getPortsrc4() != null && algBo.getTransport4() != null && algBo.getPortdest4() != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Transport: ").append(algBo.getTransport4()).append(" Source Port: ").append(algBo.getPortsrc4()).append(" Destination Port: ").append(algBo.getPortdest4());
            testD = sb.toString();
        }
        jRadioButton4.setText(testD);
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
            //udp
            if (jRadioButton1.isSelected()) {
                setProgress(50);
                sr(1, comb1SentMsgREG, comb1SentMsgINV, cc);
            } //tcp
            else if (jRadioButton2.isSelected()) {
                setProgress(50);
                sr(2, comb2SentMsgREG, comb2SentMsgINV, cc);
            } //udp
            else if (jRadioButton3.isSelected()) {
                setProgress(50);
                sr(3, comb3SentMsgREG, comb3SentMsgINV, cc);
            } //tcp
            else if (jRadioButton4.isSelected()) {
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
