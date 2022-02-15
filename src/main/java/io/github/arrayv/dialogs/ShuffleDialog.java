package io.github.arrayv.dialogs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import io.github.arrayv.frames.AppFrame;
import io.github.arrayv.main.ArrayManager;
import io.github.arrayv.panels.ShufflePanel;
import io.github.arrayv.panes.JErrorPane;
import utils.Distributions;
import utils.ShuffleGraph;
import utils.ShuffleInfo;
import utils.Shuffles;
import utils.shuffleutils.GraphReader;
import utils.shuffleutils.GraphReader.MalformedGraphFileException;
import utils.shuffleutils.GraphWriter;

/*
 *
MIT License

Copyright (c) 2019 w0rthy
Copyright (c) 2021-2022 ArrayV Team

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 *
 */

public final class ShuffleDialog extends javax.swing.JDialog implements AppFrame {
    private static final long serialVersionUID = 1L;

    private ArrayManager ArrayManager;
    private List<Distributions> distributions;
    private static boolean perShuffleDelay = false;

    private boolean bypassEvents;

    /**
     * Creates new form SortPrompt
     */
    @SuppressWarnings("unchecked")
    public ShuffleDialog(ArrayManager ArrayManager, JFrame frame) {
        super(frame, "ArrayV Advanced Shuffle Editor", true);

        this.ArrayManager = ArrayManager;

        initComponents();

        bypassEvents = true;
        this.shuffleEditor.graph = ArrayManager.getShuffle();
        jList4.setListData(ArrayManager.getDistributionIDs());
        for (int i = 0; i < ArrayManager.getDistributions().length; i++) {
            if (ArrayManager.getDistribution().equals(ArrayManager.getDistributions()[i])) {
                jList4.setSelectedIndex(i);
                break;
            }
        }

        distributions = Arrays.stream(ArrayManager.getDistributions())
                              .filter(dist -> dist.getName() != "Custom")
                              .collect(Collectors.toList());
        Object[] distributionNames = distributions.stream()
                                                  .map(Distributions::getName)
                                                  .collect(Collectors.toList())
                                                  .toArray();
        jList1.setListData(distributionNames);
        jList3.setListData(distributionNames);

        jList2.setListData(ArrayManager.getShuffleIDs());

        jTextField1.setText(Double.toString(
            perShuffleDelay ?
            shuffleEditor.graph.sleepRatio / shuffleEditor.graph.size() :
            shuffleEditor.graph.sleepRatio
        ));
        jCheckBox1.setSelected(perShuffleDelay);
        bypassEvents = false;

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (perShuffleDelay = jCheckBox1.isSelected()) {
                    shuffleEditor.graph.sleepRatio *= shuffleEditor.graph.size();
                }
            }
        });

        setMinimumSize(new Dimension(765, 310));
        setAlwaysOnTop(false);
        reposition();
        setVisible(true);
    }

    @Override
    public void reposition() {
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings({ "rawtypes" })
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        this.shuffleEditor = new ShufflePanel();

        this.jButton1 = new javax.swing.JButton();
        this.jButton2 = new javax.swing.JButton();
        this.jButton3 = new javax.swing.JButton();

        this.jTextField1 = new javax.swing.JTextField();
        this.jLabel5 = new javax.swing.JLabel();
        this.jCheckBox1 = new javax.swing.JCheckBox();

        this.jScrollPane4 = new javax.swing.JScrollPane();
        this.jList4 = new javax.swing.JList();
        this.jLabel4 = new javax.swing.JLabel();

        this.jScrollPane1 = new javax.swing.JScrollPane();
        this.jList1 = new javax.swing.JList();
        this.jLabel1 = new javax.swing.JLabel();

        this.jScrollPane3 = new javax.swing.JScrollPane();
        this.jList3 = new javax.swing.JList();
        this.jLabel3 = new javax.swing.JLabel();

        this.jScrollPane2 = new javax.swing.JScrollPane();
        this.jList2 = new javax.swing.JList();
        this.jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButton1.setText("Import...");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed();
            }
        });

        jButton2.setText("Export...");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed();
            }
        });

        jButton3.setText("Clear Disconnected Nodes");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed();
            }
        });

        jLabel5.setText("Sleep Ratio");

        jTextField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                jTextField1TextChanged(e);
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                jTextField1TextChanged(e);
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                jTextField1TextChanged(e);
            }
        });

        jCheckBox1.setText("Time per sub-shuffle");

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setViewportView(this.jList4);

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setViewportView(this.jList1);

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setViewportView(this.jList3);

        jScrollPane2.setViewportView(this.jList2);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jList4.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                try {
                    jList4ValueChanged(evt);
                } catch (Exception e) {
                    JErrorPane.invokeErrorMessage(e);
                }
            }
        });

        jLabel4.setText("Base Distribution");

        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                try {
                    jList1ValueChanged(evt);
                } catch (Exception e) {
                    JErrorPane.invokeErrorMessage(e);
                }
            }
        });

        jLabel1.setText("Distribution Stretch");

        jList3.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                try {
                    jList3ValueChanged(evt);
                } catch (Exception e) {
                    JErrorPane.invokeErrorMessage(e);
                }
            }
        });

        jLabel3.setText("Distribution Warp");

        jList2.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                try {
                    jList2ValueChanged(evt);
                } catch (Exception e) {
                    JErrorPane.invokeErrorMessage(e);
                }
            }
        });

        jLabel2.setText("Shuffle");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(this.jLabel4)
                    .addComponent(this.jScrollPane4, 175, 175, 175)
                    .addComponent(this.jButton3)
                    .addComponent(this.jLabel5)
                    .addComponent(this.jTextField1)
                    .addComponent(this.jCheckBox1))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(this.shuffleEditor)
                        .addGap(10, 10, 10))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 75, 75)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addComponent(this.jLabel1)
                            .addComponent(this.jScrollPane1, 175, 175, 175))
                        .addGap(10, 75, 75)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addComponent(this.jLabel3)
                            .addComponent(this.jScrollPane3, 175, 175, 175))
                        .addGap(10, 75, 75)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addComponent(this.jLabel2)
                            .addComponent(this.jScrollPane2, 175, 175, 175))
                        .addGap(10, 75, 75))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(this.jButton1)
                        .addGap(20, 20, 20)
                        .addComponent(this.jButton2)
                        .addGap(150, 150, 150)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(10, 30, 30)
                    .addComponent(this.jLabel4)
                    .addComponent(this.jScrollPane4, 175, 175, 175)
                    .addGap(20, 20, 20)
                    .addComponent(this.jButton3)
                    .addGap(20, 20, 20)
                    .addComponent(this.jLabel5)
                    .addComponent(this.jTextField1, 20, 20, 20)
                    .addComponent(this.jCheckBox1))
                .addGroup(layout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addComponent(this.shuffleEditor)
                    .addGap(10, 10, 10)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(this.jLabel1)
                            .addComponent(this.jScrollPane1, 175, 175, 175))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(this.jLabel3)
                            .addComponent(this.jScrollPane3, 175, 175, 175))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(this.jLabel2)
                            .addComponent(this.jScrollPane2, 175, 175, 175)))
                    .addGap(10, 10, 10)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(this.jButton1)
                        .addComponent(this.jButton2))
                    .addGap(10, 10, 10))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed() {//GEN-FIRST:event_jButton1ActionPerformed
        FileDialog fileDialog = new ImportShuffleDialog();
        ShuffleGraph newShuffle;
        try {
            newShuffle = new GraphReader().read(fileDialog.file);
        } catch (IOException e) {
            e.printStackTrace();
            JErrorPane.invokeCustomErrorMessage("IO Error: " + e.getMessage());
            return;
        } catch (MalformedGraphFileException e) {
            e.printStackTrace();
            JErrorPane.invokeCustomErrorMessage("Error Parsing File: " + e.getMessage());
            return;
        }
        ArrayManager.setShuffle(newShuffle);
        if (jCheckBox1.isSelected()) {
            shuffleEditor.graph.sleepRatio /= shuffleEditor.graph.size();
        }
        jTextField1.setForeground(Color.BLACK);
        jTextField1.setText(Double.toString(newShuffle.sleepRatio));
        this.shuffleEditor.graph = newShuffle;
        this.shuffleEditor.repaint();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed() {//GEN-FIRST:event_jButton1ActionPerformed
        FileDialog fileDialog = new ExportShuffleDialog();
        double oldSleepRatio = shuffleEditor.graph.sleepRatio;
        if (jCheckBox1.isSelected()) {
            shuffleEditor.graph.sleepRatio *= shuffleEditor.graph.size();
        }
        try {
            new GraphWriter(shuffleEditor.graph).write(fileDialog.file);
        } catch (IOException e) {
            shuffleEditor.graph.sleepRatio = oldSleepRatio;
            e.printStackTrace();
            JErrorPane.invokeCustomErrorMessage("IO Error: " + e.getMessage());
            return;
        }
        shuffleEditor.graph.sleepRatio = oldSleepRatio;
        JOptionPane.showMessageDialog(null,
            "Successfully exported current shuffle to file \"" + fileDialog.file.getAbsolutePath() + "\"",
            "Advanced Shuffle Editor", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed() {//GEN-FIRST:event_jButton1ActionPerformed
        shuffleEditor.graph.removeAllDisconnected();
        shuffleEditor.repaint();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1TextChanged(DocumentEvent e) {//GEN-FIRST:event_jList1ValueChanged
        String text = jTextField1.getText();
        if (text.length() == 0) return;
        double sleepRatio;
        try {
            sleepRatio = Double.parseDouble(text);
        } catch (NumberFormatException ex) {
            jTextField1.setForeground(new Color(204, 0, 0));
            return;
        }
        jTextField1.setForeground(Color.BLACK);
        shuffleEditor.graph.sleepRatio = sleepRatio;
    }//GEN-LAST:event_jList1ValueChanged

    private void addToGraph(ShuffleInfo shuffle) {
        Point safePos = shuffleEditor.graph.findSafeCoordinates(100, 100, 20, 20);
        shuffleEditor.graph.addDisconnected(shuffle, safePos.x, safePos.y);
    }

    private void jList4ValueChanged(javax.swing.event.ListSelectionEvent evt) throws Exception {//GEN-FIRST:event_jList1ValueChanged
        if (bypassEvents)
            return;
        int selection = jList4.getSelectedIndex();
        Distributions[] distributions = ArrayManager.getDistributions();
        if (selection >= 0 && selection < distributions.length)
            ArrayManager.setDistribution(distributions[selection]);
    }//GEN-LAST:event_jList1ValueChanged

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) throws Exception {//GEN-FIRST:event_jList1ValueChanged
        if (bypassEvents)
            return;
        String selection = (String)jList1.getSelectedValue();
        Distributions distribution = distributions.stream()
                                                  .filter(d -> d.getName().equals(selection))
                                                  .findFirst()
                                                  .orElse(null);
        if (distribution != null)
            addToGraph(new ShuffleInfo(distribution, false));
        shuffleEditor.repaint();
        bypassEvents = true;
        jList1.clearSelection();
        bypassEvents = false;
    }//GEN-LAST:event_jList1ValueChanged

    private void jList3ValueChanged(javax.swing.event.ListSelectionEvent evt) throws Exception {//GEN-FIRST:event_jList1ValueChanged
        if (bypassEvents)
            return;
        String selection = (String)jList3.getSelectedValue();
        Distributions distribution = distributions.stream()
                                                  .filter(d -> d.getName().equals(selection))
                                                  .findFirst()
                                                  .orElse(null);
        if (distribution != null)
            addToGraph(new ShuffleInfo(distribution, true));
        shuffleEditor.repaint();
        bypassEvents = true;
        jList3.clearSelection();
        bypassEvents = false;
    }//GEN-LAST:event_jList1ValueChanged

    private void jList2ValueChanged(javax.swing.event.ListSelectionEvent evt) throws Exception {//GEN-FIRST:event_jList1ValueChanged
        if (bypassEvents)
            return;
        int selection = jList2.getSelectedIndex();
        Shuffles[] shuffles = ArrayManager.getShuffles();
        if (selection >= 0 && selection < shuffles.length)
            addToGraph(new ShuffleInfo(shuffles[selection]));
        shuffleEditor.repaint();
        bypassEvents = true;
        jList2.clearSelection();
        bypassEvents = false;
    }//GEN-LAST:event_jList1ValueChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ShufflePanel shuffleEditor;

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;

    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JCheckBox jCheckBox1;

    @SuppressWarnings("rawtypes")
    private javax.swing.JList jList4;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel jLabel4;

    @SuppressWarnings("rawtypes")
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jLabel1;

    @SuppressWarnings("rawtypes")
    private javax.swing.JList jList3;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel jLabel3;

    @SuppressWarnings("rawtypes")
    private javax.swing.JList jList2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
