package io.github.dsh105.sparktrail.particle;

import io.github.dsh105.sparktrail.SparkTrail;
import io.github.dsh105.sparktrail.config.options.ConfigOptions;
import io.github.dsh105.sparktrail.data.EffectCreator;
import io.github.dsh105.sparktrail.data.EffectHandler;
import io.github.dsh105.sparktrail.particle.type.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Project by DSH105
 */

public class EffectHolder extends BukkitRunnable {

    private HashSet<Effect> effects = new HashSet<Effect>();
    private BukkitTask task = null;
    protected EffectType effectType;
    protected EffectDetails details;

    public World world;
    public int locX;
    public int locY;
    public int locZ;

    private int tick = 0;

    public EffectHolder(EffectType effectType) {
        this.effectType = effectType;
        this.details = new EffectDetails(effectType);
    }

    public void setEffects(HashSet<Effect> effects) {
        for (Effect e : effects) {
            this.effects.add(e);
        }
    }

    public HashSet<Effect> getEffects() {
        return this.effects;
    }

    public EffectType getEffectType() {
        return this.effectType;
    }

    public void addEffect(ParticleType particleType) {
        if (this.effectType == EffectType.PLAYER) {
            ParticleDetails pd = new ParticleDetails(particleType);
            pd.setPlayer(this.details.playerName, this.details.mobUuid);
            Effect effect = EffectCreator.createEffect(this, particleType, pd.getDetails());
            if (effect != null) {
                this.effects.add(effect);
            }
            return;
        } else if (this.effectType == EffectType.LOCATION) {
            ParticleDetails pd = new ParticleDetails(particleType);
            Effect effect = EffectCreator.createEffect(this, particleType, pd.getDetails());
            if (effect != null) {
                this.effects.add(effect);
            }
            return;
        } else if (this.effectType == EffectType.MOB) {
            ParticleDetails pd = new ParticleDetails(particleType);
            pd.setMob(this.details.mobUuid);
            Effect effect = EffectCreator.createEffect(this, particleType, pd.getDetails());
            if (effect != null) {
                this.effects.add(effect);
            }
            return;
        }
        EffectHandler.getInstance().save(this);
    }

    public void addEffect(ParticleDetails particleDetails) {
        Effect effect = EffectCreator.createEffect(this, particleDetails.getParticleType(), particleDetails.getDetails());
        if (effect != null) {
            this.effects.add(effect);
        }
        EffectHandler.getInstance().save(this);
    }

