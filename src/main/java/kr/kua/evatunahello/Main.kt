package kr.kua.evatunahello

import kr.kua.evatunahello.command.SearchCommand
import kr.kua.evatunahello.listener.EventListenerBase
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.plugin.Plugin

class Main : Plugin() {
    override fun onEnable() {
        val pm = proxy.pluginManager
        pm.registerListener(this, EventListenerBase(this))

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
}