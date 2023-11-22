package boardGame.piece

import java.util.*

class BasicPiece(private val type: Int, private val color: Int, private val ID: UUID = UUID.randomUUID()): Piece {
    override fun getPieceType(): Int = type
    override fun getPieceColor(): Int = color
    override fun getPieceId(): UUID = ID
}