package boardGame.piece

import IdGenerator

class BasicPiece(private val type: Int, private val color: Int, private val ID: Int = IdGenerator.getNewId()): Piece {
    override fun getPieceType(): Int = type
    override fun getPieceColor(): Int = color
    override fun getPieceId(): Int = ID
}