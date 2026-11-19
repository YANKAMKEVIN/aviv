package com.kev.aviv.common.network

/**
 * Thrown by the repositories when an API call fails, so the UI can tell a connectivity problem
 * from a server error. The message keeps the historical "Error <message>: <code>" format.
 */
class ApiException(val error: NetworkError) : Exception("Error ${error.message}: ${error.code}")
