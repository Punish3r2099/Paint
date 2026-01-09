package com.example.demo1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.effect.Light;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;


/**
 * This HelloApplication recreates my version of MSPaint
 *
 * @author Aiden Moran
 * @version 1.5.0
 * @since 2022-09-09
 */
public class HelloApplication extends Application {
    public Image buffImage = null;
    public VBox vb = new VBox(20);
    public Font font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12);
    public Line user = new Line();
    public ImageView imageView3 = new ImageView();
    public File returnFile;
    Stack<Image> undoStack = new Stack<Image>();
    Stack<Image> redoStack = new Stack<Image>();
    //gc.drawImage(i, 0, 0);

    boolean buttonPress = true;


    File newFile = null;
    //Menu Bar
    MenuBar menuBar = new MenuBar();
    Menu menuFileF = new Menu("File");
    Menu menuFileE = new Menu("Edit");
    Menu menuFileH = new Menu("Help");
    CheckMenuItem openFile = new CheckMenuItem("Open File...");
    MenuItem add = new MenuItem("Save");
    MenuItem add1 = new MenuItem("Save As");
    MenuItem close = new MenuItem("Close");
    MenuItem square = new MenuItem("Square");
    MenuItem circle = new MenuItem("Circle");
    MenuItem rectangle = new MenuItem("Rectangle");
    MenuItem ellipse = new MenuItem("Ellipse");
    MenuItem triangle = new MenuItem("Triangle");
    MenuItem polygon = new MenuItem("n-sided Polygon");
    MenuItem undo = new MenuItem("Undo");
    MenuItem redo = new MenuItem("Redo");
    MenuItem copy = new MenuItem("Copy Image");
    MenuItem paste = new MenuItem("Paste Image");
    MenuItem color = new MenuItem("Color");
    MenuItem thickness = new MenuItem("Line Thickness");
    MenuItem help = new MenuItem("Help");
    MenuItem option = new MenuItem("Options");
    Button endt = new Button("Back");
    Button clear = new Button("Clear Canvas");
    Canvas canvas = null;
    GraphicsContext gc = null;
    Canvas canvas2 = null;
    GraphicsContext gc2 = null;
    Button draw = null;
    ImageView imageView = null;
    ImageView imageView2 = null;
    Image i = null;

    /**
     * @param primaryStage
     * @throws IOException
     * @throws AWTException
     */
    @Override
    public void start(Stage primaryStage) throws IOException, AWTException {
        


        
        menuBar.getMenus().addAll(menuFileF,menuFileE, menuFileH);
        menuFileF.getItems().addAll(openFile, add,add1,close);
        menuFileE.getItems().addAll(color,thickness,square,circle,rectangle,ellipse,triangle,polygon,copy,paste);
        menuFileH.getItems().addAll(option,help);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"),new FileChooser.ExtensionFilter("PNG","*.png"),new FileChooser.ExtensionFilter("SVG","*.svg"));




        //Open File
       // openFile.setOnAction(new EventHandler<ActionEvent>() {
           // @Override
            //public void handle(ActionEvent actionEvent) {
                FileChooser file = new FileChooser();
                newFile = file.showOpenDialog(primaryStage);
                InputStream stream =  new FileInputStream(newFile);
                i = new Image(stream);



            //}
        //});
        imageView = new ImageView(i);
        imageView2 = new ImageView(i);
        Logger logger = Logger.getLogger(HelloApplication.class.getName());
        InputStream drawpic = new FileInputStream("/Users/108446/School/drawpic.png");
        Image drawi = new Image(drawpic);
        ImageView drawiv = new ImageView(drawi);
        InputStream linepic = new FileInputStream("/Users/108446/School/Line pic.png");
        Image linei = new Image(linepic);
        ImageView lineiv = new ImageView(linei);
        InputStream eraserpic = new FileInputStream("/Users/108446/School/eraser pic.png");
        Image eraseri = new Image(eraserpic);
        ImageView eraseriv = new ImageView(eraseri);

        draw = new Button("Draw",drawiv);
        Button line = new Button("Line",lineiv);
        Button eraser = new Button("Eraser",eraseriv);

        canvas = new Canvas(i.getWidth(), i.getHeight());
        gc = canvas.getGraphicsContext2D();
        canvas2 = new Canvas(i.getWidth(), i.getHeight());
        gc2 = canvas2.getGraphicsContext2D();
        Scene scene = new Scene(new VBox());


        imageView.setPreserveRatio(true);
        ScrollBar sch = new ScrollBar();
        ScrollBar scv = new ScrollBar();

        StackPane stack1 = new StackPane(imageView,canvas);
        StackPane stack2 = new StackPane(imageView2,canvas2);

        //Tabs
        Tab tab_1 = new Tab("Tab_1");
        Tab tab_2 = new Tab("Tab_2");
        tab_1.setContent(stack1);
        tab_2.setContent(stack2);
        TabPane tabpane = new TabPane(tab_1,tab_2);

        //Save
        add.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * @param actionEvent
             * saves content
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                //File file2 = fileChooser.getInitialDirectory();
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(i, null), "jpg", newFile);
                } catch (IOException e) {
                    // TODO: handle exception here
                }
            }
        });
        final KeyCombination keyComb1 = new KeyCodeCombination(KeyCode.S,
                KeyCombination.CONTROL_DOWN);
        scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler() {
            /**
             * @param event
             * same as above
             */
            @Override
            public void handle(Event event) {
                if (keyComb1.match((KeyEvent) event)) {
                    File file2 = fileChooser.getInitialDirectory();
                    try {
                        ImageIO.write(SwingFXUtils.fromFXImage(i, null), "jpg", file2);
                    } catch (IOException e) {
                        // TODO: handle exception here
                    }
            }
            }
        });

        //Save As
        add1.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * @param actionEvent 
             * File Chooser opens up files on computer and saves with snapshot
             */
            public void handle(ActionEvent actionEvent) {
                File file1 = fileChooser.showSaveDialog(primaryStage);
                try {
                    Image snapshot = canvas.snapshot(null, null);

                    ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "jpg", file1);
                } catch (IOException e) {
                    // TODO: handle exception here
                }
            }

        });
        final KeyCombination keyComb2 = new KeyCodeCombination(KeyCode.A,
                KeyCombination.CONTROL_DOWN);
        scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler() {
            /**
             * @param event 
             * same as above
             */
            @Override
            public void handle(Event event) {
                if (keyComb2.match((KeyEvent) event)) {
                    File file1 = fileChooser.showSaveDialog(primaryStage);
                    try {
                        Image snapshot = canvas.snapshot(null, null);

                        ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "jpg", file1);
                    } catch (IOException e) {
                        // TODO: handle exception here
                    }
                }
            }
        });
        //color options
        Label label2= new Label("Color Options");
        VBox layout2= new VBox(20);
        layout2.getChildren().addAll(label2);
        Scene colors= new Scene(layout2,300,250);
        color.setOnAction(e -> primaryStage.setScene(colors));
        ColorPicker colorPicker = new ColorPicker();
        Button eyedrop = new Button("Eye Dropper");
        eyedrop.setOnAction(e -> primaryStage.setScene(scene));
        eyedrop.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            /**
             * @param e 
             * gets mouse coordinates and attempts colorpicker
             */
            @Override
            public void handle(MouseEvent e) {
                int x = (int) e.getX();
                int y = (int) e.getY();
                Color color = i.getPixelReader().getColor(x, y);
                //PixelReader r = i.getPixelReader();
                //Color argb = r.getColor(x, y);
                colorPicker.setValue(color);
            }
        });

        colorPicker.setValue(Color.CORAL);

        colorPicker.setOnAction(new EventHandler() {
            /**
             * @param t 
             * attempts to do color chooser
             */
            public void handle(Event t) {
                user.setStroke(colorPicker.getValue());
            }
        });
        Button endc = new Button("Back");
        endc.setOnAction(e -> primaryStage.setScene(scene));


        //thickness options
                Label label3= new Label("Thickness Options");
                VBox layout3= new VBox(20);
                layout3.getChildren().addAll(label3);
                Scene thick= new Scene(layout3,300,250);
                thickness.setOnAction(e -> primaryStage.setScene(thick));
                //Circle thick3 = new Circle();
                //Circle thick5 = new Circle();
                ///thick3.setRadius(10.0f);
                //thick5.setRadius(20.0f);
                //thick3.setFill(Color.BLACK);
                Button buttonthick3 = new Button("Stroke Width: 3");
                buttonthick3.setOnAction(e -> user.setStrokeWidth(20));
                Button buttonthick5 = new Button("Stroke Width: 5");
                buttonthick5.setOnAction(e -> user.setStrokeWidth(50));
                endt.setOnAction(e -> primaryStage.setScene(scene));


        //Drawing
        Line st = new Line();
        Line dash = new Line(50, 50, 100, 100);
        dash.setStrokeLineCap(StrokeLineCap.SQUARE);
        dash.getStrokeDashArray().addAll(15d, 5d, 15d, 15d, 20d);
        dash.setStrokeDashOffset(10);

        draw.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * @param actionEvent 
             * gets path and sets line to x and y coordinates after pressed for tab 1
             * sets stroke width
             * sets stroke color
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        gc.beginPath();
                        gc.lineTo(t.getX(),t.getY());
                        gc.stroke();
                        gc.setLineWidth(user.getStrokeWidth());
                        gc.setStroke(user.getFill());

                    }
                });
                canvas2.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    /**
                     * @param t 
                     * gets path and sets line to x and y coordinates after pressed for tab 2
                     * sets stroke width
                     * sets stroke color
                     */
                    @Override
                    public void handle(MouseEvent t) {
                        gc2.beginPath();
                        gc2.lineTo(t.getX(),t.getY());
                        gc2.stroke();
                        gc2.setLineWidth(user.getStrokeWidth());
                        gc2.setStroke(user.getFill());

                    }
                });
                canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>(){
                    /**
                     * @param t 
                     * when dragged it creates another path over and over like above
                     * sets stroke width and color
                     */
                    @Override
                    public void handle(MouseEvent t){
                        gc.beginPath();
                        gc.lineTo(t.getX(),t.getY());
                        gc.stroke();
                        gc.setLineWidth(user.getStrokeWidth());
                        gc.setStroke(user.getStroke());
                    }
                });
                canvas2.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>(){
                    /**
                     * @param t 
                     * same as above but for tab 2
                     */
                    @Override
                    public void handle(MouseEvent t){
                        gc2.beginPath();
                        gc2.lineTo(t.getX(),t.getY());
                        gc2.stroke();
                        gc2.setLineWidth(user.getStrokeWidth());
                        gc2.setStroke(user.getStroke());
                    }
                });
            }
        });

        //line
        line.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * @param actionEvent 
             * same as draw but for a line
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        gc.beginPath();
                        gc.setLineWidth(user.getStrokeWidth());
                        gc.lineTo(t.getX(),t.getY());
                        gc2.beginPath();
                        gc2.setLineWidth(user.getStrokeWidth());
                        gc2.lineTo(t.getX(),t.getY());
                    }
                });


                canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
                    /**
                     * @param t 
                     * when released the line is created in the canvas
                     */
                    @Override
                    public void handle(MouseEvent t) {
                        gc.moveTo(t.getX(),t.getY());
                        gc.closePath();
                        gc2.moveTo(t.getX(),t.getY());
                        gc2.closePath();
                    }
                });
                st.getStrokeDashArray().addAll(30.0, 15.0);
            }
        });
        Group l = new Group();
        l.getChildren().add(st);
        Group g = new Group();
        g.getChildren().add(dash);

        //Square
        Rectangle sq = new Rectangle();
        square.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * @param actionEvent 
             * when pressed a square is created at set coordinates
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        gc.strokeRect(50,50,50,50);
                        gc2.strokeRect(50,50,50,50);

                        //gc.re(sq.());

                    }
                });
            }
        });
        //Rectangle
        Rectangle rect = new Rectangle();
        rectangle.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * @param actionEvent 
             * creates rectangle at set coordinates when pressed
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        gc.strokeRect(100,100,50,100);
                        gc2.strokeRect(100,100,50,100);

                    }
                });
            }
        });


        //Circle
        Circle circles = new Circle();
        circle.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * @param actionEvent 
             * creates circle at set coordinates
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        gc.strokeOval(100,100,100,100);
                        gc2.strokeOval(100,100,100,100);


                    }
                });
            }
        });

        //Ellipse
        Ellipse ell = new Ellipse();
        ellipse.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * @param actionEvent 
             * creates ellipse at set coordinates
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        gc.strokeOval(200.0f, 120.0f, 150.0f, 80.f);
                        gc2.strokeOval(200.0f, 120.0f, 150.0f, 80.f);


                    }
                });
            }
        });

        //Polygon
        TextField n = new TextField();
        Group textF = new Group();
        textF.getChildren().add(n);
        String text1 = n.getText();
        //int int1 = Integer.parseInt(text1);
        polygon.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * @param actionEvent 
             * when pressed any sided polygon attempts to be created
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        Scanner sc= new Scanner(System.in);
                        //double angle = 360/int1;
                        Line dist = new Line();
                        Line side = new Line();
                        double x = t.getX();
                        double y = t.getY();
                        dist.setStartX(x);
                        dist.setStartY(y);
                        dist.setEndX(x + 150);
                        dist.setEndY(y);

                    }
                });
            }
        });

        //Triangle
        triangle.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * @param actionEvent 
             * when pressed triangle is coordinates
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        gc.strokeLine(100,100,200,200);
                        gc.strokeLine(200,200,100,200);
                        gc.strokeLine(100,200,100,100);
                    }
                });
            }
        });
        //eraser
        eraser.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * @param actionEvent 
             * when pressed eraser erases with white color
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        gc.beginPath();
                        gc.lineTo(t.getX(),t.getY());
                        gc.stroke();
                        gc.setLineWidth(user.getStrokeWidth());
                        gc.setStroke(Color.WHITE);

                    }
                });
                canvas2.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    /**
                     * @param t 
                     * same as above but for tab 2
                     */
                    @Override
                    public void handle(MouseEvent t) {
                        gc2.beginPath();
                        gc2.lineTo(t.getX(),t.getY());
                        gc2.stroke();
                        gc2.setLineWidth(user.getStrokeWidth());
                        gc2.setStroke(Color.WHITE);

                    }
                });
                canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>(){
                    /**
                     * @param t 
                     * when dragged same as pressed
                     */
                    @Override
                    public void handle(MouseEvent t){
                        gc.beginPath();
                        gc.lineTo(t.getX(),t.getY());
                        gc.stroke();
                        gc.setLineWidth(user.getStrokeWidth());
                        gc.setStroke(Color.WHITE);
                    }
                });
                canvas2.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>(){
                    /**
                     * @param t 
                     * same as tab 2
                     */
                    @Override
                    public void handle(MouseEvent t){
                        gc2.beginPath();
                        gc2.lineTo(t.getX(),t.getY());
                        gc2.stroke();
                        gc2.setLineWidth(user.getStrokeWidth());
                        gc2.setStroke(Color.WHITE);
                    }
                });
            }
        });


        // Undo/redo
        undoStack.push(imageView.snapshot(new SnapshotParameters(), null));
        undo.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * @param actionEvent 
             * undos last event
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                redoStack.push(undoStack.pop());
            }
        });

        redo.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * @param actionEvent 
             * redo last event
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                redoStack.pop();

            }
        });


        StackPane stackPane = new StackPane();
        ObservableList list = stackPane.getChildren();
        list.addAll(user,dash);

        //close app
        Label saving= new Label("Do You Want To Save?");
        VBox layout4= new VBox(20);
        layout4.getChildren().addAll(saving);
        Scene closing= new Scene(layout4,300,250);
        close.setOnAction(e -> primaryStage.setScene(closing));
        Button buttonS = new Button("Save");
        Button buttonC = new Button("Cancel");
        Button buttonSw = new Button("Close without saving");
        buttonC.setOnAction(e -> primaryStage.setScene(scene));
        buttonS.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * @param actionEvent 
             * when closing and chose to save then options to save like save as
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                File file2 = fileChooser.getInitialDirectory();
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(i, null), "jpg", file2);
                } catch (IOException e) {
                    // TODO: handle exception here
                }

                Platform.exit();
                System.exit(0);
            }
        });
        buttonSw.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * @param actionEvent 
             * exits program
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.exit();
                System.exit(0);
            }
        });
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        //Vertical scroll bar
        scv.setMin(0);
        scv.setOrientation(Orientation.VERTICAL);
        //scv.setPrefHeight(i.getHeight());
        scv.setPrefWidth(20);
        scv.setLayoutX(0);
        scv.setLayoutY(0);
        scv.valueProperty().addListener(new ChangeListener<Number>() {
            /**
             * @param ov 
             * @param old_val
             * @param new_val
             */
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                vb.setLayoutY(-new_val.doubleValue());
            }
        });

        //Horizontal scroll bar
        sch.setLayoutX(vb.getWidth() - sch.getWidth());
        sch.setMin(0);
        sch.setOrientation(Orientation.HORIZONTAL);
        sch.setPrefHeight(20);
        //sch.setPrefWidth(i.getWidth());
        sch.setLayoutX(20);
        sch.setLayoutY(0);
        sch.valueProperty().addListener(new ChangeListener<Number>() {
            /**
             * @param ov 
             * @param old_val
             * @param new_val
             */
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                vb.setLayoutY(-new_val.doubleValue());
            }
        });

        //select image
        copy.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * @param actionEvent 
             * copies image for specific point
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                final Rectangle selection = new Rectangle();
                final Light.Point anchor = new Light.Point();

                canvas.setOnMousePressed(event -> {
                    anchor.setX(event.getX());
                    anchor.setY(event.getY());
                    selection.setX(event.getX());
                    selection.setY(event.getY());
                    selection.setFill(null);
                    selection.setStroke(colorPicker.getValue());

            });
                canvas.setOnMouseDragged(event -> {
                    selection.setWidth(Math.abs(event.getX() - anchor.getX()));
                    selection.setHeight(Math.abs(event.getY() - anchor.getX()));
                    selection.setX(Math.min(anchor.getX(), event.getX()));
                    selection.setY(Math.min(anchor.getY(), event.getY()));

            });
                System.out.printf(String.valueOf(selection.getX()),selection.getWidth());
                    BufferedImage bufferedImage = null;
                    try {
                        bufferedImage = ImageIO.read(newFile);
                        //(int) selection.getX(),(int)selection.getY(),(int)selection.getWidth(),(int)selection.getHeight()
                        BufferedImage image = bufferedImage.getSubimage((int) selection.getX(),(int)selection.getY(),(int)selection.getWidth(),(int)selection.getHeight());
                        buffImage = SwingFXUtils.toFXImage(image, null);
                        //ImageIO.write(SwingFXUtils.fromFXImage(image,null), "PNG", newFile);
                        Clipboard clipboard = Clipboard.getSystemClipboard();
                        ClipboardContent content = new ClipboardContent();
                        content.putImage(buffImage);
                        clipboard.setContent(content);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        });
            paste.setOnAction(new EventHandler<ActionEvent>() {
                /**
                 * @param actionEvent 
                 * pastes image that was previously copied
                 */
                @Override
                public void handle(ActionEvent actionEvent) {
                    imageView3.setImage(buffImage);
                    //imageView3.setX(100);
                    //imageView3.setY(100);
                    stack1.getChildren().add(imageView3);

                }
            });

        //Timer
        Button timerButton = new Button("Save Timer ON/OFF");
        timerButton.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * @param actionEvent 
             * option for automatic save timer
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                if (buttonPress == true){
                    buttonPress = false;
                }
                if(!buttonPress){
                    buttonPress = true;
                }
            }
        });
        Timer timer = new Timer();
        TimerTask task = new Helper();
        if(buttonPress == true){

            timer.schedule(task, 2000, 5000);
        }
        if(buttonPress == false){
            timer.schedule(task, 0, 0);
        }

        //Clear canvas
        ImageView finalImageView3 = imageView;
        clear.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * @param actionEvent 
             * clears canvas
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert clearingAlert = new Alert(Alert.AlertType.CONFIRMATION);
                clearingAlert.setTitle("Clear Canvas");
                clearingAlert.setHeaderText("Are you sure you want to clear the canvas?");
                clearingAlert.setContentText("This will remove all drawings, shapes and images!");

                if(clearingAlert.showAndWait().get() == ButtonType.OK){
                    canvas.getGraphicsContext2D().clearRect(0,0,5000,5000);
                    finalImageView3.setImage(null);

                }
            }
        });

        //rotate
        Button r = new Button("Rotate 90 Degrees");
        ImageView finalImageView2 = imageView;
        r.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * @param actionEvent 
             * rotates image 90 degrees
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                Rotate rotate = new Rotate();
                rotate.setAngle(90);
                rotate.setPivotX(i.getWidth()/2);
                rotate.setPivotY(i.getHeight()/2);
                canvas.getTransforms().add(rotate);
                finalImageView2.getTransforms().add(rotate);
            }
        });
        Button r1 = new Button("Rotate 180 Degrees");
        ImageView finalImageView = imageView;
        r1.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * @param actionEvent 
             * rotate 180 degrees
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                Rotate rotate = new Rotate();
                rotate.setAngle(180);
                rotate.setPivotX(i.getWidth()/2);
                rotate.setPivotY(i.getHeight()/2);
                canvas.getTransforms().add(rotate);
                finalImageView.getTransforms().add(rotate);
            }
        });
        Button r2 = new Button("Rotate 270 Degrees");
        ImageView finalImageView1 = imageView;
        r2.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * @param actionEvent 
             * rotate 270 degrees
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                Rotate rotate = new Rotate();
                rotate.setAngle(270);
                rotate.setPivotX(i.getWidth()/2);
                rotate.setPivotY(i.getHeight()/2);
                canvas.getTransforms().add(rotate);
                finalImageView1.getTransforms().add(rotate);
            }
        });
        Button rc = new Button("Rotate Chunk 90 Degrees");
        rc.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * @param actionEvent 
             * rotate a selected chunk
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                final Rectangle selection = new Rectangle();
                final Light.Point anchor = new Light.Point();

                canvas.setOnMousePressed(event -> {
                    anchor.setX(event.getX());
                    anchor.setY(event.getY());
                    selection.setX(event.getX());
                    selection.setY(event.getY());
                    selection.setFill(null);
                    selection.setStroke(colorPicker.getValue());

                });
                canvas.setOnMouseDragged(event -> {
                    selection.setWidth(Math.abs(event.getX() - anchor.getX()));
                    selection.setHeight(Math.abs(event.getY() - anchor.getX()));
                    selection.setX(Math.min(anchor.getX(), event.getX()));
                    selection.setY(Math.min(anchor.getY(), event.getY()));
                });
                Rotate rotate = new Rotate();
                rotate.setAngle(90);
                selection.getTransforms().add(rotate);
            }
        });
        Button rc1 = new Button("Rotate Chunk 180 Dergees");
        rc1.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * @param actionEvent 
             * rotate a specific chunk 180 degrees
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                final Rectangle selection = new Rectangle();
                final Light.Point anchor = new Light.Point();

                canvas.setOnMousePressed(event -> {
                    anchor.setX(event.getX());
                    anchor.setY(event.getY());
                    selection.setX(event.getX());
                    selection.setY(event.getY());
                    selection.setFill(null);
                    selection.setStroke(colorPicker.getValue());

                });
                canvas.setOnMouseDragged(event -> {
                    selection.setWidth(Math.abs(event.getX() - anchor.getX()));
                    selection.setHeight(Math.abs(event.getY() - anchor.getX()));
                    selection.setX(Math.min(anchor.getX(), event.getX()));
                    selection.setY(Math.min(anchor.getY(), event.getY()));
                });
                Rotate rotate = new Rotate();
                rotate.setAngle(180);
                selection.getTransforms().add(rotate);
            }
        });
        Button rc2 = new Button("Rotate Chunk 270 Degrees");
        rc2.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * @param actionEvent 
             * rotate chunk of image 270 degrees
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                final Rectangle selection = new Rectangle();
                final Light.Point anchor = new Light.Point();

                canvas.setOnMousePressed(event -> {
                    anchor.setX(event.getX());
                    anchor.setY(event.getY());
                    selection.setX(event.getX());
                    selection.setY(event.getY());
                    selection.setFill(null);
                    selection.setStroke(colorPicker.getValue());

                });
                canvas.setOnMouseDragged(event -> {
                    selection.setWidth(Math.abs(event.getX() - anchor.getX()));
                    selection.setHeight(Math.abs(event.getY() - anchor.getX()));
                    selection.setX(Math.min(anchor.getX(), event.getX()));
                    selection.setY(Math.min(anchor.getY(), event.getY()));
                });
                Rotate rotate = new Rotate();
                rotate.setAngle(270);
                selection.getTransforms().add(rotate);
            }
        });

        //tooltip
        Tooltip tooltipd = new Tooltip();
        tooltipd.setText("Draw Tool");
        draw.setTooltip(tooltipd);

        Tooltip tooltipl = new Tooltip();
        tooltipl.setText("Line Tool");
        line.setTooltip(tooltipl);

        Tooltip tooltipe = new Tooltip();
        tooltipe.setText("Eraser Tool");
        eraser.setTooltip(tooltipe);

        //Threading
        Thread th = new Thread();
        //thread for timer
        //system thread checks with timer
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                add.setOnAction(new EventHandler<ActionEvent>() {
                    /**
                     * @param actionEvent 
                     * prints when event occured(not up to date)
                     */
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        System.out.println("10/21/2022 Pain(t) " + timer + " Image Saved");
                    }
                });
                add1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        System.out.println("10/21/2022 Pain(t) " + timer + " Image Save As");
                    }
                });
            }
        });
        th.start();
        System.out.println(th);



        //Scene
        ((VBox) scene.getRoot()).getChildren().addAll(menuBar,tabpane,textF,draw,line,eraser,clear,timerButton,r,r1,r2,rc,rc1,rc2,vb,user);
        ((VBox) thick.getRoot()).getChildren().addAll(buttonthick3,buttonthick5,endt);
        ((VBox) colors.getRoot()).getChildren().addAll(colorPicker,eyedrop,endc);
        ((VBox) closing.getRoot()).getChildren().addAll(buttonS,buttonSw,buttonC);
        primaryStage.setTitle("Pain(t)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args 
     * runs main codd
     */
    public static void main(String[] args) {
        launch();
    }


    /**
     * @return 
     * tests code from above(down to bottom)
     */
    public boolean methodLine() {
        double lwide = gc.getLineWidth();
        double swide = user.getStrokeWidth();
        if(lwide == swide){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean methodClearButton(){
        if(clear.isPressed()){
            canvas.getGraphicsContext2D().clearRect(0,0,5000,5000);
            return true;
        }
        else{
            return false;
        }

    }
   public boolean methodDrawButton(){
        if(draw.isPressed()){
            return true;

        }
        return false;
    }
    public void run(){

    }
    public File returnFile() {
        return newFile;
    }

    public Image returnImage(){
        return i;
    }
}
