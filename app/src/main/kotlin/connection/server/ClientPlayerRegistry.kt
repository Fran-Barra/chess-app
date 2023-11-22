package connection.server

import FailedOutcome
import Outcome
import SuccessfulOutcome
import boardGame.player.Player
import edu.austral.dissis.chess.gui.InvalidMove
import edu.austral.ingsis.clientserver.ServerConnectionListener

class ClientPlayerRegistry(val serverGame: ServerGameAdapter, val players: List<Player>): ServerConnectionListener {
    private var clientPlayer: Map<String, Player> = mapOf()

    override fun handleClientConnection(clientId: String) {
        serverGame.sendInitialStateToPlayer(clientId)
        clientPlayer = when (val outcome = AssignClientToPlayer(clientId, clientPlayer)){
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> {
                serverGame.ClientPlayerRegistryNotification(
                    SendMessageToClient(
                        clientId,
                        MessageTypeByServer.DISPLAY_MESSAGE.toString(),
                        InvalidMove("You are an observer of this game"))
                )
                return
            }
        }
    }

    override fun handleClientConnectionClosed(clientId: String) {
        TODO("Not yet implemented")
    }

    fun getPlayer(clientId: String): Outcome<Player> {
        val player = clientPlayer[clientId]?: return FailedOutcome("Not a player")
        return SuccessfulOutcome(player)
    }

    private fun AssignClientToPlayer(clientId: String, clientPlayer: Map<String, Player>): Outcome<Map<String, Player>> {
        for (player in players){
            if (player in clientPlayer.values) continue
            return SuccessfulOutcome(clientPlayer + mapOf(Pair(clientId, player)))
        }
        return FailedOutcome("All players taken")
    }
}