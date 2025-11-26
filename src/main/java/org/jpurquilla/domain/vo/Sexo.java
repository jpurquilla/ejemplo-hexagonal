package org.jpurquilla.domain.vo;

/**
 * Enum de Dominio: Sexo
 *
 * Value Object que representa los valores válidos para el sexo de una persona.
 * Solo puede ser MASCULINO o FEMENINO.
 */
public enum Sexo {
    MASCULINO("M", "Masculino"),
    FEMENINO("F", "Femenino");

    private final String codigo;      // Valor en BD: "M" o "F"
    private final String descripcion; // Valor legible

    Sexo(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Convierte un String a su correspondiente valor del Enum.
     * Ejemplo: "M" → Sexo.MASCULINO
     * Los mappers usarán este método para hacer la conversión.
     *
     * @param codigo "M" o "F"
     * @return Sexo.MASCULINO o Sexo.FEMENINO
     * @throws IllegalArgumentException si el código no es válido
     */
    public static Sexo fromCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("Sexo no puede ser nulo o vacío");
        }

        for (Sexo sexo : values()) {
            if (sexo.codigo.equalsIgnoreCase(codigo.trim())) {
                return sexo;
            }
        }

        throw new IllegalArgumentException(
                "Sexo inválido: '" + codigo + "'. Valores válidos: M, F"
        );
    }
}
