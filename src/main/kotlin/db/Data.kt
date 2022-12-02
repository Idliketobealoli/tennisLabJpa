package db

import dto.*
import mappers.*
import models.enums.PedidoEstado
import models.enums.Profile
import models.enums.TipoProducto
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class DataLoader {
    val pMapper = ProductoMapper()
    val uMapper = UserMapper()
    val mMapper = MaquinaMapper()
    val tMapper = TareaMapper()
    val turMapper = TurnoMapper()

    val raqueta = ProductoDTO(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83ba"),
        tipoProducto = TipoProducto.RAQUETAS,
        marca = "MarcaRaqueta",
        modelo = "ModeloRaqueta",
        precio = 150.5,
        stock = 3
    )

    val producto1 = ProductoDTO(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83ac"),
        tipoProducto = TipoProducto.ANTIVIBRADORES,
        marca = "MarcaX",
        modelo = "ModeloX",
        precio = 10.5,
        stock = 5
    )

    val producto2 = ProductoDTO(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83ab"),
        tipoProducto = TipoProducto.GRIPS,
        marca = "MarcaY",
        modelo = "ModeloY",
        precio = 20.7,
        stock = 10
    )

    val producto3 = ProductoDTO(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83aa"),
        tipoProducto = TipoProducto.FUNDAS,
        marca = "MarcaZ",
        modelo = "ModeloZ",
        precio = 36.4,
        stock = 8
    )

    val cordaje = ProductoDTO(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea839b"),
        tipoProducto = TipoProducto.CORDAJES,
        marca = "CordajEx",
        modelo = "C945-Alpha",
        precio = 53.1,
        stock = 4
    )

    val client = UserDTO(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea839a"),
        nombre = "Maria",
        apellido = "Martinez",
        telefono = "632120281",
        email = "email2@email.com",
        password = "contra",
        perfil = Profile.CLIENT
    )

    val worker = UserDTO(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea839c"),
        nombre = "Luis",
        apellido = "Martinez",
        telefono = "632950281",
        email = "email@email.com",
        password = "estacontraseñanoestaensha512",
        perfil = Profile.WORKER
    )

    val personalizadora1 = PersonalizadoraDTO(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83bb"),
        modelo = "RTX-3080TI",
        marca = "Nvidia",
        fechaAdquisicion = LocalDate.parse("2022-11-10"),
        numeroSerie = "123456789X",
        measuresRigidity = false,
        measuresBalance = true,
        measuresManeuverability = true
    )

    val encordadora1 = EncordadoraDTO(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83bc"),
        modelo = "ENC-4070Turbo",
        marca = "Genericosas Marca Registrada",
        fechaAdquisicion = LocalDate.parse("2021-10-01"),
        numeroSerie = "975318642Q",
        isManual = true,
        maxTension = 15.2,
        minTension = 5.1
    )

    val adquisicion1 = AdquisicionDTO(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83ca"),
        raqueta = pMapper.fromDTO(raqueta),
        user = uMapper.fromDTO(client),
        productoAdquirido = pMapper.fromDTO(producto1)
    )

    val adquisicion2 = AdquisicionDTO(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83cb"),
        raqueta = pMapper.fromDTO(raqueta),
        user = uMapper.fromDTO(client),
        productoAdquirido = pMapper.fromDTO(producto2)
    )

    val adquisicion3 = AdquisicionDTO(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83cc"),
        raqueta = pMapper.fromDTO(raqueta),
        user = uMapper.fromDTO(client),
        productoAdquirido = pMapper.fromDTO(producto3)
    )

    val encordado = EncordadoDTO(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea8aaa"),
        raqueta = pMapper.fromDTO(raqueta),
        user = uMapper.fromDTO(client),
        tensionHorizontal = 25.5,
        cordajeHorizontal = pMapper.fromDTO(cordaje),
        tensionVertical = 23.1,
        cordajeVertical = pMapper.fromDTO(cordaje),
        dosNudos = true
    )

    val personalizacion = PersonalizacionDTO(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea8aab"),
        raqueta = pMapper.fromDTO(raqueta),
        user = uMapper.fromDTO(client),
        peso = 890,
        balance = 15.4,
        rigidez = 4
    )

    fun getUsers() = listOf(
        worker,
        client,
        UserDTO(
            id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea8aac"),
            nombre = "Admin",
            apellido = "Administrador",
            telefono = "000000000",
            email = "admin@email.com",
            password = "admin",
            perfil = Profile.ADMIN
        )
    )

    fun getMaquinas() = listOf(
        personalizadora1,
        PersonalizadoraDTO(
            id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea8aba"),
            modelo = "RX-480",
            marca = "Sapphire Radeon",
            fechaAdquisicion = LocalDate.parse("2020-01-01"),
            numeroSerie = "111111111A",
            measuresRigidity = true,
            measuresBalance = true,
            measuresManeuverability = true
        ),
        EncordadoraDTO(
            id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea8abb"),
            modelo = "ENC-2022XT",
            marca = "EncordadorasSL",
            fechaAdquisicion = LocalDate.parse("2011-11-11"),
            numeroSerie = "124356879W",
            isManual = false,
            maxTension = 25.5,
            minTension = 9.7
        ),
        encordadora1
    )

    fun getProductos() = listOf(
        raqueta, producto1, producto2, producto3, cordaje
    )

    fun getTareas() = listOf(
        adquisicion1,
        adquisicion2,
        adquisicion3,
        encordado,
        personalizacion
    )

    fun getTurnos() = listOf(
        TurnoDTO(
            id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea8abc"),
            worker = uMapper.fromDTO(worker),
            maquina = mMapper.fromDTO(personalizadora1),
            horaInicio = LocalDateTime.of(2002,10,14,10,9),
            horaFin = null,
            tarea1 = tMapper.fromDTO(personalizacion),
            tarea2 = tMapper.fromDTO(adquisicion1)
        ),
        TurnoDTO(
            id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea8aca"),
            worker = uMapper.fromDTO(worker),
            maquina = mMapper.fromDTO(encordadora1),
            horaInicio = LocalDateTime.of(2022,1,10,1,0),
            horaFin = null,
            tarea1 = tMapper.fromDTO(encordado),
            tarea2 = tMapper.fromDTO(adquisicion2)
        ),
        TurnoDTO(
            id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea8acb"),
            worker = uMapper.fromDTO(worker),
            maquina = mMapper.fromDTO(personalizadora1),
            horaInicio = LocalDateTime.of(2022,5,5,5,5),
            horaFin = null,
            tarea1 = tMapper.fromDTO(adquisicion3),
            tarea2 = null
        )
    )

    fun getPedidos() = listOf(
        PedidoDTO(
            id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea8acc"),
            tareas = tMapper.fromDTO(getTareas()),
            client = uMapper.fromDTO(client),
            turnos = turMapper.fromDTO(getTurnos()),
            state = PedidoEstado.PROCESO,
            fechaEntrada = LocalDate.parse("2013-10-10"),
            fechaProgramada = getTurnos().maxBy { it.horaFin }
                .horaFin.toLocalDate() ?: LocalDate.parse("2023-12-12"),
            fechaSalida = getTurnos().maxBy { it.horaFin }
                .horaFin.plusDays(5L).toLocalDate() ?: LocalDate.parse("2023-12-12"),
            fechaEntrega = null
        )
    )
}