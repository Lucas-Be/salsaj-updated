//EU_HOU
package ij.gui;
import ij.*;
import java.awt.*;
import java.awt.event.*;
/*
 *  EU_HOU CHANGES
 */
import java.util.ResourceBundle;
//EU_HOU COMMENTARY: just for visibility of 'getString()'
/*
 *  EU_HOU CHANGES END
 */

/** A modal dialog box with a one line message and
	"Yes", "No" and "Cancel" buttons. */
public class YesNoCancelDialog extends Dialog implements ActionListener, KeyListener, WindowListener {
    private Button yesB, noB, cancelB;
    private boolean cancelPressed, yesPressed;
	private boolean firstPaint = true;

	public YesNoCancelDialog(Frame parent, String title, String msg) {
		//EU_HOU Bundle
		this(parent, title, msg, IJ.getBundle().getString("YNCDialYes"), IJ.getBundle().getString("YNCDialNo"));
	}

	public YesNoCancelDialog(Frame parent, String title, String msg, String yesLabel, String noLabel) {
		super(parent, title, true);
		setLayout(new BorderLayout());
		Panel panel = new Panel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		MultiLineLabel message = new MultiLineLabel(msg);
		message.setFont(new Font("Dialog", Font.PLAIN, 14));
		panel.add(message);
		add("North", panel);
		
		panel = new Panel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 8));
		if (msg.startsWith("Save")) {
			//EU_HOU Bundle =3
			yesB = new Button(IJ.getBundle().getString("YNCDialYes"));
			noB = new Button(IJ.getBundle().getString("YNCDialNo"));
			cancelB = new Button(IJ.getBundle().getString("YNCDialCan"));
		} else {
			//EU_HOU Bundle =3
			//EU_HOU COMMENTARY: yesB and noB originally used yesLabel and noLabel
			yesB = new Button(IJ.getBundle().getString("YNCDialYes"));
			noB = new Button(IJ.getBundle().getString("YNCDialNo"));
			cancelB = new Button(IJ.getBundle().getString("YNCDialCan"));
		}
		yesB.addActionListener(this);
		noB.addActionListener(this);
		cancelB.addActionListener(this);
		yesB.addKeyListener(this);
		noB.addKeyListener(this);
		cancelB.addKeyListener(this);
		if (IJ.isWindows() || Prefs.dialogCancelButtonOnRight) {
			panel.add(yesB);
			panel.add(noB);
			panel.add(cancelB);
		} else {
			panel.add(noB);
			panel.add(cancelB);
			panel.add(yesB);
		}
		if (IJ.isMacintosh())
			setResizable(false);
		add("South", panel);
		addWindowListener(this);
		GUI.scale(this);
		pack();
		yesB.requestFocusInWindow();
		GUI.centerOnImageJScreen(this);
		show();
	}
    
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==cancelB)
			cancelPressed = true;
		else if (e.getSource()==yesB)
			yesPressed = true;
		closeDialog();
	}
	
	/** Returns true if the user dismissed dialog by pressing "Cancel". */
	public boolean cancelPressed() {
		return cancelPressed;
	}

	/** Returns true if the user dismissed dialog by pressing "Yes". */
	public boolean yesPressed() {
		return yesPressed;
	}
	
	void closeDialog() {
		dispose();
	}

	public void keyPressed(KeyEvent e) { 
		int keyCode = e.getKeyCode(); 
		IJ.setKeyDown(keyCode); 
		if (keyCode==KeyEvent.VK_ENTER) {
			if (cancelB.isFocusOwner()) {
				cancelPressed = true; 
				closeDialog(); 
			} else if (noB.isFocusOwner()) {
				closeDialog(); 
			} else {
				yesPressed = true;
				closeDialog(); 
			}
		} else if (keyCode==KeyEvent.VK_Y||keyCode==KeyEvent.VK_S) {
			yesPressed = true;
			closeDialog(); 
		} else if (keyCode==KeyEvent.VK_N || keyCode==KeyEvent.VK_D) {
			closeDialog(); 
		} else if (keyCode==KeyEvent.VK_ESCAPE||keyCode==KeyEvent.VK_C) { 
			cancelPressed = true; 
			closeDialog(); 
			IJ.resetEscape();
		} 
	} 

	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode(); 
		IJ.setKeyUp(keyCode); 
	}
	
	public void keyTyped(KeyEvent e) {}

    public void paint(Graphics g) {
    	super.paint(g);
      	if (firstPaint) {
    		yesB.requestFocus();
    		firstPaint = false;
    	}
    }

	public void windowClosing(WindowEvent e) {
		cancelPressed = true; 
		closeDialog(); 
	}
    
	public void windowActivated(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	
}