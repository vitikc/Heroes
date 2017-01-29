package me.vitikc.heroes.entity;

import net.minecraft.server.v1_11_R1.EntityChicken;
import net.minecraft.server.v1_11_R1.EntityTypes;
import net.minecraft.server.v1_11_R1.MinecraftKey;
import net.minecraft.server.v1_11_R1.RegistryMaterials;
import org.bukkit.entity.EntityType;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * Created by Vitikc on 27/Jan/17.
 * TY to @Arektor NMS Tutorials
 */
public enum HeroesCustomEntities {
    HEALINGWARD("HealingWard",93, EntityType.CHICKEN, EntityChicken.class, HeroesHealingWard.class);

    private String name;
    private int id;
    private EntityType entityType;
    private Class<?> nmsClass;
    private Class<?> customClass;
    private MinecraftKey key;
    private MinecraftKey oldKey;

    @SuppressWarnings("unchecked")
    private HeroesCustomEntities(String name, int id, EntityType entityType, Class<?> nmsClass, Class<?> customClass) {
        this.name = name;
        this.id = id;
        this.entityType = entityType;
        this.nmsClass = nmsClass;
        this.customClass = customClass;
        this.key = new MinecraftKey(name);
        this.oldKey = ((RegistryMaterials<MinecraftKey,Class<?>>) getPrivateStatic(EntityTypes.class, "b")).b(nmsClass);
    }

    public static void registerEntities() { for (HeroesCustomEntities ce : HeroesCustomEntities.values()) ce.register(); }
    public static void unregisterEntities() { for (HeroesCustomEntities ce : HeroesCustomEntities.values()) ce.unregister(); }

    @SuppressWarnings("unchecked")
    private void register() {
        ((Set<MinecraftKey>) getPrivateStatic(EntityTypes.class, "d")).add(key);
        ((RegistryMaterials<MinecraftKey,Class<?>>) getPrivateStatic(EntityTypes.class, "b")).a(id, key, customClass);
    }

    @SuppressWarnings("unchecked")
    private void unregister() {
        ((Set<MinecraftKey>) getPrivateStatic(EntityTypes.class, "d")).remove(key);
        ((RegistryMaterials<MinecraftKey,Class<?>>) getPrivateStatic(EntityTypes.class, "b")).a(id, oldKey, nmsClass);
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public Class<?> getCustomClass() {
        return customClass;
    }

    private static Object getPrivateStatic(final Class<?> clazz, final String f) {
        try {
            Field field = clazz.getDeclaredField(f);
            field.setAccessible(true);
            return field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}