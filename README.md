# API DE TENIS - JPA 
### Hecho por: 
- Daniel Rodriguez Muñoz
- Ivan Azagra Troya

### [Enlace al video en Google Drive](https://drive.google.com/file/d/1Ca_K6DGbzdKIePWy1A-u3_kPTeXk4c8l/view?usp=sharing)

---

### PROGRAMA PRINCIPAL

<details>
  <summary>Config</summary>

### ApplicationProperties:

Esta clase simplemente se encarga de cargar las propiedades del fichero 
application.properties y si no lo consigue devuelve una IOException.
</details>

---

<details>
  <summary>Controllers</summary>

Los controladores se encargan de llamar a los servicios para que hagan sus respectivas 
funciones y de pasar a JSON lo que dichos servicios devuelvan, o un string de error si 
estos devuelven null, ya sea por un error o porque se ha buscado algo que no figura en la BD.

Son objects, por lo que son singletons thread-safe. Esta decisión se tomó para asegurarnos de que 
siempre que se haga una operacion en el programa, se pase por los mismos objetos controller, y no haya 
multiples objetos instanciados de estos cuando sería redundante.

Cada controlador tiene su(s) servicio(s) y las siguientes funciones (a excepcion de unos 
pocos, que tienen funciones especificas, que trataremos mas adelante):
---
La funcion findAllX (donde X es el tipo de objeto que devuelve en JSON) 
hace una llamada al servicio correspondiente y el resultado (una lista de X) lo pasa a string, por lo que 
la lista llama al toString de cada elemento. Metodo el cual tienen modificados los DTO para que al llamarse, se 
devuelvan a si mismos en JSON.

```kotlin 
suspend fun findAllUsers(): String {
    [...]
}
```
---
La funcion findXById(id) hace una llamada al servicio correspondiente 
y el resultado (X?) lo pasa a JSON si existe, y si devuelve un nulo devuelve un 
string con un mensaje de error diciendo que no se pudo encontrar.

Esta forma de gestionar los errores será muy usada a lo largo de esta practica, ya que asi nos 
evitamos tirar excepciones y hacer try catch por un tubo. Simplemente hacemos que las cosas 
devuelvan nulo si hay un error y al final si recibimos un nulo ponemos un mensaje de error pero sin 
tirar excepcion y por tanto sin tirar abajo el programa. Las unicas excepciones que podrian saltar estan en 
zonas que ni siquiera deberian ser accesibles mediante el uso de los menus (semi)implementados.
```kotlin
suspend fun getUserById(id: UUID): String {
    [...]
}
```
---
La funcion insertX(dto) hace una llamada de creacion al servicio correspondiente y 
el resultado (Xdto) lo pasa a JSON
```kotlin
suspend fun insertUser(dto: UserDTO): String {
    [...]
}
```
---
La funcion deleteX(dto) hace una llamada de borrado al servicio correspondiente y 
si el resultado es true(que significa que se ha borrado exitosamente
), devuelve el dto pasado por parametro en JSON. Si devuelve false, returnea el mensaje de 
error de que no se ha podido borrar X .
```kotlin
suspend fun deleteUser(dto: UserDTO): String {
    [...]
}
```
---
#### CASOS ESPECIALES: 

Las funciones getXbyY(campo: T) funcionan todas de la misma manera, excepto para user, porque en UserRepository hicimos 
consultas personalizadas (innecesarias, ya que todo se puede hacer con las operaciones CRUD y encima las 
consultas personalizadas son mas costosas para la BD, pero queriamos ponerlas en la práctica para practicarlas).

Generalmente, funcionan haciendo un findAllX y luego filtrandolo por Y; de esta manera 
obtenemos una lista filtrada por lo que queramos, que pasamos finalmente a toString y eso es lo que devolvemos.

Para el caso particular de Users, simplemente llamamos a las correspondientes funciones del servicio y lo que devuelven (UserDTO?) 
lo pasamos a JSON o si nos devolvio un nulo devolvemos un string de mensaje de error.

En el caso de Users tambien están getUserBy[Email/Phone]ForLogin: estas funciones 
hacen lo mismo de llamar al servicio pero devuelven directamente lo que devuelve el servicio, sin pasar a JSON. Estas funciones, como 
su nombre indica, existen unicamente para la clase Login, que necesita el objeto, no el JSON, pero no queriamos que accediese directamente a los servicios ni 
mucho menos a los repositorios, asique hicimos que los controladores se encargaran de ello.
```kotlin
suspend fun getUserByEmail(email: String): String {
    [...]
}
```
--- 

