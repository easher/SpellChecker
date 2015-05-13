import java.io.*;
import java.lang.StringBuffer;

public class SpellChecker extends DictHash{
    
    String[] alphabet;
	
	public SpellChecker() {
    
        super();
        alphabet = "abcdefghijklmnopqrstuvwxyz".split("");
	}
	
	public static void main(String[] args)throws FileNotFoundException, IOException
    {
		SpellChecker spellCheck = new SpellChecker();
		try{spellCheck.dictMap(args[0]);
			spellCheck.dictMap(args[1]);
			spellCheck.fileChecker(args[2]);

		} catch(FileNotFoundException e1) {
			System.err.println("One of the file name given as an argument doesnt exist");
		
		} catch(IOException e) {
			System.err.println("One of the files you gave as a command line argument has no text");
		}

	}

	
	/** 
	 *inserts dictionary file into hashtable
	 *each word is separated with a newline character.
	 *@param dictionaryfile the path of the dictonary file.
	 */
	public void dictMap(String dictionaryFile) throws FileNotFoundException, IOException {
	
		BufferedReader file = new BufferedReader( new FileReader(dictionaryFile) );
		String word = file.readLine();
		
        while(word != null){
            
			if(word.isEmpty()){
				word = file.readLine();
				continue;
			}
			
			this.insert(word.toLowerCase());
			word = file.readLine();
		}
	}
    
	/**
	 *takes the name of a textFile file and prints out each misspelled word, the line it occured on,
	 *and then lists, line by line suggested correct spelling. 
	 *@param textFile path to file to be spell checked
     */
	public void fileChecker(String textFile) throws FileNotFoundException, IOException {
	
		BufferedReader file = new BufferedReader( new FileReader(textFile) );
		String lineTest = file.readLine();
		int lineNumber = 1;
        
		while(lineTest != null){
			
		if( lineTest.isEmpty() || lineTest.trim().equals("") || lineTest.trim().equals("\n") || lineTest.trim().equals("\t") ){//blankLine
				lineTest = file.readLine();
				continue;
			}
			String[] wordList = lineTest.split(" ");
			for(int i = 0; i < wordList.length; ++i) {
				
				if( wordList[i].isEmpty() ||
				wordList[i].trim().equals("") ||
				wordList[i].trim().equals("\n") ||
				wordList[i].trim().equals("\t") )
                                           
					continue;
                
				spellCheck(""+wordList[i].toLowerCase(), lineNumber);	
			}
			
			++lineNumber;
			lineTest = file.readLine();
		}
	}
	
	/**
     * checks to see if a word is in the dictionary, otherwise it checks for other words
     * @param data word to be spellchecked
     */
	private void spellCheck(String data, int lineNumber) {
	
		data = cleanWord(data);
        
		if(find(data) > -1){
            
            //output options to correct word
        } else {
        	
			System.out.println(data+" at line"+lineNumber);
		    
			for(int i = 0; i < data.length(); ++i) {
				
				for(int j = 1; j < this.alphabet.length; ++j)
					tryInsert(new StringBuffer(data), i, this.alphabet[j]);
				
				if(i > 0)
					trySwapAdjacent(new StringBuffer(data), i);
				
				tryRemove(new StringBuffer(data), i);
			}	
		}
	}
				
    /**
     *sanatizes word for spellchecking
     *@param word to be sanitized
     *@return word striped of characters
     */
	private String cleanWord(String word){

		if( word.replaceAll("[^a-zA-Z ]","").equals("") ) 
			return word;//word is just a character, returning a blank string will cause an error
		
		StringBuffer cleanWord = new StringBuffer(word);
		String item0 = ""+word.charAt(0);
		String item1 = ""+word.charAt(word.length() -1);
		
		if(!item1.equals("'") && item1.replaceAll("[^a-zA-Z ]","").equals("") )
			cleanWord.deleteCharAt(word.length()-1);

		if(item0.replaceAll("[^a-zA-Z ]","").equals(""))
			cleanWord.deleteCharAt(0);
		
		return cleanWord.toString();
	}
	
	/**
     * swaps index pivot with index pivot minus 1, checks to see if it is a word
     *
     * @param tryWord string to try swap
     * @param pivot
     */
	private void trySwapAdjacent(StringBuffer tryWord, int pivot) {
	
		tryWord.insert(pivot+1,tryWord.charAt(pivot - 1));
		tryWord.deleteCharAt(pivot - 1);
		wordCheck(tryWord);
	}

	/**
     * removes a letter from tryword, checks to see if it is a word
     * @param tryWord string to have letter inserted
     * @param index location of removal
     */
	private void tryRemove(StringBuffer tryWord, int index) {
		
		tryWord.deleteCharAt(index);
		wordCheck(tryWord);
	}

	/**
	 * inserts a letter into tryword, checks to see if it is a word
	 * @param tryWord string to have letter inserted
	 * @param index location of insertion
	 * @param s letter to be inserted
	 */
	private void tryInsert(StringBuffer tryWord, int index, String s) {
	
		char c = s.charAt(0);
		
		if(index < tryWord.length()-1){
			tryWord.insert(index,c);
			wordCheck(tryWord);
		} else {
			tryWord.insert(index+1, c);
			wordCheck(tryWord);
		}
	}

	/**
	 * Checks to see if it is a correct word
	 *
	 * @param tryWord string be checked
	 */
	private void wordCheck(StringBuffer tryWord){
	
		int correctWordHash = find(tryWord.toString());
		if(correctWordHash != -1)
			System.out.println(" "+hashTable[correctWordHash]);
	}
}
