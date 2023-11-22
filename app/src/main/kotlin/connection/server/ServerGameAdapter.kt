package connection.server

import FailedOutcome
import SuccessfulOutcome
import boardGame.board.Vector
import boardGame.game.*
import boardGame.player.Player
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import edu.austral.dissis.chess.gui.GameOver
import edu.austral.dissis.chess.gui.InitialState
import edu.austral.dissis.chess.gui.InvalidMove
import edu.austral.dissis.chess.gui.NewGameState
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener
import edu.austral.ingsis.clientserver.netty.server.NettyServerBuilder

class ServerGameAdapter(private var game: Game, private val pieceType: GetPieceFromTypeInStringFormat): MessageListener<MovePayload> {
    private val playerRegistry = ClientPlayerRegistry(this, game.getTurnController().getPlayers())
    private val server = NettyServerBuilder.createDefault()
        .withPort((System.getenv("PORT")?: 3000) as Int)
        .withConnectionListener(playerRegistry)
        .addMessageListener(MessageTypeByClient.MOVE.toString(), jacksonTypeRef(), this)
        .build()

    init {
        server.start()
    }

    override fun handleMessage(message: Message<MovePayload>) {
        val player: Player = when (val outcome = playerRegistry.getPlayer(message.payload.clientId)) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return server.sendMessage(
                message.payload.clientId,
                Message(MessageTypeByServer.DISPLAY_MESSAGE.toString(), InvalidMove(outcome.error)))

        }

        val gameMovementResult: GameMovementResult = game.makeMovement(player, message.payload.from, message.payload.destiny)

        when (gameMovementResult) {
            is MovementSuccessful -> game = gameMovementResult.newGameState
            is MovementFailed -> {}
            is PlayerWon -> {}
        }
        //TODO: dactivate server when messages send on win

        val movementResult = fromGameResultToMoveResult(gameMovementResult, pieceType)
        when (movementResult){
            is NewGameState -> {
                server.broadcast(
                    Message(MessageTypeByServer.MOVEMENT_SUCCESSFUL.toString(), movementResult)
                )
            }
            is GameOver -> {
                server.sendMessage(message.payload.clientId,
                    Message(MessageTypeByServer.PLAYER_WON.toString(), movementResult))
                server.stop()
            }
            is InvalidMove -> {
                server.sendMessage(message.payload.clientId,
                    Message(MessageTypeByServer.DISPLAY_MESSAGE.toString(), movementResult)
                )
            }
        }
    }

    fun <T>ClientPlayerRegistryNotification(notification: ClientPlayerRegistryNotification<T>){
        when (notification) {
            is SendMessageToClient<*> -> server.sendMessage(
                notification.clientId,
                Message(notification.type, notification.payload as Any)
            )
        }
    }

    fun sendInitialStateToPlayer(clientId: String) {
        server.sendMessage(clientId, Message(MessageTypeByServer.CONNECTED.toString(),
            StartConnectionData(clientId, getInitialState(game, pieceType))
        ))
    }
}

data class MovePayload(val clientId: String, val from: Vector, val destiny: Vector)
enum class MessageTypeByServer{
    DISPLAY_MESSAGE,
    MOVEMENT_SUCCESSFUL,
    PLAYER_WON,
    CONNECTED
}

enum class MessageTypeByClient{
    MOVE,
}

data class StartConnectionData(val clientId: String, val initialState: InitialState)

sealed interface ClientPlayerRegistryNotification<T>
data class SendMessageToClient<T>(val clientId: String, val type: String, val payload: T): ClientPlayerRegistryNotification<T>