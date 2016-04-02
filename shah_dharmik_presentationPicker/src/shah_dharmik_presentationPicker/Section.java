package shah_dharmik_presentationPicker;

import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

/**
 * Class that handles the creation of a section object and methods associated
 * with the section itself
 * 
 * @author Dharmik Shah
 * 
 */
public class Section {
	private String sectionName;
	private ArrayList<Student> listOfStudents = new ArrayList<Student>();
	private ArrayList<Student> completedStudents = new ArrayList<Student>();
	private String filePath;
	private String fileName;
	private String firstLineOfFile;
	private int errorOnLine;

	/**
	 * Constructor for the Section class
	 * 
	 * @param inFile
	 *            - the file that the user chose
	 */
	public Section(File inFile) {
		this.sectionName = inFile.getName();
		this.filePath = inFile.getParent();
		fileName = sectionName.substring(0, sectionName.lastIndexOf('.'));

	}

	/**
	 * Accessor method to get the name of the file only (with the extension)
	 * 
	 * @return the name of the file (ex. ICS4UP)
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Method to load the students from the file the user chose
	 * 
	 * @param chosenFile
	 *            - the file that the user chose to be marked
	 * @param section
	 *            - the section
	 * @return true or false, indicating whether the file was valid or not,
	 *         which will be handled in the PresentationPickerPanel class
	 */
	public boolean loadStudents(File chosenFile, Section section) {
		Scanner in = null;
		int MAX_COMMAS = 5;
		errorOnLine = 0;
		try {
			in = new Scanner(chosenFile);
			String line = "";
			String[] studentInfo;
			firstLineOfFile = in.nextLine();
			while (in.hasNext()) {
				errorOnLine++;
				line = in.nextLine();
				studentInfo = line.split(",");
				if (studentInfo.length == MAX_COMMAS) {
					section.addStudent(new Student(studentInfo[0],
							studentInfo[1], Integer.valueOf(studentInfo[2]),
							Integer.valueOf(studentInfo[3]), Integer
									.valueOf(studentInfo[4])));

				}

				else {
					in.close();
					throw new Exception();
				}

			}
			in.close();
			return true;
		} catch (Exception e) {
			return false;

		}

	}

	/**
	 * Method that adds students to the Array List of type Student
	 * 
	 * @param student
	 *            - the student in the file
	 */
	private void addStudent(Student student) {
		listOfStudents.add(student);

	}

	/**
	 * Method that transfers students from one Array List to another indicating
	 * that they have been marked
	 * 
	 * @param student
	 *            - the student that the program randomly chose to be marked
	 */
	public void transferStudents(Student student) {
		completedStudents.add(student);
		listOfStudents.remove(student);
	}

	/**
	 * Method that chooses a random student from the listOfStudents Array List
	 * which will then be evaluated
	 * 
	 * @return the student that the program chose
	 */
	public Student chooseRandomStudent() {
		Random rand = new Random();
		int choice = rand.nextInt(listOfStudents.size());
		return listOfStudents.get(choice);
	}

	/**
	 * Accessor method indicating how many students are left to be marked
	 * 
	 * @return the size of the Array List: listOfStudents
	 */
	public int getStudentsLeft() {

		return listOfStudents.size();
	}

	/**
	 * Accessor method indicating what line there is an error on (first error,
	 * if any)
	 * 
	 * @return the line the error exists on
	 */
	public int getErrorLine() {
		return errorOnLine;
	}

	/**
	 * A method that creates the new file in the same directory that the user
	 * chose a file
	 * 
	 * @param chosenFile
	 *            - the file that the user originally chose to be evaulated
	 */
	public void createFile(File chosenFile) {
		filePath = chosenFile.getParent();
		chosenFile = new File(getOutputFilePath());

		FileWriter out = null;
		try {
			out = new FileWriter(chosenFile);
		} catch (IOException e) {

			JOptionPane.showMessageDialog(null, "An unknown error occured");
		}

		BufferedWriter writeFile = new BufferedWriter(out);

		try {
			writeFile.write(firstLineOfFile);
			writeFile.newLine();

			for (Student student : completedStudents) {
				writeFile.write(student.getFirstName() + ","
						+ student.getLastName() + "," + student.getID() + ","
						+ student.getContentMark() + ","
						+ student.getDeliveryMark());
				writeFile.newLine();
			}

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"An error occured whilst trying to write to the file");
		}

		try {
			writeFile.close();
			out.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"An error occured while trying to close the writers");
		}

	}

	/**
	 * Accessor method that tells the user where the file will be created
	 * 
	 * @return the file path of the new file that will be created
	 */
	public String getOutputFilePath() {
		return filePath + "\\" + fileName + "-FINAL.csv";
	}

}
