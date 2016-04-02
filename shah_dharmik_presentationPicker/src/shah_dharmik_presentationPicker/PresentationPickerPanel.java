package shah_dharmik_presentationPicker;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 * This class creates the GUI layout of the application, and also handles input
 * that the user provides
 * 
 * @author Dharmik Shah
 * 
 */
public class PresentationPickerPanel extends JPanel {

	Section section;
	Student chosenStudent;
	File chosenFile;
	JButton btnPickAFile;
	JButton btnPickAStudent;
	JButton btnUpdateMarks;
	JButton btnCreateFile;
	JLabel lblStudentDetails;
	JLabel lblStudentsLeft;
	JLabel lblContentMark;
	JLabel lblDeliveryMark;
	JTextField tfContentMark;
	JTextField tfDeliveryMark;

	Color defaultColor = new JButton().getBackground();
	Color guideColor = new Color(255, 83, 83);
	Font font = (new Font("Arial", Font.BOLD, 20));
	Font lblFont = new Font("Arial", Font.BOLD, 14);

	/**
	 * Constructor for the class. Creates the GUI components (JLabel, JButton
	 * ... etc) and adds them to the JFrame
	 */
	public PresentationPickerPanel() {
		setBackground(new Color(229, 229, 229));
		setLayout(new GridLayout(10, 1));
		setPreferredSize(new Dimension(300, 400));

		btnPickAFile = new JButton("Pick A Course Code");
		btnPickAFile.setFont(font);
		btnPickAFile.setBackground(guideColor);
		btnPickAFile.addActionListener(new clickListener());

		btnPickAStudent = new JButton("Pick a Student");
		btnPickAStudent.setFont(font);
		btnPickAStudent.addActionListener(new clickListener());
		btnPickAStudent.setEnabled(false);

		btnUpdateMarks = new JButton("Update Student Marks");
		btnUpdateMarks.setFont(font);
		btnUpdateMarks.addActionListener(new clickListener());
		btnUpdateMarks.setEnabled(false);

		btnCreateFile = new JButton("Create File");
		btnCreateFile.setFont(font);
		btnCreateFile.addActionListener(new clickListener());
		btnCreateFile.setEnabled(false);

		lblStudentDetails = new JLabel("Please Select a Course Code");
		lblStudentDetails.setFont(lblFont);
		lblStudentDetails.setForeground(Color.BLUE);
		lblStudentsLeft = new JLabel("");

		tfContentMark = new JTextField("");
		tfContentMark.setEnabled(false);
		tfDeliveryMark = new JTextField("");
		tfDeliveryMark.setEnabled(false);

		lblContentMark = new JLabel("Content Mark: (0-100)");
		lblDeliveryMark = new JLabel("Delivery Mark: (0-100)");
		lblContentMark.setFont(lblFont);
		lblDeliveryMark.setFont(lblFont);

		lblStudentsLeft = new JLabel("Students left to Mark: N/A");
		lblStudentsLeft.setFont(lblFont);

		add(btnPickAFile);
		add(btnPickAStudent);
		add(lblStudentDetails);
		add(lblContentMark);
		add(tfContentMark);
		add(lblDeliveryMark);
		add(tfDeliveryMark);
		add(lblStudentsLeft);
		add(btnUpdateMarks);
		add(btnCreateFile);

	}

