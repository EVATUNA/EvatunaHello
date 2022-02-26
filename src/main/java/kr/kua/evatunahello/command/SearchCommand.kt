package kr.kua.evatunahello.command

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Command

class SearchCommand : Command("Search") {
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (sender is ProxiedPlayer) {
            if (args.isEmpty()) {
                sender.sendMessage(*ComponentBuilder("/search [키워드] 로 검색하세요.").color(ChatColor.WHITE).create())
            } else {
                val players = players
                val rPlayers: MutableList<String> = ArrayList()
                for (i in players.indices) {
                    val cur_str = players.toTypedArray()[i]
                    if (cur_str.contains(args[0])) rPlayers.add(cur_str)
                }
                if (rPlayers.size == 0) {
                    sender.sendMessage(*ComponentBuilder("검색 결과가 없습니다.").color(ChatColor.WHITE).create())
                } else {
                    sender.sendMessage(
                        *ComponentBuilder("검색결과[" + rPlayers.size + "] : " + attachListString(rPlayers)).color(
                            ChatColor.WHITE
                        ).create()
                    )
                }
            }
        }
    }

    private fun attachListString(ls: List<String>): String {
        val sb = StringBuffer()
        for (str in ls) {
            if (sb.isNotEmpty()) sb.append(", ")
            sb.append(str)
        }
        return sb.toString()
    }

    private val players: List<String>
        get() {
            val result: MutableList<String> = ArrayList()
            val players: Iterable<ProxiedPlayer> = ProxyServer.getInstance().players
            for (player in players) {
                result.add(player.name)
            }
            return result
        }
}
