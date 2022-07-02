package io.github.maxmilite.maxutil.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.maxmilite.maxutil.events.MessageEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SkullItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class PlayerUtil {

    public static void addBlockEntityNbt(ItemStack stack, BlockEntity blockEntity) {
        NbtCompound nbtCompound = blockEntity.createNbtWithIdentifyingData();
        NbtCompound nbtCompound2;
        if (stack.getItem() instanceof SkullItem && nbtCompound.contains("SkullOwner")) {
            nbtCompound2 = nbtCompound.getCompound("SkullOwner");
            stack.getOrCreateNbt().put("SkullOwner", nbtCompound2);
        } else {
            BlockItem.setBlockEntityNbt(stack, blockEntity.getType(), nbtCompound);
            nbtCompound2 = new NbtCompound();
            NbtList nbtList = new NbtList();
            nbtList.add(NbtString.of("\"(+NBT)\""));
            nbtCompound2.put("Lore", nbtList);
            stack.setSubNbt("display", nbtCompound2);
        }
    }

    public static void getBlockNBT() {
        Gson gson = new GsonBuilder().create();
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.crosshairTarget != null && mc.world != null && mc.crosshairTarget.getType() == HitResult.Type.BLOCK) {
            BlockPos pos = new BlockPos(mc.crosshairTarget.getPos());
            BlockState block = mc.world.getBlockState(pos);
            BlockEntity entity = mc.world.getBlockEntity(pos);
            if (block != null && entity != null) {
                ItemStack itemStack = block.getBlock().getPickStack(mc.world, pos, block);
                addBlockEntityNbt(itemStack, entity);
                mc.player.giveItemStack(itemStack);
                MessageEvent.sendInfo(gson.toJson(itemStack.getNbt()));
            } else
                MessageEvent.sendError("This block has no nbt.");

        } else {
            MessageEvent.sendError("Player is not looking at a block.");
        }
    }
}
