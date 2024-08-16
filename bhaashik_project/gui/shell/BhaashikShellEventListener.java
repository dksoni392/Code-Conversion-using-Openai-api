/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.gui.shell;

import java.util.EventListener;

/**
 *
 * @author anil
 */
public interface BhaashikShellEventListener extends EventListener {
    void handledShellEvent(BhaashikShellEvent evt);
}
