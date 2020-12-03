package model

enum class AttachmentType {
    IMAGE, AUDIO, VIDEO
}

data class AttachmentModel(val id: String, val url: String, val type: AttachmentType)