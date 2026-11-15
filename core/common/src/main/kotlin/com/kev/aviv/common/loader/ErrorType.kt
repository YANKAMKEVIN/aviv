package com.kev.aviv.common.loader

import com.kev.aviv.common.network.ApiException
import com.kev.aviv.common.network.NetworkError
import java.io.IOException

/** What went wrong, as far as the user is concerned. */
enum class ErrorType {
    /** No connection / host unreachable: the user can fix it. */
    NETWORK,

    /** The API answered with an error status. */
    SERVER,

    UNKNOWN,
}

fun Throwable?.toErrorType(): ErrorType = when (this) {
    is IOException -> ErrorType.NETWORK
    is ApiException -> when (error) {
        is NetworkError.UnResolveAddress -> ErrorType.NETWORK
        is NetworkError.Unknown -> ErrorType.UNKNOWN
        else -> ErrorType.SERVER
    }

    else -> ErrorType.UNKNOWN
}