</details>

---

<details>
  <summary>DB</summary>

### Data.kt 

Esta clase tiene los datos iniciales de la base de datos, asi como las funciones necesarias para inicializarlos.

### HibernateManager

El controlador de la base de datos. Puede abrir y cerrar conexiones con la BD, asi como iniciar y commitear 
transacciones en la misma. Todo ello gracias a los metodos open(), close(), 
query() y transaction()

</details>

---

<details>
  <summary>DTO</summary>

Son las clases POKO con las que trabajará el programa fuera de los repositorios y la BD. 
Tienen los metodos fromJSON y toJSON (que el nombre los explica solos) y tienen un toString overrideado para que 
las devuelva como JSON al llamarlo, usando la libreria de Gson (que honestamente, no sabemos por que las listas no las pasa tambien a JSON cuando deberia hacerlo, pero 
bueno).
Algunas tienen la etiqueta @Expose porque en los metodos que las pasan a JSON hemos puesto un
excludeFieldsWithoutExposeAnnotation. Esto está asi porque las fechas daban problemas, asique para algunas clases 
hemos creado unas fechasString que se crean automaticamente al instanciar el objeto en base a las fechas que tengan. Estas son las que se mostraran en el JSON.

Todos los campos que tengan que ser calcuados automaticamente (como precios) se hacen en el constructor de los DTOs automaticamente. Asi no hay riesgo 
de incongruencias.
</details>

---

<details>
  <summary>Exceptions</summary>

### MapperException

Esta clase hereda de Exception pero con el mensaje de "Error en el mapper."

No tiene nada mas porque una piedra tiene más imaginacion que nosotros.
</details>

---

<details>
  <summary>Login</summary>

Este archivo consta de dos funciones: login() y register(), y ambas devuelven un UserDTO.

Ambas funciones son usadas en el principio del programa para, como su nombre indica, loguearse o registrarse.

Al loguearte, introduces tu email y contraseña, y el programa lanza una consulta asincronamente buscando un usuario 
con ese email. Si no encuentra el usuario, o la contraseña introducida (una vez encriptada) no coincide con la del 
usuario recuperado, le dice al cliente que las credenciales son incorrectas, sin especificar si el fallo esta en la contraseña o el email 
para evitar vulnerabilidades, y le dice si quiere salir del programa o continuar intentando loguearse. Si el usuario decide salir, hace un 
exitProcess(0), y de querer continuar, vuelve a pedir credenciales desde el principio.

Si te logueas como administrador porque introduces bien la contraseña y correo del admin, tendras acceso a un menu distinto del de los trabajadores y usuarios.

Al registrarte, introduces tus datos (nombre, apellido, numero de telefono, email, contraseña, repetir contraseña) 
y el programa lanza una consulta para comprobar si ya hay un usuario con el mismo email o numero de telefono. Si no lo hay, se crea un nuevo usuario en la base de datos CON EL ROL DE CLIENTE 
y devuelve el usuario creado. Si coincide el email o el telefono con el de otro usuario, o la contraseña no coincide con la contraseña repetida, te dice que los parametros son incorrectos (sin decir si lo incorrecto es el email o el numero de telefono) y te pregunta si 
quieres salir del programa o volver a intentarlo. Si le das a salir hace un exitProcess(0)
</details>

---

<details>
  <summary>Mappers</summary>

Estas clases se encargan de pasar de DTO a modelo y de modelo a DTO, haciendo las alteraciones necesarias, como cifrar las contraseñas de los usuarios o, en el caso especifico de los pedidos, 
a la hora de pasar de pedido a PedidoDTO, como este ultimo tiene una lista de tareas y turnos, el Mapper llama a los repositorios de tareas y turnos, hace un findAll de ambos y 
filtra las listas resultantes para quedarse solo con aquellas tareas y turnos que pertenezcan a ese pedido. Luego 
le pasa esas listas al constructor del DTO para crearlo. Esto esta hecho asi porque en la base de datos (y por tanto 
en el modelo), el pedido no tiene listas de nada, porque no queriamos que estas relaciones fuesen bidireccionales.
</details>

---

<details>
  <summary>Menu</summary>

