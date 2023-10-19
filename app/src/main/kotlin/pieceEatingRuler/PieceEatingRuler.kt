package pieceEatingRuler

import piece.Piece

interface PieceEatingRuler {
    //return if one piece is able to eat another piece, not considering position
    fun canPieceEatPiece(eater: Piece, eaten: Piece) : Boolean
}