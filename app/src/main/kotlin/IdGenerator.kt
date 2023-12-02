object IdGenerator {
    private var id: Int = 100_000
    fun getNewId() = id++
}