Como es lo que menos tiene que ver con Acceso a Datos, será lo que menos expliquemos, que tampoco es plan de hacer esto innecesariamente largo (sobretodo 
tambien porque no esta implementado del todo, ya que no teniamos mas tiempo).

Basicamente, esta carpeta entera se encarga de proporcionarle al usuario una "interfaz" con la que interactuar con la aplicacion por consola.
</details>

---

<details>
  <summary>Models</summary>

Los modelos son clases POKO de lo que va a ser guardado en la base de datos.

Todos ellos constan de las etiquetas @Entity (para que tengan su respectiva tabla en la BD), 
@Table (para renombrar dicha tabla) y una @NamedQuery que es para el findAll.

Para sus atributos, el id esta marcado como @Id (para indicar que es 
el id de la entidad), @GenericGenerator(...) (para que hibernate sepa que es un uuid, si no daba problemas), 
@Column (para renombrar campos) y @Type(para indicarle que es tipo "uuid-char") (si no daba problemas).

Los modelos que presentan herencia (maquinas y tareas) tienen a la clase padre con la anotacion adicional 
@Inheritance(strategy = "InheritanceType.JOINED") porque queriamos que la base de datos 
tuviera una tabla por cada clase, y gracias a JOINED la base de datos creará una tabla para la clase padre y 
otra tabla para cada una de las hijas (sin ponerle a esas tablas los campos de la padre, simplemente referenciara a la 
clase padre). Asi nos evitamos tener una supertabla Tarea/Maquina con millones de campos nulos y nos evitamos tambien tener 
tablas con campos duplicados. 

Los campos que expresan una relacion muchos a uno (ManyToOne) tienen la etiqueta @ManyToOne seguida de @JoinColumn, 
donde entre parentesis se le expresa el nombre que tendra el campo en esta tabla, el nombre del campo al que referencia, y si 
admite nulos o no. Gracias a estas etiquetas podemos hacer las relaciones del programa. No hemos usado ni OneToMany, ni OneToOne, ni ManyToMany 
porque no hemos necesitado de ellas (y no queremos bidireccionalidad).

Los campos que fuesen fechas los hemos puesto con las etiquietas @Type(type = "org.hibernate.type.LocalDateTimeType")
y @CreationTimestamp porque si no daba problemas.

</details>

---
<details>
  <summary>Models exposed</summary>
Los modelos son clases POKO a secas, no tienen ningúna diferencia a un POKO normal, no utilizan anotaciones y solamente constan de un constructor utilizado en diferentes partes del programa.
</details>
---

<details>
  <summary>Entities</summary>

Las entidades son las clases dedicadas a crear las bases de datos y donde casan los valores con todos los parámetros de la base de datos para rellenar los valores necesarios

---

### Tabla 

Crea la tabla en la base de datos utilizando un id que se le pase extendiendo de una clase ya creada por Exposed:

```kotlin
object Table: UUIDTable("Nombre Tabla") {
  val atributo = reference("", Tabla con la relación)
  val número = integer("número")
}
```

---

### Dao

Setea el resultado a nulo, inicia transaccion, busca por ID, cierra transaccion y devuelve el resultado.
```kotlin
class Dao(id: EntityID<UUID>): UUIDEntity(id) {
  companion object: UUIDEntityClass<Dao>(Table)

  var atributo by Dao referencedOn Table.atributo
  var número by Table.número
}
```

---
</details>

<details>
  <summary>Repositories</summary>

Se encargan de hacer las operaciones CRUD (y en el caso de Users, un par mas) necesarias para que el programa funcione. 

Implementan la interfaz ICRUDRepository que les obliga a tener al menos un readAll, findById, 
create y delete.

---

### Read All 

Primero crea una lista mutable vacia del objeto que queramos devolver, luego abre una transaccion y 
como query hace una TypedQuery<T> llamando al metodo createNamedQuery del manager del controlador de la BD, y 
a ese metodo se le introduce por parametro el nombre de la namedQuery descrita en el modelo, asi como la clase del modelo 
a la que pertenece dicha NamedQuery.

Luego la lista se actualiza al resultList de la query, se finaliza la transaccion y se devuelve la lista.
```kotlin
override suspend fun readAll(): List<T> = withContext(Dispatchers.IO) {
    var result = mutableListOf<T>()
    HibernateManager.transaction {
        val query: TypedQuery<T> = HibernateManager.manager.createNamedQuery("T.findAll", T::class.java)
        result = query.resultList
    }
    result
}
```
Nota: * Donde en el codigo pone T no se refiere a un generico, sino al tipo que sea el repositorio, por ejemplo: Adquisicion.

