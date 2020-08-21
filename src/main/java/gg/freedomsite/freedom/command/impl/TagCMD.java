package gg.freedomsite.freedom.command.impl;

import gg.freedomsite.freedom.command.FreedomCommand;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.ranking.Rank;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TagCMD extends FreedomCommand
{


    public TagCMD() {
        super("/tag [-s[ave]] <set | clear> [prefix | player]", "tag", "Modify the prefix", Rank.OP);
        setEnabled(true);
    }

    @Override
    public void run(CommandSender sender, String[] args)
    {
        if (args.length == 0)
        {
            sender.sendMessage("§7Correct usage: §e" + getUsage());
            return;
        }


        if (args[0].equalsIgnoreCase("set"))
        {
            if (!(sender instanceof Player))
            {
                sender.sendMessage(IN_GAME_ONLY);
                return;
            }
            Player player = (Player) sender;

            String prefix = StringUtils.join(args, " ", 1, args.length);

            if (prefix.length() > 16)
            {
                player.sendMessage("§7Your prefix must consist of 16 characters only.");
                return;
            }
            FPlayer fPlayer = getPlugin().getPlayerData().getData(player.getUniqueId());
            fPlayer.setTag(prefix);
            player.sendMessage("§7Your tag has been successfully changed to §r" + ChatColor.translateAlternateColorCodes('&', prefix));
            return;
        }
        if (args[0].equalsIgnoreCase("clear"))
        {
            if (args.length == 1)
            {
                if (!(sender instanceof Player))
                {
                    sender.sendMessage(IN_GAME_ONLY);
                    return;
                }
                Player player = (Player) sender;

                FPlayer fPlayer = getPlugin().getPlayerData().getData(player.getUniqueId());
                fPlayer.setTag("");
                player.sendMessage("§7Successfully cleared your tag.");
                return;
            } else if (args.length == 2)
            {
                if (sender instanceof Player)
                {
                    Player player = (Player) sender;
                    FPlayer fPlayer = getPlugin().getPlayerData().getData(player.getUniqueId());
                    if (!fPlayer.isAdmin())
                    {
                        player.sendMessage(NO_PERMISSION);
                        return;
                    }
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null)
                {
                    sender.sendMessage(PLAYER_NOT_FOUND);
                    return;
                }

                FPlayer fPlayer = getPlugin().getPlayerData().getData(target.getUniqueId());
                fPlayer.setTag("");
                sender.sendMessage("§7Successfully cleared " + target.getName() + "'s tag.");
                target.sendMessage("§7Your tag has been cleared.");
                return;
            }
        }

        if (args[0].equalsIgnoreCase("-s") || args[0].equalsIgnoreCase("-save"))
        {
            if (args[1].equalsIgnoreCase("set"))
            {
                if (!(sender instanceof Player))
                {
                    sender.sendMessage(IN_GAME_ONLY);
                    return;
                }
                Player player = (Player) sender;

                String prefix = StringUtils.join(args, " ", 2, args.length);

                if (prefix.length() > 16)
                {
                    player.sendMessage("§7Your prefix must consist of 16 characters only.");
                    return;
                }
                FPlayer fPlayer = getPlugin().getPlayerData().getData(player.getUniqueId());
                fPlayer.setTag(prefix);
                getPlugin().getPlayerData().update(fPlayer);
                player.sendMessage("§7Your tag has been successfully changed to §r" + ChatColor.translateAlternateColorCodes('&', prefix) + ChatColor.GRAY + " and saved.");
                return;
            }
            if (args[1].equalsIgnoreCase("clear"))
            {
                if (args.length == 2)
                {
                    if (!(sender instanceof Player))
                    {
                        sender.sendMessage(IN_GAME_ONLY);
                        return;
                    }
                    Player player = (Player) sender;

                    FPlayer fPlayer = getPlugin().getPlayerData().getData(player.getUniqueId());
                    fPlayer.setTag("");
                    getPlugin().getPlayerData().update(fPlayer);
                    player.sendMessage("§7Successfully cleared your tag and saved.");
                    return;
                } else if (args.length == 3)
                {
                    if (sender instanceof Player)
                    {
                        Player player = (Player) sender;
                        FPlayer fPlayer = getPlugin().getPlayerData().getData(player.getUniqueId());
                        if (!fPlayer.isAdmin())
                        {
                            player.sendMessage(NO_PERMISSION);
                            return;
                        }
                    }
                    Player target = Bukkit.getPlayer(args[2]);
                    if (target == null)
                    {
                        sender.sendMessage(PLAYER_NOT_FOUND);
                        return;
                    }

                    FPlayer fPlayer = getPlugin().getPlayerData().getData(target.getUniqueId());
                    fPlayer.setTag("");
                    getPlugin().getPlayerData().update(fPlayer);
                    sender.sendMessage("§7Successfully cleared " + target.getName() + "'s tag and saved.");
                    target.sendMessage("§7Your tag has been cleared and saved.");
                    return;
                }
            }
        }
    }
}
