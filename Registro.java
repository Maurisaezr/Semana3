/*
 * Clase: Registro
 * Aquí simulamos el uso de Vehiculo dentro de un registro.
 * Aquí simulamos el uso de Vehiculo dentro de un registro.
 */
public class Registro {
    private Vehiculo[] vehiculos;
    private int contador;

    public Registro(int capacidad) {
        vehiculos = new Vehiculo[capacidad];
        contador = 0;
    }

    // Agregar vehículo al registro
    public void agregarVehiculo(Vehiculo v) {
        if (contador < vehiculos.length) {
            vehiculos[contador++] = v;
            System.out.println("Vehículo agregado: " + v.getMarca() + " " + v.getModelo());
        } else {
            System.out.println("No se pueden agregar más vehículos.");
        }
    }

    // Mostrar todos los vehículos registrados
    public void mostrarVehiculos() {
        if (contador == 0) {
            System.out.println("No hay vehículos registrados.");
            return;
        }
        System.out.println("\n=== Vehículos Registrados ===");
        for (int i = 0; i < contador; i++) {
            System.out.println(vehiculos[i]);
        }
    }
}
