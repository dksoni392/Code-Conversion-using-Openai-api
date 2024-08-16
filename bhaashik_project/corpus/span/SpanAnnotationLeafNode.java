/*created by Kinshul
used to store information for connective-instances and their arguments 
the annotation information of a story can be stored in the form of a tree of these AnnotationInfo nodes   
 */
package bhaashik.corpus.span;

import java.io.Serializable;
import javax.swing.tree.MutableTreeNode;

import bhaashik.corpus.ssf.tree.SSFLexItem;
import bhaashik.properties.PropertiesManager;

public class SpanAnnotationLeafNode extends SSFLexItem
        implements MutableTreeNode, Serializable {
    protected SpanAnnotationInfo spanAnnotationInfo;
    protected PropertiesManager propman;
    protected SpanAnnotationParentNode Parent;
    
    SpanAnnotationLeafNode() {
        PropertiesManager propman = new PropertiesManager();
        spanAnnotationInfo = new SpanAnnotationInfo();
    }

    SpanAnnotationLeafNode(int f) {
        PropertiesManager propman = new PropertiesManager();
         spanAnnotationInfo = new SpanAnnotationInfo();
   }

    SpanAnnotationLeafNode(String n) {
        PropertiesManager propman = new PropertiesManager();
       // BhaashikProperties v = (BhaashikProperties) n;
        //int m = propman.addPropertyContainer('name',v,PropertyType.KEY_VALUE_PROPERTIES);
        spanAnnotationInfo = new SpanAnnotationInfo();
    }
    
    
    

    SpanAnnotationLeafNode(String n, int f) {
        PropertiesManager propman = new PropertiesManager();
        spanAnnotationInfo = new SpanAnnotationInfo();
    }

    public SpanAnnotationInfo getSpanAnnotationInfo() {
        return spanAnnotationInfo;
    }

    public int setParent(SpanAnnotationParentNode par)
     {
            if(par==null)
                return 0;
            parent=par;
            return 1;
      }
    
    
    public void setSpanAnnotationInfo(SpanAnnotationInfo spanAnnotationInfo) {
        this.spanAnnotationInfo = spanAnnotationInfo;
    }

    public PropertiesManager getPropman() {
        return propman;
    }

    public void setPropman(PropertiesManager propman) {
        this.propman = propman;
    }
}

