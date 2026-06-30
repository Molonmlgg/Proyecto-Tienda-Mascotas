package modelo;

public abstract class Mascota {

    /** Nivel mínimo y máximo permitido para los atributos que le daremos a las mascotas*/
    private static final int MIN_NIVEL = 0;
    private static final int MAX_NIVEL = 100;

    private String nombre;
    private int nivelHambre;
    private int nivelFelicidad;
    private int higiene;
    private int salud;
    private double precioCompra;

    /**
     * Constructor de la mascota
     * @param nombre , nombre de la mascota
     * @param precioCompra , precio a pagar por la mascota.
     */
    public Mascota(String nombre, double precioCompra) {
        this.nombre = nombre;
        this.nivelHambre = 0;
        this.nivelFelicidad = 0;
        this.higiene = 0;
        this.salud = 100;
        this.precioCompra = precioCompra;
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
     * Modifica el nivel de hambre.
     * El valor siempre permanece entre 0 y 100
     *
     * @param nivelHambre nuevo nivel de hambre.
     */
    public void setNivelHambre(int nivelHambre) {
        if (nivelHambre < MIN_NIVEL){
            this.nivelHambre = MIN_NIVEL;
        } else if (nivelHambre > MAX_NIVEL){
            this.nivelHambre = MAX_NIVEL;
        } else {
            this.nivelHambre = nivelHambre;
        }
    }

    /**
     * Obtenie el nivel de felicidad
     * @return nivel de felicidad
     */
    public int getNivelFelicidad() { return nivelFelicidad; }

    /**
     * Modifica el nivel de felicidad de la mascota
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
    }

    /**
     * Obtiene el nivel de higiene de la mascota
     * @return nivel de higiene
     */
    public int getHigiene() { return higiene; }

    /**
     * Modifica el nivel de higiene de la mascota
     * @param higiene nuevo nivel de higiene
     */
    public void setHigiene(int higiene) {
        if (higiene < MIN_NIVEL){
            this.higiene = MIN_NIVEL;
        } else if (higiene > MAX_NIVEL){
            this.higiene = MAX_NIVEL;
        } else {
            this.higiene = higiene;
        }
    }

    /**
     * Obtiene el nivel de salud de la mascota
     * @return nivel de salud
     */
    public int getSalud() { return salud; }

    /**
     * Modifica el nivel de salud de la mascota
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

    public void disminuirFelicidad(int cantidad){
        setNivelFelicidad(getNivelFelicidad() - cantidad);
    }

    public void disminuirHigiene(int cantidad){
        setHigiene(getHigiene() - cantidad);
    }

    public void disminuirSalud(int cantidad){
        setSalud(getSalud() - cantidad);
    }

    /**
     * Indica si la mascota tiene mucha hambre
     * @param true si el nivel de hambre es mayor o igual a 80
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

}