package nn

class KGPT(modelIndex:Int) {
    fun generate(from: String, maxNewTokens: Int): String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9') + ' '

        return (1..maxNewTokens)
            .map { charset.random() }
            .joinToString("")
    }

    fun loadModelContent(content:String) {

    }
}