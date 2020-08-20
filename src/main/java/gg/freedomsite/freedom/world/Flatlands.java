package gg.freedomsite.freedom.world;

import gg.freedomsite.freedom.world.generators.FlatRoomGenerator;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class Flatlands
{

    @Getter
    private World world;

    public Flatlands() {
        if (Bukkit.getWorld("flatlands") != null) {
            this.world = Bukkit.getWorld("flatlands");
            return;
        }
        WorldCreator worldCreator = new WorldCreator("flatlands");
        worldCreator.generator(new FlatRoomGenerator());
        worldCreator.generateStructures(false);
        world = worldCreator.createWorld();


        assert world != null;
        Location location = world.getSpawnLocation();
        location.getBlock().setType(Material.OAK_SIGN);
        Block block = location.getBlock();
        Sign sign = (Sign) block.getState();
        sign.setLine(0, "§eFlatlands");
        sign.update();
    }

}
