package io.github.maxmilite.maxutil.module;

import io.github.maxmilite.maxutil.module.blatant.*;
import io.github.maxmilite.maxutil.module.misc.Derp;
import io.github.maxmilite.maxutil.module.misc.Timer;
import io.github.maxmilite.maxutil.module.movement.Sprint;
import io.github.maxmilite.maxutil.module.player.NightVision;
import io.github.maxmilite.maxutil.module.render.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager {
    private final List<Module> modules = new ArrayList<>();

    public List<Module> getModules() {
        return this.modules;
    }

    public List<Module> getEnabledModules() {
        return modules.stream().filter(Module::isToggle).collect(Collectors.toList());
    }

    public Module getModule(String name) {
        for (var i : modules) {
            if (i.getName().equalsIgnoreCase(name))
                return i;
        }
        return null;
    }

    public ModuleManager() {
        loadModules();
    }

    public void loadModules() {

        // Render
        modules.add(new NoFireRender());
        modules.add(new NoUnderwaterOverlay());
        modules.add(new NoInWallOverlay());
        modules.add(new NoPumpkinRender());
        modules.add(new ScoreBoard());
        modules.add(new NoScoreboard());
        modules.add(new ActiveMods());
        modules.add(new HitEffect());

        // Movement
        modules.add(new Sprint());
        modules.add(new NoSlow());
        modules.add(new Strafe());
        modules.add(new SkyFloat());
        modules.add(new Launch());

        // Player
        modules.add(new NightVision());
        modules.add(new Reach());
        modules.add(new NoFall());
        modules.add(new Velocity());
        modules.add(new FastUse());
        modules.add(new KillAura());
        modules.add(new Scaffold());

        // Misc
        modules.add(new Timer());
        modules.add(new Derp());
    }

}
