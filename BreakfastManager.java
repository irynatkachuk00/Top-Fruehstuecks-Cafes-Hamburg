


	import java.io.*;
	import java.util.*;
	import java.util.stream.Collectors;

	/**
	 * Verwaltet die Liste der Frühstücks-Cafés,
	 * inklusive Laden/Speichern aus einer CSV-Datei.
	 */
	public class BreakfastManager {
	    private List<BreakfastSpot> spots = new ArrayList<>();
	    private int nextId = 1;
	    private final File storageFile;

	    public BreakfastManager(String filename) {
	        this.storageFile = new File(filename);
	        loadFromFile();
	    }

	    public void addSpot(String name, String address,
	                        String neighborhood, double rating, String notes) {
	        BreakfastSpot s = new BreakfastSpot(nextId++, name, address, neighborhood,
	                                            rating, notes == null ? "" : notes);
	        spots.add(s);
	        saveToFile();
	    }

	    public List<BreakfastSpot> listAll() {
	        return new ArrayList<>(spots);
	    }

	    public List<BreakfastSpot> getTopN(int n) {
	        return spots.stream()
	                .sorted(Comparator.comparingDouble(BreakfastSpot::getRating)
	                        .reversed()
	                        .thenComparing(BreakfastSpot::getName))
	                .limit(n)
	                .collect(Collectors.toList());
	    }
	public List<BreakfastSpot> filterByNeighborhood(String neighborhood) {
	        String target = neighborhood.trim().toLowerCase();
	        return spots.stream()
	                .filter(s -> s.getNeighborhood().toLowerCase().contains(target))
	                .collect(Collectors.toList());
	    }

	    public boolean removeById(int id) {
	        boolean removed = spots.removeIf(s -> s.getId() == id);
	        if (removed) saveToFile();
	        return removed;
	    }

	    private void saveToFile() {
	        try (PrintWriter pw = new PrintWriter(new FileWriter(storageFile))) {
	            for (BreakfastSpot s : spots) {
	                pw.println(s.toCsvLine());
	            }
	        } catch (IOException e) {
	            System.err.println("Fehler beim Speichern: " + e.getMessage());
	        }
	    }

	    private void loadFromFile() {
	        if (!storageFile.exists()) return;
	        try (BufferedReader br = new BufferedReader(new FileReader(storageFile))) {
	            String line;
	            int maxId = 0;
	            while ((line = br.readLine()) != null) {
	                BreakfastSpot s = BreakfastSpot.fromCsvLine(line);
	                if (s != null) {
	                    spots.add(s);
	                    if (s.getId() > maxId) maxId = s.getId();
	                }
	            }
	            nextId = maxId + 1;
	        } catch (IOException e) {
	            System.err.println("Fehler beim Laden: " + e.getMessage());
	        }
	    }

	    /** Startdatensätze für Hamburg (Top 5) */
	    public void seedIfEmpty() {
	        if (!spots.isEmpty()) return;
	        addSpot("Nord Coast Coffee Roastery",
	                "Deichstraße 9",
	                "Altstadt",
	                4.8,
	                "Eigene Kaffeeröstung, Bowls, Pancakes, vegane Optionen");
	        addSpot("Mamalicious",
	                "Max-Bräuer-Allee 277",
	                "Sternschanze",
	                4.7,
	                "Amerikanischer Stil: Pancakes, Waffeln, große Portionen");
	        addSpot("Mit Herz und Zucker",
	                "Lübecker Str. 29",
	                "Hohenfelde",
	                4.7,
	                "Hausgemachtes Gebäck, regionale Produkte, gemütlich");
	        addSpot("Café Koppel",
	                "Koppel 66 (Lange Reihe 75)",
	                "St. Georg",
	                4.6,
	                "Grüner Innenhof, viele vegane/vegetarische Optionen");
	        addSpot("Patisserie Madeleine",
	                "Keplerstraße 36",
	                "Ottensen",
	                4.6,
	                "Französische Croissants und Tartes, Pariser Flair");
	    }
	

}
