package dto

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import java.time.LocalDate
import java.util.*

/**
 * @author Ivan Azagra Troya
 *
 * Clase POKO de Encordadora usada en el programa.
 * Consta de un metodo toString() overrideado que
 * devuelve el objeto como JSON, asi como un metodo
 * fromJSON y un metodo toJSON.
 */
class EncordadoraDTO(): MaquinaDTO {
    @Expose
    lateinit var id: UUID
    @Expose
    lateinit var modelo: String
    @Expose
    lateinit var marca: String
    lateinit var fechaAdquisicion: LocalDate
    @Expose
    lateinit var fechaAdquisicionString: String
    @Expose
    lateinit var numeroSerie: String
    @Expose
    var isManual: Boolean = true
    @Expose
    var maxTension: Double = 0.0
    @Expose
    var minTension: Double = 0.0

    constructor(
        id: UUID?,
        modelo: String,
        marca: String,
        fechaAdquisicion: LocalDate?,
        numeroSerie: String,
        isManual: Boolean,
        maxTension: Double,
        minTension: Double
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.modelo = modelo
        this.marca = marca
        this.fechaAdquisicion = fechaAdquisicion ?: LocalDate.now()
        this.fechaAdquisicionString = this.fechaAdquisicion.toString()
        this.numeroSerie = numeroSerie
        this.isManual = isManual
        this.maxTension = maxTension
        this.minTension = minTension
    }

    fun fromJSON(json: String): EncordadoraDTO? {
        return Gson().fromJson(json, EncordadoraDTO::class.java)
    }

    override fun toJSON(): String {
        return GsonBuilder().setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .create().toJson(this)
    }

    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .create().toJson(this)
    }
}