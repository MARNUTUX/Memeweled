import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class Ventana extends JFrame implements KeyListener {
    
    Etiqueta [] _miEtiqueta;
    String [] _rutas;
    Etiqueta _selec1;
    Etiqueta _selec2;
    
    int _i, _j;
    int _seleccionado;
    public int cantidadImagenes;     //ARREGLAR
    int var = 1;
    boolean bandera = false;
    int _x = 1;
    int _y = 1;
    Container _miContenedor;
    JLabel _marcador;
    int _puntos;
    

    public Ventana(int i, int j) {
        super("Memeweled");
        cantidadImagenes = 10;
        getContentPane().setBackground(new java.awt.Color(255,255,255)); 
        _miContenedor = new Container();
        _marcador = new JLabel(" 0 ");
        _i = i;
        _j = j;  
        _selec1 = null;
        _selec2 = null;
        setLayout(null);
        _miEtiqueta = new Etiqueta[_i*_j];
        _rutas = new String[10];
        setBounds(50, 50, 700, 700);
        _puntos = 0;
        _miContenedor.setLayout(new GridLayout(i,j));
        _miContenedor.setBounds(0, 0, 600, 600);
        addKeyListener(this);
        initEtiquetas();
        add(_marcador);
        add(_miContenedor);
        
        _marcador.setBounds(300, 600, 50, 50);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        buscarRepetidosColumnas();
        buscarRepetidosFilas();
      
    }
    

    public String getRutaAleatorio() {    return _rutas[(int)Math.floor(Math.random()*cantidadImagenes)];  }
    
    public Dimension getContenedor() { return _miContenedor.getSize();  }
    
    public int getFilas() { return _i;  }
    
    public int getColumnas() { return _j; }
    
    public void initEtiquetas() {
        for(int i = 0; i < cantidadImagenes; i++) {
            _rutas[i] = new String("memes/meme"+i+".png");  
        }
        for(int i = 0; i < _i*_j; i++) {
            _miEtiqueta[i] = new Etiqueta(this, getRutaAleatorio());         
            _miContenedor.add(_miEtiqueta[i]);
            
         } 
        _seleccionado = 0;
        repintar(_seleccionado, true);

    }
    
    public void repintar(int i, boolean b) {  
        Etiqueta e2 = _miEtiqueta[i];
        e2.setSeleccionado(b);
        e2.repaint();       
    }
    
    public void intercambiar() {
        if(_selec1 == null) {
            _selec1 = _miEtiqueta[_seleccionado];
            var = _seleccionado;
            bandera = true;
                   
        } else if(_selec2 == null ) {
            _selec2 = _miEtiqueta[_seleccionado];
            cambiarImagen();
            bandera = false;
        }    
    }
    
    public void cambiarImagen() {
        Icon i1 = _selec1.getIcon();
        Icon i2 = _selec2.getIcon();
        _selec1.setIcon(i2);
        _selec1.update(_selec1.getGraphics());
        _selec2.setIcon(i1);
        _selec2.update(_selec2.getGraphics());
        _selec1 = null;
        _selec2 = null;
        _x = 1;
        _y = 1;
        buscarRepetidosFilas();
        buscarRepetidosColumnas();
        _marcador.setText(""+_puntos);
        _marcador.update(_marcador.getGraphics());

    }
    
    public void moverIzquierda() {
        if(_seleccionado != 0 & (_seleccionado % 8) != 0 & _x > 0 & _y != 0 & _y != 2) {
            repintar(_seleccionado, false);
            _seleccionado--;
            if(!bandera) {
                var = _seleccionado;
            } else {
                _x--;
            }
            repintar(_seleccionado, true);
        }
    }
    
    public void moverAbajo() {
        if(_seleccionado >= _j & _y > 0 & _x != 0 & _x != 2) {
            repintar(_seleccionado, false);
            _seleccionado-=_j;
            if(!bandera) {
                var = _seleccionado;
            } else {
                _y--;
            } 
            repintar(_seleccionado, true);
        }
    }
    
    public void moverDerecha() {
      if(_seleccionado < (_i*_j)-1  & ((_seleccionado+1) % 8) != 0 & _x < 2 & _y != 0 & _y != 2) {                    
          repintar(_seleccionado, false);
          _seleccionado++;
          if(!bandera){
            var = _seleccionado;
          } else {
            _x++;
          }
          repintar(_seleccionado, true);
      }
    }
    
    public void moverArriba() {
        if(_seleccionado < ((_i*_j)-_j) & _y < 2 & _x != 0 & _x != 2) {
            repintar(_seleccionado, false);
            _seleccionado +=_j;
        if(!bandera) {
            var = _seleccionado;
        } else {
            _y++;
        }
        repintar(_seleccionado, true);
        }
    }
    
    // manejar evento de pulsación de cualquier tecla
    public void keyPressed( KeyEvent evento )  {
        switch(evento.getKeyCode()) {
            case 37:
                moverIzquierda();
                break;           
                
            case 38:
                moverAbajo();
                break;
                     
            case 39: 
                moverDerecha();
                break;    
                
            case 40:
                moverArriba();
                break;
                
            case 10:            
                intercambiar();   
                break;

            default:
                break;
            
        }
    }
    public void buscarRepetidosFilas() {
        boolean b = true;
        boolean cuarteto = false;
        boolean quienteto = false;
        
        for(int i = 0; i < _i*_j; i += _j){
            int m = i + _j;
            for(int k = i; k < m-2; k++){
                int x = k;
                if(_miEtiqueta[k].getDescripcion() == _miEtiqueta[++x].getDescripcion()){
                    int h = x;
                    if(_miEtiqueta[h].getDescripcion() == _miEtiqueta[++h].getDescripcion()){
                        int l = h;
                        int z = 0;
                        _puntos += 10;
                        if(l < m-2)
                        if(_miEtiqueta[l].getDescripcion() == _miEtiqueta[++l].getDescripcion()) {
                            //cuarteto
                            _puntos += 10;
                            z = l;
                            if(z < m)
                            if(_miEtiqueta[z].getDescripcion() == _miEtiqueta[++z].getDescripcion()) {
                                //quinteto
                                _puntos += 10;
                                quienteto = true;
                            }
                            cuarteto = true;
                        }
                        caida(k);
                        caida(x);
                        caida(h);

                        if(cuarteto)
                            caida(l); 
                        if(quienteto)
                            caida(z);  
                    }
                }
            }
        }
        
    }
    
        public void buscarRepetidosColumnas() {
        boolean b = true;
        boolean cuarteto = false;
        boolean quienteto = false;
        
        for(int i = 0; i < _j; i++){
            int m = i+(_i*_j)-_j;
            for(int k = i; k <= m-(_j*2); k += _j ) {
                int x = k;
                if(_miEtiqueta[k].getDescripcion() == _miEtiqueta[x+=_j].getDescripcion()){
                    int h = x;
                    if(_miEtiqueta[h].getDescripcion() == _miEtiqueta[h+=_j].getDescripcion()){
                        //triada
                        _puntos += 10;
                        int l = h;
                        int z = 0;
                        if(l <= m-_j)
                        if(_miEtiqueta[l].getDescripcion() == _miEtiqueta[l+=_j].getDescripcion() ){
                            //cuarteto
                            _puntos += 10;
                            z = l;
                            if(z <= m-_j)
                            if(_miEtiqueta[z].getDescripcion() == _miEtiqueta[z+=_j].getDescripcion() ){
                                //quinteto
                                _puntos += 10;
                                quienteto = true;
                            }
                            cuarteto = true;
                        }
                        caida(k);
                        caida(x);
                        caida(h);
                        if(cuarteto)
                            caida(l);
                        if(quienteto)
                            caida(z);                  
                    }
                }   
            }
        }
    }
    
    public void caida(int num) { // Object 
        Icon aux = null;
        for(int i=num; i > 0; i-=_j) {

                if(i > _j){
                    aux = _miEtiqueta[i-_j].getIcon();
                    _miEtiqueta[i].setIcon(aux);
                    _miEtiqueta[i].setCaida(true);
                    _miEtiqueta[i].update(_miEtiqueta[i].getGraphics());

                }else {
                    aux = new ImageIcon(getRutaAleatorio());
                    _miEtiqueta[i].setIcon(aux);
                    _miEtiqueta[i].update(_miEtiqueta[i].getGraphics());

                }
           
        }
        
        
        
    }
   
    public void keyReleased( KeyEvent evento )  {    }
 
    // manejar evento de pulsación de una tecla de acción
    public void keyTyped( KeyEvent evento ) {  }
    
    /* a = 65; w = 87; s = 83; d = 68; espacio = 32*/
    /* arriba: 38; abajo: 40; izq: 37; der = 39; enter: 10*/
    

    
   
    
}
