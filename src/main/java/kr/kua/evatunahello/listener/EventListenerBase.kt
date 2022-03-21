package kr.kua.evatunahello.listener

import kr.kua.evatunahello.Main
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.event.PlayerDisconnectEvent
import net.md_5.bungee.api.event.ServerConnectEvent
import net.md_5.bungee.api.event.ServerSwitchEvent
import net.md_5.bungee.event.EventPriority
import net.md_5.bungee.api.plugin.Listener

class EventListenerBase(plugin: Main) : Listener {

    private var plugin: Main

    init {
        this.plugin = plugin
    }

    @net.md_5.bungee.event.EventHandler(priority = EventPriority.LOW)
    fun ServerConnectEvent(event: ServerConnectEvent) {
        val player = event.player
        try {
            if (plugin.isBot(player.name)) return
            if (event.target.name == "guest") return
            if (event.reason != ServerConnectEvent.Reason.JOIN_PROXY) return
        } catch (ex: Exception) {
        }
        sendJoinMessage(ProxyServer.getInstance().players, player.name)
    }

    @net.md_5.bungee.event.EventHandler(priority = EventPriority.NORMAL)
    fun PlayerDisconnectEvent(event: PlayerDisconnectEvent) {
        val player = event.player
        try {
            if (plugin.isBot(player.name)) return
            if (event.player.server.info.name == "guest") return
            sendLeaveMessage(ProxyServer.getInstance().players, player.name)
        } catch (ex: Exception) {
        }
    }

    @net.md_5.bungee.event.EventHandler(priority = EventPriority.NORMAL)
    fun ServerSwitchEvent(event: ServerSwitchEvent) {
        val player = event.player
        try {
            if (plugin.isBot(player.name)) return
            if (event.from == null) return
            if (event.from.name != "guest") return
        } catch (ex: Exception) {
        }
        broadcastMessage(
            ProxyServer.getInstance().players,
            "\uD83C\uDF89 " + ChatColor.of("#FF55FF") + player.name + " §7님이 서버에 처음 접속 하셨습니다. §r\uD83D\uDC4F\uD83D\uDC4F"
        )
//        broadcastMessage(ProxyServer.getInstance().getPlayers(),
//                "\ue4db " + ChatColor.of("#FF55FF") + player.getName() + "님이 서버에 처음 접속 하셨습니다. :)");
//        );
    }

    private fun broadcastMessage(players: Iterable<ProxiedPlayer>, content: String) {
        val message = TextComponent.fromLegacyText(content)
        for (player: ProxiedPlayer in players) {
            if (player.server.info.name == "guest") return
            player.sendMessage(*message)
        }
    }

    private fun sendJoinMessage(players: Iterable<ProxiedPlayer>, name: String) {
        val message = TextComponent.fromLegacyText(
//            "${ChatColor.of("#AAAAAA")}[${ChatColor.of("#55FF55")}+${ChatColor.of("#AAAAAA")}] $name"
            "\uE4E5 ${ChatColor.of("#808080")}$name"
        )
        for (player: ProxiedPlayer in players) {
            if (player.server.info.name == "guest") return
            player.sendMessage(*message)
        }
    }

    private fun sendLeaveMessage(players: Iterable<ProxiedPlayer>, name: String) {
        val message = TextComponent.fromLegacyText(
//            "${ChatColor.of("#AAAAAA")}[${ChatColor.of("#FF5555")}-${ChatColor.of("#AAAAAA")}] $name"
            "\uE4E6 ${ChatColor.of("#808080")}$name"
        )
        for (player: ProxiedPlayer in players) {
            if (player.server.info.name == "guest") return
            player.sendMessage(*message)
        }
    }
}
