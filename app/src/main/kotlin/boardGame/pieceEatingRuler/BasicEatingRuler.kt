package boardGame.pieceEatingRuler

import boardGame.piece.Piece

class BasicEatingRuler: PieceEatingRuler {
    override fun canPieceEatPiece(eater: Piece, eaten: Piece): Boolean =
        eater.getPieceColor() != eaten.getPieceColor()
}