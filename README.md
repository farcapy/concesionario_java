
# Proyecto de Concesionario de Vehículos

El presente proyecto fue planteado en el 3er año de la carrera de Ingeniería en Informática de la Universidad Columbia del Paraguay, en las materias de Base de Datos 1 (BD1) y Programación 1(PR1), ambas, impartidas por el profesor Charles Cabrera.

Dicho proyecto, es una aplicación básica de gestión para concesionarios de vehículos desarrollada en Java. Permite a los usuarios agregar, editar y gestionar información sobre vehículos en un concesionario, así como realizar búsquedas y manejar inventarios. La aplicación está integrada con una base de datos MySQL para almacenar la información de los vehículos y utiliza una interfaz gráfica (GUI) para interactuar con el usuario.

## Funcionalidades
- **Gestión de Vehículos:** Agregar, editar, y eliminar vehículos.
- **Búsqueda y Filtrado:** Buscar vehículos por marca, modelo, año, y otros criterios.
- **Gestión de Inventario:** Visualizar y actualizar el inventario de vehículos.
- **Interfaz Gráfica:** Interfaz de usuario intuitiva para facilitar la interacción.
- **Autocompletado:** Sugerencias automáticas en los campos de texto para marcas de vehículos.
- **Validación de Datos:** Asegurarse de que los datos ingresados cumplen con el formato requerido (como las matrículas en formato MERCOSUR Paraguay).
## Requisitos del sistema
- Java Development Kit (JDK) 8 o superior
- MySQL Server 8.4.0 (Versiones superiores en pruebas)
## Instalación
**1. Clonar el repositorio:**

*git clone https://github.com/fredyarca15/concesionario_java*

*cd proyecto-concesionario*

**2. Configuración de la base de datos:**

- Crear una base de datos en MySQL llamada concesionario_test o similar.
- Importar el archivo concesionario_sch.sql que se encuentra en la carpeta database para crear las tablas necesarias.
- Configurar las credenciales de la base de datos en el archivo config.properties o directamente en el código.

**3. Compilar y ejecutar el proyecto:**

- Si usas NetBeans:
  - Abrir el proyecto en NetBeans.
  - Ejecutar el proyecto desde el menú Run.
- Si usas la línea de comandos:
  - Compilar el proyecto usando Maven o directamente con javac.
  - Ejecutar el archivo .jar generado o los .class.
## Uso
**1. Inicio de sesión:**
- Una vez que inicies la aplicación, se te pedirá iniciar sesión (si implementaste autenticación).

**2. Agregar un nuevo vehículo:**

- Haz clic en el botón "Nuevo" y rellena los campos obligatorios.
- Haz clic en "Guardar" para almacenar el vehículo en la base de datos.
**3. Editar un vehículo existente:**
- Selecciona un vehículo de la lista.
- Haz clic en "Editar" para modificar los datos del vehículo.
- Haz clic en "Guardar" para actualizar los cambios.

**4. Eliminar un vehículo:**
- Selecciona un vehículo de la lista.
- Haz clic en "Eliminar" para removerlo del inventario.
## Estructura del proyecto
- src/: Contiene el código fuente de la aplicación.
  - gui/: Paquete para la interfaz gráfica de usuario.
  - dao/: Paquete para la interacción con la base de datos (Data Access Objects).
  - model/: Paquete que contiene las clases modelo (e.g., Vehiculo, Marca).
- database/: Archivos SQL para la creación y poblamiento de la base de datos.
- README.md: Este archivo.
- config.properties: Archivo de configuración para las credenciales de la base de datos.
## Contacto
Si tienes alguna pregunta o sugerencia, puedes contactar a través de:
- Email: fredyarcawpy@gmail.com
