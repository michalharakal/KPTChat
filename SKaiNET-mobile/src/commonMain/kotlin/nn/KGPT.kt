package nn

class KGPT {
    fun generate(from: String, maxNewTokens: Int): String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9') + ' '

        return (1..maxNewTokens)
            .map { charset.random() }
            .joinToString("")
    }
}