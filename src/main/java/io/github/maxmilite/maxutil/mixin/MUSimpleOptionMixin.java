package io.github.maxmilite.maxutil.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Objects;
import java.util.function.Consumer;

@Mixin(SimpleOption.class)
public class MUSimpleOptionMixin<T> {

    @Shadow T value;

    @Shadow @Final
    private Consumer<T> changeCallback;

    /**
     * @author Maxmilite
     * @reason Remove limits on gamma
     */
    @Overwrite
    public void setValue(T value) {
        if (!MinecraftClient.getInstance().isRunning()) {
            this.value = value;
        } else {
            if (!Objects.equals(this.value, value)) {
                this.value = value;
                this.changeCallback.accept(this.value);
            }

        }
    }

}
