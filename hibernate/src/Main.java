import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main {

	 Session session;

	public static void main(String[] args) {
		Main main = new Main();
//		main.printSchools();
//		main.addNewData();
		main.executeQueries();
		main.close();
		
		
	}

	public Main() {
		session = HibernateUtil.getSessionFactory().openSession();
	}

	public void close() {
		session.close();
		HibernateUtil.shutdown();
	}

	private void printSchools() {
		Criteria crit = session.createCriteria(School.class);
		List<School> schools = crit.list();
		Criteria crit2 = session.createCriteria(SchoolClass.class);
		List<SchoolClass> classes = crit2.list();

		System.out.println("### Schools and classes and students");
		for (School s : schools) {
			System.out.println(s);
			System.out.println(s.getClasses());
			for(SchoolClass schoolclass: s.getClasses()){
				System.out.println(schoolclass.getStudents());}
//			System.out.println(s.getStudents());
			
		}

	}
	private  void addNewData(){
		School school = new School();
		school.setName("UJCM");
		school.setAddress("Anny 12");
		SchoolClass anatomy = new SchoolClass();
		anatomy.setProfile("anatomy");
		anatomy.setCurrentYear(4);
		anatomy.setStartYear(1);
		SchoolClass biology = new SchoolClass();
		Student Anna= new Student();
		Anna.setName("Anna");
		Anna.setSurname("A");
		Student Krzysztof = new Student();
		Krzysztof.setName("Krzysztof");
		Krzysztof.setSurname("k");
		Student Tomasz=new Student();
		Tomasz.setName("Tomasz");
		Tomasz.setSurname("T");
		Student Aurelia = new Student();
		Aurelia.setName("Aurelia");
		Aurelia.setSurname("Au");
		Set<SchoolClass> classes = new HashSet<SchoolClass>();
		Set<Student> studentsMan = new HashSet<Student>();
		Set<Student> studentWoman = new HashSet<Student>();
		classes.add(anatomy);
		classes.add(biology);
		studentsMan.add(Krzysztof);
		studentsMan.add(Tomasz);
		studentWoman.add(Anna);
		studentWoman.add(Aurelia);
		

		school.setClasses(classes);
		anatomy.setStudents(studentWoman);
		biology.setStudents(studentsMan);
		Transaction transaction = session.beginTransaction();
		session.save(school); // gdzie newSchool to instancja nowej szko³y
		transaction.commit();
	}
	private void executeQueries() {
        String hql = "FROM School";
        Query query = session.createQuery(hql);
        List results = query.list();
        System.out.println(results);
}

	private void jdbcTest() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("org.sqlite.JDBC");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection("jdbc:sqlite:school.db", "", "");

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM schools";
			ResultSet rs = stmt.executeQuery(sql);

			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				String name = rs.getString("name");
				String address = rs.getString("address");

				// Display values
				System.out.println("Name: " + name);
				System.out.println(" address: " + address);
			}
			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
	}// end jdbcTest
	

	

}
