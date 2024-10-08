/*
 * BhaashikLanguagesJPanel.java
 *
 * Created on April 17, 2006, 8:46 PM
 */

package bhaashik.gui.common;

import javax.swing.*;
import java.awt.event.*;

import bhaashik.GlobalProperties;

/**
 *
 * @author  anil
 */
public class BhaashikLanguagesJPanel extends javax.swing.JPanel
	implements ActionListener
{
    protected String langEnc;

    protected DefaultComboBoxModel languages;
    protected DefaultComboBoxModel encodings;
    
    /** Creates new form BhaashikLanguagesJPanel */
    public BhaashikLanguagesJPanel() {
	initComponents();

	languages = new DefaultComboBoxModel();
	BhaashikLanguages.fillLanguages(languages);
	languageJComboBox.setModel(languages);

	encodings = new DefaultComboBoxModel();
	BhaashikLanguages.fillEncodings(encodings, GlobalProperties.getIntlString("utf8"));
	encodingJComboBox.setModel(encodings);

	languageJComboBox.addActionListener(this);
	encodingJComboBox.addActionListener(this);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        languageJPanel = new javax.swing.JPanel();
        languageJLabel = new javax.swing.JLabel();
        languageJComboBox = new javax.swing.JComboBox();
        encodingJLabel = new javax.swing.JLabel();
        encodingJComboBox = new javax.swing.JComboBox();

        setLayout(new java.awt.BorderLayout());

        languageJPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("bhaashik"); // NOI18N
        languageJLabel.setText(bundle.getString("Language:")); // NOI18N
        languageJPanel.add(languageJLabel);

        languageJPanel.add(languageJComboBox);

        encodingJLabel.setText(bundle.getString("__Encoding:")); // NOI18N
        languageJPanel.add(encodingJLabel);

        languageJPanel.add(encodingJComboBox);

        add(languageJPanel, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

    public void actionPerformed(ActionEvent ae) {
	updateLanguage(ae);
    }
       
    private void updateLanguage(ActionEvent ae)
    {
    } 
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JComboBox encodingJComboBox;
    protected javax.swing.JLabel encodingJLabel;
    protected javax.swing.JComboBox languageJComboBox;
    protected javax.swing.JLabel languageJLabel;
    protected javax.swing.JPanel languageJPanel;
    // End of variables declaration//GEN-END:variables
    
}
