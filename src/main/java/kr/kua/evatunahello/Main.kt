package kr.kua.evatunahello

import com.google.common.io.ByteStreams
import kr.kua.evatunahello.command.SearchCommand
import kr.kua.evatunahello.listener.EventListenerBase
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Plugin
import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener


class Main : Plugin(), PluginMessageListener {
    override fun onEnable() {
        val pm = proxy.pluginManager
        pm.registerListener(this, EventListenerBase(this))

        ProxyServer.getInstance().registerChannel("NewbieHello")

        ProxyServer.getInstance().pluginManager.registerCommand(this, SearchCommand())

        logger.info("EvaTunaHello Enabled.")
    }

    override fun onDisable() {
        logger.info("EvaTunaHello Disabled.")
    }

    fun isBot(name: String?): Boolean {
        return when (name) {
            "Geralt_w", "LopLove", "dohun1", "1L_Bottle", "creamcrepe", "AppleCrepe" -> true
            else -> false
        }
    }

    override fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray?) {
        if (channel != "NewbieHello") return

        val username = ByteStreams.newDataInput(message)
        broadcastMessage(
            ProxyServer.getInstance().players,
            "\uD83C\uDF89 " + ChatColor.of("#FF55FF") + username + " §7님이 서버에 처음 접속 하셨습니다. §r\uD83D\uDC4F\uD83D\uDC4F"
        )
    }

    private fun broadcastMessage(players: Iterable<ProxiedPlayer>, content: String) {
        val message = TextComponent.fromLegacyText(content)
        for (player: ProxiedPlayer in players) {
            if (player.server != null && player.server.info.name == "guest") return
            player.sendMessage(*message)
        }
    }
}