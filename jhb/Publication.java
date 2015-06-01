import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Publication {
	private String pathToPapers;
	private String title = null;
	private String year = null;
	//private PubType type = null; TODO uncomment
	private String authorString = null;
	private String journal = null;
	private String volume = null;
	private String pages = null;
	private String bibtex = "";
	private String bibtexFull = null;
	private String summary = null;
	private String subpage_link = null;
	private String texName = null;
	private String pathToPdf = null;

	/** 
	 * Instantiates a Publication object given the full BibTex entry
	 * Extracts information from the BibTex entry, the summary from the tex file (if found),
	 * and creates the link for the subpage based on the tex file name (if found)
	 * @param bibtexFull
	 */
	public Publication(String pathToPapers, String bibtexFull) {
		this.pathToPapers = pathToPapers;
		this.bibtexFull = bibtexFull;
		extractBibtexInfo(bibtexFull);
		setSubpageLink(this.texName);
	}
	
	/**
	 * Extract info and set fields from bibtex
	 * @param bibtexFull The full bibtex summary
	 */
	private void extractBibtexInfo(String bibtexFull) {
		String[] lines = bibtexFull.split("\n");
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains("@") || lines[i].trim().equals("}")) {
				this.bibtex += lines[i] + "\n";
				continue;
			} else {
				assignInfo(lines[i]);
			}
		}
	}
	
	/**
	 * Assign a specific field given a line of the form
	 * title = {This is a title}
	 * Also tries to find the tex and pdf files for summary and linking
	 * @param line The line from bibtex to set
	 */
	private void assignInfo(String line) {
		String trimmed = line.trim();
		String[] parts = trimmed.split("=");
		String left = parts[0].trim().toLowerCase();
		String right = parts[1].trim();
		
		if(left.equals("type")) {
			//this.type = PubType.getType(right); TODO uncomment
		} else if (left.equals("filenametex")) {
			this.texName = right;
			findFilesAndSummary(texName);
		} else {
			this.bibtex += line + "\n";
			if(left.equals("title")) {
				this.title = right;
			} else if (left.equals("year")) {
				this.year = right;
			} else if (left.equals("author")) {
				this.authorString = right;
			} else if (left.equals("journal")) {
				this.journal = right;
			} else if (left.equals("volume")) {
				this.volume = right;
			} else if (left.equals("pages")) {
				this.pages = right;
			}
		}
	}
	
	/**
	 * Checks to see if .tex and .pdf files exist
	 * If .tex exists, pull summary (abstract) out for use
	 * If .pdf exists, creates link to pdf
	 * @param fileName The name of the .tex file (not the full path)
	 */
	private void findFilesAndSummary(String fileName) {
		if(fileName == null) {
			return;
		}
		
		String name = fileName.substring(0,fileName.indexOf("."));
		
		// see if tex file exists
		File tex = new File(pathToPapers+fileName);
		if(tex.exists() && !tex.isDirectory()) { 
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(pathToPapers+fileName));
			} catch (FileNotFoundException e) {
				System.out.println("\nCould not open tex file named \"" + pathToPapers+fileName + "\" - aborting.");
				System.exit(1);
			}
			
			// Read through tex file, trying to find abstract
			String summaryHolder = "";
			String line = "";
			boolean inSummary = false;
			try {
				while ( (line = br.readLine()) != null ) {
					if( ! inSummary) {
						line = line.trim().toLowerCase();
					}
					
					// if line is "\begin{abstract}", we found the summary
					if(line.equals("\\begin{abstract}")) {
						inSummary = true;
						continue;
					}
					
					// if found first line of summary, check to see whether
					// this is the last line or whether to keep adding
					if(inSummary) {
						// last line - stop
						if(line.toLowerCase().equals("\\end{abstract}")) {
							this.summary = summaryHolder;
							break;
						}
						// not last line - keep going
						else {
							summaryHolder += line + " ";
						}
					}
				}
			} catch (IOException e) {
				System.out.println("Could not read a line in \"" + pathToPapers+fileName + "\" - aborting.");
				System.exit(1);
			}
			
			// Close up resources
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// see if pdf file exists
		File pdf = new File(pathToPapers+name+".pdf");
		if(pdf.exists() && !pdf.isDirectory()) {
			this.pathToPdf = name+".pdf";
		}
	}
	
	/**
	 * Creates a subpage link based on the name of the .tex file
	 * or on title of .tex file doesn't exist
	 * @param fileName The name of the .tex file (not full path)
	 */
	private void setSubpageLink(String fileName) {
		// if .tex file doesn't exist, create a name based on title
		// and restrict it to 30 characters
		if(fileName == null) {
			int restriction = 30;
			
			// clean out title
			String cleanTitle = clean(this.title);
			cleanTitle = cleanTitle.substring(0, restriction);
			
			// replace spaces with underscores and set link
			String htmlTitle = cleanTitle.replaceAll(" ", "_");
			htmlTitle = htmlTitle + ".html";
			this.subpage_link = htmlTitle;
		}
		// if .tex file does exist, create name by stripping .tex 
		// and replacing with .html
		else {
			String name = fileName.substring(0,fileName.indexOf("."));
			this.subpage_link = name+".html";
		}
	}
	
	// TODO null checks and formatting
	public String getHTML() {
		String html = "";
		html += clean(authorString) + ". ";
		html += "<a href=\"papers/" + subpage_link + "\">" + clean(title) + "</a>. ";
		html += clean(journal) + ", " + clean(volume) + " (" + clean(year) + ")" + ", pp. " + clean(pages) + ".";
		return html;
	}
	
	// TODO formatting
	public String getSubpageHTML() {
		String html = "";
		html += "<h3>" + clean(title) + "</h3>\n";
		
		if(pathToPdf != null) {
			html += "<p>View PDF <a href=\"" + pathToPdf + "\">here.</a></p>\n";
		}
		
		if(summary != null) {
			html += "<h4>Summary</h4>\n<p>" + summary + "</p>\n";
		}
		
		html += "<h4>Bibtex</h4>\n<p class=\"bibtex\">" + formatBibtex(bibtex) + "</p>";
		
		html +="<br><br><a href=\"../publications.html\">Back to Publications</a>";
		return html;
	}
	
	/** 
	 * Formats bibtex entry for HTML display
	 * @param bibtexFull The bibtex entry to foramt
	 * @return The HTML-formatted bibtex
	 */
	private String formatBibtex(String bibtex) {
		String[] lines = bibtex.split("\n");
		String bib = lines[0] + "<br>";
		for(int i = 1; i < lines.length - 1; i++) {
			lines[i] = lines[i].trim();
			if(i == lines.length - 2) {
				String lastFullLine = lines[i];
				String lastChar = lastFullLine.substring(lastFullLine.length()-1, lastFullLine.length());
				if(lastChar.equals(",")) {
					lastFullLine = lastFullLine.substring(0, lastFullLine.length()-1);
					lines[i] = lastFullLine;
				}
			}
			bib += "&nbsp;&nbsp;&nbsp;&nbsp;" + lines[i] + "<br>";
		}
		bib += lines[lines.length - 1];
		return bib;
	}
	
	/**
	 * @return The link to the subpage
	 */
	public String getSubpageLink() {
		return this.subpage_link;
	}
	
	/**
	 * Strips trailing commas and unneeded curly braces
	 * @param string of format "{Aida, {Z}akai, and Wong}"
	 * @return string of format "Aida, Zakai, and Wong"
	 */
	private String clean(String string) {
		if(string == null) {
			return string;
		}
		String s = string;
		s = s.trim();
		if(s.charAt(s.length()-1) == ',') {
			s = s.substring(0, s.length()-1);
		}
		s = s.replaceAll("\\{", "");
		s = s.replaceAll("\\}", "");
		return s;
	}
	
	/**
	 * @return The year of the publication
	 */
	public String getYear() {
		if(this.year==null) {
			return "0000";
		}
		return clean(this.year);
	}
	
	/**
	 * Returns the full, unformatted bibtex
	 */
	public String toString() {
		return this.bibtexFull;
	}
}
