package fr.Doxteur.DevPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;

public class MainClass extends JavaPlugin implements Listener {
	private Connection connection;
	public String host, database, username, password;
	public int port;

	@Override
	public void onEnable() {

		getServer().getPluginManager().registerEvents(this, this);

		getServer().getPluginManager().registerEvents(new MySQLGetSet(), this);
		getCommand("buy").setExecutor(new CommandBuy());
		getCommand("sell").setExecutor(new CommandSell());
		mySqlSetup();
		
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void onDisable() {

	}

	

	// Setup Mysql
	public void mySqlSetup() {
		host = "localhost";
		port = 3306;
		database = "minecraftdb";
		username = "root";
		password = "";

		try {

			synchronized (this) {
				if (getConnection() != null && !getConnection().isClosed()) {
					return;
				}
				Class.forName("com.mysql.jdbc.Driver");
				setConnection(
						DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database,
								this.username, this.password));
				Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MYSQL Connected");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
