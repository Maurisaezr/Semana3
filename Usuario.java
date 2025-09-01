public class Usuario {
    public String nombre;
    public String email;
    public String clave;
    private Vehiculo vehiculo; // cada usuario puede tener un vehículo asociado
// contructor para Usuario 
    public Usuario(String nombre, String email, String clave) {
        this.nombre = nombre;
        this.email = email;
        this.clave = clave;
    }

    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getClave() { return clave; }
//  se asigna o asocia un vehiculo por usuario 
    public void asignarVehiculo(Vehiculo v) {
        this.vehiculo = v;
    }
// metodo que muestra datos  de usuario 
    public void mostrarDatos() {
        System.out.println("Usuario: " + nombre + " | Email: " + email);
        if (vehiculo != null) {
            vehiculo.mostrar();
        } else {
            System.out.println("→ Sin vehículo registrado");
        }
    }
}
