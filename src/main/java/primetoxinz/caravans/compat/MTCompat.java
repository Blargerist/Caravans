package primetoxinz.caravans.compat;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.CrafttweakerImplementationAPI;
import crafttweaker.mc1120.brackets.BracketHandlerItem;
import crafttweaker.mc1120.commands.CTChatCommand;
import crafttweaker.runtime.providers.ScriptProviderDirectory;
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
        CraftTweakerAPI.registerClass(IEntity.class);

        CraftTweakerAPI.registerClass(MTCaravan.class);
        CraftTweakerAPI.registerClass(MTMerchant.class);
        File scriptDirectory = new File(CaravansMod.INSTANCE.caravansFolder, "scripts");
        if (!scriptDirectory.exists())
            scriptDirectory.mkdirs();
        CraftTweakerAPI.registerBracketHandler(new BracketHandlerItem());
        BracketHandlerItem.rebuildItemRegistry();
        CrafttweakerImplementationAPI.setScriptProvider(new ScriptProviderDirectory(scriptDirectory));
        CTChatCommand.registerCommand(new CommandSellable());
        CrafttweakerImplementationAPI.load();
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
