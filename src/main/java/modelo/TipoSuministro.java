package modelo;

/**
 * Enumeración que define los tipos de suministros disponibles para comprar en la tienda.
 * El uso de Enums evita errores de escritura al momento de gestionar el inventario.
 * * @author Cristóbal Araya Lillo
 */
public enum TipoSuministro {
    /** Comida genérica para restaurar el hambre de las mascotas */
    COMIDA_PREMIUM,

    /** Medicina para curar enfermedades y restaurar la salud */
    MEDICINA_BASICA,

    /** KIT DE LIMPIEZA */
    KIT_LIMPIEZA
}