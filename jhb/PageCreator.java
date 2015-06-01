import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Inserts content into template for individual page
 * @author Kaitlin Huben
 *
 */
public class PageCreator {
	protected String pathToContent;   // where the content is stored
	protected String pageName;		// name of the page in the files
	protected String pageTitle;		// actual display title of the page
	protected String pageContent;		// content to fill in the template
	protected String template;		// name of the template to fill in
	
	/**
	 * Constructor. Reads in both the template and all the page info (title and content)
	 * @param pathToContent Path to where the templates and content are stored
	 * @param templateFile Name of the template HTML file to be used
	 * @param pageName Name of the page to compile (e.g. if want to compile about.html, then pageName=about)
	 */
	public PageCreator(String pathToContent, String templateFile, String pageName) {
		this.pathToContent = pathToContent;
		this.pageName = pageName;
		setTitleAndContent();
		setTemplate(templateFile);
	}
	
	/** 
	 * Extracts title and content from -content.txt page
	 */
	private void setTitleAndContent() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(this.pathToContent + pageName+"-content.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("\nCould not find content named \"" + pageName + "-content.txt\" - aborting.");
			System.exit(1);
		}
		
		// First line should be the title of the page like this: Title=actualTitle
		// The split at = gets the actual title 
		String line = "";
		try {
			line = br.readLine().split("=")[1];
		} catch (IOException e) {
			System.out.println("Could not read a line in \"" + pageName + "-content.txt\" - aborting.");
			System.exit(1);
		}
		this.pageTitle = line;
		
		// After the title is the rest of the content
		// Read it all and store it
		String contentHolder = "";
		try {
			while ( (line = br.readLine()) != null ) {
				contentHolder += line + "\n";
			}
		} catch (IOException e) {
			System.out.println("Could not read a line in \"" + pageName + "-content.txt\" - aborting.");
			System.exit(1);
		}
		
		this.pageContent = contentHolder;
		
		// Close up resources
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Read in the HTML template to fill in with title and content
	 * @param templateFile Name of the HTML file (not the full path)
	 */
	private void setTemplate(String templateFile) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(this.pathToContent + templateFile));
		} catch (FileNotFoundException e) {
			System.out.println("\nCould not find HTML template named \"" + templateFile + "\" - aborting.");
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
			System.out.println("Could not read a line in \"" + templateFile + "\" - aborting.");
			System.exit(1);
		}
		
		this.template = templateHolder;
		
		// Close up resources
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Recompiles a page. Deletes the current version if it exists, injects title and content
	 * into template, and prints to file.
	 */
	public void recompilePage() {
		File page = new File(pageName+".html");
		
		// If this page already exists, delete it so we can write a new one
		if(page.exists() && !page.isDirectory()) { 
			// Try to delete the page
			if( ! page.delete() ){
    			System.out.println("\nDelete operation failed for \"" + pageName + ".html\"- aborting.");
    			System.exit(1);
    		}
		}
		
		// Either page never existed or page successfully deleted
		// Create the new page
		template = template.replace("pageTitle", this.pageTitle);
		template = template.replace("pageContent", this.pageContent);
		
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
		writer.print(template);
		writer.close();
	}

}
