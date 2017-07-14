package primetoxinz.caravans.compat;

import minetweaker.MineTweakerAPI;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.mc1112.brackets.ItemBracketHandler;
import minetweaker.runtime.providers.ScriptProviderDirectory;
import net.minecraft.util.ResourceLocation;
import primetoxinz.caravans.CaravansMod;
import primetoxinz.caravans.api.CaravanAPI;
import primetoxinz.caravans.api.CaravanBuilder;
import primetoxinz.caravans.api.MerchantBuilder;
import primetoxinz.caravans.common.CommandSellable;

import java.io.File;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class MTCompat {

    public static void preInit() {
        MineTweakerAPI.registerClass(MTCaravan.class);
        MineTweakerAPI.registerClass(MTMerchant.class);
        File scriptDirectory = new File(CaravansMod.INSTANCE.caravansFolder, "scripts");
        if (!scriptDirectory.exists())
            scriptDirectory.mkdirs();
        MineTweakerAPI.registerBracketHandler(new ItemBracketHandler());
        ItemBracketHandler.rebuildItemRegistry();
        MineTweakerImplementationAPI.setScriptProvider(new ScriptProviderDirectory(scriptDirectory));
        MineTweakerImplementationAPI.addMineTweakerCommand("sellableEntities",new String[]{"List of Entity Classes which can be sold by a merchant"}, new CommandSellable());
        MineTweakerImplementationAPI.reload();

    }

    public static MerchantBuilder getMerchant(String name) {
        if(name.contains(":"))
            return CaravanAPI.getMerchant(name);
        return CaravanAPI.getMerchant(new ResourceLocation(CaravansMod.MODID, name));
    }

    public static CaravanBuilder getCaravan(String name) {
        if(name.contains(":"))
            return CaravanAPI.getCaravan(name);
        return CaravanAPI.getCaravan(new ResourceLocation(CaravansMod.MODID, name));
    }
}
