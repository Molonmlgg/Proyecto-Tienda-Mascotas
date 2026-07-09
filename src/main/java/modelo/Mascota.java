package modelo;

import observador.EstadoObservador;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta que representa una mascota dentro del simulador.
 * Actúa como <b>sujeto observable</b> dentro del patrón de diseño Observer:
 * cualquier clase que implemente {@link EstadoObservador} puede suscribirse
 * para ser notificada automáticamente cada vez que cambian los niveles de
 * hambre, felicidad, higiene o salud, sin que el controlador o la vista
 * tengan que refrescarse manualmente tras cada acción.
 *
 * @author Cristóbal Araya Lillo
 */
public abstract class Mascota {

    /** Nivel mínimo y máximo permitido para los atributos que le daremos a las mascotas*/
    private static final int MIN_NIVEL = 0;
    private static final int MAX_NIVEL = 100;

    /** Marca de tiempo (ms) en que la mascota fue adoptada, usada para calcular su valor de venta. */
    private long tiempoAdopcion;

    private String nombre;
    private int nivelHambre;
    private int nivelFelicidad;
    private int higiene;
    private int salud;
    private double precioCompra;

    /** Lista de observadores suscritos a los cambios de estado de esta mascota. */
    private final List<EstadoObservador> observadores = new ArrayList<>();

    /**
     * Constructor de la mascota
     * @param nombre , nombre de la mascota
     * @param precioCompra , precio a pagar por la mascota.
     */
    public Mascota(String nombre, double precioCompra) {
        this.nombre = nombre;
        this.nivelHambre = 20;
        this.nivelFelicidad = 50;
        this.higiene = 50;
        this.salud = 100;
        this.precioCompra = precioCompra;
        this.tiempoAdopcion = System.currentTimeMillis();
    }

    /**
     * Hace que la mascota emita un sonido específico
     */
    public abstract void emitirSonido();

    /**
     * Alimenta a la mascota
     */
    public abstract void alimentar();

