package player

class MulticolorPlayer(val colors: Collection<Int>): Player {
    override fun playerControlColor(colorId: Int): Boolean = colors.contains(colorId)
}