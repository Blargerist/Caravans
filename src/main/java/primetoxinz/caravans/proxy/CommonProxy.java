package primetoxinz.caravans.proxy;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import primetoxinz.caravans.network.CaravanerListener;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {

    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    public void serverStarting(FMLServerStartingEvent event) {
        World world = event.getServer().getEntityWorld();
        if (world != null) {
            world.addEventListener(new CaravanerListener());
        }
    }

    public void syncCaravaner(int id, String caravan) {
        //NOOP
    }


}