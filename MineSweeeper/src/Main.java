import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.*;

public class Main extends Application
{
    /*
    Sam Lu
    Mine Sweeper
    */

    private Image dead = new Image("File:Images/Dead.png");
    private Image digit_eight = new Image("File:Images/Digit_Eight.png");
    private Image digit_empty = new Image("File:Images/Digit_Empty.png");
    private Image digit_five = new Image("File:Images/Digit_Five.png");
    private Image digit_four = new Image("File:Images/Digit_Four.png");
    private Image digit_hyphen = new Image("File:Images/Digit_Hyphen.png");
    private Image digit_nine = new Image("File:Images/Digit_Nine.png");
    private Image digit_one = new Image("File:Images/Digit_One.png");
    private Image digit_seven = new Image("File:Images/Digit_Seven.png");
    private Image digit_six = new Image("File:Images/Digit_Six.png");
    private Image digit_three = new Image("File:Images/Digit_Three.png");
    private Image digit_two = new Image("File:Images/Digit_Two.png");
    private Image digit_zero = new Image("File:Images/Digit_Zero.png");
    private Image down = new Image("File:Images/Down.png");
    private Image empty = new Image("File:Images/Empty.png");
    private Image eight = new Image("File:Images/Eight.png");
    private Image exploded = new Image("File:Images/Exploded.png");
    private Image five = new Image("File:Images/Five.png");
    private Image flag = new Image("File:Images/Flag.png");
    private Image four = new Image("File:Images/Four.png");
    private Image happy = new Image("File:Images/Happy.png");
    private Image happy_down = new Image("File:Images/Happy_Down.png");
    private Image incorrectFlag = new Image("File:Images/IncorrectFlag.png");
    private Image mine = new Image("File:Images/Mine.png");
    private Image oh = new Image("File:Images/Oh.png");
    private Image one = new Image("File:Images/One.png");
    private Image question = new Image("File:Images/Question.png");
    private Image seven = new Image("File:Images/Seven.png");
    private Image shades = new Image("File:Images/Shades.png");
    private Image six = new Image("File:Images/Six.png");
    private Image three = new Image("File:Images/Three.png");
    private Image two = new Image("File:Images/Two.png");
    private Image unclicked = new Image("File:Images/Unclicked.png");

    private Canvas canvas;
    private Board mineSweeperBoard;

    private boolean playing;

    private double imageSize = 32;
    private long startTime,gameTime;

    private int status = HAPPY;

    private static final int HAPPY = 0;
    private static final int DOWN = 1;
    private static final int OH = 2;
    private static final int SHADES = 3;
    private static final int DEAD = 4;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("Mine Sweeper");
        mineSweeperBoard = new Board(10, 10, 15);
        Group group = new Group();
        canvas = new Canvas(mineSweeperBoard.getCols() * imageSize + 64,mineSweeperBoard.getRows() * imageSize + 128);
        Scene scene = new Scene(group);
        startTime = System.nanoTime();playing = false;

        group.getChildren().add(canvas);
        scene.setFill(Color.DARKGRAY);
        primaryStage.setScene(scene);
        draw();

        Stage rules_stage = new Stage();
        Group rules_group = new Group();
        Scene rules_scene = new Scene(rules_group, 600, 500);

        Stage about_stage = new Stage();
        Group about_group = new Group();
        Scene about_scene = new Scene(about_group, 200, 100);

        ArrayList <Button> buttons = new ArrayList<>();
        GridPane gridPane = new GridPane();
        gridPane.setLayoutX(32);gridPane.setLayoutY(96);

        Button face = new Button("Face");
        face.setLayoutX(canvas.getWidth() / 2 - imageSize * 3 / 4);face.setLayoutY(imageSize);
        face.setPrefSize(48,48);face.setOpacity(0);buttons.add(face);

