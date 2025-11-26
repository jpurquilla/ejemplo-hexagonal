# Proyecto Hexagonal: Gestión de Personas

## 📋 Descripción

Sistema de gestión de personas implementado con **Arquitectura Hexagonal (Clean Architecture)** usando **Quarkus**, **Panache**, **MapStruct** y **H2**.

El proyecto demuestra cómo construir una aplicación escalable, mantenible y agnóstica a la tecnología, separando claramente la lógica de negocio de los detalles técnicos.

---

## 🏗️ Arquitectura

### **3 Capas (Hexagonos) Independientes**
```
┌─────────────────────────────────────┐
│    FRAMEWORK (Técnico)              │
│  REST, BD, HTTP, Quarkus            │
└─────────────────────────────────────┘
            ↕ (Interfaces)
┌─────────────────────────────────────┐
│    APPLICATION (Lógica)             │
│  Use Cases, Servicio, Puertos       │
│  ❌ Sin tecnología, sin anotaciones │
└─────────────────────────────────────┘
            ↕ (Interfaces)
┌─────────────────────────────────────┐
│    DOMAIN (Reglas de Negocio)       │
│  Entidades, Value Objects           │
│  ❌ Sin dependencias externas       │
└─────────────────────────────────────┘
```

---

## 📁 Estructura del Proyecto
```
src/main/java/dev/davivieira/

domain/                          ← CAPA 1: DOMINIO PURO
├── entity/
│   └── Persona.java            (Entity de dominio, sin @Entity JPA)
├── vo/
│   └── Sexo.java               (Enum - Value Object)
└── exception/
    ├── PersonaValidationException.java
    ├── PersonaNotFoundException.java
    └── PersonaBusinessException.java

application/                     ← CAPA 2: LÓGICA DE NEGOCIO
├── ports/
│   ├── input/
│   │   └── PersonaInputPort.java       (Puerto de entrada - interfaz)
│   └── output/
│       └── PersonaRepository.java      (Puerto de salida - interfaz)
├── usecases/                   (6 casos de uso puros)
│   ├── CreatePersonaUseCase.java
│   ├── FindPersonaByIdUseCase.java
│   ├── UpdatePersonaUseCase.java
│   ├── FindAllPersonasUseCase.java
│   ├── FindPersonaBySexoUseCase.java
│   └── DeletePersonaUseCase.java
└── service/
    └── PersonaService.java     (Orquesta los Use Cases)

framework/                       ← CAPA 3: FRAMEWORK TÉCNICO
├── config/
│   └── ApplicationConfig.java  (Configuración de inyección)
└── adapters/
    ├── input/                  (Adaptador de entrada - REST)
    │   ├── controller/
    │   │   └── PersonaController.java
    │   ├── dto/
    │   │   ├── PersonaRequestDTO.java
    │   │   └── PersonaResponseDTO.java
    │   └── mapper/
    │       └── PersonaInputMapper.java
    ├── output/                 (Adaptador de salida - BD)
    │   ├── persistence/
    │   │   ├── PersonaJpaEntity.java
    │   │   ├── PersonaJpaEntityRepository.java
    │   │   └── PersonaRepositoryAdapter.java
    │   └── mapper/
    │       └── PersonaOutputMapper.java
    └── exception/
        ├── ErrorResponseDTO.java
        └── GlobalExceptionHandler.java
```

---

## 🚀 Características

✅ **Separación de responsabilidades** - Dominio, lógica y framework independientes  
✅ **6 Casos de uso** - CRUD completo (Crear, Leer, Actualizar, Eliminar, Listar, Filtrar)  
✅ **Puertos y Adaptadores** - Agnóstico a tecnología  
✅ **Mapeo automático** - MapStruct convierte entre DTOs, Dominio y JPA  
✅ **Validación en 2 niveles** - DTOs (@NotBlank, @Min) + Dominio (reglas de negocio)  
✅ **Manejo centralizado de excepciones** - GlobalExceptionHandler  
✅ **Constructor injection** - Mejor práctica de inyección de dependencias  
✅ **Transacciones** - @Transactional en el controller

---

## 🛠️ Tecnologías

| Tecnología | Versión | Propósito |
|-----------|---------|----------|
| **Quarkus** | 3.27.1 | Framework REST |
| **Panache** | Latest | Simplificación JPA |
| **H2 Database** | Latest | Base de datos en memoria |
| **MapStruct** | Latest | Mapeo de objetos |
| **Lombok** | Latest | Reducción de código boilerplate |
| **Jakarta EE** | Latest | Estándares JEE (REST, JPA, CDI) |
| **Maven** | 3.9+ | Build tool |

