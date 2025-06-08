package com.niolasdev.randommouse.domain

interface MergeStrategy<E> {
    fun merge(cache: E, server: E): E
}

internal class RequestResponseMergeStrategy<T : Any> : MergeStrategy<CatResult<T>> {
    @Suppress("CyclomaticComplexMethod")
    override fun merge(cache: CatResult<T>, server: CatResult<T>): CatResult<T> {
        return when {
            cache is CatResult.InProgress && server is CatResult.InProgress -> merge(
                cache,
                server
            )

            cache is CatResult.Success && server is CatResult.InProgress -> merge(
                cache,
                server
            )

            cache is CatResult.InProgress && server is CatResult.Success -> merge(
                cache,
                server
            )

            cache is CatResult.Success && server is CatResult.Success -> merge(
                cache,
                server
            )

            cache is CatResult.Success && server is CatResult.Error -> merge(cache, server)
            cache is CatResult.InProgress && server is CatResult.Error -> merge(
                cache,
                server
            )

            cache is CatResult.Error && server is CatResult.InProgress -> merge(
                cache,
                server
            )

            cache is CatResult.Error && server is CatResult.Success -> merge(cache, server)

            else -> error("Unimplemented branch cache=$cache & server=$server")
        }
    }

    private fun merge(
        cache: CatResult.InProgress<T>,
        server: CatResult.InProgress<T>
    ): CatResult<T> {
        return CatResult.InProgress()
    }

    @kotlin.Suppress("UNUSED_PARAMETER")
    private fun merge(
        cache: CatResult.Success<T>,
        server: CatResult.InProgress<T>
    ): CatResult<T> {
        return CatResult.InProgress()
    }

    @kotlin.Suppress("UNUSED_PARAMETER")
    private fun merge(
        cache: CatResult.InProgress<T>,
        server: CatResult.Success<T>
    ): CatResult<T> {
        return CatResult.InProgress(server.data)
    }

    private fun merge(
        cache: CatResult.Success<T>,
        server: CatResult.Error<T>
    ): CatResult<T> {
        return CatResult.Error(e = server.e)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun merge(
        cache: CatResult.Success<T>,
        server: CatResult.Success<T>
    ): CatResult<T> {
        return CatResult.Success(data = server.data)
    }

    private fun merge(
        cache: CatResult.InProgress<T>,
        server: CatResult.Error<T>
    ): CatResult<T> {
        return CatResult.Error(data = server.data ?: cache.data, e = server.e)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun merge(
        cache: CatResult.Error<T>,
        server: CatResult.InProgress<T>
    ): CatResult<T> {
        return server
    }

    @Suppress("UNUSED_PARAMETER")
    private fun merge(
        cache: CatResult.Error<T>,
        server: CatResult.Success<T>
    ): CatResult<T> {
        return server
    }
}
