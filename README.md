Menú de Retos JavaFX
Integrantes
Caleb — Desarrollo e integración del módulo Reto 3: Tienda de Artesanías, incluyendo el modelo, DAO, controlador, interfaz FXML, menú, barra de herramientas y conexión con el menú principal.

Rafael — Desarrollo de los módulos de Inventario de Pulpería y Recepción de Café, además de componentes generales del proyecto.

Cada integrante trabajó desde su propia rama de Git y realizó commits con cambios funcionales antes de integrar sus aportes a la rama main.

Descripción del proyecto
Aplicación de escritorio desarrollada con JavaFX que reúne tres retos académicos en un menú principal. La aplicación se inicia desde la clase Launcher, la cual carga el menú principal Menú de Retos JavaFX. Desde esta ventana, el usuario puede seleccionar y abrir cada módulo.

La aplicación contiene los siguientes módulos:

Reto 1 - Inventario de Pulpería

Gestión de productos de inventario.

Reto 2 - Recepción de Café

Registro de lotes de café.

Validación de campos.

Visualización de lotes en una tabla.

Consulta de detalles mediante selección y opciones de edición o eliminación.

Reto 3 - Tienda de Artesanías

Registro de artesanías con nombre, categoría, precio, stock e imagen.

Menú con las opciones Catálogo, Ventas y Ayuda.

Barra de herramientas (ToolBar) con acciones de Nuevo, Guardar y Buscar.

Tabla (TableView) para mostrar imagen, nombre, categoría, precio y stock de las artesanías.

Búsqueda por nombre o categoría.

Registro de ventas, reduciendo el stock del producto seleccionado.

Tecnologías utilizadas
Java 21

JavaFX 21

Maven

FXML

IntelliJ IDEA

Git y GitHub

Requisitos previos
Antes de ejecutar el proyecto, se recomienda tener instalado:

JDK 21.

IntelliJ IDEA.

Maven configurado en IntelliJ IDEA o incluido mediante el proyecto Maven.

Conexión a Internet la primera vez que se descarguen las dependencias de JavaFX.

Instrucciones de ejecución
Clone el repositorio:

bash
git clone <URL_DEL_REPOSITORIO>
Abra la carpeta del proyecto en IntelliJ IDEA.

Espere a que IntelliJ reconozca el archivo pom.xml como proyecto Maven.

Si las dependencias no se cargan automáticamente, haga clic derecho sobre pom.xml y seleccione:

text
Maven > Reload Project
Verifique que el SDK del proyecto sea JDK 21:

text
File > Project Structure > Project > SDK
Ejecute la clase principal:

text
org.example.javafxmenuretos.Launcher
La clase Launcher iniciará la aplicación y mostrará el Menú de Retos JavaFX.

En el menú principal, seleccione uno de los tres retos:

Reto 1 - Inventario de Pulpería.

Reto 2 - Recepción de Café.

Reto 3 - Tienda de Artesanías.

Uso del módulo de Artesanías
Abra Reto 3 - Tienda de Artesanías desde el menú principal.

Para registrar un producto, complete los campos:

Nombre.

Categoría.

Precio.

Stock.

Nombre de imagen, si corresponde.

Presione Guardar en la barra de herramientas.

Para limpiar el formulario, presione Nuevo.

Para buscar un producto, escriba el nombre o categoría en el campo de búsqueda y presione Buscar.

Para registrar una venta, seleccione una artesanía en la tabla y use:

text
Ventas > Registrar venta
Para conocer las instrucciones del módulo, seleccione:

text
Ayuda > Cómo usar el sistema
Imágenes de artesanías
Las imágenes utilizadas en el módulo de artesanías deben colocarse en:

text
src/main/resources/org.example.javafxmenuretos/
En el campo Nombre de imagen, escriba el nombre exacto del archivo, por ejemplo:

text
jarra.png
Organización del proyecto
text
src/
└── main/
    ├── java/
    │   └── org/example/javafxmenuretos/
    │       ├── Launcher.java
    │       ├── MenuApplication.java
    │       ├── MenuController.java
    │       ├── Artesania.java
    │       ├── ArtesaniasApplication.java
    │       ├── ArtesaniasController.java
    │       ├── CafeController.java
    │       ├── InventarioController.java
    │       └── dao/
    │           └── ArtesaniaDAO.java
    └── resources/
        └── org/example/javafxmenuretos/
            ├── menu-view.fxml
            ├── inventario-view.fxml
            ├── cafe-view.fxml
            ├── artesanias-view.fxml
            └── uam_logo.png
Control de versiones
El proyecto se desarrolló de forma colaborativa mediante Git y GitHub:

Cada integrante creó y utilizó una rama personal.

Los cambios funcionales se registraron mediante commits.

Las ramas se subieron al repositorio remoto.

Los cambios fueron revisados mediante Pull Requests.

Finalmente, los aportes se integraron en la rama main.
