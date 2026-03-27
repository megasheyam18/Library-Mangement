package com.qspiders.project.library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class User {

	public static void viewAllDetails(Connection con, Scanner sc) {
		System.out.println(
				"==================================================================================================");
		System.out.println("book_id\t\tbook_title\t\tauthor\t\tavailable\tissueto");
		System.out.println(
				"===================================================================================================");
		try {
			String query = "SELECT* FROM library_system";
			Statement stat = con.createStatement();
			ResultSet rs = stat.executeQuery(query);
			while (rs.next()) {

				int book_id = rs.getInt(1);
				String book_title = rs.getString(2);
				String author = rs.getString(3);
				double available = rs.getDouble(4);
				String issue = rs.getString(5);
				System.out.println(
						book_id + "\t\t" + book_title + "\t\t\t" + author + "\t\t" + available + "\t\t" + issue);
				System.out.println("----------------------------------------------------------------------------");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void ViewById(Connection con, Scanner sc) {
		System.out.println("View all the Book only by Book Id");
		int book_id = sc.nextInt();
		System.out.println(
				"==================================================================================================");
		System.out.println("book_id\t\tbook_title\t\tauthor\t\tavailable\tissueto");
		System.out.println(
				"===================================================================================================");
		try {

			String query = "SELECT * FROM library_system WHERE book_id=" + book_id;
			Statement stat = con.createStatement();
			ResultSet rs = stat.executeQuery(query);
			while (rs.next()) {

				book_id = rs.getInt(1);
				String book_title = rs.getString(2);
				String author = rs.getString(3);
				double available = rs.getDouble(4);
				String issue = rs.getString(5);
				System.out.println(
						book_id + "\t\t" + book_title + "\t\t\t" + author + "\t\t" + available + "\t\t" + issue);
				System.out.println("----------------------------------------------------------------------------");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void takeBook(Connection con, Scanner sc) {
		System.out.println("Take the Book Title(1)  OR book by ID(2) ");
		int choice = sc.nextInt();
		System.out.println("Enter the Username");
		sc.nextLine();
		String username = sc.nextLine();
		switch (choice) {
		case 1: {
			try {
				System.out.println("Enter the Book Title");
				String bookTitle = sc.nextLine();
				String query = "UPDATE library_system SET available='0',issueto='" + username + "' WHERE book_title='"
						+ bookTitle + "' AND available='1'";
				Statement stat = con.createStatement();
				int count = stat.executeUpdate(query);
				if (count != 0) {
					System.out.println("\nBook is taken by the User = " + username);
				} else {
					System.out.println("\nNo That book is not avaiable at the Moment already some other is taken");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
			break;
		case 2: {
			System.out.println("Enter the Book Id");
			sc.nextLine();
			int bookId = sc.nextInt();
			String query = "UPDATE library_system SET available='0',issueto='" + username + "'  WHERE book_id='"
					+ bookId + "' AND available='1'";
			try {
				Statement stat = con.createStatement();
				int count = stat.executeUpdate(query);
				if (count != 0) {
					System.out.println("\nYou got the Book " + username);
				} else {
					System.out.println("\nNo That book is not avaiable at the Moment already some other is taken");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + choice);
		}
	}

	public static void returnTheBook(Connection con, Scanner sc) {
		System.out.println("Enter the Book Id");
		sc.nextLine();
		int bookId = sc.nextInt();
		double available = 1;
		String name = null;
		String query = "UPDATE library_system SET available='" + available + "',issueto='" + name + "' WHERE book_id="
				+ bookId;
		try {
			Statement stat = con.createStatement();
			int count = stat.executeUpdate(query);
			if (count != 0) {
				System.out.println("\nThe book is return to the library scuesffully");
			} else {
				System.out.println("\nPls Check the Book id properly and tell again");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void exitApplication(Connection con, Scanner sc) {
		try {
			System.out.println("=======Exit From the Library Inventory System Application=======");
			System.out.println("---------------BYE--------------------");

			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void UserHowMuchBookISAvaible(Connection con, Scanner sc) {
		System.out.println(
				"==================================================================================================");
		System.out.println("book_id\t\tbook_title\t\tauthor");
		System.out.println(
				"===================================================================================================");
		try {
			String query = "SELECT book_id book_title author FROM library_system 	WHERE=";
			Statement stat = con.createStatement();
			ResultSet rs = stat.executeQuery(query);
			while (rs.next()) {

				int book_id = rs.getInt(1);
				String book_title = rs.getString(2);
				String author = rs.getString(3);
				System.out.println(book_id + "\t\t" + book_title + "\t\t\t" + author);
				System.out.println("----------------------------------------------------------------------------");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void ask(Connection con, Scanner sc) {
		User us = new User();
		boolean start = true;
		while (start) {
			System.out.println("\n==================This is Library Mangament System for User!!===================");
			System.out.println(
					"\n\t1.View all details\n\t2.View by book ID\n\t3.Take the Book for the student\n\t4.Return the Book by the Library\n\t5.Student can see How much book they are having\n\t6.Exit");
			System.out.println(
					"========================\tSelect any one option from above option========================");

			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				us.viewAllDetails(con, sc);
				break;
			case 2:
				us.ViewById(con, sc);

				break;
			case 3:
				us.takeBook(con, sc);

				break;
			case 4:
				us.returnTheBook(con, sc);
				break;
			case 5:
				us.UserHowMuchBookISAvaible(con, sc);

				break;
			case 6:
				us.exitApplication(con, sc);
				start = false;
				break;
			default:

				System.out.println("Enter the option crtly it wrong select number from 1 to 6 ");
			}
		}
	}

	public static Connection connect() {
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			String dburl = "jdbc:mysql://localhost:3306/library_managment_system?user=root&password=root";
			Connection con = DriverManager.getConnection(dburl);
			return con;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static void main(String[] args) {
		Connection con = connect();
		Scanner sc = new Scanner(System.in);
	}

}
