Documentación de Microservicio de Usuarios
### 1. **Documentación de la Capa de Entidades y DTOs**
#### **Entidades**
La capa de entidades contiene las clases mapeadas a las tablas de la base de datos y define los datos persistentes que representan los objetos de negocio. Las entidades principales son `UserEntity`, `ProjectEntity`, `RoleEntity`, `TokenEntity`, `PermissionEntity`, y `UserVerification`. Estas clases se relacionan entre sí para gestionar la información de usuarios, proyectos, roles, permisos, y verificación de usuarios.
- **UserEntity**: Representa a un usuario en el sistema. Tiene propiedades como `idUsuario`, `email`, `password`, y booleans de control (`isEnabled`, `accountNoExpired`, etc.). Cada usuario puede tener uno o más roles (`roles`) y pertenecer a varios proyectos (`projects`).
- **ProjectEntity**: Representa un proyecto y contiene atributos como `idProyecto`, `nombre`, `uuid`, `fechaCreacion`, y `customEmail`. Un proyecto puede tener varios usuarios (`usuarios`).
- **RoleEntity**: Define los roles que un usuario puede tener, con un campo `nombre` que soporta valores `ADMIN` y `USER`.
- **TokenEntity**: Esta entidad gestiona los tokens de autorización con atributos como `status` y `token`. Cada token se asocia con un proyecto (`proyecto`).
- **PermissionEntity**: Almacena los permisos que pueden asignarse a los roles (`rolesPermisos`) y tiene un campo `name`.
- **UserVerification**: Se usa para la verificación de usuarios, con campos como `idUserVerification`, `code`, y `creationDate`.
#### **DTOs (Data Transfer Objects)**
Los DTOs facilitan la transferencia de datos entre la capa de servicio y el cliente, encapsulando solo la información necesaria y evitando el uso directo de entidades.
- **ProjectAndUsersDTO**: Transfiere datos de proyectos junto con su lista de usuarios. Incluye `idProyecto`, `nombre`, `uuid`, `fechaCreacion`, `customEmail`, y `usuarios`.
- **ProjectDTO**: Similar a `ProjectAndUsersDTO` pero excluye la lista de usuarios.
- **TokenRequestDTO**: Encapsula las entidades `UserEntity`, `ProjectEntity`, y `TokenEntity` para la creación y verificación de tokens.
- **UserDetailDto**: Proporciona detalles de usuario como `idUsuario`, `email`, `password`, y campos booleanos de control, junto con el rol del usuario.
- **UserEntityDTO**: DTO simple que contiene `idUsuario` y `email`.
- **UserLoginRequest**: Encapsula la información de inicio de sesión (`email` y `password`).
- **UserVerificationDTO**: Usado para la verificación de usuarios, con `idUserVerification`, `code`, y `user`.
---
### 2. **Documentación de la Capa de Servicio**
La capa de servicio gestiona la lógica de negocio de la aplicación. Utiliza las entidades y DTOs para realizar operaciones específicas de negocio y para interactuar con los repositorios. A continuación, se describen los servicios principales y sus responsabilidades.
#### **Servicios**
1. **ProjectService**: Gestiona las operaciones relacionadas con los proyectos, como la creación, consulta y eliminación de proyectos.
    - **Métodos**:
        - `saveProject(ProjectEntity projectEntity, Integer idUsuario)`: Guarda un nuevo proyecto en la base de datos y asocia un usuario al proyecto. Genera un `UUID` único para el proyecto y establece la `fechaCreacion`.
        - `projectAndUsers(Integer idProject)`: Busca un proyecto por su `idProject` y devuelve los datos junto con la lista de usuarios asociados en un `ProjectAndUsersDTO`.
        - `findProjectEntityByIdProjectEntity(Integer idProject)`: Recupera una entidad de proyecto a partir de su ID.
        - `deleteProjectById(Integer idProject)`: Elimina un proyecto de la base de datos por su ID.
