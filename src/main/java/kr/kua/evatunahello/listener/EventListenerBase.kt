package kr.kua.evatunahello.listener

import com.google.common.io.ByteStreams
import kr.kua.evatunahello.Main
import litebans.api.Database
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.event.PlayerDisconnectEvent
import net.md_5.bungee.api.event.PluginMessageEvent
import net.md_5.bungee.api.event.ServerConnectEvent
import net.md_5.bungee.api.event.ServerSwitchEvent
import net.md_5.bungee.event.EventPriority
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

class EventListenerBase(plugin: Main) : Listener {

    private var plugin: Main

    init {
        this.plugin = plugin
    }

    @EventHandler
    fun onPluginMessage(event: PluginMessageEvent) {
        if (event.tag != "BungeeCord") return
        if (ByteStreams.newDataInput(event.data).readUTF() != "NewbieHello") return

        broadcastMessage(
            "\uD83C\uDF89 " + ChatColor.of("#FF55FF") + event.receiver + " §7님이 서버에 처음 접속 하셨습니다. §r\uD83D\uDC4F\uD83D\uDC4F"
        )

        val player = plugin.proxy.getPlayer(event.receiver.toString())
        player.sendMessage("\ue4e3 ${ChatColor.RESET}에바참치 가이드: https://lavender-tugboat-45a.notion.site/EVATUNA-2-INFORMATION-2cda1b9629b54950a959fccb6d00dbe1")
    }

    private fun broadcastMessage(content: String) {
        val message = TextComponent.fromLegacyText(content)
        for (player: ProxiedPlayer in ProxyServer.getInstance().players) {
            if (player.server != null && player.server.info.name == "guest") return
            player.sendMessage(*message)
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    fun ServerConnectEvent(event: ServerConnectEvent) {
        val player = event.player
        try {
//            if (plugin.isBot(player.name)) return
//            if (event.target.name == "guest") return
            if (event.reason != ServerConnectEvent.Reason.JOIN_PROXY) return
        } catch (ex: Exception) {
        }
        val isBanned = Database.get().isPlayerBanned(player.uniqueId, null)
        if (!isBanned) sendJoinMessage(ProxyServer.getInstance().players, player.name)
    }

    @EventHandler(priority = EventPriority.NORMAL)
    fun PlayerDisconnectEvent(event: PlayerDisconnectEvent) {
        val player = event.player
        try {
//            if (plugin.isBot(player.name)) return
//            if (event.player.server.info.name == "guest") return
            sendLeaveMessage(ProxyServer.getInstance().players, player.name)
        } catch (ex: Exception) {
        }
    }

    /**
     * guest 서버 제거로 인한 이벤트 제거
     */
//    @EventHandler(priority = EventPriority.NORMAL)
//    fun ServerSwitchEvent(event: ServerSwitchEvent) {
//        val player = event.player
//        try {
////            if (plugin.isBot(player.name)) return
//            if (event.from == null) return
//            if (event.from.name != "guest") return
//        } catch (ex: Exception) {
//        }
//        broadcastMessage(
//            ProxyServer.getInstance().players,
//            "\uD83C\uDF89 " + ChatColor.of("#FF55FF") + player.name + " §7님이 서버에 처음 접속 하셨습니다. §r\uD83D\uDC4F\uD83D\uDC4F"
//        )
////        broadcastMessage(ProxyServer.getInstance().getPlayers(),
////                "\ue4db " + ChatColor.of("#FF55FF") + player.getName() + "님이 서버에 처음 접속 하셨습니다. :)");
////        );
//    }

//    private fun broadcastMessage(players: Iterable<ProxiedPlayer>, content: String) {
//        val message = TextComponent.fromLegacyText(content)
//        for (player: ProxiedPlayer in players) {
//            if (player.server != null && player.server.info.name == "guest") return
//            player.sendMessage(*message)
//        }
//    }

    private fun sendJoinMessage(players: Iterable<ProxiedPlayer>, name: String) {
        val message = TextComponent.fromLegacyText(
//            "${ChatColor.of("#AAAAAA")}[${ChatColor.of("#55FF55")}+${ChatColor.of("#AAAAAA")}] $name"
            "\uE4E5 ${ChatColor.of("#808080")}$name"
        )
        for (player: ProxiedPlayer in players) {
            if (player.server != null && player.server.info.name == "guest") return
            player.sendMessage(*message)
        }
    }

    private fun sendLeaveMessage(players: Iterable<ProxiedPlayer>, name: String) {
        val message = TextComponent.fromLegacyText(
//            "${ChatColor.of("#AAAAAA")}[${ChatColor.of("#FF5555")}-${ChatColor.of("#AAAAAA")}] $name"
            "\uE4E6 ${ChatColor.of("#808080")}$name"
        )
        for (player: ProxiedPlayer in players) {
            if (player.server != null && player.server.info.name == "guest") return
            player.sendMessage(*message)
        }
    }
}
