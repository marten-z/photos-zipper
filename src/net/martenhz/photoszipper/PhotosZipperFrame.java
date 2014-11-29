package net.martenhz.photoszipper;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;


public class PhotosZipperFrame extends JFrame implements ActionListener, WindowListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JFileChooser fileChooser;
	private JTextField textField1, textField2;
	private JList<String> fileList1 = new JList<>(), fileList2 = new JList<>();
	

	public PhotosZipperFrame(final String title) {
		// How-to from: http://www.ntu.edu.sg/home/ehchua/programming/java/j4a_gui.html
		
		fileChooser = new JFileChooser(new File("."));
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		// Content-pane sets layout
		setLayout(new GridLayout(1,3));
		setJMenuBar(constructJMenuBar());
 
		// Content-pane adds components
		add(photosPanel1());
		add(informationPanel());
		add(photosPanel2());
 
		// Source object adds listener
		// .....
 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// Exit the program when the close-window button clicked
		setTitle(title);  // "this" JFrame sets title
		setSize(800, 600);   // "this" JFrame sets initial size (or pack())
		setVisible(true);    // show it
	}
	
	
	private JPanel photosPanel1() {
		final JPanel photosPanel = new JPanel();
		
		this.textField1 = new JTextField();
		this.textField1.setName("textField1");
		this.textField1.setEditable(false);
		this.textField1.setColumns(20);
		this.textField1.setText("Choose directory");
		
		final TitledBorder border = new TitledBorder("Camera 1");
		photosPanel.setBorder(border);
		
		final JButton button = new JButton("Browse");
		button.setName("button1");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				openFileChooser(1);
			}
		});
		
		this.fileList1.setName("fileList1");
		this.fileList1.setAutoscrolls(true);
		
		photosPanel.add(this.textField1);
		photosPanel.add(button);
		photosPanel.add(this.fileList1);
		
		return photosPanel;
	}
	
	private JPanel photosPanel2() {
		final JPanel photosPanel = new JPanel();
		
		this.textField2 = new JTextField();
		this.textField2.setName("textField2");
		this.textField2.setEditable(false);
		this.textField2.setColumns(20);
		this.textField2.setText("Choose directory");
		
		final TitledBorder border = new TitledBorder("Camera 2");
		photosPanel.setBorder(border);
		
		final JButton button = new JButton("Browse");
		button.setName("button2");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				openFileChooser(2);
			}
		});
		
		this.fileList2.setName("fileList2");
		this.fileList2.setAutoscrolls(true);
		
		photosPanel.add(this.textField2);
		photosPanel.add(button);
		photosPanel.add(this.fileList2);
		
		return photosPanel;
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
		
		final TitledBorder border = new TitledBorder("Photo information");
		informationPanel.setBorder(border);
		
		return informationPanel;
	}
	
	private void openFileChooser(final int panelNumber) {
		if(fileChooser.showOpenDialog(this) == fileChooser.APPROVE_OPTION) {
			openFile(panelNumber, fileChooser.getSelectedFile());
		}
	}
	
	private void openFile(final int panelNumber, final File file) {
		if(panelNumber == 1) {
			textField1.setText(file.getAbsolutePath());
		}
		else if(panelNumber == 2) {
			textField2.setText(file.getAbsolutePath());
		}
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
