package net.martenhz.photoszipper;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.DefaultListModel;
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
	
	private Configuration configuration;
	private JFileChooser fileChooser;
	private JTextField textFieldA, textFieldB;
	private JList<String> fileListA = new JList<>(), fileListB = new JList<>();
	

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
		
		this.textFieldA = new JTextField();
		this.textFieldA.setName("textField1");
		this.textFieldA.setEditable(false);
		this.textFieldA.setColumns(20);;
		this.textFieldA.setText("Choose directory");
		
		final TitledBorder border = new TitledBorder("Camera 1");
		photosPanel.setBorder(border);
		
		final JButton button = new JButton("Browse");
		button.setName("button1");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				openDirectoryChooser(1);
			}
		});
		
		this.fileListA.setName("fileList1");
		this.fileListA.setAutoscrolls(false);
		
		photosPanel.add(this.textFieldA);
		photosPanel.add(button);
		photosPanel.add(this.fileListA);
		
		return photosPanel;
	}
	
	private JPanel photosPanel2() {
		final JPanel photosPanel = new JPanel();
		
		this.textFieldB = new JTextField();
		this.textFieldB.setName("textField2");
		this.textFieldB.setEditable(false);
		this.textFieldB.setColumns(20);
		this.textFieldB.setText("Choose directory");
		
		final TitledBorder border = new TitledBorder("Camera 2");
		photosPanel.setBorder(border);
		
		final JButton button = new JButton("Browse");
		button.setName("button2");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				openDirectoryChooser(2);
			}
		});
		
		this.fileListB.setName("fileList2");
		this.fileListB.setAutoscrolls(true);
		
		photosPanel.add(this.textFieldB);
		photosPanel.add(button);
		photosPanel.add(this.fileListB);
		
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
			textFieldA.setText(path);
			configuration.setDirectoryA(path);
			fillAndShowListA();
		}
		else if(panelNumber == 2) {
			textFieldB.setText(path);
			configuration.setDirectoryB(path);
			fillAndShowListB();
		}
	}


	private void fillAndShowListA() {
		try {
			final DefaultListModel model = readDirectoriesAndFiles(this.configuration.getDirectoryA());
			this.fileListA.setModel(model);
			this.fileListA.printAll(getGraphics());
			Utils.renamePictures(this.configuration.getDirectoryA());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private void fillAndShowListB() {
		try {
			final DefaultListModel model = readDirectoriesAndFiles(this.configuration.getDirectoryB());
			this.fileListB.setModel(model);
			this.fileListB.printAll(getGraphics());
			Utils.renamePictures(this.configuration.getDirectoryB());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}


	private DefaultListModel readDirectoriesAndFiles(final String path) throws Exception {
		final File folder = new File(path);
		
		if(!folder.exists() || !folder.isDirectory() || !folder.canRead()) {
			throw new Exception("Folder not readable");
		}
		
		final DefaultListModel model = new DefaultListModel();
		for(final File f : folder.listFiles()) {
			// Ignore hidden files
			if(!f.isHidden()) {
				model.addElement(f);
			} 
		}
		return model;
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