---

### Find by ID

Setea el resultado a nulo, inicia transaccion, busca por ID, cierra transaccion y devuelve el resultado.
```kotlin
override suspend fun findById(id: UUID): T? = withContext(Dispatchers.IO) {
    var result: T? = null
    HibernateManager.transaction {
        result = HibernateManager.manager.find(T::class.java, id)
    }
    result
}
```

---

### Insert

Inicia transaccion, crea o actualiza (dependiendo de si ya existe o no ese objeto en la BD), cierra transaccion y 
devuelve el objeto pasado por parametro.
```kotlin
override suspend fun create(entity: Adquisicion): Adquisicion = withContext(Dispatchers.IO) {
    HibernateManager.transaction {
        HibernateManager.manager.merge(entity)
    }
    entity
}
```

---

### Delete
Setea el resultado a falso, inicia transaccion, busca por id la entidad pasada por parametro, si la encuentra 
la borra y setea el resultado a true, si no no hace nada, cierra transaccion y devuelve el resultado.

Es importante que el remove lo haga sobre el objeto recuperado por el findById y no sobre el pasado por parametro, ya que 
el objeto requiere de estar en la misma sesion para poder ser borrado.
```kotlin
override suspend fun delete(entity: Adquisicion): Boolean = withContext(Dispatchers.IO) {
    var result = false
    HibernateManager.transaction {
        val adquisicion = HibernateManager.manager.find(Adquisicion::class.java, entity.id)
        adquisicion?.let {
            HibernateManager.manager.remove(it)
            result = true
        }
    }
    result
}
```

--- 

## Casos especiales de User 

### Find by Email/Phone/Perfil 

Crea una lista mutable de usuarios, inicia la transaccion, crea una query en la que busca por el campo X, 
le setea a la query como parametro el que queremos y actualiza la lista mutable al resultList de la query; 
Cierra la transaccion y devuelve la lista.

```kotlin
suspend fun findByPerfil(profile: Profile): List<User> = withContext(Dispatchers.IO) {
    var result = mutableListOf<User>()
    HibernateManager.transaction {
        val query: TypedQuery<User> = manager.createQuery(
            "select u from User u where u.perfil = :profile", User::class.java)
        query.setParameter("profile", profile)
        result = query.resultList
    }
    result
}
```

</details>

---

<details>
  <summary>Services</summary>

Los servicios tienen un repositorio inyectado por constructor y, gracias a este y al mapper, 
se encargara de llamar al repositorio para que haga las operaciones CRUD necesarias pero lo que 
dicho repositorio devuelva, el servicio lo transformara en DTO gracias al mapper y devolvera eso. 

En esencia, para los create y delete recibe un DTO y devuelve un DTO, haciendo un insert/delete 
del DTO mapeado a Modelo, y el resultado lo pasará de Modelo de vuelta a DTO, devolviendo el susodicho DTO.
Para findAll y findById simplemente el Modelo o lista de modelos los pasa a DTO o lista de DTOs y los devuelve.

Los servicios son simples porque queriamos que el bulto de la logica de negocio estuviera en los controladores.

Todos extienden de la clase abstracta BaseService, que tiene ya hechos los metodos crud pero sin pasar a DTO.

</details>

---

<details>
  <summary>Util</summary>

Un archivo con funciones de utilidad varias:
- Encode codifica un string dado a SHA-512 y devuelve el string codificado.
- BetweenXandY coge un string pasado por parametro y lo intenta convertir a Int. Si no 
puede, da un nulo (y si da un nulo returnea false) y si puede y ese numero se encuentra entre X e Y (inclusive) 
returnea true. Returnea false si el numero pasado a int no está entre esos valores inclusive.
- waitingText recibe por parametro un Deferred y printea un puntito cada 0.1s en grupos de tres puntitos
hasta que ese deferred sea completado.

</details>

### TEST

<details>
  <summary>Controllers</summary>

Clases para testar unitariamente los controladores usando MockK (y JUnit). 
</details>

---

<details>
  <summary>Repositories</summary>

Clases para testear (unitariamente, aunque tecnicamente seria 
de integracion porque por debajo usa hibernate, pero se asume que hibernate esta 
completamente testeado) los repositorios usando JUnit.
</details>
