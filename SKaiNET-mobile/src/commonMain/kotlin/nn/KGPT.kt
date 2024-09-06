package nn

import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class KGPT(modelIndex: Int) {
    var loaded = false
    val shakespeare = """"O gentle moon, that sails the velvet skies,
Thy silver beams doth grace the lover's path.
Yet fickle Fortune, with her cruel hand,
Doth turn the wheel and scatter dreams to dust.
Why doth the heart, so filled with tender hope,
Beat on in sorrow when the light is lost?
For love, though pure, doth tremble at the storm,
And yields to shadows creeping on the soul.
Yet, in the darkest hour, shall truth arise,
And from despair, sweet joy shall be reborn.
For time doth heal the deepest wound,
And in its course, the broken heart finds peace,
Restoring faith in love once more,
When dawn returns to chase the night away.""""

    fun generate(from: String, maxNewTokens: Int): String {
        return if (!loaded) {
            val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9') + ' '

            return (1..maxNewTokens)
                .map { charset.random() }
                .joinToString("")
        } else {
            val range = shakespeare.indices// Define your range
            val randomIndex = Random.nextInt(range.first, range.last) // Generate random index
            val safeStart = min(randomIndex, shakespeare.length - 1)

            shakespeare.substring(
                max(0, safeStart),
                min(
                    randomIndex + maxNewTokens,
                    shakespeare.length - 1
                )
            )
        }
    }

    fun loadModelContent(content: String) {
        loaded = true
    }
}