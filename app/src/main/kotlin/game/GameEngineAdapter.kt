package game

import edu.austral.dissis.chess.gui.*
import piece.Piece
import player.Player
import vector.Vector


class GameEngineAdapter(private var game: Game): GameEngine {
    override fun init(): InitialState {
        return InitialState(BoardSize(8, 8),
            fromGameToListPieces(game),
            fromPlayerToPlayerColor(game.actualPlayer))
    }

    override fun applyMove(move: Move): MoveResult {
        return fromGameResultToMoveResult(
            game.makeMovement(
                game.actualPlayer, fromPosToVector(move.from), fromPosToVector(move.to)
            )
        )
    }

    private fun fromPosToVector(pos: Position): Vector{
        return Vector(pos.column, pos.row)
    }

    private fun fromVectorToPos(vec: Vector): Position{
        return Position(vec.y, vec.x)
    }

    private fun fromGameResultToMoveResult(gameResult: Result<Game>): MoveResult{
        if (gameResult.isFailure){
            val r = gameResult.exceptionOrNull() ?: return InvalidMove("Unexpected failure");
            return InvalidMove(r.message!!)
        }else{
            //TODO: winning
            game = gameResult.getOrThrow()
            return NewGameState(
                fromGameToListPieces(game),
                fromPlayerToPlayerColor(game.actualPlayer)
            )
        }
    }

    private fun fromGameToListPieces(game: Game): List<ChessPiece>{
        return game.board.getPiecesAndPosition().map { pv ->
            fromPieceToChessPiece(pv.first, pv.second)
        }
    }

    private fun fromPieceToChessPiece(piece: Piece, vec: Vector): ChessPiece{
        return ChessPiece(
            piece.toString(),
            fromPieceGetPlayerColor(piece),
            fromVectorToPos(vec),
            getPieceTypeInStringFormat(piece))
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

    private fun getPieceTypeInStringFormat(piece: Piece): String{
        return when (piece.getPieceType()) {
            0 -> "king"
            1 -> "queen"
            2 -> "bishop"
            3 -> "knight"
            4 -> "rook"
            5 -> "pawn"
            else -> "ghost"
        }
    }
}