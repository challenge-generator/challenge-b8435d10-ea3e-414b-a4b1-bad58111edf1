package com.pragma.banking.transactions.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.UUID;

/**
 * Representa un cliente bancario en el sistema.
 * Contiene información básica y validaciones para garantizar consistencia.
 */
public class Client {
    @NotNull(message = "El ID del cliente no puede ser nulo")
    private final UUID clientId;

    @NotBlank(message = "El nombre del cliente no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private final String name;

    @NotBlank(message = "El número de identificación no puede estar vacío")
    @Pattern(regexp = "^[A-Z0-9]{6,20}$", message = "Formato de identificación inválido")
    private final String identificationNumber;

    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", message = "Formato de correo electrónico inválido")
    private final String email;

    public Client(UUID clientId, String name, String identificationNumber, String email) {
        this.clientId = clientId;
        this.name = name;
        this.identificationNumber = identificationNumber;
        this.email = email;
    }

    public UUID getClientId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Client{clientId=" + clientId +
                ", name='" + name + '\'' +
                ", identificationNumber='" + identificationNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return clientId.equals(client.clientId);
    }

    @Override
    public int hashCode() {
        return clientId.hashCode();
    }
}