# AppInmobiliaria — Aplicación móvil para propietarios

Aplicación Android desarrollada como complemento del sistema web inmobiliario. Está destinada exclusivamente a los propietarios de inmuebles, permitiéndoles gestionar su perfil, propiedades y contratos desde el celular.

---

## 📱 Funcionalidades

- Login y logout de propietarios
- Recuperación de contraseña (“olvidé mi contraseña”)
- Ver y editar el perfil personal
- Cambiar contraseña de manera independiente
- Listar inmuebles propios
- Habilitar o deshabilitar un inmueble
- Agregar nuevo inmueble con imagen (por defecto deshabilitado)
- Listar contratos asociados a cada inmueble y sus pagos

**🔐 Seguridad:**
- Autenticación mediante token (JWT)
- El ID del propietario se recupera desde el token, no se envía desde la app
- Todas las funcionalidades (excepto login) requieren autenticación

---

## 🧰 Tecnologías utilizadas

- Android SDK  
- Java  
- MVVM (Model-View-ViewModel)  
- Retrofit para consumo de APIs REST  
- Glide para carga de imágenes  
- SharedPreferences para almacenamiento de tokens  
- Validaciones de formulario del lado del cliente  
- IDE: Android Studio  

---

## 🔗 Requisitos del backend

La aplicación consume los endpoints protegidos del backend ASP.NET (.NET), los cuales manejan autenticación, gestión de datos y validaciones de seguridad.

---
