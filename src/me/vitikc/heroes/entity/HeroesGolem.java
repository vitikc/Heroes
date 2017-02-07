package me.vitikc.heroes.entity;

import me.vitikc.heroes.HeroesMain;
import net.minecraft.server.v1_11_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.UUID;

import static me.vitikc.heroes.entity.HeroesNMSUtils.getPrivateField;

/**
 * Created by Vitikc on 07/Feb/17.
 */
public class HeroesGolem extends EntityIronGolem{
    private UUID owner;
    private HeroesMain plugin;

    public HeroesGolem(World world, UUID owner, HeroesMain plugin){
        super(world);
        this.owner = owner;
        this.plugin = plugin;
        this.setCustomNameVisible(true);
        this.setCustomName("Golem");
        this.owner = owner;
        this.plugin = plugin;

        this.setHealth(1f);
        this.expToDrop = 0;
        this.setAI(false);

        LinkedHashSet goalB = (LinkedHashSet)getPrivateField("b", PathfinderGoalSelector.class, goalSelector); goalB.clear();
        LinkedHashSet goalC = (LinkedHashSet)getPrivateField("c", PathfinderGoalSelector.class, goalSelector); goalC.clear();
        LinkedHashSet targetB = (LinkedHashSet)getPrivateField("b", PathfinderGoalSelector.class, targetSelector); targetB.clear();
        LinkedHashSet targetC = (LinkedHashSet)getPrivateField("c", PathfinderGoalSelector.class, targetSelector); targetC.clear();

        this.goalSelector.a(0, new PathfinderGoalFloat(this));
    }


    @Override
    public void g(float sideMot, float forMot) {
        if (this.passengers != null) {
            EntityLiving passenger = (EntityLiving) this.passengers.get(0);
            this.yaw = passenger.yaw;
            this.lastYaw = this.yaw;
            this.pitch = (passenger.pitch * 0.5F);
            setYawPitch(this.yaw, this.pitch);
            this.aN = this.yaw;
            this.aP = this.aN;

            Float speedmultiplicator = 1F;//Here you can set the speed
            sideMot = passenger.be * speedmultiplicator;
            forMot = passenger.bf * speedmultiplicator;
            if(forMot <= 0.0F) {
                forMot *= 0.25F;// Make backwards slower
            }
            Field jump = null; //Jumping
            try {
                jump = EntityLiving.class.getDeclaredField("bd");
            } catch (NoSuchFieldException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (SecurityException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            jump.setAccessible(true);

            if (jump != null && this.onGround) {     // Wouldn't want it jumping while on the ground would we?
                try {
                    if (jump.getBoolean(passenger)) {
                        double jumpHeight = 0.5D;     //Here you can set the jumpHeight
                        this.motY = jumpHeight;        // Used all the time in NMS for entity jumping
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            float speed = 0.2F;
            this.l(speed);
            super.g(sideMot, forMot);
        }
    }

    public Player getPlayerOwner(){
        return Bukkit.getPlayer(this.owner);
    }

}