2. **TokenService**: Gestiona la creación, búsqueda y eliminación de tokens de autorización para los proyectos.
    - **Métodos**:
        - `createToken(Integer idProject)`: Crea un nuevo token de acceso para un proyecto específico. Asocia el token generado al proyecto y lo guarda en la base de datos.
        - `deleteLogically(TokenEntity tokenEntity)`: Marca un token con el estado "delete" para eliminarlo lógicamente sin eliminarlo físicamente.
        - `deletePhysically(Integer idToken)`: Elimina un token de la base de datos de forma permanente usando su ID.
        - `findByToken(String token)`: Busca y retorna un token específico en la base de datos.
3. **UserDetailServiceImp**: Implementa `UserDetailsService` de Spring Security para cargar la información de los usuarios, necesaria para la autenticación.
    - **Método**:
        - `loadUserByUsername(String username)`: Carga los detalles del usuario a partir de su correo electrónico (`username`). Genera una lista de `SimpleGrantedAuthority` para los roles y permisos asociados al usuario, permitiendo que estos datos se utilicen en el contexto de seguridad.
4. **UserService**: Gestiona las operaciones relacionadas con los usuarios, como la creación y recuperación de datos de usuarios.
    - **Métodos**:
        - `saveUser(UserEntity userEntity)`: Guarda un nuevo usuario en la base de datos con contraseñas encriptadas y establece valores por defecto para los campos booleanos. Asigna el rol `USER` al usuario.
        - `findUserById(Integer id)`: Recupera un usuario por su ID.
        - `findAllUsers()`: Retorna una lista de todos los usuarios en la base de datos.
        - `findUserByEmail(String email)`: Recupera un usuario por su correo electrónico.
5. **VerificationService**: Gestiona la verificación de usuarios a través de códigos generados y almacenados temporalmente.
    - **Métodos**:
        - `createVerification(UserEntity user)`: Crea un nuevo código de verificación para un usuario, guarda los detalles en la base de datos y retorna un `UserVerificationDTO`.
        - `validate(int code, UserVerification userVerification)`: Valida un código de verificación proporcionado y, si es correcto, elimina la entrada de verificación.
        - `findUserVerification(Integer code)`: Busca una verificación de usuario usando el código.
---
### 3. **Documentación de la Capa de Base de Datos**
La capa de base de datos define la estructura de la información persistente, incluyendo las tablas y las relaciones entre ellas. A continuación, se describen las tablas principales, sus campos y las relaciones establecidas en la base de datos para esta aplicación.
#### **Tablas y Relaciones**
1. **Tabla `permisos`**: Define los permisos individuales que pueden asignarse a roles.
    - **Campos**:
        - `id_permiso`: ID único generado automáticamente.
        - `name`: Nombre del permiso, debe ser único.
    - **Restricciones**:
        - La columna `name` debe tener valores únicos.
2. **Tabla `proyectos`**: Almacena la información básica de los proyectos.
    - **Campos**:
        - `id_proyecto`: ID único generado automáticamente.
        - `fecha_creacion`: Marca la fecha y hora de creación del proyecto.
        - `uuid`: Identificador único para cada proyecto.
        - `custom_email`: Correo electrónico personalizado asociado al proyecto.
        - `nombre`: Nombre del proyecto.
        - `type_of_plan`: Tipo de plan del proyecto.
3. **Tabla `proyectos_usuarios`**: Representa la relación muchos a muchos entre `proyectos` y `usuarios`, indicando los usuarios asignados a cada proyecto.
    - **Campos**:
        - `id_proyecto`: ID del proyecto (relación con la tabla `proyectos`).
        - `id_usuario`: ID del usuario (relación con la tabla `usuarios`).
    - **Restricciones**:
        - Llave primaria compuesta por `id_proyecto` y `id_usuario`.
        - Clave foránea `id_usuario` referenciada en la tabla `usuarios`.
        - Clave foránea `id_proyecto` referenciada en la tabla `proyectos`.
4. **Tabla `roles`**: Define los roles posibles en la aplicación, con una restricción que limita los valores posibles de rol.
    - **Campos**:
        - `id_rol`: ID único generado automáticamente.
        - `nombre`: Nombre del rol (solo puede tener los valores `'ADMIN'` o `'USER'`).
    - **Restricciones**:
        - Restricción `check` para asegurar que `nombre` solo tome los valores `'ADMIN'` o `'USER'`.
