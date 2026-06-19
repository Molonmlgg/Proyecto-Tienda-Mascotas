package modelo;

public abstract class Mascota {
    private String nombre;
    private int nivelHambre;
    private int nivelFelicidad;
    private int higiene;
    private int salud;
    private double precioCompra;

    public Mascota(String nombre, double precioCompra) {
        this.nombre = nombre;
        this.nivelHambre = 0;
        this.nivelFelicidad = 0;
        this.higiene = 0;
        this.salud = 0;
        this.precioCompra = 0;
    }

    public abstract void emitirSonido();
    public abstract void alimentar();

    public String getNombre() { return nombre; }
    public int getNivelHambre() { return nivelHambre; }
    public void setNivelHambre(int nivelHambre) { this.nivelHambre = Math.max(0, Math.min(100, nivelHambre)); }
    public int getNivelFelicidad() { return nivelFelicidad; }
    public void setNivelFelicidad(int nivelFelicidad) { this.nivelFelicidad = Math.max(0, Math.min(100, nivelFelicidad)); }
    public int getHigiene() { return higiene; }
    public void setHigiene(int higiene) { this.higiene = Math.max(0, Math.min(100, higiene)); }
    public int getSalud() { return salud; }
    public void setSalud(int salud) { this.salud = Math.max(0, Math.min(100, salud)); }
    public double getPrecioCompra() { return precioCompra; }
}