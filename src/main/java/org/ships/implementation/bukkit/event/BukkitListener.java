package org.ships.implementation.bukkit.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.core.CorePlugin;
import org.core.entity.living.human.player.LivePlayer;
import org.core.event.Event;
import org.core.event.HEvent;
import org.core.event.events.entity.EntityInteractEvent;
import org.core.text.Text;
import org.ships.implementation.bukkit.event.events.block.AbstractBlockChangeEvent;
import org.ships.implementation.bukkit.event.events.block.tileentity.BSignChangeEvent;
import org.ships.implementation.bukkit.event.events.entity.BEntityInteractEvent;
import org.ships.implementation.bukkit.platform.BukkitPlatform;
import org.ships.implementation.bukkit.text.BText;
import org.ships.implementation.bukkit.utils.DirectionUtils;
import org.ships.implementation.bukkit.world.position.BBlockPosition;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.HashSet;
import java.util.Set;

public class BukkitListener implements Listener {

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
        if(event.getClickedBlock() == null){
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
    public static void onBlockBreakByPlayer(BlockBreakEvent event){
        AbstractBlockChangeEvent.BreakBlockChangeEvent event1 = new AbstractBlockChangeEvent.BreakBlockChangeEvent(new BBlockPosition(event.getBlock()), (LivePlayer) ((BukkitPlatform)CorePlugin.getPlatform()).createEntityInstance(event.getPlayer()));
        call(event1);
        if(event1.isCancelled()){
            event.setCancelled(event1.isCancelled());
        }
    }

    public static <E extends Event> E call(E event){
        Set<BEventLaunch> methods = getMethods(event.getClass());
        methods.stream().forEach(m -> m.run(event));
        return event;
    }

    private static Set<BEventLaunch> getMethods(Class<? extends Event> classEvent){
        Set<BEventLaunch> methods = new HashSet<>();
        CorePlugin.getEventManager().getEventListeners().entrySet().stream().forEach(e -> e.getValue().stream().forEach(el -> {
            for (Method method : el.getClass().getDeclaredMethods()){
                if(method.getDeclaredAnnotationsByType(HEvent.class) == null){
                    continue;
                }
                if(methods.stream().anyMatch(m -> method.getName().contains("$"))){
                    continue;
                }
                Parameter[] parameters = method.getParameters();
                if(parameters.length == 0){
                    System.err.println("Failed to know what to do: HEvent found on method, but no event on " + el.getClass().getName() + "." + method.getName() + "()");
                    continue;
                }
                if(!Modifier.isPublic(method.getModifiers())){
                    continue;
                }
                Class<? extends Object> class1 = parameters[0].getType();
                if(!Event.class.isAssignableFrom(classEvent)){
                    System.err.println("Failed to know what to do: HEvent found on method, but no known event on " + el.getClass().getName() + "." + method.getName() + "(" + CorePlugin.toString(", ", p -> p.getType().getSimpleName() + " " + p.getName(), parameters) + ")");
                }
                if(class1.isAssignableFrom(classEvent)){
                    methods.add(new BEventLaunch(e.getKey(), el, method));
                }
            }
        }));
        return methods;
    }
}
