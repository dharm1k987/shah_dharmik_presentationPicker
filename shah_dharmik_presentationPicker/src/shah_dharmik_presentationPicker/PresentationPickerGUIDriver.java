package shah_dharmik_presentationPicker;

import javax.swing.*;

/**
 * This main class initiates the graphics through the PresentationPickerPanel
 * class and creates a frame
 * 
 * @author Dharmik Shah
 * 
 */
public class PresentationPickerGUIDriver {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Presentation Picker");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new PresentationPickerPanel());
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}

}
