package checkers.game

import boardGame.game.GetPieceFromTypeInStringFormat
import boardGame.piece.Piece

class FromPieceTypeToString: GetPieceFromTypeInStringFormat {
    override fun getPieceTypeInStringFormat(piece: Piece): String {
        return when (piece.getPieceType()){
            0 -> "queen"
            1 -> "pawn"
            else -> "ghost"
        }
    }
}