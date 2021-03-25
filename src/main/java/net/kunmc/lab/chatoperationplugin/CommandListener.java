package net.kunmc.lab.chatoperationplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class CommandListener implements CommandExecutor, TabCompleter{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //if (!command.equals("cho")) return false;
        String target = Chatoperationplugin.INSTANCE.target;

        if (args.length < 1) {
            sender.sendMessage("引数が足りません");
            return false;
        }
        if (args[0].equals("off")) {
            Chatoperationplugin.INSTANCE.mode = 0;
            sender.sendMessage("チャット操作がオフになりました。");
            return true;
        }
        if (args[0].equals("on")) {
            Chatoperationplugin.INSTANCE.mode = 1;
            if (Chatoperationplugin.INSTANCE.target == null)
                sender.sendMessage("チャット操作がオンになりました。操作対象を指定してください。");
            else
                sender.sendMessage("チャット操作がオンになりました。現在の操作対象は" + target + "です。");
            return true;
        }
        if (args[0].equals("target")) {
            if (args.length < 2) {
                sender.sendMessage("現在の操作対象は"+target+"です。");
                return false;
            } else {
                if(Bukkit.getPlayer(args[1]) != null){
                    Chatoperationplugin.INSTANCE.target = args[1];
                    target = Chatoperationplugin.INSTANCE.target;
                    sender.sendMessage("操作対象が"+target+"になりました");
                } else {
                    sender.sendMessage("プレイヤーが存在しません。");
                }
            }
        }
        if (args[0].equals("detail")) {
            //作業台いらないモード等の実装
        }//if
        return true;
    }//boolean

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        final List<String> completions = new ArrayList<>();
        if(args.length==1){
            completions.add("target");
            completions.add("on");
            completions.add("off");
            completions.add("details");
        }
        if(args.length==2&&"target".equals(args[0])){
            completions.addAll(Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList()));
        }
        return completions;
    }
}//class