5. **Tabla `roles_permisos`**: Representa la relación muchos a muchos entre `roles` y `permisos`, permitiendo asignar múltiples permisos a cada rol.
    - **Campos**:
        - `id_permiso`: ID del permiso (relación con la tabla `permisos`).
        - `id_rol`: ID del rol (relación con la tabla `roles`).
    - **Restricciones**:
        - Llave primaria compuesta por `id_permiso` y `id_rol`.
        - Clave foránea `id_permiso` referenciada en la tabla `permisos`.
        - Clave foránea `id_rol` referenciada en la tabla `roles`.
6. **Tabla `tokens`**: Almacena los tokens de autorización para cada proyecto.
    - **Campos**:
        - `id_proyecto`: ID del proyecto (relación con la tabla `proyectos`).
        - `id_token`: ID único del token, generado automáticamente.
        - `status`: Estado del token (debe ser único).
        - `token`: El valor del token, único y necesario para la autenticación.
    - **Restricciones**:
        - La columna `status` y `token` deben ser únicos.
        - Clave foránea `id_proyecto` referenciada en la tabla `proyectos`.
7. **Tabla `usuario_roles`**: Representa la relación muchos a muchos entre `usuarios` y `roles`, indicando los roles asignados a cada usuario.
    - **Campos**:
        - `id_rol`: ID del rol (relación con la tabla `roles`).
        - `id_usuario`: ID del usuario (relación con la tabla `usuarios`).
    - **Restricciones**:
        - Llave primaria compuesta por `id_rol` y `id_usuario`.
        - Clave foránea `id_rol` referenciada en la tabla `roles`.
        - Clave foránea `id_usuario` referenciada en la tabla `usuarios`.
8. **Tabla `usuarios`**: Almacena la información básica y de seguridad de cada usuario.
    - **Campos**:
        - `account_no_expired`: Booleano que indica si la cuenta del usuario ha expirado.
        - `account_no_locket`: Booleano que indica si la cuenta está bloqueada.
        - `credential_no_expired`: Booleano que indica si las credenciales han expirado.
        - `id_usuario`: ID único del usuario, generado automáticamente.
        - `is_enabled`: Booleano que indica si el usuario está habilitado.
        - `email`: Dirección de correo electrónico, debe ser única.
        - `password`: Contraseña encriptada del usuario.
    - **Restricciones**:
        - La columna `email` debe ser única.
9. **Tabla `verificacion`**: Almacena la información de verificación de cada usuario.
    - **Campos**:
        - `attempts`: Número de intentos realizados por el usuario para verificar su cuenta.
        - `code`: Código de verificación del usuario.
        - `id_user`: ID del usuario al que pertenece la verificación (relación con la tabla `usuarios`).
        - `id_user_verification`: ID único de la verificación, generado automáticamente.
        - `creation_date`: Fecha de creación de la verificación.
    - **Restricciones**:
        - La columna `id_user` debe ser única.
        - Clave foránea `id_user` referenciada en la tabla `usuarios`.
---
### 4. **Documentación de la Capa de Configuración**
La capa de configuración gestiona las configuraciones de seguridad, autenticación, autorización y CORS de la aplicación. En este caso, se utiliza `Spring Security` para implementar la autenticación y autorización, y se configura `CORS` para controlar el acceso desde diferentes dominios.
#### **Clase `SecurityConfig`**
La clase `SecurityConfig` define varias configuraciones para la seguridad de la aplicación, utilizando las siguientes anotaciones:
- **@Configuration**: Declara que esta clase es de configuración y provee beans a Spring.
- **@EnableWebSecurity**: Habilita la configuración de seguridad web de Spring.
- **@EnableMethodSecurity**: Permite el uso de anotaciones de seguridad a nivel de método.
#### **Configuraciones de Seguridad**
1. **SecurityFilterChain**: Configura el filtro de seguridad y controla qué endpoints son accesibles o protegidos.
    - Deshabilita CSRF (`csrf.disable()`) para simplificar el manejo de solicitudes, especialmente para aplicaciones REST.
    - Habilita CORS con la configuración por defecto (`cors(withDefaults())`).
    - Define la política de sesión como **STATELESS**, apropiada para aplicaciones REST.
    - Configura rutas de acceso y permisos:
        - `"/projects/**"` y `"/user/**"` están permitidas para todos los usuarios sin autenticación.
        - `http.anyRequest().permitAll()` permite el acceso a otras rutas.
