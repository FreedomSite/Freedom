package gg.freedomsite.freedom.command;

import gg.freedomsite.freedom.Freedom;
import gg.freedomsite.freedom.player.FPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class BlankCommand extends Command {

    private FreedomCommand command;

    public BlankCommand(FreedomCommand command) {
        super(command.getName());
        this.command = command;


        if (command.getDescription() != null) {
            this.setDescription(command.getDescription());
        }

        this.setName(command.getName());
        this.setLabel(command.getName());
        if (command.getAliases() != null && command.getAliases().length != 0) {
            this.setAliases(Arrays.asList(command.getAliases()));
        }

        if (command.getUsage() != null) {
            this.setUsage(command.getUsage());
        }
        //this.setPermissionMessage(ChatColor.WHITE + "You lack the permission node " + ChatColor.RED + command.getPermission() + ChatColor.WHITE + "!");

    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        if (command.matches(label)) {
            if (sender instanceof Player) //check if sender is a player
            {
                Player player = (Player) sender;
                FPlayer fplayer = Freedom.get().getPlayerData().getData(player.getUniqueId());

                if (fplayer.isImposter())
                {
                    player.sendMessage("§cYou do not have permission to execute this command. Please verify first!");
                    return true;
                }
                if (!fplayer.getRank().isAtleast(command.getRank()))
                {
                    player.sendMessage("§cYou do not have permission to execute this command.");
                    return true;
                }
                command.run(sender, args);
                return true; //execute the command and end the if statement
            } //now check for if sender is console instead of player
            command.run(sender, args);
            return true;
        }
        return false;
    }

}
