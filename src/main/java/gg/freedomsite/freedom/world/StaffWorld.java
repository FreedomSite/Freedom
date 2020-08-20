package gg.freedomsite.freedom.world;

import gg.freedomsite.freedom.world.generators.FlatRoomGenerator;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class StaffWorld
{

    @Getter
    private World world;

    public StaffWorld() {
        if (Bukkit.getWorld("staffworld") != null) {
            this.world = Bukkit.getWorld("staffworld");
            return;
        }
        WorldCreator worldCreator = new WorldCreator("staffworld");
        worldCreator.generator(new FlatRoomGenerator());
        worldCreator.generateStructures(false);
        world = worldCreator.createWorld();


        assert world != null;
        Location location = world.getSpawnLocation();
        location.getBlock().setType(Material.OAK_SIGN);
        Block block = location.getBlock();
        Sign sign = (Sign) block.getState();
        sign.setLine(0, "§eStaffWorld");
        sign.update();
    }

}