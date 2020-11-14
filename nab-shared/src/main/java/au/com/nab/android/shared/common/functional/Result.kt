package au.com.nab.android.shared.common.functional

/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Result] are either an instance of [Success] or [Failure].
 * FP Convention dictates that [Success] is used for "failure"
 * and [Failure] is used for "success".
 *
 * @see Success
 * @see Failure
 */
sealed class Result<out L, out R> {

    object Loading : Result<Nothing, Nothing>()

    /** * Represents the left side of [Result] class which by convention is a "Failure". */
    data class Success<out L>(val data: L) : Result<L, Nothing>()

    /** * Represents the right side of [Result] class which by convention is a "Success". */
    data class Failure<out R>(val error: R) : Result<Nothing, R>()

    /**
     * Returns true if this is a Failure, false otherwise.
     * @see Failure
     */
    val isFailure get() = this is Failure<R>

    /**
     * Returns true if this is a Success, false otherwise.
     * @see Success
     */
    val isSuccess get() = this is Success<L>

    /**
     * Returns true if this is a Loading
     * @see Loading
     */
    val isLoading get() = this is Loading

    /**
     * Creates a Success type.
     * @see Success
     */
    fun <L> success(data: L) = Success(data)


    /**
     * Creates a Success type.
     * @see Failure
     */
    fun <R> failure(error: R) = Failure(error)

    /**
     * Applies fnL if this is a Success or fnR if this is a Failure.
     * @see Success
     * @see Failure
     */
    fun fold(
        onSuccess: (L) -> Unit,
        onFailure: ((R) -> Unit)? = null,
        onLoading: (() -> Unit)? = null
    ) {
        when (this) {
            is Success -> onSuccess(data)
            is Failure -> onFailure?.invoke(error)
            is Loading -> onLoading?.invoke()
        }
    }
}

fun <A, B, C> ((A) -> B).c(f: (B) -> C): (A) -> C = { f(this(it)) }

/**
 * Success-biased onFailure() FP convention dictates that when this class is Success, it'll perform
 * the onFailure functionality passed as a parameter, but, overall will still return an either
 * object so you chain calls.
 */
fun <L, R> Result<L, R>.onSuccess(fn: (failure: L) -> Unit): Result<L, R> =
    this.apply {
        if (this is Result.Success) fn(data)
    }

/**
 * Failure-biased onSuccess() FP convention dictates that when this class is Failure, it'll perform
 * the onSuccess functionality passed as a parameter, but, overall will still return an either
 * object so you chain calls.
 */
fun <L, R> Result<L, R>.onFailure(fn: (success: R) -> Unit): Result<L, R> =
    this.apply { if (this is Result.Failure) fn(error) }
