package primetoxinz.caravans.proxy;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import primetoxinz.caravans.common.ItemUtils;
import primetoxinz.caravans.common.entity.EntityUtil;

/**
 * Created by primetoxinz on 7/3/17.
 */

@Mod.EventBusSubscriber(Side.SERVER)
public class ServerProxy extends CommonProxy {

    @Override
    public void buyEntityTrade(MessageContext context, ItemStack input, String inputEntity, String outputEntity) {

    }
}
