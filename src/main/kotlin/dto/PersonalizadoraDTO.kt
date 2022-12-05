package dto

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import java.time.LocalDate
import java.util.*

/**
 * @author Ivan Azagra Troya
 *
 * Clase POKO de Personalizadora usada en el programa.
 * Consta de un metodo toString() overrideado que
 * devuelve el objeto como JSON, asi como un metodo
 * fromJSON y un metodo toJSON.
 */
class PersonalizadoraDTO(): MaquinaDTO {
    @Expose
    lateinit var id: UUID
    @Expose
    lateinit var modelo: String
    @Expose
    lateinit var marca: String
    lateinit var fechaAdquisicion: LocalDate
    @Expose lateinit var fechaAdquisicionString: String
    @Expose
    lateinit var numeroSerie: String
    @Expose
    var measuresManeuverability: Boolean = true
    @Expose
    var measuresBalance: Boolean = true
    @Expose
    var measuresRigidity: Boolean = true

    constructor(
        id: UUID?,
        modelo: String,
        marca: String,
        fechaAdquisicion: LocalDate?,
        numeroSerie: String,
        measuresManeuverability: Boolean,
        measuresBalance: Boolean,
        measuresRigidity: Boolean
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.modelo = modelo
        this.marca = marca
        this.fechaAdquisicion = fechaAdquisicion ?: LocalDate.now()
        this.fechaAdquisicionString = this.fechaAdquisicion.toString()
        this.numeroSerie = numeroSerie
        this.measuresManeuverability = measuresManeuverability
        this.measuresBalance = measuresBalance
        this.measuresRigidity = measuresRigidity
    }

    fun fromJSON(json: String): PersonalizadoraDTO? {
        return Gson().fromJson(json, PersonalizadoraDTO::class.java)
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