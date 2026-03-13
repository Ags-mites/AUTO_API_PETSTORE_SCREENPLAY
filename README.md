# QA REST Screenplay

Proyecto de automatización de pruebas para APIs REST usando el patrón **Screenplay** con **Serenity BDD** y **Cucumber**.

## Tecnologías

- Java 17
- Gradle
- Serenity BDD
- Cucumber
- JUnit 5
- Rest Assured

## Prerrequisitos

- JDK 17 instalado
- Gradle Wrapper incluido en el proyecto (`gradlew`)

## Ejecución de pruebas

Desde la raíz del proyecto:

### Linux / macOS

```bash
./gradlew clean test aggregate
```

### Windows (PowerShell / CMD)

```bash
gradlew.bat clean test aggregate
```

Notas:
- `clean`: limpia artefactos previos
- `test`: ejecuta los tests automatizados
- `aggregate`: genera el reporte consolidado de Serenity

## Ver reporte

Luego de la ejecución, abre el reporte HTML en:

- `target/site/serenity/index.html`
