/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik;

import java.util.EventListener;

/**
 *
 * @author anil
 */
public interface BhaashikMainEventListener extends EventListener {
    void openTab(BhaashikMainEvent evt);
    void displayFile(BhaashikMainEvent evt);
}
