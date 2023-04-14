package com.aa.act.interview.org;

import java.util.Optional;

public abstract class Organization {

	private Position root;
	private int nextId = 0; //id of next hire
	
	public Organization() {
		root = createOrganization();
	}
	
	protected abstract Position createOrganization();
	
	/**
	 * hire the given person as an employee in the position that has that title
	 * 
	 * @param person
	 * @param title
	 * @return the newly filled position or empty if no position has that title
	 */
	public Optional<Position> hire(Name person, String title) {
		//your code here
		Position positionToFill = findPosition(root, title);
		
		if (positionToFill != null) {
			positionToFill.setEmployee(Optional.of(new Employee(getNewHireId(), person)));
	
			return Optional.of(positionToFill);
		}
		
		return Optional.empty();
	}

	@Override
	public String toString() {
		return printOrganization(root, "");
	}
	
	private String printOrganization(Position pos, String prefix) {
		StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
		for(Position p : pos.getDirectReports()) {
			sb.append(printOrganization(p, prefix + "\t"));
		}
		return sb.toString();
	}

	/**
	 * Compares the title of comparandum to the passed title.  In the case they match, the comparandum is returned.  In the case they do not match, we traverse the Organization by passing the comparandum's children to findPosition (this uses the call-stack for a breadth-first search).
	 * 
	 * @param comparandum - Position being compared to our target title
	 * @param targetTitle - Title of position we are searching for
	 * @return - Position which contains targetTitle, or null if position is not found
	 */
	private Position findPosition(Position comparandum, String targetTitle) {
		if (comparandum.getTitle().equals(targetTitle)) {
			return comparandum;
		}

		for (Position directReport : comparandum.getDirectReports()) {
			Position positionReturned = findPosition(directReport, targetTitle);

			if (positionReturned != null) {
				return positionReturned;
			}
		}

		return null;
	}

	/**
	 * Returns nextId and then increments nextId for next hire
	 * @return the next id
	 */
	private int getNewHireId() {
		return nextId++;
	}
}
