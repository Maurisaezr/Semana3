/*
 * Clase: Canasta
 * Carrito con arrays simples (productos + cantidades)
 */
public class Canasta {
    private Producto[] productos;
    private int[] cantidades;
    private int contador;

    public Canasta(int capacidad) {
        productos = new Producto[capacidad];
        cantidades = new int[capacidad];
        contador = 0;
    }
// metodo para agregar  productos a la canasta y con un limite de producto se controla desde APP.java
    public void agregarProducto(Producto p, int cantidad) {
        if (contador >= productos.length) {
            System.out.println(" La canasta está llena.");
            return;
        }
        // Comportamiento simple: añade línea nueva (no acumula iguales)
        productos[contador] = p;
        cantidades[contador] = cantidad;
        // cuando se agrega un producto el contado suma 1 para tener la cantidad 
        contador++;
        // imprime el producto agregado y su cantidad 
        System.out.println(" Agregado: " + p.getNombre() + " (x" + cantidad + ")");
    }
// este metodo muestra la canasta he imprime en caso de estar vacia  o con algun producto
    public void mostrarCanasta() {
        if (contador == 0) {
            System.out.println(" La canasta está vacía.");
            return;
        }
        System.out.println("\nContenido de la Canasta:");
        double total = 0;
        for (int i = 0; i < contador; i++) {
            double subtotal = cantidades[i] * productos[i].getPrecio();
            total += subtotal;
            System.out.printf("- %s (x%d) -> $%.2f%n", productos[i].getNombre(), cantidades[i], subtotal);
        }
        System.out.printf("TOTAL: $%.2f%n", total);
    }

    public double total() {
        double total = 0;
        for (int i = 0; i < contador; i++) {
            total += cantidades[i] * productos[i].getPrecio();
        }
        return total;
    }

    public void vaciar() {
        contador = 0; // no hace falta limpiar arrays para el MVP
    }
}
