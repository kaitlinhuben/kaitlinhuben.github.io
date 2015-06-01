/**
 * Models the various publication types allowed for papers on the Publications page
 * @author Kaitlin Huben
 */
public enum PubType {
	DOCTORAL         ("Doctoral Thesis Title"),
	ARTICLE          ("Articles in Journals (published or accepted for publication)"),
	ARTICLE_2        ("Articles in Journals (accepted up to minor revision)"),
	ARTICLE_3        ("Articles Submitted for Publication (or to be submitted)"),
	CONFERENCE       ("Conference Proceedings (published or accepted for publication)"),
	CONFERENCE_2     ("Conference Proceedings (accepted up to a minor revision)"),
	BOOK             ("Book Chapters and Encyclopedia Articles"),
	EDITORIAL        ("Editorials"),
	PRE_PRINT        ("Sample Pre-Prints, Extended Abstracts, and Working Papers (please request if needed)"),
	WORK_IN_PROGRESS ("Works in Progress");
	
	private final String description;
	
	PubType(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static PubType getType(String stringType) throws IllegalArgumentException {
		if(stringType.toUpperCase().equals("DOCTORAL")) {
			return DOCTORAL;
		} else if (stringType.toUpperCase().equals("ARTICLE")) {
			return ARTICLE;
		} else if (stringType.toUpperCase().equals("ARTICLE_2")) {
			return ARTICLE_2;
		} else if (stringType.toUpperCase().equals("ARTICLE_3")) {
			return ARTICLE_3;
		} else if (stringType.toUpperCase().equals("CONFERENCE")) {
			return CONFERENCE;
		} else if (stringType.toUpperCase().equals("CONFERENCE_2")) {
			return CONFERENCE_2;
		} else if (stringType.toUpperCase().equals("BOOK")) {
			return BOOK;
		} else if (stringType.toUpperCase().equals("EDITORIAL")) {
			return EDITORIAL;
		} else if (stringType.toUpperCase().equals("PRE_PRINT")) {
			return PRE_PRINT;
		} else if (stringType.toUpperCase().equals("WORK_IN_PROGRESS")) {
			return WORK_IN_PROGRESS;
		} else {
			throw new IllegalArgumentException("No such type");
		}
	}
}
