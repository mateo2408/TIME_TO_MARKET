# Fase 5: Testing y Documentación

## Objetivo
Validar que la aplicación funciona de punta a punta después de la configuración de MySQL, la creación del esquema y la migración de datos desde HSQLDB, y dejar evidencia clara del resultado.

## Alcance de las pruebas funcionales básicas

### 1) Arranque de la base de datos
**Objetivo:** confirmar que MySQL levanta correctamente y queda disponible para la aplicación.

**Comando usado:**
```bash
docker compose up -d mysql mysql-client
docker compose ps
```

**Criterio de éxito:**
- El contenedor `mysql` queda en estado `healthy`.
- El contenedor `mysql-client` queda disponible para ejecutar comandos.

---

### 2) Arranque de la aplicación
**Objetivo:** verificar que el front de OpenXava inicia sin errores y se conecta al datasource configurado en `src/main/webapp/META-INF/context.xml`.

**Método de verificación:**
- Ejecutar la clase `com.yourcompany.untitled.run.untitled` desde el IDE o desplegar el WAR en Tomcat.
- Confirmar que el login y los módulos de la aplicación cargan correctamente.

**Criterio de éxito:**
- La aplicación abre en el navegador.
- No aparecen errores de conexión a la base de datos.
- Los módulos de la aplicación son visibles.

---

### 3) Creación de esquema y tablas
**Objetivo:** comprobar que Hibernate/OpenXava crea las tablas automáticamente en MySQL.

**Verificación:**
```bash
docker compose exec -T mysql-client mysql -h mysql -uuntitled_app -puntitled_app_password --database=untitleddb -e "SHOW TABLES;"
```

**Criterio de éxito:**
- Se listan tablas como `Customer`, `Product`, `Category`, `CommercialDocument` y `Invoice_details`.

---

### 4) Migración de datos desde HSQLDB
**Objetivo:** cargar en MySQL los datos existentes en `data/untitled-db.script`.

**Comando usado:**
```bash
docker compose exec -T mysql-client mysql -h mysql -uuntitled_app -puntitled_app_password --database=untitleddb < docker/mysql/migrate-hsqldb.sql
```

**Criterio de éxito:**
- El comando termina sin errores.
- Los registros de ejemplo aparecen en las tablas correspondientes.

**Verificación posterior:**
```bash
docker compose exec -T mysql-client mysql -h mysql -uuntitled_app -puntitled_app_password --database=untitleddb -e "SELECT COUNT(*) AS customers FROM Customer; SELECT COUNT(*) AS products FROM Product; SELECT COUNT(*) AS invoices FROM Invoice;"
```

---

### 5) Prueba funcional CRUD básica
**Objetivo:** validar que la base acepta operaciones de lectura, inserción, actualización y eliminación.

**Script de referencia:** `docker/mysql/crud-smoke.sql`

**Comando usado:**
```bash
docker compose exec -T mysql-client mysql -h mysql -uuntitled_app -puntitled_app_password --database=untitleddb < docker/mysql/crud-smoke.sql
```

**Criterio de éxito:**
- El script se ejecuta correctamente.
- La prueba deja el entorno limpio o restaura el estado inicial según el script.

---

## Captura de evidencias finales

Guardar las imágenes en una carpeta como `docs/images/` o `docs/evidence/`.

### Evidencias sugeridas
1. **Arranque de contenedores**
   - Captura de `docker compose ps` mostrando MySQL en estado saludable.

2. **Pantalla de login / front de OpenXava**
   - Captura de la aplicación abierta en el navegador.

3. **Listado de módulos o menú principal**
   - Captura del front mostrando que la navegación funciona.

4. **Consulta de tablas**
   - Captura del resultado de `SHOW TABLES;`.

5. **Consulta de datos migrados**
   - Captura del resultado de `SELECT COUNT(*) ...` o de un `SELECT` con filas reales.

6. **CRUD smoke test**
   - Captura de la ejecución del script `crud-smoke.sql` sin errores.

### Formato recomendado para cada screenshot
- Nombre del archivo: `01-arranque-mysql.png`, `02-login-front.png`, etc.
- Título breve
- Fecha
- Descripción de lo que se observa

---

## Resultados esperados / resumen de validación

| Prueba | Estado | Observación |
|---|---:|---|
| Arranque de MySQL | OK | Contenedor levantado y accesible |
| Arranque del front | OK | La aplicación carga correctamente |
| Creación de tablas | OK | Hibernate genera el esquema |
| Migración de datos | OK | Los registros se insertan en MySQL |
| CRUD básico | OK | Operaciones válidas en la base |

---

## Conclusiones
- Se confirmó que la base de datos de producción local es MySQL y que el esquema se genera automáticamente al iniciar la aplicación.
- Se verificó que los datos históricos de HSQLDB no se migran solos: deben cargarse explícitamente con el script de migración.
- Se validó que el front de OpenXava depende de que la aplicación esté apuntando al datasource correcto.
- La separación entre arranque de MySQL, arranque del front y migración de datos permite detectar con claridad fallos de configuración.
- Como mejora futura, conviene automatizar la validación con un pipeline o un script de verificación único.

---

## Comandos de referencia rápida
```bash
docker compose up -d mysql mysql-client
docker compose ps
docker compose exec -T mysql-client mysql -h mysql -uuntitled_app -puntitled_app_password --database=untitleddb -e "SHOW TABLES;"
docker compose exec -T mysql-client mysql -h mysql -uuntitled_app -puntitled_app_password --database=untitleddb < docker/mysql/migrate-hsqldb.sql
docker compose exec -T mysql-client mysql -h mysql -uuntitled_app -puntitled_app_password --database=untitleddb < docker/mysql/crud-smoke.sql
```

---

## Nota final
Este documento sirve como base para la entrega de la fase 5. Solo falta añadir las capturas de pantalla reales y, si se desea, completar la columna de observaciones con resultados concretos obtenidos durante la ejecución.
