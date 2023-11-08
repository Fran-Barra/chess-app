package boardGame.game

import boardGame.board.Board
import boardGame.board.Vector
import edu.austral.dissis.chess.gui.*
import boardGame.piece.Piece
import boardGame.player.Player
import chess.game.FromPieceTypeToString

class GameEngineAdapter(private var game: Game,
    private val pieceTypeToString: FromPieceTypeToString
): GameEngine {
    override fun init(): InitialState {
        val boardSize: Pair<Int, Int> = getSquareBoardSize(game.getBoard())
        return InitialState(BoardSize(boardSize.first, boardSize.second),
            fromGameToListPieces(game),
            fromPlayerToPlayerColor(game.getActualPlayer()))
    }

    override fun applyMove(move: Move): MoveResult {
        return fromGameResultToMoveResult(
            game.makeMovement(
                game.getActualPlayer(), fromPosToVector(move.from), fromPosToVector(move.to)
            )
        )
    }

    private fun fromPosToVector(pos: Position): Vector{
        return Vector(pos.column, pos.row)
    }

    private fun fromVectorToPos(vec: Vector): Position{
        return Position(vec.y, vec.x)
    }

    private fun fromGameResultToMoveResult(gameResult: GameMovementResult): MoveResult {
        return when (gameResult) {
            is PlayerWon -> GameOver(fromPlayerToPlayerColor(gameResult.player))
            is MovementFailed -> InvalidMove(gameResult.message)
            is MovementSuccessful -> {
                game = gameResult.newGameState
                NewGameState(fromGameToListPieces(game), fromPlayerToPlayerColor(game.getActualPlayer()))
            }
        }
    }

    private fun fromGameToListPieces(game: Game): List<ChessPiece>{
        return game.getBoard().getPiecesAndPosition().map { pv ->
            fromPieceToChessPiece(pv.first, pv.second)
        }
    }

    private fun fromPieceToChessPiece(piece: Piece, vec: Vector): ChessPiece{
        return ChessPiece(
            piece.toString(),
            fromPieceGetPlayerColor(piece),
            fromVectorToPos(vec),
            pieceTypeToString.getPieceTypeInStringFormat(piece))
    }

    private fun fromPlayerToPlayerColor(player: Player): PlayerColor{
        return fromColorIdToPlayerColor(player.getPlayerId())
    }

    private fun fromPieceGetPlayerColor(piece: Piece): PlayerColor{
        return fromColorIdToPlayerColor(piece.getPieceColor())
    }

    private fun fromColorIdToPlayerColor(colorId: Int): PlayerColor{
        if (colorId == 0) return PlayerColor.WHITE
        return PlayerColor.BLACK
    }

    private fun getSquareBoardSize(board: Board): Pair<Int, Int> {
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

        return Pair(xMax-xMin, yMax-yMin)
    }
}

interface GetPieceFromTypeInStringFormat {
    fun getPieceTypeInStringFormat(piece: Piece): String
}