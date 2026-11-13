# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Implementación de Control de Transacciones y Aplicación del Modelo ACID**.

| | |
|---|---|
| Tema | Diseño e Implementación de un Sistema de Transacciones Robusto y Eficiente |
| Nivel | senior-l2 |
| Chapter | Backend |
| Especialidad | Java |
| Stack | Java / Spring Boot 3.5 |
| Patron arquitectonico | hexagonal/clean |
| Tiempo estimado | 20 horas |

## Receta del stack

Esqueleto obligatorio:

- `pom.xml en la raiz`
- `clase con @SpringBootApplication`
- `application.yml en src/main/resources`
- `capa de dominio con entidades y puertos`
- `capa de aplicacion con casos de uso`
- `capa de infraestructura con adaptadores y @RestController`

Trampas conocidas:

- TODA `<version>` del pom va con tres segmentos: la del parent (ej. `3.5.6`) y la de cada dependencia que la lleve (ej. Resilience4j `2.2.0`). `3.4` y `2.0` no existen como artefacto y el build muere resolviendo dependencias.
- Las dependencias que el parent POM gestiona van SIN `<version>`: `spring-boot-starter-web`, `-data-jpa`, `-validation`, `-test`, etc.
- Resilience4j publica un artefacto por linea de Spring Boot. Con Spring Boot 3 va `resilience4j-spring-boot3` con version de tres segmentos (ej. `2.2.0`, no `2.0`). `resilience4j-spring-boot2` es de Spring Boot 2 y rompe el arranque.
- Si usas anotaciones de validacion (`@NotNull`, `@Size`, `@Positive`) declara `spring-boot-starter-validation`: el starter web no las trae.
- El `spring-boot-maven-plugin` tiene que estar en `<build><plugins>` o no se empaqueta ejecutable.
- Spring Boot 3 usa `jakarta.*`, nunca `javax.*`.
- Cada archivo empieza con su `package` y con un `import` por cada clase del proyecto que viva en otro paquete. Usar `PaymentService` desde `infrastructure` sin `import com.x.application.PaymentService` no compila.

Dependencias:

- org.springframework.boot:spring-boot-starter-web 3.5.6
- org.springframework.boot:spring-boot-starter-data-jpa n/a
- org.springframework.boot:spring-boot-starter-validation n/a
- org.postgresql:postgresql 42.7.3
- org.springframework.boot:spring-boot-starter-actuator n/a
- io.github.resilience4j:resilience4j-spring-boot3 2.2.0
- org.springframework.boot:spring-boot-starter-aop n/a
- org.flywaydb:flyway-core 10.10.0
- org.springdoc:springdoc-openapi-starter-webmvc-ui 2.9.1
- org.projectlombok:lombok 1.18.34
- org.springframework.boot:spring-boot-starter-test n/a
- org.junit.jupiter:junit-jupiter-api n/a
- org.mockito:mockito-core 5.11.0

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `mvn clean compile` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `mvn clean compile` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Configuración del Entorno y Creación de la Base de Datos**: Base de datos configurada y tablas creadas.
- **Fase 2 — Implementación del Control de Transacciones**: Control de transacciones implementado y optimizado.
- **Fase 3 — Aplicación del Modelo ACID**: Sistema de transacciones que cumple con el modelo ACID y documentación de decisiones de diseño.
- **Fase 4 — Evaluación y Optimización**: Informe de evaluación y optimización del sistema de transacciones.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Lo que falta y tenes que completar

