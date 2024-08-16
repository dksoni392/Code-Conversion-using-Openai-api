/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SyntacticAnnotationValidationJPanel.java
 *
 * Created on 18 Nov, 2010, 6:38:05 PM
 */

package bhaashik.corpus.validation;

import java.awt.Frame;
import java.io.UnsupportedEncodingException;

import bhaashik.corpus.ssf.gui.SyntacticAnnotationWorkJPanel;
import bhaashik.corpus.ssf.impl.SSFStoryImpl;
import bhaashik.corpus.validation.rule.ValidationRuleBased;
import bhaashik.corpus.validation.sanity.SanityCheckAll_1;
import bhaashik.common.BhaashikClientsStateData;
import bhaashik.corpus.ssf.SSFStory;
import bhaashik.gui.common.ExitListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import bhaashik.common.types.ClientType;
import bhaashik.properties.KeyValueProperties;
import bhaashik.table.BhaashikTableModel;

/**
 *
 * @author ambati
 */
public class SyntacticAnnotationValidationJPanel extends javax.swing.JPanel {

    private JFrame owner;
    private JDialog dialog;
    protected String inDir;
    protected boolean typeSanity;
    protected boolean typeRules;
    protected boolean typeStats;
    protected boolean levelPOS;
    protected boolean levelChunk;
    protected boolean levelMorph;
    protected boolean levelDep;
    protected boolean levelOthers;
    protected String execFile;
    protected BhaashikTableModel matchesFinal;
    protected List filesList;
    protected LinkedHashMap<File, SSFStory> selStories;
    protected String langEnc;
    protected String charset;
    protected SanityCheckAll_1 sca;
    protected SyntacticAnnotationWorkJPanel workJPanel;

    /** Creates new form SyntacticAnnotationValidationJPanel */
    public SyntacticAnnotationValidationJPanel() {
        initComponents();
        SetVariables();
    }

    public SyntacticAnnotationValidationJPanel(SyntacticAnnotationWorkJPanel workJPanel) {
        
        this.workJPanel = workJPanel;
    }

    public Frame getOwner()
    {
        return owner;
    }

    public void setOwner(Frame f)
    {
        owner = (JFrame) f;
    }

    public JDialog getDialog()
    {
        return dialog;
    }

    public void setDialog(JDialog diag)
    {
        dialog = diag;
    }

    private void SetVariables()
    {
        inDir = "";
        typeSanity = false;
        typeRules = true;
        typeStats = false;
        levelPOS = true;
        levelChunk = true;
        levelMorph = true;
        levelDep = true;
        levelOthers = true;
        execFile = "";
        matchesFinal = null;
        //matchesFinal = new BhaashikTableModel(new String[]{"Sentence", "Matched Node", "Context", "Referred Node", "File", "Comment"}, 2);
        filesList = new ArrayList();

        valrulejCheckBox.setSelected(true);
        posjCheckBox.setSelected(true);
        chunkjCheckBox.setSelected(true);
        morphjCheckBox.setSelected(true);
        depjCheckBox.setSelected(true);
        othersjCheckBox.setSelected(true);
        langEnc="hin";
        charset="UTF-8";
    }

    public void setLangEnc(String enc)
    {
        langEnc = enc;
    }

    public void setCharSet(String value)
    {
        charset = value;
    }

    public BhaashikTableModel getTableModel()
    {
        
        return matchesFinal;
    }

