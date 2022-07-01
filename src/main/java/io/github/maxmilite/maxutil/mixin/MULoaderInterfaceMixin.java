package io.github.maxmilite.maxutil.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MinecraftClient.class)
public interface MULoaderInterfaceMixin {

    @Accessor("itemUseCooldown")
    void setItemUseCooldown(int itemUseCooldown);

    @Accessor("itemUseCooldown")
    int getItemUseCooldown();

    @Invoker("doAttack")
    boolean leftClick();
}
