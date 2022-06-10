import java.util.ArrayList;

//Modellklasse für den Präsidenten
public class President {
	// Eigenschaften definieren
	String name;
	String presidentNumber;
	String inaugurationAge;
	String tenureInDays;
	String noOfVetoes;
	String noOfChildren;
	String lengthOfSpeech;

	// Eigenschaften initialisieren
	public President(String name, String presidentNumber, String inaugurationAge, String tenureInDays,
			String noOfVetoes, String noOfChildren, String lengthOfSpeech) {
		this.name = name;
		this.presidentNumber = presidentNumber;
		this.inaugurationAge = inaugurationAge;
		this.tenureInDays = tenureInDays;
		this.noOfVetoes = noOfVetoes;
		this.noOfChildren = noOfChildren;
		this.lengthOfSpeech = lengthOfSpeech;
	}

	// String-Darstellung des Präsidenten
	@Override
	public String toString() {

		return "{" + name + "," + presidentNumber + "," + inaugurationAge + "," + tenureInDays + "," + noOfVetoes + ","
				+ noOfChildren + "," + lengthOfSpeech + "}";
	}

	/**
	 * 
	 * @param presidents :Liste der Präsidenten
	 * @param category:  Kategorie mit der besten Chance gewählt
	 * @return wahr, wenn die gewählte Kategorie richtig ist ansonsten falsch
	 */
	public boolean isBestChance(ArrayList<President> presidents, String category) {
		// Definieren einer separaten Liste für jede Eigenschaft
		ArrayList<String> PNoList = new ArrayList<>();
		ArrayList<String> iAgeList = new ArrayList<>();
		ArrayList<String> tIdList = new ArrayList<>();
		ArrayList<String> noVList = new ArrayList<>();
		ArrayList<String> noCList = new ArrayList<>();
		ArrayList<String> lOSList = new ArrayList<>();
		for (int i = 0; i < presidents.size(); i++) {
			// Hinzufügen von Eigenschaften in ihren jeweiligen Listen
			PNoList.add(presidents.get(i).presidentNumber);
			iAgeList.add(presidents.get(i).inaugurationAge);
			tIdList.add(presidents.get(i).tenureInDays);
			noCList.add(presidents.get(i).noOfChildren);
			noVList.add(presidents.get(i).noOfVetoes);
			lOSList.add(presidents.get(i).lengthOfSpeech);
		}

		// Wenn eine Kategorie erfüllt ist, geben Sie wahr zurück
		switch (category) {
		case "PNo":
			if (isBestQuality(presidentNumber, PNoList))
				return true;
		case "LoS":

			if (isBestQuality(lengthOfSpeech, lOSList))
				return true;
			break;
		case "IAg":
			if (isBestQuality(inaugurationAge, iAgeList))
				return true;
		case "Tid":
			if (isBestQuality(tenureInDays, tIdList))
				return true;

		case "Noc":
			if (isBestQuality(noOfChildren, noCList))
				return true;

		case "NOv":
			if (isBestQuality(noOfVetoes, noVList))
				return true;

		default:
			break;
		}

		return false;
	}

	/**
	 * 
	 * @param quality      : die gewählte Kategorie
	 * @param qualityList: die Liste der Präsidenten dieser Kategorie
	 * @return true wenn der gewählte Kategoriewert das Maximum dieser Kategorie von
	 *         mehr Präsidenten ist
	 */
	private boolean isBestQuality(String quality, ArrayList<String> qualityList) {
		int i = 0;
		while (i < qualityList.size() && max(quality, qualityList.get(i++)).equals(quality)) {

		}
		if (i >= qualityList.size() / 1.5)
			return true;// d.h. die Schleifenbedingung war für alle Präsidenten wahr
		else
			return false;
	}

	// Methode zum Analysieren einer Zeichenfolge in eine ganze Zahl zum Vergleich
	private int parse(String s) {
		if (s.equals("-") || s.equals(""))
			return 0;
		else
			return Integer.parseInt(s);
	}

	// Methode, die nach der Analyse den größeren der beiden Zeichenfolgenwerte
	// zurückgibt
	private String max(String s1, String s2) {
		int x1, x2;
		x1 = x2 = 0;
		if (!s1.equals("-"))
			x1 = parse(s1);
		if (!s2.equals("-"))
			x2 = parse(s2);
		if (x1 >= x2)
			return s1;
		else
			return s2;
	}
}
