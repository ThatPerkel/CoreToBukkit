package org.ships.implementation.bukkit.event;

import org.array.utils.ArrayUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.core.CorePlugin;
import org.core.entity.Entity;
import org.core.entity.living.human.player.LivePlayer;
import org.core.event.Event;
import org.core.event.HEvent;
import org.core.event.events.entity.EntityInteractEvent;
import org.core.text.Text;
import org.core.world.position.block.details.BlockDetails;
import org.core.world.position.block.details.BlockSnapshot;
import org.core.world.position.impl.sync.SyncBlockPosition;
import org.ships.implementation.bukkit.entity.scene.live.BLiveDroppedItem;
import org.ships.implementation.bukkit.event.events.block.AbstractBlockChangeEvent;
import org.ships.implementation.bukkit.event.events.block.tileentity.BSignChangeEvent;
import org.ships.implementation.bukkit.event.events.connection.BJoinedEvent;
import org.ships.implementation.bukkit.event.events.connection.BKickEvent;
import org.ships.implementation.bukkit.event.events.entity.BEntityInteractEvent;
import org.ships.implementation.bukkit.event.events.entity.BEntitySpawnEvent;
import org.ships.implementation.bukkit.platform.BukkitPlatform;
import org.ships.implementation.bukkit.text.BText;
import org.ships.implementation.bukkit.utils.DirectionUtils;
import org.ships.implementation.bukkit.world.expload.EntityExplosion;
import org.ships.implementation.bukkit.world.position.block.details.blocks.BBlockDetails;
import org.ships.implementation.bukkit.world.position.block.details.blocks.BlockStateSnapshot;
import org.ships.implementation.bukkit.world.position.impl.sync.BBlockPosition;
import org.ships.implementation.bukkit.world.position.impl.sync.BExactPosition;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.*;

