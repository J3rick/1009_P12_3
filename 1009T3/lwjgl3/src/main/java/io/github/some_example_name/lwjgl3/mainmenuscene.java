package io.github.some_example_name.lwjgl3;

import abstractengine.entity;
import abstractengine.scene;
import abstractengine.scenetransitionmanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.List;

public class mainmenuscene extends scene{
	private String sceneName;
	private BitmapFont font;
    private Texture bgImg;
    private Skin mainMenuSkin;
    private Stage mainMenuStage;
    
    private TextButton buttonPlay;
    private TextButton buttonTutorial;
	private scenetransitionmanager sceneTransitionManager;
	
	private Table mainMenuTable;
    
    public mainmenuscene(){
        super("Default main menu Scene");
    }

    public mainmenuscene(String name, Texture bgImg, Color bgColor, OrthographicCamera camera, List<entity> entityList_in){
		super(name,bgColor,bgImg,camera,entityList_in);
    }

    public mainmenuscene(String name, Texture bgImg_in, Color bgColor, OrthographicCamera camera){
		super(name,bgColor,bgImg_in,camera);
    }
    
    // Use this constructor!
    public mainmenuscene(String name, Texture bgImg_in, Color bgColor, OrthographicCamera camera, scenetransitionmanager sceneTransitionManager, String skinPath){
		super(name,bgColor,bgImg_in,camera);
		bgImg = bgImg_in;
		this.font = new BitmapFont();
		font.setColor(Color.BLACK);
		this.sceneTransitionManager = sceneTransitionManager; // allows us to transition btwn scene from this scene
		mainMenuSkin = new Skin(Gdx.files.internal(skinPath));
    }

    private void loadScene(String sceneName) {
    	sceneTransitionManager.loadScene(sceneName);
    	if (sceneName == "main") {
    		((gamemaster) Gdx.app.getApplicationListener()).setGameState(gamemaster.gamestate.PLAYING); // indicate to start game
    	} 
    }
    
    @Override
    public void init() {

    	mainMenuStage = new Stage(new ScreenViewport());
    	Gdx.input.setInputProcessor(mainMenuStage);
    	
    	Table mainMenuTable = new Table();
    	mainMenuTable.setFillParent(true);
    	mainMenuStage.addActor(mainMenuTable);
    	
    	//Begin main menu layout
        mainMenuTable.defaults().space(20);        
        mainMenuTable.padTop(mainMenuStage.getHeight() / 4);
        buttonPlay = new TextButton("Play", mainMenuSkin);
        mainMenuTable.add(buttonPlay);
        buttonPlay.addListener(new ChangeListener() {
        	@Override
        	public void changed(ChangeEvent event, Actor actor) {
        		System.out.println("Main Menu: Play button pressed");
        		loadScene("main");
        	}
        });
        
        mainMenuTable.row();
        buttonTutorial = new TextButton("How to Play", mainMenuSkin);
        mainMenuTable.add(buttonTutorial);
        buttonTutorial.addListener(new ChangeListener() {
        	@Override
        	public void changed(ChangeEvent event, Actor actor) {
        		System.out.println("Tutorial button pressed");
        		loadScene("tutorial");
        	}
        });
    }

    @Override
    public void update() {
        	
    }

    @Override
    public void render(SpriteBatch batch, float x, float y, float width, float height) {
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    	
    	// draw background
    	if (!batch.isDrawing()) { batch.begin();}
    	batch.draw(super.getBgImg(), x, y, width, height); 
    	batch.end();
    	
    	// draw ui elements
        mainMenuStage.act();
        mainMenuStage.draw();

    }
    
    @Override
    public void render(SpriteBatch batch) {
    	batch.draw(super.getBgImg(),0,0);
    }
    

    @Override
    public void dispose() {
        super.getBgImg().dispose();
        mainMenuSkin.dispose();
        mainMenuStage.dispose();
    }
}