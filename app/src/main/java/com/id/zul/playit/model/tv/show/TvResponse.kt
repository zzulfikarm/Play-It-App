package com.id.zul.mtv.data.model.tv

data class TvResponse(
    val page: Int,
    val results: MutableList<Tv>,
    val total_pages: Int,
    val total_results: Int
)