---

## 📦 Dependencias Principales
```xml
<dependencies>
    <!-- Quarkus -->
    <dependency>
        <groupId>io.quarkus</groupId>
        <artifactId>quarkus-resteasy-reactive</artifactId>
    </dependency>
    
    <!-- Panache (Simplificación JPA) -->
    <dependency>
        <groupId>io.quarkus</groupId>
        <artifactId>quarkus-hibernate-orm-panache</artifactId>
    </dependency>
    
    <!-- H2 Database -->
    <dependency>
        <groupId>io.quarkus</groupId>
        <artifactId>quarkus-jdbc-h2</artifactId>
    </dependency>
    
    <!-- MapStruct -->
    <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct</artifactId>
        <version>1.5.5.Final</version>
    </dependency>
    
    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

---

## 🚀 Instalación y Ejecución

### **Requisitos**
- Java 21+
- Maven 3.9+
- Git

### **Pasos**

1. **Clonar el repositorio**
```bash
git clone <URL-DEL-REPOSITORIO>
cd ejemplo-hexagonal
```

2. **Compilar el proyecto**
```bash
mvn clean compile
```

3. **Ejecutar en modo desarrollo**
```bash
mvn quarkus:dev
```

4. **Compilar para producción**
```bash
mvn clean package -DskipTests
java -jar target/quarkus-app/quarkus-run.jar
```

La aplicación estará disponible en: `http://localhost:8080`

---

## 📡 API REST

### **Crear Persona**
```http
POST /api/personas
Content-Type: application/json

{
  "nombre": "Juan",
  "apellido": "Pérez",
  "edad": 30,
  "sexo": "M"
}
```

**Respuesta (201 Created):**
```json
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "edad": 30,
  "sexo": "M"
}
```

---

### **Buscar por ID**
```http
GET /api/personas/1
```

**Respuesta (200 OK):**
```json
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "edad": 30,
  "sexo": "M"
}
```

---

### **Actualizar Persona**
```http
PUT /api/personas/1
Content-Type: application/json

{
  "nombre": "Juan Carlos",
  "apellido": "Pérez López",
  "edad": 31,
  "sexo": "M"
}
```

**Respuesta (200 OK):**
```json
{
  "id": 1,
  "nombre": "Juan Carlos",
  "apellido": "Pérez López",
  "edad": 31,
  "sexo": "M"
}
```

---

### **Obtener todas las Personas**
```http
GET /api/personas
```

**Respuesta (200 OK):**
```json
[
  {
    "id": 1,
    "nombre": "Juan",
    "apellido": "Pérez",
    "edad": 30,
    "sexo": "M"
  },
  {
    "id": 2,
    "nombre": "María",
    "apellido": "García",
    "edad": 28,
    "sexo": "F"
  }
]
```

---

### **Buscar por Sexo**
```http
GET /api/personas/por-sexo/M
```

**Respuesta (200 OK):**
```json
[
  {
    "id": 1,
    "nombre": "Juan",
    "apellido": "Pérez",
    "edad": 30,
    "sexo": "M"
  }
]
```

---

### **Eliminar Persona**
```http
DELETE /api/personas/1
```

**Respuesta (204 No Content)**

---

## ⚠️ Códigos de Error

### **400 Bad Request - Validación fallida**
```json
{
  "status": 400,
  "error": "Validation Failed",
  "message": "Validación fallida en 'edad': Debe estar entre 0 y 150",
  "timestamp": "2025-11-21T10:30:45.123456",
  "path": "/api/personas",
  "details": null
}
```

### **404 Not Found - Persona no encontrada**
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Persona con ID 999 no encontrada",
  "timestamp": "2025-11-21T10:30:45.123456",
  "path": "/api/personas/999",
  "details": null
}
```

### **400 Bad Request - Violación de restricciones**
```json
{
  "status": 400,
  "error": "Validation Failed",
  "message": "Los datos enviados no cumplen con las validaciones requeridas",
  "timestamp": "2025-11-21T10:30:45.123456",
  "path": "/api/personas",
  "details": [
    "nombre: No puede estar vacío",
    "edad: Debe estar entre 0 y 150"
  ]
}
```

---

## 🔄 Flujo de Petición
```
Cliente REST
    ↓
PersonaController (Framework)
├─ Valida DTO
├─ Mapea DTO → Persona (Dominio)
├─ Llama PersonaService
    ↓
PersonaService (Application)
├─ Orquesta Use Case
    ↓
CreatePersonaUseCase (Application)
├─ Valida Persona (Dominio)
├─ Persiste usando PersonaRepository
    ↓
