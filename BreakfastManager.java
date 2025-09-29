import java.io.*;
import java.util.*;

public class BreakfastManager {
    private List<BreakfastSpot> spots = new ArrayList<>();
    private int nextId = 1;
    private final File storageFile;

    public BreakfastManager(String filename) {
        // CSV-Datei im Projektordner oder als absoluter Pfad
        this.storageFile = new File(filename);

        // Ordner erstellen, falls nötig
        try {
            if (!storageFile.exists()) {
                if (storageFile.getParentFile() != null) {
                    storageFile.getParentFile().mkdirs();
                }
                storageFile.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Erstellen der Datei: " + e.getMessage());
        }

        ladeVonDatei();
        seedIfEmpty(); // Startdaten, falls Datei leer
    }

    public void addSpot(String name, String address, String neighborhood, double rating) {
        spots.add(new BreakfastSpot(nextId++, name, address, neighborhood, rating));
        speichereInDatei();
    }

    public List<BreakfastSpot> listAll() {
        return new ArrayList<>(spots);
    }

    public List<BreakfastSpot> filterByNeighborhood(String neighborhood) {
        String target = neighborhood.toLowerCase();
        List<BreakfastSpot> result = new ArrayList<>();
        for (BreakfastSpot s : spots) {
            if (s.getNeighborhood().toLowerCase().contains(target)) {
                result.add(s);
            }
        }
        return result;
    }

    public boolean removeById(int id) {
        Iterator<BreakfastSpot> it = spots.iterator();
        while (it.hasNext()) {
            BreakfastSpot s = it.next();
            if (s.getId() == id) {
                it.remove();
                speichereInDatei();
                return true;
            }
        }
        return false;
    }

    private void speichereInDatei() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(storageFile))) {
            for (BreakfastSpot s : spots) {
                pw.println(s.getId() + ";" + s.getName() + ";" + s.getAddress() + ";" +
                           s.getNeighborhood() + ";" + s.getRating());
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern: " + e.getMessage());
        }
    }

    private void ladeVonDatei() {
        if (!storageFile.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(storageFile))) {
            String line;
            int maxId = 0;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 5) {
                    try {
                        int id = Integer.parseInt(parts[0]);
                        String name = parts[1];
                        String addr = parts[2];
                        String nb = parts[3];
                        double rating = Double.parseDouble(parts[4]);
                        spots.add(new BreakfastSpot(id, name, addr, nb, rating));
                        if (id > maxId) maxId = id;
                    } catch (NumberFormatException ex) {
                        System.err.println("Fehler beim Lesen der Zeile: " + line);
                    }
                }
            }
            nextId = maxId + 1;
        } catch (IOException e) {
            System.err.println("Fehler beim Laden: " + e.getMessage());
        }
    }

    private void seedIfEmpty() {
        if (!spots.isEmpty()) return;
        addSpot("Nord Coast Coffee Roastery", "Deichstraße 9", "Altstadt", 4.8);
        addSpot("Mamalicious", "Max-Bräuer-Allee 277", "Sternschanze", 4.7);
        addSpot("Mit Herz und Zucker", "Lübecker Str. 29", "Hohenfelde", 4.7);
    }

    // Optional: für Debugging, zeigt, wo die Datei erstellt wird
    public void printFilePath() {
        System.out.println("CSV-Datei: " + storageFile.getAbsolutePath());
    }
}