    /**
     * Suscribe un observador para que sea notificado de los cambios
     * de estado de esta mascota.
     *
     * @param observador el observador a registrar (típicamente una vista).
     */
    public void agregarObservador(EstadoObservador observador) {
        if (observador != null && !observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    /**
     * Da de baja a un observador previamente suscrito, para que deje
     * de recibir notificaciones de esta mascota (por ejemplo, al venderla).
     *
     * @param observador el observador a remover.
     */
    public void removerObservador(EstadoObservador observador) {
        observadores.remove(observador);
    }

    /**
     * Notifica a todos los observadores suscritos que el estado de la
     * mascota acaba de cambiar. Se itera sobre una copia de la lista
     * para evitar problemas si algún observador se suscribe o
     * desuscribe durante la notificación.
     */
    private void notificarObservadores() {
        for (EstadoObservador observador : new ArrayList<>(observadores)) {
            observador.actualizarEstado(this);
        }
    }

    /**
     * Obtiene el nombre de la mascota.
     *
     * @return nombre de la mascota.
     */
    public String getNombre() { return nombre; }

    /**
     * Obtiene el nivel de hambre.
     *
     * @return nivel de hambre.
     */
    public int getNivelHambre() { return nivelHambre; }

    /**
     * Modifica el nivel de hambre. Si el hambre es extrema (>= 80),
     * la mascota comienza a perder salud y felicidad.
     */
    public void setNivelHambre(int nivelHambre) {
        if (nivelHambre < MIN_NIVEL){
            this.nivelHambre = MIN_NIVEL;
        } else if (nivelHambre > MAX_NIVEL){
            this.nivelHambre = MAX_NIVEL;
        } else {
            this.nivelHambre = nivelHambre;
        }

        if (this.nivelHambre >= 80) {
            this.salud = Math.max(MIN_NIVEL, this.salud - 5);
            this.nivelFelicidad = Math.max(MIN_NIVEL, this.nivelFelicidad - 5);
        }

        notificarObservadores();
    }

    /**
     * Obtenie el nivel de felicidad
     * @return nivel de felicidad
     */
    public int getNivelFelicidad() { return nivelFelicidad; }

    /**
     * Modifica el nivel de felicidad de la mascota.
     * Notifica a los observadores suscritos tras aplicar el cambio.
     *
     * @param nivelFelicidad nivel nuevo de felicidad
     */
    public void setNivelFelicidad(int nivelFelicidad) {
        if (nivelFelicidad < MIN_NIVEL){
            this.nivelFelicidad = MIN_NIVEL;
        } else if (nivelFelicidad > MAX_NIVEL){
            this.nivelFelicidad = MAX_NIVEL;
        } else {
            this.nivelFelicidad = nivelFelicidad;
        }
        notificarObservadores();
    }

    /**
     * Obtiene el nivel de higiene de la mascota
     * @return nivel de higiene
     */
    public int getHigiene() { return higiene; }

    /**
     * Modifica el nivel de higiene. Si está muy sucia (<= 20),
     * la mascota se enferma (pierde salud) y se pone triste.
     */
    public void setHigiene(int higiene) {
        if (higiene < MIN_NIVEL){
            this.higiene = MIN_NIVEL;
        } else if (higiene > MAX_NIVEL){
            this.higiene = MAX_NIVEL;
        } else {
            this.higiene = higiene;
        }

        if (this.higiene <= 20) {
            this.salud = Math.max(MIN_NIVEL, this.salud - 5);
            this.nivelFelicidad = Math.max(MIN_NIVEL, this.nivelFelicidad - 5);
        }

        notificarObservadores();
    }

    /**
     * Obtiene el nivel de salud de la mascota
     * @return nivel de salud
     */
    public int getSalud() { return salud; }

    /**
     * Modifica el nivel de salud de la mascota.
     * Notifica a los observadores suscritos tras aplicar el cambio.
     *
     * @param salud nuevo nivel de salud
     */
    public void setSalud(int salud) {
        if (salud < MIN_NIVEL){
            this.salud = MIN_NIVEL;
        } else if (salud > MAX_NIVEL){
            this.salud = MAX_NIVEL;
        } else {
            this.salud = salud;
        }
        notificarObservadores();
    }

    /**
     * Obtiene el precio de compra de la mascota
     * @return precio de compra
     */
    public double getPrecioCompra() { return precioCompra; }

    /**
     * Cura a la mascota aumentando la salud de esta
     * @param cantidad puntos de salud para recuperar
     */
    public void curar(int cantidad){
        setSalud(getSalud() + cantidad);
    }

    /**
     * Limpia la mascota aumentando su higiene.
     * @param cantidad puntos de higiene a recuperar
     */
    public void limpiar(int cantidad){
        setHigiene(getHigiene() +cantidad);
    }

    /** Aumenta el nivel de hambre
     *
     * @param cantidad puntos de hambre que aumentan
     */
    public void aumentarHambre(int cantidad) {
        setNivelHambre(getNivelHambre() + cantidad);
    }

    /**
     * Aumenta el nivel de felicidad
     * @param cantidad puntos de felicidad que aumentarán
     */
    public void aumentarFelicidad(int cantidad){
        setNivelFelicidad(getNivelFelicidad() + cantidad);
    }

    /**
     * Disminuye el nivel de felicidad de la mascota.
     * @param cantidad puntos de felicidad a restar
     */
    public void disminuirFelicidad(int cantidad){
        setNivelFelicidad(getNivelFelicidad() - cantidad);
    }

    /**
     * Disminuye el nivel de higiene de la mascota.
     * @param cantidad puntos de higiene a restar
     */
    public void disminuirHigiene(int cantidad){
        setHigiene(getHigiene() - cantidad);
    }

    /**
     * Disminuye el nivel de salud de la mascota.
     * @param cantidad puntos de salud a restar
     */
    public void disminuirSalud(int cantidad){
        setSalud(getSalud() - cantidad);
    }

    /**
     * Indica si la mascota tiene mucha hambre
     */
    public boolean tieneHambre(){
        return nivelHambre >= 80;
    }

    /**
     * Indica si la mascota esta enferma
     * @return true si la salud es menor o igual a 30
     */
    public boolean estaEnferma() {
        return salud <= 30;
    }

    /**
     * Indica si la mascota esta sucia
     * @return true si la higiene es menor o igual a 50.
     */
    public boolean estaSucia() {
        return higiene <= 50;
    }

    /**
     * Indica si la mascota esta triste
     * @return true si la felicidad es menor o igual a 40
     */
    public boolean estaTriste() {
        return nivelFelicidad <= 40;
    }


    /**
     * Calcula el valor de venta de la mascota.
     * Empieza en $0 y crece a un ritmo moderado. Castiga las ventas prematuras.
     * @return El dinero que te pagarán por ella.
     */
    public double calcularValorActual() {
        double valorFinal = 0.0;
        long segundosEnTienda = (System.currentTimeMillis() - this.tiempoAdopcion) / 1000;

        double crecimientoPorTiempo = segundosEnTienda * 1.2;

        valorFinal += Math.min(100.0, crecimientoPorTiempo);

        if (valorFinal > 15.0) {


            if (this.salud >= 80 && this.higiene >= 70 && this.nivelHambre <= 30 && this.nivelFelicidad >= 80) {
                valorFinal *= 1.3;
            }

            else if (this.salud <= 40 || this.higiene <= 30 || this.nivelHambre >= 70) {
                valorFinal *= 0.6;
            }
        }

        return valorFinal;
    }

}