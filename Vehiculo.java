// clase vehiculo

public class Vehiculo {
    String marca;
    String modelo;
    int cilindrada;
    String combustible;
    int capacidadPasajeros;
// contructor para vehiculo
    Vehiculo(String marca, String modelo, int cilindrada, String combustible, int capacidadPasajeros) {
        this.marca = marca;
        this.modelo = modelo;
        this.cilindrada = cilindrada;
        this.combustible = combustible;
        this.capacidadPasajeros = capacidadPasajeros;
    }

    void mostrar() {
        System.out.println("Vehículo: " + marca + " " + modelo + 
                           " | " + cilindrada + "cc" + 
                           " | Combustible: " + combustible + 
                           " | Capacidad: " + capacidadPasajeros + " pasajeros");
    }
}
