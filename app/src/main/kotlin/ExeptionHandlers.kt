/**
 * Used when result fails, if it fails it will return the exception thrown by the given result.
 * In case that the given result does not have an error (should not happen) this method will create
 * a generic exception, describing the object and the action.
 */
fun <T, R>exceptionHandler(result: Result<T>, obj: String, action: String): Result<R> {
    val exception: Throwable? = result.exceptionOrNull()
    return  Result.failure(exception ?:
    Exception("A not specified error happened within the $obj performing the action: $action")
    )
}