package net.martenhz.photoszipper;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;


public class PhotosZipperFrame extends JFrame implements ActionListener, WindowListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MenuBar menuBar;
	

	public PhotosZipperFrame(final String title) {
		// How-to from: http://www.ntu.edu.sg/home/ehchua/programming/java/j4a_gui.html
		
		final Container cp = this.getContentPane();
 
		// Content-pane sets layout
		cp.setLayout(new GridLayout());
 
		// Allocate the GUI components
		constructMenuBar();
		setMenuBar(menuBar);
 
		// Content-pane adds components
//		cp.add(....);
 
		// Source object adds listener
		// .....
 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// Exit the program when the close-window button clicked
		setTitle(title);  // "this" JFrame sets title
		setSize(640, 480);   // "this" JFrame sets initial size (or pack())
		setVisible(true);    // show it
	}
	
	
	private MenuBar constructMenuBar() {
		this.menuBar = new MenuBar();
		
		final Menu file = new Menu("File");
		menuBar.add(file);
		
		return menuBar;
	}


	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
