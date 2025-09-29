

	import java.util.List;
	import java.util.Scanner;

	/**
	 * Hauptklasse: Konsolenmenü für den Benutzer.
	 */
	public class Main {
	    private static final String STORAGE = "breakfasts.csv";

	    public static void main(String[] args) {
	        BreakfastManager manager = new BreakfastManager(STORAGE);
	        manager.seedIfEmpty();

	        Scanner sc = new Scanner(System.in, "UTF-8");
	        System.out.println("=== Top Frühstücks-Cafés in Hamburg ===");

	        boolean exit = false;
	        while (!exit) {
	            showMenu();
	            String cmd = sc.nextLine().trim();
	            switch (cmd) {
	                case "1" -> listAll(manager);
	                case "2" -> showTop(manager, sc);
	                case "3" -> filter(manager, sc);
	                case "4" -> add(manager, sc);
	                case "5" -> remove(manager, sc);
	                case "0" -> {
	                    exit = true;
	                    System.out.println("Auf Wiedersehen!");
	                }
	                default -> System.out.println("Unbekannte Eingabe.");
	            }
	            System.out.println();
	        }
	        sc.close();
	    }

	    private static void showMenu() {
	        System.out.println("Menü:");
	        System.out.println("1 - Alle Cafés anzeigen");
	        System.out.println("2 - Top-N Cafés nach Bewertung");
	        System.out.println("3 - Nach Stadtteil filtern");
	        System.out.println("4 - Neues Café hinzufügen");
	        System.out.
	println("5 - Café nach ID löschen");
	        System.out.println("0 - Beenden");
	        System.out.print("Auswahl: ");
	    }

	    private static void listAll(BreakfastManager manager) {
	        List<BreakfastSpot> all = manager.listAll();
	        if (all.isEmpty()) System.out.println("Keine Einträge vorhanden.");
	        else all.forEach(System.out::println);
	    }

	    private static void showTop(BreakfastManager manager, Scanner sc) {
	        System.out.print("Anzahl Top-Cafés (N): ");
	        int n = readInt(sc, 5);
	        List<BreakfastSpot> top = manager.getTopN(n);
	        if (top.isEmpty()) System.out.println("Keine Einträge vorhanden.");
	        else top.forEach(System.out::println);
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
	        System.out.print("Bewertung (0.0-5.0): ");
	        double rating = readDouble(sc, 4.5);
	        System.out.print("Notizen (optional): ");
	        String notes = sc.nextLine().trim();
	        manager.addSpot(name, addr, nb, rating, notes);
	        System.out.println("Hinzugefügt.");
	    }

	    private static void remove(BreakfastManager manager, Scanner sc) {
	        System.out.print("ID zum Löschen: ");
	        int id = readInt(sc, -1);
	        if (id <= 0) { System.out.println("Ungültige ID."); return; }
	        boolean ok = manager.removeById(id);
	        System.out.println(ok ? "Gelöscht." : "ID nicht gefunden.");
	    }

	    private static int readInt(Scanner sc, int defaultVal) {
	        try {
	            String s = sc.nextLine().trim();
	            if (s.isEmpty()) return defaultVal;
	            return Integer.parseInt(s);
	        } catch (Exception e) {
	            return defaultVal;
	        }
	    }

	    private static double readDouble(Scanner sc, double defaultVal) {
	        try {
	            String s = sc.nextLine().trim();
	            if (s.isEmpty()) return defaultVal;
	            double v = Double.parseDouble(s);
	            if (v < 0) return 0.0;
	            if (v > 5) return 5.0;
	            return v;
	        } catch (Exception e) {
	            return defaultVal;
	        }
	    }
	

	}


