import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;

import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI extends JFrame {
    //Lógica de la aplicación
    private Logica l;

    //Paneles
    private JPanel panelTitulo; //panel con cartel de cinemaipoo
    private JPanel panelInfo; //panel con la info de la reserva     
    private JPanel panelButacas; // panel donde se muestra la matriz de botones de las butacas
    private JPanel panelReservar; //panel de boton reservar
    private JPanel panelReservaInfo; //otros paneles para mejorar el diseño de la GUI
    private JPanel panelCeldas; //otros paneles para mejorar el diseño de la GUI

    //Botones
    private JButton [][] botones; 
    private JButton botonReservar;

    //ComboBox
    private JComboBox peliculas;
    private JComboBox cantEntradas;

    //Labels
    private JLabel pelicula;
    private JLabel entradas;
    private JLabel total;

    
    //Constructor
    public GUI() { 
        super("Cinema IPOO");
        l = new Logica();         
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(800, 800));
        inicializarGUI();
        setVisible(true);
    }
    
    //Método privado para inicializar las componentes     
    private void inicializarGUI() {
        this.setLayout(new BorderLayout());

        pelicula= new JLabel("Seleccione la película");
        entradas = new JLabel("Seleccione cantidad de entradas");
        total = new JLabel("Total a pagar: $0.00");

        //Armado del boton Comprar
        botonReservar= new JButton("Comprar");
        botonReservar.addActionListener(new OyenteBotonReservar());
        botonReservar.setEnabled(false); //inicialmente se encuentra deshabilitado

        //Armado del panel del banner 
        panelTitulo= new JPanel();
        panelTitulo.setPreferredSize(new Dimension(800,120));
        JLabel lbImage = new JLabel();
        lbImage.setSize(new Dimension(800,120));
        ImageIcon imgIcon = new ImageIcon(getClass().getResource("banner.png"));
        lbImage.setIcon(imgIcon);
        panelTitulo.add(lbImage);
        panelTitulo.setBackground(new Color(255,255,255));

        //Armado de los paneles con la informacion de la compra
        panelReservaInfo= new JPanel();
        panelReservaInfo.setPreferredSize(new Dimension(800,200));
        panelReservaInfo.setBackground(new Color(255,255,255));
        panelReservaInfo.setLayout(new BorderLayout());

        JPanel panelPeliculas = new JPanel(new FlowLayout());
        panelPeliculas.setBackground(new Color(255,255,255));
        inicializarPeliculas();
        panelPeliculas.add(pelicula);
        panelPeliculas.add(peliculas);

        JPanel panelEntradas = new JPanel(new FlowLayout());
        panelEntradas.setBackground(new Color(255,255,255));
        inicializarEntradas();
        panelEntradas.add(entradas);
        panelEntradas.add(cantEntradas);

        JPanel pago = new JPanel(new FlowLayout());
        panelEntradas.add(total);

        JPanel infoEntradas = new JPanel(new GridLayout(2,1));
        infoEntradas.setBackground(new Color(255,255,255));
        infoEntradas.add(panelEntradas);
        infoEntradas.add(total);

        panelReservaInfo.add(panelPeliculas,BorderLayout.WEST);
        panelReservaInfo.add(infoEntradas,BorderLayout.CENTER);

        //Armado del panel con el banner e info de la compra
        panelInfo= new JPanel();
        panelInfo.setLayout(new GridLayout(2,1));
        panelInfo.setPreferredSize(new Dimension(800,280));
        panelInfo.setBackground(new Color(0,255,0));
        panelInfo.add(panelTitulo);
        panelInfo.add(panelReservaInfo);

        //Armado del panel donde se muetran las celdas (butacas)
        panelCeldas= new JPanel(new FlowLayout());
        panelButacas = new JPanel();
        panelButacas.setPreferredSize(new Dimension(400,400));
        panelButacas.setLayout(new GridLayout(15,15)); // se le establece un gridLayout con las medidas standar del tablero 
        inicializarBotones(); // inicializa cada uno de los botones de la matriz
        panelCeldas.add(panelButacas);

        //Agrego el boton Comprar al panel
        panelReservar= new JPanel();
        panelReservar.setPreferredSize(new Dimension(800,80));
        panelReservar.setBackground(new Color(255,255,255));
        panelReservar.add(botonReservar);

        //Se agregan los tres paneles principales al contentpane
        this.add(panelInfo,BorderLayout.NORTH);
        this.add(panelCeldas,BorderLayout.CENTER);
        this.add(panelReservar,BorderLayout.SOUTH);
        this.setResizable(false);
    }

    //Método que inicializa los botones que representan las butacas
    private void inicializarBotones() {
        //Crear la matriz de botones
        botones = new JButton[15][15];
        //Por cada boton de la matriz:
        for (int i = 0; i < botones.length; i++) { //por cada fila de botones
            for (int j = 0; j < botones[0].length; j++) { //por cada columna de botones
                //Crear el boton
                botones[i][j] = new JButton();
                //Setear el action command "i,j"
                botones[i][j].setActionCommand(i+","+j);
                //Establecer su oyente
                botones[i][j].addActionListener(new OyenteBotonCelda());
                //Deshabilitar el boton
                botones[i][j].setEnabled(false);
                //Setear el icono
                botones[i][j].setIcon(new ImageIcon("butaca.png"));
                //Agregar el boton al panel de butacas (panelButacas)
                panelButacas.add(botones[i][j]);
            }
        }
    }

    //Método para deshabilitar los botones al finalizar una reserva
    private void deshabilitarBotones() {
        for (int i=0; i<botones.length;i++) //por cada fila de botones
            for (int j=0; j<botones[0].length;j++) { //por cada columna de botones
                botones[i][j].setEnabled(false);//le indicamos deshabilitar el boton  
            }
    }

    //Método que actualiza los botones que representan las butacas
    private void actualizarBotones() {
        //Por cada boton de la matriz de butacas, verificar si la butaca correspondiente se encuentra disponible u ocupada
        //Actualizar el icono e indicar si se encuentra habilitado o no (enabled)
        for (int i = 0; i < botones.length; i++) {
            for (int j = 0; j < botones[0].length; j++) {
                if (l.obtenerPeliElegida().obtenerButaca(i,j)) {
                    botones[i][j].setEnabled(true);
                    botones[i][j].setIcon(new ImageIcon("butaca.png"));
                } else {
                    botones[i][j].setIcon(new ImageIcon("butacaOcupada.png"));
                }
            }
        }
    }

    //Método para inicializar el JComboBox con las peliculas
    private void inicializarPeliculas() {
        //Crear el JComboBox de peliculas
        peliculas = new JComboBox<String>(); //<String> le indica al JComboBox que contendrá objetos de tipo String
        //Por cada pelicula en el cine, agregar un item en el JComboBox de peliculas con el nombre de las peliculas
        for (int i = 0; i < l.obtenerCine().cantPeliculas(); i++) {
            peliculas.addItem(l.obtenerCine().obtenerPelicula(i).obtenerNombre());
        }
        //Establecer el oyente al JComboBox de peliculas
        peliculas.addActionListener(new OyenteBotonPelicula());
    }

    private void inicializarEntradas() {
        cantEntradas = new JComboBox<String>();
        cantEntradas.addItem("1");
        cantEntradas.addItem("2");
        cantEntradas.addItem("3");
        cantEntradas.addItem("4");
        cantEntradas.addItem("5");
        cantEntradas.addItem("6"); //agregamos un item (opcion) en el JComboBox (el usuario puede comprar de 1 a 6 entradas)-->también se puede hacer con un bucle
        cantEntradas.setEnabled(false); //inicialmente se encuentra deshabilitado hasta elegir la película
        cantEntradas.addActionListener(new OyenteBotonEntradas()); // se establece su oyente
    }

    //Oyente para el JComboBox de cantidad de entradas
    private class OyenteBotonEntradas implements ActionListener {
        public void actionPerformed(ActionEvent e){
            //Obtener el item seleccionado del JComboBox de entradas (es un String el valor retornado por el método)
            String cantidadEntradas = cantEntradas.getSelectedItem().toString();
            //Parsear el valor obtenido a entero
            int numeroEntradas = Integer.parseInt(cantidadEntradas);
            //Modificar la cantidad de entradas a comprar en la lógica de la aplicación
            l.modificarCantEntradas(numeroEntradas);
            //Actualizar la matriz de botones (butacas)
            actualizarBotones();
            //Calcular el total a pagar y actualizarlo en la JLabel correspondiente
            float totalAPagar = l.obtenerCantEntradas()*l.obtenerCine().obtenerValorEntrada();
            total.setText("Total a pagar: $"+totalAPagar);
            //Deshabilitar el JComboBox de la cantidad de entradas
            cantEntradas.setEnabled(false); 
        } 
    }

    //Oyente para el JComboBox de películas
    private class OyenteBotonPelicula implements ActionListener {
        public void actionPerformed(ActionEvent e){
            String opcion = peliculas.getSelectedItem().toString();//obtengo el nombre de la pelicula
            l.elegirPelicula(opcion); //seteo en la lógica la pelicula elegida
            peliculas.setEnabled(false); //deshabilito la opción para volver a elegir la pelicula --> PREGUNTAR
            cantEntradas.setEnabled(true); //seteo JComboBox de cantidad de entradas a habilitado
            float costoTotal = l.obtenerCantEntradas()*l.obtenerCine().obtenerValorEntrada();
            total.setText("Total a pagar: $"+costoTotal); //seteo el total a pagar
        } 
    }

    //Oyente de botones de butacas
    private class OyenteBotonCelda implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String indices=e.getActionCommand(); //obtengo el identificador del boton seleccionado
            String[] iyj= indices.split(",");
            int i=Integer.parseInt(iyj[0]); //obtengo nro de fila del boton
            int j=Integer.parseInt(iyj[1]); //obtengo nro de columna del boton
            Pelicula peli = l.obtenerPeliElegida();

            if (!peli.obtenerButaca(i,j) ) {//si la butaca ya estaba seleccionada y la deselecciono
                peli.desocuparButaca(i,j); //desocupo la butaca
                l.disminuirEntradasElegidas(); //disminuyo la cantidad de entradas elegidas
                botones[i][j].setIcon(new ImageIcon("butaca.png")); //seteo el icono del boton correspondiente
            } else if (peli.obtenerButaca(i,j) && l.obtenerCantEntradasElegidas()<l.obtenerCantEntradas()) {//Si no estaba seleccionada y todavía no seleccione el total de butacas
                peli.ocuparButaca(i,j); //ocupo la butaca 
                l.aumentarEntradasElegidas(); //aumento la cantidad de entradas elegidas
                botones[i][j].setIcon(new ImageIcon("butacaSeleccionada.png")); //seteo el icono del boton correspondiente  
            }

            if(l.todasEntradasElegidas()) //si ya seleccione el total de las butacas seleccionadas
                botonReservar.setEnabled(true); //Habilito el JButton de compra 
            else //si me faltan seleccionar butacas
                botonReservar.setEnabled(false); //deshabilito el JButton de compra
        } 
    }
    
    //Oyente para el botón de comprar
    private class OyenteBotonReservar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //Mostrar el mensaje de exito
            JOptionPane mensaje = new JOptionPane();
            JOptionPane.showMessageDialog(mensaje,"Gracias por su compra","Cinema IPOO",JOptionPane.INFORMATION_MESSAGE);
            //Resetear los JComboBox de película y entradas
            peliculas.setSelectedIndex(0);
            cantEntradas.setSelectedIndex(0);
            //Deshabilitar todos los botones (butacas) de la matriz 
            deshabilitarBotones();
            //Deshabilitar el boton comprar y el JComboBox de entradas
            cantEntradas.setEnabled(false);
            botonReservar.setEnabled(false);
            //Setear el total a pagar a 0.00
            total.setText("Total a pagar: $0.00");
            peliculas.setEnabled(true); //Habilito nuevamente el JComboBox de peliculas para poder seleccionar una pelicula -->PREGUNTAR
        }
    }
}