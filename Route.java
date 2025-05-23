public class Route {

    City destination;
    int distance; // en kilómetros
    int tollCost; // en unidades monetarias

    public Route(City destination, int distance, int tollCost) {
        this.destination = destination;
        this.distance = distance;
        this.tollCost = tollCost;
    }

    public City getDestination() {
        return destination;
    }

    public int getDistance() {
        return distance;
    }

    public int getTollCost() {
        return tollCost;
    }

    @Override
    public String toString() {
        return "-> " + destination.getName() + " (Dist: " + distance + "km, Peaje: $" + tollCost + ")";
    }
}

