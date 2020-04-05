package me.boykev.kdr;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Commands implements CommandExecutor {
	
	
	private Main instance;
	@SuppressWarnings("unused")
	private ConfigManager cm;
	private SqlManager db;

	public Commands(Main main) {
		this.instance = main;
	}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		cm = new ConfigManager(instance);
		db = new SqlManager(instance);
		Player p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("kadaster")) {
			if(args.length < 1) {
				p.sendMessage(ChatColor.RED + "Je hebt het commando niet juist gebruikt!");
				p.sendMessage(ChatColor.GRAY + "Juiste gebruik:");
				p.sendMessage(ChatColor.GREEN + "/k info");
				if(p.hasPermission("kadaster.manager")) {
					p.sendMessage(ChatColor.GREEN + "/kr set");
				}
				if(p.hasPermission("kadaster.admin")) {
					p.sendMessage(ChatColor.GREEN + "/kr delete");
				}
				return false;
			}//end of args check
			if(args[0].equalsIgnoreCase("info")) {
				if(args.length < 2) {
					p.sendMessage(ChatColor.RED + "/kr info [plot]");
					return false;
				}
				p.sendMessage(ChatColor.GRAY + "Even geduld, we halen de gegevens op uit de database.....");
				Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable() {
	                @Override
	                public void run() {
	                	String type = db.checkInfo(args[1]);
	                	if (type == null) {
	                		p.sendMessage(ChatColor.RED + "Helaas, dit is geen plot of dit plot is nog niet geregistreerd!");
	                		return;
	                	}
	                	p.sendMessage(ChatColor.GRAY + "Dit plot staat ingeschreven als:");
	                	p.sendMessage(ChatColor.GRAY + type);
	                	p.sendMessage(ChatColor.GRAY + "om de vergunningen voor dit plot te bekijken");
	                	
	                	TextComponent msg = new TextComponent(ChatColor.BLUE + "Klik Hier!");
	    				msg.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://api.boykevanvugt.nl/checkvergs.php?plot=" + args[1]));
	    				p.spigot().sendMessage(msg);
	                	
	                }
	            }, 40);
				return false;
			}//end of info command
			
			if(args[0].equalsIgnoreCase("set")) {
				if(p.hasPermission("kadaster.manager")) {
					if(args.length < 3) {
						p.sendMessage(ChatColor.RED + "/kr set [plot] [nieuwe info]");
						return false;
					}
					StringBuilder sb = new StringBuilder();
					for (int i = 2; i < args.length; i++){
					sb.append(args[i]).append(" ");
					}
					 
					String allArgs = sb.toString().trim();
					if(db.checkInfo(args[1]) == null) {
						db.makePlot(args[1], allArgs);
						p.sendMessage(ChatColor.GREEN + "Plot aangemaakt met de volgende informatie:");
						p.sendMessage(ChatColor.GREEN + "Plotnummer: " + args[1] + " Info: " + allArgs);
						return false;
					}
					db.updatePlot(args[1], allArgs);
					p.sendMessage(ChatColor.GREEN + "Plot geupdate met de volgende info:");
					p.sendMessage(ChatColor.GREEN + "Plotnummer: " + args[1] + " Info: " + allArgs);
					return false;
				}
				p.sendMessage(ChatColor.RED + "Helaas, alleen de regering kan kadaster informatie aanpassen!");
				return false;
			}//end of set command
			if(args[0].equalsIgnoreCase("delete")) {
				if(p.hasPermission("kadaster.admin")) {
					if(args.length < 2) {
						p.sendMessage(ChatColor.RED + "/kr delete [plot]");
						return false;
					}
					if(db.checkInfo(args[1]) == null) {
						p.sendMessage(ChatColor.RED + "Dit plot is niet gevonden in de database!");
						return false;
					}
					db.deletePlot(args[1]);
					p.sendMessage(ChatColor.GREEN + "Plot " + args[1] + " is successvol verwijderd!");
					return false;
				}
				p.sendMessage(ChatColor.RED + "Helaas alleen een Kadaster Administrator kan een plot verwijderen uit de database!");
				return false;
			}//end of delete command
			
			if(args[0].equalsIgnoreCase("addverg")) {
				if(args.length < 2) {
					p.sendMessage(ChatColor.RED + "/kr delete [plot]");
					return false;
				}
				if(db.checkInfo(args[1]) == null) {
					p.sendMessage(ChatColor.RED + "Dit plot is niet gevonden in de database!");
					return false;
				}
				StringBuilder sb = new StringBuilder();
				for (int i = 2; i < args.length; i++){
				sb.append(args[i]).append(" ");
				}
				 
				String allArgs = sb.toString().trim();
				db.addVergunning(args[1], allArgs);
				p.sendMessage(ChatColor.GREEN + "Vergunning: " + ChatColor.RED +  allArgs + ChatColor.GREEN + " toegevoegd aan: " + ChatColor.RED + args[1]);
				return false;
			}
			if(args[0].equalsIgnoreCase("remverg")) {
				if(args.length < 2) {
					p.sendMessage(ChatColor.RED + "/kr delete [plot]");
					return false;
				}
				if(db.checkInfo(args[1]) == null) {
					p.sendMessage(ChatColor.RED + "Dit plot is niet gevonden in de database!");
					return false;
				}
				StringBuilder sb = new StringBuilder();
				for (int i = 2; i < args.length; i++){
				sb.append(args[i]).append(" ");
				}
				String allArgs = sb.toString().trim();
				
				if(db.checkVerg(args[1], allArgs) == null) {
					p.sendMessage(ChatColor.RED + "Dit plot in combinatie met deze vergunning is niet gevonden in de database!");
					return false;
				}
				
				db.deleteVerg(args[1], allArgs);
				p.sendMessage(ChatColor.GREEN + "Vergunning: " + ChatColor.RED +  allArgs + ChatColor.GREEN + " verwijderd van: " + ChatColor.RED + args[1]);
				return false;
			}
			
			
			
			//end of file
			return false;
		}
		
		return false;
	}
}
