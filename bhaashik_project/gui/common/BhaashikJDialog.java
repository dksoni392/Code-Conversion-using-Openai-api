/*
 * BhaashikJDialog.java
 *
 * Created on May 20, 2006, 5:53 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.gui.common;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Anil Kumar Singh
 */
public class BhaashikJDialog extends JDialog
{
    protected JPanel panel;

    public BhaashikJDialog()
    {
	super();
    }

    public BhaashikJDialog(Dialog owner, String title, boolean modal)
    {
	super(owner, title, modal);
    }

    public BhaashikJDialog(Dialog owner, String title, boolean modal, JPanelDialog pnl)
    {
	super(owner, title, modal);
	pnl.setDialog(this);
	panel = (JPanel) pnl;
	add(panel);
    }

    public BhaashikJDialog(Frame owner, String title, boolean modal, JPanelDialog pnl)
    {
	super(owner, title, modal);
	pnl.setDialog(this);
	panel = (JPanel) pnl;
	add(panel);
    }

    public JPanel getJPanel()
    {
	return panel;
    }
};
