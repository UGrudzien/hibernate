import java.util.Set;

public class Teacher {
	private long id;

	private String name;
	private String surname;
	private Set<SchoolClass>schoolClass;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public Set<SchoolClass> getClasses() {
		return schoolClass;
	}
	public void setClasses(Set<SchoolClass> schoolClass) {
		this.schoolClass = schoolClass;
	}
	public String toString(){
		return "Teachers: " + getName()+ " "+ getSurname() + " "+ getClasses();
	}
}
