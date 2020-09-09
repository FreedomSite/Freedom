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
        sender.sendMessage("§e§lFREEDOM");
        sender.sendMessage("§8- §eCreated by Taahh and vJayyy");
        sender.sendMessage("§8- §eVersion §8->§e " + getPlugin().getDescription().getVersion());
        sender.sendMessage("§8- §eAPI Version §8->§e " + getPlugin().getDescription().getAPIVersion());
        sender.sendMessage("§8- §eRegistered CMDS §8->§e " + getPlugin().getCommandHandler().freedomCommandList.size());
        sender.sendMessage("§8- §eDescription §8->§e " + getPlugin().getDescription().getDescription());
    }
}
