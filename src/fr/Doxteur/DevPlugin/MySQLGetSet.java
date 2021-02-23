package fr.Doxteur.DevPlugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.md_5.bungee.api.ChatColor;

public class MySQLGetSet implements Listener {

	MainClass plugin = MainClass.getPlugin(MainClass.class);

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		createPlayer(player.getUniqueId(), player);
		getMoney(player.getUniqueId());
	}

	public boolean playerExists(UUID uuid) {
		try {
			PreparedStatement statement = plugin.getConnection()
					.prepareStatement("SELECT * FROM playermoney WHERE UUID=?");
			statement.setString(1, uuid.toString());

			ResultSet results = statement.executeQuery();
			if (results.next()) {
				plugin.getServer().broadcastMessage(ChatColor.YELLOW + "Player Found");
				return true;
			}
			plugin.getServer().broadcastMessage(ChatColor.YELLOW + "Player Not Found");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void createPlayer(final UUID uuid, Player player) {
		try {
			PreparedStatement statement = plugin.getConnection()
					.prepareStatement("SELECT * FROM playermoney WHERE UUID=?");

			statement.setString(1, uuid.toString());
			ResultSet results = statement.executeQuery();
			results.next();
			if (playerExists(uuid) != true) {

				PreparedStatement insert = plugin.getConnection()
						.prepareStatement("INSERT INTO playermoney (UUID,MONEY) VALUE (?,?)");
				insert.setString(1, uuid.toString());
				insert.setInt(2, 45);
				insert.executeUpdate();

				plugin.getServer().broadcastMessage(ChatColor.GREEN + "Player Inserted");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getMoney(UUID uuid) {
		try {
			PreparedStatement getMoney = plugin.getConnection()
					.prepareStatement("SELECT UUID,MONEY FROM playermoney WHERE UUID=?");
			getMoney.setString(1, uuid.toString());
			ResultSet results = getMoney.executeQuery();

			if (playerExists(uuid) == true) {
				results.next();
				return results.getInt(2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void setMoney(UUID uuid, int money, String bs) {
		try {

			PreparedStatement getMoney = plugin.getConnection().prepareStatement("UPDATE playermoney SET MONEY=? WHERE UUID = ?");
				int choix = (bs.equals("Achat")?-money:money);
				getMoney.setInt(1, (getMoney(uuid) + choix));
				getMoney.setString(2, uuid.toString());
				

				getMoney.executeUpdate();
				System.out.println("Money has been se to " + (getMoney(uuid) + choix));
				
				} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
