package io.github.maxmilite.maxutil.module.render;

import io.github.maxmilite.maxutil.module.Module;

import java.util.Arrays;

public class HitEffect extends Module {

    public HitEffect() {
        super("HitEffect", false);
        this.mode = "LIGHTNING";
        this.modeList = Arrays.asList("LIGHTNING", "PARTICLE");
    }
}
