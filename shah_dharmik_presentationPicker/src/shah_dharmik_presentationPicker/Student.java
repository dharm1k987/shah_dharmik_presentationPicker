package shah_dharmik_presentationPicker;

/**
 * Class that stores information about the Students that were present in the
 * file.
 * 
 * @author Dharmik Shah
 * 
 */
public class Student {

	private String firstName;
	private String lastName;
	private int id;
	private int deliveryMark;
	private int contentMark;

	/**
	 * Constructor for the Student class
	 * 
	 * @param firstName
	 *            - the students first name
	 * @param lastName
	 *            - the students last name
	 * @param id
	 *            - the students ID number
	 * @param deliveryMark
	 *            - the students delivery mark to be changed
	 * @param contentMark
	 *            - the students content mark to be changed
	 */
	public Student(String firstName, String lastName, int id, int deliveryMark,
			int contentMark) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
		this.deliveryMark = deliveryMark;
		this.contentMark = contentMark;
	}

	/**
	 * Accessor method to get the students first name
	 * 
	 * @return the students first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Accessor method to get the students last name
	 * 
	 * @return the students last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Accessor method to get the students ID number
	 * 
	 * @return the ID number
	 */
	public int getID() {
		return id;
	}

	/**
	 * Accessor method to get the students delivery mark
	 * 
	 * @return the students delivery mark
	 */
	public int getDeliveryMark() {
		return deliveryMark;
	}

	/**
	 * Accessor method to get the students content mark
	 * 
	 * @return the students content mark
	 */
	public int getContentMark() {
		return contentMark;
	}

	/**
	 * Mutator method to change the students content mark after the user has
	 * entered it
	 * 
	 * @param contentMark
	 *            - the mark to be updated
	 */
	public void setContentMark(int contentMark) {
		this.contentMark = contentMark;
	}

	/**
	 * Mutator method to change the students delivery mark after the user has
	 * entered it
	 * 
	 * @param deliveryMark
	 *            - the mark to be updated
	 */
	public void setDeliveryMark(int deliveryMark) {
		this.deliveryMark = deliveryMark;
	}

}
