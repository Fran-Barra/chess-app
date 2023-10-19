package piece

class BasicPiece(private val type: Int, private val color: Int): Piece {
    override fun getPieceType(): Int = type
    override fun getPieceColor(): Int = color

}