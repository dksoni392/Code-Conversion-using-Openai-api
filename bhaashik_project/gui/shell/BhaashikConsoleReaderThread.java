/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.gui.shell;

/**
 *
 * @author anil
 */
public class BhaashikConsoleReaderThread extends Thread {
    protected BhaashikConsoleReader cr;
    protected JTextShell textShell;
    protected BhaashikShellJPanel bhaashikShellJPanel;

    public BhaashikConsoleReaderThread(BhaashikConsoleReader cr, JTextShell textShell, BhaashikShellJPanel bhaashikShellJPanel)
    {
        this.cr = cr;
        this.textShell = textShell;
        this.bhaashikShellJPanel = bhaashikShellJPanel;
    }

	public synchronized void run()
	{
		try
		{
			while (Thread.currentThread() == this)
			{
				try { this.wait(100);}catch(InterruptedException ie) {}
//				if (pi.available()!=0)
//				{
					String input=cr.readLine();
//					String input=this.readLine(pi);
//					textArea.append(input);

                    if(input != null && input.equals("") == false)
                        bhaashikShellJPanel.runCommand(input);
//				}
//				if (quit) return;
			}

		} catch (Exception e)
		{
			textShell.append("\nConsole reports an Internal error.");
			textShell.append("The error is: "+e);
		}

		// just for testing (Throw a Nullpointer after 1 second)
//		if (Thread.currentThread()==errorThrower)
//		{
//			try { this.wait(1000); }catch(InterruptedException ie){}
//			throw new NullPointerException("Application test: throwing an NullPointerException It should arrive at the console");
//		}
	}

//	public synchronized String readLine(PipedInputStream in) throws IOException
//	{
//		String input="";
//		do
//		{
//			int available=in.available();
//			if (available==0) break;
//			byte b[]=new byte[available];
//			in.read(b);
//			input=input+new String(b,0,b.length);
////		} while( !input.endsWith("\n") &&  !input.endsWith("\r\n") && !quit);
//		} while( !input.endsWith("\n") &&  !input.endsWith("\r\n"));
//		return input;
//	}
}
