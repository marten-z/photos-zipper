package nl.mdata.photoszipper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Label;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public Frame(final String title) {
		//1. Create the frame.
		super(title);		
		
		//2. Optional: What happens when the frame closes?
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//3. Create components and put them in the frame.
//		this.setIconImage(image);
		
		final Label label = new Label(title);
		this.getContentPane().add(label, BorderLayout.CENTER);
		
		final JPanel cameraA = this.getCameraPanel("Camera A");
		final JPanel cameraB = this.getCameraPanel("Camera B");
		
		this.getContentPane().add(cameraA, BorderLayout.CENTER);
		this.getContentPane().add(cameraB, BorderLayout.CENTER);
		
		//4. Size the frame.
		this.pack();
		
		//5. Show it.
		this.setVisible(true);
	}
	
	
	private JPanel getCameraPanel(final String name) {
		final JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
        panel.setVisible(true);
        panel.setName(name);
        
        final Label label = new Label(name + " label");
        panel.add(label);
		
		return panel;
	}

}
