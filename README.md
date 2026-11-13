# Implementación de Control de Transacciones y Aplicación del Modelo ACID

El equipo de desarrollo de una plataforma de banca en línea necesita implementar un sistema robusto y eficiente para manejar transacciones financieras. El sistema debe asegurar que todas las transacciones cumplan con el modelo ACID (Atomicidad, Consistencia, Aislamiento y Durabilidad). Los actores involucrados son los Clientes Bancarios, el Sistema de Pagos y el Entorno de Auditoría. El sistema debe manejar volúmenes pico de 10,000 transacciones por minuto y garantizar una disponibilidad del 99.9%.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | Diseño e Implementación de un Sistema de Transacciones Robusto y Eficiente |
| **Nivel** | senior-l2 |
| **Tipo** | practical |
| **Tiempo estimado** | 20 horas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Configuración del Entorno y Creación de la Base de Datos

**Objetivo:** Configurar el entorno de desarrollo y crear la estructura de la base de datos para soportar transacciones.

**Tiempo estimado:** 4 horas

**Instrucciones:**

- Configura el entorno de desarrollo para trabajar con Java y una RDBMS.
- Crea la estructura de la base de datos incluyendo tablas para Clientes, Transacciones y Auditoría.
- Asegura que la base de datos pueda manejar el volumen pico de transacciones.

**Entregable:** Base de datos configurada y tablas creadas.

<details>
<summary>Pistas de conocimiento</summary>

- Configuración de entornos de desarrollo para Java y RDBMS.
- Diseño de tablas para soportar transacciones financieras.

</details>

### Fase 2: Implementación del Control de Transacciones

**Objetivo:** Implementar el control de transacciones en la RDBMS y en el framework, aplicando optimizaciones.

**Tiempo estimado:** 8 horas

**Instrucciones:**

- Implementa el control de transacciones utilizando TCL en la RDBMS.
- Aplica optimizaciones mediante procedimientos almacenados e índices.
- Asegura que las transacciones cumplan con el modelo ACID.

**Entregable:** Control de transacciones implementado y optimizado.

<details>
<summary>Pistas de conocimiento</summary>

- Uso de TCL en RDBMS.
- Optimización de transacciones mediante procedimientos almacenados e índices.
- Aplicación del modelo ACID en transacciones.

</details>

### Fase 3: Aplicación del Modelo ACID

**Objetivo:** Asegurar que todas las transacciones cumplan con el modelo ACID.

**Tiempo estimado:** 6 horas

**Instrucciones:**

- Verifica que todas las transacciones cumplan con las propiedades de Atomicidad, Consistencia, Aislamiento y Durabilidad.
- Maneja posibles fallos y asegura la recuperación de transacciones.
- Documenta las decisiones de diseño y trade-offs realizados.

**Entregable:** Sistema de transacciones que cumple con el modelo ACID y documentación de decisiones de diseño.

<details>
<summary>Pistas de conocimiento</summary>

- Verificación de cumplimiento del modelo ACID.
- Manejo de fallos y recuperación de transacciones.
- Documentación de decisiones de diseño y trade-offs.

</details>

### Fase 4: Evaluación y Optimización

**Objetivo:** Evaluar el rendimiento del sistema y aplicar optimizaciones adicionales si es necesario.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- Realiza pruebas de rendimiento para evaluar el sistema de transacciones.
- Identifica áreas de mejora y aplica optimizaciones adicionales.
- Documenta los resultados de las pruebas y las optimizaciones realizadas.

**Entregable:** Informe de evaluación y optimización del sistema de transacciones.

<details>
<summary>Pistas de conocimiento</summary>

- Realización de pruebas de rendimiento.
- Identificación de áreas de mejora.
- Documentación de resultados y optimizaciones.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué es el control de transacciones y cómo se implementa en una RDBMS?
- **paraQueSirve**: ¿Para qué sirve el modelo ACID en las transacciones?
- **comoSeUsa**: ¿Cómo se aplican optimizaciones mediante procedimientos almacenados e índices?
- **erroresComunes**: ¿Cuáles son los errores comunes al implementar el control de transacciones?
- **queDecisionesImplica**: ¿Qué decisiones de diseño y trade-offs implica la implementación del modelo ACID?

## Criterios de Evaluacion

- Configuración correcta del entorno de desarrollo y creación de la estructura de la base de datos.
- Implementación del control de transacciones en la RDBMS y en el framework, aplicando optimizaciones.
- Cumplimiento del modelo ACID en todas las transacciones.
- Manejo de posibles fallos y recuperación de transacciones.
- Documentación de decisiones de diseño y trade-offs realizados.
- Evaluación y optimización del rendimiento del sistema de transacciones.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
mvn clean compile
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
