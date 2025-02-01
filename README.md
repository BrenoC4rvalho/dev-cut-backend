### **Desafio: API RESTful para Barbearia "Corte Certo"**

#### **Requisitos Gerais**
1. **Autenticação/Autorização**:
    - Apenas usuários autenticados podem acessar os endpoints (exceto `/auth/login`).
    - `role: admin` pode gerenciar todos os recursos.
    - `role: client` só pode gerenciar seus próprios agendamentos.

---

### **Entidades e Modelos de Dados**

#### **1. Usuário (`User`)**
| Campo      | Tipo        | Descrição                          | Validações                          |
|------------|-------------|------------------------------------|-------------------------------------|
| `id`       | UUID/String | Identificador único                | Gerado automaticamente              |
| `username` | String      | Nome de usuário único              | Obrigatório, único, mínimo 3 chars  |
| `password` | String      | Senha criptografada                | Obrigatório, mínimo 6 chars         |
| `role`     | Enum        | `admin` ou `client`                | Obrigatório                         |
| `phone`    | String      | Telefone (formato internacional)   | Obrigatório, ex: `+5511999999999`   |

#### **2. Agendamento (`Appointment`)**
| Campo         | Tipo        | Descrição                          | Validações                          |
|---------------|-------------|------------------------------------|-------------------------------------|
| `id`          | UUID/String | Identificador único                | Gerado automaticamente              |
| `client_id`   | UUID/String | ID do cliente                      | Obrigatório, referência a `User`    |
| `datetime`    | DateTime    | Data e hora do agendamento         | Obrigatório, futuro, horário comercial (ex: 08:00–18:00) |
| `status`      | Enum        | `SCHEDULED`, `CONFIRMED`, `COMPLETED`, `CANCELED` | Valor padrão: `SCHEDULED` |
| `notes`       | String      | Observações opcionais              | Máximo 200 caracteres               |

---

### **Endpoints**

#### **Autenticação**
- **POST `/auth/login`**  
  Autentica um usuário e retorna um token JWT.  
  **Body**:
  ```json
  { "username": "joao", "password": "senha123" }
  ```

---

#### **Usuários**
- **POST `/users`** (Apenas admin)  
  Cria um novo usuário.  
  **Body**:
  ```json
  {
    "username": "joao",
    "password": "senha123",
    "role": "client",
    "phone": "+5511999999999"
  }
  ```

- **GET `/users`** (Apenas admin)  
  Lista todos os usuários (com paginação).  
  **Query Params**: `page=1&limit=10`

- **GET `/users/{id}`**  
  Retorna um usuário específico. Clientes só podem ver seu próprio perfil.

- **PUT `/users/{id}`**  
  Atualiza um usuário. Clientes só podem atualizar seu próprio perfil.  
  **Body**:
  ```json
  { "phone": "+5511888888888" }
  ```

- **DELETE `/users/{id}`** (Apenas admin)  
  Exclui um usuário.

---

#### **Agendamentos**
- **POST `/appointments`** (Clientes e admin)  
  Cria um novo agendamento.  
  **Body**:
  ```json
  {
    "datetime": "2024-05-20T14:30:00Z",
    "notes": "Corte com tesoura"
  }
  ```

- **GET `/appointments`**  
  Lista agendamentos:
    - Clientes veem apenas seus próprios agendamentos.
    - Admins podem filtrar por `client_id`, `status` ou `date`.  
      **Query Params**: `?client_id=uuid&date=2024-05-20`

- **GET `/appointments/{id}`**  
  Retorna um agendamento específico. Clientes só podem ver seus próprios agendamentos.

- **PUT `/appointments/{id}`**  
  Atualiza um agendamento (ex: status ou notas).  
  **Body**:
  ```json
  { "status": "CONFIRMED" }
  ```

- **DELETE `/appointments/{id}`**  
  Cancela um agendamento (status alterado para `CANCELED`).

---

### **Regras de Negócio e Exceções**
1. **Validações de Agendamento**:
    - Um cliente não pode marcar dois horários no mesmo intervalo de tempo.
    - Um barbeiro não pode ter dois agendamentos no mesmo horário.
    - Horários devem ser em intervalos de 30 minutos (ex: 09:00, 09:30).
    - Não é possível agendar no passado ou fora do horário comercial (ex: 08:00–18:00).

2. **Exceções Comuns**:
    - `409 Conflict`: Barbeiro ou cliente já possui agendamento no mesmo horário.
    - `400 Bad Request`: Data inválida, horário fora do intervalo, ou campos faltantes.
    - `403 Forbidden`: Cliente tentando acessar recurso de outro usuário.
    - `404 Not Found`: Recurso (usuário, barbeiro, agendamento) não existe.
    - `401 Unauthorized`: Token inválido ou ausente.

3. **Segurança**:
    - Senhas devem ser armazenadas com hash (ex: bcrypt).
    - Tokens JWT devem incluir o `role` do usuário no payload.

---

### **Exemplo de Resposta de Erro**
```json
{
  "error": "CONFLICT",
  "message": "O barbeiro já possui um agendamento neste horário."
}
```

---

### **Dicas Extras**
- Use migrations para criar as tabelas `users` e `appointments`.
- Documente a API com OpenAPI/Swagger ou Postman.
- Teste casos como:
    - Cliente tentando marcar horário com barbeiro inexistente.
    - Admin tentando cancelar um agendamento de outro cliente.

Boa sorte! 🚀