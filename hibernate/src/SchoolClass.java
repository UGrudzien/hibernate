import java.util.Set;

public class SchoolClass implements java.io.Serializable {

	private long id;
	private int startYear;
	private Integer currentYear;
	private String profile;
	private Set<Student> student;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public Integer getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(Integer currentYear) {
		this.currentYear = currentYear;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
	public void setStudents( Set<Student> student){
		this.student = student;
	}
	public Set<Student> getStudents(){
		return student;
	}
	public String toString() {
		return "Class: " + profile + " (Started: " + getStartYear() + ", Current year: " + getCurrentYear() + ")";
	}
}