package connection

import boardGame.game.GetPieceFromTypeInStringFormat
import checkers.game.checkersIdToString
import checkers.game.gameFactory.CheckersFactory
import connection.server.ServerGameAdapter

fun main(){
    ServerGameAdapter(CheckersFactory.getGame(),
        GetPieceFromTypeInStringFormat(checkersIdToString.idToString, checkersIdToString.defaultValue)
    )
}

//
////TODO: use a IP
//class ServerGame: Application() {
//    private var server: Server = NettyServerBuilder.createDefault().build()
//    private var game: Game = CheckersFactory.getGame()
//
//    override fun start(primaryStage: Stage?) {
//        game.getTurnController().getPlayers()
//        server = initializeServer(game)
//        server.start()
//    }
//
//    private fun initializeServer(game: Game): Server =
//        NettyServerBuilder.createDefault()
//            .withPort(System.getenv("PORT").toInt())
//            .withConnectionListener(ServerGameAdapter(game))
//            .build()
//}