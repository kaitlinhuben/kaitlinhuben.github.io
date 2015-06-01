import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PublicationsPageCreator extends PageCreator {
	private String pathToPapers;	// where the papers are stored
	private String bibFileName;		// name of the bitbex file
	private String subpageTemplate; // different template for subpage
	private ArrayList<Publication> publications;
	private final String LINKNAME = "section"; 

	public PublicationsPageCreator(String pathToContent, String pathToPapers, String htmlTemplateName, String pathToSubpageTemplate, String pageName, String bibName) {
		// Super sets template, pageName, pageTitle, page
		super(pathToContent, htmlTemplateName, pageName);
		
		// Set additional fields
		this.pathToPapers = pathToPapers;
		this.bibFileName = bibName;
		this.subpageTemplate = readTemplate(pathToSubpageTemplate);
		this.publications = new ArrayList<Publication>();
		
		// Fill the publications list with entries and sort them chronologically
		extractBibtexInfo();
		sortPublications();
	}

	@Override
	/** 
	 * In case recompilePage accidentally gets called
	 * Should actually recompile main Publications page and then all subpages below that
	 */
	public void recompilePage() {
		recompilePageAndSubpages();
	}
	
	/**
	 * Deletes main page and subpages, then rewrites them all
	 */
	public void recompilePageAndSubpages() {
		deleteOldPageAndSubpages();
		writeMainPageContent();
		writeAllSubpages();
	}
	
	/**
	 * Opens up the bibtex file and reads all the entries
	 */
	private void extractBibtexInfo() {
		// open up the bibtex file
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(this.pathToPapers + this.bibFileName));
		} catch (FileNotFoundException e) {
			System.out.println("\nCould not find bibtex file named \"" + bibFileName + "\" - aborting.");
			System.exit(1);
		}
		
		// go through whole bibtex file, read entries, and create Publication objects
		String bibEntry = "";
		String line = "";
		try {
			while ( (line = br.readLine()) != null ) {
				// if an empty line, ignore and keep going
				if(line.trim().equals("")) {
					continue;
				}
				
				bibEntry += line + "\n";
				
				// if hit the last line of a bib entry, create the Publication
				// and reset everything
				if(line.trim().equals("}")) {
					Publication p = new Publication(this.pathToPapers, bibEntry);
					publications.add(p);
					bibEntry = "";
					line = "";
				}
				
			}
		} catch (IOException e) {
			System.out.println("Could not read a line in \"" + bibFileName + "\" - aborting.");
			System.exit(1);
		}
	}

	/**
	 * Deletes old publications.html page and all subpages 
	 */
	private void deleteOldPageAndSubpages() {
		File mainPage = new File(pageName+".html");
		
		// If this page already exists, delete it so we can write a new one
		if(mainPage.exists() && !mainPage.isDirectory()) { 
			// Try to delete the page
			if( ! mainPage.delete() ){
    			System.out.println("\nDelete operation failed for \"" + pageName + ".html\"- aborting.");
    			System.exit(1);
    		}
		}
		
		// go through subpages and delete if exist
		for(int i = 0; i < publications.size(); i++) {
			Publication p = publications.get(i);
			String path = pathToPapers + p.getSubpageLink();
			File subpage = new File(path);
			
			// If this page already exists, delete it so we can write a new one
			if(subpage.exists() && !subpage.isDirectory()) { 
				// Try to delete the page
				if( ! subpage.delete() ){
	    			System.out.println("\nDelete operation failed for \"" + path + ".html\"- aborting.");
	    			System.exit(1);
	    		}
			}
		}
	}
	
	/**
	 * Sorts publications by year, most recent first
	 */
	private void sortPublications() {
		Collections.sort(publications, new PubComparator());
	}
	
	/**
	 * Writes the main publications.html page
	 */
	private void writeMainPageContent() {
		// Already have beginning content: 
		// - add table of contents
		// - add list of all articles
		this.pageContent += getTableOfContents();
		this.pageContent += getListOfPublications();
		
		// Create the new page
		String html = template;
		html = html.replace("pageTitle", this.pageTitle);
		html = html.replace("pageContent", this.pageContent);
		
		// Write to file
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(pageName+".html", "UTF-8");
		} catch (FileNotFoundException e) {
			System.out.println("Could not write to \"" + pageName + ".html\" - aborting.");
			System.exit(1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		writer.print(html);
		writer.close();
	}
	
	/**
	 * Goes through list of publications and writes subpages for all
	 */
	private void writeAllSubpages() {
		for(int i = 0; i < publications.size(); i++) {
			writeSubpage(publications.get(i));
		}
	}
	
	/**
	 * Writes subpage for publication
	 * @param pub The publication to write a page for
	 */
	private void writeSubpage(Publication pub) {
		String subpageLink = pub.getSubpageLink();
		String subpageTitle = "Publication";
		String subpageHtml = pub.getSubpageHTML();
		
		String html = this.subpageTemplate;
		html = html.replaceAll("pageTitle", subpageTitle);
		html = html.replaceAll("pageContent", subpageHtml);
		
		String fullPath = pathToPapers + subpageLink;
		// Write to file
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(fullPath, "UTF-8");
		} catch (FileNotFoundException e) {
			System.out.println("Could not write to \"" + fullPath + "\" - aborting.");
			System.exit(1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		writer.print(html);
		writer.close();
	}
	
	/** 
	 * Gets the table of contents for display on the main page
	 * @return The HTML formatted TOC
	 */
	private String getTableOfContents() {
		String toc = "\t<div class=\"row\"><div class=\"col-xs-12\">\n";
		toc += "\t<h4>Go to a specific year</h4>\n";
		toc += "\t\t<p>\n\t\t\t";
		
		String currentYear = "";
		for(int i = 0; i < publications.size(); i++) {
			Publication p = publications.get(i);
			if( ! currentYear.equals(p.getYear()) ) {
				currentYear = p.getYear();
				toc += "<a href=\"#" + LINKNAME + currentYear + "\">[" + currentYear + "]</a>&nbsp;&nbsp;";
			}
		}
		toc += "\t\t</p>\n\t</div></div>\n";
		return toc;
	}
	
	/**
	 * Gets the list of publications for display on the main page
	 * @return The HTML formatted publications list
	 */
	private String getListOfPublications() {
		String html = "\t<div class=\"row\">\n\t\t<div class=\"col-xs-12\">\n";
		Publication p = publications.get(0);
		String currentYear = p.getYear();
		
		html += "\t\t\t<div id=\"" + LINKNAME + currentYear + "\" class=\"spacer-30\"></div>\n";
		html += "\t\t\t<h3>" + currentYear + "</h3>\n\t\t\t<ul>";
		for(int i = 0; i < publications.size(); i++) {
			p = publications.get(i);
			
			// moved into a new year
			if( ! currentYear.equals(p.getYear())) {
				currentYear = p.getYear();
				html += "\t\t\t</ul>\n\t\t</div>\n\t\t<div class=\"col-xs-12\">\n";
				html += "\t\t\t<div id=\"" + LINKNAME + currentYear + "\" class=\"spacer-30\"></div>\n";
				html += "\t\t\t<h3>" + currentYear + "</h3>\n\t\t\t<ul>";
			}
			
			html += "\t\t\t\t<li>" + p.getHTML() + "</li>\n";
		}
		html += "\t\t\t</ul>\n\t\t</div>\n\t</div>\n";
		
		return html;
	}
	
	/**
	 * Reads a template into a String
	 * @param pathToTemplate The path to the template to be read
	 * @return The String version of the template
	 */
	private String readTemplate(String pathToTemplate) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(pathToTemplate));
		} catch (FileNotFoundException e) {
			System.out.println("\nCould not find HTML template named \"" + pathToTemplate + "\" - aborting.");
			System.exit(1);
		}
		
		// Read in entire template
		String templateHolder = "";
		String line = "";
		try {
			while ( (line = br.readLine()) != null ) {
				templateHolder += line + "\n";
			}
		} catch (IOException e) {
			System.out.println("Could not read a line in \"" + pathToTemplate + "\" - aborting.");
			System.exit(1);
		}
		
		// Close up resources
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return templateHolder;
	}
	
	/**
	 * Prints out the publications HTML and subpage HTML for each publication
	 */
	public String toString() {
		String s = "";
		for(int i = 0; i < publications.size(); i++) {
			Publication p = publications.get(i);
			s += p.getHTML() + "\n";
			s += p.getSubpageHTML() + "\n\n";
		}
		return s;
	}
	
	/**
	 * For sorting publications array list
	 * @author Kaitlin Huben
	 */
	private class PubComparator implements Comparator<Publication> {
		@Override
		public int compare(Publication p1, Publication p2) {
			return p2.getYear().compareTo(p1.getYear());
		}
	}

}
