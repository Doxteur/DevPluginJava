package fr.Doxteur.DevPlugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandSell implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		List<String> sellList = new ArrayList();
		List<Integer> sellListPrice = new ArrayList();
		sellList.add("ICE");
		sellListPrice.add(5);

		MySQLGetSet sql = new MySQLGetSet();
		if (sender instanceof Player) {
			Player p = Bukkit.getPlayer(sender.getName());
			if (p.hasPermission("buyItem.use")) {
				if (args.length >= 1) {
					if (args[0].equals("list")) {
						p.sendMessage("Voici les items vendables : ");

						for (int i = 0; i < sellList.size(); i++) {
							p.sendMessage(sellList.get(i) + " se vend " + sellListPrice.get(i) + " coins-coins.");
						}

					}
					if (sellList.contains(args[0])) {
						int index = sellList.indexOf(args[0]);
						int price = sellListPrice.get(index);
						if (p.getInventory().containsAtLeast(new ItemStack(Material.valueOf(sellList.get(index))), 1)) {
							sql.setMoney(p.getUniqueId(), price,"Vente");
							p.getInventory().remove(new ItemStack(Material.valueOf(sellList.get(index)),1));						}

					}
				}
			} else {
				p.sendMessage("§cVous n'avez pas la permissions.");
			}
		} else

		{

			System.out.println("Vous devez etre un joueur");
		}

		return false;
	}

}