    public boolean hasEffect(ParticleType pt) {
        for (Effect e : effects) {
            if (e.getParticleType().equals(pt)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasEffect(ParticleDetails pd) {
        for (Effect e : effects) {
            if (e.getParticleType().equals(pd.getParticleType())) {
                ParticleType pt = pd.getParticleType();
                if (pt == ParticleType.BLOCKBREAK) {
                    if (pd.blockId == ((BlockBreak) e).idValue && pd.blockMeta == ((BlockBreak) e).metaValue) {
                        return true;
                    }
                } else if (pt == ParticleType.CRITICAL) {
                    if (pd.criticalType.equals(((Critical) e).criticalType)) {
                        return true;
                    }
                } else if (pt == ParticleType.FIREWORK) {
                    return true;
                } else if (pt == ParticleType.POTION) {
                    if (pd.potionType.equals(((Potion) e).potionType)) {
                        return true;
                    }
                } else if (pt == ParticleType.SMOKE) {
                    if (pd.smokeType.equals(((Smoke) e).smokeType)) {
                        return true;
                    }
                } else if (pt == ParticleType.SWIRL) {
                    if (pd.swirlType.equals(((Swirl) e).swirlType)) {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    public void removeEffect(ParticleType particleType) {
        Iterator<Effect> i = this.effects.iterator();
        while (i.hasNext()) {
            Effect e = i.next();
            if (e.getParticleType().equals(particleType)) {
                i.remove();
            }
        }
        EffectHandler.getInstance().save(this);
    }

    public void removeEffect(ParticleDetails particleDetails) {
        Iterator<Effect> i = this.effects.iterator();
        while (i.hasNext()) {
            Effect e = i.next();
            if (e.getParticleType().equals(particleDetails.getParticleType())) {
                ParticleType pt = e.getParticleType();
                if (pt == ParticleType.BLOCKBREAK) {
                    if (particleDetails.blockId == ((BlockBreak) e).idValue && particleDetails.blockMeta == ((BlockBreak) e).metaValue) {
                        i.remove();
                    }
                } else if (pt == ParticleType.CRITICAL) {
                    if (particleDetails.criticalType.equals(((Critical) e).criticalType)) {
                        i.remove();
                    }
                } else if (pt == ParticleType.FIREWORK) {
                    i.remove();
                }
                /*else if (pt == ParticleType.NOTE) {
                    if (particleDetails.noteType.equals(((Note) e).noteType)) {
						i.remove();
					}
				}*/
                else if (pt == ParticleType.POTION) {
                    if (particleDetails.potionType.equals(((Potion) e).potionType)) {
                        i.remove();
                    }
                } else if (pt == ParticleType.SMOKE) {
                    if (particleDetails.smokeType.equals(((Smoke) e).smokeType)) {
                        i.remove();
                    }
                } else if (pt == ParticleType.SWIRL) {
                    if (particleDetails.swirlType.equals(((Swirl) e).swirlType)) {
                        i.remove();
                    }
                } else {
                    i.remove();
                }
            }
        }
        EffectHandler.getInstance().save(this);
    }

    public Location getLocation() {
        return new Location(this.world, this.locX, this.locY, this.locZ);
    }

    public boolean isLocation(Location location) {
        if (this.world.getName().equals(location.getWorld().getName())
                && this.locX == location.getBlockX()
                && this.locY == location.getBlockY()
                && this.locZ == location.getBlockZ()) {
            return true;
        }
        return false;
    }

    public void updateLocation(Location l) {
        this.updateLocation(l.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ());
    }

    public void updateLocation(World world, int x, int y, int z) {
        this.world = world;
        this.locX = x;
        this.locY = y;
        this.locZ = z;
    }

    public EffectDetails getDetails() {
        return this.details;
    }

    public void start() {
        this.task = this.runTaskTimer(SparkTrail.getInstance(), 0L, 1L);
    }

    public void run() {
        if (!this.effects.isEmpty()) {
            if (update()) {
                Iterator<Effect> i = this.effects.iterator();
                while (i.hasNext()) {
                    Effect e = i.next();
                    if (e == null || e.getParticleType() == null) {
                        continue;
                    }
                    if (e.getParticleType().getIncompatibleTypes().contains(this.effectType)) {
                        i.remove();
                        continue;
                    }
                    if (this.tick % e.getParticleType().getFrequency() == 0) {
                        e.play();
                    }
                }
            }
        }
        if (tick == ConfigOptions.instance.maxTick) {
            tick = 0;
        } else {
            tick++;
        }
    }

    private boolean update() {
        if (this.effectType == EffectType.PLAYER) {
            if (this.details.playerName == null) {
                return false;
            }
            Player p = Bukkit.getPlayerExact(this.details.playerName);
            if (p == null) {
                //Logger.log(Logger.LogLevel.WARNING, "Encountered missing player (Name: " + this.details.playerName + "). Removing particle effect.", true);
                EffectHandler.getInstance().remove(this);
                return false;
            }
            Location l = p.getLocation();
            if (l == null) {
                EffectHandler.getInstance().remove(this);
                return false;
            }
            if (this.world == null || !this.world.equals(l.getWorld())) {
                this.world = l.getWorld();
            }
            if (((Integer) this.locX) == null || !(this.locX == l.getBlockX())) {
                this.locX = l.getBlockX();
            }
            if (((Integer) this.locY) == null || !(this.locY == l.getBlockY())) {
                this.locY = l.getBlockY();
            }
            if (((Integer) this.locZ) == null || !(this.locZ == l.getBlockZ())) {
                this.locZ = l.getBlockZ();
            }
        } else if (this.effectType == EffectType.MOB) {
            if (this.details.mobUuid == null) {
                return false;
            }
            Entity e = null;
            for (Entity entity : this.world.getEntities()) {
                if (entity.getUniqueId().equals(this.details.mobUuid)) {
                    e = entity;
                }
            }
            if (e == null) {
                //Logger.log(Logger.LogLevel.WARNING, "Encountered missing entity (UUID: " + this.details.mobUuid + "). Removing particle effect. Maybe it despawned?", true);
                EffectHandler.getInstance().remove(this);
                return false;
            }
            Location l = e.getLocation();
            if (this.world == null || !this.world.equals(l.getWorld())) {
                this.world = l.getWorld();
            }
            if (((Integer) this.locX) == null || !(this.locX == l.getBlockX())) {
                this.locX = l.getBlockX();
            }
            if (((Integer) this.locY) == null || !(this.locY == l.getBlockY())) {
                this.locY = l.getBlockY();
            }
            if (((Integer) this.locZ) == null || !(this.locZ == l.getBlockZ())) {
                this.locZ = l.getBlockZ();
            }
        }
        return true;
    }

    public void stop() {
        this.task.cancel();
    }

    public enum EffectType {

        LOCATION, PLAYER, MOB;
    }
}