package me.boykev.kdr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.bukkit.Bukkit;

public class SqlManager {
	private Main instance;
	public Connection con;
	private DatabaseManager db;
	private String host, database, username, password;
	private int port;
	
    public SqlManager(Main main) {
		this.instance = main;
	}
    
    public void openConnection() throws SQLException, ClassNotFoundException {
    	db = new DatabaseManager(instance);
    	host = db.getConfig().getString("db.host");
    	database = db.getConfig().getString("db.database");
    	port = db.getConfig().getInt("db.port");
    	username = db.getConfig().getString("db.username");
    	password = db.getConfig().getString("db.password");
    if (con != null && !con.isClosed()) {
        return;
    }
 
    synchronized (this) {
        if (con != null && !con.isClosed()) {
            return;
        }
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?useSSL=false", this.username, this.password);
    }
}
    
	public void makePlot(String plot, String info) {
        try {
            openConnection();
            Statement statement = con.createStatement();   
            statement.executeUpdate("INSERT INTO KadasterRegister (plot, type) VALUES ('" + plot + "', '" + info + "');");
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
	}
	
	public void addVergunning(String plot, String info) {
        try {
            openConnection();
            Statement statement = con.createStatement();   
            statement.executeUpdate("INSERT INTO KadasterRegisterVergunningen (plot, type) VALUES ('" + plot + "', '" + info + "');");
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
	}
	
    public String checkInfo(String plot) {
        try {     
        	openConnection();
            Statement statement = con.createStatement();   
            ResultSet result = statement.executeQuery("SELECT * FROM KadasterRegister WHERE plot = '" + plot + "'");
            while (result.next()) {
            	String type = result.getString("type");
            	return type;
            }
            con.close();
            
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
		return null;
        
	}
    public String checkVerg(String plot, String verg) {
        try {     
        	openConnection();
            Statement statement = con.createStatement();   
            ResultSet result = statement.executeQuery("SELECT * FROM KadasterRegisterVergunningen WHERE plot = '" + plot + "' && type= '" + verg + "'");
            while (result.next()) {
            	String type = result.getString("type");
            	return type;
            }
            con.close();
            
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
		return null;
        
	}
    
	public void updatePlot(String plot, String info) {
        try {
            openConnection();
            Statement statement = con.createStatement();   
            statement.executeUpdate("UPDATE KadasterRegister SET type = '" + info + "' WHERE plot = '" + plot + "'");
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
	}
	
	public void deletePlot(String plot) {
        try {
            openConnection();
            Statement statement = con.createStatement();   
            statement.executeUpdate("DELETE FROM KadasterRegister WHERE plot = '" + plot + "'");
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
	}
	
	public void deleteVerg(String plot, String verg) {
        try {
            openConnection();
            Statement statement = con.createStatement();   
            statement.executeUpdate("DELETE FROM KadasterRegisterVergunningen WHERE plot = '" + plot + "' && type= '" + verg + "'");
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
	}
	
	
}
