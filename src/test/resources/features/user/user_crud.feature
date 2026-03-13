Feature: Un ciclo de vida CRUD en la API de PetStore

  Background:
    Given el actor está configurado para interactuar con la API de PetStore

  @CRUD @Smoke
  Scenario: Crear, leer, actualizar y eliminar un usuario exitosamente
    Given el actor crea un usuario con los siguientes datos:
      | id        | 1001                   |
      | username  | usuario_prueba         |
      | firstName | Juan                   |
      | lastName  | Pérez                  |
      | email     | juan.perez@example.com |
      | password  | SecurePass123          |
      | phone     | "+593912345678"        |
      | userStatus| 1                      |
    Then el actor recibe una respuesta con código de estado 200

    When el actor consulta el usuario por su username
    Then el actor recibe una respuesta con código de estado 200
    And el actor debería verificar que los datos del usuario sean:
      | username  | usuario_prueba         |
      | firstName | Juan                   |
      | lastName  | Pérez                  |
      | email     | juan.perez@example.com |

    When el actor actualiza el usuario con los siguientes datos:
      | firstName | Carlos                  |
      | lastName  | Gómez                   |
      | email     | carlos.gomez@example.com |
      | password  | NuevaPass456            |
    Then el actor recibe una respuesta con código de estado 200

    When el actor consulta el usuario actualizado
    Then el actor recibe una respuesta con código de estado 200
    And el actor debería verificar que los datos del usuario sean:
      | username  | usuario_prueba         |

    When el actor elimina el usuario
    Then el actor recibe una respuesta con código de estado 200

    When el actor intenta consultar el usuario eliminado
    Then el actor recibe una respuesta con código de estado 404
