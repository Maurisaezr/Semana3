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
                        
                        // EN el CASE 1  chequeamos  si es GMAIL 
                        if (!esGmail(email)) {
                            System.out.println(ANSI_RED + "❌ El correo debe ser @gmail.com" + ANSI_RESET);
                        break;
                        }
                        String clave  = leerLinea(sc, "Clave: ", ANSI_GREEN);
                        
                        Usuario nuevo = new Usuario(nombre, email, clave);
                        if (auth.registrar(nuevo)) {
                            System.out.println(ANSI_GREEN + "Registro exitoso. Ya puedes iniciar sesión." + ANSI_RESET);
                        } else {
                            System.out.println(ANSI_RED + "No se pudo registrar (email duplicado o sin espacio)." + ANSI_RESET);
                        }
                        break;

                    case "2": // Login
                        System.out.println(ANSI_BLUE + "\n--- Iniciar sesión ---" + ANSI_RESET);
                        String e = leerLinea(sc, "Email (Gmail): ", ANSI_GREEN);  //aca para GMAIL 
                        String c = leerLinea(sc, "Clave: ", ANSI_GREEN);
                        // ACA USAMOS EL AMIGO HELPER CORRESPONDIENTE AL CHEQUEO GMAIL
                        if (!esGmail(e)) {
                            System.out.println(ANSI_RED + "❌ El correo debe ser @gmail.com" + ANSI_RESET);
                            break;
                        }   
                        // llama a la clase AUTH  metodo Login  y en el IF  chequea si viene null 
                         Usuario u = auth.login(e, c);
                        if (u != null) {
                            usuarioActual = u;
                            canasta.vaciar(); // nueva sesión, canasta limpia asi partimos de CERO  con la canasta limpia 
                            // mensajito para saber si inicio sesion 
                            System.out.println(ANSI_GREEN + "Sesión iniciada. ¡Hola, " + usuarioActual.getNombre() + "!" + ANSI_RESET);
                        } else {
                            System.out.println(ANSI_RED + "❌ Credenciales inválidas." + ANSI_RESET);
                        }
                        break;

                    case "3":
                        salir = true;
                        System.out.println(ANSI_YELLOW + "Hasta pronto 👋" + ANSI_RESET);
                        break;

                    default:
                        System.out.println(ANSI_RED + "⚠ Opción inválida." + ANSI_RESET);
                }
            } else {
                // === Menú autenticado (compra) ===
                System.out.println(ANSI_BLUE + "\n=== Menú (Usuario: " + usuarioActual.getNombre() + ") ===" + ANSI_RESET);
                System.out.println(ANSI_WHITE + "1) Ver catálogo" + ANSI_RESET);
                System.out.println(ANSI_WHITE + "2) Agregar producto a canasta" + ANSI_RESET);
                System.out.println(ANSI_WHITE + "3) Ver canasta" + ANSI_RESET);
                System.out.println(ANSI_WHITE + "4) Finalizar compra" + ANSI_RESET);
                System.out.println(ANSI_RED   + "5) Cerrar sesión" + ANSI_RESET);
                String op = leerLinea(sc, "Elige una opción: ", ANSI_GREEN);

                switch (op) {
                    case "1":
                        mostrarInventario(inventario);
                        break;

                    case "2":
                        mostrarInventario(inventario);
                        int idx = leerEnteroSeguro(sc, "Ingresa el índice del producto: ");
                        if (idx < 0 || idx >= inventario.length) {
                            System.out.println(ANSI_RED + "⚠ Índice fuera de rango." + ANSI_RESET);
                            break;
                        }
                        int cant = leerEnteroSeguro(sc, "Cantidad: ");
                        if (cant <= 0) {
                            System.out.println(ANSI_RED + "⚠ La cantidad debe ser > 0." + ANSI_RESET);
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
                        System.out.printf(ANSI_PURPLE + "🧾 Total a pagar: $%.2f%n" + ANSI_RESET, total);
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

                    default:
                        System.out.println(ANSI_RED + "⚠ Opción inválida." + ANSI_RESET);
                }
            }
        }

        sc.close();
    }
}
