package connection

import boardGame.game.fromPosToVector
import boardGame.movement.MovementResult
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import connection.server.MessageTypeByClient
import connection.server.MessageTypeByServer
import connection.server.MovePayload
import connection.server.StartConnectionData
import edu.austral.dissis.chess.gui.*
import edu.austral.ingsis.clientserver.Client
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener
import edu.austral.ingsis.clientserver.netty.client.NettyClientBuilder
import javafx.application.Platform
import javafx.scene.Scene
import javafx.stage.Stage
import java.net.InetSocketAddress

class ClientGame(val application: BoardApplication): GameEventListener {
    private lateinit var clientId: String
    private val client: Client = NettyClientBuilder.createDefault()
        .withAddress(InetSocketAddress(3000))
        .addMessageListener(
            MessageTypeByServer.CONNECTED.toString(), jacksonTypeRef(), ClientConnectedAsPlayer(this)
        )
        .addMessageListener(
            MessageTypeByServer.MOVEMENT_SUCCESSFUL.toString(), jacksonTypeRef(), GameUpdateListener(this)
        )
        .addMessageListener(
            MessageTypeByServer.PLAYER_WON.toString(), jacksonTypeRef(), GameOverListener(this)
        )
        .addMessageListener(
            MessageTypeByServer.DISPLAY_MESSAGE.toString(), jacksonTypeRef(), InvalidMoveListener(this)
        )
        .build()


    init {
        client.connect()
    }

    fun getGameView(initializedGameView: GameView){
        initializedGameView.addListener(this)
        application.updateUI(initializedGameView)
    }

    fun setId(id: String) {
        clientId = id
    }

    override fun handleMove(move: Move) {
        client.send(Message(
            MessageTypeByClient.MOVE.toString(),
            MovePayload(clientId.toString(), fromPosToVector(move.from), fromPosToVector(move.to))
        ))
    }

    fun newGameState(newGameState: MoveResult){
        application.handleMoveResult(newGameState)
    }
}

class ClientConnectedAsPlayer(val gameClient: ClientGame): MessageListener<StartConnectionData> {
    override fun handleMessage(message: Message<StartConnectionData>) {
        val gameView = GameView(CachedImageResolver(DefaultImageResolver()))
        gameView.handleInitialState(message.payload.initialState)
        gameClient.getGameView(gameView)
        gameClient.setId(message.payload.clientId)
    }
}


class GameUpdateListener(private val gmAdapter: ClientGame): MessageListener<NewGameState> {
    override fun handleMessage(message: Message<NewGameState>) {
        gmAdapter.newGameState(message.payload)
    }
}

class GameOverListener(private val gmAdapter: ClientGame): MessageListener<GameOver> {
    override fun handleMessage(message: Message<GameOver>) {
        gmAdapter.newGameState(message.payload)
    }
}

class InvalidMoveListener(private val gmAdapter: ClientGame): MessageListener<InvalidMove> {
    override fun handleMessage(message: Message<InvalidMove>) {
        gmAdapter.newGameState(message.payload)
    }
}


