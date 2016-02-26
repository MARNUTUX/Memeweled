import javax.swing.*;
import java.awt.*;


public class Etiqueta extends JLabel {
  
    Ventana _miVentana;
    int _alto;
    int _ancho; 
    int  _x, _y;
    boolean _seleccionado = false;
    boolean _caida = false;
    
   
    public void setSeleccionado(boolean bool) { _seleccionado = bool; }
    
    public void setCaida(boolean bool) { _caida = bool;    }

    public Etiqueta(Ventana miVentana, String ruta) {
        _miVentana = miVentana;
        
        Icon miIcono = new ImageIcon(ruta, ruta);
        this.setIcon(miIcono);
        Dimension dim = _miVentana.getContenedor();
        _alto  = (int) (dim.getHeight() / _miVentana.getFilas());
        _ancho = (int) dim.getWidth()  / _miVentana.getColumnas();     
        
    }
    
    public void paint(Graphics g) {
     	
        ImageIcon miIcono = (ImageIcon)getIcon(); 
        Image img = miIcono.getImage();
        if(!_caida)
		    g.drawImage(img, 15, 2, _ancho-21, _alto-21, this);
		else
		    caida(g);
		if(_seleccionado)
		    seleccionar(g);
		
    }
    
    public void caida(Graphics g) {
        int i = 0;
        
        ImageIcon miIcono = (ImageIcon)getIcon(); 
        Image img = miIcono.getImage();
        while(i < _alto-20 ) {
            g.drawImage(img, 15, 2, _ancho-21, i, this);
             try { //validar
                 i++;
                
                Thread.sleep(2);
            } catch(Exception exc) {
                System.out.println("Error. "+exc.toString());
            }
           
        }       
        
            
        _caida = false;
        
    }
    
    public void seleccionar(Graphics g) {
        
        g.setColor(new Color(70));
        g.drawLine(1, 1, _ancho-5, 1);
        g.drawLine(_ancho-5, 1, _ancho-5, _alto-5);
        g.drawLine(_ancho-5, _alto-5, 1, _alto-5);
        g.drawLine(1, _alto-5, 1, 1);
        
    }
    
    public String getDescripcion() { 
        ImageIcon i = (ImageIcon)this.getIcon();
        return i.getDescription(); 
    
    }
    
    
}
