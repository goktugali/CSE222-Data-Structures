import java.util.*;
import java.io.File;

public class MainApp{

	private NLP nlp;
	private GtuArrayList<NLPJob> jobs = new GtuArrayList<>(); // stores the jobs (query)
	private String datasetDir; // DATASET DIRECTORY(FOLDER)
	private String commandsDir; // NAME OF THE TXT FILE WHICH CONTAINS COMMANDS

	public MainApp(String datasetDir,String commandsDir) // MainApp constructor creates the application
	{
		this.datasetDir = datasetDir;
		this.commandsDir = commandsDir;
		nlp = new NLP(new Word_Map());

		/* READ ALL FILES IN DIRECTORY */
        try {
            File f = new File(datasetDir);
            ArrayList<String> allDatasets = new ArrayList<>(Arrays.asList(f.list()));
            for (String  file : allDatasets)
                nlp.readDataset(datasetDir + "/" + file);
        }catch (Exception ex){
            System.out.println("Check the folder name !");
        }
		this.commandReader(); // reads the input.txt file
	}

	public MainApp(String datasetDir,String commandsDir,NLP nlp) // Main App constructor, takes specific nlp
	{
		this.datasetDir = datasetDir;
		this.commandsDir = commandsDir;
		this.nlp = nlp;

		/* READ ALL FILES IN DIRECTORY */
		File f = new File(datasetDir);
		ArrayList<String> allDatasets = new ArrayList<String>(Arrays.asList(f.list()));
		for (String  file : allDatasets) 
			nlp.readDataset(datasetDir + "/" + file);

		this.commandReader();
	}

	public NLP getNLP()
	{
		return nlp;
	}
	public Word_Map getWmap()
	{
		return nlp.getWmap();
	}
	/**
	 * Prints the word map
	 */
	public void printWordMap()
	{
		nlp.printWordMap();;
	}

	/**
	 * runs all commands in input.txt
	 */
	public void start()
	{
		for (NLPJob job : jobs) // starts all jobs
			job.run();
	}

	/**
	 * Job class that defines actions.
	 */
	private class NLPJob{ // acts like a thread

		private String job; // job command (bigram or tfidf)
		private String fileName; // if it is tfidf, store the filename
		private String wordName; 
		private NLP nlp; // nlp processor

		private NLPJob(String command,NLP nlp)
		{	
			String[] arr = command.split(" ");
			
			if(arr.length==0)
				throw new UnsupportedOperationException("Invalid Command");
			if(arr[0].equals("bigram")){ // BIGRAM COMMAND
				if(arr.length!=2) // ILLEGAL ARGUMENT
					throw new IllegalArgumentException("Invalid Command - bigram");
				//parse the string to meaningful command
				job = "bigram";				
				wordName = arr[1];
			}
			else if(arr[0].equals("tfidf")){ // TFIDF COMMAND
				if(arr.length!=3) // ILLEAGAL ARGUMENT
					throw new IllegalArgumentException("Invalid Command - tfidf");
				//parse the string to meaningful command
				job ="tfidf";
				wordName = arr[1];
				fileName = arr[2];
			}
			else{ // ILLEGAL COMMAND
				throw new UnsupportedOperationException("Invalid Command : " + arr[0] + "--");
			}

			this.nlp = nlp;    

		}
		private void run()
		{
			if(job.equals("bigram")){
				System.out.println("# Job name : bigram , word : " + wordName);	
				System.out.println(nlp.bigrams(wordName)); // run biagram
				System.out.println("--------------------------------------");
			}
			else if(job.equals("tfidf")){
				System.out.println("# Job name : tfidf , word : " + wordName + ", file : " + fileName);
				System.out.println(nlp.tfIDF(wordName,datasetDir+"/"+fileName)); // run tfidf
				System.out.println("--------------------------------------");
			}		
		}
	}

	/**
	 * Reads the input.txt file and add commands(queries) into the jobs list.
	 */
	private void commandReader()
	{	
		Scanner sc = null;
		try {
            sc = new Scanner(new File(commandsDir));
        } catch (Exception e) {
            e.printStackTrace();  
        }
        while (sc.hasNextLine()) 
        {
            Scanner s2 = new Scanner(sc.nextLine());
            StringBuilder str = new StringBuilder();
            while (s2.hasNext())  
                str.append(s2.next() + " ");          
            
            if(str.toString().length()!=0) // do not add empty lines
            	jobs.add(new NLPJob(str.toString(),nlp)); // add job into job list (query)
        }
	}
}