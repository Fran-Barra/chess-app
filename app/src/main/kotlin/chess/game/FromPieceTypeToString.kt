package chess.game

import boardGame.game.GetPieceFromTypeInStringFormat
import boardGame.piece.Piece

class FromPieceTypeToString: GetPieceFromTypeInStringFormat {
    override fun getPieceTypeInStringFormat(piece: Piece): String{
        return when (piece.getPieceType()) {
            0 -> "king"
            1 -> "queen"
            2 -> "bishop"
            3 -> "knight"
            4 -> "rook"
            5 -> "pawn"
            6 -> "archbishop"
            else -> "ghost"
        }
    }
}