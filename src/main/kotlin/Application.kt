import java.io.File
import java.io.FileOutputStream

fun main(args: Array<String>) {
    when (args.size) {
        0 -> JsonGenerator.generate()
        1 -> JsonGenerator.generate(args[0].toInt())
        2 -> JsonGenerator.generate(args[0].toInt(), args[1].toInt())
        3 -> JsonGenerator.generate(
            args[0].toInt(),
            args[1].toInt(),
            args[2].toBoolean()
        )
        else -> JsonGenerator.generate(
            args[0].toInt(),
            args[1].toInt(),
            args[2].toBoolean(),
            FileOutputStream(File(args[3]))
        )
    }
}
