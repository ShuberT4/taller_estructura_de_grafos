package debufg_test;

class TestDebug {
    public static void main(String[] args) {
        int count = 100;
        String greeting = "Hola Debug";
        double value = 99.5;

        // PON UN BREAKPOINT EN LA SIGUIENTE LÍNEA:
        System.out.println(greeting + " - Número: " + count + ", Valor: " + value);

        int finalValue = count * 2;
        // Y OTRO BREAKPOINT AQUÍ SI QUIERES:
        System.out.println("Valor final: " + finalValue);
    }
}