2. **CorsConfigurationSource**: Configura las reglas de `CORS` para permitir acceso desde múltiples orígenes.
    - Permite cualquier origen (`"*"`), lo cual es útil en entornos de desarrollo.
    - Habilita métodos HTTP comunes (`GET`, `POST`, `PUT`, `DELETE`, `OPTIONS`).
    - Configura encabezados permitidos (`"*"`), permitiendo cualquier encabezado en las solicitudes.
    - Habilita el envío de credenciales si es necesario.
3. **AuthenticationManager**: Este bean facilita la autenticación en la aplicación utilizando la configuración definida en `AuthenticationConfiguration`.
4. **AuthenticationProvider**: Configura el proveedor de autenticación para que use `UserDetailsService` y `BCryptPasswordEncoder` como cifrador de contraseñas, asegurando que se utilice una capa de seguridad adecuada.
5. **PasswordEncoder**: Define el cifrador de contraseñas `BCryptPasswordEncoder`, usado para encriptar y verificar contraseñas de manera segura.
Esta configuración permite que los endpoints definidos sean accesibles solo para usuarios autorizados, y los tokens se usan sin necesidad de mantener sesiones en el servidor, ideal para aplicaciones con arquitecturas sin estado.
---
### 5. **Documentación de la Capa de Controladores**
La capa de controladores se encarga de gestionar las peticiones HTTP, procesar los datos y comunicarse con los servicios de la aplicación para responder con los datos necesarios. A continuación, se presentan los principales controladores, sus rutas y sus funcionalidades.
#### **Controlador `ProjectController`**
Este controlador gestiona las peticiones relacionadas con los proyectos, y realiza las operaciones CRUD.
- **Endpoints**:
    - `POST /projects`: Crea un nuevo proyecto.
    - `GET /projects/{idProject}`: Retorna la información de un proyecto y los usuarios asociados.
    - `DELETE /projects/{idProject}`: Elimina un proyecto de forma lógica o física.
- **Métodos**:
    - `saveProject`: Recibe un `ProjectEntity` y un `idUsuario` para asociar el proyecto con un usuario y guardarlo en la base de datos.
    - `projectAndUsers`: Recupera la información del proyecto y la lista de usuarios asociados en formato DTO.
    - `deleteProject`: Permite la eliminación lógica o física de un proyecto.
#### **Controlador `UserController`**
Este controlador maneja las solicitudes relacionadas con los usuarios.
- **Endpoints**:
    - `POST /user`: Crea un nuevo usuario.
    - `GET /user/{idUsuario}`: Obtiene los datos de un usuario por ID.
    - `GET /user/all`: Devuelve una lista de todos los usuarios registrados.
- **Métodos**:
    - `saveUser`: Crea un nuevo usuario en la base de datos con sus propiedades de seguridad.
    - `findUserById`: Recupera la información de un usuario específico por su ID.
    - `findAllUsers`: Recupera todos los usuarios registrados en el sistema.
    - `findUserByEmail`: Encuentra un usuario específico por su dirección de correo electrónico.
#### **Controlador `TokenController`**
Este controlador gestiona las peticiones para la generación y manejo de tokens de autorización.
- **Endpoints**:
    - `POST /tokens`: Genera un nuevo token para un proyecto específico.
    - `DELETE /tokens/{idToken}`: Elimina un token por ID de forma lógica o física.
- **Métodos**:
    - `createToken`: Genera y asocia un nuevo token a un proyecto.
    - `deleteLogically`: Cambia el estado del token a "delete" para una eliminación lógica.
    - `deletePhysically`: Elimina completamente el token de la base de datos.
#### **Controlador `VerificationController`**
Este controlador maneja las solicitudes relacionadas con la verificación de usuarios.
- **Endpoints**:
    - `POST /verification`: Crea un nuevo código de verificación para un usuario.
    - `POST /verification/validate`: Valida el código de verificación de un usuario.
- **Métodos**:
    - `createVerification`: Genera un código de verificación de cinco dígitos y lo asocia al usuario.
    - `validate`: Valida el código ingresado por el usuario y elimina la verificación si es correcta.