PersonaRepositoryAdapter (Framework)
├─ Mapea Persona → PersonaJpaEntity
├─ Persiste en BD con Panache
    ↓
Base de Datos H2
    ↓
(Respuesta inversa)
    ↓
Cliente recibe Response 201 + PersonaResponseDTO
```

---

## 🔐 Validaciones

### **Nivel 1: DTOs (Formato)**
- `@NotBlank` - Nombre y apellido no vacíos
- `@Min(0)` - Edad mínima 0
- `@Max(150)` - Edad máxima 150
- `@Pattern("[MF]")` - Sexo solo M o F

### **Nivel 2: Dominio (Reglas de negocio)**
- Nombre: mínimo 2 caracteres
- Apellido: mínimo 2 caracteres
- Edad: entre 0 y 150
- Sexo: MASCULINO o FEMENINO (Enum)

---

## 📚 Conceptos Clave

### **Arquitectura Hexagonal**
Separa la aplicación en 3 capas independientes comunicadas por interfaces (puertos).

### **Puertos**
- **Input**: PersonaInputPort (¿Qué puedo hacer?)
- **Output**: PersonaRepository (¿Dónde persisto?)

### **Adaptadores**
- **Input**: PersonaController (REST)
- **Output**: PersonaRepositoryAdapter (BD)

### **Use Cases**
Lógica de negocio pura. Cada uno es responsable de UN caso de uso.

### **Value Objects**
Sexo como Enum garantiza que solo existan M o F.

### **Mappers**
- PersonaInputMapper: DTO ↔ Dominio
- PersonaOutputMapper: Dominio ↔ JPA

---

## 🧪 Testing (Próximamente)
```bash
# Ejecutar tests unitarios
mvn test

# Ejecutar tests de integración
mvn verify

# Cobertura de código
mvn jacoco:report
```

---

## 📖 Estructura Conceptual

### **Domain (Puro)**
- ❌ Sin @Entity, @Table, @Column
- ❌ Sin @Inject, @ApplicationScoped
- ❌ Sin dependencias externas
- ✅ Solo lógica de negocio

### **Application (Lógica)**
- ❌ Sin anotaciones técnicas
- ❌ Sin acceso a BD
- ✅ Use Cases puros
- ✅ Servicio que orquesta

### **Framework (Técnico)**
- ✅ @ApplicationScoped, @Inject
- ✅ @Entity, @Table, @Column
- ✅ @Path, @POST, @GET
- ✅ Todas las anotaciones de Quarkus

---

## 🎯 Beneficios

✅ **Testeable** - Puedes testear cada capa independientemente  
✅ **Mantenible** - Código organizado y claro  
✅ **Escalable** - Fácil agregar nuevas funcionalidades  
✅ **Agnóstico** - Si cambias de tecnología, el dominio no se afecta  
✅ **Reutilizable** - El dominio puede usarse en CLI, gRPC, etc.

---

## 📝 Convenciones

- **Clases**: PascalCase (PersonaController)
- **Métodos**: camelCase (crear, buscarPorId)
- **Variables**: camelCase (personaRepository)
- **Paquetes**: minúsculas (domain, application, framework)
- **Archivos**: NombreClase.java (Persona.java)

---

## 🤝 Contribuir

1. Fork el proyecto
2. Crea una rama (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request



---

## 👨‍💻 Autor

Desarrollado como ejemplo de **Arquitectura Hexagonal con Quarkus**.

---

## 🔗 Referencias

- [Arquitectura Hexagonal - Alistair Cockburn](https://alistair.cockburn.us/hexagonal-architecture/)
- [Quarkus Documentation](https://quarkus.io/guides/)
- [Panache Documentation](https://quarkus.io/guides/hibernate-orm-panache)
- [MapStruct Documentation](https://mapstruct.org/)

---

## ❓ Preguntas Frecuentes

### **¿Por qué tres capas?**
Domain (puro) → Application (lógica) → Framework (técnico). Cada una tiene una responsabilidad clara.

### **¿Por qué Panache?**
Simplifica JPA eliminando boilerplate sin perder potencia.

### **¿Por qué MapStruct?**
Mapeo seguro y eficiente de objetos en tiempo de compilación.

### **¿Por qué RuntimeException?**
Las excepciones de dominio deben propagarse sin necesidad de try-catch.

### **¿Puedo cambiar de BD?**
Sí, solo cambias PersonaRepositoryAdapter y PersonaJpaEntity.

### **¿Puedo cambiar de REST a gRPC?**
Sí, solo cambias PersonaController. La aplicación no se afecta.

---

**¡Gracias por usar este proyecto!** 🙌