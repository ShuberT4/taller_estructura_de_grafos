import java.util.Scanner;

public class SimulacionRedEstructuraDeDatos {

public static void main(String[] args) {
        TransportGraph graph = new TransportGraph();

        // 1. Creación del Grafo - Definición de Ciudades
        String[] cityNames = {"Valledupar", "SantaMarta", "Riohacha", "Barranquilla", "Cartagena", "Sincelejo", "Monteria"};
        for (String name : cityNames) {
            graph.addCity(name);
        }

        // 2. Definición de los Datos de las Rutas (Distancia en km, Costo de Peaje en unidades monetarias)
        // Asegurarse de que cada ciudad tenga al menos una ruta y haya variedad.
        graph.addRoute("Valledupar", "SantaMarta", 200, 30); // Relativamente corta, peaje moderado
        graph.addRoute("Valledupar", "Riohacha", 180, 20);   // Corta, peaje bajo
        graph.addRoute("Valledupar", "Sincelejo", 280, 50);  // Larga, peaje alto

        graph.addRoute("SantaMarta", "Barranquilla", 100, 60); // Corta, peaje alto (turística)
        graph.addRoute("SantaMarta", "Riohacha", 170, 25);

        graph.addRoute("Riohacha", "Barranquilla", 280, 35); // Larga, peaje moderado

        graph.addRoute("Barranquilla", "Cartagena", 130, 70); // Corta, peaje muy alto (muy transitada)
        graph.addRoute("Barranquilla", "Sincelejo", 200, 40);

        graph.addRoute("Cartagena", "Sincelejo", 190, 30);
        graph.addRoute("Cartagena", "Monteria", 250, 80);   // Más larga, peaje alto para evitar Sincelejo

        graph.addRoute("Sincelejo", "Monteria", 90, 15);    // Corta y barata

        // 3. Método de Entrada y Salida
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ciudades disponibles en la red de transporte:");
        graph.getAllCities().forEach(city -> System.out.println("- " + city.getName()));
        System.out.println("-------------------------------------------");

        while (true) {
            System.out.print("\nIngrese la ciudad de origen (o 'salir' para terminar): ");
            String startCityName = scanner.nextLine();
            if (startCityName.equalsIgnoreCase("salir")) {
                break;
            }

            System.out.print("Ingrese la ciudad de destino: ");
            String endCityName = scanner.nextLine();

            City startCity = graph.getCity(startCityName);
            City endCity = graph.getCity(endCityName);

            if (startCity == null) {
                System.out.println("Error: La ciudad de origen '" + startCityName + "' no existe en la red.");
                continue;
            }
            if (endCity == null) {
                System.out.println("Error: La ciudad de destino '" + endCityName + "' no existe en la red.");
                continue;
            }
            if (startCityName.equalsIgnoreCase(endCityName)) {
                System.out.println("La ciudad de origen y destino son la misma.");
                System.out.println("Ruta más corta: " + startCityName + ", Distancia Total: 0 km");
                System.out.println("Ruta más económica: " + startCityName + ", Costo de Peaje Total: 0 unidades monetarias");
                continue;
            }


            // Búsqueda de rata más Corta
            System.out.println("\n--- Buscando Ruta Más Corta ---");
            Pathesult shortestPath = graph.findShortestPath(startCityName, endCityName);
            if (shortestPath.isFound()) {
                System.out.println(shortestPath);
            } else {
                System.out.println("No se encontró una ruta por distancia entre " + startCityName + " y " + endCityName + ".");
            }

            // Búsqueda de Ruta más Económica
            System.out.println("\n--- Buscando Ruta Más Económica ---");
            Pathesult cheapestPath = graph.findCheapestPath(startCityName, endCityName);
            if (cheapestPath.isFound()) {
                System.out.println(cheapestPath);
            } else {
                System.out.println("No se encontró una ruta por costo de peaje entre " + startCityName + " y " + endCityName + ".");
            }
            System.out.println("-------------------------------------------");
        }

        scanner.close();
        System.out.println("Simulación terminada.");
    }
}