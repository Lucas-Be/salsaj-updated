//EU_HOU
package ij.gui;
import ij.IJ;
import java.awt.*;
import java.awt.event.*;

/** A modal dialog box with a one line message and
	"Don't Save", "Cancel" and "Save" buttons. */
public class SaveChangesDialog extends Dialog implements ActionListener, KeyListener {
	private Button dontSave, cancel, save;
	private boolean cancelPressed, savePressed;

	public SaveChangesDialog(Frame parent, String fileName) {
		//EU_HOU Bundle
		super(parent, IJ.getBundle().getString("DoSave"), true);
		setLayout(new BorderLayout());
		Panel panel = new Panel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		Component message;
		//EU_HOU MISSING Bundle
		if (fileName.startsWith("Save "))
			message = new Label(fileName);
		else {
			if (fileName.length()>22)
				//EU_HOU Bundle
				message = new MultiLineLabel(IJ.getBundle().getString("SaveChanges") + "\n" + "\"" + fileName + "\"?");
			else
				//EU_HOU Bundle
				message = new Label(IJ.getBundle().getString("SaveChanges") + " \"" + fileName + "\"?");
		}
		message.setFont(new Font("Dialog", Font.BOLD, 12));
		panel.add(message);
		add("Center", panel);
		
		panel = new Panel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 8));
		//EU_HOU Bundle
		save = new Button(IJ.getBundle().getString("YNCDialYes"));
		save.addActionListener(this);
		save.addKeyListener(this);
		//EU_HOU Bundle
		cancel = new Button(IJ.getBundle().getString("YNCDialCan"));
		cancel.addActionListener(this);
		cancel.addKeyListener(this);
		//EU_HOU Bundle
		dontSave = new Button(IJ.getBundle().getString("YNCDialNo"));
		dontSave.addActionListener(this);
		dontSave.addKeyListener(this);
		if (ij.IJ.isMacintosh()) {
			panel.add(dontSave);
			panel.add(cancel);
			panel.add(save);
		} else {
			panel.add(save);
			panel.add(dontSave);
			panel.add(cancel);
		}
		add("South", panel);
		if (ij.IJ.isMacintosh())
			setResizable(false);
		pack();
		GUI.centerOnImageJScreen(this);
		show();
	}
    
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==cancel)
			cancelPressed = true;
		else if (e.getSource()==save)
			savePressed = true;
		closeDialog();
	}
	
	/** Returns true if the user dismissed dialog by pressing "Cancel". */
	public boolean cancelPressed() {
		if (cancelPressed)
			ij.Macro.abort();
		return cancelPressed;
	}
	
	/** Returns true if the user dismissed dialog by pressing "Save". */
	public boolean savePressed() {
		return savePressed;
	}
	
	void closeDialog() {
		//setVisible(false);
		dispose();
	}

	public void keyPressed(KeyEvent e) { 
		int keyCode = e.getKeyCode(); 
		IJ.setKeyDown(keyCode); 
		if (keyCode==KeyEvent.VK_ENTER) 
			closeDialog(); 
		else if (keyCode==KeyEvent.VK_ESCAPE) { 
			cancelPressed = true; 
			closeDialog(); 
			IJ.resetEscape();
		} 
	} 

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

}
