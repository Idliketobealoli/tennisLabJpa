package menu

import dto.UserDTO
import models.enums.Profile
import util.betweenXandY

suspend fun menu(user: UserDTO) {
    var back = false
    while (!back) {
        println(" - Please select one of the following actions.")
        when (user.perfil) {
            Profile.CLIENT -> {
                println("""
                1. Pedidos
                2. Productos
                3. Back
                4. Modo Presentacion [Recomendado - CRUD DE TODO]
                """.trimIndent())
                var res = ""
                while (!betweenXandY(res, 1, 4)) {
                    res = readln()
                }
                when (res.toInt()) {
                    1 -> menuPedidos(Profile.CLIENT)
                    2 -> menuProductos(Profile.CLIENT)
                    3 -> back = true
                    4 -> modoPresentacion()
                }
            }
            Profile.WORKER -> {
                println("""
                1. Pedidos
                2. Tareas
                3. Turnos
                4. Maquinas
                5. Back
                6. Modo Presentacion [Recomendado - CRUD DE TODO]
                """.trimIndent())
                var res = ""
                while (!betweenXandY(res, 1, 6)) {
                    res = readln()
                }
                when (res.toInt()) {
                    1 -> menuPedidos(Profile.WORKER)
                    2 -> menuTareas(Profile.WORKER)
                    3 -> menuTurnos(Profile.WORKER)
                    4 -> menuMaquinas(Profile.WORKER)
                    5 -> back = true
                    6 -> modoPresentacion()
                }
            }
            Profile.ADMIN -> {
                println("""
                1. Users
                2. Pedidos [menu sin implementar]
                3. Tareas [menu sin implementar]
                4. Turnos [menu sin implementar]
                5. Productos [menu sin implementar]
                6. Maquinas
                7. Back
                8. Modo presentacion [recomendado - CRUD DE TODO]
                """.trimIndent())
                var res = ""
                while (!betweenXandY(res, 1, 8)) {
                    res = readln()
                }
                when (res.toInt()) {
                    1 -> menuUsers()
                    2 -> menuPedidos(Profile.ADMIN)
                    3 -> menuTareas(Profile.ADMIN)
                    4 -> menuTurnos(Profile.ADMIN)
                    5 -> menuProductos(Profile.ADMIN)
                    6 -> menuMaquinas(Profile.ADMIN)
                    7 -> back = true
                    8 -> modoPresentacion()
                }
            }
        }
    }
}