package com.state;

import com.audio.AudioPlayer;
import com.audio.NoAvailableAudioDevice;
import com.core.Position;
import com.core.Size;
import com.display.Camera;
import com.entity.GameObject;
import com.entity.sentientEntity.SentientEntity;
import com.game.Game;
import com.game.Time;
import com.game.settings.AudioSettings;
import com.gfx.SpriteLibrary;
import com.input.Input;
import com.input.mouse.MouseHandler;
import com.map.Map;
import com.state.game.GameState;
import com.ui.UIComponent;
import com.ui.container.UIContainer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
//The state class. Any state extends this class. Holds important stuff like map,
//  audio player, audio settings, gameObjects, ui, camera, etc.
public abstract class State {

    protected AudioSettings audioSettings;
    protected AudioPlayer audioPlayer;
    protected Map map;
    protected List<GameObject> gameObjects;
    protected List<SentientEntity> sentientEntities;
    protected List<UIContainer> uiContainers;
    protected SpriteLibrary spriteLibrary;
    protected Input input;
    protected Camera camera;
    protected Time time;
    private State nextState;
    protected Size windowSize;
    protected String stateName;
    protected MouseHandler mouseHandler;
    private boolean paused;

    public State(Size windowSize, Input input, AudioSettings audioSettings) {

        paused = false;
        this.audioSettings = audioSettings;
        this.windowSize = windowSize;
        this.input = input;
        try {
            audioPlayer = new AudioPlayer(audioSettings);
        } catch (NoAvailableAudioDevice e) {
            e.printStackTrace();
        }
        gameObjects = new ArrayList<>();
        uiContainers = new ArrayList<>();
        sentientEntities = new ArrayList<>();
        spriteLibrary = new SpriteLibrary();
        camera = new Camera(windowSize);
        time = new Time();
        mouseHandler = new MouseHandler();

    }

    //a default initialize method, since both states should always show this container
    protected void initializeUI(){

        uiContainers.add(new MusicToggle(windowSize, audioSettings.getPlayAudio()));

    }

    //updates the audio player, game objects, ui, mouseHandle, etc.
    public void update(Game game){
        //if the game is paused, gameObjects and time is not updated.
        if(!paused) {
            time.update();
            updateGameObjects();
            sortObjectsByPosition();
        }

        audioPlayer.update();

        List.copyOf(uiContainers).forEach(uiContainer -> uiContainer.update(this));

        camera.update(this);

        mouseHandler.update(this);

        if(nextState != null){

            game.setState(nextState);

        }

    }

    //since gameobjects are always changing, so iterating through the original list is best
    private void updateGameObjects() {

        for (int i = 0; i < gameObjects.size(); i++){

            gameObjects.get(i).update(this);

        }

        sentientEntities = getGameObjectsOfClass(SentientEntity.class);

    }

    //sorts the objects by their render layer, then by their x-position
    private void sortObjectsByPosition(){

        gameObjects.sort(Comparator.comparing(GameObject::getRenderLayer)
                .thenComparing(gameObjects -> gameObjects.getPosition().getY()));

    }

    public List<GameObject> getGameObjects() {

        return gameObjects;

    }

    public List<UIContainer> getUiContainers() {

        return uiContainers;

    }

    public Map getGameMap(){

        return map;

    }

    public Camera getCamera() {

        return camera;

    }

    public Time getTime() {

        return time;

    }

    public Position getRandomPosition() {

        return map.getRandomPosition(camera);

    }

    //returns a list of gameobjects that are colliding with a specific object
    public List<GameObject> getCollidingGameObjects(GameObject gameObject) {

        return gameObjects.stream()
                .filter(other -> other.collidesWith(gameObject))
                .collect(Collectors.toList());

    }

    //returns game objects of a specific class
    public <T extends GameObject> List<T> getGameObjectsOfClass(Class<T> type){

        return gameObjects.stream().
                filter(type::isInstance)
                .map(gameObject -> (T) gameObject)
                .collect(Collectors.toList());

    }

    //returns the number of game objects of a certain type
    public int getNumberOfGameObjectsOfClass(Class type){

        return (int) gameObjects.stream()
                .filter(type::isInstance)
                .count();

    }

    public SpriteLibrary getSpriteLibrary(){

        return spriteLibrary;

    }

    //just adds a new gameobject to the state
    public void spawn(GameObject gameObject){

        gameObjects.add(gameObject);

    }

    public void removeObject(GameObject object){

        gameObjects.remove(object);

    }

    public Input getInput() {
        return input;
    }

    //sets the next state that this will transistion to
    public void setNextState(State nextState) {

        this.nextState = nextState;

    }

    public AudioSettings getAudioSettings() {

        return audioSettings;

    }

    public AudioPlayer getAudioPlayer() {

        return audioPlayer;

    }

    //clears the audio player
    public void cleanup() {

        audioPlayer.clear();

    }

    @Override
    public String toString(){

        return stateName;

    }

    public MouseHandler getMouseHandler() {

        return mouseHandler;

    }

    //pauses the state and opens the game menu if is gameState
    public void togglePaused() {

        this.paused = !this.paused;

        if(this instanceof GameState){

            ((GameState) this).toggleGameMenu();

        }

    }

    public void setPaused(boolean paused) {

        this.paused = paused;

    }

    //returns whether or not the UI list contains a UI of a specific class
    public <T extends UIComponent> boolean containsUIOfType(Class<T> type){

        return uiContainers.stream().anyMatch(type::isInstance);

    }

    //removes all instances of a ui of a certain type from the state
    public <T extends UIComponent> void removeUIOfType(Class<T> type){

        List<UIContainer> matchingContainers = List.copyOf(uiContainers).stream()
                                                    .filter(type::isInstance)
                                                    .collect(Collectors.toList());

        uiContainers.removeAll(matchingContainers);

    }

    //adds a uicontainer to the state
    public void addUIContainer(UIContainer container){

        uiContainers.add(container);

    }

}
