package com.state.menu.ui;

import com.core.Size;
import com.ui.UIText;
import com.ui.container.VerticalContainer;

import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
//Displays/holds the top 5 scores in scores.txt
public class UIScoreReport extends VerticalContainer {

    private List<String> topFiveScores;
    private final UIText[] scoreReport;

    public UIScoreReport(Size windowSize) {
        super(windowSize);

        centerChildren = true;

        scoreReport = new UIText[5];

        //sets them to 0 at first (in case blank)
        for (int i = 0; i < scoreReport.length; i++) {

            scoreReport[i] = new UIText("0");
            addUIComponent(scoreReport[i]);

        }

        setScores();

    }

    //sets the displayed text to the top five scores
    private void setScores(){

        try {
            topFiveScores = getScoresFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 5; i++) {

            scoreReport[i].setText("Score " + (i+1) + ": " + topFiveScores.get(i));

        }

    }

    //reads the scores from the file
    private List<String> getScoresFromFile() throws IOException {

        List<Integer> scoreSet;

        BufferedReader reader = null;

        try{
            reader = new BufferedReader(new FileReader("scores.txt"));
        } catch (FileNotFoundException e){
            System.err.println("No file by the name of \"scores\".txt was found.");
        }

        assert reader != null;
        String line = reader.readLine();

        //if the file isn't empty
        if(line != null){

            //DELIMITER
            scoreSet = Arrays.stream(line.split(","))
                    .map(Integer::parseInt).collect(Collectors.toList());

            fillEmptyScores(scoreSet);

        } else {
            //if it is empty
            scoreSet = Arrays.asList(0,0,0,0,0);

        }

        //sorts the scores in descending order since top scores will be
        //processed first
        scoreSet.sort(Collections.reverseOrder());

        //maps to strings and returns
        return scoreSet.stream()
                .map(integer -> Integer.toString(integer))
                .collect(Collectors.toList());

    }

    //fill in any missing scores as 0
    private void fillEmptyScores(List<Integer> scoreSet) {

        if(scoreSet.size() < 5){

            for (int i = scoreSet.size(); i < 5; i++) {

                scoreSet.add(i, 0);

            }

        }

    }

}
