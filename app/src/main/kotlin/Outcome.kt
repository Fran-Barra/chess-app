sealed interface Outcome<T>
data class SuccessfulOutcome<T>(val data: T): Outcome<T>
data class FailedOutcome<T>(val error: String): Outcome<T>