        for (int r = 0; r < mineSweeperBoard.getRows(); r++)
        {
            for (int c = 0; c < mineSweeperBoard.getCols(); c++)
            {
                Button button = new Button(r + " " + c);
                button.setPrefSize(imageSize,imageSize);button.setOpacity(0);
                buttons.add(button);gridPane.add(button,r,c);
            }
        }

        ArrayList<Menu> menus = new ArrayList<>();
        MenuBar menuBar = new MenuBar();
        Menu menu_file = new Menu("File");
        Menu menu_help = new Menu("Help");
        Menu menu_newGame = new Menu("New Game");

        menus.add(menu_file);menus.add(menu_help);menus.add(menu_newGame);

        ArrayList<MenuItem> menuItems = new ArrayList<>();
        MenuItem menuItem_easy = new MenuItem("Easy");
        MenuItem menuItem_medium = new MenuItem("Medium");
        MenuItem menuItem_hard = new MenuItem("Hard");
        MenuItem menuItem_viewHighScores = new MenuItem("View High Scores");
        MenuItem menuItem_exit = new MenuItem("Exit");
        MenuItem menuItem_rules = new MenuItem("Rules");
        MenuItem menuItem_about = new MenuItem("About");

        menuItems.add(menuItem_easy);menuItems.add(menuItem_medium);menuItems.add(menuItem_hard);
        menuItems.add(menuItem_viewHighScores);menuItems.add(menuItem_exit);
        menuItems.add(menuItem_rules);menuItems.add(menuItem_about);

        EventHandler<ActionEvent> buttonEvent = event ->
        {
            Button button = ((Button) event.getSource());
        };

        System.out.println(mineSweeperBoard.toString());

        buttonEvent(buttons,buttonEvent);

        EventHandler<ActionEvent> menuEvent = event ->
        {
            String menuItem = ((MenuItem)event.getSource()).getText();
            if ("EasyMediumHard".contains(menuItem))
            {
                if (menuItem.equals("Easy"))
                    mineSweeperBoard.newBoard(10,10,15);
                else if (menuItem.equals("Medium"))
                    mineSweeperBoard.newBoard(15, 15, 40);
                else if (menuItem.equals("Hard"))
                    mineSweeperBoard.newBoard(20,20,100);

                canvas.setWidth(mineSweeperBoard.getCols() * imageSize + 64);
                canvas.setHeight(mineSweeperBoard.getRows() * imageSize + 96);

                primaryStage.setWidth(mineSweeperBoard.getCols() * imageSize + 77);
                primaryStage.setHeight(mineSweeperBoard.getRows() * imageSize + 163);
                primaryStage.centerOnScreen();

                status = HAPPY;playing = false;
                startTime = System.nanoTime();gameTime = 0;
                buttons.removeIf(button -> !button.getText().equals("Face"));
                face.setLayoutX(canvas.getWidth() / 2 - imageSize * 3 / 4);
                gridPane.getChildren().clear();

                for (int r = 0; r < mineSweeperBoard.getRows(); r++)
                {
                    for (int c = 0; c < mineSweeperBoard.getCols(); c++)
                    {
                        Button button = new Button(r + " " + c);
                        button.setPrefSize(imageSize,imageSize);button.setOpacity(0);
                        buttons.add(button);gridPane.add(button,r,c);
                    }
                }
                buttonEvent(buttons,buttonEvent);
            }
            else if (menuItem.equals("View High Scores"))
            {

            }
            else if (menuItem.equals("Exit"))
            {
                // Close all stages when primary stage is closed
                primaryStage.close();
                rules_stage.close();
                about_stage.close();
            }
            else if (menuItem.equals("Rules"))
            {
                TextArea rules_textArea = new TextArea();
                rules_textArea.setPrefSize(600,500);
                rules_textArea.setEditable(false);
                rules_textArea.appendText("Game Modes:\n" +
                        "  -Easy 10 by 10 with 15 mines\n" +
                        "  -Medium 15 by 15 with 40 mines\n" +
                        "  -Hard 20 by 20 with 100 mines\n" +
                        "Rules:\n" +
                        "  -Winning:\n" +
                        "    *You win when all the non-mine squares have been revealed.\n" +
                        "  -Losing:\n" +
                        "    *You lose when you reveal a mine.\n" +
                        "  -Controls:\n" +
                        "    *Right clicking an un-revealed square cycles the following marks:\n" +
                        "      -Flag - Denotes the square as a mine\n" +
                        "      -Question - Denotes the square as unknown\n" +
                        "      -Un-marked - Removes all markings\n" +
                        "    *Left clicking\n" +
                        "      -Reveals the clicked square\n" +
                        "    *Pressing the left button down, then holding the right button down and releasing the left button.\n" +
                        "      -Works on revealed numbers\n" +
                        "      -Reveals the nighboring locations\n" +
                        "      -Only functions when there are enough locations marked with flags to correspond to the numbers\n" +
                        "Symbols:\n" +
                        "  -1 to 8: number of mines near by\n" +
                        "  -Mine: unfound mine\n" +
                        "  -Mine with x: Incorrect Flag\n" +
                        "  -Red Mine: Exploded mine\n");
                rules_group.getChildren().add(rules_textArea);
                rules_stage.setTitle("Rules");
                rules_stage.setScene(rules_scene);
                rules_stage.show();
            }
            else if (menuItem.equals("About"))
            {
                TextArea about_textArea = new TextArea();
                about_textArea.setPrefSize(200,100);
                about_textArea.setFont(Font.font(15));
                about_textArea.setEditable(false);
                about_textArea.appendText("Mine Sweeper v2\n" +
                        "Created By Sam Lu\n");
                about_group.getChildren().add(about_textArea);
                about_stage.setTitle("About");
                about_stage.setScene(about_scene);
                about_stage.show();
            }
            draw();
        };

