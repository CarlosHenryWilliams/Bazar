Sistema de Gestión de Bazar (backend) desarrollado desde cero utilizando Java y Spring Boot
🚀 Funcionalidades Principales:
•	Gestión Completa de Entidades (CRUD): 
o	Ventas: Control detallado del proceso de compra.
o	Productos: Administración del inventario y detalles de los artículos.
o	Clientes: Gestión de la información de los usuarios.
•	Lógica de Negocio Específica: Además de los CRUD básicos, la API maneja lógica como la relación entre ventas y productos, control de stock, y consultas específicas (ej. ventas por fecha, cliente con mayor venta, falta de stock de los productos).
🛠️ Tecnologías y Buenas Prácticas Implementadas:
Lombok: Utilice Lombok para la reducción de código repetitivo como los getters y setters de las entidades.	
Arquitectura en Capas (Spring MVC): 
o	Controladores (controller): Manejo de las solicitudes HTTP y enrutamiento.
o	Servicios (service): Implementación de la lógica de negocio.
o	Repositorios (repository): Interacción con la capa de persistencia (bases de datos) utilizando Spring Data JPA.
o	Modelos (model): Definición de las entidades de dominio.
o	DTOs (dto): Implementé un diseño de DTOs diferenciados para solicitudes (RequestDTOs) y respuestas (ResponseDTOs). Esto permite controlar con precisión la información que se recibe de los clientes y la que se les envía, evitando la exposición innecesaria de información sensible.
•	Documentación de API con Swagger UI / OpenAPI: La API está completamente documentada de forma automática, permitiendo a los desarrolladores clientes explorar, entender y probar los endpoints fácilmente (Además me ayudo a darme cuenta si los request estaban bien implementados).
•	Pruebas y Consumo de API con Postman: Utilicé Postman para probar y validar cada endpoint de la API, asegurando su correcto funcionamiento y la correcta manipulación de los datos.
•	Manejo Global de Excepciones (Paquete exceptions y @ControllerAdvice): 
Implemente un paquete de exceptions para tratar las diferentes excepciones lanzadas y asi brindar una respuesta amigable en vez del típico error 404 entre otros. Esto garantiza mensajes de error consistentes, claros y útiles para el cliente (por ejemplo, cuando un recurso no se encuentra o no hay stock insuficiente), mejorando la experiencia del desarrollador que consume la API.
•	Validación de Datos (@Valid): Uso de anotaciones de validación en los DTOs 
