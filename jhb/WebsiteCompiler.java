import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * Compiles content into template for each page of website
 * @author Kaitlin Huben
 *
 */
public class WebsiteCompiler {

	/**
	 * Main method to run website compilation
	 * @param args Possible flags include: -p for publication page only
	 */
	public static void main(String[] args) {
		//---------------------------------------------------------------------
		// SETUP: make sure all files exist and can be read from/written to
		//---------------------------------------------------------------------
		
		// check to see if -p flag used
		boolean doAllPages = true;
		if(args.length > 0 && args[0].toLowerCase().equals("-p")) {
			doAllPages = false;
		}
		
		// Set up variables to read from and write to console
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	    PrintStream stdout = System.out;
	    PrintStream stderr = System.err;
		
	    // Double-check that user wants to re-make website
		stdout.print("Delete all website files and recompile? (y/n): ");
		stdout.flush();
		String response = null;
		try {
			response = stdin.readLine().toLowerCase();
		} catch (IOException e) {
			stdout.println("Could not read input, aborting.");
			e.printStackTrace();
			System.exit(1);
		}
		
		// If anything other than "y" or "yes" then stop here
		if( (!response.equals("y")) && (!response.equals("yes")) ) {
			stdout.print("Stopping compilation...");
			try {
				stdin.close();
			} catch (IOException e) {
				stderr.println("Could not close stdin.");
			}
			stderr.close();
			stdout.println("done.");
			stdout.close();
			System.exit(1);
		}

		//---------------------------------------------------------------------
		// ACTUAL COMPILATION
		//---------------------------------------------------------------------
		
		// Variables that tell the PageCreator where to look for information
		// If file names change, then make sure to change these variables
		// ALSO NOTE: all templates, content files, etc. should be in the PATH_TO_CONTENT folder
		final String LIST_OF_WEBSITE_PAGES = "website-pages.txt";
		final String HTML_TEMPLATE_NAME = "template.html";
		final String SUBPAGE_TEMPLATE_PATH = "papers/template.html";
		final String PATH_TO_CONTENT = "content/";
		final String PATH_TO_PAPERS = "papers/";
		final String BIB_FILE_NAME = "tempbib.txt";
		
		// Read in the list of pages to compile
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(PATH_TO_CONTENT + LIST_OF_WEBSITE_PAGES));
		} catch (FileNotFoundException e) {
			stdout.println("Could not find list named \"" + LIST_OF_WEBSITE_PAGES + "\" - aborting.");
			System.exit(1);
		}
		String pageName;
		
		// For each page, recompile
		try {
			while ( (pageName = br.readLine()) != null ) {
				if(pageName.equals("publications")) {
					stdout.print("Starting compilation for " + pageName + ".html...");
					PublicationsPageCreator ppc = new PublicationsPageCreator(PATH_TO_CONTENT, 
																			  PATH_TO_PAPERS, 
																			  HTML_TEMPLATE_NAME,
																			  SUBPAGE_TEMPLATE_PATH,
																			  pageName,
																			  BIB_FILE_NAME);
					ppc.recompilePageAndSubpages();
					stdout.println("done");
				} 
				// check to see if -p flag used - if so, only do publications page
				else if(doAllPages) {
					stdout.print("Starting compilation for " + pageName + ".html...");
					PageCreator pc = new PageCreator(PATH_TO_CONTENT, HTML_TEMPLATE_NAME, pageName);
					pc.recompilePage();
					stdout.println("done");
				}
			}
		} catch (IOException e) {
			stdout.println("Could not read a line in " + LIST_OF_WEBSITE_PAGES);
			e.printStackTrace();
		}
		
		//---------------------------------------------------------------------
		// CLOSE RESOURCES
		//---------------------------------------------------------------------
		try {
			br.close();
		} catch (IOException e) {
			stdout.println("Could not close reader");
			e.printStackTrace();
		}
		try {
			stdin.close();
		} catch (IOException e) {
			stdout.println("Could not close stdin");
			e.printStackTrace();
		}
		stderr.close();
		stdout.println("\nDone! Double-check to make sure everything went well.");
		stdout.close();
	}

}
