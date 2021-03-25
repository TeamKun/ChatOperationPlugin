package net.kunmc.lab.chatoperationplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;


public final class Chatoperationplugin extends JavaPlugin implements Listener {
    public static Chatoperationplugin INSTANCE;
    public int mode;
    public String target = null;


    @Override
    public void onEnable() {
        INSTANCE=this;
        // イベント登録
        getCommand("cho").setExecutor(new CommandListener());
        getServer().getPluginManager().registerEvents(this, this);
        Chatoperationplugin.INSTANCE.mode=1;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        Player target = Bukkit.getPlayer(INSTANCE.target);
        String message = event.getMessage();
        Location loc = target.getLocation();

        Vector dir = player.getLocation().getDirection();
        Vector vec;

        player.sendMessage(message);

        if(INSTANCE.mode == 0) return;

        if(message.matches("w|ｗ|まえ|前")) {
            vec = new Vector(dir.getX(), 0, dir.getZ());
            target.setVelocity(vec);
        }
        if(message.matches("s|ｓ|うしろ|後ろ")) {
            vec = new Vector(dir.getX()*-1, 0, dir.getZ()*-1);
            target.setVelocity(vec);
        }

        if(message.matches("a|ａ|あ|ひだり|左")) {
            //左に移動
            vec = new Vector(0,1,0);
            target.setVelocity(dir.getCrossProduct(vec).normalize().multiply(-1));
        }

        if(message.matches("d|ｄ|みぎ|右")) {
            //右に移動
            vec = new Vector(0,1,0);
            target.setVelocity(dir.getCrossProduct(vec).normalize().multiply(1));
        }

        if(message.matches("jump|space|ジャンプ|飛ぶ")) {
            target.setVelocity(player.getVelocity().setY(1.0));
        }

        //can't set player to sneak - only appearance
        /*
        if(message.contains("shift") || message.contains("シフト")) {
            target.setSneaking(true);
        }
        */

        if(message.contains("右向け") || message.contains("みぎむけ")) {
            target.sendMessage("みぎむけ");
            new BukkitRunnable() {
                @Override
                public void run(){
                    for (int i=0; i<90; i++){
                        loc.setYaw(loc.getYaw()+1);
                        target.teleport(loc);
                    }
                }
            }.runTaskTimer(this, 1, 20);
        }
    }
}