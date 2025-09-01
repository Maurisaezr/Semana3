/*
 * Clase: Producto
 * Representa un producto de la distribuidora.
 */
 
 
public class Producto {
    
    //  declaramos los atributos del producto bien sencillo  nombre y precio 
    private String nombre;
    private double precio;
//  armamos el contructor
    public Producto(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }

// pequeño metodo que muestra el producto y su preco 
    public void mostrar() {
        System.out.printf("Producto: %-15s Precio: $%.2f%n", nombre, precio);
    }
}