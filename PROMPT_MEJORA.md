# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/main/java/com/pragma/banking/transactions/domain/service/TransactionService.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/banking/transactions/application/usecase/ProcessTransactionUseCase.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/banking/transactions/infrastructure/controller/TransactionController.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/banking/transactions/application/usecase/ProcessTransactionUseCase.java` — `TransactionStatus.name`: Se invoca `name` sobre `TransactionStatus`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/TransactionJpaAdapter.java` — `TransactionJpaRepository.save`: Se invoca `save` sobre `TransactionJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/TransactionJpaAdapter.java` — `TransactionJpaRepository.findById`: Se invoca `findById` sobre `TransactionJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/TransactionJpaAdapter.java` — `TransactionJpaRepository.findAll`: Se invoca `findAll` sobre `TransactionJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/TransactionJpaAdapter.java` — `TransactionStatus.name`: Se invoca `name` sobre `TransactionStatus`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/TransactionJpaAdapter.java` — `TransactionJpaRepository.findByTransactionDateBetween`: Se invoca `findByTransactionDateBetween` sobre `TransactionJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/TransactionJpaAdapter.java` — `TransactionJpaRepository.sumAmountByClientIdAndDateRange`: Se invoca `sumAmountByClientIdAndDateRange` sobre `TransactionJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/TransactionJpaAdapter.java` — `TransactionJpaRepository.deleteById`: Se invoca `deleteById` sobre `TransactionJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/TransactionJpaAdapter.java` — `TransactionJpaRepository.existsById`: Se invoca `existsById` sobre `TransactionJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/AuditJpaAdapter.java` — `AuditJpaRepository.save`: Se invoca `save` sobre `AuditJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/AuditJpaAdapter.java` — `AuditJpaRepository.findById`: Se invoca `findById` sobre `AuditJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/AuditJpaAdapter.java` — `AuditJpaRepository.findAll`: Se invoca `findAll` sobre `AuditJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/AuditJpaAdapter.java` — `AuditJpaRepository.findByAction`: Se invoca `findByAction` sobre `AuditJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/AuditJpaAdapter.java` — `AuditJpaRepository.deleteById`: Se invoca `deleteById` sobre `AuditJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/AuditJpaAdapter.java` — `AuditJpaRepository.existsById`: Se invoca `existsById` sobre `AuditJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/banking/transactions/infrastructure/controller/TransactionController.java` — `TransactionRequest.type`: Se invoca `type` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/banking/transactions/infrastructure/controller/TransactionController.java` — `TransactionRequest.amount`: Se invoca `amount` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/banking/transactions/infrastructure/controller/TransactionController.java` — `TransactionRequest.clientId`: Se invoca `clientId` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/banking/transactions/infrastructure/controller/TransactionController.java` — `TransactionRequest.description`: Se invoca `description` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/banking/transactions/infrastructure/controller/TransactionController.java` — `ProcessTransactionUseCase.execute`: Se invoca `execute` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/banking/transactions/infrastructure/controller/TransactionController.java` — `ProcessTransactionUseCase.findById`: Se invoca `findById` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/banking/transactions/infrastructure/controller/TransactionController.java` — `ProcessTransactionUseCase.findByFilters`: Se invoca `findByFilters` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/banking/transactions/infrastructure/controller/TransactionController.java` — `ProcessTransactionUseCase.cancelTransaction`: Se invoca `cancelTransaction` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/banking/transactions/infrastructure/controller/TransactionController.java` — `TransactionRequest.reason`: Se invoca `reason` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/banking/transactions/infrastructure/controller/TransactionController.java` — `ProcessTransactionUseCase.reverseTransaction`: Se invoca `reverseTransaction` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/banking/transactions/domain/service/TransactionServiceTest.java` — `TransactionService.processTransactionWithAtomicity`: Se invoca `processTransactionWithAtomicity` sobre `TransactionService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/banking/transactions/application/usecase/ProcessTransactionUseCaseTest.java` — `ProcessTransactionUseCase.execute`: Se invoca `execute` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/banking/transactions/application/usecase/ProcessTransactionUseCaseTest.java` — `ProcessTransactionUseCase.executeWithFallback`: Se invoca `executeWithFallback` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/banking/transactions/application/usecase/ProcessTransactionUseCaseTest.java` — `ProcessTransactionUseCase.validateClient`: Se invoca `validateClient` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/banking/transactions/application/usecase/ProcessTransactionUseCaseTest.java` — `ProcessTransactionUseCase.executeWithRetry`: Se invoca `executeWithRetry` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/banking/transactions/application/usecase/ProcessTransactionUseCaseTest.java` — `ProcessTransactionUseCase.isCircuitBreakerClosed`: Se invoca `isCircuitBreakerClosed` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/banking/transactions/application/usecase/ProcessTransactionUseCaseTest.java` — `ProcessTransactionUseCase.isValidTransactionType`: Se invoca `isValidTransactionType` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

## Como saber que terminaste

