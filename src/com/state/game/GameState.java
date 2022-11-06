package com.state.game;

import com.controller.GameController;
import com.entity.sentientEntity.SentientEntity;
import com.entity.sentientEntity.action.staticAction.Death;
import com.game.settings.AudioSettings;
import com.controller.NPCController;
import com.controller.PlayerController;
import com.core.Condition;
import com.core.Size;
import com.entity.sentientEntity.sentientEntities.npc.friendlies.LightSpirit;
import com.entity.sentientEntity.sentientEntities.npc.hostiles.DarkGhost;
import com.entity.sentientEntity.sentientEntities.npc.hostiles.DarkGolem;
import com.entity.sentientEntity.sentientEntities.npc.NPC;
import com.entity.sentientEntity.sentientEntities.Player;
import com.game.Game;
import com.game.settings.GameSettings;
import com.gfx.ImageLoader;
import com.input.Input;
import com.map.Map;
import com.state.State;
import com.state.game.ui.UIGameOverMenu;
import com.state.game.ui.UIPauseMenu;
import com.state.game.ui.UIScore;
import com.ui.*;
import com.ui.clickable.UIMiniMap;
import com.ui.container.VerticalContainer;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
//The game state. This state is active when in game.
public class GameState extends State {

    //The conditions that need to be met for game-over
    private List<Condition> lossConditions;
    //If the game is playing or not
    private boolean playing;

    private final GameSettings gameSettings;
    //input interpreted whilst in game
    private final GameController gameController;

    //for easy access to the player
    private Player player;

    //for easy access to the score
    public int score;

    public GameState(Size windowSize, Input input, AudioSettings audioSettings) {
        super(windowSize, input, audioSettings);

        //debug
        stateName = "Game State";

        gameSettings = new GameSettings(false);
        gameController = new GameController(input);

        audioPlayer.playMusic("gameMusic2");

        playing = true;

        map = new Map(new Size(64, 64), spriteLibrary);
        initializeUI();

        initializePlayer();
        initializeUI();
        initializeConditions();

        score = 0;

    }

    @Override
    public void update(Game game){
        super.update(game);

        gameController.update(this);

        //When the player dies
        if(player.isShouldDispose() && !player.isDead()){

            player.perform(new Death(player));

        }

        if(playing){

            //checking if all the loss conditions have been met
            //if not, continues. If so, calls lose() method.
            if (lossConditions.stream().allMatch(Condition::isMet)){

                lose();

            }

        }

        updateNPCCount();

    }

    //Makes sure the npcs constantly respawn to a count of 200 in total (friendlies, ghosts, and golems)
    private void updateNPCCount() {
        //It is set to 201 because the player is also a sentient entity
        if(getNumberOfGameObjectsOfClass(SentientEntity.class) < 201){

            NPC hostileNPC;
            NPC friendlyNPC = null;

            //a 1 in 15 chance of a friendly and Golem spawning instead of a ghost
            if(Math.random() * 15 < 1){

                hostileNPC = new DarkGolem(new NPCController(), spriteLibrary);
                friendlyNPC = new LightSpirit(new NPCController(), spriteLibrary);

            }
            else{

                hostileNPC = new DarkGhost(new NPCController(), spriteLibrary);

            }

            hostileNPC.setPosition(getRandomPosition());
            gameObjects.add(hostileNPC);
            gameObjects.add(hostileNPC.getHealthBar());

            if(friendlyNPC != null){

                friendlyNPC.setPosition(getRandomPosition());
                gameObjects.add(friendlyNPC);

            }

        }
    }

    //when you die, this is called. It sets the playing variable to false and
    //creates a game-over menu.
    private void lose() {

        playing = false;
        setPaused(true);
        uiContainers.add(new UIGameOverMenu(windowSize, score));

        //saves the score when you die
        try {
            saveScore();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Writes your score to score.txt
    private void saveScore() throws IOException {

        /*
        This goes how it does because the reader must read everything in the file,
        store it, and then give it to the writer so that the writer can write in
        what was already there and then the new score, since the writer cannot
        be set to write at the end of the file (as far as I know).
         */
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader("scores.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert reader != null;
        String all = reader.readLine();

        BufferedWriter writer = null;
        try{
            writer = new BufferedWriter(new FileWriter("scores.txt"));

        //JAVA LIBRARY EXCEPTIONS
        } catch (IOException e){
            e.printStackTrace();
        }

        try {
            //writes the previous scores if any
            assert writer != null;
            if(all != null) {
                writer.write(all);
            }
            //then the new score
            writer.write(score + "," );

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert writer != null;
            writer.close();
            reader.close();
        }

    }

    private void initializeConditions() {

        //if the player is dead and the death animation has finished, the loss condition have been met
        lossConditions = List.of(() -> player.isShouldDispose(), () -> player.isNotDying());

    }

    //simply spawns in the player.
    private void initializePlayer() {

        player = new Player(new PlayerController(input), spriteLibrary);

        gameObjects.add(player);
        camera.focusOn(player);

    }

    //Initializes all of the HUD UI like the minimap and score.
    @Override
    protected void initializeUI() {
        super.initializeUI();

        UIScore uiScore = new UIScore(windowSize);
        uiScore.setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.START));

        VerticalContainer minimapContainer = getMinimapContainer();

        uiContainers.add(minimapContainer);
        uiContainers.add(uiScore);

    }

    //creates and returns a container containing a minimap
    private VerticalContainer getMinimapContainer() {

        VerticalContainer minimapContainer = new VerticalContainer(windowSize);
        minimapContainer.setAlignment(new Alignment(Alignment.Position.START, Alignment.Position.END));
        minimapContainer.setContainerImage((BufferedImage) ImageLoader.loadImage("/sprites/HUD/map.png"));
        minimapContainer.addUIComponent(new UIMiniMap(this));
        return minimapContainer;

    }

    //toggles the game menu (E)
    public void toggleGameMenu(){

        if(containsUIOfType(UIPauseMenu.class)){

            removeUIOfType(UIPauseMenu.class);

        } else {

            uiContainers.add(new UIPauseMenu(windowSize));

        }

    }

    public Player getPlayer() {

        return player;

    }

    public int getScore() {

        return score;

    }

    public GameSettings getGameSettings() {

        return gameSettings;

    }

    public boolean isPlaying() {

        return playing;

    }

}
