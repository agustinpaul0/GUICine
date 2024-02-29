import java.util.Random;

public class Pelicula {
    //Atributos de instancia
    private String nombre;
    private boolean butacas[][];

    //Constructor
    public Pelicula(String nom) {
        nombre = nom;
        butacas = new boolean[15][15];
        Random n= new Random();
        for (int i=0; i<butacas.length;i++)
            for (int j=0;j<butacas[0].length;j++)
                if ((n.nextInt()%2==0)&&(n.nextInt()%2==0))
                    butacas[i][j]= true;
                else    
                    butacas[i][j]= false;
    }

    //Consultas
    public String obtenerNombre() {
        return nombre;
    }
    
    public int cantidadFilas() {
        return butacas.length;
    }
    
    public int cantidadColumnas() {
        return butacas[0].length;
    }
    
    public boolean obtenerButaca(int f, int c) {
        return butacas[f][c]; //si la butaca está ocupada retorna false
    }

    //Comandos
    public void ocuparButaca(int f, int c) {
        butacas[f][c] = false;
    }
    
    public void desocuparButaca(int f, int c) {
        butacas[f][c] = true;
    }    
}