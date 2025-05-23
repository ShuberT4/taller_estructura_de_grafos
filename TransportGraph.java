import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class TransportGraph {
   
    private Map<City, List<Route>> adjacencyList;
    private Map<String, City> citiesMap; // Para buscar ciudades por nombre fácilmente

    public TransportGraph() {
        this.adjacencyList = new HashMap<>();
        this.citiesMap = new HashMap<>();
    }

    public void addCity(String cityName) {
        City city = new City(cityName);
        adjacencyList.putIfAbsent(city, new ArrayList<>());
        citiesMap.put(cityName.toLowerCase(), city);
    }

    public City getCity(String name) {
        return citiesMap.get(name.toLowerCase());
    }

    public Collection<City> getAllCities() {
        return Collections.unmodifiableCollection(citiesMap.values());
    }

    public void addRoute(String city1Name, String city2Name, int distance, int tollCost) {
        City city1 = getCity(city1Name);
        City city2 = getCity(city2Name);

        if (city1 == null || city2 == null) {
            System.out.println("Error: Una o ambas ciudades no existen al agregar la ruta: " + city1Name + " - " + city2Name);
            return;
        }

        // Rutas bidireccionales
        adjacencyList.get(city1).add(new Route(city2, distance, tollCost));
        adjacencyList.get(city2).add(new Route(city1, distance, tollCost));
    }

    // Enum para especificar el tipo de peso a considerar (distancia o costo de peaje)
    private enum WeightType {
        DISTANCE,
        TOLL_COST
    }

    // Implementación del algoritmo de Dijkstra
    private Pathesult findPath(City start, City end, WeightType weightType) {
        if (start == null || end == null) {
            return new Pathesult(null, 0, ""); // Ciudades no válidas
        }
        if (start.equals(end)) {
             return new Pathesult(Collections.singletonList(start), 0, weightType == WeightType.DISTANCE ? "Distancia" : "Costo de Peaje");
        }

        Map<City, Integer> weights = new HashMap<>(); // Almacena el peso mínimo desde el inicio hasta la ciudad
        Map<City, City> predecessors = new HashMap<>(); // Almacena el predecesor en la ruta óptima
        PriorityQueue<Map.Entry<City, Integer>> priorityQueue = new PriorityQueue<>(Map.Entry.comparingByValue());
        Set<City> visited = new HashSet<>();

        // Inicialización
        for (City city : adjacencyList.keySet()) {
            weights.put(city, Integer.MAX_VALUE);
        }
        weights.put(start, 0);
        priorityQueue.add(new AbstractMap.SimpleEntry<>(start, 0));

        while (!priorityQueue.isEmpty()) {
            City currentCity = priorityQueue.poll().getKey();

            if (currentCity.equals(end)) {
                // Ruta encontrada, reconstruir
                LinkedList<City> path = new LinkedList<>();
                City step = end;
                while (step != null) {
                    path.addFirst(step);
                    step = predecessors.get(step);
                }
                String typeString = weightType == WeightType.DISTANCE ? "Distancia" : "Costo de Peaje";
                return new Pathesult(path, weights.get(end), typeString);
            }

            if (visited.contains(currentCity)) {
                continue;
            }
            visited.add(currentCity);

            if (!adjacencyList.containsKey(currentCity)) continue; // Si la ciudad no tiene rutas de salida

            for (Route route : adjacencyList.get(currentCity)) {
                City neighbor = route.getDestination();
                if (visited.contains(neighbor)) {
                    continue;
                }

                int weightOfEdge = (weightType == WeightType.DISTANCE) ? route.getDistance() : route.getTollCost();
                int newWeight = weights.get(currentCity) + weightOfEdge;

                if (newWeight < weights.get(neighbor)) {
                    weights.put(neighbor, newWeight);
                    predecessors.put(neighbor, currentCity);
                    // Eliminar y volver a agregar para actualizar la prioridad si ya está en la cola
                    priorityQueue.removeIf(entry -> entry.getKey().equals(neighbor));
                    priorityQueue.add(new AbstractMap.SimpleEntry<>(neighbor, newWeight));
                }
            }
        }
        // No se encontró ruta
        String typeString = weightType == WeightType.DISTANCE ? "Distancia" : "Costo de Peaje";
        return new Pathesult(null, 0, typeString);
    }

    public Pathesult findShortestPath(String startCityName, String endCityName) {
        City start = getCity(startCityName);
        City end = getCity(endCityName);
        if (start == null) {
            System.out.println("Ciudad de origen '" + startCityName + "' no encontrada.");
            return new Pathesult(null, 0, "Distancia");
        }
        if (end == null) {
            System.out.println("Ciudad de destino '" + endCityName + "' no encontrada.");
            return new Pathesult(null, 0, "Distancia");
        }
        return findPath(start, end, WeightType.DISTANCE);
    }

    public Pathesult findCheapestPath(String startCityName, String endCityName) {
        City start = getCity(startCityName);
        City end = getCity(endCityName);
         if (start == null) {
            System.out.println("Ciudad de origen '" + startCityName + "' no encontrada.");
            return new Pathesult(null, 0, "Costo de Peaje");
        }
        if (end == null) {
            System.out.println("Ciudad de destino '" + endCityName + "' no encontrada.");
            return new Pathesult(null, 0, "Costo de Peaje");
        }
        return findPath(start, end, WeightType.TOLL_COST);
    }
}

