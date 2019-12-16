import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import JsonGenerator.GeneratedType.*
import java.io.OutputStream
import kotlin.random.Random

object JsonGenerator {

    private const val NUMBER_OF_DOCUMENT_DEFAULT = 1
    private const val NUMBER_OF_FIELD_DEFAULT = 10
    private const val BATCH_SIZE = 1000
    private const val CHAR_POOL = "abcdefghijklmnopqrstuvwxyz"

    private val jackson = ObjectMapper()

    fun generate(
        numberOfDocument: Int = NUMBER_OF_DOCUMENT_DEFAULT,
        numberOfField: Int = NUMBER_OF_FIELD_DEFAULT,
        keepSameFieldsForAllDocs: Boolean = false,
        output: OutputStream = System.out
    ) {
        if (numberOfDocument == 0 || numberOfField == 0) {
            return
        }

        output.use { os ->
            jackson.writer().writeValues(os).use { writer ->
                var fields = generateFields(numberOfField)

                if (numberOfDocument == 1) {
                    writer.init(false)
                    writer.write(generateDocument(fields))
                } else {
                    writer.init(true)
                    for (batch in (1..numberOfDocument).chunked(BATCH_SIZE)) {
                        for (i in batch) {
                            fields = if (keepSameFieldsForAllDocs) fields else generateFields(
                                numberOfField
                            )
                            writer.write(generateDocument(fields))
                        }
                    }
                }
            }
        }
    }

    private fun generateFields(numberOfField: Int): Map<String, GeneratedType> {
        val fields = mutableMapOf<String, GeneratedType>()

        for (i in 1..numberOfField) {
            fields[nextString()] = GeneratedType.values()[Random.nextInt(0, GeneratedType.values().size)]
        }

        return fields
    }

    private fun generateDocument(fields: Map<String, GeneratedType>): ObjectNode {
        val objectNode = jackson.createObjectNode()

        fields.forEach { (name, type) ->
            when (type) {
                INT -> objectNode.put(name, Random.nextInt())
                BOOL -> objectNode.put(name, Random.nextBoolean())
                STRING -> objectNode.put(name, nextString())
            }
        }

        return objectNode
    }

    private fun nextString(): String {
        val size = Random.nextInt(1, 20)
        var randomString = StringBuilder("")

        for (i in 0 until size) {
            randomString = randomString.append(CHAR_POOL[Random.nextInt(0, CHAR_POOL.length)])
        }

        return randomString.toString()
    }

    private enum class GeneratedType {
        INT, BOOL, STRING
    }
}