    public LinkedHashMap getSelFilesMap()
    {
        return selStories;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        validationjPanel = new javax.swing.JPanel();
        inDirjPanel = new javax.swing.JPanel();
        inDirjLabel = new javax.swing.JLabel();
        inDirjTextField = new javax.swing.JTextField();
        inDirjButton = new javax.swing.JButton();
        selectvaljPanel = new javax.swing.JPanel();
        valtypejPanel = new javax.swing.JPanel();
        sanityjCheckBox = new javax.swing.JCheckBox();
        valrulejCheckBox = new javax.swing.JCheckBox();
        valstatjCheckBox = new javax.swing.JCheckBox();
        valleveljPanel = new javax.swing.JPanel();
        posjCheckBox = new javax.swing.JCheckBox();
        chunkjCheckBox = new javax.swing.JCheckBox();
        morphjCheckBox = new javax.swing.JCheckBox();
        depjCheckBox = new javax.swing.JCheckBox();
        othersjCheckBox = new javax.swing.JCheckBox();
        runjPanel = new javax.swing.JPanel();
        executejButton = new javax.swing.JButton();
        executedjPanel = new javax.swing.JPanel();
        execDirjPanel = new javax.swing.JPanel();
        execfilejLabel = new javax.swing.JLabel();
        execfilejTextField = new javax.swing.JTextField();
        execfilejButton = new javax.swing.JButton();
        execrunjPanel = new javax.swing.JPanel();
        execrunjButton = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        validationjPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Validate The Files"));
        validationjPanel.setLayout(new java.awt.BorderLayout());

        inDirjPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Input"));
        inDirjPanel.setLayout(new javax.swing.BoxLayout(inDirjPanel, javax.swing.BoxLayout.LINE_AXIS));

        inDirjLabel.setText("Input Directory: ");
        inDirjPanel.add(inDirjLabel);

        inDirjTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inDirjTextFieldActionPerformed(evt);
            }
        });
        inDirjPanel.add(inDirjTextField);

        inDirjButton.setText("Browse");
        inDirjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inDirjButtonActionPerformed(evt);
            }
        });
        inDirjPanel.add(inDirjButton);

        validationjPanel.add(inDirjPanel, java.awt.BorderLayout.PAGE_START);

        selectvaljPanel.setBorder(null);
        selectvaljPanel.setLayout(new java.awt.BorderLayout());

        valtypejPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Validation Type"));
        valtypejPanel.setLayout(new javax.swing.BoxLayout(valtypejPanel, javax.swing.BoxLayout.LINE_AXIS));

        sanityjCheckBox.setText("Sanity Checks");
        sanityjCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sanityjCheckBoxActionPerformed(evt);
            }
        });
        valtypejPanel.add(sanityjCheckBox);

        valrulejCheckBox.setText("Validation (Rules)");
        valrulejCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valrulejCheckBoxActionPerformed(evt);
            }
        });
        valtypejPanel.add(valrulejCheckBox);

        valstatjCheckBox.setText("Validation (Stats)");
        valtypejPanel.add(valstatjCheckBox);

        selectvaljPanel.add(valtypejPanel, java.awt.BorderLayout.PAGE_START);

        valleveljPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Validation Level"));
        valleveljPanel.setLayout(new javax.swing.BoxLayout(valleveljPanel, javax.swing.BoxLayout.LINE_AXIS));

        posjCheckBox.setText("POS  ");
        valleveljPanel.add(posjCheckBox);

        chunkjCheckBox.setText("Chunk  ");
        valleveljPanel.add(chunkjCheckBox);

        morphjCheckBox.setText("Morph  ");
        valleveljPanel.add(morphjCheckBox);

        depjCheckBox.setText("Dependency  ");
        valleveljPanel.add(depjCheckBox);

        othersjCheckBox.setText("Others ");
        valleveljPanel.add(othersjCheckBox);

        selectvaljPanel.add(valleveljPanel, java.awt.BorderLayout.CENTER);

        validationjPanel.add(selectvaljPanel, java.awt.BorderLayout.CENTER);

        executejButton.setText("Execute");
        executejButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                executejButtonActionPerformed(evt);
            }
        });
        runjPanel.add(executejButton);

        validationjPanel.add(runjPanel, java.awt.BorderLayout.PAGE_END);

        add(validationjPanel, java.awt.BorderLayout.PAGE_START);

        executedjPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Already Executed"));
        executedjPanel.setLayout(new java.awt.BorderLayout());

        execDirjPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Input"));
        execDirjPanel.setLayout(new javax.swing.BoxLayout(execDirjPanel, javax.swing.BoxLayout.LINE_AXIS));

        execfilejLabel.setText("Executed File: ");
        execDirjPanel.add(execfilejLabel);
        execDirjPanel.add(execfilejTextField);

        execfilejButton.setText("Browse   ");
        execfilejButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                execfilejButtonActionPerformed(evt);
            }
        });
        execDirjPanel.add(execfilejButton);

        executedjPanel.add(execDirjPanel, java.awt.BorderLayout.PAGE_START);

        execrunjButton.setText("Run");
        execrunjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                execrunjButtonActionPerformed(evt);
            }
        });
        execrunjPanel.add(execrunjButton);

        executedjPanel.add(execrunjPanel, java.awt.BorderLayout.CENTER);

        add(executedjPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void inDirjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inDirjButtonActionPerformed
        // TODO add your handling code here:
        String path = null;
        KeyValueProperties stateKVProps = BhaashikClientsStateData.getSateData().getPropertiesValue(ClientType.SYNTACTIC_ANNOTATION.toString());

        if(inDir != null) {
            File sfile = new File(inDir);

            if(sfile.exists() && sfile.getParentFile() != null)
                path = sfile.getParent();
            else
                path = stateKVProps.getPropertyValue("CurrentDir");
        }
        else
            path = stateKVProps.getPropertyValue("CurrentDir");

        
        
	JFileChooser chooser = null;

	if(path != null)
        {
            String npath = (new File(path)).getParent();
	    chooser = new JFileChooser(npath);
        }
	else
	    chooser = new JFileChooser();

        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           inDir = chooser.getSelectedFile().getAbsolutePath();
           inDirjTextField.setText(inDir);
        }
    }//GEN-LAST:event_inDirjButtonActionPerformed

    private void SetGetOptions()
    {
        inDir=inDirjTextField.getText();
        if(sanityjCheckBox.isSelected())
            typeSanity = true;
        else
            typeSanity = false;
        
        if(valrulejCheckBox.isSelected())
            typeRules = true;
        else
            typeRules = false;
        
        if(valstatjCheckBox.isSelected())
            typeStats = true;
        else
            typeStats = false;

        if(posjCheckBox.isSelected())
            levelPOS = true;
        else
            levelPOS = false;

        if(chunkjCheckBox.isSelected())
            levelChunk = true;
        else
            levelChunk = false;

        if(morphjCheckBox.isSelected())
            levelMorph = true;
        else
            levelMorph = false;

        if(depjCheckBox.isSelected())
            levelDep = true;
        else
            levelDep = false;

        if(othersjCheckBox.isSelected())
            levelOthers = true;
        else
            levelOthers = false;

    }

    private void GetFilesList(File ifile)
    {
        if(ifile.isDirectory() == true)
        {
            File files[] = ifile.listFiles();
            Arrays.sort(files, new Comparator<File>(){
            public int compare(File f1, File f2)
            {
                /*Long name1 = Long.valueOf(f1.getName().split("_")[0]);
                Long name2 = Long.valueOf(f2.getName().split("_")[0]);
                return name1.compareTo(name2);
                 * */
                return f1.getName().compareTo(f2.getName());
            } });

            for(int i = 0; i < files.length; i++)
            {
                GetFilesList(files[i]);
            }
        }
        
        else if(ifile.isFile() == true)
        {
            if(!(ifile.getName().startsWith("task-")) && !(ifile.getName().endsWith(".bak")) && !(ifile.getName().endsWith("~")) && !(ifile.getName().startsWith(".")))
            {
                System.out.println("Files:"+ifile.getAbsolutePath());
                filesList.add(ifile.getAbsoluteFile());
            }
        }
    }
