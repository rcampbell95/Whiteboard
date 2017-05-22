package model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.sun.javafx.geom.Rectangle;
import view.DShape;

public class TableModel extends AbstractTableModel implements ModelListener{

	ArrayList<DShapeModel> modelArray;
	public static final String [] COLUMN_HEADERS = { "X", "Y", "Width", "Height"};

	/**
	 * TableModel Constructor
	 * Makes an empty arraylist for the models
	 */
	public TableModel()
	{
		super();		
		modelArray = new ArrayList<>();
	}

	/**
	 * Gets the column name
	 * @param column the number of the column
	 * @return column
	 */
	@Override
	public String getColumnName(int column) {
		return COLUMN_HEADERS[column];
	}

	/**
	 * Adds a model to the ArrayList that holds the models for the table, updates the table
	 * @param model
	 */
	public void addModel(DShapeModel model)
	{
		modelArray.add(0, model);
		model.addListener(this);
		fireTableDataChanged();

	}

	/**
	 * Removes a model from the ArrayList that holds the models for the table, updates the table
	 * @param model
	 */
	public void removeModel(DShapeModel model) {
        model.removeListener(this);
        modelArray.remove(model);
        fireTableDataChanged();

    }

	/**
	 * Clears the table
	 */
	public void clearTable()
	{
		if(!(modelArray.isEmpty()))
		{
			modelArray.clear();
			fireTableDataChanged();
		}
	}

	/**
	 * Gets the row for a specific model
	 * @param model the model that the row is to be found for
	 * @return row
	 */
	public int getRowForModel(DShapeModel model) {
	    return modelArray.indexOf(model);
    }

	/**
	 * Gets the column count (always gonna be 4 for this project)
	 */
	@Override
	public int getColumnCount() {
		return 4;
	}

	/**
	 * Gets the total row count of the table
	 */
	@Override
	public int getRowCount() {

		return modelArray.size();
	}

	/**
	 * Gets the value at a certain cell specified by a row and column as the arguments 
	 */
	@Override
	public Object getValueAt(int arg0, int arg1) {
		
		
		switch (arg1) {
        case 0:
            return modelArray.get(arg0).getBounds().getX();
        case 1:
            return modelArray.get(arg0).getBounds().getY();
        case 2:
            return modelArray.get(arg0).getBounds().getWidth();
        case 3:
            return modelArray.get(arg0).getBounds().getHeight();
        case 4:
            return modelArray.get(arg0);
        default:
            return null;}

		
	}

	/**
	 * Implementation for the model changed interface, if the model is changed, the table is updated
	 * @param model
	 */
	@Override
	public void modelChanged(DShapeModel model) {

		if(!modelArray.contains(model))
		{
			modelArray.add(0,model);
		}
		else{

		 int index = modelArray.indexOf(model);
	     fireTableRowsUpdated(index, index);
		}
	}

	/**
	 * Moves a model to the beginning of the arraylist
	 * @param model
	 */
	public void moveToFront(DShapeModel model)
	{
		if(!modelArray.isEmpty() && modelArray.contains(model))
		{
			modelArray.remove(model);
			modelArray.add(0, model);
			
		}
		
	}

	/**
	 * Moves a model to the end of the arraylist
	 * @param model
	 */
	public void moveToBack(DShapeModel model)
	{
		if(!modelArray.isEmpty() && modelArray.contains(model))
		{
			modelArray.remove(model);
			modelArray.add(model);
			
		}
	}

	/**
	 * Sets the cells to not be editable in the table
	 */
	@Override
	public boolean isCellEditable(int row, int column)
	{
		return false;
	}

}
