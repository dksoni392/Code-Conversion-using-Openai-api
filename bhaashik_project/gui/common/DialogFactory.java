/*
 * DialogFactory.java
 *
 * Created on May 20, 2006, 3:19 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.gui.common;

import java.awt.*;
import java.io.File;
import javax.swing.*;

import bhaashik.corpus.ssf.gui.CorpusStatisticsJPanel;
import bhaashik.corpus.ssf.gui.HierarchicalTagsJPanel;
import bhaashik.corpus.ssf.gui.SSFAnnotationLevelsJPanel;
import bhaashik.table.gui.BhaashikTableJPanel;
import bhaashik.text.spell.gui.DictionaryFSTJPanel;
import bhaashik.util.gui.MNReadJPanel;
import bhaashik.util.gui.RegexOptionsJPanel;
import bhaashik.db.gui.ConnectToDBJPanel;
import bhaashik.table.BhaashikTableModel;

import bhaashik.util.UtilityFunctions;

/**
 *
 * @author Anil Kumar Singh
 */
public class DialogFactory {
    
    /** Creates a new instance of DialogFactory */
    public static BhaashikJDialog showDialog(JPanel panel, Frame owner, String title, boolean modal)
    {
	BhaashikJDialog dialog = new BhaashikJDialog(owner, title, modal, (JPanelDialog) panel);
        ((JPanelDialog) panel).setDialog(dialog);

	dialog.pack();

	UtilityFunctions.centre(dialog);

	dialog.setVisible(true);

	return dialog;
    }

    public static BhaashikJDialog showDialog(Class cl, Frame owner, String title, boolean modal)
    {
	JPanel panel = createJPanel(cl);
	BhaashikJDialog dialog = new BhaashikJDialog(owner, title, modal, (JPanelDialog) panel);
        ((JPanelDialog) panel).setDialog(dialog);
	
	dialog.pack();
	
	UtilityFunctions.centre(dialog);

	dialog.setVisible(true);
	
	return dialog;
    }
    
    public static BhaashikJDialog showDialog(Class cl, Dialog owner, String title, boolean modal)
    {
	JPanel panel = createJPanel(cl);
	BhaashikJDialog dialog = new BhaashikJDialog(owner, title, modal, (JPanelDialog) panel);
        ((JPanelDialog) panel).setDialog(dialog);
	
	dialog.pack();
	
	UtilityFunctions.centre(dialog);

	dialog.setVisible(true);
	
	return dialog;
    }

    public static BhaashikJDialog showDialog(Class cl, Frame owner, String title, boolean modal, String langEnc, String charset)
    {
	JPanel panel = createJPanel(cl, langEnc, charset);
	BhaashikJDialog dialog = new BhaashikJDialog(owner, title, modal, (JPanelDialog) panel);
        ((JPanelDialog) panel).setDialog(dialog);

	dialog.pack();

	UtilityFunctions.centre(dialog);

	dialog.setVisible(true);

	return dialog;
    }

    public static BhaashikJDialog showDialog(Class cl, Dialog owner, String title, boolean modal, String langEnc, String charset)
    {
	JPanel panel = createJPanel(cl, langEnc, charset);
	BhaashikJDialog dialog = new BhaashikJDialog(owner, title, modal, (JPanelDialog) panel);
        ((JPanelDialog) panel).setDialog(dialog);

	dialog.pack();

	UtilityFunctions.centre(dialog);

	dialog.setVisible(true);

	return dialog;
    }

    public static BhaashikJDialog showTableDialog(Dialog owner, String title, boolean modal, BhaashikTableModel model, String langEnc, int mode)
    {
        BhaashikJDialog diag = createTableDialog(owner, title, modal, model, langEnc, mode);
        diag.setVisible(true);
        return diag;
    }

    public static BhaashikJDialog createTableDialog(Dialog owner, String title, boolean modal, BhaashikTableModel model, String langEnc, int mode)
    {
	JPanel panel = new BhaashikTableJPanel(model, true, null, mode, langEnc);
	BhaashikJDialog dialog = new BhaashikJDialog(owner, title, modal, (JPanelDialog) panel);
        ((JPanelDialog) panel).setDialog(dialog);
        
        ((BhaashikTableJPanel) panel).getJTable().setPreferredScrollableViewportSize(new Dimension(700, 220));
	
	dialog.pack();
	
	UtilityFunctions.centre(dialog);
	
	return dialog;
    }

    public static BhaashikJDialog showTableDialog(Frame owner, String title, boolean modal, BhaashikTableModel model, String langEnc, int mode)
    {
        BhaashikJDialog diag = createTableDialog(owner, title, modal, model, langEnc, mode);
        diag.setVisible(true);
        return diag;
    }

    public static BhaashikJDialog createTableDialog(Frame owner, String title, boolean modal, BhaashikTableModel model, String langEnc, int mode)
    {
	JPanel panel = new BhaashikTableJPanel(model, true, null, mode, langEnc);
	BhaashikJDialog dialog = new BhaashikJDialog(owner, title, modal, (JPanelDialog) panel);
        ((JPanelDialog) panel).setDialog(dialog);
        
        ((BhaashikTableJPanel) panel).getJTable().setPreferredScrollableViewportSize(new Dimension(700, 220));
	
	dialog.pack();
	
	UtilityFunctions.centre(dialog);
	
	return dialog;        
    }

