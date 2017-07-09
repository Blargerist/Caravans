package primetoxinz.caravans.network;

/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Psi Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Psi
 * <p>
 * Psi is Open Source and distributed under the
 * Psi License: http://psi.vazkii.us/license.php
 * <p>
 * File Created @ [11/01/2016, 22:00:30 (GMT)]
 */

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public abstract class NetworkMessage<REQ extends NetworkMessage> implements Serializable, IMessage, IMessageHandler<REQ, IMessage> {

    private static final HashMap<Class, Pair<Reader, Writer>> handlers = new HashMap();
    private static final HashMap<Class, Field[]> fieldCache = new HashMap();

    static {
        mapHandler(byte.class, NetworkMessage::readByte, NetworkMessage::writeByte);
        mapHandler(short.class, NetworkMessage::readShort, NetworkMessage::writeShort);
        mapHandler(int.class, NetworkMessage::readInt, NetworkMessage::writeInt);
        mapHandler(long.class, NetworkMessage::readLong, NetworkMessage::writeLong);
        mapHandler(float.class, NetworkMessage::readFloat, NetworkMessage::writeFloat);
        mapHandler(double.class, NetworkMessage::readDouble, NetworkMessage::writeDouble);
        mapHandler(boolean.class, NetworkMessage::readBoolean, NetworkMessage::writeBoolean);
        mapHandler(char.class, NetworkMessage::readChar, NetworkMessage::writeChar);
        mapHandler(String.class, NetworkMessage::readString, NetworkMessage::writeString);
        mapHandler(NBTTagCompound.class, NetworkMessage::readNBT, NetworkMessage::writeNBT);
        mapHandler(ItemStack.class, NetworkMessage::readItemStack, NetworkMessage::writeItemStack);
        mapHandler(BlockPos.class, NetworkMessage::readBlockPos, NetworkMessage::writeBlockPos);
    }

    // The thing you override!
    public IMessage handleMessage(MessageContext context) {
        return null;
    }

    @Override
    public final IMessage onMessage(REQ message, MessageContext context) {
        return message.handleMessage(context);
    }

    @Override
    public final void fromBytes(ByteBuf buf) {
        try {
            Class<?> clazz = getClass();
            Field[] clFields = getClassFields(clazz);
            for (Field f : clFields) {
                Class<?> type = f.getType();
                if (acceptField(f, type))
                    readField(f, type, buf);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error at reading packet " + this, e);
        }
    }

    @Override
    public final void toBytes(ByteBuf buf) {
        try {
            Class<?> clazz = getClass();
            Field[] clFields = getClassFields(clazz);
            for (Field f : clFields) {
                Class<?> type = f.getType();
                if (acceptField(f, type))
                    writeField(f, type, buf);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error at writing packet " + this, e);
        }
    }

    private static Field[] getClassFields(Class<?> clazz) {
        if (fieldCache.containsValue(clazz))
            return fieldCache.get(clazz);
        else {
            Field[] fields = clazz.getFields();
            Arrays.sort(fields, Comparator.comparing(Field::getName));
            fieldCache.put(clazz, fields);
            return fields;
        }
    }

    private final void writeField(Field f, Class clazz, ByteBuf buf) throws IllegalArgumentException, IllegalAccessException {
        Pair<Reader, Writer> handler = getHandler(clazz);
        handler.getRight().write(f.get(this), buf);
    }

    private final void readField(Field f, Class clazz, ByteBuf buf) throws IllegalArgumentException, IllegalAccessException {
        Pair<Reader, Writer> handler = getHandler(clazz);
        f.set(this, handler.getLeft().read(buf));
    }

    private static Pair<Reader, Writer> getHandler(Class<?> clazz) {
        Pair<Reader, Writer> pair = handlers.get(clazz);
        if (pair == null)
            throw new RuntimeException("No R/W handler for  " + clazz);
        return pair;
    }

    private static boolean acceptField(Field f, Class<?> type) {
        int mods = f.getModifiers();
        return !(Modifier.isFinal(mods) || Modifier.isStatic(mods) || Modifier.isTransient(mods)) && handlers.containsKey(type);

    }

    public static <T extends Object> void mapHandler(Class<T> type, Reader<T> reader, Writer<T> writer) {
        handlers.put(type, Pair.of(reader, writer));
    }

    public static byte readByte(ByteBuf buf) {
        return buf.readByte();
    }

    public static void writeByte(byte b, ByteBuf buf) {
        buf.writeByte(b);
    }

    public static short readShort(ByteBuf buf) {
        return buf.readShort();
    }

    public static void writeShort(short s, ByteBuf buf) {
        buf.writeShort(s);
    }

    public static int readInt(ByteBuf buf) {
        return buf.readInt();
    }

    public static void writeInt(int i, ByteBuf buf) {
        buf.writeInt(i);
    }

    public static long readLong(ByteBuf buf) {
        return buf.readLong();
    }

    public static void writeLong(long l, ByteBuf buf) {
        buf.writeLong(l);
    }

    public static float readFloat(ByteBuf buf) {
        return buf.readFloat();
    }

    public static void writeFloat(float f, ByteBuf buf) {
        buf.writeFloat(f);
    }

    public static double readDouble(ByteBuf buf) {
        return buf.readDouble();
    }

    public static void writeDouble(double d, ByteBuf buf) {
        buf.writeDouble(d);
    }

    public static boolean readBoolean(ByteBuf buf) {
        return buf.readBoolean();
    }

    public static void writeBoolean(boolean b, ByteBuf buf) {
        buf.writeBoolean(b);
    }

    public static char readChar(ByteBuf buf) {
        return buf.readChar();
    }

    public static void writeChar(char c, ByteBuf buf) {
        buf.writeChar(c);
    }

    public static String readString(ByteBuf buf) {
        return ByteBufUtils.readUTF8String(buf);
    }

    public static void writeString(String s, ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, s);
    }

    public static NBTTagCompound readNBT(ByteBuf buf) {
        return ByteBufUtils.readTag(buf);
    }

    public static void writeNBT(NBTTagCompound cmp, ByteBuf buf) {
        ByteBufUtils.writeTag(buf, cmp);
    }

    public static ItemStack readItemStack(ByteBuf buf) {
        return ByteBufUtils.readItemStack(buf);
    }

    public static void writeItemStack(ItemStack stack, ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, stack);
    }

    public static BlockPos readBlockPos(ByteBuf buf) {
        return BlockPos.fromLong(buf.readLong());
    }

    public static void writeBlockPos(BlockPos pos, ByteBuf buf) {
        buf.writeLong(pos.toLong());
    }

    // Functional interfaces
    public interface Writer<T extends Object> {
        void write(T t, ByteBuf buf);
    }

    public interface Reader<T extends Object> {
        T read(ByteBuf buf);
    }

}