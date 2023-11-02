package boardGame.pieceEatingRuler

import boardGame.piece.Piece

interface PieceEatingRuler {
    //return if one boardGame.piece is able to eat another boardGame.piece, not considering position
    fun canPieceEatPiece(eater: Piece, eaten: Piece) : Boolean
}