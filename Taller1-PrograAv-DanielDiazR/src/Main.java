//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import ucn.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        ArchivoEntrada arch1 = new ArchivoEntrada("Productos.txt");
        int lineaActual = 0;
        String[][] Archivo = new String[100][4];

        //Lectura de archivo .txt
        while (!arch1.isEndFile()) {
            Registro reg1 = arch1.getRegistro();
            Archivo[lineaActual][0] = reg1.getString();
            Archivo[lineaActual][1] = reg1.getString();
            Archivo[lineaActual][2] = reg1.getString();
            Archivo[lineaActual][3] = reg1.getString();
            lineaActual++;
        }

        //Arreglo de lectura
        String[] nombreProducto = new String[lineaActual];
        int[] precioUnitario = new int[lineaActual];
        int[] cantidad = new int[lineaActual];
        String[] categoria = new String[lineaActual];

        for (int i = 0; i < lineaActual; i++) {
            nombreProducto[i] = Archivo[i][0];
            precioUnitario[i] = Integer.parseInt(Archivo[i][1]);
            cantidad[i] = Integer.parseInt(Archivo[i][2]);
            categoria[i] = Archivo[i][3];
        }


        while (true) {
            //Variables para estadisticas
            String productoCompra = "";
            int cantidadCompra = 0;
            int dineroRecaudado = 0;
            int cantidadVentas = 0;
            int[] ventaProducto = new int[0];


            //Imprimir el menu
            StdOut.println("""
                    ************Menu**************
                    [1] Ver Productos.
                    [2] Registrar Compra.
                    [3] Estadísticas.
                    [4] Salir.
                    ******************************
                    Ingrese su opcion:
                    """);
            String opcion;
            opcion = StdIn.readString();

            //Opcion 1 Ver productos
            if (opcion.equalsIgnoreCase("1")) {
                MostrarProductos(nombreProducto, precioUnitario, cantidad, categoria, lineaActual);
            }
            //Opcion 2 Registrar compras
            if (opcion.equalsIgnoreCase("2")) {
                Compra(nombreProducto, precioUnitario, cantidad, productoCompra, cantidadCompra, lineaActual, dineroRecaudado, cantidadVentas, ventaProducto);

            }
            //Opcion 3 Estadisticas
            if (opcion.equalsIgnoreCase("3")) {
                Estadisticas(cantidadVentas,dineroRecaudado,nombreProducto,categoria,ventaProducto,lineaActual);
            }

            //Opcion 4 Salir
            if (opcion.equalsIgnoreCase("4")) {
                StdOut.println("Muchas gracias por su visita, adios.");
                break;
            }
            //Mensaje en caso de que se escriba una opcion distinta a 1, 2, 3 o 4
            StdOut.println("La opcion seleccionada no esta disponible, porfavor selesccione una de las 4 opciones disponibles");


        }


    }

    //Mostrar los productos disponibles con la opcion 1
    public static void MostrarProductos(String[] nombreProducto, int[] precioUnitario, int[] cantidad, String[] categoria, int lineaActual) {
        for (int i = 0; i < lineaActual; i++) {
            //Imprimir los nombres de los prductos
            StdOut.println("* * * * * * Producto * * * * * *\n" +
                    "Nombre: " + nombreProducto[i]);
            //Imprimir la informacion de los productos en caso de que el stock sea diferente a 0
            if (cantidad[i] != 0) {
                StdOut.println(
                        "Precio unitario: " + precioUnitario[i] + "\n" +
                                "Cantidad: " + cantidad[i] + "\n" +
                                "Categoria: " + categoria[i]);
            }
            //Imprimir mensaje en caso de no haber Stock
            else {
                StdOut.println("No disponibre");
            }
        }
    }

    //Dar la opcion de compra con la opcion 2
    public static int[] Compra(String[] nombreProducto, int[] precioUnitario, int[] cantidad, String productoCompra, int cantidadCompra, int lineaActual, int dineroRecaudado, int cantidadVentas, int[] ventaProducto) {
        //Pedir al usuario que escriba el nombre del producto
        StdOut.println("Que producto desea comprar: ");
        productoCompra = StdIn.readString();
        for (int i = 0; i < lineaActual; i++) {
            if (productoCompra.equalsIgnoreCase(nombreProducto[i])) {
                //Pedir la usuario que escriba la cantidad del producto que desea
                StdOut.println("Cuanta cantidad de este producto desea: ");
                cantidadCompra = StdIn.readInt();
                //Validacion para que la cantidad  de compra no sea un numero negativo o 0
                if (cantidadCompra <= 0) {
                    StdOut.println("ERROR... Lo sentimos, no tememos disponoble esa cantidad de este producto...");
                    break;
                }
                //Imprimir la boleta si la cantidad de compra es menor a la cantidad que tiene el producto
                if (cantidadCompra <= cantidad[i]) {

                    //Se actualiza el valor de precioFinal
                    int precioFinal = precioUnitario[i] * cantidadCompra;

                    StdOut.println("***********BOLETA***********\n" +
                            "* Nombre del producto: " + nombreProducto[i] + "*\n" +
                            "+ Cantidad comprada: " + cantidadCompra + "*\n" +
                            "* Precio unitario: $" + precioUnitario[i] + "*\n" +
                            "Precio Final: $" + precioFinal + "*\n" +
                            "***********************************");
                    //Se resta la cantidad comprada a la cantidad disponible del producto
                    cantidad[i] -= cantidadCompra;
                    //Se le suma el precio de la venta al dinero recaudado por la tienda
                    dineroRecaudado += precioFinal;
                    //Se le suma la cantidad de productos comprados a la cantidad total de productos vendidos
                    cantidadVentas += cantidadCompra;
                    //Se le suma la cantidad de ventas a tal producto
                    ventaProducto[i] += cantidadCompra;


                }
                //Mensaje de error en caso de que la cantidad de compra  sea mayor a la cantidad del producto
                else {
                    StdOut.println("ERROR... Lo sentimos, no tememos disponoble esa cantidad de este producto...");
                    break;
                }
            }
        }
        return cantidad;

    }

    public static void Estadisticas(int cantidadVentas, int dineroRecaudado, String[] nombreProducto, String[] categoria, int[] ventaProducto, int lineaActual) {

        // Producto más vendido
        int maxVentas = 0;
        String productoMasVendido = "";
        for (int i = 0; i < lineaActual; i++) {
            if (ventaProducto[i] > maxVentas) {
                maxVentas = ventaProducto[i];
                productoMasVendido = nombreProducto[i];
            }
        }
        // Categoría más vendida
        String categoriaMas = "";
        int maxCat = 0;
        for (int i = 0; i < lineaActual; i++) {
            int sumaCat = 0;
            for (int j = 0; j < lineaActual; j++) {
                if (categoria[j].equals(categoria[i])) {
                    sumaCat += ventaProducto[j];
                }
            }
            if (sumaCat > maxCat) {
                maxCat = sumaCat;
                categoriaMas = categoria[i];
            }

            //Imprimir las estadisticas
            StdOut.println(
                    "**************Estadisticas**************\n" +
                            "a) Cantidad total de productos vendidos: " + cantidadVentas + "*\n" +
                            "b) Producto más vendido: " + productoMasVendido + "*\n" +
                            "c) Dinero total recaudado: " + dineroRecaudado + "*\n" +
                            "d) Categoría mas vendida: " + categoriaMas + "*\n" +
                            "****************************************");
        }

    }
}