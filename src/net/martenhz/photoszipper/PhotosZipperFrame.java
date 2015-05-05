package net.martenhz.photoszipper;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

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

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifReader;


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
		
		final TitledBorder border = new TitledBorder("Photo information");
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
			renamePicturesAndVideos(this.configuration.getDirectoryA());
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
			renamePicturesAndVideos(this.configuration.getDirectoryB());
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
	
	private void renamePicturesAndVideos(final String path) throws Exception {
		final File folder = new File(path);
		
		if(!folder.exists() || !folder.isDirectory() || !folder.canRead()) {
			throw new Exception("Folder not readable");
		}
		
		for(final File file : folder.listFiles()) {
			if(file.isDirectory()) {
				renamePicturesAndVideos(file.getAbsolutePath());
			} else if (file.isFile()) {
				renameFile(file);
			}
		}
		
	}

	private void renameFile(final File file) throws Exception {
		if (file.exists()) {
			try {
				System.out.println( "Trying to rename file: " + file.getAbsolutePath());
				
				final Metadata meta = ImageMetadataReader.readMetadata(file);
				
				for(final Directory directory : meta.getDirectories()) {
					System.out.println("Found directory: " + directory.getName());
				}
	        
		        // Read Exif Data
	            final Directory directory = meta.getFirstDirectoryOfType(ExifIFD0Directory.class);
	            
	            if(directory != null) {
	                // Read the date
	            	final Date date = directory.getDate(ExifDirectoryBase.TAG_DATETIME);
	            	final DateFormat df = DateFormat.getDateInstance();
	                df.format(date);
	                
	                final Calendar calendar = df.getCalendar();	                	                
	                calendar.setTimeInMillis(calendar.getTimeInMillis() - (calendar.get(Calendar.ZONE_OFFSET) + calendar.get(Calendar.DST_OFFSET)));
	                System.out.println("TimeZone offset: " + calendar.get(Calendar.ZONE_OFFSET));
	                
	                System.out.println( "File: " + file.getAbsolutePath());
	
	                System.out.println( "Year: " + calendar.get(Calendar.YEAR) + ", Month: " + calendar.get(Calendar.MONTH) + 1 );
	
	                System.out.println( "Date: " + date + " - " + calendar.getTime() );
	
	                System.out.println( "Tags" );
	                for(final Iterator i = directory.getTags().iterator(); i.hasNext(); )
	                {
	                    Tag tag = ( Tag )i.next();
	                    System.out.println( "\t" + tag.getTagName() + " = " + tag.getDescription() );
	                }
	                
	                System.out.println();
	                System.out.println();
	            }
			} catch (ImageProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	    } else {
	        throw new Exception("Unable to read file: " + file.getAbsolutePath());
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
