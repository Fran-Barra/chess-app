package boardGame.game

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.piece.Piece
import boardGame.player.Player
import edu.austral.dissis.chess.gui.*

//TODO: make an basic adapter class
//TODO: make an Adapter builder that allows me to change the way things are calculated (the use of strategies)
fun getInitialState(game: Game, pieceTypeToString: GetPieceFromTypeInStringFormat): InitialState =
    InitialState(
        getSquareBoardSize(game.getBoard()),
        fromGameToListPieces(game, pieceTypeToString),
        fromPlayerToPlayerColor(game.getActualPlayer())
    )

fun fromGameResultToMoveResult(gameResult: GameMovementResult, pieceTypeToString: GetPieceFromTypeInStringFormat): MoveResult {
    return when (gameResult) {
        is PlayerWon -> GameOver(fromPlayerToPlayerColor(gameResult.player))
        is MovementFailed -> InvalidMove(gameResult.message)
        is MovementSuccessful -> {
            NewGameState(fromGameToListPieces(
                gameResult.newGameState, pieceTypeToString),
                fromPlayerToPlayerColor(gameResult.newGameState.getActualPlayer())
            )
        }
    }
}


private fun getSquareBoardSize(board: Board): BoardSize {
    val positions: List<Pair<Vector, Piece?>> = board.getBoardAssList()
    var xMin = positions[0].first.x
    var xMax = positions[0].first.x
    var yMin = positions[0].first.y
    var yMax = positions[0].first.y

    for (position in positions) {
        if (position.first.x < xMin) xMin = position.first.x
        if (position.first.x > xMax) xMax = position.first.x
        if (position.first.y < yMin) yMin = position.first.y
        if (position.first.y > yMax) yMax = position.first.y

    }

    return BoardSize(xMax-xMin+1, yMax-yMin+1)
}

fun fromGameToListPieces(game: Game, pieceTypeToString: GetPieceFromTypeInStringFormat): List<ChessPiece>{
    return game.getBoard().getPiecesAndPosition().map { pv ->
        fromPieceToChessPiece(pv.first, pv.second, pieceTypeToString)
    }
}

private fun fromPieceToChessPiece(piece: Piece, vec: Vector, pieceTypeToString: GetPieceFromTypeInStringFormat): ChessPiece{
    return ChessPiece(
        piece.getPieceId().toString(),
        fromPieceGetPlayerColor(piece),
        fromVectorToPos(vec),
        pieceTypeToString.getPieceTypeInStringFormat(piece))
}

private fun fromPieceGetPlayerColor(piece: Piece): PlayerColor {
    return fromColorIdToPlayerColor(piece.getPieceColor())
}

fun fromPosToVector(pos: Position): Vector {
    return Vector(pos.column, pos.row)
}

fun fromVectorToPos(vec: Vector): Position {
    return Position(vec.y, vec.x)
}

fun fromPlayerToPlayerColor(player: Player): PlayerColor{
    return fromColorIdToPlayerColor(player.getPlayerId())
}

private fun fromColorIdToPlayerColor(colorId: Int): PlayerColor {
    if (colorId == 0) return PlayerColor.WHITE
    return PlayerColor.BLACK
}

class GetPieceFromTypeInStringFormat(private val idToString: Map<Int, String>, private val defaultValue: String) {
    fun getPieceTypeInStringFormat(piece: Piece): String {
        return idToString[piece.getPieceType()] ?: defaultValue
    }
}
data class PieceToString(val idToString: Map<Int, String>, val defaultValue: String)
