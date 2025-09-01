/*
 * ==========================================
 *  Nombre del Programa: App Móvil (Consola)
 *  Empresa: Distribuidora Pepito
 *  Programador: Mauricio Sáez
 *  Fecha de Creación: 26-08-2025
 *  Versión: 1.0
 *  Descripción: MVP de registro/login + compra con canasta (arrays simples)
 * ==========================================
 */

/* 
TIPS 
https://www.geeksforgeeks.org/java/how-to-print-colored-text-in-java-console/    PARA LOS COLORES 
https://www.freecodecamp.org/news/java-getters-and-setters/    PARA LOS GETTERS
https://www.w3schools.com/java/java_encapsulation.asp    LO MISMO 
https://medium.com/@AlexanderObregon/what-helper-functions-are-in-java-and-how-to-use-them-852676d4590c    LOS HELPERS

*/

/* importo paquetes */
import java.util.Scanner;

public class App {

    // === Colores ANSI para consola ===
// ACA EMPIEZO  A USAR COLORES PARA QUE SE VEA MEJOR LA CONSOLITA  COPIADO DE ACA https://www.geeksforgeeks.org/java/how-to-print-colored-text-in-java-console/  ANTERIORMENTE LO USABA PARA SCRIPT KSH ASI QUE ES SIMILAR
    public static final String ANSI_RESET  = "\u001B[0m";
    public static final String ANSI_RED    = "\u001B[31m";
    public static final String ANSI_GREEN  = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE   = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m"; // Magenta
    public static final String ANSI_CYAN   = "\u001B[36m";
    public static final String ANSI_WHITE  = "\u001B[37m";

    // === ACA AGREGAMOS UNOS HELPERS PARA QUE SE VEA MEJOR  TIPO METODO AUXILIAR AL LEER DE CONSOLA CON UN POCO DE AYUDA ===
    private static String leerLinea(Scanner sc, String prompt, String colorPrompt) {
        System.out.print(colorPrompt + prompt + ANSI_RESET);
        return sc.nextLine().trim();
    }

    private static int leerEnteroSeguro(Scanner sc, String prompt) {
        while (true) {
            String s = leerLinea(sc, prompt, ANSI_GREEN);
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println(ANSI_RED + " Debes ingresar un número válido." + ANSI_RESET);
            }
        }
    }

// ESTE HELPER  NO ESTOY SEGURO SI ARMARLO ACA QUE ES MAS RAPIDO  O  HACER UNA CLASE DESPACHO Y DENTRO DE ELLA UN METODO CALCULO DE COSTO para respetar POO
// a este metodo recibe por parametro el total de compra  y la distancia asi aplico reglas de negocio 
private static double calcularDespacho(double totalCompra, int distanciaKm) {
    if (distanciaKm > 20) {
        // fuera de cobertura en el caso de estudio
        return -1; // señalamos inválido
    }
    if (totalCompra >= 50000) {
        return 0;
    } else if (totalCompra >= 25000) {
        return 150.0 * distanciaKm;
    } else {
        return 300.0 * distanciaKm;
    }
}