### 1. Referencias colgando (35)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/main/java/com/pragma/banking/transactions/domain/service/TransactionService.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/banking/transactions/application/usecase/ProcessTransactionUseCase.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/banking/transactions/infrastructure/controller/TransactionController.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/banking/transactions/application/usecase/ProcessTransactionUseCase.java` — `TransactionStatus.name`
      Se invoca `name` sobre `TransactionStatus`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/TransactionJpaAdapter.java` — `TransactionJpaRepository.save`
      Se invoca `save` sobre `TransactionJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/TransactionJpaAdapter.java` — `TransactionJpaRepository.findById`
      Se invoca `findById` sobre `TransactionJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/TransactionJpaAdapter.java` — `TransactionJpaRepository.findAll`
      Se invoca `findAll` sobre `TransactionJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/TransactionJpaAdapter.java` — `TransactionStatus.name`
      Se invoca `name` sobre `TransactionStatus`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/TransactionJpaAdapter.java` — `TransactionJpaRepository.findByTransactionDateBetween`
      Se invoca `findByTransactionDateBetween` sobre `TransactionJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/TransactionJpaAdapter.java` — `TransactionJpaRepository.sumAmountByClientIdAndDateRange`
      Se invoca `sumAmountByClientIdAndDateRange` sobre `TransactionJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/TransactionJpaAdapter.java` — `TransactionJpaRepository.deleteById`
      Se invoca `deleteById` sobre `TransactionJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/TransactionJpaAdapter.java` — `TransactionJpaRepository.existsById`
      Se invoca `existsById` sobre `TransactionJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/AuditJpaAdapter.java` — `AuditJpaRepository.save`
      Se invoca `save` sobre `AuditJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/AuditJpaAdapter.java` — `AuditJpaRepository.findById`
      Se invoca `findById` sobre `AuditJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/AuditJpaAdapter.java` — `AuditJpaRepository.findAll`
      Se invoca `findAll` sobre `AuditJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/AuditJpaAdapter.java` — `AuditJpaRepository.findByAction`
      Se invoca `findByAction` sobre `AuditJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/AuditJpaAdapter.java` — `AuditJpaRepository.deleteById`
      Se invoca `deleteById` sobre `AuditJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/AuditJpaAdapter.java` — `AuditJpaRepository.existsById`
      Se invoca `existsById` sobre `AuditJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/banking/transactions/infrastructure/controller/TransactionController.java` — `TransactionRequest.type`
      Se invoca `type` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/banking/transactions/infrastructure/controller/TransactionController.java` — `TransactionRequest.amount`
      Se invoca `amount` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/banking/transactions/infrastructure/controller/TransactionController.java` — `TransactionRequest.clientId`
      Se invoca `clientId` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/banking/transactions/infrastructure/controller/TransactionController.java` — `TransactionRequest.description`
      Se invoca `description` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/banking/transactions/infrastructure/controller/TransactionController.java` — `ProcessTransactionUseCase.execute`
      Se invoca `execute` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/banking/transactions/infrastructure/controller/TransactionController.java` — `ProcessTransactionUseCase.findById`
      Se invoca `findById` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/banking/transactions/infrastructure/controller/TransactionController.java` — `ProcessTransactionUseCase.findByFilters`
      Se invoca `findByFilters` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/banking/transactions/infrastructure/controller/TransactionController.java` — `ProcessTransactionUseCase.cancelTransaction`
      Se invoca `cancelTransaction` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/banking/transactions/infrastructure/controller/TransactionController.java` — `TransactionRequest.reason`
      Se invoca `reason` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/banking/transactions/infrastructure/controller/TransactionController.java` — `ProcessTransactionUseCase.reverseTransaction`
      Se invoca `reverseTransaction` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/banking/transactions/domain/service/TransactionServiceTest.java` — `TransactionService.processTransactionWithAtomicity`
      Se invoca `processTransactionWithAtomicity` sobre `TransactionService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/banking/transactions/application/usecase/ProcessTransactionUseCaseTest.java` — `ProcessTransactionUseCase.execute`
      Se invoca `execute` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/banking/transactions/application/usecase/ProcessTransactionUseCaseTest.java` — `ProcessTransactionUseCase.executeWithFallback`
      Se invoca `executeWithFallback` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/banking/transactions/application/usecase/ProcessTransactionUseCaseTest.java` — `ProcessTransactionUseCase.validateClient`
      Se invoca `validateClient` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/banking/transactions/application/usecase/ProcessTransactionUseCaseTest.java` — `ProcessTransactionUseCase.executeWithRetry`
      Se invoca `executeWithRetry` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/banking/transactions/application/usecase/ProcessTransactionUseCaseTest.java` — `ProcessTransactionUseCase.isCircuitBreakerClosed`
      Se invoca `isCircuitBreakerClosed` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/banking/transactions/application/usecase/ProcessTransactionUseCaseTest.java` — `ProcessTransactionUseCase.isValidTransactionType`
      Se invoca `isValidTransactionType` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

### Presentes (23)

- `pom.xml`
- `src/main/java/com/pragma/banking/transactions/TransactionsApplication.java`
- `src/main/resources/application.yml`
- `src/main/java/com/pragma/banking/transactions/domain/model/Client.java`
- `src/main/java/com/pragma/banking/transactions/domain/model/Transaction.java`
- `src/main/java/com/pragma/banking/transactions/domain/model/Audit.java`
- `src/main/java/com/pragma/banking/transactions/domain/port/TransactionRepositoryPort.java`
- `src/main/java/com/pragma/banking/transactions/domain/port/AuditRepositoryPort.java`
- `src/main/java/com/pragma/banking/transactions/domain/service/TransactionService.java`
- `src/main/java/com/pragma/banking/transactions/infrastructure/repository/TransactionJpaRepository.java`
- `src/main/java/com/pragma/banking/transactions/infrastructure/repository/AuditJpaRepository.java`
- `src/main/java/com/pragma/banking/transactions/application/usecase/ProcessTransactionUseCase.java`
- `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/TransactionJpaAdapter.java`
- `src/main/java/com/pragma/banking/transactions/infrastructure/adapter/AuditJpaAdapter.java`
- `src/main/java/com/pragma/banking/transactions/infrastructure/entity/ClientEntity.java`
- `src/main/java/com/pragma/banking/transactions/infrastructure/entity/TransactionEntity.java`
- `src/main/java/com/pragma/banking/transactions/infrastructure/entity/AuditEntity.java`
- `src/main/java/com/pragma/banking/transactions/infrastructure/controller/TransactionController.java`
- `src/main/java/com/pragma/banking/transactions/infrastructure/config/TransactionConfig.java`
- `src/main/resources/db/migration/V1__Create_tables.sql`
- `src/main/resources/db/migration/V2__Add_procedures.sql`
- `src/test/java/com/pragma/banking/transactions/domain/service/TransactionServiceTest.java`
- `src/test/java/com/pragma/banking/transactions/application/usecase/ProcessTransactionUseCaseTest.java`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/java/com/pragma/banking/transactions`
- `src/main/java/com/pragma/banking/transactions/domain`
- `src/main/java/com/pragma/banking/transactions/domain/model`
- `src/main/java/com/pragma/banking/transactions/domain/port`
- `src/main/java/com/pragma/banking/transactions/domain/service`
- `src/main/java/com/pragma/banking/transactions/application`
- `src/main/java/com/pragma/banking/transactions/application/usecase`
- `src/main/java/com/pragma/banking/transactions/infrastructure`
- `src/main/java/com/pragma/banking/transactions/infrastructure/adapter`
- `src/main/java/com/pragma/banking/transactions/infrastructure/config`
- `src/main/java/com/pragma/banking/transactions/infrastructure/controller`
- `src/main/java/com/pragma/banking/transactions/infrastructure/entity`
- `src/main/java/com/pragma/banking/transactions/infrastructure/repository`
- `src/main/resources`
- `src/test/java/com/pragma/banking/transactions`

## Verificacion

```bash
mvn clean compile
```

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **hexagonal/clean**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Perfil: Chapter Backend, Especialidad Desarrollador, Tecnología Java, Senior L2
- Brecha que el reto ataca: Implementa control de transacciones (TCL) en RDBMS y en el framework, aplicando optimizaciones mediante procedimientos almacenados, índices, etc. Asimismo, asegura la aplicación del modelo ACID (Atomicidad, Consistencia, Aislamiento y Durabilidad) en las transacciones., Implementa control de transacciones (TCL) en RDBMS y en el framework, aplicando optimizaciones mediante procedimientos almacenados, índices, etc. Asimismo, asegura la aplicación del modelo ACID (Atomicidad, Consistencia, Aislamiento y Durabilidad) en las transacciones.

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
