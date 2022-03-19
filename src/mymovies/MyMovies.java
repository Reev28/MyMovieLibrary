package mymovies;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author reevd
 */
public class MyMovies {

    private static Connection con;
  //  private Statement st;
    private static boolean hasData = false;
    
    
    public ResultSet displayMovies() throws ClassNotFoundException, SQLException {
        System.out.println("DISPLAYING");
        if(con==null){System.out.println("No Connection yet");getConnection();}
        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("SELECT * FROM Movies");
        return res;
    }
    
      public ResultSet actorMovies(String actor) throws ClassNotFoundException, SQLException {
        System.out.println("Inside actorMovies");
        if(con==null){System.out.println("No Connection yet");getConnection();}
        Statement state = con.createStatement();
        
     
        PreparedStatement prep = con.prepareStatement("SELECT * FROM Movies WHERE actor= ?");
        prep.setString(1, actor);
        ResultSet res = prep.executeQuery();
        return res;
    }
         
    
    
            
    
    public void getConnection() throws ClassNotFoundException, SQLException
    {  
        Class.forName("org.sqlite.JDBC");
        con=DriverManager.getConnection("jdbc:sqlite:Moviesdb.db");
        initialise();
        System.out.println("CONNECTION SUCCESSFUL");
    }
    private void initialise() throws SQLException, ClassNotFoundException {
        if (!hasData) { 
            //System.out.println("Empty");
            hasData = true;
            Statement state = con.createStatement();
            ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='Movies'");
            //System.out.println("Check if exists");
            if(!res.next()) {
                System.out.println("Movies table NOT FOUND");
                System.out.println("CREATING Movies table with values");
                //creating Movie Table 
                Statement state2 = con.createStatement();
                state2.execute("CREATE TABLE Movies (id integer,"+"name varchar(60),"
                               +"actor varchar(60),"+"actress varchar(60),"
                               +"director varchar(60),"+"year integer,"+"primary key(id));");
                 
                //Insert Data
                PreparedStatement prep = con.prepareStatement("INSERT INTO Movies values(?,?,?,?,?,?);");
                prep.setString(2, "Avengers: Endgame");
                prep.setString(3, "Robert");
                prep.setString(4, "Brie Larson");
                prep.setString(5, "Anthony Russo");
                prep.setInt(6, 2019);
                prep.execute();
                
                PreparedStatement prep2 = con.prepareStatement("INSERT INTO Movies values(?,?,?,?,?,?);");
                prep2.setString(2, "Avengers: Infinity War");
                prep2.setString(3, "Robert");
                prep2.setString(4, "Scarlett Johansson");
                prep2.setString(5, "Joe Russo1");
                prep2.setInt(6, 2018);     
                prep2.execute();
                
                addMovie("Shang-chi","Simu","Awkwafina","Destin Daniel Cretton",2021);
                addMovie("Doctor Strange","Benedict","Tilda Swinton","Scott Derrickson",2016);
                addMovie("Black Panther","Chadwick","Latita Wright","Ryan Coogler",2018);
                addMovie("Ant-Man","Paul","Evangeline Lilly","Peyton Reed",2015);
                addMovie("Iron Man","Robert","Gwyneth Paltrow","Shane Black",2008);
                        
            }
        }
        
    }
    
public void addMovie(String name, String actor, String actress, String director, int year) throws ClassNotFoundException, SQLException {
        if(con==null){getConnection();}
                PreparedStatement prep = con.prepareStatement("INSERT INTO Movies values(?,?,?,?,?,?);");
                prep.setString(2, name);
                prep.setString(3, actor);
                prep.setString(4, actress);
                prep.setString(5, director);
                prep.setInt(6, year); 
                prep.execute();        
   
    }
    public static void main(String[] args) {
        int flag=0;
        Scanner sc=new Scanner(System.in);
        MyMovies obj=new MyMovies();
        ResultSet rs;
        try {
            rs = obj.displayMovies();
            System.out.println("id"+"| "+"name"+" | "+"actor"+" | "+"actress"+" | "+"director"+" | "+"year");
            while(rs.next()) {
                System.out.println(rs.getInt("id")+" | "
                        +rs.getString("name")+" | "
                        +rs.getString("actor")+" | "
                        +rs.getString("actress")+" | "
                        +rs.getString("director")+" | "
                        +rs.getInt("year"));
            }
            System.out.println("Enter Actor name");
             String str=sc.nextLine(); 
            
            rs = obj.actorMovies(str);
            System.out.println("id"+"| "+"name"+" | "+"actor"+" | "+"actress"+" | "+"director"+" | "+"year");
            while(rs.next()) {             
                System.out.println(rs.getInt("id")+" | "
                        +rs.getString("name")+" | "
                        +rs.getString("actor")+" | "
                        +rs.getString("actress")+" | "
                        +rs.getString("director")+" | "
                        +rs.getInt("year"));
                flag=1;
            }
             if(flag==0){System.out.println("Actor Not Found");};

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MyMovies.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MyMovies.class.getName()).log(Level.SEVERE, null, ex);
        }
               
    }
    
}
