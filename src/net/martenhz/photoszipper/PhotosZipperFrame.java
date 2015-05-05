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
	
	private Configuration configuration;
	private JFileChooser fileChooser;
	private JTextField pathTextFieldA, pathTextFieldB, offsetYearTextFieldA, offsetYearTextFieldB;
	

	public PhotosZipperFrame(final String title) {
		// How-to from: http://www.ntu.edu.sg/home/ehchua/programming/java/j4a_gui.html
		
		this.configuration = new Configuration();
		
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
		
		this.pathTextFieldA = new JTextField();
		this.pathTextFieldA.setName("pathTextField1");
		this.pathTextFieldA.setEditable(false);
		this.pathTextFieldA.setColumns(20);;
		this.pathTextFieldA.setText("Choose directory");
		
		final TitledBorder border = new TitledBorder("Camera 1");
		photosPanel.setBorder(border);
		
		final JButton browseButton = new JButton("Browse");
		browseButton.setName("browse1");
		browseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				openDirectoryChooser(1);
			}
		});
		
		this.offsetYearTextFieldA = new JTextField();
		this.offsetYearTextFieldA.setName("offsetYearTextField1");
		this.offsetYearTextFieldA.setEditable(true);
		this.offsetYearTextFieldA.setColumns(5);
		this.offsetYearTextFieldA.setText("0");
		
		final JButton renameButton = new JButton("Rename pictures");
		renameButton.setName("rename1");
		renameButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					final PhotosZipperUtils utils = new PhotosZipperUtils(Integer.valueOf(offsetYearTextFieldA.getText()));
					utils.renamePictures(configuration.getDirectoryA());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		photosPanel.add(this.pathTextFieldA);
		photosPanel.add(browseButton);
		photosPanel.add(this.offsetYearTextFieldA);
		photosPanel.add(renameButton);
		
		return photosPanel;
	}
	
	private JPanel photosPanel2() {
		final JPanel photosPanel = new JPanel();
		
		this.pathTextFieldB = new JTextField();
		this.pathTextFieldB.setName("pathTextField2");
		this.pathTextFieldB.setEditable(false);
		this.pathTextFieldB.setColumns(20);
		this.pathTextFieldB.setText("Choose directory");
		
		final TitledBorder border = new TitledBorder("Camera 2");
		photosPanel.setBorder(border);
		
		final JButton browseButton = new JButton("Browse");
		browseButton.setName("browse2");
		browseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				openDirectoryChooser(2);
			}
		});
		
		this.offsetYearTextFieldB = new JTextField();
		this.offsetYearTextFieldB.setName("offsetYearTextField2");
		this.offsetYearTextFieldB.setEditable(true);
		this.offsetYearTextFieldB.setColumns(5);
		this.offsetYearTextFieldB.setText("0");
		
		final JButton renameButton = new JButton("Rename pictures");
		renameButton.setName("rename2");
		renameButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					final PhotosZipperUtils utils = new PhotosZipperUtils(Integer.valueOf(offsetYearTextFieldB.getText()));
					utils.renamePictures(configuration.getDirectoryB());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		photosPanel.add(this.pathTextFieldB);
		photosPanel.add(browseButton);
		photosPanel.add(this.offsetYearTextFieldB);
		photosPanel.add(renameButton);
		
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
		
		final TitledBorder border = new TitledBorder("Merging options");
		informationPanel.setBorder(border);
		
		return informationPanel;
	}
	
	private void openDirectoryChooser(final int panelNumber) {
		if(fileChooser.showOpenDialog(this) == fileChooser.APPROVE_OPTION) {
			openDirectory(panelNumber, fileChooser.getSelectedFile());
		}
	}
	
	private void openDirectory(final int panelNumber, final File directory) {
		final String path = directory.getAbsolutePath();
		
		if(panelNumber == 1) {
			pathTextFieldA.setText(path);
			configuration.setDirectoryA(path);
		}
		else if(panelNumber == 2) {
			pathTextFieldB.setText(path);
			configuration.setDirectoryB(path);
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