```bash
mvn clean compile
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Perfil
Chapter Backend, Especialidad Desarrollador, Tecnología Java, Senior L2

### Brecha de conocimiento
Implementa control de transacciones (TCL) en RDBMS y en el framework, aplicando optimizaciones mediante procedimientos almacenados, índices, etc. Asimismo, asegura la aplicación del modelo ACID (Atomicidad, Consistencia, Aislamiento y Durabilidad) en las transacciones., Implementa control de transacciones (TCL) en RDBMS y en el framework, aplicando optimizaciones mediante procedimientos almacenados, índices, etc. Asimismo, asegura la aplicación del modelo ACID (Atomicidad, Consistencia, Aislamiento y Durabilidad) en las transacciones.

### Reto
- Tema: Diseño e Implementación de un Sistema de Transacciones Robusto y Eficiente
- Seniority: senior-l2
- Tipo: practical
- Título: Implementación de Control de Transacciones y Aplicación del Modelo ACID
- Tiempo estimado: 20 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Configuración del Entorno y Creación de la Base de Datos — objetivo: Configurar el entorno de desarrollo y crear la estructura de la base de datos para soportar transacciones. — entregable (NO resolver): Base de datos configurada y tablas creadas.
- Fase 2: Implementación del Control de Transacciones — objetivo: Implementar el control de transacciones en la RDBMS y en el framework, aplicando optimizaciones. — entregable (NO resolver): Control de transacciones implementado y optimizado.
- Fase 3: Aplicación del Modelo ACID — objetivo: Asegurar que todas las transacciones cumplan con el modelo ACID. — entregable (NO resolver): Sistema de transacciones que cumple con el modelo ACID y documentación de decisiones de diseño.
- Fase 4: Evaluación y Optimización — objetivo: Evaluar el rendimiento del sistema y aplicar optimizaciones adicionales si es necesario. — entregable (NO resolver): Informe de evaluación y optimización del sistema de transacciones.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.6</version>
        <relativePath/>
    </parent>

    <groupId>com.pragma</groupId>
    <artifactId>banking-transactions</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>banking-transactions</name>
    <description>Sistema de transacciones bancarias con soporte ACID y manejo de volúmenes altos</description>

    <properties>
        <java.version>21</java.version>
        <resilience4j.version>2.2.0</resilience4j.version>
    </properties>

    <dependencies>
        <!-- Spring Boot Starters -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>

        <!-- Resilience4j -->
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot3</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>

        <!-- Database -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
            <version>42.7.3</version>
        </dependency>
        <dependency>
            <groupId>org.flywaydb</groupId>
            <artifactId>flyway-core</artifactId>
            <version>10.10.0</version>
        </dependency>

        <!-- Swagger/OpenAPI -->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>2.9.1</version>
        </dependency>

        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.34</version>
            <scope>provided</scope>
        </dependency>

        <!-- Test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>5.11.0</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <repositories>
        <repository>
            <id>spring-milestones</id>
            <name>Spring Milestones</name>
            <url>https://repo.spring.io/milestone</url>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </repository>
    </repositories>

    <pluginRepositories>
        <pluginRepository>
            <id>spring-milestones</id>
            <name>Spring Milestones</name>
            <url>https://repo.spring.io/milestone</url>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </pluginRepository>
    </pluginRepositories>
</project>

// === ARCHIVO: src/main/java/com/pragma/banking/transactions/TransactionsApplication.java ===
package com.pragma.banking.transactions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.Duration;

@SpringBootApplication
@EnableConfigurationProperties
@EnableTransactionManagement
@EnableRetry
@EnableAsync
@ComponentScan(basePackages = {"com.pragma.banking.transactions"})
public class TransactionsApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(TransactionsApplication.class, args);
    }
    
    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .permittedNumberOfCallsInHalfOpenState(3)
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(5)
                .recordExceptions(RuntimeException.class)
                .build();
        return CircuitBreakerRegistry.of(config);
    }
    
    @Bean
    public RetryRegistry retryRegistry() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(500))
                .retryExceptions(RuntimeException.class)
                .build();
        return RetryRegistry.of(config);
    }
    
    @Bean
    public CommandLineRunner startupRunner(TransactionService transactionService) {
        return args -> {
            try {
                // Verificación inicial de conectividad con la base de datos
                transactionService.verifyDatabaseConnectivity();
                System.out.println("Database connectivity verified successfully.");
                
                // Carga inicial de datos si es necesario
                transactionService.initializeSampleData();
                System.out.println("Sample data initialized.");
            } catch (Exception e) {
                System.err.println("Error during application startup: " + e.getMessage());
                throw e;
            }
        };
    }
}

interface TransactionService {
    void verifyDatabaseConnectivity();
    void initializeSampleData();
}

// === ARCHIVO: src/main/resources/application.yml ===
spring:
  application:
    name: banking-transactions
  
  datasource:
    url: jdbc:postgresql://localhost:5432/banking_transactions
    username: postgres
    password: postgres
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 20
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1800000
      pool-name: BankingTransactionPool
  
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: validate
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        jdbc:
          batch_size: 50
        order_inserts: true
        order_updates: true
        connection:
          provider_disables_autocommit: true
  
  flyway:
    enabled: true
    locations: classpath:db/migration
    baseline-on-migrate: true
    validate-on-migrate: true
    out-of-order: false
    schemas: public
    table: flyway_schema_history

server:
  port: 8080
  servlet:
    context-path: /api

management:
  endpoints:
    web:
      exposure:
        include: health,metrics,info,circuitbreakers,retryevents
  endpoint:
    health:
      show-details: always
  metrics:
    export:
      prometheus:
        enabled: true

resilience4j:
  circuitbreaker:
    configs:
      default:
        registerHealthIndicator: true
        slidingWindowSize: 10
        minimumNumberOfCalls: 5
        permittedNumberOfCallsInHalfOpenState: 3
        automaticTransitionFromOpenToHalfOpenEnabled: true
        waitDurationInOpenState: 5s
        failureRateThreshold: 50
        eventConsumerBufferSize: 10
        recordExceptions:
          - org.springframework.transaction.TransactionSystemException
          - java.sql.SQLException
          - org.hibernate.exception.JDBCConnectionException
    instances:
      transactionService:
        baseConfig: default
  retry:
    configs:
      default:
        maxAttempts: 3
        waitDuration: 100
        retryExceptions:
          - org.springframework.transaction.TransactionSystemException
          - java.sql.SQLException
          - org.hibernate.exception.JDBCConnectionException
    instances:
      transactionService:
        baseConfig: default

logging:
  level:
    root: INFO
    com.pragma.banking.transactions: DEBUG
    org.hibernate.SQL: DEBUG
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE
    org.springframework.transaction: TRACE
    org.springframework.orm.jpa: DEBUG
    org.flywaydb: DEBUG
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
    file: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
  file:
    name: logs/banking-transactions.log"

// === ARCHIVO: src/main/java/com/pragma/banking/transactions/domain/model/Client.java ===
package com.pragma.banking.transactions.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.UUID;

/**
 * Representa un cliente bancario en el sistema.
 * Contiene información básica y validaciones para garantizar consistencia.
 */
public class Client {
    @NotNull(message = "El ID del cliente no puede ser nulo")
    private final UUID clientId;

    @NotBlank(message = "El nombre del cliente no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private final String name;

    @NotBlank(message = "El número de identificación no puede estar vacío")
    @Pattern(regexp = "^[A-Z0-9]{6,20}$", message = "Formato de identificación inválido")
    private final String identificationNumber;

    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", message = "Formato de correo electrónico inválido")
    private final String email;

    public Client(UUID clientId, String name, String identificationNumber, String email) {
        this.clientId = clientId;
        this.name = name;
        this.identificationNumber = identificationNumber;
        this.email = email;
    }

    public UUID getClientId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Client{clientId=" + clientId +
                ", name='" + name + '\'' +
                ", identificationNumber='" + identificationNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return clientId.equals(client.clientId);
    }

    @Override
    public int hashCode() {
        return clientId.hashCode();
    }
}

// === ARCHIVO: src/main/java/com/pragma/banking/transactions/domain/model/Transaction.java ===
package com.pragma.banking.transactions.domain.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa una transacción financiera en el sistema.
 * Incluye validaciones para garantizar atomicidad y consistencia.
 */
public class Transaction {
    @NotNull(message = "El ID de la transacción no puede ser nulo")
    private final UUID transactionId;

    @NotNull(message = "El ID del cliente no puede ser nulo")
    private final UUID clientId;

    @NotNull(message = "El monto no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor que cero")
    private final BigDecimal amount;

    @NotNull(message = "La fecha de la transacción no puede ser nula")
    @PastOrPresent(message = "La fecha de la transacción no puede ser futura")
    private final LocalDateTime transactionDate;

    @NotNull(message = "El tipo de transacción no puede ser nulo")
    private final TransactionType type;

    @Size(max = 200, message = "La descripción no puede exceder 200 caracteres")
    private final String description;

    @NotNull(message = "El estado de la transacción no puede ser nulo")
    private TransactionStatus status;

    public enum TransactionType {
        DEPOSIT, WITHDRAWAL, TRANSFER, PAYMENT
    }

    public enum TransactionStatus {
        PENDING, COMPLETED, FAILED, REVERSED
    }

    public Transaction(UUID transactionId, UUID clientId, BigDecimal amount, 
                      LocalDateTime transactionDate, TransactionType type, String description) {
        this.transactionId = transactionId;
        this.clientId = clientId;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.type = type;
        this.description = description;
        this.status = TransactionStatus.PENDING;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public UUID getClientId() {
        return clientId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public TransactionType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public boolean isCompleted() {
        return status == TransactionStatus.COMPLETED;
    }

    public boolean isFailed() {
        return status == TransactionStatus.FAILED;
    }

    @Override
    public String toString() {
        return "Transaction{transactionId=" + transactionId +
                ", clientId=" + clientId +
                ", amount=" + amount +
                ", transactionDate=" + transactionDate +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return transactionId.equals(that.transactionId);
    }

    @Override
    public int hashCode() {
        return transactionId.hashCode();
    }
}

// === ARCHIVO: src/main/java/com/pragma/banking/transactions/domain/model/Audit.java ===
package com.pragma.banking.transactions.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa un registro de auditoría en el sistema.
 * Registra eventos importantes para garantizar trazabilidad y cumplimiento.
 */
public class Audit {
    @NotNull(message = "El ID de auditoría no puede ser nulo")
    private final UUID auditId;

    @NotNull(message = "El ID de la transacción no puede ser nulo")
    private final UUID transactionId;

    @NotBlank(message = "La acción no puede estar vacía")
    @Size(max = 50, message = "La acción no puede exceder 50 caracteres")
    private final String action;

    @NotBlank(message = "El usuario no puede estar vacío")
    @Size(max = 50, message = "El usuario no puede exceder 50 caracteres")
    private final String performedBy;

    @NotNull(message = "La marca de tiempo no puede ser nula")
    @PastOrPresent(message = "La marca de tiempo no puede ser futura")
    private final LocalDateTime timestamp;

    @Size(max = 200, message = "Los detalles no pueden exceder 200 caracteres")
    private final String details;

    public Audit(UUID auditId, UUID transactionId, String action, 
                String performedBy, LocalDateTime timestamp, String details) {
        this.auditId = auditId;
        this.transactionId = transactionId;
        this.action = action;
        this.performedBy = performedBy;
        this.timestamp = timestamp;
        this.details = details;
    }

    public UUID getAuditId() {
        return auditId;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public String getAction() {
        return action;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return "Audit{auditId=" + auditId +
                ", transactionId=" + transactionId +
                ", action='" + action + '\'' +
                ", performedBy='" + performedBy + '\'' +
                ", timestamp=" + timestamp +
                ", details='" + details + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Audit audit = (Audit) o;
        return auditId.equals(audit.auditId);
    }

    @Override
    public int hashCode() {
        return auditId.hashCode();
    }
}

// === ARCHIVO: src/main/java/com/pragma/banking/transactions/domain/port/TransactionRepositoryPort.java ===
package com.pragma.banking.transactions.domain.port;

import com.pragma.banking.transactions.domain.model.Transaction;
import com.pragma.banking.transactions.domain.model.Transaction.TransactionStatus;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Puerto que define el contrato para la persistencia de transacciones.
 * Esta interfaz pertenece al dominio y es implementada por la capa de infraestructura.
 * El dominio no conoce detalles de cómo se persiste la información.
 */
public interface TransactionRepositoryPort {

    /**
     * Guarda una nueva transacción en el repositorio.
     * @param transaction la transacción a guardar
     * @return la transacción guardada con su identificador asignado
     */
    Transaction save(Transaction transaction);

    /**
     * Busca una transacción por su identificador único.
     * @param transactionId el identificador de la transacción
     * @return un Optional que contiene la transacción si existe
     */
    Optional<Transaction> findById(UUID transactionId);

    /**
     * Recupera todas las transacciones asociadas a un cliente específico.
     * @param clientId el identificador del cliente
     * @return lista de transacciones del cliente
     */
    List<Transaction> findByClientId(UUID clientId);

    /**
     * Actualiza el estado de una transacción existente.
     * @param transactionId el identificador de la transacción
     * @param status el nuevo estado de la transacción
     * @return la transacción actualizada
     */
    Optional<Transaction> updateStatus(UUID transactionId, TransactionStatus status);

    /**
     * Busca transacciones por estado específico.
     * @param status el estado de las transacciones a buscar
     * @return lista de transacciones con el estado especificado
     */
    List<Transaction> findByStatus(TransactionStatus status);

    /**
     * Calcula el total de transacciones de un cliente dentro de un rango de monto.
     * @param clientId el identificador del cliente
     * @param minAmount monto mínimo del rango
     * @param maxAmount monto máximo del rango
     * @return suma total de los montos de las transacciones
     */
    BigDecimal sumAmountsByClientIdAndAmountBetween(UUID clientId, BigDecimal minAmount, BigDecimal maxAmount);

    /**
     * Verifica si existe una transacción con el mismo identificador externo.
     * @param externalId identificador externo de la transacción
     * @return true si existe una transacción con ese identificador
     */
    boolean existsByExternalId(String externalId);

    /**
     * Elimina una transacción por su identificador.
     * @param transactionId el identificador de la transacción a eliminar
     */
    void deleteById(UUID transactionId);

    /**
     * Cuenta el número total de transacciones en el sistema.
     * @return cantidad total de transacciones
     */
    long count();

    /**
     * Recupera las transacciones más recientes de un cliente.
     * @param clientId el identificador del cliente
     * @param limit número máximo de transacciones a retornar
     * @return lista de las transacciones más recientes
     */
    List<Transaction> findTopByClientIdOrderByTransactionDateDesc(UUID clientId, int limit);
}

// === ARCHIVO: src/main/java/com/pragma/banking/transactions/domain/port/AuditRepositoryPort.java ===
package com.pragma.banking.transactions.domain.port;

import com.pragma.banking.transactions.domain.model.Audit;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Puerto que define el contrato para la persistencia de registros de auditoría.
 * Esta interfaz pertenece al dominio y es implementada por la capa de infraestructura.
 * Permite al dominio mantener un registro inmutable de todas las operaciones realizadas.
 */
public interface AuditRepositoryPort {

    /**
     * Guarda un nuevo registro de auditoría en el repositorio.
     * Los registros de auditoría son inmutables y nunca se modifican.
     * @param audit el registro de auditoría a guardar
     * @return el registro de auditoría guardado con su identificador asignado
     */
    Audit save(Audit audit);

    /**
     * Busca un registro de auditoría por su identificador único.
     * @param auditId el identificador del registro de auditoría
     * @return un Optional que contiene el registro si existe
     */
    Optional<Audit> findById(UUID auditId);

    /**
     * Recupera todos los registros de auditoría asociados a una transacción específica.
     * @param transactionId el identificador de la transacción
     * @return lista de registros de auditoría para la transacción
     */
    List<Audit> findByTransactionId(UUID transactionId);

    /**
     * Busca registros de auditoría por acción específica.
     * @param action la acción registrada (CREATED, UPDATED, DELETED, etc.)
     * @return lista de registros de auditoría con la acción especificada
     */
    List<Audit> findByAction(String action);

    /**
     * Recupera registros de auditoría realizados por un usuario específico.
     * @param performedBy el identificador del usuario que realizó la acción
     * @return lista de registros de auditoría del usuario
     */
    List<Audit> findByPerformedBy(String performedBy);

    /**
     * Busca registros de auditoría dentro de un rango de fechas.
     * @param startDate fecha de inicio del rango
     * @param endDate fecha de fin del rango
     * @return lista de registros de auditoría en el rango de fechas
     */
    List<Audit> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Recupera los registros de auditoría más recientes.
     * @param limit número máximo de registros a retornar
     * @return lista de los registros más recientes
     */
    List<Audit> findTopByOrderByTimestampDesc(int limit);

    /**
     * Cuenta el número de registros de auditoría para una transacción.
     * @param transactionId el identificador de la transacción
     * @return cantidad de registros de auditoría asociados
     */
    long countByTransactionId(UUID transactionId);

    /**
     * Elimina registros de auditoría antiguos basándose en una fecha de corte.
     * Esta operación es útil para políticas de retención de datos.
     * @param cutoffDate fecha de corte para eliminar registros
     * @return número de registros eliminados
     */
    long deleteByTimestampBefore(LocalDateTime cutoffDate);
}

// === ARCHIVO: src/main/java/com/pragma/banking/transactions/domain/service/TransactionService.java ===
package com.pragma.banking.transactions.domain.service;

import com.pragma.banking.transactions.domain.model.Audit;
import com.pragma.banking.transactions.domain.model.Transaction;
import com.pragma.banking.transactions.domain.model.Transaction.TransactionStatus;
import com.pragma.banking.transactions.domain.model.Transaction.TransactionType;
import com.pragma.banking.transactions.domain.port.AuditRepositoryPort;
import com.pragma.banking.transactions.domain.port.TransactionRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Servicio de dominio que gestiona las operaciones de transacciones bancarias.
 * Implementa la lógica de negocio asegurando el cumplimiento del modelo ACID:
 * - Atomicidad: cada operación es atómica, se completa completamente o no se realiza
 * - Consistencia: las transacciones mantienen la integridad de los datos
 * - Aislamiento: las transacciones concurrentes no interfieren entre sí
 * - Durabilidad: una vez completada, la transacción persiste permanentemente
 */
@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    private static final String SYSTEM_USER = "SYSTEM";

    private final TransactionRepositoryPort transactionRepository;
    private final AuditRepositoryPort auditRepository;

    public TransactionService(TransactionRepositoryPort transactionRepository,
                              AuditRepositoryPort auditRepository) {
        this.transactionRepository = transactionRepository;
        this.auditRepository = auditRepository;
    }

    /**
     * Procesa una nueva transacción bancaria aplicando el modelo ACID.
     * La operación se ejecuta dentro de una transacción que asegura atomicidad.
     * @param clientId identificador del cliente que realiza la transacción
     * @param amount monto de la transacción
     * @param type tipo de transacción (DEPOSIT, WITHDRAWAL, TRANSFER)
     * @param description descripción de la transacción
     * @return la transacción procesada
     */
    @Transactional
    public Transaction processTransaction(UUID clientId, BigDecimal amount,
                                          TransactionType type, String description) {
        logger.info("Iniciando procesamiento de transacción para cliente: {}, monto: {}, tipo: {}",
                    clientId, amount, type);

        validateTransactionInput(clientId, amount, type);

        Transaction transaction = new Transaction(
            UUID.randomUUID(),
            clientId,
            amount,
            LocalDateTime.now(),
            type,
            description,
            TransactionStatus.PENDING
        );

        Transaction savedTransaction = transactionRepository.save(transaction);
        logger.info("Transacción guardada con ID: {}", savedTransaction.getTransactionId());

        createAuditRecord(savedTransaction.getTransactionId(), "TRANSACTION_CREATED",
                         SYSTEM_USER, "Transacción creada con estado PENDING");

        try {
            Transaction completedTransaction = executeTransactionLogic(savedTransaction);
            transactionRepository.updateStatus(completedTransaction.getTransactionId(),
                                               TransactionStatus.COMPLETED);

            createAuditRecord(completedTransaction.getTransactionId(), "TRANSACTION_COMPLETED",
                             SYSTEM_USER, "Transacción completada exitosamente");

            logger.info("Transacción {} completada exitosamente",
                       completedTransaction.getTransactionId());
            return completedTransaction;

        } catch (Exception e) {
            logger.error("Error al procesar transacción {}: {}",
                        savedTransaction.getTransactionId(), e.getMessage());
            transactionRepository.updateStatus(savedTransaction.getTransactionId(),
                                               TransactionStatus.FAILED);

            createAuditRecord(savedTransaction.getTransactionId(), "TRANSACTION_FAILED",
                             SYSTEM_USER, "Error: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Ejecuta la lógica específica de la transacción según su tipo.
     * Este método contiene la lógica de negocio diferenciada por tipo de transacción.
     * @param transaction la transacción a ejecutar
     * @return la transacción con el resultado de la ejecución
     */
    private Transaction executeTransactionLogic(Transaction transaction) {
        switch (transaction.getType()) {
            case DEPOSIT:
                return processDeposit(transaction);
            case WITHDRAWAL:
                return processWithdrawal(transaction);
            case TRANSFER:
                return processTransfer(transaction);
            default:
                throw new IllegalArgumentException("Tipo de transacción no soportado: " + transaction.getType());
        }
    }

    private Transaction processDeposit(Transaction transaction) {
        logger.debug("Procesando depósito para transacción: {}", transaction.getTransactionId());
        validatePositiveAmount(transaction.getAmount());
        return transaction;
    }

    private Transaction processWithdrawal(Transaction transaction) {
        logger.debug("Procesando retiro para transacción: {}", transaction.getTransactionId());
        validatePositiveAmount(transaction.getAmount());
        return transaction;
    }

    private Transaction processTransfer(Transaction transaction) {
        logger.debug("Procesando transferencia para transacción: {}", transaction.getTransactionId());
        validatePositiveAmount(transaction.getAmount());
        return transaction;
    }

    /**
     * Valida los parámetros de entrada de una transacción.
     * @param clientId identificador del cliente
     * @param amount monto de la transacción
     * @param type tipo de transacción
     */
    private void validateTransactionInput(UUID clientId, BigDecimal amount, TransactionType type) {
        if (clientId == null) {
            throw new IllegalArgumentException("El identificador del cliente no puede ser nulo");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        if (type == null) {
            throw new IllegalArgumentException("El tipo de transacción no puede ser nulo");
        }
    }

    /**
     * Valida que el monto sea positivo.
     * @param amount monto a validar
     */
    private void validatePositiveAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
    }

    /**
     * Crea un registro de auditoría para una operación.
     * @param transactionId identificador de la transacción
     * @param action acción realizada
     * @param performedBy usuario que realizó la acción
     * @param details detalles de la operación
     */
    private void createAuditRecord(UUID transactionId, String action,
                                   String performedBy, String details) {
        Audit audit = new Audit(
            UUID.randomUUID(),
            transactionId,
            action,
            performedBy,
            LocalDateTime.now(),
            details
        );
        auditRepository.save(audit);
        logger.debug("Registro de auditoría creado para transacción: {}", transactionId);
    }

    /**
     * Recupera una transacción por su identificador.
     * @param transactionId identificador de la transacción
     * @return Optional con la transacción si existe
     */
    @Transactional(readOnly = true)
    public Optional<Transaction> getTransactionById(UUID transactionId) {
        logger.debug("Consultando transacción con ID: {}", transactionId);
        return transactionRepository.findById(transactionId);
    }

    /**
     * Recupera todas las transacciones de un cliente.
     * @param clientId identificador del cliente
     * @return lista de transacciones del cliente
     */
    @Transactional(readOnly = true)
    public List<Transaction> getTransactionsByClient(UUID clientId) {
        logger.debug("Consultando transacciones para cliente: {}", clientId);
        return transactionRepository.findByClientId(clientId);
    }

    /**
     * Recupera las transacciones por estado.
     * @param status estado de las transacciones
     * @return lista de transacciones con el estado especificado
     */
    @Transactional(readOnly = true)
    public List<Transaction> getTransactionsByStatus(TransactionStatus status) {
        logger.debug("Consultando transacciones con estado: {}", status);
        return transactionRepository.findByStatus(status);
    }

    /**
     * Cancela una transacción existente.
     * @param transactionId identificador de la transacción a cancelar
     * @return la transacción cancelada
     */
    @Transactional
    public Transaction cancelTransaction(UUID transactionId) {
        logger.info("Cancelando transacción: {}", transactionId);

        Transaction transaction = transactionRepository.findById(transactionId)
            .orElseThrow(() -> new IllegalArgumentException(
                "Transacción no encontrada: " + transactionId));

        if (transaction.isCompleted()) {
            throw new IllegalStateException(
                "No se puede cancelar una transacción completada: " + transactionId);
        }

        if (transaction.isFailed()) {
            throw new IllegalStateException(
                "La transacción ya está en estado fallido: " + transactionId);
        }

        Transaction updated = transactionRepository.updateStatus(transactionId, TransactionStatus.CANCELLED)
            .orElseThrow(() -> new IllegalStateException(
                "Error al actualizar el estado de la transacción: " + transactionId));

        createAuditRecord(transactionId, "TRANSACTION_CANCELLED", SYSTEM_USER,
                         "Transacción cancelada por el sistema");

        logger.info("Transacción {} cancelada exitosamente", transactionId);
        return updated;
    }

    /**
     * Calcula el balance total de un cliente.
     * @param clientId identificador del cliente
     * @return suma de todos los depósitos menos retiros
     */
    @Transactional(readOnly = true)
    public BigDecimal calculateClientBalance(UUID clientId) {
        logger.debug("Calculando balance para cliente: {}", clientId);

        List<Transaction> transactions = transactionRepository.findByClientId(clientId);

        BigDecimal totalDeposits = transactions.stream()
            .filter(t -> t.getType() == TransactionType.DEPOSIT && t.isCompleted())
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalWithdrawals = transactions.stream()
            .filter(t -> t.getType() == TransactionType.WITHDRAWAL && t.isCompleted())
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalTransfersOut = transactions.stream()
            .filter(t -> t.getType() == TransactionType.TRANSFER && t.isCompleted())
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalDeposits.subtract(totalWithdrawals).subtract(totalTransfersOut);
    }

    /**
     * Obtiene el historial de auditoría de una transacción.
     * @param transactionId identificador de la transacción
     * @return lista de registros de auditoría
     */
    @Transactional(readOnly = true)
    public List<Audit> getTransactionAuditHistory(UUID transactionId) {
        logger.debug("Consultando historial de auditoría para transacción: {}", transactionId);
        return auditRepository.findByTransactionId(transactionId);
    }

    /**
     * Verifica la conectividad con la base de datos.
     * Método utilizado para validación de startup.
     */
    public void verifyDatabaseConnectivity() {
        logger.info("Verificando conectividad con la base de datos");
        long count = transactionRepository.count();
        logger.info("Conectividad verificada. Total de transacciones en sistema: {}", count);
    }

    /**
     * Inicializa datos de ejemplo para pruebas.
     * Método utilizado para validación de startup.
     */
    public void initializeSampleData() {
        logger.info("Inicializando datos de ejemplo");
        UUID sampleClientId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        if (transactionRepository.findByClientId(sampleClientId).isEmpty()) {
            Transaction sampleTransaction = new Transaction(
                UUID.randomUUID(),
                sampleClientId,
                new BigDecimal("1000.00"),
                LocalDateTime.now(),
                TransactionType.DEPOSIT,
                "Transacción de ejemplo",
                TransactionStatus.COMPLETED
            );
            transactionRepository.save(sampleTransaction);
            createAuditRecord(sampleTransaction.getTransactionId(), "INITIAL_DATA",
                            SYSTEM_USER, "Datos de ejemplo inicializados");
            logger.info("Datos de ejemplo inicializados correctamente");
        } else {
            logger.info("Los datos de ejemplo ya existen, omitiendo inicialización");
        }
    }
}

// === ARCHIVO: src/main/java/com/pragma/banking/transactions/infrastructure/repository/TransactionJpaRepository.java ===
package com.pragma.banking.transactions.infrastructure.repository;

import com.pragma.banking.transactions.infrastructure.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, UUID> {

    List<TransactionEntity> findByClientId(UUID clientId);

    List<TransactionEntity> findByStatus(String status);

    @Query("SELECT t FROM TransactionEntity t WHERE t.clientId = :clientId AND t.transactionDate BETWEEN :startDate AND :endDate")
    List<TransactionEntity> findByClientIdAndTransactionDateBetween(
            @Param("clientId") UUID clientId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT t FROM TransactionEntity t WHERE t.status = :status AND t.transactionDate < :threshold")
    List<TransactionEntity> findStaleTransactions(
            @Param("status") String status,
            @Param("threshold") LocalDateTime threshold);

    @Query("SELECT SUM(t.amount) FROM TransactionEntity t WHERE t.clientId = :clientId AND t.type = :type")
    BigDecimal sumAmountByClientIdAndType(
            @Param("clientId") UUID clientId,
            @Param("type") String type);

    @Query("SELECT COUNT(t) FROM TransactionEntity t WHERE t.transactionDate >= :since")
    long countTransactionsSince(@Param("since") LocalDateTime since);

    @Query("SELECT t FROM TransactionEntity t WHERE t.clientId = :clientId ORDER BY t.transactionDate DESC")
    List<TransactionEntity> findLatestByClientId(@Param("clientId") UUID clientId);

    @Query("SELECT t.type, COUNT(t) FROM TransactionEntity t WHERE t.transactionDate BETWEEN :start AND :end GROUP BY t.type")
    List<Object[]> countByTypeBetweenDates(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
}

// === ARCHIVO: src/main/java/com/pragma/banking/transactions/infrastructure/repository/AuditJpaRepository.java ===
package com.pragma.banking.transactions.infrastructure.repository;

import com.pragma.banking.transactions.infrastructure.entity.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AuditJpaRepository extends JpaRepository<AuditEntity, UUID> {

    List<AuditEntity> findByTransactionId(UUID transactionId);

    List<AuditEntity> findByPerformedBy(String performedBy);

    @Query("SELECT a FROM AuditEntity a WHERE a.timestamp BETWEEN :startDate AND :endDate ORDER BY a.timestamp DESC")
    List<AuditEntity> findByTimestampBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT a FROM AuditEntity a WHERE a.action = :action AND a.timestamp >= :since")
    List<AuditEntity> findRecentByAction(
            @Param("action") String action,
            @Param("since") LocalDateTime since);

    @Query("SELECT COUNT(a) FROM AuditEntity a WHERE a.transactionId = :transactionId")
    long countByTransactionId(@Param("transactionId") UUID transactionId);

    @Query("SELECT a.performedBy, COUNT(a) FROM AuditEntity a WHERE a.timestamp >= :since GROUP BY a.performedBy")
    List<Object[]> countActionsByUserSince(@Param("since") LocalDateTime since);
}

// === ARCHIVO: src/main/java/com/pragma/banking/transactions/application/usecase/ProcessTransactionUseCase.java ===
package com.pragma.banking.transactions.application.usecase;

import com.pragma.banking.transactions.domain.model.Audit;
import com.pragma.banking.transactions.domain.model.Transaction;
import com.pragma.banking.transactions.domain.model.Transaction.TransactionStatus;
import com.pragma.banking.transactions.domain.port.AuditRepositoryPort;
import com.pragma.banking.transactions.domain.port.TransactionRepositoryPort;
import com.pragma.banking.transactions.domain.service.TransactionService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProcessTransactionUseCase {

    private static final Logger log = LoggerFactory.getLogger(ProcessTransactionUseCase.class);

    private final TransactionRepositoryPort transactionRepository;
    private final AuditRepositoryPort auditRepository;
    private final TransactionService transactionService;

    public ProcessTransactionUseCase(
            TransactionRepositoryPort transactionRepository,
            AuditRepositoryPort auditRepository,
            TransactionService transactionService) {
        this.transactionRepository = transactionRepository;
        this.auditRepository = auditRepository;
        this.transactionService = transactionService;
    }

    @Transactional
    @CircuitBreaker(name = "transactionCircuitBreaker", fallbackMethod = "processTransactionFallback")
    @Retry(name = "transactionRetry")
    public Transaction processTransaction(Transaction transaction) {
        log.info("Iniciando procesamiento de transacción: {}", transaction.getTransactionId());

        transactionService.verifyDatabaseConnectivity();

        if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto de la transacción debe ser mayor que cero");
        }

        if (transaction.getClientId() == null) {
            throw new IllegalArgumentException("El cliente es obligatorio");
        }

        Transaction savedTransaction = transactionRepository.save(transaction);
        log.info("Transacción guardada con estado: {}", savedTransaction.getStatus());

        Audit auditEntry = new Audit(
                UUID.randomUUID(),
                savedTransaction.getTransactionId(),
                "TRANSACTION_CREATED",
                "SYSTEM",
                LocalDateTime.now(),
                String.format("Transacción creada para cliente %s por monto %s",
                        savedTransaction.getClientId(), savedTransaction.getAmount())
        );
        auditRepository.save(auditEntry);

        return savedTransaction;
    }

    @Transactional
    public Transaction updateTransactionStatus(UUID transactionId, TransactionStatus newStatus) {
        log.info("Actualizando estado de transacción {} a {}", transactionId, newStatus);

        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        if (optionalTransaction.isEmpty()) {
            throw new IllegalArgumentException("Transacción no encontrada: " + transactionId);
        }

        Transaction transaction = optionalTransaction.get();
        TransactionStatus oldStatus = transaction.getStatus();
        transaction.setStatus(newStatus);

        Transaction updatedTransaction = transactionRepository.save(transaction);

        Audit auditEntry = new Audit(
                UUID.randomUUID(),
                transactionId,
                "TRANSACTION_STATUS_CHANGED",
                "SYSTEM",
                LocalDateTime.now(),
                String.format("Estado cambiado de %s a %s", oldStatus, newStatus)
        );
        auditRepository.save(auditEntry);

        log.info("Transacción {} actualizada exitosamente", transactionId);
        return updatedTransaction;
    }

    @Transactional(readOnly = true)
    public Optional<Transaction> getTransactionById(UUID transactionId) {
        return transactionRepository.findById(transactionId);
    }

    @Transactional(readOnly = true)
    public java.util.List<Transaction> getTransactionsByClient(UUID clientId) {
        return transactionRepository.findByClientId(clientId);
    }

    @Transactional(readOnly = true)
    public java.util.List<Transaction> getTransactionsByStatus(TransactionStatus status) {
        return transactionRepository.findByStatus(status.name());
    }

    private Transaction processTransactionFallback(Transaction transaction, Throwable t) {
        log.error("Fallback activado para transacción {}: {}", transaction.getTransactionId(), t.getMessage());

        Audit auditEntry = new Audit(
                UUID.randomUUID(),
                transaction.getTransactionId(),
                "TRANSACTION_FAILED",
                "SYSTEM",
                LocalDateTime.now(),
                String.format("Transacción fallida: %s", t.getMessage())
        );
        try {
            auditRepository.save(auditEntry);
        } catch (Exception e) {
            log.error("Error al guardar auditoría de fallback: {}", e.getMessage());
        }

        transaction.setStatus(TransactionStatus.FAILED);
        return transaction;
    }
}

// === ARCHIVO: src/main/java/com/pragma/banking/transactions/infrastructure/adapter/TransactionJpaAdapter.java ===
package com.pragma.banking.transactions.infrastructure.adapter;



import com.pragma.banking.transactions.domain.model.TransactionType;
import com.pragma.banking.transactions.domain.model.TransactionStatus;
import com.pragma.banking.transactions.domain.model.Transaction;
import com.pragma.banking.transactions.domain.port.TransactionRepositoryPort;
import com.pragma.banking.transactions.infrastructure.entity.TransactionEntity;
import com.pragma.banking.transactions.infrastructure.repository.TransactionJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Transactional
public class TransactionJpaAdapter implements TransactionRepositoryPort {

    private final TransactionJpaRepository transactionRepository;

    public TransactionJpaAdapter(TransactionJpaRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity entity = toEntity(transaction);
        TransactionEntity savedEntity = transactionRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Transaction> findById(UUID transactionId) {
        return transactionRepository.findById(transactionId)
                .map(this::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> findAll() {
        return transactionRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> findByClientId(UUID clientId) {
        return transactionRepository.findByClientId(clientId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> findByStatus(Transaction.TransactionStatus status) {
        return transactionRepository.findByStatus(status.name()).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findByTransactionDateBetween(startDate, endDate).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTotalAmountByClientAndDateRange(UUID clientId, LocalDateTime startDate, LocalDateTime endDate) {
        BigDecimal total = transactionRepository.sumAmountByClientIdAndDateRange(clientId, startDate, endDate);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    public void delete(UUID transactionId) {
        transactionRepository.deleteById(transactionId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID transactionId) {
        return transactionRepository.existsById(transactionId);
    }

    private TransactionEntity toEntity(Transaction transaction) {
        TransactionEntity entity = new TransactionEntity();
        if (transaction.getTransactionId() != null) {
            entity.setTransactionId(transaction.getTransactionId());
        }
        entity.setClientId(transaction.getClientId());
        entity.setAmount(transaction.getAmount());
        entity.setTransactionDate(transaction.getTransactionDate());
        entity.setType(transaction.getType().name());
        entity.setDescription(transaction.getDescription());
        entity.setStatus(transaction.getStatus().name());
        return entity;
    }

    private Transaction toDomain(TransactionEntity entity) {
        return new Transaction(
                entity.getTransactionId(),
                entity.getClientId(),
                entity.getAmount(),
                entity.getTransactionDate(),
                Transaction.TransactionType.valueOf(entity.getType()),
                entity.getDescription(),
                Transaction.TransactionStatus.valueOf(entity.getStatus())
        );
    }
}

// === ARCHIVO: src/main/java/com/pragma/banking/transactions/infrastructure/adapter/AuditJpaAdapter.java ===
package com.pragma.banking.transactions.infrastructure.adapter;

import com.pragma.banking.transactions.domain.model.Audit;
import com.pragma.banking.transactions.domain.port.AuditRepositoryPort;
import com.pragma.banking.transactions.infrastructure.entity.AuditEntity;
import com.pragma.banking.transactions.infrastructure.repository.AuditJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Transactional
public class AuditJpaAdapter implements AuditRepositoryPort {

    private final AuditJpaRepository auditRepository;

    public AuditJpaAdapter(AuditJpaRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Override
    public Audit save(Audit audit) {
        AuditEntity entity = toEntity(audit);
        AuditEntity savedEntity = auditRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Audit> findById(UUID auditId) {
        return auditRepository.findById(auditId)
                .map(this::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Audit> findAll() {
        return auditRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Audit> findByTransactionId(UUID transactionId) {
        return auditRepository.findByTransactionId(transactionId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Audit> findByAction(String action) {
        return auditRepository.findByAction(action).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Audit> findByPerformedBy(String performedBy) {
        return auditRepository.findByPerformedBy(performedBy).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Audit> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return auditRepository.findByTimestampBetween(startDate, endDate).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID auditId) {
        auditRepository.deleteById(auditId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID auditId) {
        return auditRepository.existsById(auditId);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByTransactionId(UUID transactionId) {
        return auditRepository.countByTransactionId(transactionId);
    }

    private AuditEntity toEntity(Audit audit) {
        AuditEntity entity = new AuditEntity();
        if (audit.getAuditId() != null) {
            entity.setAuditId(audit.getAuditId());
        }
        entity.setTransactionId(audit.getTransactionId());
        entity.setAction(audit.getAction());
        entity.setPerformedBy(audit.getPerformedBy());
        entity.setTimestamp(audit.getTimestamp());
        entity.setDetails(audit.getDetails());
        return entity;
    }

    private Audit toDomain(AuditEntity entity) {
        return new Audit(
                entity.getAuditId(),
                entity.getTransactionId(),
                entity.getAction(),
                entity.getPerformedBy(),
                entity.getTimestamp(),
                entity.getDetails()
        );
    }
}

// === ARCHIVO: src/main/java/com/pragma/banking/transactions/infrastructure/entity/ClientEntity.java ===
package com.pragma.banking.transactions.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "clients")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "client_id")
    private UUID clientId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "identification_number", nullable = false, unique = true, length = 50)
    private String identificationNumber;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "client_type", length = 20)
    private String clientType;

    public ClientEntity() {
    }

    public ClientEntity(String name, String identificationNumber, String email) {
        this.name = name;
        this.identificationNumber = identificationNumber;
        this.email = email;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.isActive == null) {
            this.isActive = true;
        }
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }
}

// === ARCHIVO: src/main/java/com/pragma/banking/transactions/infrastructure/entity/TransactionEntity.java ===
package com.pragma.banking.transactions.infrastructure.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions", indexes = {
    @Index(name = "idx_transaction_client_id", columnList = "client_id"),
    @Index(name = "idx_transaction_status", columnList = "status"),
    @Index(name = "idx_transaction_date", columnList = "transaction_date")
})
public class TransactionEntity {

    @Id
    @Column(name = "transaction_id", nullable = false)
    private UUID transactionId;

    @Column(name = "client_id", nullable = false)
    private UUID clientId;

    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private TransactionTypeEnum type;

    @Column(name = "description", length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private TransactionStatusEnum status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version")
    private Long version;

    public TransactionEntity() {
    }

    public TransactionEntity(UUID transactionId, UUID clientId, BigDecimal amount,
                            LocalDateTime transactionDate, TransactionTypeEnum type,
                            String description, TransactionStatusEnum status) {
        this.transactionId = transactionId;
        this.clientId = clientId;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.type = type;
        this.description = description;
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public TransactionTypeEnum getType() {
        return type;
    }

    public void setType(TransactionTypeEnum type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TransactionStatusEnum status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public enum TransactionTypeEnum {
        DEPOSIT,
        WITHDRAWAL,
        TRANSFER,
        PAYMENT,
        REFUND
    }

    public enum TransactionStatusEnum {
        PENDING,
        PROCESSING,
        COMPLETED,
        FAILED,
        CANCELLED,
        REVERSED
    }
}

// === ARCHIVO: src/main/java/com/pragma/banking/transactions/infrastructure/entity/AuditEntity.java ===
package com.pragma.banking.transactions.infrastructure.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "audit_logs", indexes = {
    @Index(name = "idx_audit_transaction_id", columnList = "transaction_id"),
    @Index(name = "idx_audit_timestamp", columnList = "timestamp"),
    @Index(name = "idx_audit_action", columnList = "action"),
    @Index(name = "idx_audit_performed_by", columnList = "performed_by")
})
public class AuditEntity {

    @Id
    @Column(name = "audit_id", nullable = false)
    private UUID auditId;

    @Column(name = "transaction_id", nullable = false)
    private UUID transactionId;

    @Column(name = "action", nullable = false, length = 50)
    private String action;

    @Column(name = "performed_by", nullable = false, length = 100)
    private String performedBy;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "details", columnDefinition = "TEXT")
    private String details;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", length = 500)
    private String userAgent;

    @Column(name = "success", nullable = false)
    private Boolean success;

    @Column(name = "error_message", length = 1000)
    private String errorMessage;

    @Column(name = "execution_time_ms")
    private Long executionTimeMs;

    public AuditEntity() {
    }

    public AuditEntity(UUID auditId, UUID transactionId, String action,
                      String performedBy, LocalDateTime timestamp, String details) {
        this.auditId = auditId;
        this.transactionId = transactionId;
        this.action = action;
        this.performedBy = performedBy;
        this.timestamp = timestamp;
        this.details = details;
        this.success = true;
    }

    public static AuditEntity createFailure(UUID transactionId, String action,
                                           String performedBy, String errorMessage) {
        AuditEntity audit = new AuditEntity();
        audit.auditId = UUID.randomUUID();
        audit.transactionId = transactionId;
        audit.action = action;
        audit.performedBy = performedBy;
        audit.timestamp = LocalDateTime.now();
        audit.success = false;
        audit.errorMessage = errorMessage;
        return audit;
    }

    public UUID getAuditId() {
        return auditId;
    }

    public void setAuditId(UUID auditId) {
        this.auditId = auditId;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Long getExecutionTimeMs() {
        return executionTimeMs;
    }

    public void setExecutionTimeMs(Long executionTimeMs) {
        this.executionTimeMs = executionTimeMs;
    }
}

// === ARCHIVO: src/main/java/com/pragma/banking/transactions/infrastructure/controller/TransactionController.java ===
package com.pragma.banking.transactions.infrastructure.controller;



import com.pragma.banking.transactions.domain.model.TransactionStatus;
import com.pragma.banking.transactions.domain.model.TransactionType;
import com.pragma.banking.transactions.application.usecase.ProcessTransactionUseCase;
import com.pragma.banking.transactions.domain.model.Transaction;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    private final ProcessTransactionUseCase processTransactionUseCase;

    public TransactionController(ProcessTransactionUseCase processTransactionUseCase) {
        this.processTransactionUseCase = processTransactionUseCase;
    }

    @PostMapping
    @CircuitBreaker(name = "transactionCircuitBreaker", fallbackMethod = "processTransactionFallback")
    @Retry(name = "transactionRetry")
    public ResponseEntity<TransactionResponse> processTransaction(
            @Valid @RequestBody TransactionRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "system") String userId) {
        logger.info("Processing transaction request: type={}, amount={}, clientId={}",
                    request.type(), request.amount(), request.clientId());

        Transaction transaction = new Transaction(
            UUID.randomUUID(),
            request.clientId(),
            request.amount(),
            java.time.LocalDateTime.now(),
            Transaction.TransactionType.valueOf(request.type().toUpperCase()),
            request.description()
        );
        transaction.setStatus(Transaction.TransactionStatus.PENDING);

        processTransactionUseCase.execute(transaction, userId);

        TransactionResponse response = new TransactionResponse(
            transaction.getTransactionId().toString(),
            transaction.getStatus().name(),
            "Transaction processed successfully"
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDetailsResponse> getTransaction(
            @PathVariable @NotNull UUID transactionId) {
        logger.info("Fetching transaction: {}", transactionId);

        Transaction transaction = processTransactionUseCase.findById(transactionId);

        if (transaction == null) {
            return ResponseEntity.notFound().build();
        }

        TransactionDetailsResponse response = new TransactionDetailsResponse(
            transaction.getTransactionId().toString(),
            transaction.getClientId().toString(),
            transaction.getAmount(),
            transaction.getTransactionDate().toString(),
            transaction.getType().name(),
            transaction.getDescription(),
            transaction.getStatus().name()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TransactionSummaryResponse>> getTransactions(
            @RequestParam(required = false) UUID clientId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        logger.info("Fetching transactions: clientId={}, status={}, page={}, size={}",
                    clientId, status, page, size);

        List<Transaction> transactions = processTransactionUseCase.findByFilters(clientId, status, page, size);

        List<TransactionSummaryResponse> response = transactions.stream()
            .map(t -> new TransactionSummaryResponse(
                t.getTransactionId().toString(),
                t.getAmount(),
                t.getType().name(),
                t.getStatus().name(),
                t.getTransactionDate().toString()
            ))
            .toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{transactionId}/cancel")
    public ResponseEntity<Map<String, String>> cancelTransaction(
            @PathVariable @NotNull UUID transactionId,
            @RequestHeader(value = "X-User-Id", defaultValue = "system") String userId) {
        logger.info("Cancelling transaction: {}, user: {}", transactionId, userId);

        boolean cancelled = processTransactionUseCase.cancelTransaction(transactionId, userId);

        if (!cancelled) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", "Transaction cannot be cancelled in its current state"));
        }

        return ResponseEntity.ok(Map.of(
            "transactionId", transactionId.toString(),
            "status", "CANCELLED"
        ));
    }

    @PostMapping("/{transactionId}/reverse")
    public ResponseEntity<Map<String, String>> reverseTransaction(
            @PathVariable @NotNull UUID transactionId,
            @RequestBody ReverseTransactionRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "system") String userId) {
        logger.info("Reversing transaction: {}, reason: {}", transactionId, request.reason());

        boolean reversed = processTransactionUseCase.reverseTransaction(transactionId, request.reason(), userId);

        if (!reversed) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", "Transaction cannot be reversed"));
        }

        return ResponseEntity.ok(Map.of(
            "transactionId", transactionId.toString(),
            "status", "REVERSED",
            "reversalReason", request.reason()
        ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        logger.warn("Invalid argument: {}", ex.getMessage());
        return ResponseEntity.badRequest()
            .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        logger.error("Unexpected error processing transaction", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of("error", "An unexpected error occurred"));
    }

    public ResponseEntity<TransactionResponse> processTransactionFallback(
            TransactionRequest request, Throwable t) {
        logger.error("Circuit breaker fallback triggered for transaction: {}", t.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(new TransactionResponse(
                null,
                "UNAVAILABLE",
                "Service temporarily unavailable. Please try again later."
            ));
    }

    public record TransactionRequest(
        @NotNull UUID clientId,
        @NotNull BigDecimal amount,
        @NotNull String type,
        String description
    ) {}

    public record TransactionResponse(String transactionId, String status, String message) {}

    public record TransactionDetailsResponse(
        String transactionId,
        String clientId,
        BigDecimal amount,
        String transactionDate,
        String type,
        String description,
        String status
    ) {}

    public record TransactionSummaryResponse(
        String transactionId,
        BigDecimal amount,
        String type,
        String status,
        String transactionDate
    ) {}

    public record ReverseTransactionRequest(String reason) {}
}

// === ARCHIVO: src/main/java/com/pragma/banking/transactions/infrastructure/config/TransactionConfig.java ===
package com.pragma.banking.transactions.infrastructure.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.time.Duration;
import java.util.function.Supplier;

@Configuration
@EnableTransactionManagement
public class TransactionConfig {

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        transactionManager.setNestedTransactionAllowed(true);
        return transactionManager;
    }

    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        TransactionTemplate template = new TransactionTemplate(transactionManager);
        template.setIsolationLevel(TransactionTemplate.ISOLATION_READ_COMMITTED);
        template.setPropagationBehavior(TransactionTemplate.PROPAGATION_REQUIRED);
        template.setTimeout(30);
        return template;
    }

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(60))
                .slidingWindowSize(10)
                .minimumNumberOfCalls(5)
                .permittedNumberOfCallsInHalfOpenState(3)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .waitDurationInOpenState(Duration.ofSeconds(60))
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .recordExceptions(
                        java.io.IOException.class,
                        java.sql.SQLException.class,
                        org.springframework.dao.DataAccessException.class
                )
                .build();

        return CircuitBreakerRegistry.of(config);
    }

    @Bean
    public CircuitBreakerRegistry resilientCircuitBreakerRegistry() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(30)
                .waitDurationInOpenState(Duration.ofSeconds(120))
                .slidingWindowSize(20)
                .minimumNumberOfCalls(10)
                .permittedNumberOfCallsInHalfOpenState(5)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .slowCallRateThreshold(50)
                .slowCallDurationThreshold(Duration.ofSeconds(5))
                .recordExceptions(
                        java.io.IOException.class,
                        java.sql.SQLException.class,
                        org.springframework.dao.DataAccessException.class,
                        java.util.concurrent.TimeoutException.class
                )
                .build();

        return CircuitBreakerRegistry.of(config);
    }

    @Bean
    public RetryRegistry retryRegistry() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(500))
                .retryExceptions(
                        java.io.IOException.class,
                        java.sql.SQLException.class,
                        org.springframework.dao.DataAccessException.class
                )
                .ignoreExceptions(
                        org.springframework.dao.DataIntegrityViolationException.class
                )
                .build();

        return RetryRegistry.of(config);
    }

    @Bean
    public RetryRegistry resilientRetryRegistry() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(5)
                .waitDuration(Duration.ofSeconds(1))
                .retryExceptions(
                        java.io.IOException.class,
                        java.sql.SQLException.class,
                        org.springframework.dao.DataAccessException.class,
                        java.util.concurrent.TimeoutException.class
                )
                .ignoreExceptions(
                        org.springframework.dao.DataIntegrityViolationException.class
                )
                .build();

        return RetryRegistry.of(config);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setQueryTimeout(30);
        return jdbcTemplate;
    }

    @Bean
    public Supplier<CircuitBreakerRegistry> circuitBreakerSupplier() {
        return this::circuitBreakerRegistry;
    }

    @Bean
    public Supplier<RetryRegistry> retrySupplier() {
        return this::retryRegistry;
    }
}

// === ARCHIVO: src/main/resources/db/migration/V1__Create_tables.sql ===
-- V1__Create_tables.sql
-- Creación de tablas para el sistema de transacciones bancarias
-- Cumplimiento del modelo ACID: Atomicidad, Consistencia, Aislamiento y Durabilidad

-- Tabla de clientes
CREATE TABLE IF NOT EXISTS clients (
    client_id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    identification_number VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_clients_identification ON clients(identification_number);
CREATE INDEX idx_clients_email ON clients(email);

-- Tabla de transacciones
CREATE TABLE IF NOT EXISTS transactions (
    transaction_id UUID PRIMARY KEY,
    client_id UUID NOT NULL REFERENCES clients(client_id) ON DELETE RESTRICT,
    amount DECIMAL(19, 4) NOT NULL CHECK (amount > 0),
    transaction_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    type VARCHAR(20) NOT NULL CHECK (type IN ('DEPOSIT', 'WITHDRAWAL', 'TRANSFER', 'PAYMENT')),
    description VARCHAR(500),
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'COMPLETED', 'FAILED', 'CANCELLED')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    version INTEGER NOT NULL DEFAULT 0
);

CREATE INDEX idx_transactions_client ON transactions(client_id);
CREATE INDEX idx_transactions_date ON transactions(transaction_date);
CREATE INDEX idx_transactions_status ON transactions(status);
CREATE INDEX idx_transactions_client_date ON transactions(client_id, transaction_date);

-- Tabla de auditoría
CREATE TABLE IF NOT EXISTS audit_log (
    audit_id UUID PRIMARY KEY,
    transaction_id UUID NOT NULL REFERENCES transactions(transaction_id) ON DELETE CASCADE,
    action VARCHAR(50) NOT NULL,
    performed_by VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    details TEXT,
    previous_state TEXT,
    new_state TEXT
);

CREATE INDEX idx_audit_transaction ON audit_log(transaction_id);
CREATE INDEX idx_audit_timestamp ON audit_log(timestamp);
CREATE INDEX idx_audit_performed_by ON audit_log(performed_by);

-- Tabla de control de concurrencia para transacciones
CREATE TABLE IF NOT EXISTS transaction_lock (
    lock_id UUID PRIMARY KEY,
    resource_id UUID NOT NULL,
    resource_type VARCHAR(50) NOT NULL,
    lock_type VARCHAR(20) NOT NULL CHECK (lock_type IN ('SHARED', 'EXCLUSIVE')),
    acquired_by VARCHAR(255) NOT NULL,
    acquired_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NOT NULL,
    released_at TIMESTAMP,
    UNIQUE(resource_id, resource_type, lock_type)
);

CREATE INDEX idx_lock_resource ON transaction_lock(resource_id, resource_type);
CREATE INDEX idx_lock_expires ON transaction_lock(expires_at);

-- Tabla de configuraciones de transacciones
CREATE TABLE IF NOT EXISTS transaction_config (
    config_id UUID PRIMARY KEY,
    config_key VARCHAR(100) NOT NULL UNIQUE,
    config_value TEXT NOT NULL,
    description VARCHAR(500),
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_config_key ON transaction_config(config_key);

-- Insertar configuraciones iniciales
INSERT INTO transaction_config (config_id, config_key, config_value, description, is_active)
VALUES 
    (gen_random_uuid(), 'max.transaction.amount', '1000000.00', 'Monto máximo por transacción', true),
    (gen_random_uuid(), 'min.transaction.amount', '0.01', 'Monto mínimo por transacción', true),
    (gen_random_uuid(), 'daily.transaction.limit', '5000000.00', 'Límite diario de transacciones', true),
    (gen_random_uuid(), 'transaction.timeout.seconds', '30', 'Timeout de transacción en segundos', true),
    (gen_random_uuid(), 'enable.circuit.breaker', 'true', 'Habilitar circuit breaker', true)
ON CONFLICT (config_key) DO NOTHING;

-- Función para actualizar timestamps automáticamente
CREATE OR REPLACE FUNCTION update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger para actualizar timestamp en clients
CREATE TRIGGER update_clients_timestamp
    BEFORE UPDATE ON clients
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

-- Trigger para actualizar timestamp en transactions
CREATE TRIGGER update_transactions_timestamp
    BEFORE UPDATE ON transactions
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

-- Trigger para actualizar timestamp en transaction_config
CREATE TRIGGER update_config_timestamp
    BEFORE UPDATE ON transaction_config
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

-- Función para validación de monto
CREATE OR REPLACE FUNCTION validate_transaction_amount(p_amount DECIMAL)
RETURNS BOOLEAN AS $$
DECLARE
    v_max_amount DECIMAL;
    v_min_amount DECIMAL;
BEGIN
    SELECT CAST(config_value AS DECIMAL) INTO v_max_amount
    FROM transaction_config
    WHERE config_key = 'max.transaction.amount' AND is_active = true;

    SELECT CAST(config_value AS DECIMAL) INTO v_min_amount
    FROM transaction_config
    WHERE config_key = 'min.transaction.amount' AND is_active = true;

    RETURN p_amount >= v_min_amount AND p_amount <= v_max_amount;
END;
$$ LANGUAGE plpgsql;

-- Función para verificar límite diario
CREATE OR REPLACE FUNCTION check_daily_limit(p_client_id UUID, p_amount DECIMAL)
RETURNS BOOLEAN AS $$
DECLARE
    v_daily_limit DECIMAL;
    v_total_today DECIMAL;
BEGIN
    SELECT CAST(config_value AS DECIMAL) INTO v_daily_limit
    FROM transaction_config
    WHERE config_key = 'daily.transaction.limit' AND is_active = true;

    SELECT COALESCE(SUM(amount), 0) INTO v_total_today
    FROM transactions
    WHERE client_id = p_client_id
      AND DATE(transaction_date) = CURRENT_DATE
      AND status = 'COMPLETED';

    RETURN (v_total_today + p_amount) <= v_daily_limit;
END;
$$ LANGUAGE plpgsql;

// === ARCHIVO: src/main/resources/db/migration/V2__Add_procedures.sql ===
-- V2__Add_procedures.sql
-- Procedimientos almacenados para optimización de operaciones de transacciones
-- Optimización de rendimiento y manejo de concurrencia

-- Procedimiento para crear una transacción con bloqueo pesimista
CREATE OR REPLACE PROCEDURE create_transaction_with_lock(
    IN p_transaction_id UUID,
    IN p_client_id UUID,
    IN p_amount DECIMAL,
    IN p_type VARCHAR,
    IN p_description VARCHAR,
    OUT p_result BOOLEAN,
    OUT p_error_message VARCHAR
)
LANGUAGE plpgsql
AS $$
DECLARE
    v_lock_id UUID;
    v_current_balance DECIMAL;
BEGIN
    p_result := false;
    p_error_message := NULL;

    -- Adquirir bloqueo exclusivo en el cliente
    BEGIN
        INSERT INTO transaction_lock (lock_id, resource_id, resource_type, lock_type, acquired_by, expires_at)
        VALUES (gen_random_uuid(), p_client_id, 'CLIENT', 'EXCLUSIVE', 'transaction_service', CURRENT_TIMESTAMP + INTERVAL '30 seconds')
        ON CONFLICT (resource_id, resource_type, lock_type) DO NOTHING
        RETURNING lock_id INTO v_lock_id;

        IF v_lock_id IS NULL THEN
            p_error_message := 'No se pudo adquirir bloqueo para el cliente';
            RETURN;
        END IF;

        -- Verificar límite diario
        IF NOT check_daily_limit(p_client_id, p_amount) THEN
            p_error_message := 'Límite diario excedido';
            RETURN;
        END IF;

        -- Validar monto
        IF NOT validate_transaction_amount(p_amount) THEN
            p_error_message := 'Monto de transacción inválido';
            RETURN;
        END IF;

        -- Crear la transacción
        INSERT INTO transactions (transaction_id, client_id, amount, type, description, status)
        VALUES (p_transaction_id, p_client_id, p_amount, p_type, p_description, 'PENDING');

        -- Registrar en auditoría
        INSERT INTO audit_log (audit_id, transaction_id, action, performed_by, details, new_state)
        VALUES (gen_random_uuid(), p_transaction_id, 'TRANSACTION_CREATED', 'system', 
                'Transacción creada con bloqueo', '{"status": "PENDING"}');

        p_result := true;

    EXCEPTION
        WHEN OTHERS THEN
            p_error_message := SQLERRM;
            p_result := false;
    END;

    -- Liberar bloqueo
    IF v_lock_id IS NOT NULL THEN
        UPDATE transaction_lock
        SET released_at = CURRENT_TIMESTAMP
        WHERE lock_id = v_lock_id;
    END IF;
END;
$$;

-- Procedimiento para completar una transacción
CREATE OR REPLACE PROCEDURE complete_transaction(
    IN p_transaction_id UUID,
    IN p_performed_by VARCHAR,
    OUT p_result BOOLEAN,
    OUT p_error_message VARCHAR
)
LANGUAGE plpgsql
AS $$
DECLARE
    v_current_status VARCHAR;
    v_client_id UUID;
    v_amount DECIMAL;
BEGIN
    p_result := false;
    p_error_message := NULL;

    -- Obtener información de la transacción
    SELECT t.status, t.client_id, t.amount
    INTO v_current_status, v_client_id, v_amount
    FROM transactions t
    WHERE t.transaction_id = p_transaction_id
    FOR UPDATE;

    IF v_current_status IS NULL THEN
        p_error_message := 'Transacción no encontrada';
        RETURN;
    END IF;

    IF v_current_status != 'PENDING' THEN
        p_error_message := 'La transacción no está en estado PENDING';
        RETURN;
    END IF;

    -- Actualizar estado a COMPLETED
    UPDATE transactions
    SET status = 'COMPLETED', version = version + 1
    WHERE transaction_id = p_transaction_id AND version = (SELECT version FROM transactions WHERE transaction_id = p_transaction_id);

    IF NOT FOUND THEN
        p_error_message := 'Conflicto de versión - transacción modificada por otro proceso';
        RETURN;
    END IF;

    -- Registrar en auditoría
    INSERT INTO audit_log (audit_id, transaction_id, action, performed_by, details, previous_state, new_state)
    VALUES (gen_random_uuid(), p_transaction_id, 'TRANSACTION_COMPLETED', p_performed_by,
            'Transacción completada successfully',
            '{"status": "PENDING"}',
            '{"status": "COMPLETED"}');

    p_result := true;

EXCEPTION
    WHEN OTHERS THEN
        p_error_message := SQLERRM;
        p_result := false;
END;
$$;

-- Procedimiento para revertir una transacción
CREATE OR REPLACE PROCEDURE rollback_transaction(
    IN p_transaction_id UUID,
    IN p_reason VARCHAR,
    IN p_performed_by VARCHAR,
    OUT p_result BOOLEAN,
    OUT p_error_message VARCHAR
)
LANGUAGE plpgsql
AS $$
DECLARE
    v_current_status VARCHAR;
    v_previous_state TEXT;
BEGIN
    p_result := false;
    p_error_message := NULL;

    -- Obtener estado actual
    SELECT status INTO v_current_status
    FROM transactions
    WHERE transaction_id = p_transaction_id
    FOR UPDATE;

    IF v_current_status IS NULL THEN
        p_error_message := 'Transacción no encontrada';
        RETURN;
    END IF;

    IF v_current_status IN ('COMPLETED', 'CANCELLED') THEN
        p_error_message := 'No se puede revertir una transacción ' || v_current_status;
        RETURN;
    END IF;

    v_previous_state := '{"status": "' || v_current_status || '"}';

    -- Marcar como cancelada
    UPDATE transactions
    SET status = 'CANCELLED', version = version + 1
    WHERE transaction_id = p_transaction_id;

    -- Registrar en auditoría
    INSERT INTO audit_log (audit_id, transaction_id, action, performed_by, details, previous_state, new_state)
    VALUES (gen_random_uuid(), p_transaction_id, 'TRANSACTION_ROLLBACK', p_performed_by,
            'Reversión: ' || p_reason,
            v_previous_state,
            '{"status": "CANCELLED"}');

    p_result := true;

EXCEPTION
    WHEN OTHERS THEN
        p_error_message := SQLERRM;
        p_result := false;
END;
$$;

-- Procedimiento para obtener resumen de transacciones por cliente
CREATE OR REPLACE PROCEDURE get_client_transaction_summary(
    IN p_client_id UUID,
    IN p_start_date DATE,
    IN p_end_date DATE,
    OUT p_total_transactions BIGINT,
    OUT p_total_amount DECIMAL,
    OUT p_completed_count BIGINT,
    OUT p_failed_count BIGINT,
    OUT p_pending_count BIGINT
)
LANGUAGE plpgsql
AS $$
BEGIN
    SELECT COUNT(*), COALESCE(SUM(amount), 0), 0, 0, 0
    INTO p_total_transactions, p_total_amount, p_completed_count, p_failed_count, p_pending_count
    FROM transactions
    WHERE client_id = p_client_id
      AND DATE(transaction_date) BETWEEN p_start_date AND p_end_date;

    SELECT COUNT(*) INTO p_completed_count
    FROM transactions
    WHERE client_id = p_client_id
      AND DATE(transaction_date) BETWEEN p_start_date AND p_end_date
      AND status = 'COMPLETED';

    SELECT COUNT(*) INTO p_failed_count
    FROM transactions
    WHERE client_id = p_client_id
      AND DATE(transaction_date) BETWEEN p_start_date AND p_end_date
      AND status = 'FAILED';

    SELECT COUNT(*) INTO p_pending_count
    FROM transactions
    WHERE client_id = p_client_id
      AND DATE(transaction_date) BETWEEN p_start_date AND p_end_date
      AND status = 'PENDING';
END;
$$;

-- Función para limpiar bloqueos expirados
CREATE OR REPLACE FUNCTION cleanup_expired_locks()
RETURNS INTEGER
LANGUAGE plpgsql
AS $$
DECLARE
    v_deleted INTEGER;
BEGIN
    UPDATE transaction_lock
    SET released_at = CURRENT_TIMESTAMP
    WHERE expires_at < CURRENT_TIMESTAMP AND released_at IS NULL
    RETURNING COUNT(*) INTO v_deleted;

    RETURN v_deleted;
END;
$$;

-- Función para obtener historial de auditoría de una transacción
CREATE OR REPLACE FUNCTION get_transaction_audit_history(p_transaction_id UUID)
RETURNS TABLE (
    audit_id UUID,
    action VARCHAR,
    performed_by VARCHAR,
    timestamp TIMESTAMP,
    details TEXT,
    previous_state TEXT,
    new_state TEXT
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT a.audit_id, a.action, a.performed_by, a.timestamp, a.details, a.previous_state, a.new_state
    FROM audit_log a
    WHERE a.transaction_id = p_transaction_id
    ORDER BY a.timestamp ASC;
END;
$$;

-- Procedimiento para ejecutar transferencia entre cuentas
CREATE OR REPLACE PROCEDURE execute_transfer(
    IN p_transaction_id UUID,
    IN p_source_client_id UUID,
    IN p_destination_client_id UUID,
    IN p_amount DECIMAL,
    IN p_description VARCHAR,
    IN p_performed_by VARCHAR,
    OUT p_result BOOLEAN,
    OUT p_error_message VARCHAR
)
LANGUAGE plpgsql
AS $$
DECLARE
    v_source_lock UUID;
    v_dest_lock UUID;
BEGIN
    p_result := false;
    p_error_message := NULL;

    -- Adquirir bloqueos en orden para evitar deadlocks (cliente con ID menor primero)
    IF p_source_client_id < p_destination_client_id THEN
        INSERT INTO transaction_lock (lock_id, resource_id, resource_type, lock_type, acquired_by, expires_at)
        VALUES (gen_random_uuid(), p_source_client_id, 'CLIENT', 'EXCLUSIVE', 'transfer_service', CURRENT_TIMESTAMP + INTERVAL '60 seconds')
        ON CONFLICT DO NOTHING RETURNING lock_id INTO v_source_lock;

        INSERT INTO transaction_lock (lock_id, resource_id, resource_type, lock_type, acquired_by, expires_at)
        VALUES (gen_random_uuid(), p_destination_client_id, 'CLIENT', 'EXCLUSIVE', 'transfer_service', CURRENT_TIMESTAMP + INTERVAL '60 seconds')
        ON CONFLICT DO NOTHING RETURNING lock_id INTO v_dest_lock;
    ELSE
        INSERT INTO transaction_lock (lock_id, resource_id, resource_type, lock_type, acquired_by, expires_at)
        VALUES (gen_random_uuid(), p_destination_client_id, 'CLIENT', 'EXCLUSIVE', 'transfer_service', CURRENT_TIMESTAMP + INTERVAL '60 seconds')
        ON CONFLICT DO NOTHING RETURNING lock_id INTO v_dest_lock;

        INSERT INTO transaction_lock (lock_id, resource_id, resource_type, lock_type, acquired_by, expires_at)
        VALUES (gen_random_uuid(), p_source_client_id, 'CLIENT', 'EXCLUSIVE', 'transfer_service', CURRENT_TIMESTAMP + INTERVAL '60 seconds')
        ON CONFLICT DO NOTHING RETURNING lock_id INTO v_source_lock;
    END IF;

    IF v_source_lock IS NULL OR v_dest_lock IS NULL THEN
        p_error_message := 'No se pudieron adquirir los bloqueos necesarios';
        RETURN;
    END IF;

    -- Verificar límites para origen
    IF NOT check_daily_limit(p_source_client_id, p_amount) THEN
        p_error_message := 'Límite diario excedido para cuenta origen';
        RETURN;
    END IF;

    -- Validar monto
    IF NOT validate_transaction_amount(p_amount) THEN
        p_error_message := 'Monto de transferencia inválido';
        RETURN;
    END IF;

    -- Crear transacción de débito
    INSERT INTO transactions (transaction_id, client_id, amount, type, description, status)
    VALUES (p_transaction_id, p_source_client_id, p_amount, 'TRANSFER', p_description || ' (Débito)', 'COMPLETED');

    -- Crear transacción de crédito
    INSERT INTO transactions (transaction_id, client_id, amount, type, description, status)
    VALUES (gen_random_uuid(), p_destination_client_id, p_amount, 'TRANSFER', p_description || ' (Crédito)', 'COMPLETED');

    -- Registrar en auditoría
    INSERT INTO audit_log (audit_id, transaction_id, action, performed_by, details, new_state)
    VALUES (gen_random_uuid(), p_transaction_id, 'TRANSFER_EXECUTED', p_performed_by,
            'Transferencia de ' || p_amount || ' de ' || p_source_client_id || ' a ' || p_destination_client_id,
            '{"status": "COMPLETED"}');

    p_result := true;

EXCEPTION
    WHEN OTHERS THEN
        p_error_message := SQLERRM;
        p_result := false;
END;
$$;

-- Índices adicionales para optimizar consultas de procedimientos
CREATE INDEX IF NOT EXISTS idx_transactions_client_date_status 
ON transactions(client_id, transaction_date, status);

CREATE INDEX IF NOT EXISTS idx_audit_log_composite 
ON audit_log(transaction_id, timestamp, action);

-- Agregar columna para tracking de retries
ALTER TABLE transactions ADD COLUMN IF NOT EXISTS retry_count INTEGER DEFAULT 0;
ALTER TABLE transactions ADD COLUMN IF NOT EXISTS last_retry_at TIMESTAMP;

// === ARCHIVO: src/test/java/com/pragma/banking/transactions/domain/service/TransactionServiceTest.java ===
package com.pragma.banking.transactions.domain.service;


import com.pragma.banking.transactions.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pragma.banking.transactions.domain.port.TransactionRepositoryPort;
import com.pragma.banking.transactions.domain.port.AuditRepositoryPort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para TransactionService - Verificación de modelo ACID")
class TransactionServiceTest {

    @Mock
    private TransactionRepositoryPort transactionRepository;

    @Mock
    private AuditRepositoryPort auditRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("verifyDatabaseConnectivity debería verificar la conexión a la base de datos")
    void verifyDatabaseConnectivity_shouldVerifyConnection() {
        doNothing().when(transactionRepository).verifyConnection();
        
        assertDoesNotThrow(() -> transactionService.verifyDatabaseConnectivity());
        verify(transactionRepository, times(1)).verifyConnection();
    }

    @Test
    @DisplayName("verifyDatabaseConnectivity debería lanzar excepción cuando la conexión falla")
    void verifyDatabaseConnectivity_shouldThrowExceptionOnConnectionFailure() {
        doThrow(new RuntimeException("Conexión a base de datos fallida"))
            .when(transactionRepository).verifyConnection();
        
        assertThrows(RuntimeException.class, () -> transactionService.verifyDatabaseConnectivity());
    }

    @Test
    @DisplayName("initializeSampleData debería crear datos de ejemplo para pruebas")
    void initializeSampleData_shouldCreateSampleData() {
        UUID clientId = UUID.randomUUID();
        when(transactionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(auditRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        
        assertDoesNotThrow(() -> transactionService.initializeSampleData());
        verify(transactionRepository, atLeastOnce()).save(any());
        verify(auditRepository, atLeastOnce()).save(any());
    }

    @Test
    @DisplayName("El servicio debería mantener atomicidad en las operaciones")
    void service_shouldMaintainAtomicity() {
        UUID transactionId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("1000.00");
        
        when(transactionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(auditRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        
        boolean success = transactionService.processTransactionWithAtomicity(
            transactionId, clientId, amount, "DEPOSIT", "Depósito inicial"
        );
        
        assertTrue(success);
        verify(transactionRepository, times(1)).save(any());
        verify(auditRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("El servicio debería rollbackear ante fallos - consistencia")
    void service_shouldRollbackOnFailure_EnsuringConsistency() {
        UUID transactionId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("5000.00");
        
        when(transactionRepository.save(any()))
            .thenAnswer(invocation -> invocation.getArgument(0))
            .thenThrow(new RuntimeException("Error de base de datos"));
        
        boolean result = transactionService.processTransactionWithAtomicity(
            transactionId, clientId, amount, "WITHDRAWAL", "Retiro"
        );
        
        assertFalse(result);
        verify(transactionRepository, atLeastOnce()).save(any());
        verify(auditRepository, never()).save(any());
    }

    @Test
    @DisplayName("El servicio debería manejar aislamiento entre transacciones concurrentes")
    void service_shouldHandleIsolationBetweenConcurrentTransactions() {
        UUID clientId1 = UUID.randomUUID();
        UUID clientId2 = UUID.randomUUID();
        BigDecimal amount1 = new BigDecimal("100.00");
        BigDecimal amount2 = new BigDecimal("200.00");
        
        when(transactionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(transactionRepository.findByClientId(clientId1)).thenReturn(java.util.List.of());
        when(transactionRepository.findByClientId(clientId2)).thenReturn(java.util.List.of());
        
        boolean result1 = transactionService.processTransactionWithAtomicity(
            UUID.randomUUID(), clientId1, amount1, "DEPOSIT", "Depósito cliente 1"
        );
        boolean result2 = transactionService.processTransactionWithAtomicity(
            UUID.randomUUID(), clientId2, amount2, "DEPOSIT", "Depósito cliente 2"
        );
        
        assertTrue(result1);
        assertTrue(result2);
    }

    @Test
    @DisplayName("El servicio debería registrar auditoría para trazabilidad - durabilidad")
    void service_shouldRegisterAudit_EnsuringDurability() {
        UUID transactionId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("1500.00");
        
        when(transactionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(auditRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        
        transactionService.processTransactionWithAtomicity(
            transactionId, clientId, amount, "TRANSFER", "Transferencia"
        );
        
        verify(auditRepository, times(1)).save(argThat(audit -> 
            audit.getTransactionId().equals(transactionId) &&
            audit.getAction().equals("TRANSACTION_CREATED")
        ));
    }
}

// === ARCHIVO: src/test/java/com/pragma/banking/transactions/application/usecase/ProcessTransactionUseCaseTest.java ===
package com.pragma.banking.transactions.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pragma.banking.transactions.domain.model.Transaction;
import com.pragma.banking.transactions.domain.model.Transaction.TransactionStatus;
import com.pragma.banking.transactions.domain.model.Transaction.TransactionType;
import com.pragma.banking.transactions.domain.model.Audit;
import com.pragma.banking.transactions.domain.port.TransactionRepositoryPort;
import com.pragma.banking.transactions.domain.port.AuditRepositoryPort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para ProcessTransactionUseCase - Validación de cumplimiento ACID")
class ProcessTransactionUseCaseTest {

    @Mock
    private TransactionRepositoryPort transactionRepository;

    @Mock
    private AuditRepositoryPort auditRepository;

    @InjectMocks
    private ProcessTransactionUseCase processTransactionUseCase;

    private Transaction testTransaction;
    private UUID transactionId;
    private UUID clientId;

    @BeforeEach
    void setUp() {
        transactionId = UUID.randomUUID();
        clientId = UUID.randomUUID();
        testTransaction = new Transaction(
            transactionId,
            clientId,
            new BigDecimal("2500.00"),
            LocalDateTime.now(),
            TransactionType.DEPOSIT,
            "Depósito de prueba"
        );
        testTransaction.setStatus(TransactionStatus.PENDING);
    }

    @Test
    @DisplayName("Execute debería procesar transacción exitosamente - Atomicidad")
    void execute_shouldProcessTransactionSuccessfully_Atomicity() {
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(auditRepository.save(any(Audit.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        Transaction result = processTransactionUseCase.execute(testTransaction);
        
        assertNotNull(result);
        assertEquals(TransactionStatus.COMPLETED, result.getStatus());
        assertTrue(result.isCompleted());
        verify(transactionRepository, times(2)).save(any(Transaction.class));
        verify(auditRepository, atLeastOnce()).save(any(Audit.class));
    }

    @Test
    @DisplayName("Execute debería rechazar transacción con monto inválido - Consistencia")
    void execute_shouldRejectTransactionWithInvalidAmount_Consistency() {
        Transaction invalidTransaction = new Transaction(
            UUID.randomUUID(),
            clientId,
            new BigDecimal("-100.00"),
            LocalDateTime.now(),
            TransactionType.WITHDRAWAL,
            "Monto negativo"
        );
        invalidTransaction.setStatus(TransactionStatus.PENDING);
        
        assertThrows(IllegalArgumentException.class, () -> processTransactionUseCase.execute(invalidTransaction));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Execute debería manejar fallo de base de datos - Atomicidad")
    void execute_shouldHandleDatabaseFailure_Atomicity() {
        when(transactionRepository.save(any(Transaction.class)))
            .thenAnswer(invocation -> invocation.getArgument(0))
            .thenThrow(new RuntimeException("Error de persistencia"));
        
        Transaction result = processTransactionUseCase.executeWithFallback(testTransaction);
        
        assertNotNull(result);
        assertEquals(TransactionStatus.FAILED, result.getStatus());
        assertTrue(result.isFailed());
    }

    @Test
    @DisplayName("Execute debería validar cliente existente - Consistencia")
    void execute_shouldValidateExistingClient_Consistency() {
        when(transactionRepository.findByClientId(clientId)).thenReturn(Optional.of(testTransaction));
        
        boolean isValid = processTransactionUseCase.validateClient(clientId);
        
        assertTrue(isValid);
        verify(transactionRepository, times(1)).findByClientId(clientId);
    }

    @Test
    @DisplayName("Execute debería rechazar cliente no existente - Consistencia")
    void execute_shouldRejectNonExistingClient_Consistency() {
        UUID nonExistingClientId = UUID.randomUUID();
        when(transactionRepository.findByClientId(nonExistingClientId)).thenReturn(Optional.empty());
        
        boolean isValid = processTransactionUseCase.validateClient(nonExistingClientId);
        
        assertFalse(isValid);
    }

    @Test
    @DisplayName("Execute debería registrar auditoría de la operación - Durabilidad")
    void execute_shouldRegisterAuditOfOperation_Durability() {
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(auditRepository.save(any(Audit.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        processTransactionUseCase.execute(testTransaction);
        
        verify(auditRepository, atLeastOnce()).save(argThat(audit -> 
            audit.getTransactionId().equals(transactionId) &&
            audit.getAction().equals("TRANSACTION_PROCESSED")
        ));
    }

    @Test
    @DisplayName("Execute debería mantener aislamiento entre transacciones")
    void execute_shouldMaintainIsolationBetweenTransactions() {
        Transaction transaction1 = new Transaction(
            UUID.randomUUID(), UUID.randomUUID(), new BigDecimal("500.00"),
            LocalDateTime.now(), TransactionType.DEPOSIT, "Transacción 1"
        );
        Transaction transaction2 = new Transaction(
            UUID.randomUUID(), UUID.randomUUID(), new BigDecimal("800.00"),
            LocalDateTime.now(), TransactionType.WITHDRAWAL, "Transacción 2"
        );
        
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(auditRepository.save(any(Audit.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        Transaction result1 = processTransactionUseCase.execute(transaction1);
        Transaction result2 = processTransactionUseCase.execute(transaction2);
        
        assertNotNull(result1);
        assertNotNull(result2);
        assertNotEquals(result1.getTransactionId(), result2.getTransactionId());
        assertEquals(TransactionStatus.COMPLETED, result1.getStatus());
        assertEquals(TransactionStatus.COMPLETED, result2.getStatus());
    }

    @Test
    @DisplayName("Execute debería aplicar retry en caso de fallo transitorio")
    void execute_shouldApplyRetryOnTransientFailure() {
        when(transactionRepository.save(any(Transaction.class)))
            .thenThrow(new RuntimeException("Fallo transitorio"))
            .thenThrow(new RuntimeException("Fallo transitorio"))
            .thenAnswer(invocation -> invocation.getArgument(0));
        when(auditRepository.save(any(Audit.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        Transaction result = processTransactionUseCase.executeWithRetry(testTransaction);
        
        assertNotNull(result);
        verify(transactionRepository, times(3)).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Execute debería usar circuit breaker para operaciones críticas")
    void execute_shouldUseCircuitBreakerForCriticalOperations() {
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(auditRepository.save(any(Audit.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        boolean circuitClosed = processTransactionUseCase.isCircuitBreakerClosed();
        
        assertTrue(circuitClosed);
        verify(transactionRepository, times(0)).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Execute debería validar tipo de transacción válido")
    void execute_shouldValidateTransactionType() {
        Transaction depositTransaction = new Transaction(
            UUID.randomUUID(), clientId, new BigDecimal("1000.00"),
            LocalDateTime.now(), TransactionType.DEPOSIT, "Depósito"
        );
        
        Transaction transferTransaction = new Transaction(
            UUID.randomUUID(), clientId, new BigDecimal("500.00"),
            LocalDateTime.now(), TransactionType.TRANSFER, "Transferencia"
        );
        
        assertTrue(processTransactionUseCase.isValidTransactionType(depositTransaction.getType()));
        assertTrue(processTransactionUseCase.isValidTransactionType(transferTransaction.getType()));
    }
}
```
