import java.util.Scanner;

public class Auth {
    Usuario[] usuarios;
    int contador;
// esto es paa manejar la cantidad de usuarios que se pueden registrar esta en APP.java para llevar un control 
    public Auth(int capacidad) {
        usuarios = new Usuario[capacidad];
        contador = 0;
    }
//  cuenta la cantidad de usuarios registraos 
    public boolean registrar(Usuario u) {
        if (contador < usuarios.length) {
            usuarios[contador++] = u;
            return true;
        }
        return false;
    }

    public Usuario login(String email, String clave) {
        for (int i = 0; i < contador; i++) {
            if (usuarios[i].email.equals(email) && usuarios[i].clave.equals(clave)) {
                return usuarios[i];
            }
        }
        return null;
    }

    // Método para registrar un usuario con su vehículo
    public void registrarConVehiculo(Scanner sc) {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Clave: ");
        String clave = sc.nextLine();

        Usuario nuevo = new Usuario(nombre, email, clave);

        // Ahora pedimos datos del vehículo
        System.out.println("--- Datos del Vehículo ---");
        System.out.print("Marca: ");
        String marca = sc.nextLine();
        System.out.print("Modelo: ");
        String modelo = sc.nextLine();
        System.out.print("Cilindrada (cc): ");
        int cilindrada = Integer.parseInt(sc.nextLine());
        System.out.print("Combustible: ");
        String combustible = sc.nextLine();
        System.out.print("Capacidad de pasajeros: ");
        int capacidad = Integer.parseInt(sc.nextLine());
       // genera objeto vehiculo
        Vehiculo v = new Vehiculo(marca, modelo, cilindrada, combustible, capacidad);
        nuevo.asignarVehiculo(v);

        if (registrar(nuevo)) {
            System.out.println(" Usuario registrado con su vehículo.");
        } else {
            System.out.println(" No se pudo registrar (capacidad llena).");
        }
    }
}
