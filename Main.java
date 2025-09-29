import java.util.List;
import java.util.Scanner;

public class Main {
    // CSV-Datei im Projektordner oder als absoluter Pfad
    private static final String DATEI = "cafes.csv";

    public static void main(String[] args) {
        BreakfastManager manager = new BreakfastManager(DATEI);

        // Optional: Zeige den Pfad zur CSV-Datei
        manager.printFilePath();

        Scanner sc = new Scanner(System.in, "UTF-8");
        boolean exit = false;

        while (!exit) {
            System.out.println("=== Frühstücks-Cafés Menü ===");
            System.out.println("1 - Alle Cafés anzeigen");
            System.out.println("2 - Nach Stadtteil filtern");
            System.out.println("3 - Neues Café hinzufügen");
            System.out.println("4 - Café löschen");
            System.out.println("0 - Beenden");
            System.out.print("Auswahl: ");
            String cmd = sc.nextLine().trim();

            switch (cmd) {
                case "1" -> listAll(manager);
                case "2" -> filter(manager, sc);
                case "3" -> add(manager, sc);
                case "4" -> remove(manager, sc);
                case "0" -> exit = true;
                default -> System.out.println("Unbekannte Eingabe.");
            }
            System.out.println();
        }
        sc.close();
        System.out.println("Programm beendet.");
    }

    private static void listAll(BreakfastManager manager) {
        List<BreakfastSpot> all = manager.listAll();
        if (all.isEmpty()) System.out.println("Keine Einträge vorhanden.");
        else all.forEach(System.out::println);
    }

    private static void filter(BreakfastManager manager, Scanner sc) {
        System.out.print("Stadtteil eingeben: ");
        String nb = sc.nextLine().trim();
        List<BreakfastSpot> res = manager.filterByNeighborhood(nb);
        if (res.isEmpty()) System.out.println("Keine Treffer.");
        else res.forEach(System.out::println);
    }

    private static void add(BreakfastManager manager, Scanner sc) {
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Adresse: ");
        String addr = sc.nextLine().trim();
        System.out.print("Stadtteil: ");
        String nb = sc.nextLine().trim();
        System.out.print("Bewertung (0.0 - 5.0): ");
        double rating;
        try {
            rating = Double.parseDouble(sc.nextLine().trim());
            if (rating < 0) rating = 0;
            if (rating > 5) rating = 5;
        } catch (Exception e) {
            rating = 4.5; // Standardwert
        }
        manager.addSpot(name, addr, nb, rating);
        System.out.println("Café hinzugefügt!");
    }

    private static void remove(BreakfastManager manager, Scanner sc) {
        System.out.print("ID zum Löschen: ");
        int id;
        try {
            id = Integer.parseInt(sc.nextLine().trim());
        } catch (Exception e) {
            System.out.println("Ungültige ID.");
            return;
        }
        boolean ok = manager.removeById(id);
        System.out.println(ok ? "Café gelöscht." : "ID nicht gefunden.");
    }
}