	/**
	 * A class that handles 'button clicked' events by the user. Also
	 * communicates with the other classes (Section/Student) to constantly
	 * update information
	 * 
	 * @author Dharmik Shah
	 * 
	 */
	private class clickListener implements ActionListener {
		/**
		 * Method that carries out tasked to be performed when the user clicks
		 * any button (task varies per button)
		 */
		public void actionPerformed(ActionEvent event) {

			if (event.getSource() == btnPickAFile) {
				try {
					chosenFile = getFile();

					section = new Section(chosenFile);

					if (!section.loadStudents(chosenFile, section)) {
						throw new Exception(
								"Invalid file format.\n Expected: FIRST NAME, LAST NAME, ID, CONTENT MARK, DELIVERY MARK\n (Error on line: "
										+ section.getErrorLine() + ")");
					}

					btnPickAFile.setEnabled(false);
					btnPickAStudent.setEnabled(true);
					btnPickAStudent.setBackground(guideColor);
					btnPickAFile.setBackground(defaultColor);

					lblStudentDetails.setText("Please select a Student");
					btnPickAFile.setText(section.getFileName());

				} catch (NullPointerException e) {
					JOptionPane
							.showMessageDialog(null, "Please Select a File.");

				}

				catch (Exception f) {
					JOptionPane.showMessageDialog(null, f.getMessage());
				}

			}

			else if (event.getSource() == btnPickAStudent) {
				tfContentMark.setEnabled(true);
				tfDeliveryMark.setEnabled(true);
				lblStudentsLeft.setText("Students left to Mark: "
						+ section.getStudentsLeft());
				chosenStudent = section.chooseRandomStudent();
				updateStudentJLabel(chosenStudent);
				btnUpdateMarks.setEnabled(true);
				btnPickAStudent.setEnabled(false);
				btnPickAStudent.setBackground(defaultColor);
				btnUpdateMarks.setBackground(guideColor);
			}

			else if (event.getSource() == btnUpdateMarks) {
				if (markValid()) {
					lblStudentsLeft.setText("Students left to Mark: "
							+ (section.getStudentsLeft() - 1));
					lblStudentDetails.setText("Name: N/A (Pick a Student)");
					chosenStudent.setContentMark(Integer.parseInt(tfContentMark
							.getText()));
					chosenStudent.setDeliveryMark(Integer
							.parseInt(tfDeliveryMark.getText()));

					section.transferStudents(chosenStudent);

					if (section.getStudentsLeft() == 0) {
						lblStudentDetails.setText("Select 'Create File'");
						btnUpdateMarks.setEnabled(false);
						btnPickAStudent.setEnabled(false);
						btnUpdateMarks.setBackground(defaultColor);
						btnCreateFile.setBackground(guideColor);
						btnCreateFile.setEnabled(true);
					} else {
						btnUpdateMarks.setEnabled(false);
						btnPickAStudent.setEnabled(true);
						btnUpdateMarks.setBackground(defaultColor);
						btnPickAStudent.setBackground(guideColor);
					}
					tfContentMark.setEnabled(false);
					tfContentMark.setText("");
					tfDeliveryMark.setEnabled(false);
					tfDeliveryMark.setText("");
				}
			}

			else if (event.getSource() == btnCreateFile) {

				btnPickAFile.setEnabled(true);
				try {
					section.createFile(chosenFile);
				} catch (Exception e) {
					System.out.println("An error occured while clicking");
				}
				JOptionPane.showMessageDialog(
						null,
						"Your file has been created at "
								+ section.getOutputFilePath());

				lblStudentDetails.setText("");
				lblStudentDetails.setText("File Created");

				btnCreateFile.setBackground(defaultColor);
				tfContentMark.setBackground(defaultColor);
				tfDeliveryMark.setBackground(defaultColor);
				btnPickAFile.setEnabled(false);
				btnCreateFile.setEnabled(false);

			}

		}

	}

	/**
	 * Method that checks to see if the user has entered a valid content and
	 * delivery mark (proper range, integer only, not leaving blank)
	 * 
	 * @return true or false, which will then allow the program to decide
	 *         whether to proceed or stop and ask the user for proper input
	 */
	private boolean markValid() {
		try {
			int contentMark = Integer.parseInt(tfContentMark.getText());
			int deliveryMark = Integer.parseInt(tfDeliveryMark.getText());

			if (contentMark < 0 || contentMark > 100 || deliveryMark < 0
					|| deliveryMark > 100) {

				JOptionPane.showMessageDialog(null,
						"Please enter in the correct range (0-100)");
				return false;
			}
			return true;
		} catch (NumberFormatException e) {

			JOptionPane.showMessageDialog(null,
					"Please fill in both boxes with proper Integers");
		}

		return false;
	}

	/**
	 * Method that updates the student JLabel in the JFrame to tell the user
	 * which student they are marking
	 * 
	 * @param chosenStudent
	 *            - the Student object, through which they can get the Student's
	 *            information
	 */
	private void updateStudentJLabel(Student chosenStudent) {
		lblStudentDetails.setText("Name: " + chosenStudent.getFirstName() + " "
				+ chosenStudent.getLastName());

	}

	/**
	 * Method that uses JFileChooser to allow the user to choose a proper .csv
	 * file to be evaluated.
	 * 
	 * @return the File that the user chose: whether it is a proper file or null
	 *         (the user closed the window)
	 */
	private File getFile() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"CSV file", "csv");
		chooser.setFileFilter(filter);
		int returnValue = chooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File inFile = new File(chooser.getSelectedFile().toString());
			return inFile;
		}
		return null;
	}

}