//  ESTE OTRO HELPER AYUDA PARA VERIFICAR SI ES GMAIL EL CORREO , y verifica las minus
private static boolean esGmail(String email) {
    return email != null && email.toLowerCase().endsWith("@gmail.com");
}


    // === PEqueña ventanita para emular una especie de banner ===
    private static void banner() {
        System.out.println(ANSI_CYAN + "======================================" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "|        Distribuidora Pepito        |" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "|  Fecha: 2025-08-26   Versión: 1.0  |" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "|         App Móvil (Consola)        |" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "======================================" + ANSI_RESET);
        System.out.println();
    }

    // EN esta vista inventario lo pongo afuera -  Ya que en java no puedo declarar metodos dentro de metodos, python si lo hace 
    private static void mostrarInventario(Producto[] inventario) {  // aca el metodo mostrarInventario recibe el array de mas abajo 
        System.out.println(ANSI_BLUE + "\nCatálogo de Productos:" + ANSI_RESET);  // pequeño mensake para mostrar los productos 
        for (int i = 0; i < inventario.length; i++) {  // lo recorre 
            System.out.printf("[%d] %-15s $%.2f%n", i, inventario[i].getNombre(), inventario[i].getPrecio());   // lo imprime con un formato  
        } 
    }

    public static void main(String[] args) {
        banner(); // llamamos al metodo banner de mas arriba  

        //CREO UN INVENTARIO FIJO  QUIZA PODRIA UTILIZAR UN SQLITE PARA MAS COMODIDAD PERO NO LE AGREGO COMPLEJIDAD AHORA 
        Producto[] inventario = {
            new Producto("Arroz 1kg", 1200),
            new Producto("Aceite 1L", 2500),
            new Producto("Azúcar 1kg", 1100),
            new Producto("Fideos 500g", 900),
            new Producto("Harina 1kg", 1300)
        };

        // Autenticación (hasta 10 usuarios  aunque en realidad si es esta tarea una emulacion de una APP movil deberia tener solo 1 registro por Dispositivo )
        Auth auth = new Auth(10);

        // Canasta del usuario autenticado (hasta 10 ítems)
        Canasta canasta = new Canasta(10);

        // para leer la entrada estandar del sistema
        Scanner sc = new Scanner(System.in);
        boolean salir = false;
        Usuario usuarioActual = null;

        while (!salir) {
            if (usuarioActual == null) {
                // === Pantalla de bienvenida tipo "app" === BUSQUE EL VECTOR EN NEGRO EN GOOGLE Y LO TRANSFORME ACA https://www.asciiart.eu/image-to-ascii
                
                System.out.println(ANSI_BLUE + "   +#.:###########+.  "            + ANSI_RESET);                       
                System.out.println(ANSI_BLUE + "   ::..::::::::::::.    "        + ANSI_RESET);                         
                System.out.println(ANSI_BLUE + "   +%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%=.  "        + ANSI_RESET);        
                System.out.println(ANSI_BLUE + "   *@:.............................:@*.  "     + ANSI_RESET);           
                System.out.println(ANSI_BLUE + "   *@::@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*.   "        + ANSI_RESET);       
                System.out.println(ANSI_BLUE + "   *@:                             :@%++++++++=..  "     + ANSI_RESET); 
                System.out.println(ANSI_BLUE + "  *@:                             :@%-------=@#..  "     + ANSI_RESET);
                System.out.println(ANSI_BLUE + "   *@:                             :@#.=@@@@@@@@@:..   " + ANSI_RESET); 
                System.out.println(ANSI_BLUE + "   *@:                             :@#.*@.......#@-.. "   + ANSI_RESET);
                System.out.println(ANSI_BLUE + "   *@:                             :@#.*@.      .*@=.. "  + ANSI_RESET);
                System.out.println(ANSI_BLUE + "   *@:                             :@#.*@.       .=@*.  " + ANSI_RESET);
                System.out.println(ANSI_BLUE + "  *@:                             :@#.*@%#########%@%.. " + ANSI_RESET);
                System.out.println(ANSI_BLUE + "   *@:                             :@#..::::::::::::-@*.." + ANSI_RESET);
                System.out.println(ANSI_BLUE + "   *@:                             :@#.             :@*. " + ANSI_RESET);
                System.out.println(ANSI_BLUE + "   *@:      ..--:..                :@#.  ..---..    :@*. " + ANSI_RESET);
                System.out.println(ANSI_BLUE + "   *@@@@@-.+@@*+#@%:.#@@@@@@@@@@@@@@@#..=@@*+#@@-.+@@@*. " + ANSI_RESET);
                System.out.println(ANSI_BLUE + "   *@:....+@=.....#@:..............:@#.=@+.....*@-..:@*. " + ANSI_RESET);
                System.out.println(ANSI_BLUE + "   +@@@@@@@@..+%:.=@@@@@@@@@@@@@@@@@@@@@@. =@-.-@@@@@@+. " + ANSI_RESET);
                System.out.println(ANSI_BLUE + "   .     .=@*....:@%.                  -@#....:%@:   ..  " + ANSI_RESET);
                System.out.println(ANSI_BLUE + "          ..#@@@@@+.                   ..#@@@@@*..      " + ANSI_RESET);
                System.out.println(ANSI_BLUE + "            .......                        .....       "+ ANSI_RESET);
                System.out.println(ANSI_BLUE + "\n=== Bienvenido a la App de Pepito ===" + ANSI_RESET);
                System.out.println(ANSI_WHITE + "1) Registrarse" + ANSI_RESET);
                System.out.println(ANSI_WHITE + "2) Iniciar sesión" + ANSI_RESET);
                System.out.println(ANSI_RED   + "3) Salir" + ANSI_RESET);
                String op = leerLinea(sc, "Elige una opción: ", ANSI_GREEN);
//  ACA VAMOS LLAMANDO A LAS DISTINTAS OPCIONES QUE SE ENCUENTRAN- EJEMPLO AL ENTRAR EN CASE 1  PASA A REGISTRO 
                switch (op) {
                    case "1": // Registro
    System.out.println(ANSI_BLUE + "\n--- Registro ---" + ANSI_RESET);
    String nombre = leerLinea(sc, "Nombre: ", ANSI_GREEN);
    String email  = leerLinea(sc, "Email: ", ANSI_GREEN);
// es gmail ? 
    if (!esGmail(email)) {
        System.out.println(ANSI_RED + "❌ El correo debe ser @gmail.com" + ANSI_RESET);
        break;
    }
    String clave  = leerLinea(sc, "Clave: ", ANSI_GREEN);
// se crea un nuevo OBJETO USUARIO 
    Usuario nuevo = new Usuario(nombre, email, clave);

    // === Nuevo bloque: pedimos datos del vehículo ===
    
    System.out.println(ANSI_BLUE + "\n--- Datos del Vehículo ---" + ANSI_RESET);
    String marca = leerLinea(sc, "Marca: ", ANSI_GREEN);
    String modelo = leerLinea(sc, "Modelo: ", ANSI_GREEN);
    int cilindrada = leerEnteroSeguro(sc, "Cilindrada (cc): ");
    String combustible = leerLinea(sc, "Combustible: ", ANSI_GREEN);
    int capacidad = leerEnteroSeguro(sc, "Capacidad de pasajeros: ");
    
// Se crea un NUEVO OBJETO VEHICULO
    Vehiculo v = new Vehiculo(marca, modelo, cilindrada, combustible, capacidad);
    nuevo.asignarVehiculo(v);

// Alguna comprobacion de si el usuario es nuevo 
    if (auth.registrar(nuevo)) {
        System.out.println(ANSI_GREEN + "Registro exitoso. Ya puedes iniciar sesión." + ANSI_RESET);
    } else {
        System.out.println(ANSI_RED + "No se pudo registrar (email duplicado o sin espacio)." + ANSI_RESET);
    }
    break;
//  CLARAMENTE en este case hacemos LOGIN
                    case "2": // Login
                        System.out.println(ANSI_BLUE + "\n--- Iniciar sesión ---" + ANSI_RESET);
                        String e = leerLinea(sc, "Email (Gmail): ", ANSI_GREEN);
                        String c = leerLinea(sc, "Clave: ", ANSI_GREEN);

                        if (!esGmail(e)) {
                            System.out.println(ANSI_RED + "❌ El correo debe ser @gmail.com" + ANSI_RESET);
                            break;
                        }
                            // llamamos a clase auth y metodo login 
                        Usuario u = auth.login(e, c);
                           // USUARIO NO PUEDE VENIR NULO 
                           if (u != null) {
                            usuarioActual = u;
                            canasta.vaciar();
                            System.out.println(ANSI_GREEN + "Sesión iniciada. ¡Hola, " + usuarioActual.getNombre() + "!" + ANSI_RESET);
                            usuarioActual.mostrarDatos(); // <<< Muestra usuario + vehículo
                            } else {
                            System.out.println(ANSI_RED + "❌ Credenciales inválidas." + ANSI_RESET);
                            }
                            break;
// CASE DE SALIDA
                    case "3":
                        salir = true;
                        System.out.println(ANSI_YELLOW + "Hasta pronto " + ANSI_RESET);
                        break;

                    default:
                        System.out.println(ANSI_RED + " Opción inválida." + ANSI_RESET);
                }
            } else {
                // === Menú autenticado (compra) ACA ES EL MENU MAGIA ===
                System.out.println(ANSI_BLUE + "\n=== Menú (Usuario: " + usuarioActual.getNombre() + ") ===" + ANSI_RESET);
                System.out.println(ANSI_WHITE + "1) Ver catálogo" + ANSI_RESET);
                System.out.println(ANSI_WHITE + "2) Agregar producto a canasta" + ANSI_RESET);
                System.out.println(ANSI_WHITE + "3) Ver canasta" + ANSI_RESET);
                System.out.println(ANSI_WHITE + "4) Finalizar compra" + ANSI_RESET);
                System.out.println(ANSI_WHITE+ " 6) Ver datos usuario " + ANSI_RESET);
                System.out.println(ANSI_RED   + "5) Cerrar sesión" + ANSI_RESET);
                String op = leerLinea(sc, "Elige una opción: ", ANSI_GREEN);
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------//
                // desde aca las opciones para seleccionar  // 
                switch (op) {
                    case "1":
                        mostrarInventario(inventario);
                        break;

                    case "2":
                        mostrarInventario(inventario);
                        int idx = leerEnteroSeguro(sc, "Ingresa el índice del producto: ");
                        if (idx < 0 || idx >= inventario.length) {
                            System.out.println(ANSI_RED + "Índice fuera de rango." + ANSI_RESET);
                            break;
                        }
                        int cant = leerEnteroSeguro(sc, "Cantidad: ");
                        if (cant <= 0) {
                            System.out.println(ANSI_RED + "La cantidad debe ser > 0." + ANSI_RESET);
                            break;
                        }
                        canasta.agregarProducto(inventario[idx], cant);
                        break;

                    case "3":
                        canasta.mostrarCanasta();
                        break;

                    case "4":
double total = canasta.total();
if (total <= 0) {
    System.out.println(ANSI_YELLOW + "🛒 Tu canasta está vacía." + ANSI_RESET);
    break;
}

// Pedimos distancia
int distancia = leerEnteroSeguro(sc, "Distancia al domicilio (km, máx 20): ");

// aca llamo al helper de arriba  y le paso  los parametros de las variables 
System.out.println("ACA IMPRIMO COMO DEBUG distancia:" + distancia + "aca total:" + total);
double costoDespacho = calcularDespacho(total, distancia);
if (costoDespacho < 0) {
    System.out.println(ANSI_RED + "❌ Fuera de cobertura: solo hasta 20 km." + ANSI_RESET);
    break;
}

double totalFinal = total + costoDespacho;

System.out.printf(ANSI_PURPLE + "🧾 Subtotal: $%.2f%n" + ANSI_RESET, total);
System.out.printf(ANSI_PURPLE + "🚚 Despacho: $%.2f%n" + ANSI_RESET, costoDespacho);
System.out.printf(ANSI_PURPLE + "💰 TOTAL:    $%.2f%n" + ANSI_RESET, totalFinal);

String conf = leerLinea(sc, "Confirmar compra (s/n): ", ANSI_GREEN).toLowerCase();
if (conf.equals("s")) {
    System.out.println(ANSI_GREEN + "✅ Compra realizada. ¡Gracias por tu pedido!" + ANSI_RESET);
    canasta.vaciar();
} else {
    System.out.println(ANSI_YELLOW + "ℹ Operación cancelada." + ANSI_RESET);
}
                        break;

                    case "5":
                        usuarioActual = null;
                        canasta.vaciar();
                        System.out.println(ANSI_YELLOW + "Sesión cerrada." + ANSI_RESET);
                        break;
                    case "6":

    usuarioActual.mostrarDatos();  

                        break;    

                    default:
                        System.out.println(ANSI_RED + "Opción inválida." + ANSI_RESET);
                }
            }
        }

        sc.close();
    }
}
