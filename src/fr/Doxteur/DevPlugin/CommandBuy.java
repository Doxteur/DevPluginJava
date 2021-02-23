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


public class CommandBuy implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		List<String> itemList = new ArrayList<>();
		List<Integer> itemListPrix = new ArrayList<>();
		List<String> itemListName = new ArrayList<>();
		
		itemList.add("Pomme");
		itemList.add("Pioche_Fer");
		itemList.add("Ice");
		itemListPrix.add(5);
		itemListPrix.add(20);
		itemListPrix.add(50);
		itemListName.add("APPLE");
		itemListName.add("IRON_PICKAXE");
		itemListName.add("ICE");
		
		MySQLGetSet sql = new MySQLGetSet();
		if (sender instanceof Player) {
			Player p = Bukkit.getPlayer(sender.getName());
			System.out.println("Vous avez " + sql.getMoney(p.getUniqueId()));
			if (p.hasPermission("buyItem.use")) {
				if (args.length >= 1) {
					if (args[0].equals("list")) {
						p.sendMessage("Voici la list des items : ");

						for (int i = 0; i < itemList.size(); i++) {
							p.sendMessage(itemList.get(i) + " coute " + itemListPrix.get(i) + " coins-coins.");
						}

					}
					if (itemList.contains(args[0])) {
						int index = itemList.indexOf(args[0]);
						int price = itemListPrix.get(index);
						
						if (sql.getMoney(p.getUniqueId()) >= price) {
							p.sendMessage("§cVous venez d'acheter " + args[0] + " coutant " + price);
							System.out.println(itemListName.get(index));
							System.out.println(Material.valueOf(itemListName.get(index)));
							p.getInventory().addItem(new ItemStack(Material.valueOf(itemListName.get(index))));
							sql.setMoney(p.getUniqueId(),price,"Achat");
							
							p.sendMessage("Il vous reste " + sql.getMoney(p.getUniqueId()));
							
						}else {
							p.sendMessage("T povre traveil. Il te reste: " + sql.getMoney(p.getUniqueId()));
						}
					}
				}
			} else {
				p.sendMessage("§cVous n'avez pas la permissions.");
			}
		} else {

			System.out.println("Vous devez etre un joueur");
		}

		return false;
	}

}
