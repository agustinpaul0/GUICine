public class Cine {
    //Atributos de instancia
    private Pelicula salas[];
    private int n;
    private float valorEntrada;

    //Constructor
    public Cine(int cantSalas) {
        salas = new Pelicula[cantSalas];
        n = 0;
        valorEntrada = 1200.0f;
    }
    
    //Consultas
    public int cantPeliculas() {
        return n;
    }
    
    public float obtenerValorEntrada() {
        return valorEntrada;
    }
    
    public Pelicula obtenerPelicula(int idSala) {
        return salas[idSala];
    }
    
    public Pelicula obtenerPelicula(String nom) {
        boolean encontre = false;
        Pelicula p = null;
        for (int i = 0; i<n && !encontre; i++) {
            if (salas[i].obtenerNombre().equals(nom)) {
                encontre = true;
                p = salas[i];
            }
        }
        return p;
    }
    
    //Comandos
    public void agregarPelicula(String nom) {
        salas[n] = new Pelicula(nom);
        n++;
    }
}