    public static BhaashikJDialog showFileSelectionDialog(Dialog owner, String title, boolean modal, File curDir)
    {
        FileSelectionJPanel panel = (FileSelectionJPanel) createJPanel(FileSelectionJPanel.class);

        panel.setCurrentDir(curDir);

        BhaashikJDialog dialog = new BhaashikJDialog(owner, title, modal, (JPanelDialog) panel);
        ((JPanelDialog) panel).setDialog(dialog);

	dialog.pack();

	UtilityFunctions.centre(dialog);

	dialog.setVisible(true);

	return dialog;
    }

    public static BhaashikJDialog showFileSelectionDialog(Frame owner, String title, boolean modal, File curDir)
    {
        FileSelectionJPanel panel = (FileSelectionJPanel) createJPanel(FileSelectionJPanel.class);

        panel.setCurrentDir(curDir);

        BhaashikJDialog dialog = new BhaashikJDialog(owner, title, modal, (JPanelDialog) panel);
        ((JPanelDialog) panel).setDialog(dialog);

	dialog.pack();

	UtilityFunctions.centre(dialog);

	dialog.setVisible(true);

	return dialog;
    }

    public static JPanel createJPanel(Class cl)
    {
	if(cl.equals(RegexOptionsJPanel.class))
	    return new RegexOptionsJPanel();
	else if(cl.equals(SSFAnnotationLevelsJPanel.class))
	    return new SSFAnnotationLevelsJPanel();
	else if(cl.equals(MNReadJPanel.class))
	    return new MNReadJPanel();
	else if(cl.equals(GenerateTasksJPanel.class))
	    return new GenerateTasksJPanel();
	else if(cl.equals(FileSelectionJPanel.class))
	    return new FileSelectionJPanel();
//	else if(cl.equals(BoardJPanel.class))
//	    return new BoardJPanel();
//	else if(cl.equals(UlatPalatKeJoRoCustomizeJPanel.class))
//	    return new UlatPalatKeJoRoCustomizeJPanel();
	else if(cl.equals(DictionaryFSTJPanel.class))
	    return new DictionaryFSTJPanel();
//	else if(cl.equals(PuzzleSelectionJPanel.class))
//	    return new PuzzleSelectionJPanel("hin:utf8", JoRoConstants.PROD_ULAT_PALAT_KE);
	else if(cl.equals(ConnectToDBJPanel.class))
	    return new ConnectToDBJPanel();
	else if(cl.equals(LocalKeyboardShorcutEditorJPanel.class))
	    return new LocalKeyboardShorcutEditorJPanel(new BhaashikTableModel(0, 3));
	else if(cl.equals(KeystrokeEditorJPanel.class))
	    return new KeystrokeEditorJPanel();
	else if(cl.equals(CorpusStatisticsJPanel.class))
	    return new CorpusStatisticsJPanel();
	else if(cl.equals(HierarchicalTagsJPanel.class))
	    return new HierarchicalTagsJPanel("hin::utf8", "UTF-8", false);
	
	return null;
    }

    public static JPanel createJPanel(Class cl, String langEnc, String charset)
    {
	if(cl.equals(RegexOptionsJPanel.class))
	    return new RegexOptionsJPanel();
	else if(cl.equals(SSFAnnotationLevelsJPanel.class))
	    return new SSFAnnotationLevelsJPanel();
	else if(cl.equals(MNReadJPanel.class))
	    return new MNReadJPanel();
	else if(cl.equals(GenerateTasksJPanel.class))
	    return new GenerateTasksJPanel();
	else if(cl.equals(FileSelectionJPanel.class))
	    return new FileSelectionJPanel();
//	else if(cl.equals(BoardJPanel.class))
//	    return new BoardJPanel();
//	else if(cl.equals(UlatPalatKeJoRoCustomizeJPanel.class))
//	    return new UlatPalatKeJoRoCustomizeJPanel();
	else if(cl.equals(DictionaryFSTJPanel.class))
	    return new DictionaryFSTJPanel();
//	else if(cl.equals(PuzzleSelectionJPanel.class))
//	    return new PuzzleSelectionJPanel("hin:utf8", JoRoConstants.PROD_ULAT_PALAT_KE);
	else if(cl.equals(ConnectToDBJPanel.class))
	    return new ConnectToDBJPanel();
	else if(cl.equals(LocalKeyboardShorcutEditorJPanel.class))
	    return new LocalKeyboardShorcutEditorJPanel(new BhaashikTableModel(0, 3));
	else if(cl.equals(KeystrokeEditorJPanel.class))
	    return new KeystrokeEditorJPanel();
	else if(cl.equals(CorpusStatisticsJPanel.class))
	    return new CorpusStatisticsJPanel();
	else if(cl.equals(HierarchicalTagsJPanel.class))
	    return new HierarchicalTagsJPanel(langEnc, charset, false);

	return null;
    }
}
