package gg.freedomsite.freedom.command.impl;

import gg.freedomsite.freedom.command.FreedomCommand;
import gg.freedomsite.freedom.ranking.Rank;
import org.bukkit.command.CommandSender;

public class FreedomCMD extends FreedomCommand
{


    public FreedomCMD() {
        super("/freedom", "freedom", Rank.NON);
        setEnabled(true);
    }

    @Override
    public void run(CommandSender sender, String[] args)
    {
        sender.sendMessage("§r§r§r§r§r§r§r§r§r§e§lFREEDOM");
        sender.sendMessage("§8- §eCreated by Taahh and vJayyy");
        sender.sendMessage("§8- §eVersion " + getPlugin().getDescription().getVersion());
        sender.sendMessage("§8- §eAPI Version " + getPlugin().getDescription().getAPIVersion());
        sender.sendMessage("§8- §eRegistered CMDS " + getPlugin().getCommandHandler().freedomCommandList.size());
        sender.sendMessage("§8- §eDescription: " + getPlugin().getDescription().getDescription());
    }
}
