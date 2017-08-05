package net.martenhz.photoszipper.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import net.martenhz.photoszipper.PhotosZipperUtils;

public class CameraPanel extends JPanel {
	
	private static final long serialVersionUID = -3200379990451057817L;
	
	private String name;
	private String directory;
	private JFileChooser fileChooser;
	private JTextField pathTextField, offsetYearsTextField, offsetHoursTextField, offsetMinutesTextField;
	
	
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
		browseButton.setName("browse");
		browseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				openDirectoryChooser();
			}
		});
		
		JLabel offsetYearsLabel = new JLabel("Years offset");
		this.offsetYearsTextField = new JTextField();
		this.offsetYearsTextField.setName("offsetYearsTextField");
		this.offsetYearsTextField.setEditable(true);
		this.offsetYearsTextField.setColumns(2);
		this.offsetYearsTextField.setText("0");
		offsetYearsLabel.setLabelFor(this.offsetYearsTextField);
		
		final JButton renameButton = new JButton("Rename pictures");
		renameButton.setName("rename");
		renameButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					final PhotosZipperUtils utils = new PhotosZipperUtils(
							getIntegerValue(offsetYearsTextField.getText()), 
							getIntegerValue(offsetHoursTextField.getText()),
							getIntegerValue(offsetMinutesTextField.getText())
							);
					utils.renamePictures(directory);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JLabel offsetHoursLabel = new JLabel("Hours offset");
		this.offsetHoursTextField = new JTextField();
		this.offsetHoursTextField.setName("offsetHoursTextField");
		this.offsetHoursTextField.setEditable(true);
		this.offsetHoursTextField.setColumns(2);
		this.offsetHoursTextField.setText("0");
		offsetHoursLabel.setLabelFor(this.offsetHoursTextField);
		
		JLabel offsetMinutesLabel = new JLabel("Minutes offset");
		this.offsetMinutesTextField = new JTextField();
		this.offsetMinutesTextField.setName("offsetMinutesTextField");
		this.offsetMinutesTextField.setEditable(true);
		this.offsetMinutesTextField.setColumns(2);
		this.offsetMinutesTextField.setText("0");
		offsetMinutesLabel.setLabelFor(this.offsetMinutesTextField);
		
		this.add(pathTextField);
		this.add(browseButton);
		this.add(offsetYearsLabel);
		this.add(offsetYearsTextField);
		this.add(offsetHoursLabel);
		this.add(offsetHoursTextField);
		this.add(offsetMinutesLabel);
		this.add(offsetMinutesTextField);
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