public class BukkitListener implements Listener {

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event){
        LivePlayer player = (LivePlayer) ((BukkitPlatform)CorePlugin.getPlatform()).createEntityInstance(event.getPlayer());
        BJoinedEvent cEvent = new BJoinedEvent(player);
        call(cEvent);
    }

    @EventHandler
    public static void onPlayerPlaceBlock(BlockPlaceEvent event){
        BlockDetails old = new BBlockDetails(event.getBlockReplacedState().getBlockData(), false);
        BlockDetails new1 = new BBlockDetails(event.getBlock().getBlockData(), false);
        SyncBlockPosition position = new BBlockPosition(event.getBlock());
        LivePlayer player = (LivePlayer) ((BukkitPlatform)CorePlugin.getPlatform()).createEntityInstance(event.getPlayer());
        List<BlockSnapshot<SyncBlockPosition>> collection = Collections.singletonList(new1.createSnapshot(position));
        AbstractBlockChangeEvent.PlaceBlockPlayerPostEvent event2 = new AbstractBlockChangeEvent.PlaceBlockPlayerPostEvent(position, old, new1, player, collection);
        call(event2);
        if(event2.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public static void onPlayerPlaceMultiBlock(BlockMultiPlaceEvent event){
        BlockDetails old = new BBlockDetails(event.getBlockReplacedState().getBlockData(), false);
        BlockDetails new1 = new BBlockDetails(event.getBlock().getBlockData(), false);
        SyncBlockPosition position = new BBlockPosition(event.getBlock());
        LivePlayer player = (LivePlayer) ((BukkitPlatform)CorePlugin.getPlatform()).createEntityInstance(event.getPlayer());
        List<BlockSnapshot<SyncBlockPosition>> collection = new ArrayList<>();
        event.getReplacedBlockStates().forEach(bs -> collection.add(new BlockStateSnapshot(bs)));

        AbstractBlockChangeEvent.PlaceBlockPlayerPostEvent event2 = new AbstractBlockChangeEvent.PlaceBlockPlayerPostEvent(position, old, new1, player, collection);
        call(event2);
        if(event2.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public static void onItemSpawnEvent(ItemSpawnEvent event){
        org.bukkit.entity.Item item = event.getEntity();
        BEntitySpawnEvent spawnEvent = new BEntitySpawnEvent(new BExactPosition(event.getLocation()), new BLiveDroppedItem(item));
        call(spawnEvent);
        boolean cancelled = spawnEvent.isCancelled();
        if(cancelled) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public static void onEntitySpawnEvent(EntitySpawnEvent event){
        org.bukkit.entity.Entity entity = event.getEntity();
        BEntitySpawnEvent spawnEvent = new BEntitySpawnEvent(new BExactPosition(event.getLocation()), ((BukkitPlatform)CorePlugin.getPlatform()).createEntityInstance(entity));
        call(spawnEvent);
        boolean cancelled = spawnEvent.isCancelled();
        if(cancelled) {
            event.setCancelled(true);
        }
    }

    /*@EventHandler
    public static void onItemDropFromBlock(BlockDropItemEvent event){
        BBlockDetails preDetails = new BBlockDetails(event.getBlockState().getBlockData());
        BBlockPosition position = new BBlockPosition(event.getBlock());
        Map<org.bukkit.entity.Item, DroppedItemSnapshot> items = new HashMap<>();
        event.getItems().forEach(i -> items.put(i, new BLiveDroppedItem(i).createSnapshot()));
        BLivePlayer player = new BLivePlayer(event.getPlayer());
        AbstractBlockChangeEvent.BreakBlockPostEvent event2 = new AbstractBlockChangeEvent.BreakBlockPostEvent(preDetails, position, player, items.values());
        call(event2);
        event2.getItems().forEach(is -> items.entrySet().stream().filter(e -> e.getValue().equals(is)).forEach(e -> event.getItems().remove(e.getKey())));
    }*/

    @EventHandler
    public static void onPlayerKickedEvent(PlayerKickEvent event){
        LivePlayer player = (LivePlayer)((BukkitPlatform)CorePlugin.getPlatform()).createEntityInstance(event.getPlayer());
        Text message = CorePlugin.buildText(event.getLeaveMessage());
        BKickEvent kickEvent = new BKickEvent(player, message);
        call(kickEvent);
        event.setLeaveMessage(((BText)message).toBukkitString());
    }

    @EventHandler
    public static void onPlayerQuitEvent(PlayerQuitEvent event){
        LivePlayer player = (LivePlayer)((BukkitPlatform)CorePlugin.getPlatform()).createEntityInstance(event.getPlayer());
        Text message = CorePlugin.buildText(event.getQuitMessage());
        BKickEvent kickEvent = new BKickEvent(player, message);
        call(kickEvent);
        event.setQuitMessage(((BText)message).toBukkitString());
    }

    @EventHandler
    public static void onSignChangeEvent(SignChangeEvent event){
        String[] originalLines = event.getLines();
        Text[] lines = new Text[originalLines.length];
        for(int A = 0; A < originalLines.length; A++){
            lines[A] = new BText(originalLines[A]);
        }
        BSignChangeEvent event1 = new BSignChangeEvent((LivePlayer)((BukkitPlatform)CorePlugin.getPlatform()).createEntityInstance(event.getPlayer()), new BBlockPosition(event.getBlock()), lines);
        call(event1);
        for(int A = 0; A < 4; A++) {
            final int B = A;
            event1.getTo().getLine(A).ifPresent(l -> event.setLine(B, ((BText)l).toBukkitString()));
        }
        if(event1.isCancelled()){
            event.setCancelled(event1.isCancelled());
        }
    }

    @EventHandler
    public static void onPlayerInteractWithBlockEvent(PlayerInteractEvent event){
        if(event.getClickedBlock() == null || event.getHand() != EquipmentSlot.HAND){
            return;
        }
        int action = -1;
        switch (event.getAction()){
            case RIGHT_CLICK_BLOCK:
                action = EntityInteractEvent.PRIMARY_CLICK_ACTION; break;
            case LEFT_CLICK_BLOCK:
                action = EntityInteractEvent.SECONDARY_CLICK_ACTION; break;
        }
        BEntityInteractEvent.PlayerInteractWithBlock event1 = new BEntityInteractEvent.PlayerInteractWithBlock(new BBlockPosition(event.getClickedBlock()), action, DirectionUtils.toDirection(event.getBlockFace()), (LivePlayer)((BukkitPlatform)CorePlugin.getPlatform()).createEntityInstance(event.getPlayer()));
        call(event1);
        if(event1.isCancelled()){
            event.setCancelled(event1.isCancelled());
        }
    }

    @EventHandler
    public static void onExplode(org.bukkit.event.entity.EntityExplodeEvent event){
        List<SyncBlockPosition> positions = new ArrayList<>();
        List<Block> list = event.blockList();
        for (Block value : list) {
            positions.add(new BBlockPosition(value));
        }
        Entity<?> entity = ((BukkitPlatform)CorePlugin.getPlatform()).createEntityInstance(event.getEntity());
        EntityExplosion explosion = new EntityExplosion(entity, positions);
        Iterator<Block> iterator = event.blockList().iterator();
        while(iterator.hasNext()){
            SyncBlockPosition block = new BBlockPosition(iterator.next());
            AbstractBlockChangeEvent.BreakBlockChangeExplode event2 = new AbstractBlockChangeEvent.BreakBlockChangeExplode(block, explosion);
            call(event2);
            if(event2.isCancelled()){
                iterator.remove();
            }
        }
    }

    @EventHandler
    public static void onBlockBreakByPlayer(BlockBreakEvent event){
        Player player = event.getPlayer();
        Material material = player.getInventory().getItemInMainHand().getType();
        if(player.getGameMode().equals(GameMode.CREATIVE) && (material.equals(Material.WOODEN_SWORD) || material.equals(Material.STONE_SWORD) || material.equals(Material.IRON_SWORD) || material.equals(Material.DIAMOND_SWORD) || material.equals(Material.GOLDEN_SWORD))){
            event.setCancelled(true);
            return;
        }
        AbstractBlockChangeEvent.BreakBlockChangeEventPlayer event1 = new AbstractBlockChangeEvent.BreakBlockChangeEventPlayer(new BBlockPosition(event.getBlock()), (LivePlayer) ((BukkitPlatform)CorePlugin.getPlatform()).createEntityInstance(event.getPlayer()));
        call(event1);
        if(event1.isCancelled()){
            event.setCancelled(event1.isCancelled());
        }
    }

    public static <E extends Event> E call(E event){
        Set<BEventLaunch> methods = getMethods(event.getClass());
        methods.forEach(m -> {
            m.run(event);
        });
        return event;
    }

    private static Set<BEventLaunch> getMethods(Class<? extends Event> classEvent){
        Set<BEventLaunch> methods = new HashSet<>();
        CorePlugin.getEventManager().getEventListeners().forEach((key, value) -> value.forEach(el -> {
            for (Method method : el.getClass().getDeclaredMethods()) {
                if (method.getDeclaredAnnotationsByType(HEvent.class) == null) {
                    continue;
                }
                if (methods.stream().anyMatch(m -> method.getName().contains("$"))) {
                    continue;
                }
                Parameter[] parameters = method.getParameters();
                if (parameters.length == 0) {
                    System.err.println("Failed to know what to do: HEvent found on method, but no event on " + el.getClass().getName() + "." + method.getName() + "()");
                    continue;
                }
                if (!Modifier.isPublic(method.getModifiers())) {
                    continue;
                }
                Class<?> class1 = parameters[0].getType();
                if (!Event.class.isAssignableFrom(classEvent)) {
                    System.err.println("Failed to know what to do: HEvent found on method, but no known event on " + el.getClass().getName() + "." + method.getName() + "(" + ArrayUtils.toString(", ", p -> p.getType().getSimpleName() + " " + p.getName(), parameters) + ")");
                }
                if (class1.isAssignableFrom(classEvent)) {
                    methods.add(new BEventLaunch(key, el, method));
                }
            }
        }));
        return methods;
    }
}
