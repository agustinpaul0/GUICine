public class Logica {
    private Cine cine;
    private Pelicula peliElegida;
    private int cantEntradas;
    private int cantEntradasElegidas;
    
    //Constructor
    public Logica() {
        
        String [] titulosPelis = {"EL EXORCISTA: CREYENTES","FIVE NIGHTS AT FREDDYS",
        "TROLLS 3: SE ARMÓ LA BANDA","EL SACRAMENTO DEL DIABLO",
        "THE MARVELS - 3D CAST","LOS JUEGOS DEL HAMBRE: BOSS"};
        
        cine = new Cine(titulosPelis.length);
        
        for (int i = 0; i<titulosPelis.length; i++) {
            cine.agregarPelicula(titulosPelis[i]);
        }
        
        peliElegida = cine.obtenerPelicula("EL EXORCISTA: CREYENTES");
        cantEntradas = 1;
        cantEntradasElegidas = 0;
    }
    
    //Consultas
    public Cine obtenerCine() {
        return cine;
    }
    
    public Pelicula obtenerPeliElegida() {
        return peliElegida;
    }
       
    public int obtenerCantEntradas() {
        return cantEntradas;
    }
    
    public int obtenerCantEntradasElegidas() {
        return cantEntradasElegidas;
    }
    
    public boolean todasEntradasElegidas() {
        return cantEntradas == cantEntradasElegidas;
    }
    
    //Comandos
    public void elegirPelicula(String nom) {
        peliElegida = cine.obtenerPelicula(nom);
    }
    
    public void modificarCantEntradas(int cant) {
        cantEntradas = cant;
        cantEntradasElegidas = 0;
    }
    
    public void aumentarEntradasElegidas() {
        cantEntradasElegidas++;
    }
    
    public void disminuirEntradasElegidas() {
        cantEntradasElegidas--;
    }
}