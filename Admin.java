package com.qspiders.project.library;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Admin {

	public static Connection connect() {
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
		    System.out.println("Driver is loaded Sucessfully");
		    String dburl="jdbc:mysql://localhost:3306/library_managment_system?user=root&password=root";
		    Connection con=DriverManager.getConnection(dburl);
		    
		    if(con!=null) {
		    	System.out.println("Connection is sucessfully");
		    }
		    else {
		    	System.out.println("Connection is not sucessfully");
		    }
		    return con;
		}catch(SQLException | ClassNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
		
	}
	public static void addBook(Connection con,Scanner sc)
	{
		try {
		System.out.println("Enter the Book Id");
		int bookId=sc.nextInt();
		sc.nextLine();
		System.out.println("Enter your Book Title ");
		String bookTitle =sc.nextLine();
		sc.nextLine();
		System.out.println("Enter the Author");
		String authorName=sc.nextLine();
		int available=1;
		String issueto=null;
		
		System.out.println("Enter your image using file path");
		String file_path=sc.nextLine();
		
		
		
			String query="INSERT INTO library_system (book_id,book_title,author,available,issueto,image) VALUES(?,?,?,?,?,?)";
			PreparedStatement pst=con.prepareStatement(query);
			pst.setInt(1, bookId);
			pst.setString(2,bookTitle);
			pst.setString(3,authorName);
			pst.setInt(4, available);
			pst.setString(5, issueto);
			
			
			FileInputStream fis=new FileInputStream(file_path);
			pst.setBinaryStream(6, fis);
			int count=pst.executeUpdate();
			if(count!=0)
			{
				System.out.println("========================The Book details is Inserted Sucesfully========================");
			}
			else {
				System.out.println("========================The Book details is not Inserted========================");
			}
			
		}catch(InputMismatchException e)
		{
			System.out.println("You have enter wrong input");
			sc.nextLine();			}
		catch(SQLIntegrityConstraintViolationException e)
		{
			System.out.println("\n========================The Id is already given======================== \n========================Please Check the ID========================");
		}
		catch(SQLException | IOException  e)
		{
			e.printStackTrace();
			
		}
		
	}

public static void updateBookDetails(Connection con,Scanner sc)
{
	
	System.out.println("Tell me that which is need to update\n\t1.Update Book Title\n\t2.Update author\n\t3.Update all the details\n");
	System.out.println("========================================================");
	System.out.println("Enter the any one option for updating");
	int choice=sc.nextInt();
	System.out.println("Enter the id to update the book");
	int id=sc.nextInt();
	switch(choice)
	{
	case 1:
		System.out.println("Enter new Book name:");
		sc.nextLine();
		String bookTitle=sc.nextLine();
		
		String query1 = "UPDATE library_system SET book_title='"+ bookTitle +"'WHERE book_id=" + id;
		try {
			Statement stat=con.createStatement();
			int count =stat.executeUpdate(query1);
			if(count!=0)
			{
				System.out.println("\nData updated Sucessfully");
			}
			else {
			    System.out.println("\nError updating data!!\n Please check the id you have entered");
			}
	}catch(InputMismatchException e)
		{
			System.out.println("You have enter wrong input");
			sc.nextLine();			}catch(SQLException e)
		{
			e.printStackTrace();
		}
		break;
	case 2:
		System.out.println("Enter a new author name for book");
		sc.nextLine();
		String bookkAuthor=sc.nextLine();
		String query2 = "UPDATE library_system SET author='"+ bookkAuthor +"'WHERE book_id=" + id;
		try {
			Statement stat=con.createStatement();
			int count =stat.executeUpdate(query2);
			if(count!=0)
			{
				System.out.println("\nData updated Sucessfully");
			}
			else {
			    System.out.println("\nError updating data!!\n Please check the id you have entered");
			}
	}catch(SQLException e)
		{
			e.printStackTrace();
		}
		break;
	case 3:
		sc.nextLine();
		System.out.println("Enter new Book name:");
		String book_title=sc.nextLine();
		System.out.println("Enter a new author name for book");
		String author_name =sc.nextLine();
		
		String query = "UPDATE library_system SET book_title='"+ book_title + 
	               "',author='" + author_name + 
	               "' WHERE book_id=" + id;
		try {
			Statement stat=con.createStatement();
			int count =stat.executeUpdate(query);
			if(count!=0)
			{
				System.out.println("\nData updated Sucessfully");
			}
			else {
			    System.out.println("\nError updating data!!\n Please check the id you have entered");
			}
	}catch(SQLException e)
		{
			e.printStackTrace();
		}
		break;
		default:
			System.out.println("Enter the option correctly da");
}
}

public static void deleteBookDetails(Connection con,Scanner sc)
{
	System.out.println("Enter the id to be deleted");
	int id=sc.nextInt();
	String query="DELETE FROM library_system WHERE book_id="+id;
	try {
		Statement stat=con.createStatement();
		int count=stat.executeUpdate(query);
		if(count!=0) {
			System.out.println("The Book detail is delete");
		}else {
			System.out.println("The Book details is not delete\n PLease check the id");
		}
		
	}
	catch(InputMismatchException e)
	{
		System.out.println("You have enter wrong input");
		sc.nextLine();			}catch(SQLException e)
	{
		e.printStackTrace();
	}
	
	
}
public static void viewAllDetails(Connection con,Scanner sc)
{
	System.out.println("==================================================================================================");
	System.out.println("book_id\t\tbook_title\t\tauthor\t\tavailable\tissueto");
	System.out.println("===================================================================================================");
	try {
		String query="SELECT* FROM library_system";
		Statement stat= con.createStatement();
		ResultSet rs=stat.executeQuery(query);
		while(rs.next())
		{
			
			 int book_id=rs.getInt(1);
				String book_title =rs.getString(2);
				String author=rs.getString(3);
				double available=rs.getDouble(4);
				String issue=rs.getString(5);
				System.out.println(book_id+"\t\t" +book_title+"\t\t\t" +author+"\t\t"+available+"\t\t"+issue);
			System.out.println("----------------------------------------------------------------------------");
		}
	}catch(SQLException e)
	{
		e.printStackTrace();
	}
	
}

public static void ViewById(Connection con,Scanner sc)
{
	System.out.println("View all the Book only by Book Id");
		int book_id=sc.nextInt();
		System.out.println("==================================================================================================");
		System.out.println("book_id\t\tbook_title\t\tauthor\t\tavailable\tissueto");
		System.out.println("===================================================================================================");
	try {
		
		String query="SELECT * FROM library_system WHERE book_id="+book_id;
		Statement stat=con.createStatement();
		ResultSet rs=stat.executeQuery(query);
		while(rs.next())
		{
			  	book_id=rs.getInt(1);
				String book_title =rs.getString(2);
				String author=rs.getString(3);
				double available=rs.getDouble(4);
				String issue=rs.getString(5);
				System.out.println(book_id+"\t\t" +book_title+"\t\t\t" +author+"\t\t"+available+"\t\t"+issue);
			System.out.println("----------------------------------------------------------------------------");
		}
	}catch(SQLException e)
	{
		e.printStackTrace();
	}
}

public static void roleChange(Connection con,Scanner sc)
{
	System.out.println("Enter the is choice for to user to admin(1)");
	System.out.println("Enter the is choice for to admin to user(2)");
	int choice=sc.nextInt();
	
	switch (choice) {
	case 1: {
		System.out.println("Enter the Username:");
		sc.nextLine();
		
		String username=sc.nextLine();
		
		String query1 = "UPDATE library_login SET role= 'Admin' WHERE username='"+username+"'";
		try {
			Statement stat=con.createStatement();
			int count =stat.executeUpdate(query1);
			if(count!=0)
			{
				System.out.println("\nRole is prompted to admin Sucessfully");
			}
			else {
			    System.out.println("\nError updating data!!\n Please check the Username you have entered");
			}
	}catch(InputMismatchException e)
		{
			System.out.println("You have enter wrong input");
			sc.nextLine();			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		
	}
	break;
	case 2:{
		System.out.println("Enter the Username:");
		sc.nextLine();
		
		String username=sc.nextLine();
		
		String query1 = "UPDATE library_login SET role= 'user' WHERE username='"+username+"'";
		try {
			Statement stat=con.createStatement();
			int count =stat.executeUpdate(query1);
			if(count!=0)
			{
				System.out.println("\nRole is deprompted  to user Sucessfully");
			}
			else {
			    System.out.println("\nError updating data!!\n Please check the Username you have entered");
			}
	}catch(InputMismatchException e)
		{
			System.out.println("You have enter wrong input");
			sc.nextLine();			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	default:
		System.out.println("Please enter the Crt Option");
	}
	
	
}
public static void exitApplication(Connection con,Scanner sc)
{

	System.out.println("=======Exit From the Library Inventory System Application=======");
	System.out.println("---------------BYE--------------------");

	

	
	
}

		public static void ask(Connection con,Scanner sc) {
			Admin ad=new Admin();
			boolean start=true;
			while(start)
			{
				System.out.println("\n==================This is Library Mangament System!!===================");
				System.out.println("\n\t1.Add Book\n\t2.Update Book\n\t3.View all details\n\t4.View by book ID\n\t5.Role change user to Admin\n\t6.Delete Book details\n\t7.Exit");
				System.out.println("========================\tSelect any one option from above option========================");
				
				int choice=sc.nextInt();
				switch(choice)
				{
				case 1:
					ad.addBook(con,sc);
					break;
				case 2:
					ad.updateBookDetails(con,sc);
				
					break;
				case 3:
					ad.viewAllDetails(con,sc);
					 
					break;
				case 4:
					ad.ViewById(con,sc);
				    break;
				case 5:
					ad.roleChange(con,sc);
					 break;
				case 6:
					ad.deleteBookDetails(con,sc);
					break;
				case 7:
					ad.exitApplication(con,sc);
					start=false;
					break;
				default:
					
					System.out.println("Enter the option crtly it wrong select number from 1 to 6 ");
				}
				
				
			}
		}
	
public static void main(String[] args) {
	Connection con=connect();
	Scanner sc=new Scanner(System.in);
	


}
}
