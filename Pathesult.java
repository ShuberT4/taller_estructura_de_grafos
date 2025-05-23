import java.util.List;
import java.util.stream.Collectors;

public class Pathesult {
    
    List<City> path;
    int totalValue; // Puede ser distancia total o costo total de peaje
    String type; // "Distancia" o "Costo de Peaje"

    public Pathesult(List<City> path, int totalValue, String type) {
        this.path = path;
        this.totalValue = totalValue;
        this.type = type;
    }

    public List<City> getPath() {
        return path;
    }

    public int getTotalValue() {
        return totalValue;
    }

    public boolean isFound() {
        return path != null && !path.isEmpty();
    }

    @Override
    public String toString() {
        if (!isFound()) {
            return "No se encontró una ruta.";
        }
        String pathString = path.stream().map(City::getName).collect(Collectors.joining(" -> "));
        return "Ruta: " + pathString + 
                "\n" + type + " Total: " + totalValue +
            (type.equals("Distancia") ? totalValue + " km" : " $");
    }
}

