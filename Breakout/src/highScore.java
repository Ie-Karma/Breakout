import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class highScore {

	private  int getScore() {
		
		int score = 0;
		
        try {
        	
            FileReader reader = new FileReader("high_scores.txt");
            
            int character;
            int n = 1;
 
            while ((character = reader.read()) != -1) {
            	
            	if(character == '\n') {
            		
            		score = 0;
            		n = 1;
            		
            	}else {
            		
                	score = (score * n) + (character-48); 
                	n=n*10;
            		
            	}
            	
            	
            }
            
            reader.close();
 
        } catch (IOException e) {
        	
            e.printStackTrace();
            
        }
        
        return score;
        //System.out.println(score);

    }

	private  void setScore(int newScore) {
		
	        try {
	            FileWriter writer = new FileWriter("high_scores.txt", true);
	            writer.write("\r\n");
	            writer.write(String.valueOf(newScore));

	            writer.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 	    	 		
	}
	
	public  int compareScore(int actualScore) {
		
		int highScore = 0;
		highScore = getScore();
		
		if(highScore < actualScore) {
			
			highScore = actualScore;
			setScore(highScore);

			
		}
		
		return highScore;
		
	}

	
}
