import java.util.ArrayList;
import java.util.Set;

import antlr.collections.List;

@SuppressWarnings("serial")
public class School implements java.io.Serializable {

	private long id;
	private String name;
	private String address;
	private Set<SchoolClass> schoolClass;
	
	

	public School() {
	}

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	

	public void setClasses( Set<SchoolClass> schoolClass){
		this.schoolClass=schoolClass;
	}
	public Set<SchoolClass> getClasses(){
		return schoolClass;
	}

	public String toString() {
		return "School: " + getName() + " (" + getAddress() + ")";
}
}
