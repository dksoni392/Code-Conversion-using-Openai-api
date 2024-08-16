package bhaashik.table.gui;


import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import bhaashik.corpus.ssf.tree.SSFLexItem;
import bhaashik.corpus.ssf.tree.SSFPhrase;
import bhaashik.gui.common.BhaashikDataFlavors;

public class BhaashikTransferableObject implements Transferable {

  DataFlavor flavors[] = {BhaashikDataFlavors.SSF_LEXITEM_FLAVOR, BhaashikDataFlavors.SSF_PHRASE_FLAVOR};

  protected Object bhaashikTransferableObject;

  public BhaashikTransferableObject(Object bhaashikTransferableObject) {
    this.bhaashikTransferableObject = bhaashikTransferableObject;
  }

  public Object getBhaashikTransferableObject()
  {
      return bhaashikTransferableObject;
  }

  public synchronized DataFlavor[] getTransferDataFlavors() {
    return flavors;
  }

  public boolean isDataFlavorSupported(DataFlavor flavor) {
    return (flavor.getRepresentationClass() == SSFPhrase.class
            || flavor.getRepresentationClass() == SSFLexItem.class);
  }

  public synchronized Object getTransferData(DataFlavor flavor)
      throws UnsupportedFlavorException, IOException {
    if (isDataFlavorSupported(flavor)) {
      return (Object) bhaashikTransferableObject;
    } else {
      throw new UnsupportedFlavorException(flavor);
    }
  }
}