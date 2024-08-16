/*
 * Created on Sep 21, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bhaashik.common.types;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

import bhaashik.GlobalProperties;
import bhaashik.annotation.common.AutomaticAnnotationJPanel;
import bhaashik.corpus.discourse.DiscourseAnnotationJPanel;
import bhaashik.corpus.manager.gui.NGramLMJPanel;
import bhaashik.corpus.parallel.gui.ParallelMarkupWorkJPanel;
import bhaashik.corpus.parallel.gui.SentenceAlignmentInterfaceJPanel;
import bhaashik.corpus.parallel.gui.WordAlignmentInterfaceJPanel;
import bhaashik.corpus.ssf.gui.SyntacticAnnotationWorkJPanel;
import bhaashik.corpus.ssf.tree.SSFPhrase;
import bhaashik.langenc.gui.LanguageEncodingIdentifierJPanel;
import bhaashik.propbank.gui.FramesetJPanel;
import bhaashik.resources.shabdanjali.DictionaryEditorJPanel;
import bhaashik.text.editor.gui.RichTextEditorJPanel;
import bhaashik.text.editor.gui.TextEditorJPanel;
import bhaashik.text.spell.gui.DictionaryFSTJPanel;
import bhaashik.gui.clients.BhaashikRemoteWorkJPanel;
import bhaashik.gui.common.ApplicationDescription;
import bhaashik.gui.common.IntegratedResourceAccessorJPanel;
import bhaashik.gui.common.BhaashikCharmapJPanel;
import bhaashik.gui.shell.BhaashikShellJPanel;
import bhaashik.propbank.Frameset;
import bhaashik.table.gui.BhaashikTableJPanel;
import bhaashik.tree.BhaashikMutableTreeNode;
import bhaashik.tree.gui.BhaashikTreeDrawingJPanel;
import bhaashik.util.gui.DocumentConverterJPanel;
import bhaashik.util.gui.FileSplitterJPanel;
import bhaashik.word.gui.WordListJPanel;

/**
 *  @author Anil Kumar Singh Kumar Singh
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public final class ClientType extends BhaashikType implements Serializable {
    
    public final int ord;
    private static Vector types = new Vector();
    private static String java_package;
    
    protected ServerType server;
    protected String title;
    protected String code;
    
    protected ClientType(String title, String code, String id, ServerType st, String pk) {
        super(id, pk);
        this.title = title;
        this.code = code;
        //   this.server = server;
        
        if (ClientType.last() != null) {
            this.prev = ClientType.last();
            //System.out.println("dsjkd");
            ClientType.last().next = this;
        }
        
        types.add(this);
        ord = types.size();
    }
    
    public static int size() {
        return types.size();
    }
    
    public static BhaashikType first() {
        return (BhaashikType) types.get(0);
    }
    
    public static BhaashikType last() {
        if(types.size() > 0)
            return (BhaashikType) types.get(types.size() - 1);
        
        return null;
    }
    
    public static BhaashikType getType(int i) {
        if(i >=0 && i < types.size())
            return (BhaashikType) types.get(i);
        
        return null;
    }
    
    public static Enumeration elements() {
        return new TypeEnumerator(ClientType.first());
    }
    
    public static BhaashikType findFromClassName(String className) {
        Enumeration enm = ClientType.elements();
        return findFromClassName(enm, className);
    }
    
    public static BhaashikType findFromId(String i) {
        Enumeration enm = ClientType.elements();
        return findFromId(enm, i);
    }

    public static ClientType findFromTitle(String t) {
        Enumeration enm = ClientType.elements();
        ClientType dt = null;

        while(enm.hasMoreElements())
        {
            dt = (ClientType) enm.nextElement();

            if(t.equals(dt.toString()))
                return dt;
        }

        return null;
    }

    public static BhaashikType findFromCode(String c)
    {
        Enumeration enm = ClientType.elements();
        ClientType dt = null;

        while(enm.hasMoreElements())
        {
            dt = (ClientType) enm.nextElement();

            if(c.equals(dt.getCode()))
                return dt;
        }

        return null;
    }
    
    public static JPanel createBhaashikClient(ClientType cl) {
        
        return (JPanel)identifyClient(cl);
    }
    
    
    public String toString() {
        if(title!=null)
            return this.title;
        else
            return " ";
        
    }

    public String getCode() {
        return code;
    }
    
    private static Object identifyClient(ClientType cl) {
        
        int t[] = null;
        
//        if(cl.equals(ClientType.BLOG_LIST_CAPTURE))
//            return new BlogListCaptureJPanel();
//        else if(cl.equals(ClientType.DICTIONARY_FST))
//            return new DictionaryFSTJPanel();
//        else if(cl.equals(ClientType.FILE_EXPLORER)) {
//            FileDisplayer fd = new FileDisplayer() {
//                
//                public boolean closeFile(EventObject e) {
//                    throw new UnsupportedOperationException("Not supported yet.");
//                }
//                
//                public void displayFile(String path, String charset, EventObject e) {
//                    throw new UnsupportedOperationException("Not supported yet.");
//                }
//                
//                public void displayFile(File file, String charset, EventObject e) {
//                    throw new UnsupportedOperationException("Not supported yet.");
//                }
//                
//                public String getDisplayedFile(EventObject e) {
//                    throw new UnsupportedOperationException("Not supported yet.");
//                }
//                
//                public String getCharset(EventObject e) {
//                    throw new UnsupportedOperationException("Not supported yet.");
//                }
//            };
//            return new FileExplorerJPanel(fd);
//        }
//        else if(cl.equals(ClientType.FILE_MATCHER))
//            return new FileMatcherJPanel();

        if(cl.equals(ClientType.BHAASHIK_SSH_CLIENT))
            return new BhaashikRemoteWorkJPanel();
        else if(cl.equals(ClientType.FILE_SPLITTER))
            return new FileSplitterJPanel();
        else if(cl.equals(ClientType.BHAASHIK_CHARMAP))
            return new BhaashikCharmapJPanel();
        else if(cl.equals(ClientType.INTEGRATED_RESOURCE_ACCESSOR))
            return new IntegratedResourceAccessorJPanel(true, true);
        else if(cl.equals(ClientType.WORD_LIST))
            return new WordListJPanel("hin::utf8");
        else if(cl.equals(ClientType.LANGUAGE_ENCODING_IDENTIFIER))
        {
            LanguageEncodingIdentifierJPanel panel = new LanguageEncodingIdentifierJPanel();
            return panel;
        }
        else if(cl.equals(ClientType.DICTIONARY_FST))
            return new DictionaryFSTJPanel();
//        else if(cl.equals(ClientType.FILTER_TRANSLATOR))
//            return new FilterTranslatorJPanel();
//        else if(cl.equals(ClientType.MNREAD))
//            return new MNReadJPanel();
//        else if(cl.equals(ClientType.PARALLEL_CORPUS_MARKUP))
//        {
//            ParallelSyntacticAnnotationWorkJPanel parallelSyntacticAnnotationWorkJPanel =
//                    new ParallelSyntacticAnnotationWorkJPanel();
//
//            return parallelSyntacticAnnotationWorkJPanel;
//        }
        else if(cl.equals(ClientType.NGRAM_LM))
            return new NGramLMJPanel();
        else if(cl.equals(ClientType.SENTENCE_ALIGNMENT_INTERFACE))
            return new SentenceAlignmentInterfaceJPanel(true);
        else if(cl.equals(ClientType.WORD_ALIGNMENT_INTERFACE))
            return new WordAlignmentInterfaceJPanel();
        else if(cl.equals(ClientType.DICTIONARY_EDITOR))
            return new DictionaryEditorJPanel();
        else if(cl.equals(ClientType.PARALLEL_MARKUP))
            return new ParallelMarkupWorkJPanel();
        else if(cl.equals(ClientType.DISCOURSE_ANNOTATION))
            return new DiscourseAnnotationJPanel("hin::utf8");
        else if(cl.equals(ClientType.DOCUMENT_CONVERTER))
            return new DocumentConverterJPanel();
//        else if(cl.equals(ClientType.PARALLEL_MARKUP_ANALYZER))
//            return new ParallelMarkupAnalyzerJPanel();
//        else if(cl.equals(ClientType.PROPERTIES_MANAGER))
//            return new PropertiesManagerJPanel("workspace/bhaash-components-pm.txt", "UTF-8");
        else if(cl.equals(ClientType.BHAASHIK_EDITOR))
        {
            TextEditorJPanel textEditorJPanel = new TextEditorJPanel("hin::utf8", "UTF-8", null, null, TextEditorJPanel.DEFAULT_MODE);
            textEditorJPanel.showCommandButtons(false);
            return textEditorJPanel;
        }
        else if(cl.equals(ClientType.BHAASHIK_RTF_EDITOR))
        {
            RichTextEditorJPanel richTextEditorJPanel = new RichTextEditorJPanel("hin::utf8", "UTF-8", null, null, RichTextEditorJPanel.DEFAULT_MODE);
            richTextEditorJPanel.showCommandButtons(false);
            return richTextEditorJPanel;
        }
//        else if(cl.equals(ClientType.SENTENCE_ALIGNER))
//            return new SenAlignWorkJPanel();
//        else if(cl.equals(ClientType.SIMILARITY_MEASURE))
//            return new SimilarityMeasuresJPanel();
//        else if(cl.equals(ClientType.SPELL_CHECKER))
//            return new SpellCheckerJPanel();
//        else if(cl.equals(ClientType.SSF_CORPUS_ANALYZER))
//            return new SSFCorpusAnalyzerJPanel();
        else if(cl.equals(ClientType.BHAASHIK_SHELL))
            return new BhaashikShellJPanel();
        else if(cl.equals(ClientType.SYNTACTIC_ANNOTATION))
            return new SyntacticAnnotationWorkJPanel();
        else if(cl.equals(ClientType.PROPBANK_ANNOTATION))
            return new SyntacticAnnotationWorkJPanel(true);
        else if(cl.equals(ClientType.FRAMESET_EDITOR))
            return new FramesetJPanel(new Frameset(), "hin::utf8");
        else if(cl.equals(ClientType.BHAASHIK_HTML_BROWSER))
        {
//            HTMLBrowserJPanel clientJPanal = new HTMLBrowserJPanel();
//            clientJPanal.init();
//            
//            return clientJPanal;
        }
        else if(cl.equals(ClientType.AUTOMATIC_ANNOTATION))
            return new AutomaticAnnotationJPanel();
        else if(cl.equals(ClientType.TABLE_EDITOR))
            return new BhaashikTableJPanel(false, BhaashikTableJPanel.DEFAULT_MODE, "hin::utf8");
        else if(cl.equals(ClientType.TREE_EDITOR))
        {
            SSFPhrase root = null;

            try
            {
                root = new SSFPhrase("0", "", "S", "");
            } catch (Exception ex)
            {
                Logger.getLogger(BhaashikTreeDrawingJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            return new BhaashikTreeDrawingJPanel(root, BhaashikMutableTreeNode.PHRASE_STRUCTURE_MODE, "eng::utf8", false);
        }
//        else if(cl.equals(ClientType.TREE_EDITOR))
//            return new BhaashikTreeJPanel(t , 1000, "eng::utf8");
//        else if(cl.equals(ClientType.XML_EDITOR))
//            return new BhaashikW3CXMLJPanel();
        
        return null;
    }
    
    public String applicationDescription(ClientType cl) {
        String returnThis=null;
//        try{
//            ApplicationDescription appl = (ApplicationDescription)identifyClient(cl);
//            returnThis= appl.applicationDescription();
//            if(returnThis==null) {
//                returnThis="Application description missing";
//            }
//        }catch(java.lang.ClassCastException e) {
//            returnThis= "Application description not found. Please check out the latest version for updates.";
//        } catch (Exception e) {
//            returnThis= "Unknown Exception. If this persists, please report it to bhaash@iiit.ac.in (preferably with detailed description of the sequence of events with led you here)";
//        } finally{
//            return returnThis;
//        }

        if(cl.equals(BHAASHIK_SSH_CLIENT))
            returnThis = GlobalProperties.getIntlString("SSH_client_for_working_remotely.");
        else if(cl.equals(BHAASHIK_EDITOR))
            returnThis = GlobalProperties.getIntlString("An_NLP_friendly_text_editor_with_customizable_support_for_languages_and_encodings.");
        else if(cl.equals(BHAASHIK_RTF_EDITOR))
            returnThis = GlobalProperties.getIntlString("An_NLP_friendly_rich_text_editor_with_customizable_support_for_languages_and_encodings.");
        else if(cl.equals(BHAASHIK_HTML_BROWSER))
            returnThis = "A very simple HTML browser for simple web pages.";
        else if(cl.equals(BHAASHIK_SHELL))
            returnThis = "An very simple shell for Bhaashik applications.";
        else if(cl.equals(TABLE_EDITOR))
            returnThis = GlobalProperties.getIntlString("A_table_editor_with_all_the_usual_facilities_for_editing_a_table.");
        else if(cl.equals(TREE_EDITOR))
            returnThis = "A tool for linguists to create and edit trees representing phrase structures";
        else if(cl.equals(INTEGRATED_RESOURCE_ACCESSOR))
            returnThis = GlobalProperties.getIntlString("Find,_replace_and_extract_text_with_or_without_regular_expressions_in_a_file_or_a_directory.");
        else if(cl.equals(WORD_LIST))
            returnThis = GlobalProperties.getIntlString("A_tool_for_building_a_word_list_from_different_kinds_of_sources_like_other_word_lists_and_corpora.");
        else if(cl.equals(LANGUAGE_ENCODING_IDENTIFIER))
            returnThis = GlobalProperties.getIntlString("One_of_the_most_accurate_tools_for_identifying_the_language-encoding_of_a_document_or_of_some_text._The_current_version_is_trained_for_54_language-encoding_pairs.");
        else if(cl.equals(DICTIONARY_FST))
            returnThis = GlobalProperties.getIntlString("A_tool_for_compiling_word_lists_as_an_FST._It_also_allows_the_listing_and_visualization_of_affixes.");
        else if(cl.equals(ClientType.AUTOMATIC_ANNOTATION))
            returnThis = GlobalProperties.getIntlString("A_CRF_based_automatic_annotation_tool_for_POS_tagging,_chunking_and_named_entity_recognition.");
        else if(cl.equals(SYNTACTIC_ANNOTATION))
            returnThis = GlobalProperties.getIntlString("A_user_friendly_interface_for_syntactic_and_other_kinds_of_annotation_(e.g._POS_tagging,_chunking,_dependency_markup_etc.).");
        else if(cl.equals(PROPBANK_ANNOTATION))
            returnThis = "A user friendly interface for Propbank like annotation.";
//        else if(cl.equals(SSF_CORPUS_ANALYZER))
//            returnThis = "A search, query and comparison tool for syntactically annotated corpus. Some facilities are still under development.";
//        else if(cl.equals(PARALLEL_CORPUS_MARKUP))
//            returnThis = "A user friendly interface for flat annotation (e.g. multi word expressions, named entities etc.) of parallel corpus.";
        else if(cl.equals(FRAMESET_EDITOR))
            returnThis = GlobalProperties.getIntlString("An_editor_for_frameset,_compatible_with_Cornerstone.");
        else if(cl.equals(NGRAM_LM))
            returnThis = GlobalProperties.getIntlString("A_tool_for_compiling_n-gram_models_of_different_kinds,_e.g._words,_characters,_bytes");
        else if(cl.equals(SENTENCE_ALIGNMENT_INTERFACE))
            returnThis = "An interface for paragraph and sentence alignment in parallel corpora.";
        else if(cl.equals(WORD_ALIGNMENT_INTERFACE))
            returnThis = "An interface for word and phrase alignment in parallel corpora.";
        else if(cl.equals(DICTIONARY_EDITOR))
            returnThis = "A simple dictionary editor.";
        else if(cl.equals(PARALLEL_MARKUP))
            returnThis = GlobalProperties.getIntlString("An_interface_for_parallel_markup_of_syntactically_annotated_text.");
//        else if(cl.equals(PARALLEL_MARKUP_ANALYZER))
//            returnThis = "A search, query and comparison tool for annotated parallel corpus. Some facilities are still under development.";
        else if(cl.equals(DISCOURSE_ANNOTATION))
            returnThis = GlobalProperties.getIntlString("An_interface_for_discourse_annotation_based_on_the_Penn_Treebank_annotation_scheme.");
        else if(cl.equals(FILE_SPLITTER))
            returnThis = GlobalProperties.getIntlString("A_useful_tool_for_splitting_files_(including_files_in_the_SSF_format)_according_to_various_criteria.");
        else if(cl.equals(BHAASHIK_CHARMAP))
            returnThis = "Character map with the usual facilities, except that it is connected to the Bhaashik Langauge Encoding Support facility";
//        else if(cl.equals(BHAASHIK_EDITOR))
//            returnThis = "";

        return returnThis;
    }
    
    public String email(ClientType cl) {
        String returnThis=null;
        try{
            ApplicationDescription appl = (ApplicationDescription)identifyClient(cl);
            returnThis= appl.email();
            if(returnThis==null) {
                returnThis="bhaash@bhaash.co.in";
            }
        }
//        }catch(java.lang.ClassCastException e)
//        {
//            returnThis= "bhaash@iiit.ac.in";//"email not found. Please check out the latest version for updates.";
//        }
        catch (Exception e) {
            returnThis= "bhaash@bhaash.co.in";//"Unknown Exception. If this persists, please report it to bhaash@iiit.ac.in ";
        } finally{
            return returnThis;
        }
        
    }

    public static final ClientType BHAASHIK_SSH_CLIENT = new ClientType("Bhaashik_SSH_Client", "SC", "BhaashikSSHClientJPanel", ServerType.RESOURCE_MANAGER, "bhaash.gui.clients");
    public static final ClientType BHAASHIK_EDITOR = new ClientType(GlobalProperties.getIntlString("Bhaashik_Editor"), "SE", "TextEditorJPanel", ServerType.RESOURCE_MANAGER, "bhaash.text.editor.gui");
    public static final ClientType BHAASHIK_RTF_EDITOR = new ClientType(GlobalProperties.getIntlString("Bhaashik_Rich_Text_Editor"), "RE", "RichTextEditorJPanel", ServerType.RESOURCE_MANAGER, "bhaash.text.editor.gui");
    public static final ClientType BHAASHIK_SHELL = new ClientType("Bhaashik Shell", "SS", "BhaashikShellJPanel", ServerType.RESOURCE_MANAGER, "bhaash.gui.shell");
    public static final ClientType BHAASHIK_HTML_BROWSER = new ClientType("Bhaashik HTML Browser", "SB", "HTMLBrowserJPanel", ServerType.RESOURCE_MANAGER, "bhaash.html.gui");
    public static final ClientType TABLE_EDITOR = new ClientType(GlobalProperties.getIntlString("Table_Editor"), "TE", "BhaashikTableJPanel", ServerType.RESOURCE_MANAGER, "bhaash.table.gui");
    public static final ClientType TREE_EDITOR = new ClientType("Tree Creator", "TC", "BhaashikTreeDrawingJPanel", ServerType.RESOURCE_MANAGER, "bhaash.tree.gui");
    public static final ClientType INTEGRATED_RESOURCE_ACCESSOR = new ClientType(GlobalProperties.getIntlString("Integrated_Resource_Accessor"), "RA", "IntegratedResourceAccessorJPanel", ServerType.RESOURCE_MANAGER, "bhaash.gui.common");
    public static final ClientType WORD_LIST = new ClientType(GlobalProperties.getIntlString("Word_List_Builder"), "WB", "WordListJPanel", ServerType.RESOURCE_MANAGER, "bhaash.word.gui");
    public static final ClientType DICTIONARY_FST = new ClientType(GlobalProperties.getIntlString("Word_List_FST_Visualizer"), "WV", "DictionaryFSTJPanel", ServerType.RESOURCE_MANAGER, "bhaash.text.spell.gui");
    public static final ClientType DICTIONARY_EDITOR = new ClientType("Dictionary Editor", "DE", "DictionaryEditorJPanel", ServerType.RESOURCE_MANAGER, "bhaash.resources.shabdanjali");
    public static final ClientType LANGUAGE_ENCODING_IDENTIFIER = new ClientType(GlobalProperties.getIntlString("Language_Encoding_Identifier"), "LI", "LanguageEncodingIdentifierJPanel", ServerType.RESOURCE_MANAGER , "bhaash.langenc.gui");
    public static final ClientType NGRAM_LM = new ClientType(GlobalProperties.getIntlString("N-Gram_Language_Model_Compiler"), "LM", "NGramLMJPanel",ServerType.RESOURCE_MANAGER, "bhaash.corpus.manager.gui");
    public static final ClientType SYNTACTIC_ANNOTATION = new ClientType(GlobalProperties.getIntlString("Syntactic_Annotation"), "SA", "SyntacticAnnotationWorkJPanel", ServerType.RESOURCE_MANAGER, "bhaash.corpus.ssf.gui");
    public static final ClientType PROPBANK_ANNOTATION = new ClientType("Propbank Annotation", "PB", "PropbankAnnotationWorkJPanel", ServerType.RESOURCE_MANAGER, "bhaash.corpus.ssf.gui");
    public static final ClientType FRAMESET_EDITOR = new ClientType(GlobalProperties.getIntlString("Frameset_Editor"), "FE", "FramesetJPanel", ServerType.RESOURCE_MANAGER, "bhaash.propbank.gui");
//    public static final ClientType SSF_CORPUS_ANALYZER = new ClientType("SSF Corpus Analyzer", "", "SSFCorpusAnalyzerJPanel", ServerType.BHAASHIK_SERVER, "bhaash.corpus.ssf.gui");
//    public static final ClientType PARALLEL_CORPUS_MARKUP = new ClientType("Parallel Syntactic Annotation", "PS", "ParallelSyntacticAnnotationWorkJPanel",ServerType.BHAASHIK_SERVER, "bhaash.corpus.parallel.gui");
//    public static final ClientType BHAASHIK = new ClientType("BhaashikMain", "", ServerType.BHAASHIK, "bhaash.clients");
//    public static final ClientType USER_MANAGER = new ClientType("User Manager", "", "UserManager", ServerType.USER_MANAGER, "bhaash.clients");

//    public static final ClientType BLOG_LIST_CAPTURE = new ClientType("Blog List Capture", "", "BlogListCaptureJPanel", ServerType.BHAASHIK_SERVER, "bhaash.corpus.blog");
//    public static final ClientType FILE_EXPLORER = new ClientType("File Explorer", "", "FileExplorerJPanel", ServerType.BHAASHIK_SERVER, "bhaash.gui.common");
//    public static final ClientType FILE_MATCHER = new ClientType("File Matcher", "", "FileMatcherJPanel", ServerType.BHAASHIK_SERVER, "bhaash.util.gui");
//    public static final ClientType FILTER_TRANSLATOR = new ClientType("Filter Traslator", "", "FilterTranslatorJPanel", ServerType.BHAASHIK_SERVER, "bhaash.filters.gui");
//    public static final ClientType MNREAD = new ClientType("MNRead", "", "MNReadJPanel", ServerType.BHAASHIK_SERVER, "bhaash.util.gui");
//    public static final ClientType SENTENCE_ALIGNER = new ClientType("Sentence Aligner", "", "SenAlignWorkJPanel", ServerType.BHAASHIK_SERVER, "bhaash.corpus.parallel.aligner.sentence.gui");
    public static final ClientType SENTENCE_ALIGNMENT_INTERFACE = new ClientType("Sentence Alignment Interface", "SI", "SentenceAlignmentInterfaceJPanel", ServerType.RESOURCE_MANAGER, "bhaash.corpus.parallel.gui");
    public static final ClientType WORD_ALIGNMENT_INTERFACE = new ClientType("Word Alignment Interface", "WI", "WordAlignmentInterfaceJPanel", ServerType.RESOURCE_MANAGER, "bhaash.corpus.parallel.gui");
    public static final ClientType PARALLEL_MARKUP = new ClientType(GlobalProperties.getIntlString("Parallel_Corpus_Markup"), "PA", "ParallelMarkupWorkJPanel", ServerType.RESOURCE_MANAGER, "bhaash.marker.gui");
//    public static final ClientType PARALLEL_MARKUP_ANALYZER = new ClientType("Parallel Markup Analyser", "", "ParallelMarkupAnalyzerJPanel", ServerType.BHAASHIK_SERVER, "bhaash.marker.gui");
    public static final ClientType DISCOURSE_ANNOTATION = new ClientType(GlobalProperties.getIntlString("Discourse_Annotation"), "DA", "DiscourseAnnotationJPanel", ServerType.RESOURCE_MANAGER, "bhaash.corpus.discourse");
    public static final ClientType AUTOMATIC_ANNOTATION = new ClientType(GlobalProperties.getIntlString("Automatic_Annotation"), "AA", "AutomaticAnnotationJPanel", ServerType.RESOURCE_MANAGER, "bhaash.annotation.common");
//    public static final ClientType PROPERTIES_MANAGER = new ClientType("Properties Manager", "", "PropertiesManagerJPanel", ServerType.BHAASHIK_SERVER, "bhaash.properties.gui");
    public static final ClientType FILE_SPLITTER = new ClientType(GlobalProperties.getIntlString("File_Splitter"), "FS", "FileSplitterJPanel", ServerType.RESOURCE_MANAGER, "bhaash.util.gui");
    public static final ClientType BHAASHIK_CHARMAP = new ClientType("Bhaashik Charmap", "CM", "BhaashikCharmapJPanel", ServerType.RESOURCE_MANAGER, "bhaash.gui.common");
//    public static final ClientType SIMILARITY_MEASURE = new ClientType("Similarity Measure", "", "SimilarityMeasuresJPanel", ServerType.BHAASHIK_SERVER, "bhaash.corpus.manager.gui");
//    public static final ClientType SPELL_CHECKER = new ClientType("Spell Checker", "", "SpellCheckerJPanel", ServerType.BHAASHIK_SERVER, "bhaash.gui.common");
//    public static final ClientType TREE_EDITOR = new ClientType("Tree Editor", "", "BhaashikTreeJPanel", ServerType.BHAASHIK_SERVER, "bhaash.tree.gui");
//    public static final ClientType XML_EDITOR = new ClientType("XML Editor", "", "BhaashikW3CXMLJPanel", ServerType.BHAASHIK_SERVER, "bhaash.xml.gui");
    public static final ClientType DATABASe_EDITOR = new ClientType("Database Editor", "DBE", "BhaashikDatabaseEditorJPanel", ServerType.RESOURCE_MANAGER, "bhaash.db.gui");
    public static final ClientType DOCUMENT_CONVERTER = new ClientType("Document Converter", "DCT", "DocumentConverterJPanel", ServerType.RESOURCE_MANAGER, "bhaash.util.gui");
    
    
//    public static final ClientType PARALLEL_CORPUS_MARKUP = new ClientType("ParallelCorpusMarkup", ServerType.PARALLEL_CORPUS_MARKUP, "bhaash.corpus.parallel.gui");
//    public static final ClientType UD_MANAGER = new ClientType("UDManagerClient", UDManagerServer, "bhaash.clients");
//    public static final ClientType CORPUS_MANAGER = new ClientType("CorpusManagerClient", CorpusManagerServer, "bhaash.clients");
//    public static final ClientType NGRAMLM = new ClientType("NGramLMClient", NGramLMServer, "bhaash.clients");
//    public static final ClientType IBM_MODEL = new ClientType("IBMModelClient", IBMModelServer, "bhaash.clients");
//    public static final ClientType DSF = new ClientType("DSFClient", DSFServer, "bhaash.clients");
//    public static final ClientType GTAC = new ClientType("GTACClient", GTACServer, "bhaash.clients");
//    public static final ClientType PARALLEL_CORPUS = new ClientType("ParallelCorpusClient", ParallelCorpusServer, "bhaash.clients");
//    public static final ClientType ML_ANNOTATION = new ClientType("MLAnnotationClient", MLAnnotationServer, "bhaash.clients");
//    public static final ClientType TEXT_ENCODING = new ClientType("TextEncodingClient", TextEncodingServer, "bhaash.clients");
//    public static final ClientType NLI = new ClientType("NLIClient", NLIServer, "bhaash.clients");
//    public static final ClientType NS = new ClientType("NSClient", NSServer, "bhaash.clients");
}
