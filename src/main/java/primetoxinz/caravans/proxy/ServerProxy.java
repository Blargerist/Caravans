package primetoxinz.caravans.proxy;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import primetoxinz.caravans.network.CaravanerListener;

/**
 * Created by primetoxinz on 7/3/17.
 */
public class ServerProxy extends CommonProxy {
    @Override
    public void serverStarting(FMLServerStartingEvent event) {
        World world = event.getServer().getEntityWorld();
        if (world != null) {
            world.addEventListener(new CaravanerListener());
        }
    }
}
