public class Main{

	private static final String DATASET_DIRECTORY = "dataset"; // FOLDER NAME THAT CONTAINS TXT FILES
	private static final String COMMANDS_INPUT_TXT = "input.txt"; // QUERIES

	public static void main(String[] args) {
		// create a main app with dataset and input directories
		MainApp app = new MainApp(DATASET_DIRECTORY,COMMANDS_INPUT_TXT);
		app.start(); // starts the program
		/*
		 * You can use getter methods of MainApp class to access NLP and WordMap if you want.
		 */
		//to print the word map, uncomment it
		//app.printWordMap();
	}
}