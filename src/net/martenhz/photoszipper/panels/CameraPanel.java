package net.martenhz.photoszipper.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import net.martenhz.photoszipper.PhotosZipperUtils;

public class CameraPanel extends JPanel {
	
	private static final long serialVersionUID = -3200379990451057817L;
	
	private String name;
	private String directory;
	private JFileChooser fileChooser;
	private JTextField pathTextField, offsetYearTextField, offsetHourTextField;
	
	
	public CameraPanel(final String name) {
		this.name = name;
		setupPanel();
	}
	
	
	private void setupPanel() {
		this.fileChooser = new JFileChooser(new File("."));
		this.fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		this.pathTextField = new JTextField();
		this.pathTextField.setName("pathTextField");
		this.pathTextField.setEditable(false);
		this.pathTextField.setColumns(20);
		this.pathTextField.setText("Choose directory");
		
		final TitledBorder border = new TitledBorder(this.name);
		this.setBorder(border);
		
		final JButton browseButton = new JButton("Browse");
		browseButton.setName("browse1");
		browseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				openDirectoryChooser();
			}
		});
		
		this.offsetYearTextField = new JTextField();
		this.offsetYearTextField.setName("offsetYearTextField");
		this.offsetYearTextField.setEditable(true);
		this.offsetYearTextField.setColumns(5);
		this.offsetYearTextField.setText("Year offset");
		
		final JButton renameButton = new JButton("Rename pictures");
		renameButton.setName("rename");
		renameButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					final PhotosZipperUtils utils = new PhotosZipperUtils(
							getIntegerValue(offsetYearTextField.getText()), getIntegerValue(offsetHourTextField.getText())
							);
					utils.renamePictures(directory);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		this.offsetHourTextField = new JTextField();
		this.offsetHourTextField.setName("offsetHourTextField");
		this.offsetHourTextField.setEditable(true);
		this.offsetHourTextField.setColumns(5);
		this.offsetHourTextField.setText("Hour offset");
		
		this.add(pathTextField);
		this.add(browseButton);
		this.add(offsetYearTextField);
		this.add(offsetHourTextField);
		this.add(renameButton);
	}
	
	private int getIntegerValue(final String textFieldValue) {
		return Integer.valueOf(textFieldValue.replaceAll("[^\\d.-]", ""));		
	}
	
	private void openDirectoryChooser() {
		if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			openDirectory(fileChooser.getSelectedFile());
		}
	}
	
	private void openDirectory(final File directory) {
		final String path = directory.getAbsolutePath();
		
		pathTextField.setText(path);
		this.directory = path;
	}
}
