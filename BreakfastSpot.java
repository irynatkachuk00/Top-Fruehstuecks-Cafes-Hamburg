
public class BreakfastSpot {


		    private int id;
		    private String name;
		    private String address;
		    private String neighborhood;
		    private double rating; // 0.0 – 5.0
		    private String notes;

		    public BreakfastSpot(int id, String name, String address,
		                         String neighborhood, double rating, String notes) {
		        this.id = id;
		        this.name = name;
		        this.address = address;
		        this.neighborhood = neighborhood;
		        this.rating = rating;
		        this.notes = notes;
		    }

		    public int getId() { return id; }
		    public String getName() { return name; }
		    public String getAddress() { return address; }
		    public String getNeighborhood() { return neighborhood; }
		    public double getRating() { return rating; }
		    public String getNotes() { return notes; }

		    public void setName(String name) { this.name = name; }
		    public void setAddress(String address) { this.address = address; }
		    public void setNeighborhood(String neighborhood) { this.neighborhood = neighborhood; }
		    public void setRating(double rating) { this.rating = rating; }
		    public void setNotes(String notes) { this.notes = notes; }

		    @Override
		    public String toString() {
		        return String.format("[%d] %s — %s (%s) Bewertung: %.1f %s",
		                id, name, address, neighborhood, rating,
		                (notes.isEmpty() ? "" : "- " + notes));
		    }

		    // CSV-Helfer
		    public String toCsvLine() {
		        return id + ";" + escape(name) + ";" + escape(address) + ";" +
		                escape(neighborhood) + ";" + rating + ";" + escape(notes);
		    }

		    public static BreakfastSpot fromCsvLine(String line) {
		        String[] parts = line.split(";", -1);
		        if (parts.length < 6) return null;
		        try {
		            int id = Integer.parseInt(parts[0]);
		            String name = unescape(parts[1]);
		            String address = unescape(parts[2]);
		            String neighborhood = unescape(parts[3]);
		            double rating = Double.parseDouble(parts[4]);
		            String notes = unescape(parts[5]);
		            return new BreakfastSpot(id, name, address, neighborhood, rating, notes);
		        } catch (Exception e) {
		            return null;
		        }
		    }

		    private static String escape(String s) {
		        return s.replace("\n"," ").replace(";", "\\;");
		    }
		    private static String unescape(String s) {
		        return s.replace("\\;", ";");
		    }
		

}
