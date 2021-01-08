package com.riki.net89

import com.google.gson.annotations.SerializedName

data class Content(
    val id_content: Int,
    val title: String?,
    @SerializedName("description")
    val text: String?
)

data class ContentResponse(
    val status: Boolean,
    val message: String?,
    val data : ArrayList<Content>
)