package net.martenhz.photoszipper;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

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

import org.apache.commons.io.FilenameUtils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifIFD0Directory;


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
				renameFileByDate(file);
			}
		}
		
	}

	private void renameFileByDate(final File file) throws Exception {
		if (file.exists()) {
			try {
				System.out.println( "Trying to rename file: " + file.getAbsolutePath());
				
				final Metadata meta = ImageMetadataReader.readMetadata(file);
	        
		        // Read Exif Data
	            final Directory directory = meta.getFirstDirectoryOfType(ExifIFD0Directory.class);
	            
	            if(directory != null) {
	                // Read the date
	            	final Date date = directory.getDate(ExifDirectoryBase.TAG_DATETIME);
	            	final DateFormat df = DateFormat.getDateInstance();
	                df.format(date);
	                
	                final Calendar calendar = df.getCalendar();	                	                
	                calendar.setTimeInMillis(calendar.getTimeInMillis() - (calendar.get(Calendar.ZONE_OFFSET) + calendar.get(Calendar.DST_OFFSET)));
	                
	                // Do the actual renaming
	                final StringBuilder newFileName = new StringBuilder(FilenameUtils.getFullPath(file.getAbsolutePath()));
	                newFileName.append(calendar.get(Calendar.YEAR));
	                newFileName.append("-");
	                
	                final int month = (calendar.get(Calendar.MONTH) + 1);
	                final int day = calendar.get(Calendar.DAY_OF_MONTH);
	                final int hour = calendar.get(Calendar.HOUR_OF_DAY);
	                final int minute = calendar.get(Calendar.MINUTE);
	                final int second = calendar.get(Calendar.SECOND);
	                
	                newFileName.append(month < 10 ? "0" + month : month);
	                newFileName.append("-");
	                newFileName.append(day < 10 ? "0" + day : day);
	                newFileName.append("_");
	                newFileName.append(hour < 10 ? "0" + hour : hour);
	                newFileName.append(":");
	                newFileName.append(minute < 10 ? "0" + minute : minute);
	                newFileName.append(":");
	                newFileName.append(second < 10 ? "0" + second : second);
	                newFileName.append(".");
	                newFileName.append(FilenameUtils.getExtension(file.getAbsolutePath()));
	                
	                renameFile(file, newFileName);	               
	            }
			} catch (ImageProcessingException e) {
				e.getMessage();
//				throw e;
			}
	        
	    } else {
	        throw new Exception("Unable to read file: " + file.getAbsolutePath());
	    }
	}
	
	private static void renameFile(final File file, final StringBuilder newFileName) {
		renameFile(file, newFileName, 0);
	}
	
	private static void renameFile(final File file, final StringBuilder newFileName, int iteration) {
		System.out.println("New file name: " + newFileName.toString());
		final boolean isRenamed = file.renameTo(new File(newFileName.toString()));
		
		// File possibly already exists (for example when using burst mode on a camera, it can shoot multiple pictures per second)
        if(!isRenamed) {
        	iteration++;
        	
        	final String extension = FilenameUtils.getExtension(file.getAbsolutePath());
        	
        	if(iteration == 1) {
        		newFileName.delete(newFileName.length() - (extension.length() + 1), newFileName.length());
        		newFileName.append("_01");
        		newFileName.append(".");
        		newFileName.append(extension);
        	} else {
	         	if(iteration < 10) {
	         		newFileName.delete(newFileName.length() - 1 - (extension.length() + 1), newFileName.length());
	         	} else {
	         		newFileName.delete(newFileName.length() - String.valueOf(iteration).length() - (extension.length() + 1), newFileName.length());
	         	}
	         	newFileName.append(iteration);
	         	newFileName.append(".");
	         	newFileName.append(extension);
        	}
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
