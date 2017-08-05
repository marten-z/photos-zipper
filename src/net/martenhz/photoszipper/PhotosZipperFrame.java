package net.martenhz.photoszipper;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;

import net.martenhz.photoszipper.panels.CameraPanel;


public class PhotosZipperFrame extends JFrame implements ActionListener, WindowListener {
	
	private static final long serialVersionUID = -3770165089547308360L;
	

	public PhotosZipperFrame(final String title) {
		// How-to from: http://www.ntu.edu.sg/home/ehchua/programming/java/j4a_gui.html
		
		// Content-pane sets layout
		setLayout(new GridLayout(1,3));
		setJMenuBar(constructJMenuBar());
 
		// Content-pane adds components
		add(new CameraPanel("Camera 1"));
		add(informationPanel());
		add(new CameraPanel("Camera 2"));
 
		// Source object adds listener
		// .....
 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// Exit the program when the close-window button clicked
		setTitle(title);  // "this" JFrame sets title
		setSize(800, 600);   // "this" JFrame sets initial size (or pack())
		setVisible(true);    // show it
	}


	private JMenuBar constructJMenuBar() {
		final JMenuBar menuBar = new JMenuBar();
		
		final JMenu fileMenu = new JMenu("File");
		fileMenu.addSeparator();
		
		final JMenuItem fileExit = new JMenuItem("Exit");
		fileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		fileExit.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		fileMenu.add(fileExit);
		
		final JMenu helpMenu = new JMenu("Help");
		helpMenu.addSeparator();
		helpMenu.add(new JMenuItem("About"));
		
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		
		return menuBar;
	}
	
	private JPanel informationPanel() {
		final JPanel informationPanel = new JPanel();
		informationPanel.setLayout(new FlowLayout());
		
		final TitledBorder border = new TitledBorder("Merging options");
		informationPanel.setBorder(border);
		
		return informationPanel;
	}
	

	@Override
	public void windowActivated(final WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(final WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(final WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(final WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(final WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(final WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(final WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
