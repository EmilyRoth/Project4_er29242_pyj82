package assignment4;

import javafx.application.Application;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Object class for making a grid view
 */
public class CritterGrid {

    // update the view
    // add critters to the grid view
    // return the grid

    /*
     * Returns a square or a circle, according to shapeIndex
     */


    static Shape getIcon(int shapeIndex) {
        Shape s = null;
        int size = 100;

        switch(shapeIndex) {
            case 0: s = new Rectangle(size, size);
                s.setFill(javafx.scene.paint.Color.RED); break;
            case 1: s = new Circle(size/2);
                s.setFill(javafx.scene.paint.Color.GREEN); break;
        }
        // set the outline of the shape
        s.setStroke(javafx.scene.paint.Color.BLUE); // outline
        return s;
    }

    /*
     * Paints the shape on a grid.
     */
    public static void paint() {
        Main.gp.getChildren().clear(); // clean up grid.
        for (int i = 0; i <= 1; i++) {
            Shape s = getIcon(i);	// convert the index to an icon.
            Main.gp.add(s, i, i); // add the shape to the grid.
        }

    }

    public static Shape getCell(){
        Shape s = new Rectangle(5, 5);
        return s;
    }

    public static void createGrid(){
        //grid.getColumnConstraints().add(new ColumnConstraints(gridWidth));
        //grid.getRowConstraints().add(new RowConstraints(gridHeight));
    //    Main.gp.getColumnConstraints().add(new ColumnConstraints(Params.world_width));
      //  Main.gp.getRowConstraints().add(new RowConstraints(Params.world_height));
        Main.gp.setGridLinesVisible(true);

        for(int i = 0; i < Params.world_width; i++) {
            ColumnConstraints column = new ColumnConstraints(40);
            Main.gp.getColumnConstraints().add(column);
        }

        for(int i = 0; i < Params.world_height; i++) {
            RowConstraints row = new RowConstraints(40);
            Main.gp.getRowConstraints().add(row);
        }

   //     Circle s = new Circle(10);
   //     s.setFill(javafx.scene.paint.Color.RED);

     //   Main.gp.add(s, 2, 3);
    }

}
