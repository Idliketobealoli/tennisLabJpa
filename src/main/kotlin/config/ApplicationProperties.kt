package config

import mu.KotlinLogging
import java.io.IOException
import java.util.*

val logger = KotlinLogging.logger {  }

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Clase que carga y lee el fichero application.properties
 */
class ApplicationProperties {
    private val properties: Properties = Properties()

    init {
        try {
            properties.load(javaClass.classLoader.getResourceAsStream("application.properties"))
        } catch (e: IOException) {
            logger.error { "An IOException occurred while reading properties file: ${e.message}" }
        }
    }

    fun readProperty(keyName: String?): String {
        return properties.getProperty(keyName, "This key doesn't exist in properties file")
    }
}