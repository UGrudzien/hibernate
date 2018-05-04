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
		main.addNewData();
	
		main.executeQueries();
		main.numberOfSchool();
		main.numberOFStudents();
		main.numberOFSchoolWithMoreThanTwoClasses();
		main.removeProfile();
		main.testing();
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
	private  void addNewData2(){
		School school = new School();
		school.setName("UJCM");
		school.setAddress("Anny 12");

		Transaction transaction = session.beginTransaction();
		session.save(school); // gdzie newSchool to instancja nowej szko³y
		transaction.commit();
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
		
		Student anna= new Student();
		anna.setName("Anna");
		anna.setSurname("A");
		
		Student krzysztof = new Student();
		krzysztof.setName("Krzysztof");
		krzysztof.setSurname("k");
		
		Student tomasz=new Student();
		tomasz.setName("Tomasz");
		tomasz.setSurname("T");
		
//		Student aurelia = new Student();
//		aurelia.setName("Aurelia");
//		aurelia.setSurname("Au");
		
		Set<SchoolClass> classes = new HashSet<SchoolClass>();
		
		Set<Student> studentsMan = new HashSet<Student>();
		Set<Student> studentWoman = new HashSet<Student>();
	
		studentsMan.add(krzysztof);
		studentsMan.add(tomasz);
		studentWoman.add(anna);
//		studentWoman.add(aurelia);
		anatomy.setStudents(studentWoman);
		biology.setStudents(studentsMan);
		classes.add(anatomy);
		classes.add(biology);
		school.setClasses(classes);
	
		Transaction transaction = session.beginTransaction();
		session.save(school); // gdzie newSchool to instancja nowej szko³y
		transaction.commit();
	}
	private void executeQueries() {
        String hql = "FROM School s WHERE s.name ='EU'";
        Query query = session.createQuery(hql);
        List <School>results = query.list();
		Transaction transaction = session.beginTransaction();
        for(School s: results){
        	session.delete(s);
        }
    	transaction.commit();
//        System.out.println(results);
}
	public void numberOfSchool(){
		String hql="SELECT COUNT(s) FROM School s";
		 Query query = session.createQuery(hql);
		 List results= query.list();
		 System.out.println("Number of schools " +results);	
	}
	
	public void numberOFStudents(){
		String hql ="SELECT COUNT (stud) FROM Student stud";
		 Query query = session.createQuery(hql);
		 List results= query.list();
		 System.out.println("Number of students "+results);
	}
	public void numberOFSchoolWithMoreThanTwoClasses(){
		String hql = "SELECT COUNT(s) FROM School s WHERE size(s.classes)>=2";
		 Query query = session.createQuery(hql);
		 List results= query.list();
		 System.out.println("Number of school with more than 2 classes "+results);
	}
	public void removeProfile(){
		String hql = "SELECT s FROM School s INNER JOIN s.classes classes WHERE classes.profile = 'mat-fiz' AND classes.currentYear >=2";
		 Query query = session.createQuery(hql);
		 List results= query.list();
		 System.out.println("remove mat-fiz profile "+results);
	}
	public void testing(){
		Query query = session.createQuery("from School where id= :id");
		query.setLong("id", 2);
		School school = (School) query.uniqueResult();
		school.setAddress("Nowy Adress");
		Transaction transaction = session.beginTransaction();
		session.save(school);
		transaction.commit();
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