        for (MenuItem menuItem : menuItems)
            menuItem.setOnAction(menuEvent);

        menu_file.getItems().addAll(menu_newGame,menuItem_viewHighScores,menuItem_exit);
        menu_newGame.getItems().addAll(menuItem_easy,menuItem_medium,menuItem_hard);
        menu_help.getItems().addAll(menuItem_rules,menuItem_about);
        menuBar.getMenus().addAll(menu_file,menu_help);

        new AnimationTimer()
        {
            public void handle(long currentTime)
            {
                if (gameTime < 9999 && status != SHADES && status != DEAD && playing)
                {
                    gameTime = ((currentTime - startTime) / 1000000000);
                    draw();
                }
            }
        }.start();

        group.getChildren().addAll(menuBar,face,gridPane);

        primaryStage.show();
    }

    public void draw()
    {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(0,0,canvas.getWidth(),canvas.getHeight());

        Image face = null;
        if (status == HAPPY)
            face = happy;
        else if (status == DOWN)
            face = happy_down;
        else if (status == OH)
            face = oh;
        else if (status == SHADES)
            face = shades;
        else if (status == DEAD)
            face = dead;
        gc.drawImage(face,canvas.getWidth() / 2 - imageSize * 3 / 4, imageSize, 48, 48);

        String minesLeft = "" + (mineSweeperBoard.getMines() - mineSweeperBoard.getNumFlags());
        for (int i = 3; i >= 0; i--)
        {
            Image number = null;
            String check = "0";
            if (minesLeft.length() > 0 && !minesLeft.equals("-") || i == 0 && minesLeft.equals("-"))
                check = minesLeft.substring(minesLeft.length() - 1);
            if (check.equals("0"))
                number = digit_zero;
            else if (check.equals("1"))
                number = digit_one;
            else if (check.equals("2"))
                number = digit_two;
            else if (check.equals("3"))
                number = digit_three;
            else if (check.equals("4"))
                number = digit_four;
            else if (check.equals("5"))
                number = digit_five;
            else if (check.equals("6"))
                number = digit_six;
            else if (check.equals("7"))
                number = digit_seven;
            else if (check.equals("8"))
                number = digit_eight;
            else if (check.equals("9"))
                number = digit_nine;
            else if (check.equals("-"))
                number = digit_hyphen;
            if (minesLeft.length() > 0 && !minesLeft.equals("-"))
                minesLeft = minesLeft.substring(0, minesLeft.length() - 1);
            gc.drawImage(number,32 + 26 * i,imageSize,26,46);
        }
        long time = gameTime;
        for (int i = 0; i < 4; i++)
        {
            Image number = null;
            long check = time % 10;time /= 10;
            if (check == 0)
                number = digit_zero;
            else if (check == 1)
                number = digit_one;
            else if (check == 2)
                number = digit_two;
            else if (check == 3)
                number = digit_three;
            else if (check == 4)
                number = digit_four;
            else if (check == 5)
                number = digit_five;
            else if (check == 6)
                number = digit_six;
            else if (check == 7)
                number = digit_seven;
            else if (check == 8)
                number = digit_eight;
            else if (check == 9)
                number = digit_nine;
            gc.drawImage(number,canvas.getWidth() - 58 - 26 * i,imageSize,26,46);
        }

        for (int r = 0; r < mineSweeperBoard.getRows(); r++)
        {
            for (int c = 0; c < mineSweeperBoard.getCols(); c++)
            {
                if (status == DEAD && !mineSweeperBoard.isMine(r,c) && mineSweeperBoard.isFlagged(r,c))
                    gc.drawImage(incorrectFlag,c * imageSize + 32,r * imageSize + 96,imageSize,imageSize);
                else if (mineSweeperBoard.isFlagged(r,c))
                    gc.drawImage(flag,c * imageSize + 32,r * imageSize + 96,imageSize,imageSize);
                else if (mineSweeperBoard.isQuestion(r,c))
                    gc.drawImage(question,c * imageSize + 32,r * imageSize + 96,imageSize,imageSize);
                else if (status == DEAD && mineSweeperBoard.isMine(r,c) && !mineSweeperBoard.isShowing(r,c))
                    gc.drawImage(mine,c * imageSize + 32,r * imageSize + 96,imageSize,imageSize);
                else if (mineSweeperBoard.isDown(r,c) && !mineSweeperBoard.isShowing(r,c))
                    gc.drawImage(down,c * imageSize + 32,r * imageSize + 96,imageSize,imageSize);
                else if (!mineSweeperBoard.isShowing(r,c))
                    gc.drawImage(unclicked,c * imageSize + 32,r * imageSize + 96,imageSize,imageSize);
                else if (mineSweeperBoard.isMine(r,c))
                    gc.drawImage(exploded,c * imageSize + 32,r * imageSize + 96,imageSize,imageSize);
                else if (mineSweeperBoard.getNumOfNearMines(r,c) == 0)
                    gc.drawImage(empty,c * imageSize + 32,r * imageSize + 96,imageSize,imageSize);
                else if (mineSweeperBoard.getNumOfNearMines(r,c) == 1)
                    gc.drawImage(one,c * imageSize + 32,r * imageSize + 96,imageSize,imageSize);
                else if (mineSweeperBoard.getNumOfNearMines(r,c) == 2)
                    gc.drawImage(two,c * imageSize + 32,r * imageSize + 96,imageSize,imageSize);
                else if (mineSweeperBoard.getNumOfNearMines(r,c) == 3)
                    gc.drawImage(three,c * imageSize + 32,r * imageSize + 96,imageSize,imageSize);
                else if (mineSweeperBoard.getNumOfNearMines(r,c) == 4)
                    gc.drawImage(four,c * imageSize + 32,r * imageSize + 96,imageSize,imageSize);
                else if (mineSweeperBoard.getNumOfNearMines(r,c) == 5)
                    gc.drawImage(five,c * imageSize + 32,r * imageSize + 96,imageSize,imageSize);
                else if (mineSweeperBoard.getNumOfNearMines(r,c) == 6)
                    gc.drawImage(six,c * imageSize + 32,r * imageSize + 96,imageSize,imageSize);
                else if (mineSweeperBoard.getNumOfNearMines(r,c) == 7)
                    gc.drawImage(seven,c * imageSize + 32,r * imageSize + 96,imageSize,imageSize);
                else if (mineSweeperBoard.getNumOfNearMines(r,c) == 8)
                    gc.drawImage(eight,c * imageSize + 32,r * imageSize + 96,imageSize,imageSize);
            }
        }
    }

    public void buttonEvent(ArrayList <Button> buttons, EventHandler <ActionEvent> buttonEvent)
    {
        for (Button button : buttons)
        {
            button.setOnMousePressed(mouseEvent ->
            {
                if (mouseEvent.isPrimaryButtonDown())
                {
                    if (button.getText().equals("Face"))
                    {
                        if (status == SHADES || status == DEAD)
                        {
                            mineSweeperBoard.newBoard(mineSweeperBoard.getRows(), mineSweeperBoard.getCols(), mineSweeperBoard.getMines());
                            playing = false;gameTime = 0;
                        }
                        status = DOWN;
                    }
                    else if (status != SHADES && status != DEAD)
                    {
                        String[] point = button.getText().split(" ");
                        int row = Integer.parseInt(point[1]), col = Integer.parseInt(point[0]);
                        if (!mineSweeperBoard.isFlagged(row, col) && !mineSweeperBoard.isQuestion(row, col))
                        {
                            mineSweeperBoard.setDown(row, col, true);
                            status = OH;
                        }
                    }
                }
                draw();
            });
            button.setOnMouseReleased(mouseEvent ->
            {
                if (mouseEvent.getButton() == MouseButton.SECONDARY)
                {
                    if (!button.getText().equals("Face") && status != SHADES && status != DEAD)
                    {
                        String[] point = button.getText().split(" ");
                        int row = Integer.parseInt(point[1]), col = Integer.parseInt(point[0]);
                        if (!mineSweeperBoard.isShowing(row, col))
                        {
                            if (mineSweeperBoard.isFlagged(row, col))
                            {
                                mineSweeperBoard.setFlagged(row, col, false);
                                mineSweeperBoard.setQuestion(row, col , true);
                            }
                            else if (mineSweeperBoard.isQuestion(row,col))
                                mineSweeperBoard.setQuestion(row, col , false);
                            else
                                mineSweeperBoard.setFlagged(row, col, true);
                        }
                    }
                }
                else if (mouseEvent.getButton() == MouseButton.PRIMARY)
                {
                    if (button.getText().equals("Face"))
                        status = HAPPY;
                    else if (status != SHADES && status != DEAD)
                    {
                        String[] point = button.getText().split(" ");
                        int row = Integer.parseInt(point[1]), col = Integer.parseInt(point[0]);
                        if (!mineSweeperBoard.isFlagged(row, col) && !mineSweeperBoard.isQuestion(row, col))
                        {
                            mineSweeperBoard.setDown(row, col, false);
                            mineSweeperBoard.setShowing(row, col, true);
                            if (!playing)
                            {
                                startTime = System.nanoTime();
                                playing = true;
                            }
                            if (mineSweeperBoard.isMine(row, col))
                            {
                                status = DEAD;
                                playing = false;
                            }
                            else
                                status = HAPPY;
                        }
                    }
                }
                if (mineSweeperBoard.win())
                {
                    status = SHADES;
                    mineSweeperBoard.flagAll();
                }
                draw();
            });
            button.setOnAction(buttonEvent);
        }
    }

    public void menuEvent()
    {

    }
}