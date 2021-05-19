package kr.kua.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.List;

public class SearchCommand extends Command {

    public SearchCommand() {
        super("Search");
    }

    public void execute(CommandSender sender, String[] args) {

        if ((sender instanceof ProxiedPlayer)) {
            ProxiedPlayer p = (ProxiedPlayer)sender;

            if (args.length == 0) {
                p.sendMessage(new ComponentBuilder("/search [키워드] 로 검색하세요.").color(ChatColor.WHITE).create());
            } else {
                List<String> players = getPlayers();
                List<String> rPlayers = new ArrayList<>();

                for (int i = 0; i < players.size(); i++) {
                    String cur_str = (String)players.toArray()[i];
                    if (cur_str.contains(args[0])) rPlayers.add(cur_str);
                }

                if (rPlayers.size() == 0) {
                    p.sendMessage(new ComponentBuilder("검색 결과가 없습니다.").color(ChatColor.WHITE).create());
                } else {
                    p.sendMessage(new ComponentBuilder("검색결과["+rPlayers.size()+"] : " + attachListString(rPlayers)).color(ChatColor.WHITE).create());
                }
            }
        }
    }

    public String attachListString(List<String> ls) {
        StringBuffer sb = new StringBuffer();
        for (String str : ls) {
            if (sb.length() != 0) sb.append(", ");
            sb.append(str);
        }

        return sb.toString();
    }

    public List<String> getPlayers() {
        List<String> result = new ArrayList<>();
        Iterable<ProxiedPlayer> players = ProxyServer.getInstance().getPlayers();
        for (final ProxiedPlayer player : players) {
            result.add(player.getName());
        }

        return result;
    }
}
