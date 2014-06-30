DOCUMENTATION
1. Directory structure
2. How to compile site
3. Making content changes to site
4. Making design changes to site

=================================================
1. Directory structure
=================================================
|
|- content/
|   |- template.html     (the template that gets filled in for every page - contains header, footer, and container)
|   |- website-pages.txt (the list of pages to be compiled)
|   |- *-content.txt     (the content for each of the pages to be compiled)
|
|- css/
|   |- *.css             (all css [styling] used on site)
|
|- doc/
|   |- README.txt        (this file)
|
|- fonts/
|	|- glyphicons*   (icons used on site)
|
|- img/
|   |- (all images used on site)
|
|- js/
|   |- *.js              (all javascript used on site)
|
|- *.bib                 (the bib file used for publications page)
|- *.tex                 (the tex file used for publications page)
|- PageCreator.java      (helps the WebsiteCompiler)
|- WebsiteCompiler.java  (injects content into template for each page specified in content/website-pages.txt)
|- *.html                (all pages on the website)

=================================================
2. How to compile site
=================================================
From the root directory, type:
	javac *.java
to compile PageCreator and WebsiteCompiler.

Then, type:
	java WebsiteCompiler
to compile the website.

If all goes well, you will see a success message. If not, you should see a 
descriptive error (typically related to misnamed files) that can help you 
proceed.

=================================================
3. Making content changes to site
=================================================
The content for each non-publications page is located in the content/ folder 
of the same name. If you want to change what's on the homepage, for example,
you want to change index.html, so go to content/index-content.txt. If you 
wanted to change the about page, go to content/about-content.txt. 

If you want to recompile a single page and not the entire site, you can 
temporarily do one of two things:
1. Change WebsiteCompiler.java: find the variable "listOfWebsitePages" and
   change it so it is the name of the new file containing the page(s) you
   want to recompile.
2. Edit content/website-pages.txt: leave only the pages you want to 
   recompile.
Either way, make sure to revert the changes after you are done.

=================================================
4. Making design changes to site
=================================================
The website is based on the Bootstrap framework. You can learn more at 
http://getbootstrap.com/. 
Generally speaking, the main content on each page is in a container div.
Those containers are split into row divs, which are split into 12
sections. When you see a class like "col-md-6", that means for medium
size screens and larger, use 6 of the available 12 sections - aka, use half
of the page.
For colors, styling, etc., you will most likely want to edit css/style.css.