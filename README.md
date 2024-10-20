# Microusers

## Agregar usuario

- El nombre del endpoint es `/user` con el metodo `POST`
- El endpoint recibe lo siguiente:

```json
  {
  "email": "user@example.com",
  "password": "hashedPassword123"
  }
```

En endpoint puede retornar 3 cosas:
1. Error en el servidor
2. Usuario guardado correctamente `status: 201`
3. Ya existe usuario con el correo "user@example.com"