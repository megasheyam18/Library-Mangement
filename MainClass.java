package com.qspiders.project.library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;


public class MainClass {

	static boolean isLogin=false;
	public static Connection connection() {
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		System.out.println("Driver is sucessfully loaded");
		
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter name that given in the workbench");
		String username=sc.next();
		System.out.println("Enter password that given in the workbench");
		String password=sc.next();
		String dburl="jdbc:mysql://localhost:3306/library_managment_system?user="+username+"&password="+password;
		Connection con= DriverManager.getConnection(dburl);
		return con;
		}
//		 catch(InputMismatchException e) {
//			System.out.println("Enter crt input");
//			sc.nextLine();
//			return null;
//		}
		catch(ClassNotFoundException |SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static void resigesterDetails(Connection con,Scanner sc)
	{
		try {
			System.out.println("Kindly Login with username and password");
			System.out.println("Enter Your Username");
			sc.nextLine();
			String username=sc.nextLine();
			System.out.println("Enter your Password");
			String Password=sc.nextLine();
		
		String query="INSERT INTO library_login (username,password,role) VALUES(?,?,'user')";
		PreparedStatement pst=con.prepareStatement(query);
		pst.setString(1, username);
		pst.setString(2,Password);
	
		int count=pst.executeUpdate();
		if(count!=0)
		{
			System.out.println("==========Resigestion is Sucessfully Continue with login======");
		}
		else {
			System.out.println("Kindly check the login information crtly");
		}
		}catch(InputMismatchException e)
		{
			System.out.println("You have enter wrong input");
			sc.nextLine();			}
		catch(SQLIntegrityConstraintViolationException e)
		{
				System.out.println("================You have already register===============");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		
		
	}
	public static void loginDetails(Connection con,Scanner sc,boolean isLogin)
	{	

		System.out.println("Enter Your Username");
		sc.nextLine();
		String username=sc.nextLine();
		System.out.println("Enter your Password");
		String Password=sc.nextLine();
		try {
		
			String query="SELECT * FROM library_login WHERE username='"+username +"' AND password='"+Password+"'";
			
			Statement stat=con.createStatement();
			ResultSet rs=stat.executeQuery(query);
			
			if(rs.next())
			{
				isLogin =true;
				MainClass main=new MainClass();
				main.userOrAdmin(con,sc,username,Password);
				System.out.println("======================You have login Sucessfully========================");
			}
			else {
				System.out.println("=================Your Login credentials is Invaild================= \n=================Login with correct credentials=================");
				System.out.println();
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	
	}
	public static void exitLogin(Connection con,Scanner sc,boolean isLogin)
	{
		
		if(isLogin)
		{
			System.out.println("You Logout Scuessfully");
		}
		else {
			
			System.out.println("you need login first");
			ask(con,sc);	
		}
		try {
		con.close();
		sc.close();
	   System.out.println("EXIT FROM LOGIN PAGE APPLICATION");
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static void userOrAdmin(Connection con,Scanner sc,String username,String Password)
	{
		try {
			String query="SELECT role FROM library_login WHERE username='"+username +"' AND password='"+Password+"'";
			Statement stat=con.createStatement();
			ResultSet rs=stat.executeQuery(query);
			
			if(rs.next())
			{
				String role= rs.getString("role");
				if(role.equalsIgnoreCase("admin"))
				{
				Admin ad=new Admin();
				ad.ask(con, sc);	
				}
			else {
				User us=new User();
				us.ask(con, sc);
			}
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public static void deletetheusers(Connection con,Scanner sc)
	{

		System.out.println("Enter the id to be deleted");
		sc.nextLine();		
		String username=sc.nextLine();
		String query="DELETE FROM library_login WHERE username='"+username+"'";
		try {
			Statement stat=con.createStatement();
			int count=stat.executeUpdate(query);
			if(count!=0) {
				System.out.println("The user is deleted sucessfully");
			}else {
				System.out.println("The user is not present in the library system");
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
	public static void ask(Connection con,Scanner sc)
	{
		
		boolean start=true;
		while(start)
		{
		System.out.println("This is a simple login console based project");
		System.out.println("\t1.Register\n\t2.Login\n\t3.Exit\n\t4.deleted the users");
		System.out.println("Pls Select any one option from above option");
		int choice=sc.nextInt();
		switch (choice) {
		case 1: 
			resigesterDetails(con,sc);
			break;
		case 2:
			loginDetails(con,sc,isLogin);
			break;
		case 3:
			exitLogin(con,sc,isLogin);
			start=false;
			break;
		case 4:
			deletetheusers(con,sc);
			break;
		default:
			System.out.println("=======Please Choose the Correct option from above Mentioned===========");
			
		}
		}
		
		
	}
	public static void main(String[] args)
	{
		
		Connection con=connection();
		if(con!=null)
		{
				System.out.println("Connection Established Sucessfully");
		}
		else {
			System.out.println("Connection Established is in error");
		}
		Scanner sc=new Scanner(System.in);
		
		ask(con,sc);
		
		
	}

}