/*
    private void utf2WX(File ifile, File ofile, String lang, String format) throws IOException, InterruptedException
    {

        String inFile = ifile.getAbsolutePath();
        String outFile = ofile.getAbsolutePath();

        String command = "sh convertor_utf2wx.sh "+lang+" "+format+" "+inFile+" "+outFile;

        //System.out.println("Executing "+command);
        Runtime rt = Runtime.getRuntime();
        //File toolDir = new File("./validation_tool/useful/tools/convertor-indic-1.4.2");
        File toolDir = new File("./validation_tool/useful/tools/convertor-1.1");
        Process proc = rt.exec(command,null,toolDir);
        proc.waitFor();
    }

    //wx2utf conversion
    private void WX2utf(File ifile, File ofile, String lang, String format) throws IOException, InterruptedException
    {
        String inFile = ifile.getAbsolutePath();
        String outFile = ofile.getAbsolutePath();
        
        String command = "sh convertor_wx2utf.sh "+lang+" "+format+" "+inFile+" "+outFile;
        //System.out.println("Executing "+command);
        Runtime rt = Runtime.getRuntime();
        //File toolDir = new File("./validation_tool/useful/tools/convertor-indic-1.4.2");
        File toolDir = new File("./validation_tool/useful/tools/convertor-1.1");
        Process proc = rt.exec(command,null,toolDir);
        proc.waitFor();
    }
*/

    private void FileStoryMap(LinkedHashMap map, Set set) throws IOException, InterruptedException
    {
        selStories = new LinkedHashMap<File, SSFStory>();

        if(set != null && set.size() >= 1)
        {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                String ifile = (String) it.next();
                File file = new File (ifile);
                
                SSFStory story1 = new SSFStoryImpl();

                selStories.put(file, story1);
                
                //story.setSSFFile(file.getAbsolutePath());
                story1.setSSFFile(file.getAbsolutePath());

                //String cs = kvTaskProps.getPropertyValue("SSFCorpusCharset");
                String cs = charset;

                try {
                    //story.readFile(file.getAbsolutePath(), cs);
                    story1.readFile(file.getAbsolutePath(), cs);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void FileStoryMap() throws IOException, InterruptedException
    {
        selStories = new LinkedHashMap<File, SSFStory>();
        
        if(filesList != null && filesList.size() >= 1)
        {
            for (int i = 0; i < filesList.size(); i++) {
                File file = (File) filesList.get(i);
                
                SSFStory story1 = new SSFStoryImpl();
                SSFStory story2 = new SSFStoryImpl();

                selStories.put(file, story1);
                
                //story.setSSFFile(file.getAbsolutePath());
                story1.setSSFFile(file.getAbsolutePath());

                //String cs = kvTaskProps.getPropertyValue("SSFCorpusCharset");
                String cs = charset;

                try {
                    //story.readFile(file.getAbsolutePath(), cs);
                    story1.readFile(file.getAbsolutePath(), cs);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void processDir(File ifile) throws IOException, Exception
    {
        if(ifile.isDirectory() == true)
        {
            File files[] = ifile.listFiles();
            Arrays.sort(files, new Comparator<File>(){
            public int compare(File f1, File f2)
            {
                return f1.getName().compareTo(f2.getName());
            } });

            for(int i = 0; i < files.length; i++)
            {
                if(!(ifile.getName().startsWith("task-")) && !(ifile.getName().endsWith(".bak")) && !(ifile.getName().endsWith("~")) && !(ifile.getName().startsWith(".")))
                if(!(files[i].getName().startsWith("task-")) && !(files[i].getName().endsWith(".bak")) && !(files[i].getName().endsWith("~")) && !(files[i].getName().startsWith(".")))
                {
                    processDir(files[i]);
                }
            }
        }
        
        else if(ifile.isFile() == true)
        {
            if(!(ifile.getName().startsWith("task-")) && !(ifile.getName().endsWith(".bak")) && !(ifile.getName().endsWith("~")) && !(ifile.getName().startsWith(".")))
            {
                System.out.println("Processing: "+ifile.getAbsolutePath());
                checkErrors();
            }
        }
    }

    public void checkErrors() throws IOException, InterruptedException, Exception
    {
        //System.out.println("Processing: "+ifile);
        
            if(levelPOS==true)
            {
                for(int i=0;i<filesList.size();i++)
                {
                    File ifile = (File) filesList.get(i);
                    sca.checkPOSErrors(ifile);
                }
            }
            if(levelChunk==true)
            {
                for(int i=0;i<filesList.size();i++)
                {
                    File ifile = (File) filesList.get(i);
                    sca.checkChunkErrors(ifile);
                }
            }
            if(levelMorph==true)
            {
                for(int i=0;i<filesList.size();i++)
                {
                    File ifile = (File) filesList.get(i);
                    sca.checkMorphErrors(ifile);
                }
            }
            if(levelDep==true)
            {
                for(int i=0;i<filesList.size();i++)
                {
                    File ifile = (File) filesList.get(i);
                    sca.checkDependencyErrors(ifile);
                }
            }
            if(levelOthers==true)
            {
                for(int i=0;i<filesList.size();i++)
                {
                    File ifile = (File) filesList.get(i);
                }
            }
    }

    private BhaashikTableModel File2Table(String errFile, boolean flag) throws FileNotFoundException, IOException, InterruptedException
    {
        if(matchesFinal == null)
        {
            matchesFinal = new BhaashikTableModel(new String[]{"Sentence", "Matched Node", "Context", "Referred Node", "File", "Comment"}, 2);
        }
        
        File ifile = new File(errFile);
        BufferedReader inReader = new BufferedReader(new FileReader(ifile));
        
        String line="";
        int count=0;
        line = inReader.readLine();
        int colCount=0,fileIndex=0;
        String[] colNames;
        Set set = new HashSet(); 
        if(line.startsWith("Column Count"))
        {
            String[] parts = line.split("::");
            if(parts.length==2)
                colCount=Integer.parseInt(line.split("::")[1]);
            line = inReader.readLine();
            parts = line.split("::");
            if(parts.length==2)
            {
                colNames=line.split("::")[1].split("\t");
                for(int i=0;i<colNames.length;i++)
                {
                    String colname=colNames[i];
                    if(colname.equals("File"))
                    {
                        fileIndex=i;
                    }
                }
                matchesFinal.setColumnIdentifiers(colNames);
            }
        }

        while ((line = inReader.readLine()) !=null)
        {
            if(!line.isEmpty())
            {
                count++;
                String[] parts = line.split("\t");
                matchesFinal.addRow(parts);
                set.add(parts[fileIndex]);
            }
        }

        if(flag)
        {
            FileStoryMap(selStories,set);
        }
        
        /*
        List files = matchesFinal.getColumn("File");
        System.out.println(files);
        Collections.sort(files);
        System.out.println(files);
        */
        
        return matchesFinal;
    }

    private void RunSanityChecks() throws FileNotFoundException, IOException, Exception
    {
        String lines="Column Count::6\n";
        lines+="Column Names::Sentence\tMatched Node\tContext\tReferred Node\tFile\tComment\n";

        sca = new SanityCheckAll_1();
        sca.setErrFile("./validation_tool/Sanity-Checks/output.txt",lines);
        sca.setLanguage("hin");
        sca.setToolsDir("./validation_tool/Sanity-Checks/");
        sca.checkFuncsInits();
        File ifile = new File(inDir);
        checkErrors();
        //processDir(ifile);
        File2Table("./validation_tool/Sanity-Checks/output.txt",false);
    }

    private BhaashikTableModel mergeTables(BhaashikTableModel allMatches, BhaashikTableModel matches)
    {
        Vector matchesVec = new Vector();
        if(allMatches != null && matches != null)
        {
            matchesVec.add(allMatches);
            matchesVec.add(matches);

            allMatches = BhaashikTableModel.mergeRows(matchesVec);
        }
        else if(allMatches == null && matches != null)
            allMatches = matches;
        return allMatches;
    }

    private void RunValidationRules() throws FileNotFoundException, IOException
    {
        ValidationRuleBased vrb = new ValidationRuleBased();
        vrb.setFilesList(filesList);
        vrb.setSelStories(selStories);
        
        if(levelPOS==true)
        {
            BhaashikTableModel allMatches = null;
            allMatches = vrb.ValRulePOS();
            matchesFinal = mergeTables(allMatches, matchesFinal);
        }
        
        if(levelChunk==true)
        {
            BhaashikTableModel allMatches = null;
            allMatches = vrb.ValRuleChunk();
            matchesFinal = mergeTables(allMatches, matchesFinal);
        }

        if(levelMorph==true)
        {
            BhaashikTableModel allMatches = null;
            allMatches = vrb.ValRuleMorph();
            matchesFinal = mergeTables(allMatches, matchesFinal);
        }
        
        if(levelDep==true)
        {
            BhaashikTableModel allMatches = null;
            allMatches = vrb.ValRuleDep();
            matchesFinal = mergeTables(allMatches, matchesFinal);
        }
        
        if(levelOthers==true)
        {
            BhaashikTableModel allMatches = null;
            allMatches = vrb.ValRuleOthers();
            matchesFinal = mergeTables(allMatches, matchesFinal);
        }
        
        if(matchesFinal.getColumnCount()==6)
            matchesFinal.setColumnIdentifiers(new String[]{"Sentence", "Matched Node", "Context", "Referred Node", "File", "Comment"});
    }

    private void RunValidationStats()
    {
        
    }

    private BhaashikTableModel RunQueries(File ifile) throws FileNotFoundException, IOException, Exception
    {
        GetFilesList(ifile);
        FileStoryMap();
        if(typeSanity==true)
        {
            RunSanityChecks();
        }
        if(typeRules==true)
        {
            RunValidationRules();
        }
        if(typeStats==true)
        {
            RunValidationStats();
        }

        //matches = ssfQuery.queryInFiles(ssfQuery, selStories);
        //matches = ssfQuery.query(ssfStory, ssfQuery);
        
        return matchesFinal;
    }

    private void Print2File(BhaashikTableModel model)
    {
        if(model!=null)
        {
            //System.out.println("Table is:");
            PrintStream ps;
            try {
                model.trimRows(false);
                File ofile = new File("./validation_tool/validation-output.txt");
                if(ofile.exists())
                {
                    ofile.delete();
                }
                ps = new PrintStream(ofile, "UTF-8");
                model.print(ps);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SyntacticAnnotationValidationJPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(SyntacticAnnotationValidationJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            System.out.println("No Rows in Table");
        }
    }
    
    private void executejButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_executejButtonActionPerformed
        // TODO add your handling code here:
        
        SetGetOptions();
        
        if(!inDir.isEmpty())
        {
            File ifile = new File(inDir);
            try {
                matchesFinal = RunQueries(ifile);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SyntacticAnnotationValidationJPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SyntacticAnnotationValidationJPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(SyntacticAnnotationValidationJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            //System.out.println("input File");
            Print2File(matchesFinal);
        }
        else
        {
            //System.out.println("No input File");
            //JOptionPane.showMessageDialog(this, GlobalProperties.getIntlString("No input directory specified:"), GlobalProperties.getIntlString("Error"), JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(this, "No input directory specified:", "Error", JOptionPane.ERROR_MESSAGE);
        }
        //SetVariables();
        //this.setVisible(false);
    }//GEN-LAST:event_executejButtonActionPerformed

    private void execfilejButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_execfilejButtonActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
	//chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnVal = chooser.showOpenDialog(this);

        if(returnVal == JFileChooser.APPROVE_OPTION) {
	    execFile = chooser.getSelectedFile().getAbsolutePath();
	    execfilejTextField.setText(execFile);
        }
    }//GEN-LAST:event_execfilejButtonActionPerformed

    private void execrunjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_execrunjButtonActionPerformed
        if(execFile.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "No input file specified:", "File Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            try {
                // TODO add your handling code here:
                File2Table(execFile, true);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SyntacticAnnotationValidationJPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SyntacticAnnotationValidationJPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(SyntacticAnnotationValidationJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_execrunjButtonActionPerformed

    private void inDirjTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inDirjTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inDirjTextFieldActionPerformed

    private void sanityjCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sanityjCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sanityjCheckBoxActionPerformed

    private void valrulejCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valrulejCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_valrulejCheckBoxActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chunkjCheckBox;
    private javax.swing.JCheckBox depjCheckBox;
    private javax.swing.JPanel execDirjPanel;
    private javax.swing.JButton execfilejButton;
    private javax.swing.JLabel execfilejLabel;
    private javax.swing.JTextField execfilejTextField;
    private javax.swing.JButton execrunjButton;
    private javax.swing.JPanel execrunjPanel;
    private javax.swing.JPanel executedjPanel;
    private javax.swing.JButton executejButton;
    private javax.swing.JButton inDirjButton;
    private javax.swing.JLabel inDirjLabel;
    private javax.swing.JPanel inDirjPanel;
    private javax.swing.JTextField inDirjTextField;
    private javax.swing.JCheckBox morphjCheckBox;
    private javax.swing.JCheckBox othersjCheckBox;
    private javax.swing.JCheckBox posjCheckBox;
    private javax.swing.JPanel runjPanel;
    private javax.swing.JCheckBox sanityjCheckBox;
    private javax.swing.JPanel selectvaljPanel;
    private javax.swing.JPanel validationjPanel;
    private javax.swing.JPanel valleveljPanel;
    private javax.swing.JCheckBox valrulejCheckBox;
    private javax.swing.JCheckBox valstatjCheckBox;
    private javax.swing.JPanel valtypejPanel;
    // End of variables declaration//GEN-END:variables

private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("Bhaashik_Validation_Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.addWindowListener(new ExitListener());

        //Create and set up the content pane.
        SyntacticAnnotationValidationJPanel newContentPane = new SyntacticAnnotationValidationJPanel();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        
	//newContentPane.setOwner(frame);

        //Display the window.
        frame.pack();

        //int inset = 5;
        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //frame.setBounds(inset, inset,
	//	screenSize.width  - inset * 2,
	//	screenSize.height - inset * 9);

	frame.setVisible(true);
    }

public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }


}
